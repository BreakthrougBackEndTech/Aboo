package com.aboo.movie.springcloud.controller;

import com.aboo.movie.springcloud.domain.Movie;
import com.aboo.movie.springcloud.service.MybatisMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(path = "/getMovies")
    public List<Movie> getMovies(@RequestParam int start, @RequestParam int length) {
        return mybatisMovieService.getMovies(start, length);
    }

    @GetMapping(path = "/getMovieNum")
    public int getMovieNum() {
        return mybatisMovieService.getMovieNum();
    }
}

