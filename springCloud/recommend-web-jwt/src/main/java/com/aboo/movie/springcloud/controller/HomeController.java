package com.aboo.movie.springcloud.controller;

import com.aboo.movie.springcloud.security.JwtTokenProvider;
import com.aboo.movie.springcloud.service.CustomUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: jwt login
 * @author: zhegong
 * @create: 2019-09-10 09:19
 **/
@Controller
@Slf4j
public class HomeController {

    @Autowired
    CustomUserService customUserService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;


    @GetMapping("/jwt/login")
    public String getlogin(Model model, HttpServletRequest request) {


        System.out.println(request);
        System.out.println("test");


        return "jwt/login";
    }

    @PostMapping("/jwt/login")
    public String login(Model model, @RequestParam String username, @RequestParam String password) {

        try {
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtTokenProvider.createToken(username, authentication.getAuthorities());

            model.addAttribute("token", token);

            if (authentication.getAuthorities().toString().contains("writeAll")) {
                model.addAttribute("content", token);
                model.addAttribute("extraInfo", "extraInfo");
                return "home";//如果是后台管理人员登录
            } else {
                return "movie/movieList";//如果普通用户*/
            }

        } catch (AuthenticationException e) {

            return "jwt/login";
        }


    }

    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request) {

        return "jwt/login";

     /*   Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().toString().contains("writeAll")) {
            model.addAttribute("content", "content");
            model.addAttribute("extraInfo", "extraInfo");
            return "home";//如果是后台管理人员登录
        }
        else
            return "movie/movieList";//如果普通用户*/
    }


    @RequestMapping("/readSession")
    @PreAuthorize("hasAuthority('readAll')")
    @ResponseBody
    public Map<String, String> getSession(HttpServletRequest request) {
        Map<String, String> attributeMap = new HashMap<>();


        attributeMap.put("message", request.getRequestURI());
        attributeMap.put("sessionID", request.getSession().getId());

        System.out.println("sessionID:" + request.getSession().getId());


        return attributeMap;

    }

}

