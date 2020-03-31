package com.include.easydocker.session;

import com.include.easydocker.classes.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
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

    public Volume getVolume(long id){
        return temporalUserInformation.getVolumes().get(id);
    }

    public Service getService(long id) {
        return temporalUserInformation.getServices().get(id);
    }

    public Service getService(String name) {
        for(Service s: temporalUserInformation.getServices().values()){
            if (s.getName().equals(name))
                return s;
        }
        return null;
    }

    public Network getNetwork(String name) {
        for(Network n: temporalUserInformation.getNetwork().values()){
            if (n.getName().equals(name))
                return n;
        }
        return null;
    }

    public Volume getVolume(String name) {
        for(Volume v: temporalUserInformation.getVolumes().values()){
            if (v.getName().equals(name))
                return v;
        }
        return null;
    }

    public void deleteProject(long id) {
        this.getTemporalUserInformation().getProjects().remove(id);
    }

    public void deleteTemplate(long id) {
        this.getTemporalUserInformation().getTemplates().remove(id);
    }

    public void deleteVolume(long id){
        this.getTemporalUserInformation().getVolumes().remove(id);
    }

    public void deleteServices(long id){
        this.getTemporalUserInformation().getServices().remove(id);
    }

    public void deleteNetwork(long id){
        this.getTemporalUserInformation().getNetwork().remove(id);
    }
}














