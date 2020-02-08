package com.include.easydocker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TemplateOverviewController {

    @RequestMapping("/Template/{id}")
    public String templatePage(@PathVariable(value = "id")long templateId, Model model){

        return "app";
    }

    @RequestMapping("/add_docker")
    public String addDocker(Model model){

        return "app";
    }

    @RequestMapping("/edit_docker/{id}")
    public String editDocker(@PathVariable(value = "id")long dockerId, Model model){

        return "app";
    }

    @RequestMapping("/delete_docker/{id}")
    public String deleteDocker(@PathVariable(value = "id")long dockerId, Model model){

        return "app";
    }
}
