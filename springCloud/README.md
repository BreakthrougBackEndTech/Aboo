
### config-server
统一的配置服务，所有服务默认都会读取application.yml里面的配置，然后每个服务读取和自己服务名一样的配置

### regist-server
服务发现eureka

### recommend-web
推荐系统前端界面
#### Done
```task
spring security授权
Feign 服务调用，熔断，降级
csrf 解决方法, jwt
```
#### Todo 
```task
mybatis 分页
重构前端统一提交token js
添加movie-service
```

### user-service
用户服务
#### Done
```task
swagger集成 http://localhost:port/swagger-ui.html
mybatis注解
```
#### Todo

### admin-server
可以监控健康状态， 线程， 性能等
#### Todo
```task
认证
```

### zuul-server
统一的网关
http://localhost:5555/user-service/loadUserByUsername/luffy

微信公众号
![image](../img/weixin.jpg)
