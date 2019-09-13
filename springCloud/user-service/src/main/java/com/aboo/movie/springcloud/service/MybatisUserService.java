package com.aboo.movie.springcloud.service;

import com.aboo.movie.springcloud.domain.MybatisUser;
import com.aboo.movie.springcloud.mapper.MybatisUserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-09-09 19:49
 **/
@Service
@Slf4j
public class MybatisUserService /*implements UserDetailsService*/ {
    @Autowired
    MybatisUserDao mybatisUserDao;

    public Optional<MybatisUser> findByUsername(String s) {
        Optional<MybatisUser> user = mybatisUserDao.findByUsername(s);

        if (user.isPresent()) {
            log.info("username:" + user.get().getUsername() + ";password:" + user.get().getPassword());
        } else {
            log.info("username:" + s + " not exist");
            //need cache?
        }
        return user;
    }
}

