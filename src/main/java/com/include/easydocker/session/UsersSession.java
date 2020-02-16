package com.include.easydocker.session;

import com.include.easydocker.classes.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;

@Component
@SessionScope
public class UsersSession {

    private boolean logged = false;

    private User user;

    private TemporalUserInformation temporalUserInformation;

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TemporalUserInformation getTemporalUserInformation() {
        return temporalUserInformation;
    }

    public void initTemporalUserInformation() {
        this.temporalUserInformation = new TemporalUserInformation();
    }

    public void addProject(Project project) {
        temporalUserInformation.getProjects().put(project.getId(), project);
    }

    public void addNetwork(Network network) {
        temporalUserInformation.getNetwork().put(network.getId(), network);
    }

    public void addTemplate(Template template) {
        temporalUserInformation.getTemplates().put(template.getId(), template);
    }

    public void addVolume(Volume volume) {
        temporalUserInformation.getVolumes().put(volume.getId(), volume);
    }

    public void addService(Service service) {
        temporalUserInformation.getServices().put(service.getId(), service);
    }

    public Project getProject(long id){
        Project project = temporalUserInformation.getProjects().get(id);

        project.setTemplates(new ArrayList<>(temporalUserInformation.getTemplates().values()));

        return project;
    }

    public Template getTemplate(long id) {
        Template template = temporalUserInformation.getTemplates().get(id);

        template.setNetworks(new ArrayList<>(temporalUserInformation.getNetwork().values()));
        template.setServices(new ArrayList<>(temporalUserInformation.getServices().values()));
        template.setVolumes(new ArrayList<>(temporalUserInformation.getVolumes().values()));

        return template;
    }

    public Network getNetwork(long id) {
        return temporalUserInformation.getNetwork().get(id);
    }
}
