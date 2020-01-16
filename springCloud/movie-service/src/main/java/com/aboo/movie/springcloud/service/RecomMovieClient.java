package com.aboo.movie.springcloud.service;

import com.aboo.movie.springcloud.domain.SysUser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: zhegong
 * @create: 2020-01-15 16:22
 **/
@Service
public class RecomMovieClient {

    //hard code for java invoke python RESTFul service
    @Value("http://127.0.0.1:5000/recom/{userId}")
    private String recomServiceUrl;


    @HystrixCommand(fallbackMethod = "getDefaultRecomMovies")
    public List<Integer> getRecomMovies(int userId) {
        RestTemplate restTemplate = new RestTemplate();

        List<Integer> result = restTemplate.getForObject(recomServiceUrl, List.class, userId);

        return result;
    }

    public List<Integer> getDefaultRecomMovies(int userId) {
        List<Integer> list = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            list.add(i);
        }
        return list;
    }
}

