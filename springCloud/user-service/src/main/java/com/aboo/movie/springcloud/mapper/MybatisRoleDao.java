package com.aboo.movie.springcloud.mapper;


import com.aboo.movie.springcloud.domain.MybatisRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MybatisRoleDao {

    @Select("select * from mybatis_role where user_id = #{userId}")
    List<MybatisRole> findRolesByUserId(Long userId);

}
