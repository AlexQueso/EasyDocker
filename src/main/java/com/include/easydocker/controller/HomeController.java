package com.include.easydocker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(Model model){

        return "home";
    }

    @RequestMapping("/sign_in")
    public String signIn(Model model){

        return "home";
    }

    @RequestMapping("/sign_up")
    public String signUp(Model model){

        return "home";
    }
}
