package com.aboo.movie.springcloud.controller;

import com.aboo.movie.springcloud.domain.Movie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javafx.scene.input.KeyCode.M;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-09-19 09:26
 **/
@Controller
public class MovieController {

    @RequestMapping("movieList")
    public String trapList() {
        return "movie/movieList";
    }

    @RequestMapping("movieInfoList")
    @ResponseBody
    public Map<String, Object> trapInfoList(HttpServletRequest request, @RequestParam Integer start, @RequestParam Integer length,
                                            @RequestParam int draw, @RequestParam(value = "search[value]", required = false, defaultValue = "") String name) {
//        Pageable pageable = new PageRequest(start / length, length);

//        Page<TrapInfo> page = trapInfoService.findByNameLike("%" + name + "%", pageable);

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
        maps.put("data", movies);
        maps.put("recordsTotal", 2);
        maps.put("recordsFiltered", 2);
/*        maps.put("data", page.getContent());
        maps.put("recordsTotal", page.getTotalElements());
        maps.put("recordsFiltered", page.getTotalElements());*/
        maps.put("draw", draw);

        return maps;
    }


}

