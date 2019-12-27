package com.aboo.movie.springcloud.mapper;


import com.aboo.movie.springcloud.domain.MybatisRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MybatisRoleDao {

    @Select("select * from mybatis_role where user_id = #{userId}")
    List<MybatisRole> findRolesByUserId(Long userId);

    @Insert("<script>" +
            "insert into mybatis_role(name, user_id) values " +
            "<foreach collection='roleList' item='item' index='index' separator=','>" +
            "(#{item.name}, #{item.userId})" +
            "</foreach>" +
            "</script>")
    int insertRoleList(@Param("roleList") List<MybatisRole> roleList);


}
