package com.include.easydocker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProjectOverviewController {

    @RequestMapping("/project/{id}")
    public String ProjectPage(@PathVariable(value = "id")long projectId, Model model){

        return "project_overview";
    }

    @RequestMapping("/modify_template/{id}")
    public String modifyTemplate(@PathVariable(value = "id")long templateId, Model model){

        return "project_overview";
    }

    @RequestMapping("/create_template")
    public String createTemplate(Model model){

        return "project_overview";
    }

    @RequestMapping("/delete_template/{id}")
    public String deleteTemplate(@PathVariable(value = "id")long templateId, Model model){

        return "project_overview";
    }
}
