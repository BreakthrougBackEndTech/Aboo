# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/items.html
import scrapy

class MovieItem(scrapy.Item):
    imageUrls = scrapy.Field()
    # image = scrapy.Field()
    imagePaths = scrapy.Field()

    movieId = scrapy.Field()
    movieName = scrapy.Field()
    directors = scrapy.Field()
    actors = scrapy.Field()
    posterPath = scrapy.Field()
    plotSummary = scrapy.Field()
    avgRating = scrapy.Field()
    numRatings = scrapy.Field()
    # genres = scrapy.Field()
