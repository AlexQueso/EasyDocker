package com.include.easydocker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserOverviewController {

    @RequestMapping("/User/{userName}")
    public String userPage(@PathVariable(value = "id")long userId, Model model){

        return "app";
    }

    @RequestMapping("/create_project")
    public String createProject(Model model){

        return "app";
    }

    @RequestMapping("/access_project/{id}")
    public String accessProject(@PathVariable(value = "id")long proyectId, Model model){

        return "app";
    }

    @RequestMapping("/delete_project/{}")
    public String deleteProject(@PathVariable(value = "id")long proyectId, Model model){

        return "app";
    }
}
