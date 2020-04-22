package com.include.easydocker.controller;

import com.include.easydocker.classes.Project;
import com.include.easydocker.classes.Template;
import com.include.easydocker.services.AppService;
import com.include.easydocker.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AppController {

    public final AppService appService;

    @Autowired
    public AppController(AppService appService) {
        this.appService = appService;
    }

    @PostMapping(value = "/new-project")
    public String createProject(Project project) {
        appService.createProject(project);
        return Utils.redirectTo("/user-overview");
    }

    @PostMapping(value = "/new-template/{idProject}")
    public String createTemplate(@PathVariable long idProject, Template template) {
        appService.createTemplate(idProject, template);
        return Utils.redirectTo("/project/" + idProject);
    }

    @GetMapping("/delete-project/{idProject}")
    public String deleteProject(@PathVariable long idProject) {
        appService.deleteProject(idProject);
        return Utils.redirectTo("/user-overview");
    }

    @GetMapping("/delete-template/{idTemplate}/{idProject}")
    public String deleteTemplate(@PathVariable long idTemplate, @PathVariable long idProject) {
        appService.deleteTemplate(idTemplate);
        return Utils.redirectTo("/project/" + idProject);
    }

    @CacheEvict(value="cache", allEntries = true)
    @GetMapping("/log-out")
    public String logOut(HttpSession session) {
        session.invalidate();
        return Utils.redirectTo("/");
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

        model.addAttribute("services", t.getServices());
        model.addAttribute("networks", t.getNetworks());
        model.addAttribute("volumes", t.getVolumes());
        model.addAttribute("compose", "");

        return "app";
    }

    @GetMapping("/user-overview")
    public String userOverview(Model model) {
        showLoggedInfoOrTemporal(model);

        model.addAttribute("projects", appService.userOverview());
        model.addAttribute("user", true);

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

    @PostMapping("/compose/{idTemplate}")
    public String generateCompose(@PathVariable long idTemplate, Model model) {
        showLoggedInfoOrTemporal(model);

        Template t = appService.templateOverview(idTemplate);
        String dockerCompose = appService.compose(idTemplate);

        model.addAttribute("idTemplate", t.getId());
        model.addAttribute("template", true);

        model.addAttribute("services", t.getServices());
        model.addAttribute("networks", t.getNetworks());
        model.addAttribute("volumes", t.getVolumes());
        model.addAttribute("compose", dockerCompose);

        return "app";
    }
}
