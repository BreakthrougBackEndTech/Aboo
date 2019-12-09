package com.aboo.movie.springcloud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-12-09 16:17
 **/
@Service
@Slf4j
public class MovieService {

    @Autowired
    private MovieServiceClient movieServiceClient;

    public Map<String, Object> getMoviePage(int start, int length, int draw, String name) {
        return movieServiceClient.getMoviePage(start, length, draw, name);
    }
}

