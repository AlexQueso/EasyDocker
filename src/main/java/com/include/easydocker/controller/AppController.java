package com.include.easydocker.controller;

import com.include.easydocker.classes.Project;
import com.include.easydocker.classes.Template;
import com.include.easydocker.classes.User;
import com.include.easydocker.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.LinkedList;
import java.util.List;

@Controller
public class AppController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/user-overview")
    public String userPage(Model model) {

        if(usersService.getUser().getName().equals(User.UNKNOWN))
            model.addAttribute("user-temporal", true);
        else
            model.addAttribute("user-logged", true);

        model.addAttribute("username", usersService.getUser().getName());
        model.addAttribute("user", true);
        model.addAttribute("projects", usersService.getUser().getProjects());

        return "app";
    }

    /* Todo : borras esto, es para debug */
    private void debug() {
        List<Project> projects = new LinkedList<>();
        List<Template> templates = new LinkedList<>();

        Project project = new Project("Project Example", usersService.getUser(), new LinkedList<>());

        templates.add(new Template("Template Example", project, null, null));

        projects.add(project);
        project.setTemplates(templates);
        usersService.getUser().setProjects(projects);
    }

    @GetMapping("/project/{id}")
    public String projectOverview(@PathVariable long id, Model model) {

        Project p = null;   // TODO: buscar projecto.

        model.addAttribute("templates", p.getTemplates());
        model.addAttribute("project", true);

        return "app";
    }

    @GetMapping("/template/{id}")
    public String templateOverview(@PathVariable long id, Model model) {

        Template t = null;  // TODO: buscar template.

        model.addAttribute("template", true);

        return "app";
    }

    @GetMapping("/user-temporal")
    public String temporalPage(Model model) {

        // Se crea el usuario temporal.
        User unknownUser = new User();
        unknownUser.setId(System.currentTimeMillis());

        usersService.setUser(unknownUser);

        return "redirect:/user-overview";

    }

}
