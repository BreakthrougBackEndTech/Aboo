package com.aboo.movie.springcloud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-09-09 19:49
 **/
//@Service
@Slf4j
public class CustomUserService /*implements UserDetailsService*/ {
//    @Autowired
    SysUserRepository userRepository;

/*    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser user = userRepository.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        log.info("s:" + s);
        log.info("username:" + user.getUsername() + ";password:" + user.getPassword());
        return user;
    }*/
}

