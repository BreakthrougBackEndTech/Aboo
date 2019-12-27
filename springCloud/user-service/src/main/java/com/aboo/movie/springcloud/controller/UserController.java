 package com.aboo.movie.springcloud.controller;

import brave.sampler.Sampler;
import com.aboo.movie.springcloud.domain.MybatisRole;
import com.aboo.movie.springcloud.domain.MybatisUser;
import com.aboo.movie.springcloud.service.MybatisUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

    @Autowired
    MybatisUserService mybatisUserService;

    @ApiOperation(value = "根据username查找用户信息", notes = "暂时返回mock数据")
    @GetMapping(path = "/loadUserByUsername/{username}")
    public Optional<MybatisUser> loadUserByUsername(@PathVariable String username) {

        return mybatisUserService.findByUsername(username);
    }

    @ApiOperation(value = "插入新用户", notes = "暂无")
    @GetMapping(path = "/insertUser")
    public void insertUser(MybatisUser user){
        mybatisUserService.insertUser(user);
    }


    @ApiOperation(value = "内部使用，插入测试用户", notes = "仅初始化使用")
    @GetMapping(path = "/initTestUsers")
    public void initTestUsers(@RequestParam("start") int start, @RequestParam("end") int end){

        for(int i= start; i <= end; i++){
            MybatisUser user = new MybatisUser();
            user.setUsername(String.valueOf(i));
            user.setPassword(String.valueOf(i));

            List<MybatisRole> roleList = new ArrayList<>();
            MybatisRole role = new MybatisRole();
            role.setName("readAll");
            roleList.add(role);

            user.setRoles(roleList);

            mybatisUserService.insertUser(user);
        }
    }
}

