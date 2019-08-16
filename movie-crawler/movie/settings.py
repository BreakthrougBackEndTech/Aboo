# -*- coding: utf-8 -*-

# Scrapy settings for movie project
#
# For simplicity, this file contains only settings considered important or
# commonly used. You can find more settings consulting the documentation:
#
#     https://doc.scrapy.org/en/latest/topics/settings.html
#     https://doc.scrapy.org/en/latest/topics/downloader-middleware.html
#     https://doc.scrapy.org/en/latest/topics/spider-middleware.html

BOT_NAME = 'movie'

SPIDER_MODULES = ['movie.spiders']
NEWSPIDER_MODULE = 'movie.spiders'

# Crawl responsibly by identifying yourself (and your website) on the user-agent
# USER_AGENT = 'movie (+http://www.yourdomain.com)'

# Obey robots.txt rules
ROBOTSTXT_OBEY = True

# https://youngerfary.github.io/2017/05/28/%E7%88%AC%E8%99%AB%E6%89%8B%E8%AE%B0/
# 可以从网站上选取代理 https://www.xicidaili.com/
IPPOOL = [
    # {"ipaddr":"112.87.71.192:9999"},
    # {"ipaddr":"123.117.33.156:9000"},
    {"ipaddr": "10.144.1.10:8080"}
]

HTTPERROR_ALLOWED_CODES = [304,401]

# Configure maximum concurrent requests performed by Scrapy (default: 16)
# CONCURRENT_REQUESTS = 32

# Configure a delay for requests for the same website (default: 0)
# See https://doc.scrapy.org/en/latest/topics/settings.html#download-delay
# See also autothrottle settings and docs
# DOWNLOAD_DELAY = 3
# The download delay setting will honor only one of:
# CONCURRENT_REQUESTS_PER_DOMAIN = 16
# CONCURRENT_REQUESTS_PER_IP = 16

# Disable cookies (enabled by default)
# COOKIES_ENABLED = False

# Disable Telnet Console (enabled by default)
# TELNETCONSOLE_ENABLED = False

# Override the default request headers:
# DEFAULT_REQUEST_HEADERS = {
#     'Accept': 'application/json, text/plain, */*',
#     'Accept-Encoding': 'gzip, deflate, br',
#     'Accept-Language': 'zh-CN,zh;q=0.9,en;q=0.8',
#     # 'Cache-Control': 'max-age=0',
#     'Connection': 'keep-alive',
#     'Cookie': '_ga=GA1.2.732656159.1560931243; ml4_session=146f3d3c25ca2b5c0eb2803b4b9d9842cdd412cd_a3c333dc-5af4-46fe-b1ba-5dae86fa4a33; _gid=GA1.2.968515354.1565571838; uvts=240aec3a-6e70-4a5c-4a23-b80ec2da291f',
#     'Host': 'movielens.org',
#     # 'If-Modified-Since': 'Thu, 08 Aug 2019 19:20:14 GMT',
#     # 'If-None-Match': 'W/"5d4c75ee-c82"',
#     'Referer': 'https://movielens.org/login',
#     # 'Upgrade-Insecure-Requests': '1',
#     'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36'
# }

# Enable or disable spider middlewares
# See https://doc.scrapy.org/en/latest/topics/spider-middleware.html
SPIDER_MIDDLEWARES = {
    'scrapy.contrib.downloadermiddleware.httpproxy.HttpProxyMiddleware': None,
    'movie.middlewares.MovieSpiderMiddleware': 543,
}

# Enable or disable downloader middlewares
# See https://doc.scrapy.org/en/latest/topics/downloader-middleware.html
DOWNLOADER_MIDDLEWARES = {
    'scrapy.contrib.downloadermiddleware.httpproxy.HttpProxyMiddleware': None,
    'movie.middlewares.MovieDownloaderMiddleware': 543,
}

# Enable or disable extensions
# See https://doc.scrapy.org/en/latest/topics/extensions.html
# EXTENSIONS = {
#    'scrapy.extensions.telnet.TelnetConsole': None,
# }

# Configure item pipelines
# See https://doc.scrapy.org/en/latest/topics/item-pipeline.html
# 数字代表执行先后顺序
ITEM_PIPELINES = {
   'movie.pipelines.MovieImgDownloadPipeline': 300,
   'movie.pipelines.MysqlPipeline': 400,
}

IMAGES_STORE = 'images'

# 过期天数
# IMAGES_EXPIRES = 90  #90天内抓取的都不会被重抓

# Enable and configure the AutoThrottle extension (disabled by default)
# See https://doc.scrapy.org/en/latest/topics/autothrottle.html
# AUTOTHROTTLE_ENABLED = True
# The initial download delay
# AUTOTHROTTLE_START_DELAY = 5
# The maximum download delay to be set in case of high latencies
# AUTOTHROTTLE_MAX_DELAY = 60
# The average number of requests Scrapy should be sending in parallel to
# each remote server
# AUTOTHROTTLE_TARGET_CONCURRENCY = 1.0
# Enable showing throttling stats for every response received:
# AUTOTHROTTLE_DEBUG = False

# Enable and configure HTTP caching (disabled by default)
# See https://doc.scrapy.org/en/latest/topics/downloader-middleware.html#httpcache-middleware-settings
# HTTPCACHE_ENABLED = True
# HTTPCACHE_EXPIRATION_SECS = 0
# HTTPCACHE_DIR = 'httpcache'
# HTTPCACHE_IGNORE_HTTP_CODES = []
# HTTPCACHE_STORAGE = 'scrapy.extensions.httpcache.FilesystemCacheStorage'
