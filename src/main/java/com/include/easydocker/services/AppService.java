package com.include.easydocker.services;

import com.include.easydocker.classes.*;
import com.include.easydocker.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.LinkedList;

@Component
public class AppService {

    private final UsersSession usersSession;
    private final ProjectRepository projectRepository;
    private final TemplateRepository templateRepository;
    private final ServiceRepository serviceRepository;
    private final NetworkRepository networkRepository;
    private final VolumesRepository volumesRepository;

    @Autowired
    public AppService(UsersSession usersSession,
                      ProjectRepository projectRepository,
                      TemplateRepository templateRepository,
                      ServiceRepository serviceRepository,
                      NetworkRepository networkRepository,
                      VolumesRepository volumesRepository) {
        this.usersSession = usersSession;
        this.projectRepository = projectRepository;
        this.templateRepository = templateRepository;
        this.serviceRepository =  serviceRepository;
        this.networkRepository = networkRepository;
        this.volumesRepository = volumesRepository;
    }

    private void showLoggedInfoOrTemporal(Model model) {
        if(usersSession.getUser().getName().equals(User.UNKNOWN))
            model.addAttribute("user-temporal", true);
        else
            model.addAttribute("user-logged", true);

        model.addAttribute("username", usersSession.getUser().getName());
    }

    public void userPage(Model model) {
        showLoggedInfoOrTemporal(model);
        if (temporalUser()){
            model.addAttribute("projects", usersSession.getUser().getProjects());
        } else {
            if (usersSession.getUser().getProjects() == null)
                usersSession.getUser().setProjects(new LinkedList<>());
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
        project.setTemplates(new LinkedList<>());

        if (!temporalUser())
            projectRepository.save(project);

        usersSession.getUser().getProjects().add(project);
    }

    public void createTemplate(long idProject, Template template){
        Project p;
        template.setNetworks(new LinkedList<>());
        template.setServices(new LinkedList<>());
        template.setVolumes(new LinkedList<>());
        if (temporalUser()){
            p = usersSession.findProjectById(idProject);
//            if (p.getTemplates() == null) //no deberia hacer falta este if
//                p.setTemplates(new LinkedList<>());
        } else {
            p = projectRepository.findById(idProject);
            templateRepository.save(template);
        }
        template.setProject(p);
        p.getTemplates().add(template);
    }

    public void createService(long idTemplate, Service service) {
        Template t;
        service.setTemplates(new LinkedList<>());
        if (temporalUser())
            t = usersSession.findTemplateById(idTemplate);
        else {
            t = templateRepository.findById(idTemplate);
            serviceRepository.save(service);
        }
        service.getTemplates().add(t);
        t.getServices().add(service);
    }

    public void createNetwork(long idTemplate, Network network) {
        Template t;
        if (temporalUser())
            t = usersSession.findTemplateById(idTemplate);
        else {
            t = templateRepository.findById(idTemplate);
            networkRepository.save(network);
        }
        network.setTemplate(t);
        t.getNetworks().add(network);
    }

    public void createVolume(long idTemplate, Volume volume) {
        Template t;
        if (temporalUser())
            t = usersSession.findTemplateById(idTemplate);
        else {
            t = templateRepository.findById(idTemplate);
            volumesRepository.save(volume);
        }
        volume.setTemplate(t);
        t.getVolumes().add(volume);
    }

    public void projectOverview(long idProject, Model model) {
        showLoggedInfoOrTemporal(model);

        Project p;
        if (usersSession.getUser().getName().equals(User.UNKNOWN)){
            p = usersSession.findProjectById(idProject);
        } else {
            p = projectRepository.findById(idProject);
        }
        model.addAttribute("templates", p.getTemplates());
        model.addAttribute("idProject", p.getId());
        model.addAttribute("project", true);
    }

    public void templateOverview(long idTemplate,  Model model){
        showLoggedInfoOrTemporal(model);
        Template t;
        if (usersSession.getUser().getName().equals(User.UNKNOWN)){
            t = usersSession.findTemplateById(idTemplate);
        } else {
            t = templateRepository.findById(idTemplate);
        }
        model.addAttribute("idTemplate", t.getId());
        model.addAttribute("template", true);
    }

    private boolean temporalUser(){
        return usersSession.getUser().getName().equals(User.UNKNOWN);
    }

}
