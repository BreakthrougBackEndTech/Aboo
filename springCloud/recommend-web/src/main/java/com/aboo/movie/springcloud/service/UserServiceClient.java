package com.aboo.movie.springcloud.service;

import com.aboo.movie.springcloud.domain.MybatisUser;
import com.aboo.movie.springcloud.domain.SysUser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-09-10 10:36
 **/
@FeignClient(value = "user-service", fallbackFactory = UserServiceFailFactory.class)
public interface UserServiceClient {

    @GetMapping("/loadUserByUsername/{username}")
    MybatisUser loadUserByUsername(@PathVariable("username") String username);

}

