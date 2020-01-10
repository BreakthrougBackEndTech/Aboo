
# -*- coding: utf-8 -*-
'''
Created on 2017年10月16日
@author: Administrator
'''
'''
实现隐语义模型，对隐式数据进行推荐
1.对正样本生成负样本
  -负样本数量相当于正样本
  -物品越热门，越有可能成为负样本
2.使用随机梯度下降法，更新参数
'''
import math
import time
from math import exp

import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split


class LFMold:

    '''
            初始化隐语义模型
            参数：*data 训练数据,要求为pandas的dataframe
       *F  隐特征的个数      *N  迭代次数        *alpha 随机梯度下降的学习速率
       *lamda 正则化参数  *ratio 负样本/正样本比例  *topk 推荐的前k个物品
    '''
    def __init__(self,data,ratio,F=5,N=2,alpha=0.02,lamda=0.01,topk=10):
        self.data=data #样本集
        self.ratio =ratio #正负样例比率，对性能最大影响
        self.F = F#隐类数量，对性能有影响
        self.N = N#迭代次数，收敛的最佳迭代次数未知
        self.alpha =alpha#梯度下降步长
        self.lamda = lamda#正则化参数
        self.topk =topk #推荐top k项

    '''
                初始化物品池，物品池中物品出现的次数与其流行度成正比
    {item1:次数,item2:次数,...}
    '''
    def InitItemPool(self):
        itemPool=dict()
        groups = self.data.groupby([1])
        for item,group in groups:
            itemPool.setdefault(item,0)
            itemPool[item] =group.shape[0]
        itemPool=dict(sorted(itemPool.items(), key = lambda x:x[1], reverse = True))
        return itemPool
    '''
                获取每个用户对应的商品（用户购买过的商品）列表，如
    {用户1:[商品A，商品B，商品C],
                 用户2:[商品D，商品E，商品F]...}
    '''
    def user_item(self):
        ui = dict()
        groups = self.data.groupby([0])
        for item,group in groups:
            ui[item]=set(group.loc[:,1])
        return ui

    '''
                    初始化隐特征对应的参数
      numpy的array存储参数，使用dict存储每个用户（物品）对应的列
    '''
    def initParam(self):
        users=set(self.data.loc[:,0])
        items=set(self.data.loc[:,1])

        arrayp = np.random.rand(len(users), self.F) #构造p矩阵，[0,1]内随机值
        arrayq = np.random.rand(self.F, len(items)) #构造q矩阵，[0,1]内随机值
        P = pd.DataFrame(arrayp, columns=range(0, self.F), index=users)
        Q = pd.DataFrame(arrayq, columns=items, index=range(0,self.F))
        return P,Q

        '''
        self.Pdict=dict()
        self.Qdict=dict()
        for user in users:
            self.Pdict[user]=len(self.Pdict)
        
        for item in items:
            self.Qdict[item]=len(self.Qdict)
        
        self.P=np.random.rand(self.F,len(users))
        self.Q=np.random.rand(self.F,len(items))
        '''
    '''
        生成负样本
    '''
    def RandSelectNegativeSamples(self,items):
        ret=dict()
        for item in items:
            #所有正样本评分为1
            ret[item]=1
        #负样本个数，四舍五入
        negtiveNum = int(round(len(items)*self.ratio))

        N = 0
        #while N<negtiveNum:
        #item = self.itemPool[random.randint(0, len(self.itemPool) - 1)]
        for item,count in self.itemPool.items():
            if N>negtiveNum:
                break
            if item in items:
                #如果在用户已经喜欢的物品列表中，继续选
                continue
            N+=1
            #负样本评分为0
            ret[item]=0
        return ret

    def sigmod(self,x):
        # 单位阶跃函数,将兴趣度限定在[0,1]范围内
        y = 1.0/(1+exp(-x))
        return y

    def lfmPredict(self,p, q, userID, itemID):
        #利用参数p,q预测目标用户对目标物品的兴趣度
        p = np.mat(p.loc[userID].values)
        q = np.mat(q[itemID].values).T
        r = (p * q).sum()
        r = self.sigmod(r)
        return r
    '''
            使用随机梯度下降法，更新参数
    '''
    def stochasticGradientDecent(self, user_p, movie_q):
        alpha=self.alpha
        for i in range(self.N):
            for user,items in self.ui.items():
                ret=self.RandSelectNegativeSamples(items)
                for item,rui in ret.items():
                    eui = rui - self.lfmPredict(user_p, movie_q, user, item)
                    for f in range(0, self.F):
                        #df[列][行]定位
                        tmp= alpha * (eui * movie_q[item][f] - self.lamda * user_p[f][user])
                        movie_q[item][f] += alpha * (eui * user_p[f][user] - self.lamda * movie_q[item][f])
                        user_p[f][user] +=tmp

            alpha*=0.9
        return user_p, movie_q

    def Train(self):
        self.itemPool=self.InitItemPool()#生成物品的热门度排行
        self.ui = self.user_item()#生成用户-物品
        user_p,movie_q=self.initParam()#生成p,q矩阵
        self.P,self.Q=self.stochasticGradientDecent(user_p,movie_q)  #随机梯度下降训练

    def Recommend(self,user):
        items=self.ui[user]
        predictList = [self.lfmPredict(self.P, self.Q, user, item) for item in items]
        series = pd.Series(predictList, index=items)
        series = series.sort_values(ascending=False)[:self.topk]
        return series
        '''
        #items=self.ui[user]
        p=self.P[:,self.Pdict[user]]
        
        rank = dict()
        for item,id in self.Qdict.items():
            #if item in items:
            #    continue
            q=self.Q[:,id];
            rank[item]=sum(p*q)
        #return sorted(rank.items(),lambda x,y:operator.gt(x[0],y[0]),reverse=True)[0:self.topk-1];
        return sorted(rank.items(),key=operator.itemgetter(1),reverse=True)[0:self.topk-1];
        '''

    def recallAndPrecision(self,test):#召回率和准确率
        userID=set(test.loc[:,0])
        hit = 0
        recall = 0
        precision = 0
        for userid in userID:
            #trueItem = test[test.loc[:,0] == userid]
            #trueItem= trueItem.loc[:,1]
            trueItem=self.ui[userid]
            preitem=self.Recommend(userid)
            for item in list(preitem.index):
                if item in trueItem:
                    hit += 1
            recall += len(trueItem)
            precision += len(preitem)
        return (hit / (recall * 1.0),hit / (precision * 1.0))

    def coverage(self,test):#覆盖率
        userID=set(test.loc[:,0])
        recommend_items = set()
        all_items = set()
        for userid in userID:
            #trueItem = test[test.loc[:,0] == userid]
            #trueItem= trueItem.loc[:,1]
            trueItem=self.ui[userid]
            for item in trueItem:
                all_items.add(item)
            preitem=self.Recommend(userid)
            for item in list(preitem.index):
                recommend_items.add(item)
        return len(recommend_items) / (len(all_items) * 1.0)

    def popularity(self,test):#流行度
        userID=set(test.loc[:,0])
        ret = 0
        n = 0
        for userid in userID:
            preitem=self.Recommend(userid)
            for item in list(preitem.index):
                ret += math.log(1+self.itemPool[item])
                n += 1
        return ret / (n * 1.0)

if __name__ == "__main__":
    start = time.perf_counter()

    #导入数据
    data=pd.read_csv('C:\\PF1GJ6B2-Data\\zhegong\\Desktop\\workspace\\Aboo\\springCloud\\movie-service\\src\\main\\resources\\ratings.csv',nrows=10000,header=None)
    data=data.drop(0)
    data=data.loc[:,0:1]


    train,test=train_test_split(data,test_size=0.2)
    train = pd.DataFrame(train)
    test = pd.DataFrame(test)
    print ("%3s%20s%20s%20s%20s" % ('ratio',"recall",'precision','coverage','popularity'))
    for ratio in [1,2,3,5]:
        lfm = LFMold(data, ratio)
        lfm.Train()
        #rank=lfm.Recommend('1')
        #print (rank)
        recall,precision = lfm.recallAndPrecision(test)
        coverage =lfm.coverage(test)
        popularity =lfm.popularity(test)
        print ("%3d%19.3f%%%19.3f%%%19.3f%%%20.3f" % (ratio,recall * 100,precision * 100,coverage * 100,popularity))

    end = time.perf_counter()
    print('finish all in %s' % str(end - start))