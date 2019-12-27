package com.aboo.movie.springcloud.service;

import com.aboo.movie.springcloud.domain.*;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-09-11 09:38
 **/
@Slf4j
@Component
public class UserServiceFailFactory implements FallbackFactory<UserServiceClient> {
    @Override
    public UserServiceClient create(Throwable throwable) {
        log.warn("fallback; reason was: {}", throwable.getMessage());

        return (username) -> {

            MybatisUser mybatisUser = new MybatisUser();

            log.info("return default user");

            mybatisUser.setId(0L);
            mybatisUser.setUsername(username);
            mybatisUser.setPassword(username);

            MybatisRole mybatisRole = new MybatisRole();

            mybatisRole.setName("read1");

            List<MybatisRole> list = new ArrayList<>();
            list.add(mybatisRole);
            mybatisUser.setRoles(list);


            return mybatisUser;
        };

    }
}

