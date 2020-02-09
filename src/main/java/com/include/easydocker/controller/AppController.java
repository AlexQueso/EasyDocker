package com.include.easydocker.controller;

import com.include.easydocker.classes.Project;
import com.include.easydocker.classes.Template;
import com.include.easydocker.classes.User;
import com.include.easydocker.repositories.ProjectRepository;
import com.include.easydocker.repositories.TemplateRepository;
import com.include.easydocker.repositories.UserRepository;
import com.include.easydocker.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;

@Controller
public class AppController {

    private final UsersService usersService;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TemplateRepository templateRepository;

    @Autowired
    public AppController(UsersService usersService,
                          UserRepository userRepository,
                          ProjectRepository projectRepository,
                          TemplateRepository templateRepository) {

        this.usersService = usersService;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.templateRepository = templateRepository;
    }


    @GetMapping("/user-overview")
    public String userPage(Model model) {

        if(usersService.getUser().getName().equals(User.UNKNOWN))
            model.addAttribute("user-temporal", true);
        else
            model.addAttribute("user-logged", true);

        model.addAttribute("username", usersService.getUser().getName());
        model.addAttribute("user", true);
        model.addAttribute("projects", projectRepository.findByUser(usersService.getUser()));

        return "app";
    }

    @PostMapping(value = "/new-project")
    public String createProject(@RequestParam String project_name) {

        Project project = new Project(project_name);
        project.setUser(usersService.getUser());
        projectRepository.save(project);

        return "redirect:/user-overview";
    }

    @PostMapping(value = "/new-template/{idProject}")
    public String createTemplate(@PathVariable long idProject, @RequestParam String template_name) {

        Template template = new Template(template_name);
        template.setProject(projectRepository.findById(idProject));
        templateRepository.save(template);

        return "redirect:/user-overview";
    }

    @GetMapping("/project/{id}")
    public String projectOverview(@PathVariable long id, Model model) {

        Project p = projectRepository.findById(id);

        model.addAttribute("templates", p.getTemplates());
        model.addAttribute("project", true);

        return "app";
    }

    @GetMapping("/template/{id}")
    public String templateOverview(@PathVariable long id, Model model) {

        Template t = templateRepository.findById(id);

        model.addAttribute("template", true);

        return "app";
    }

    @GetMapping("/user-temporal")
    public String temporalPage(Model model) {

        User unknownUser = new User();
        unknownUser.setId(System.currentTimeMillis());
        unknownUser.setName(User.UNKNOWN);
        unknownUser.setProjects(new LinkedList<>());

        usersService.setUser(unknownUser);

        return "redirect:/user-overview";
    }

}
