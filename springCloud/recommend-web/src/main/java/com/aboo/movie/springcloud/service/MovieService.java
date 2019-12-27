package com.aboo.movie.springcloud.service;

import com.aboo.movie.springcloud.domain.Movie;
import com.aboo.movie.springcloud.domain.MovieRating;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**need download images with movie-crawler module*/
    private static final String imageFolder = "/images/";

    public List<Movie> getMoviePage(int start, int length) {
        List<Movie> movies = movieServiceClient.getMoviePage(start, length);
        //change images patch
        for (Movie movie : movies) {
            addImagePathPrefix(movie);
        }
        return movies;
    }

    public int getMovieNum() {
        return movieServiceClient.getMovieNum();
    }

    public Movie getMovieDetail(Integer movieId) {
        Movie movie = movieServiceClient.getMovieDetail(movieId);
        addImagePathPrefix(movie);

        return movie;
    }

    public void updateMovieRating(MovieRating movieRating){
        movieServiceClient.updateMovieRating(movieRating);
    }

    private void addImagePathPrefix(Movie movie) {
        movie.setImagePath(imageFolder + movie.getImagePath());
    }
}

