package com.aboo.movie.springcloud.service;

import com.aboo.movie.springcloud.domain.Movie;
import com.aboo.movie.springcloud.mapper.MybatisMovieDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-12-23 15:55
 **/
@Service
@Slf4j
public class MybatisMovieService {
    @Autowired
    MybatisMovieDao mybatisMovieDao;

    public List<Movie> getMovies(int start, int length) {
        List<Movie> list = mybatisMovieDao.findMovies(start, length);

        return list;
    }

    public int getMovieNum() {
        return mybatisMovieDao.getMovieNum();
    }

    public Movie getMovieDetail(Integer movieId) {
        return mybatisMovieDao.getMovieDetail(movieId);
    }
}

