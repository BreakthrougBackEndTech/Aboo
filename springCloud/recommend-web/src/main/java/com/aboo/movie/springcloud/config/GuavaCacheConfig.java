package com.aboo.movie.springcloud.config;

import com.aboo.movie.springcloud.domain.MybatisUser;
import com.aboo.movie.springcloud.service.UserServiceClient;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-12-26 14:19
 **/
@Configuration
public class GuavaCacheConfig {

    @Autowired
    UserServiceClient userServiceClient;

    @Bean
    public LoadingCache<String, Integer> loadingCache() {
        LoadingCache<String, Integer> userNameToId = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build( new CacheLoader<String, Integer>() {
                            @Override
                            public Integer load(String key) throws Exception {
                                //TODO redis.get
                                MybatisUser mybatisUser = userServiceClient.loadUserByUsername(key);

                                if (mybatisUser == null || mybatisUser.getUsername() == null) {
                                    throw new UsernameNotFoundException("用户名不存在");
                                }

                                return mybatisUser.getId().intValue();
                            }
                        });
        return userNameToId;
    }
}

