package com.include.easydocker.services;

import com.include.easydocker.classes.*;
import com.include.easydocker.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.LinkedList;
import java.util.List;

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

    public void createProject(Project project){
        project.setUser(usersSession.getUser());
        project.setTemplates(new LinkedList<>());

        usersSession.getUser().getProjects().add(project);

        if (!temporalUser())
            projectRepository.save(project);
    }

    public void createTemplate(long idProject, Template template){
        Project p = usersSession.findProjectById(idProject);
        template.setNetworks(new LinkedList<>());
        template.setServices(new LinkedList<>());
        template.setVolumes(new LinkedList<>());

        template.setProject(p);
        p.getTemplates().add(template);

        if (!temporalUser())
            templateRepository.save(template);
    }

    public void createService(long idTemplate, Service service) {
        Template t = usersSession.findTemplateById(idTemplate);
        service.setTemplates(new LinkedList<>());

        service.getTemplates().add(t);
        t.getServices().add(service);

        if (!temporalUser())
            serviceRepository.save(service);
    }

    public void createNetwork(long idTemplate, Network network) {
        Template t = usersSession.findTemplateById(idTemplate);
        network.setTemplate(t);
        t.getNetworks().add(network);

        if (!temporalUser())
            networkRepository.save(network);
    }

    public void createVolume(long idTemplate, Volume volume) {
        Template t = usersSession.findTemplateById(idTemplate);
        volume.setTemplate(t);
        t.getVolumes().add(volume);

        if (!temporalUser())
            volumesRepository.save(volume);
    }

    public List<Project> userOverview() {
        if (usersSession.getUser().getProjects() == null)
            usersSession.getUser().setProjects(new LinkedList<>());

        if (temporalUser())
            return usersSession.getUser().getProjects();
        else
            return projectRepository.findByUser(usersSession.getUser());
    }

    public Project projectOverview(long idProject) {
        if (temporalUser())
            return usersSession.findProjectById(idProject);
        else
            return projectRepository.findById(idProject);
    }

    public Template templateOverview(long idTemplate){
        if (temporalUser())
            return usersSession.findTemplateById(idTemplate);
        else
            return templateRepository.findById(idTemplate);
    }

    private boolean temporalUser(){
        return usersSession.getUser().getName().equals(User.UNKNOWN);
    }

    public UsersSession getUsersSession() {
        return usersSession;
    }

    public void temporalPage() {
        User unknownUser = new User();
        unknownUser.setId(System.currentTimeMillis());
        unknownUser.setName(User.UNKNOWN);
        unknownUser.setProjects(new LinkedList<>());
        usersSession.setUser(unknownUser);
    }
}
