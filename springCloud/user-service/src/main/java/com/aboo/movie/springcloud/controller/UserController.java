package com.aboo.movie.springcloud.controller;

import com.aboo.movie.springcloud.domain.SysPrivilege;
import com.aboo.movie.springcloud.domain.SysRole;
import com.aboo.movie.springcloud.domain.SysUser;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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

    @GetMapping(path = "/loadUserByUsername/{username}")
    public Optional<SysUser> loadUserByUsername(@PathVariable String username) {

        SysUser sysUser = new SysUser();
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

        return Optional.of(sysUser);

    }
}

