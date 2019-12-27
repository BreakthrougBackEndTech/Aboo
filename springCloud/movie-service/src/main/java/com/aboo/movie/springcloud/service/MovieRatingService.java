package com.aboo.movie.springcloud.service;

import com.aboo.movie.springcloud.domain.MovieRating;
import com.aboo.movie.springcloud.mapper.MyBatisMovieRatingDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-12-25 14:33
 **/
@Service
@Slf4j
public class MovieRatingService {
    @Autowired
    MyBatisMovieRatingDao myBatisMovieRatingDao;

    public void saveOrUpdateMovieRating(MovieRating movieRating){
        //TODO 消息队列

        if(myBatisMovieRatingDao.getMovieRating(movieRating) > 0){
            myBatisMovieRatingDao.updateMovieRating(movieRating);
        }else{
            myBatisMovieRatingDao.insertMovieRating(movieRating);
        }
    }



}

