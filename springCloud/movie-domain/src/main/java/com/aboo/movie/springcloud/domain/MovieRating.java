package com.aboo.movie.springcloud.domain;

import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-12-25 14:24
 **/
@Data
public class MovieRating {
    private int userId;
    private int movieId;
    private float rating;
    private Date date;
}

