package com.aboo.movie.springcloud.domain;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-09-13 09:51
 **/
@Data
public class MybatisUser {
    private Long id;
    private String username;
    private String password;


    private List<MybatisRole> roles;
}

