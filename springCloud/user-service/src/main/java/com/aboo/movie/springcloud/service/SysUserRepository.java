package com.aboo.movie.springcloud.service;

import com.aboo.movie.springcloud.domain.SysUser;

import java.util.Optional;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-09-09 19:24
 **/
public interface SysUserRepository /*extends JpaRepository<SysUser, Long>*/ {
    Optional<SysUser> findByUsername(String username);
}

