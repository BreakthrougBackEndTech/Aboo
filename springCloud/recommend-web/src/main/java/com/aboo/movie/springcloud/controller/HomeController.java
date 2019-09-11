package com.aboo.movie.springcloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-09-10 09:19
 **/
@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request) {

        model.addAttribute("content", "content");
        model.addAttribute("extraInfo", "extraInfo");

        return "home";
    }
}

