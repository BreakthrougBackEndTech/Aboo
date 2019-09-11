package com.aboo.movie.springcloud.service;

import com.aboo.movie.springcloud.domain.SysPrivilege;
import com.aboo.movie.springcloud.domain.SysRole;
import com.aboo.movie.springcloud.domain.SysUser;
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

            SysUser sysUser = new SysUser();

            log.info("return default user");

            sysUser.setId(2L);
            sysUser.setUsername(username);
            sysUser.setPassword(username);

            SysRole role = new SysRole();

            SysPrivilege sysPrivilege = new SysPrivilege();

            sysPrivilege.setId(1L);
            sysPrivilege.setPermission("readAll");


            List<SysPrivilege> list = new ArrayList<>();
            list.add(sysPrivilege);

            role.setId(2L);
            role.setName("ROLE_USER");
            role.setPrivileges(list);

            List<SysRole> roles = new ArrayList<>();
            roles.add(role);
            sysUser.setRoles(roles);

            return sysUser;
        };

    }
}

