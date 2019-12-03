package com.aboo.movie.springcloud.controller;

import com.aboo.movie.springcloud.service.CustomUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: login
 * @author: zhegong
 * @create: 2019-09-10 09:19
 **/
@Controller
@Slf4j
public class HomeController {

    @Autowired
    CustomUserService customUserService;


    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request) {
        return "login";
    }

    @RequestMapping("/home")
    public String home(Model model, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().toString().contains("writeAll")) {
            model.addAttribute("content", "content");
            model.addAttribute("extraInfo", "extraInfo");
            return "home";
        } else {
            return "movie/movieList";
        }
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

