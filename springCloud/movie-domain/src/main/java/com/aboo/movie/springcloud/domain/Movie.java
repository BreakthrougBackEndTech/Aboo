package com.aboo.movie.springcloud.domain;

import lombok.Data;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-09-19 09:29
 **/
@Data
public class Movie {
    private Integer movieId;
    private String movieName;
    private String directors;
    private String actors;
    private String posterPath;
    private String plotSummary;
    private Float avgRating;
    private Integer numRatings;
    private String imagePath;
}

