package com.aboo.movie.springcloud.controller;

import com.aboo.movie.springcloud.MovieUser;
import com.aboo.movie.springcloud.domain.Movie;
import com.aboo.movie.springcloud.domain.MovieRating;
import com.aboo.movie.springcloud.service.CustomUserService;
import com.aboo.movie.springcloud.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-09-19 09:26
 **/
@Controller
public class MovieController {

    @Autowired
    MovieService movieService;

    @Autowired
    CustomUserService customUserService;

    @RequestMapping("movieList")
    public String trapList() {
        return "movie/movieList";
    }


    @RequestMapping("movieInfoList")
    @ResponseBody
    public Map<String, Object> trapInfoList(HttpServletRequest request, @RequestParam Integer start, @RequestParam Integer length,
                                            @RequestParam(required = false, defaultValue = "0") int recordsTotal,
                                            @RequestParam(value = "search[value]", required = false, defaultValue = "") String name) {
//        Pageable pageable = new PageRequest(start / length, length);

//        Page<TrapInfo> page = trapInfoService.findByNameLike("%" + name + "%", pageable);

        Map<String, Object> maps = new HashMap<>();
        maps.put("movies", movieService.getMoviePage((start - 1) * length, length));
        maps.put("recordsTotal", recordsTotal == 0 ? movieService.getMovieNum() : recordsTotal);

        return maps;
    }

    @RequestMapping("movieDetail")
    public String movieDetail(Model model, HttpServletRequest request, @RequestParam Integer movieId) {
        Movie movie = movieService.getMovieDetail(movieId);
        model.addAttribute("movie", movie);

        return "movie/movieDetail";
    }

    @PostMapping(path = "/ratedMovie")
    @ResponseBody
    public Map<String, Object> ratedMovie(Principal principal, @RequestParam int movieId, @RequestParam float rated) {
        Map<String, Object> maps = new HashMap<>();

        try {
//        String userName = principal.getName();
// Authentication authentication 或是用
            MovieRating movieRating = new MovieRating();
            movieRating.setUserId(customUserService.getUserId(principal.getName()));
            movieRating.setDate(Calendar.getInstance().getTime());
            movieRating.setMovieId(movieId);
            movieRating.setRating(rated);
            movieService.updateMovieRating(movieRating);

            maps.put("result", "rated success");
        } catch (Exception e) {
            maps.put("result", "rated failed");
        }

        return maps;
    }


    @RequestMapping("recomMovies")
    public String recomMovies() {
        return "movie/recomMovies";
    }


    @RequestMapping("recomMovieInfoList")
    @ResponseBody
    public Map<String, Object> recomMovieInfoList(Authentication authentication,
                                                  @RequestParam(required = false, defaultValue = "0") int top) {
        Map<String, Object> maps = new HashMap<>();
        try {
            maps.put("movies", movieService.getRecomMovies(customUserService.getUserId(authentication.getName())));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return maps;
    }


}

