package com.include.easydocker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserOverviewController {

    @RequestMapping("/User/{userName}")
    public String userPage(Model model){

        return "user_overview";
    }

    @RequestMapping("/create_proyect")
    public String createProyect(Model model){

        return "";
    }

    @RequestMapping("/access_proyect")
    public String accessProyect(Model model){

        return "";
    }

    @RequestMapping("/delete_proyect")
    public String deleteProyect(Model model){

        return "";
    }
}
