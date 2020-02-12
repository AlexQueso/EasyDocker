package com.include.easydocker.controller;

import com.include.easydocker.classes.Project;
import com.include.easydocker.classes.Template;
import com.include.easydocker.classes.User;
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

    @Autowired
    public AppService appService;


    @GetMapping("/user-overview")
    public String userPage(Model model) {
        /*
        appService.showLoggedInfoOrTemporal(model);

        model.addAttribute("user", true);
        model.addAttribute("projects", usersSession.getUser().getProjects());
        */
        appService.userPage(model);
        return "app";

    }

    @PostMapping(value = "/new-project")
    public String createProject(Project project) {
        /*
        project.setUser(usersSession.getUser());
        if (!usersSession.getUser().getName().equals(User.UNKNOWN)){
            projectRepository.save(project);
        } else {
            usersSession.getUser().getProjects().add(project);
        }*/
        appService.createProject(project);
        return "redirect:/user-overview";
    }

    @PostMapping(value = "/new-template/{idProject}")
    public String createTemplate(@PathVariable long idProject, Template template) {
        /*
        template.setProject(projectRepository.findById(idProject));
        templateRepository.save(template);
        */
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
        /*
        showLoggedInfoOrTemporal(model);

        Project p = projectRepository.findById(id);

        model.addAttribute("templates", p.getTemplates());
        model.addAttribute("idProject", p.getId());

        model.addAttribute("project", true);
         */
        appService.projectOverview(id, model);
        return "app";
    }

    @GetMapping("/template/{id}")
    public String templateOverview(@PathVariable long id, Model model) {
        /*
        showLoggedInfoOrTemporal(model);

        Template t = templateRepository.findById(id);

        model.addAttribute("idTemplate", t.getId());
        model.addAttribute("template", true);
        */
        appService.templateOverview(id, model);
        return "app";
    }

    @GetMapping("/user-temporal")
    public String temporalPage(Model model, HttpSession session) {
        /*
        User unknownUser = new User();
        unknownUser.setId(System.currentTimeMillis());
        unknownUser.setName(User.UNKNOWN);
        unknownUser.setProjects(new LinkedList<>());

        usersSession.setUser(unknownUser);
        session.setAttribute("user", unknownUser);
        */
        appService.temporalPage(model);
        return "redirect:/user-overview";
    }

}
