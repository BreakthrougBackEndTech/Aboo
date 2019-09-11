package com.aboo.movie.springcloud.service;

import com.aboo.movie.springcloud.MovieUser;
import com.aboo.movie.springcloud.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-09-10 10:53
 **/
@Service
@Slf4j
public class CustomUserService implements UserDetailsService {

    @Autowired
    UserServiceClient userServiceClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info(username);
        SysUser sysUser = userServiceClient.loadUserByUsername(username);

        log.info(sysUser.toString());

        if (null == sysUser || sysUser.getUsername() == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        MovieUser movieUser = new MovieUser(sysUser);

        return movieUser;
    }
}

