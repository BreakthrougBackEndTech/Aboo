package com.aboo.movie.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-12-09 16:17
 **/
@FeignClient(value = "movie-service", fallback = MovieServiceFallback.class)
public interface MovieServiceClient {

    @GetMapping("/getMoviePage")
    Map<String, Object> getMoviePage(@RequestParam("start") int start,
                                     @RequestParam("length") int length,
                                     @RequestParam("draw") int draw,
                                     @RequestParam("name") String name);
}

