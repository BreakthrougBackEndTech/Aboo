package com.aboo.movie.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-12-12 10:38
 **/
@SpringBootApplication
@EnableZuulProxy
public class ZuulServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulServerApplication.class, args);
    }
}
