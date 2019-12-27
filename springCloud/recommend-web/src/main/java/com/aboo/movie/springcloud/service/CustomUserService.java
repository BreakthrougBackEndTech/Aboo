package com.aboo.movie.springcloud.service;

import com.aboo.movie.springcloud.MovieUser;
import com.aboo.movie.springcloud.domain.MybatisUser;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    LoadingCache<String, Integer> loadingCache;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info(username);
        MybatisUser mybatisUser = userServiceClient.loadUserByUsername(username);

        if (mybatisUser == null || mybatisUser.getUsername() == null){
            throw new UsernameNotFoundException("用户名不存在");
        }

        loadingCache.put(username, mybatisUser.getId().intValue());

        MovieUser movieUser = new MovieUser(mybatisUser);

        return movieUser;
    }

    //加入本地缓存， 真实环境可以加上redis
    public int getUserId(String userName) throws ExecutionException {
        return Integer.valueOf(loadingCache.get(userName).toString());
    }

}

