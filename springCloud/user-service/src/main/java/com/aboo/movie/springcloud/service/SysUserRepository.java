package com.aboo.movie.springcloud.service;

import com.aboo.movie.springcloud.domain.SysUser;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-09-09 19:24
 **/
//public interface SysUserRepository extends JpaRepository<SysUser, Long> {
public interface SysUserRepository {
    SysUser findByUsername(String username);
}

