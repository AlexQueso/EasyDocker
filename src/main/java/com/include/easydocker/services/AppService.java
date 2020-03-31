package com.include.easydocker.services;

import com.include.easydocker.classes.*;
import com.include.easydocker.managers.RepositoriesManager;
import com.include.easydocker.session.UsersSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class AppService {

    private final RepositoriesManager repositoryManager;
    private final UsersSession usersSession;
    private final EditorService editorService;

    @Autowired
    public AppService(RepositoriesManager repositoryManager, UsersSession usersSession, EditorService editorService) {
        this.repositoryManager = repositoryManager;
        this.usersSession = usersSession;
        this.editorService = editorService;
    }

    public void createProject(Project project){
        project.setUser(usersSession.getUser());
        project.setId(System.currentTimeMillis());
        if (usersSession.isLogged())
            repositoryManager.getProjectRepository().save(project);
        else
            usersSession.addProject(project);
    }

    public void createTemplate(long idProject, Template template){
        template.setId(System.currentTimeMillis());
        if (usersSession.isLogged()) {
            template.setProject(repositoryManager
                    .getProjectRepository().findById(idProject));
            repositoryManager.getTemplateRepository().save(template);
        }
        else {
            template.setProject(usersSession.getProject(idProject));
            usersSession.addTemplate(template);
        }
    }

    public List<Project> userOverview() {
        if (usersSession.isLogged())
            return repositoryManager
                    .getProjectRepository()
                    .findByUser(usersSession.getUser());
        else
            return new LinkedList<>(usersSession
                    .getTemporalUserInformation()
                    .getProjects().values());
    }

    public Project projectOverview(long idProject) {
        if (usersSession.isLogged())
            return repositoryManager.getProjectRepository().findById(idProject);
        else
            return usersSession.getProject(idProject);
    }

    public Template templateOverview(long idTemplate) {
        if (usersSession.isLogged())
            return repositoryManager.getTemplateRepository().findById(idTemplate);
        else
            return usersSession.getTemplate(idTemplate);
    }

    public UsersSession getUsersSession() {
        return usersSession;
    }

    public void deleteProject(long idProject) {
        if(usersSession.isLogged()) {
            Project p = repositoryManager.getProjectRepository().findById(idProject);
            for (Template t : repositoryManager.getTemplateRepository().findByProject(p))
                deleteTemplate(t.getId());
            repositoryManager.getProjectRepository().deleteById(idProject);
        }
        else
            usersSession.deleteProject(idProject);
    }

    public void deleteTemplate(long idTemplate) {
        if(usersSession.isLogged()) {
            Template t = repositoryManager.getTemplateRepository().findById(idTemplate);
            for (Service s : repositoryManager.getServiceRepository().findByTemplate(t))
                editorService.deleteService(s.getId());

            for (Network n : repositoryManager.getNetworkRepository().findByTemplate(t))
                editorService.deleteNetwork(n.getId());

            for (Volume v : repositoryManager.getVolumesRepository().findByTemplate(t))
                editorService.deleteVolume(v.getId());

            repositoryManager.getTemplateRepository().deleteById(idTemplate);
        }
        else
            usersSession.deleteTemplate(idTemplate);
    }

    public List<Service> getServices(long idTemplate){
        Template t = templateOverview(idTemplate);
        if (usersSession.isLogged()) {
            return repositoryManager.getServiceRepository().findByTemplate(t);
        } else {
            return t.getServices();
        }
    }

    public List<Network> getNetworks(long idTemplate){
        Template t = templateOverview(idTemplate);
        if (usersSession.isLogged()) {
            return repositoryManager.getNetworkRepository().findByTemplate(t);
        } else {
            return t.getNetworks();
        }
    }

    public List<Volume> getVolumes(long idTemplate){
        Template t = templateOverview(idTemplate);
        if (usersSession.isLogged()) {
            return repositoryManager.getVolumesRepository().findByTemplate(t);
        } else {
            return t.getVolumes();
        }
    }

    public String compose(long idTemplate) {
        List<Service> services = getServices(idTemplate);
        List<Network> networks = getNetworks(idTemplate);
        List<Volume> volumes = getVolumes(idTemplate);

        StringBuilder sb = new StringBuilder();
        sb.append("version: '3'\n");
        sb.append("services:\n");
        for (Service service : services)
            sb.append(service.toCompose());
        sb.append("volumes:\n");
        for (Volume volume : volumes)
            sb.append(volume.toCompose());
        sb.append("networks:\n");
        for (Network network : networks)
            sb.append(network.toCompose());

        return sb.toString();
    }
}
