package com.aboo.movie.springcloud.service;

import com.aboo.movie.springcloud.domain.Movie;
import com.aboo.movie.springcloud.domain.MovieRating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-12-09 16:17
 **/
@FeignClient(value = "movie-service", fallback = MovieServiceFallback.class)
public interface MovieServiceClient {

    @GetMapping("getMovies")
    List<Movie> getMoviePage(@RequestParam("start") int start,
                             @RequestParam("length") int length);

    @GetMapping("getMovieNum")
    int getMovieNum();

    @GetMapping("getMovieDetail")
    Movie getMovieDetail(@RequestParam("movieId")Integer movieId);

    @PostMapping("updateMovieRating")
    void updateMovieRating(MovieRating movieRating);
}

