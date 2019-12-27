package com.aboo.movie.springcloud.service;

import com.aboo.movie.springcloud.domain.Movie;
import com.aboo.movie.springcloud.domain.MovieRating;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: fallback 异常对代码不可见
 * @author: zhegong
 * @create: 2019-12-09 16:28
 **/
@Component
public class MovieServiceFallback implements MovieServiceClient {

    int defaultPage = 9;

    @Override
    public List<Movie> getMoviePage(int start, int length) {
        List<Movie> movies = new ArrayList<>();

        for(int i =1; i<defaultPage+1; i++){
            Movie movie = new Movie();
            movie.setMovieId(i);
            movie.setMovieName("movie" + i);
            movie.setImagePath("/images/testmovie.jpg");
            movies.add(movie);
        }

        return movies;
    }

    @Override
    public int getMovieNum() {
        return defaultPage;
    }

    @Override
    public Movie getMovieDetail(Integer movieId) {
        Movie movie = new Movie();
        movie.setMovieId(-1);
        movie.setMovieName("error");
        movie.setImagePath("/images/testmovie.jpg");

        return movie;
    }

    @Override
    public void updateMovieRating(MovieRating movieRating) {
        throw new RuntimeException("not support rating now");
    }
}

