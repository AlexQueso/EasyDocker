package com.include.easydocker.controller;

import com.include.easydocker.classes.Project;
import com.include.easydocker.classes.Template;
import com.include.easydocker.classes.User;
import com.include.easydocker.repositories.ProjectRepository;
import com.include.easydocker.repositories.TemplateRepository;
import com.include.easydocker.repositories.UserRepository;
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

    private final UsersSession usersSession;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TemplateRepository templateRepository;

    @Autowired
    public AppController(UsersSession usersSession,
                         UserRepository userRepository,
                         ProjectRepository projectRepository,
                         TemplateRepository templateRepository) {

        this.usersSession = usersSession;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.templateRepository = templateRepository;
    }


    @GetMapping("/user-overview")
    public String userPage(Model model) {

        showLoggedInfoOrTemporal(model);

        model.addAttribute("user", true);
        model.addAttribute("projects", projectRepository.findByUser(usersSession.getUser()));

        return "app";
    }

    public void showLoggedInfoOrTemporal(Model model) {
        if(usersSession.getUser().getName().equals(User.UNKNOWN))
            model.addAttribute("user-temporal", true);
        else
            model.addAttribute("user-logged", true);

        model.addAttribute("username", usersSession.getUser().getName());
    }

    @PostMapping(value = "/new-project")
    public String createProject(Project project) {

        project.setUser(usersSession.getUser());
        projectRepository.save(project);

        return "redirect:/user-overview";
    }

    @PostMapping(value = "/new-template/{idProject}")
    public String createTemplate(@PathVariable long idProject, Template template) {

        template.setProject(projectRepository.findById(idProject));
        templateRepository.save(template);

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

        Project p = projectRepository.findById(id);

        model.addAttribute("templates", p.getTemplates());
        model.addAttribute("idProject", p.getId());

        model.addAttribute("project", true);

        return "app";
    }

    @GetMapping("/template/{id}")
    public String templateOverview(@PathVariable long id, Model model) {

        showLoggedInfoOrTemporal(model);

        Template t = templateRepository.findById(id);

        model.addAttribute("idTemplate", t.getId());
        model.addAttribute("template", true);

        return "app";
    }

    @GetMapping("/user-temporal")
    public String temporalPage(Model model, HttpSession session) {

        /* TODO: arreglar flujo del usuario temporal */

        User unknownUser = new User();
        unknownUser.setId(System.currentTimeMillis());
        unknownUser.setName(User.UNKNOWN);
        unknownUser.setProjects(new LinkedList<>());

        usersSession.setUser(unknownUser);
        session.setAttribute("user", unknownUser);

        return "redirect:/user-overview";
    }

}
