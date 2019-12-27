package com.aboo.movie.springcloud.mapper;

import com.aboo.movie.springcloud.domain.MovieRating;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-12-25 14:27
 **/
@Mapper
public interface MyBatisMovieRatingDao {
    @Insert("insert into movie_rating(userId, movieId, rating, date) values(#{userId}, #{movieId}, #{rating}, #{date})")
    int insertMovieRating(MovieRating movieRating);
//    int insertMovieRating(int userId, int movieId, float rating, Date date);

    @Update("update movie_rating set rating=#{rating}, date=#{date} where userId=#{userId} and movieId=#{movieId}")
    int updateMovieRating(MovieRating movieRating);
//    int updateMovieRating(int userId, int movieId, float rating, Date date);

    @Select("select count(userId) from movie_rating where userId=#{userId} and movieId=#{movieId}")
    int getMovieRating(MovieRating movieRating);
//    int getMovieRating(int userId, int movieId);
}
