import scrapy
import json

import pandas as pd
import mysql
from movie.items import MovieItem


class CookieLoginSpider(scrapy.Spider):
    name = "movie"
    allowed_domains = ["movielens.org"]
    imageUrlPrefix = "http://image.tmdb.org/t/p/w185"

    movieMap = {}
    totalNum = 0

    # start_urls = [
    #     # "https://movielens.org/explore?page=1",
    #     # "https://movielens.org/api/movies/explore?page=1",
    #     "https://movielens.org/login"
    # ]

    # 自定义的爬虫设置，会覆盖全局setting中的设置
    # custom_settings = {
    #     # "ITEM_PIPELINES": {
    #     #     'Douban.pipelines.MoviePipeline': 300
    #     # },
    #     "DEFAULT_REQUEST_HEADERS": {
    #         'Accept': 'application/json, text/plain, */*',
    #         'Accept-Encoding': 'gzip, deflate, br',
    #         'Accept-Language': 'zh-CN,zh;q=0.9,en;q=0.8',
    #         # 'Cache-Control': 'max-age=0',
    #         'Connection': 'keep-alive',
    #         'Content-Length': '44',
    #         'Content-Type': 'application/json',
    #         'Cookie': '_ga=GA1.2.732656159.1560931243; _gid=GA1.2.968515354.1565571838; uvts=240aec3a-6e70-4a5c-4a23-b80ec2da291f; ml4_session=83a9258e298775450fcef9daa0850c40feb5a5bb_00754b7e-3cbd-4dda-a2cf-3c5fcfcaaf00',
    #         'Host': 'movielens.org',
    #         # 'If-Modified-Since': 'Thu, 08 Aug 2019 19:20:14 GMT',
    #         # 'If-None-Match': 'W/"5d4c75ee-c82"',
    #         'Origin': 'https://movielens.org',
    #         'Referer': 'https://movielens.org/login',
    #         # 'Upgrade-Insecure-Requests': '1',
    #         'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36'
    #     },
    #     "ROBOTSTXT_OBEY": False  # 需要忽略ROBOTS.TXT文件
    # }

    def start_requests(self):
        cookies = '_ga=GA1.2.732656159.1560931243; _gid=GA1.2.968515354.1565571838; uvts=240aec3a-6e70-4a5c-4a23-b80ec2da291f; ml4_session=0d076c18a684971cff95bf54ef95bbb76bcfc282_1caf0445-20d0-48ee-9202-7a4571945f70'
        cookies = {i.split('=')[0]: i.split('=')[1] for i in cookies.split('; ')}

        print(cookies)

        self.buildMap()

        for i in range(1, 2820):
            url = 'https://movielens.org/api/movies/explore?page=%s' % i
            print(url)
            yield scrapy.Request(url,
                                 cookies=cookies,
                                 callback=self.parse)

        self.insertOtherMovies()

        print("total num %s"% self.totalNum)

        # return [scrapy.Request("https://movielens.org/api/movies/explore?page=1",
        #                        cookies=cookies,
        #                        callback=self.parse)]

    def parse(self, response):
        # print('Preparing parse')
        # 业务逻辑
        # print(response)
        searchResults = json.loads(response.body)['data']['searchResults']

        for result in searchResults:
            movie = result["movie"]
            if movie["movieId"] not in self.movieMap.keys():
                # print(movie["movieId"])
                pass
            else:
                urls = []
                urls.append(self.imageUrlPrefix + movie["posterPath"])
                item = MovieItem()
                item['imageUrls'] = urls
                item['movieId'] = movie["movieId"]
                item['movieName'] =movie['title']
                item['directors'] =movie['directors']
                item['actors'] =movie['actors']
                item['posterPath'] =movie['posterPath']
                item['plotSummary'] =movie['plotSummary']
                item['avgRating'] =movie['avgRating']
                item['numRatings'] =movie['numRatings']

                self.movieMap.pop(movie["movieId"])
                self.totalNum += 1
                yield item

    def buildMap(self):
        csvData = pd.read_csv(r'ml-latest-small/movies.csv', delimiter = ',')
        for row in csvData.iterrows():
            self.movieMap[row[1].get('movieId')] = row[1].get('title')
            # print(row[1].get('title'))

    def insertOtherMovies(self):
        print("other movies................")
        conn = mysql.connector.connect(user='root', password='root', database='recommend')
        cursor = conn.cursor()
        insert_sql = """
                            insert into movie(movieId, movieName)
                            VALUES (%s, %s)
                        """
        for key, value in self.movieMap.items():
            print(value)
            self.totalNum += 1
            cursor.execute(insert_sql, (key, value))
        conn.commit()

# {
#     "status": "success",
#     "data": {
#         "title": null,
#         "description": null,
#         "url": null,
#         "searchResults": [
#             {
#                 "movieId": 6862,
#                 "movie": {
#                     "movieId": 6862,
#                     "tmdbMovieId": 2116,
#                     "imdbMovieId": "0313443",
#                     "title": "Out of Time",
#                     "originalTitle": "Out of Time",
#                     "mpaa": "R",
#                     "runtime": 105,
#                     "releaseDate": "2003-10-03",
#                     "dvdReleaseDate": "2004-01-06",
#                     "genres": [
#                         "Thriller",
#                         "Crime",
#                         "Drama"
#                     ],
#                     "languages": [
#                         "English"
#                     ],
#                     "directors": [
#                         "Carl Franklin"
#                     ],
#                     "actors": [
#                         "Denzel Washington",
#                         "Eva Mendes",
#                         "Sanaa Lathan",
#                         "John Billingsley",
#                         "Dean Cain",
#                         "Alex Carter",
#                         "Antoni Corone",
#                         "Robert Baker",
#                         "Ron Madoff",
#                         "Terry Loughlin",
#                         "Nora Dunn",
#                         "Jesse Beaton"
#                     ],
#                     "posterPath": "/wdniUkm0hwXv2RqzvwWy5XtRNvB.jpg",
#                     "backdropPaths": [
#                         "/adnZPm5HFmrtT1DwC82p512Vmqx.jpg",
#                         "/AmzCMsWyWAsUCRSTXHPmm3cK8mm.jpg",
#                         "/y8DXPscqF9dr9spvxOybIUIleJl.jpg",
#                         "/aDCT5cRdJM3Y8upkMg25HsWb5fN.jpg",
#                         "/qcUhQ80zNvCz11l7P2iKRcXOobB.jpg",
#                         "/653fQgbHHTmK6Rih9IH6W2SMpCT.jpg",
#                         "/nyvKU0RQIeK8g7yhmadQND9mghH.jpg",
#                         "/uZpZrjB2bbcQqM2oJKNNtNc1IMN.jpg",
#                         "/qssYXMqv5YBJsp2gE9bKhA82LGm.jpg",
#                         "/qshSb5x3AKS7K2IygYBtGeFLlbe.jpg",
#                         "/8x4iusufQPuKhiGPhCeK01QJULW.jpg",
#                         "/pqu2vFliH9EKQoHq5eQJmD3qkpN.jpg"
#                     ],
#                     "youtubeTrailerIds": [
#                         "FmhGN6dC0zc"
#                     ],
#                     "plotSummary": "Matt Lee Whitlock, respected chief of police in small Banyan Key, Florida, must solve a vicious double homicide before he himself falls under suspicion. Matt Lee has to stay a few steps ahead of his own police force and everyone he's trusted in order to find out the truth.",
#                     "numRatings": 963,
#                     "avgRating": 3.24974,
#                     "releaseYear": "2003"
#                 },
#                 "movieUserData": {
#                     "userId": 341975,
#                     "movieId": 6862,
#                     "rating": null,
#                     "prediction": 3.2556547202404382,
#                     "wishlist": false,
#                     "hidden": false,
#                     "predictionDetails": {
#                         "primaryPrediction": 3.2556547202404382,
#                         "primaryPredictionType": "USER",
#                         "components": [
#                             {
#                                 "pred": 3.2556547202404382,
#                                 "type": "USER"
#                             },
#                             {
#                                 "pred": 3.2556547202404382,
#                                 "type": "FALLBACK"
#                             }
#                         ]
#                     }
#                 }
#             },
