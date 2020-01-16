package com.aboo.movie.springcloud.controller;

import com.aboo.movie.springcloud.domain.Movie;
import com.aboo.movie.springcloud.service.MybatisMovieService;
import com.aboo.movie.springcloud.service.RecomMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-09-10 11:12
 **/
@RestController
//@RequestMapping(produces = "application/json")
@CrossOrigin(origins = "*")
public class MovieController {

    @Autowired
    MybatisMovieService mybatisMovieService;

    @Autowired
    RecomMovieService recomMovieService;

    @GetMapping(path = "/getMovies")
    public List<Movie> getMovies(@RequestParam int start, @RequestParam int length) {
        return mybatisMovieService.getMovies(start, length);
    }

    @GetMapping(path = "/getMovieNum")
    public int getMovieNum() {
        return mybatisMovieService.getMovieNum();
    }

    @GetMapping(path = "/getMovieDetail")
    public Movie getMovieDetail(@RequestParam Integer movieId) {
        return mybatisMovieService.getMovieDetail(movieId);
    }

    @GetMapping(path = "/getRecomMovies")
    public List<Movie> getRecomMovies(@RequestParam int userId){
        return recomMovieService.getRecomMovies(userId);
    }

}

