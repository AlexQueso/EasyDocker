package com.include.easydocker.controller;

import com.include.easydocker.classes.*;
import com.include.easydocker.repositories.ProjectRepository;
import com.include.easydocker.repositories.TemplateRepository;
import com.include.easydocker.repositories.UserRepository;
import com.include.easydocker.services.AppService;
import com.include.easydocker.services.UsersSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;

@Controller
public class AppController {

    public final AppService appService;

    @Autowired
    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/user-overview")
    public String userPage(Model model) {
        showLoggedInfoOrTemporal(model);

        model.addAttribute("projects", appService.userOverview());
        model.addAttribute("user", true);

        return "app";
    }

    @PostMapping(value = "/new-project")
    public String createProject(Project project) {
        appService.createProject(project);
        return "redirect:/user-overview";
    }

    @PostMapping(value = "/new-template/{idProject}")
    public String createTemplate(@PathVariable long idProject, Template template) {
        appService.createTemplate(idProject, template);
        return "redirect:/project/" + idProject;
    }

    @GetMapping("/log-out")
    public String logOut(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/project/{id}")
    public String projectOverview(@PathVariable long id, Model model) {
        showLoggedInfoOrTemporal(model);

        Project p = appService.projectOverview(id);

        model.addAttribute("templates", p.getTemplates());
        model.addAttribute("idProject", p.getId());
        model.addAttribute("project", true);

        return "app";
    }

    @GetMapping("/template/{id}")
    public String templateOverview(@PathVariable long id, Model model) {
        showLoggedInfoOrTemporal(model);

        Template t = appService.templateOverview(id);

        model.addAttribute("idTemplate", t.getId());
        model.addAttribute("template", true);

        return "app";
    }

    private void showLoggedInfoOrTemporal(Model model) {
        if(appService.getUsersSession().isLogged())
            model.addAttribute("user-logged", true);
        else
            model.addAttribute("user-temporal", true);

        model.addAttribute("username", appService.getUsersSession()
                .getUser().getName());
    }

}
