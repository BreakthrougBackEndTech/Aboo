package com.aboo.movie.springcloud.controller;

import com.aboo.movie.springcloud.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-09-19 09:26
 **/
@Controller
public class MovieController {

    @Autowired
    MovieService movieService;

    @RequestMapping("movieList")
    public String trapList() {
        return "movie/movieList";
    }


    @RequestMapping("movieInfoList")
    @ResponseBody
    public Map<String, Object> trapInfoList(HttpServletRequest request, @RequestParam Integer start, @RequestParam Integer length,
                                            @RequestParam(required = false, defaultValue = "0") int draw,
                                            @RequestParam(value = "search[value]", required = false, defaultValue = "") String name) {
//        Pageable pageable = new PageRequest(start / length, length);

//        Page<TrapInfo> page = trapInfoService.findByNameLike("%" + name + "%", pageable);

        return movieService.getMoviePage(start, length, draw, name);
    }


}

