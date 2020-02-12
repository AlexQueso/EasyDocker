package com.include.easydocker.services;

import com.include.easydocker.classes.Project;
import com.include.easydocker.classes.Template;
import com.include.easydocker.classes.User;
import com.include.easydocker.repositories.ProjectRepository;
import com.include.easydocker.repositories.TemplateRepository;
import com.include.easydocker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.LinkedList;

@Component
public class AppService {

    private final UsersSession usersSession;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TemplateRepository templateRepository;

    @Autowired
    public AppService(UsersSession usersSession,
                      UserRepository userRepository,
                      ProjectRepository projectRepository,
                      TemplateRepository templateRepository) {
        this.usersSession = usersSession;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.templateRepository = templateRepository;
    }

    public void showLoggedInfoOrTemporal(Model model) {
        if(usersSession.getUser().getName().equals(User.UNKNOWN))
            model.addAttribute("user-temporal", true);
        else
            model.addAttribute("user-logged", true);

        model.addAttribute("username", usersSession.getUser().getName());
    }

    public void userPage(Model model) {
        showLoggedInfoOrTemporal(model);
        if (usersSession.getUser().getName().equals(User.UNKNOWN)){ //usuario sin sesion
            model.addAttribute("projects", usersSession.getUser().getProjects());
        } else {
            model.addAttribute("projects", projectRepository.findByUser(usersSession.getUser()));
        }
        model.addAttribute("user", true);
    }

    public void temporalPage(Model model){
        User unknownUser = new User();
        unknownUser.setId(System.currentTimeMillis());
        unknownUser.setName(User.UNKNOWN);
        unknownUser.setProjects(new LinkedList<>());
        usersSession.setUser(unknownUser);
    }

    public void createProject(Project project){
        project.setUser(usersSession.getUser());

        if (!usersSession.getUser().getName().equals(User.UNKNOWN)) //usuario sin sesion
            projectRepository.save(project);

        usersSession.getUser().getProjects().add(project);
    }

    public void createTemplate(long idProject, Template template){
        Project p;
        if (usersSession.getUser().getName().equals(User.UNKNOWN)){ //usuario sin sesion
            p = usersSession.findProjectById(idProject);
            template.setProject(p);
            if (p.getTemplates() == null)
                p.setTemplates(new LinkedList<>());
        } else {
            p = projectRepository.findById(idProject);
            template.setProject(p);
            templateRepository.save(template);
        }
        p.getTemplates().add(template);
    }

    public void projectOverview(long idProject, Model model) {
        showLoggedInfoOrTemporal(model);

        Project p;
        if (usersSession.getUser().getName().equals(User.UNKNOWN)){ //usuario sin sesion
            p = usersSession.findProjectById(idProject);
        } else {
            p = projectRepository.findById(idProject);
        }
        model.addAttribute("templates", p.getTemplates());
        model.addAttribute("idProject", p.getId());
        model.addAttribute("project", true); //esto??
    }

    public void templateOverview(long idTemplate,  Model model){
        showLoggedInfoOrTemporal(model);
        Template t;
        if (usersSession.getUser().getName().equals(User.UNKNOWN)){ //usuario sin sesion
            t = usersSession.findTemplateById(idTemplate);
        } else {
            t = templateRepository.findById(idTemplate);
        }
        model.addAttribute("idTemplate", t.getId());
        model.addAttribute("template", true);
    }
}
