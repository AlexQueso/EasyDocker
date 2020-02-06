package com.include.easydocker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProyectOverviewController {

    @RequestMapping("/User/{userName}/{ProyectName}")
    public String ProyectPage(@PathVariable(value = "userName")String userName, Model model){

        return "template_overview";
    }

    @RequestMapping("/modify_template")
    public String modifyTemplate(Model model){

        return "";
    }

    @RequestMapping("/create_template")
    public String createTemplate(Model model){

        return "";
    }

    @RequestMapping("/delete_template")
    public String deleteTemplate(Model model){

        return "";
    }
}
