# coding=utf8

import sys

import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from main.util.utils import load_file, save_file

userModelFilepath = "store/lfm_user.pkl"
movieModelFilepath = "store/lfm_movie.pkl"

class LFM(object):
    """隐语义模型"""

    def __init__(self, k, learning_rate=0.01, regularization_rate=0.1, ratio=1):
        """
            @param k:要分成的类别
            @param learning_rate: 梯度下降的学习率
            @param regularization_rate:   正则化率
            @:param ratio: 正负样例比率，对性能最大影响
        """
        self.k = k
        self.regularization_rate = regularization_rate
        self.learning_rate = learning_rate
        self.ratio =ratio


    def read_data(self):
        data = pd.read_csv(
            'C:\\PF1GJ6B2-Data\\zhegong\\Desktop\\workspace\\Aboo\\springCloud\\movie-service\\src\\main\\resources\\ratings.csv',
            delimiter=',')

        data = data.iloc[:,0:3]

        # train, test = train_test_split(data, test_size=0.2)
        self.trainset = data
        # self.testset = test

    def init_data(self, trainset):
        item_size_dict = dict()
        user_item_dict = dict()
        for i in range(len(trainset)):
            cur_data=trainset.iloc[i]
            movieId=int(cur_data['movieId'])
            userId=int(cur_data['userId'])
            item_size_dict[movieId]=item_size_dict.get(movieId,0)+1
            item_rating_dict=user_item_dict.get(userId, dict())
            item_rating_dict[movieId]=cur_data['rating']
            user_item_dict[userId]=item_rating_dict
        item_size_dict = dict(sorted(item_size_dict.items(), key=lambda x: x[1], reverse=True))
        return item_size_dict, user_item_dict

    def build_matrix(self, ui, item_pool):
        """建立隐语义矩阵"""


        print("开始建立隐语义矩阵....", file=sys.stderr)
        # n_users,n_movies = len(self.trainset),len(self.item_pool)
        # 建立用户的隐语义
        self.user_p = dict()
        for userId in ui.keys():
            self.user_p[userId] = np.random.normal(size=(self.k))
            #np.random.rand 范围0-1

        self.movie_q = dict()
        for movieId in item_pool.keys():
            self.movie_q[movieId] = np.random.normal(size=(self.k))
            #np.random.rand 范围0-1
        print("隐语义矩阵建立完成", file=sys.stderr)


    def selectNegativeSamplesByPopularity(self,items):
        ret=dict()
        #所有正样本
        ret.update(items)
        #负样本个数，四舍五入
        negtiveNum = int(round(len(items)*self.ratio))

        # negitive_sample = random.choice(self.item_pool)
        N = 0
        for movieId in self.item_pool.keys():
            if N>negtiveNum:
                break
            if movieId in items:
                #如果在用户已经喜欢的物品列表中，继续选
                continue
            N+=1
            #负样本评分为0
            ret[movieId]=0
        return ret

    def loss(self):
        C = 0.
        for user, user_latent in self.user_p.items():
            for movie, movie_latent in self.movie_q.items():
                try:
                    rui = self.user_item[user][movie]
                except KeyError:
                    rui = 0
                eui = rui - self.predict(user, movie)
                C += np.square(eui)
                # C += (np.square(eui) +
                #       self.regularization_rate * np.sum(np.square(self.user_p[user])) +
                #       self.regularization_rate * np.sum(np.square(self.movie_q[movie]))
                #       )
        return C

    def predict(self, user_id, movie_id):
        return np.dot(self.user_p[user_id], self.movie_q[movie_id])

    def train(self, epoches):
        try:
            print("开始载入LFM模型....", file=sys.stderr)
            self.user_p = load_file(userModelFilepath)
            self.movie_q = load_file(movieModelFilepath)
            print("载入LFM模型完成", file=sys.stderr)
        except BaseException:
            print("载入LFM模型失败，重新训练", file=sys.stderr)
            # 读取数据
            self.read_data()
            # 获取商品集合
            # self.item_pool = self.init_itemPool(self.trainset)
            # 用户_电影集合
            # self.user_item = self.user_items(self.trainset)
            self.item_pool, self.user_item = self.init_data(self.trainset)

            # 建立隐语义矩阵
            self.build_matrix(self.user_item, self.item_pool)

            for epoch in range(epoches):
                print("开始第{}轮训练".format(epoch))
                for userId, movie_ratings in self.user_item.items():
                    select_samples = self.selectNegativeSamplesByPopularity(movie_ratings)
                    for movieId, rui in select_samples.items():
                        eui = rui - self.predict(userId, movieId)
                        user_latent = self.user_p[userId]
                        movie_latent = self.movie_q[movieId]

                        self.user_p[userId] += (self.learning_rate *
                                              (eui * movie_latent - self.regularization_rate * user_latent))
                        self.movie_q[movieId] += (self.learning_rate * (
                                eui * user_latent - self.regularization_rate * movie_latent))
                loss = self.loss()
                print("loss : {}".format(loss))
                print("第{}轮完成".format(epoch))
                # self.learning_rate *= 0.9
            #     0.9 * learning_rate 是否减小学习速率


            loss = self.loss()
            print("loss : {}".format(loss))

            print("开始保存协同过滤矩阵", file=sys.stderr)
            save_file(userModelFilepath, self.user_p)
            save_file(movieModelFilepath, self.movie_q)
            print("保存协同过滤矩阵完成", file=sys.stderr)

if __name__ == "__main__":
    lfm = LFM(50)
    lfm.train(1000)
