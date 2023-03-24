package com.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/login-page")
    public String loginPage(){
        return "login-page";
    }

}
