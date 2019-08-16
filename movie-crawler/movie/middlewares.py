# -*- coding: utf-8 -*-

# Define here the models for your spider middleware
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/spider-middleware.html

import random
from scrapy import signals
from movie.settings import IPPOOL


class MovieSpiderMiddleware(object):
    def __init__(self,ip=''):
        self.ip=ip

    def process_request(self, request, spider):
        thisip=random.choice(IPPOOL)
        print("this is ip:"+thisip["ipaddr"])
        request.meta["proxy"]="http://"+thisip["ipaddr"]


class MovieDownloaderMiddleware(object):
    def __init__(self,ip=''):
        self.ip=ip

    def process_request(self, request, spider):
        thisip=random.choice(IPPOOL)
        print("this is ip:"+thisip["ipaddr"])
        request.meta["proxy"]="http://"+thisip["ipaddr"]
