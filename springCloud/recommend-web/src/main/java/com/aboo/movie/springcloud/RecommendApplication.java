package com.aboo.movie.springcloud;

import brave.sampler.Sampler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-09-09 18:32
 **/
@SpringBootApplication
@EnableHystrix
public class RecommendApplication {
    @Bean
    public Sampler alwaysSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

    public static void main(String[] args) {
        SpringApplication.run(RecommendApplication.class, args);
    }
}

