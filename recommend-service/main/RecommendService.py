from flask import Flask, jsonify
from main.util.utils import load_file
from main.LFM import LFM
import numpy as np

app = Flask(__name__)

lfm = LFM(10)
lfm.read_data()
item_pool, user_item=lfm.init_data(lfm.trainset)
user_p = load_file("store/lfm_user.pkl")
movie_q = load_file("store/lfm_movie.pkl")

@app.route('/')
def hello_world():
    return 'Hello World!'

@app.route('/recom/<int:userId>')
def show_post(userId):
    movie_rating=dict()

    for movieId in item_pool.keys():
        if movieId in user_item[userId]:
            continue

        movie_rating[movieId] = np.dot(user_p[userId], movie_q[movieId])

    movie_rating = dict(sorted(movie_rating.items(), key=lambda x: x[1], reverse=True)[:10])

    ret = list(movie_rating.keys())

    return jsonify(ret)


if __name__ == '__main__':
    app.run()