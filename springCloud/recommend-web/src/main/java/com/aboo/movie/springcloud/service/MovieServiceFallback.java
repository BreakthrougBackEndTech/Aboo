package com.aboo.movie.springcloud.service;

import com.aboo.movie.springcloud.domain.Movie;
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

    @Override
    public Map<String, Object> getMoviePage(int start, int length, int draw, String name) {
        List<Movie> movies = new ArrayList<>();

        Movie movie1 = new Movie();
        movie1.setMovieId(1);
        movie1.setMovieName("movie1");
        movies.add(movie1);

        Movie movie2 = new Movie();
        movie2.setMovieId(2);
        movie2.setMovieName("movie2");
        movies.add(movie2);

        Map<String, Object> maps = new HashMap<>();
        maps.put("movies", movies);
        maps.put("recordsTotal", 31);
/*        maps.put("data", page.getContent());
        maps.put("recordsTotal", page.getTotalElements());
        maps.put("recordsFiltered", page.getTotalElements());*/
        maps.put("draw", draw);

        return maps;
    }
}

