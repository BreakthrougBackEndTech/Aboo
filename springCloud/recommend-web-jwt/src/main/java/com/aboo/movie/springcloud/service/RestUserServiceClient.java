package com.aboo.movie.springcloud.service;

import com.aboo.movie.springcloud.domain.SysUser;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//@Service
public class RestUserServiceClient {

    private RestTemplate rest;

//    public RestUserServiceClient(@LoadBalanced RestTemplate rest) {
//        this.rest = rest;
//    }

    //  @HystrixCommand(fallbackMethod="getDefaultIngredientDetails")
    public SysUser loadUserByUsername(String username) {
        return rest.getForObject(
                "http://user-service/loadUserByUsername/{username}",
                SysUser.class, username);
    }


}
