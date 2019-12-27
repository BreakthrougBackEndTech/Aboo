package com.aboo.movie.springcloud.mapper;

import com.aboo.movie.springcloud.domain.MybatisUser;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

/**
 * 如果在启动类里面 使用了 @MapperScan("com.aboo.movie.springcloud.mapper")， 这里就不需要添加注解了
 */
@Mapper
public interface MybatisUserDao {
    @Select("select * from mybatis_user where username =  #{username}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "roles", column = "id",
                    many = @Many(select = "com.aboo.movie.springcloud.mapper.MybatisRoleDao.findRolesByUserId"))
    })
    Optional<MybatisUser> findByUsername(String username);


    @Insert("insert into mybatis_user(username,password) values(#{username},#{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn="id")
    int insertUser(MybatisUser user);

}
