import scrapy


# 这个方式还不work, 后续继续研究
class FormLoginSpider(scrapy.Spider):
    name = "from_login"
    allowed_domains = ["movielens.org"]

    start_urls = [
        "https://movielens.org/login"
    ]

    def parse(self, response):
        url = 'https://movielens.org/api/sessions'  # 从源码中form表单提取的action网址

        form_data = {'userName': 'luffygong', 'password': '123456'}

        # FormRequest 是Scrapy发送POST请求的方法
        yield scrapy.FormRequest(
            url=url,
            formdata=form_data,
            callback=self.after_login
        )

        # 这种方式会报form 不存在的异常
        # yield scrapy.FormRequest.from_response(response,formdata=form_data,callback=self.after_login)

    def after_login(self, response):
        print('login status')
        print(response)
