package com.aboo.movie.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-09-09 18:32
 **/
@SpringBootApplication
@EnableHystrix
public class RecommendApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecommendApplication.class, args);
    }
}

