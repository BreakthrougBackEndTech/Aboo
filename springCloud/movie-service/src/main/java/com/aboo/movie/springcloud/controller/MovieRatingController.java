package com.aboo.movie.springcloud.controller;

import com.aboo.movie.springcloud.domain.MovieRating;
import com.aboo.movie.springcloud.service.MovieRatingService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.List;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-12-25 14:43
 **/
@RestController
public class MovieRatingController {
    @Autowired
    MovieRatingService movieRatingService;




    @PostMapping(path = "/updateMovieRating")
    public void updateMovieRating(@RequestBody MovieRating movieRating){
        movieRatingService.saveOrUpdateMovieRating(movieRating);
    }

    /**
     * 仅内部使用
     */
    @GetMapping(path = "/initRatingData")
    public void initRatingData() throws IOException {

        List<String> movieRatingList= FileUtils.readLines(new ClassPathResource("ratings.csv").getFile(), "utf-8");

        for (int i = 1; i < movieRatingList.size(); i++) {
            MovieRating movieRating = parseStr(movieRatingList.get(i));
            movieRatingService.saveOrUpdateMovieRating(movieRating);
        }
    }

    private MovieRating parseStr(String s) {
        MovieRating movieRating = new MovieRating();
        String[] arr = s.split(",");
        movieRating.setUserId(Integer.valueOf(arr[0]));
        movieRating.setMovieId(Integer.valueOf(arr[1]));
        movieRating.setRating(Float.valueOf(arr[2]));
        movieRating.setDate(Calendar.getInstance().getTime());

        return movieRating;
    }

}

