package com.include.easydocker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TemplateOverviewController {

    @RequestMapping("/Template/{TemplateId}")
    public String templatePage(Model model){

        return "";
    }

    @RequestMapping("/add_docker")
    public String addDocker(Model model){

        return "";
    }

    @RequestMapping("/User/{userName}/{ProyectName}/{TemplateId}/edit_docker")
    public String editDocker(Model model){

        return "";
    }

    @RequestMapping("/User/{userName}/{ProyectName}/{TemplateId}/delete_Dcoker")
    public String deleteDocker(Model model){

        return "";
    }
}
