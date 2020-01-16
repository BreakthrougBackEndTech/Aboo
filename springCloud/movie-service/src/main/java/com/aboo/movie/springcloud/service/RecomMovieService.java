package com.aboo.movie.springcloud.service;

import com.aboo.movie.springcloud.domain.Movie;
import com.aboo.movie.springcloud.mapper.MybatisMovieDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: zhegong
 * @create: 2020-01-15 16:20
 **/
@Service
public class RecomMovieService {

    @Autowired
    RecomMovieClient recomMovieClient;

    @Autowired
    MybatisMovieDao mybatisMovieDao;

    public List<Movie> getRecomMovies(int userId){
        List<Integer> movieIds =  recomMovieClient.getRecomMovies(userId);

        List<Movie> movies = new ArrayList<>();
        for(Integer movieId : movieIds) {
            //可以添加batch查询优化性能
            movies.add(mybatisMovieDao.getMovieDetail(movieId));
        }

        return movies;
    }


}

