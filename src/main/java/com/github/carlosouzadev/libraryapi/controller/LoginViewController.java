package com.github.carlosouzadev.libraryapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class LoginViewController{

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
