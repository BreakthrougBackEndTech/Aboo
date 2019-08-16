# -*- coding: utf-8 -*-
import scrapy
from scrapy.pipelines.images import ImagesPipeline
from scrapy.exceptions import DropItem

import mysql.connector


# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html


class MoviePipeline(object):
    def process_item(self, item, spider):
        return item


class MovieImgDownloadPipeline(ImagesPipeline):
    def get_media_requests(self, item, info):
        for image_url in item['imageUrls']:
            # self.default_headers['referer'] = image_url
            yield scrapy.Request(image_url)

    def item_completed(self, results, item, info):
        image_paths = [x['path'] for ok, x in results if ok]
        if not image_paths:
            raise DropItem("Item contains no images")

        # print("item")
        # print(item)
        # print("imagePath")
        # print(image_paths)
        item['imagePaths'] = image_paths
        return item


class MysqlPipeline(object):

    def __init__(self):
        self.conn = mysql.connector.connect(user='root', password='root', database='recommend')
        self.cursor = self.conn.cursor()

    def process_item(self, item, spider):
        print("mysql enter.................")
        # print(item)
        insert_sql = """
                            insert into movie(movieId, movieName, directors, actors, posterPath, plotSummary, avgRating, numRatings, imagePath)
                            VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)
                        """

        try:
            self.cursor.execute(insert_sql, (
                item["movieId"], item["movieName"], self.listToString(item["directors"]), self.listToString(item["actors"]), item["posterPath"],
                item["plotSummary"], item["avgRating"],
                item["numRatings"], item['imagePaths'][0]))
            self.conn.commit()
        except mysql.connector.errors.IntegrityError:
            pass

        return item

    def listToString(self, list):
        return "|".join(str(x) for x in list)



