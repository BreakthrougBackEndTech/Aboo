 package com.aboo.movie.springcloud.controller;

import brave.sampler.Sampler;
import com.aboo.movie.springcloud.domain.MybatisUser;
import com.aboo.movie.springcloud.service.MybatisUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-09-10 11:12
 **/
@RestController
//@RequestMapping(produces = "application/json")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    MybatisUserService mybatisUserService;

    @ApiOperation(value = "根据username查找用户信息", notes = "暂时返回mock数据")
    @GetMapping(path = "/loadUserByUsername/{username}")
    public Optional<MybatisUser> loadUserByUsername(@PathVariable String username) {

        return mybatisUserService.findByUsername(username);
        /*SysUser sysUser = new SysUser();
        sysUser.setId(1L);
        sysUser.setUsername(username);
        sysUser.setPassword(username);

        SysRole role = new SysRole();

        SysPrivilege sysPrivilege = new SysPrivilege();

        sysPrivilege.setId(1L);
        sysPrivilege.setPermission("readAll");

        SysPrivilege sysPrivilege1 = new SysPrivilege();

        sysPrivilege1.setId(2L);
        sysPrivilege1.setPermission("writeAll");

        List<SysPrivilege> list = new ArrayList<>();
        list.add(sysPrivilege);
        list.add(sysPrivilege1);

        role.setId(1L);
        role.setName("ROLE_ADMIN");
        role.setPrivileges(list);

        List<SysRole> roles = new ArrayList<>();
        roles.add(role);
        sysUser.setRoles(roles);

        return Optional.of(sysUser);*/

    }
}

