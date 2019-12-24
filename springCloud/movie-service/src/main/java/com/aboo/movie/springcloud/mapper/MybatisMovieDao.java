package com.aboo.movie.springcloud.mapper;


import com.aboo.movie.springcloud.domain.Movie;
import com.aboo.movie.springcloud.domain.MybatisRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MybatisMovieDao {

    @Select("select * from movie limit #{start}, #{length}")
    List<Movie> findMovies(@Param("start") int start, @Param("length")int length);


    @Select("select count(movieId) from movie")
    int getMovieNum();

    @Select("select * from movie where movieId=#{movieId}")
    Movie getMovieDetail(Integer movieId);

}
