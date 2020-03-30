package com.include.easydocker.services;

import com.include.easydocker.classes.Network;
import com.include.easydocker.classes.Service;
import com.include.easydocker.classes.Volume;
import com.include.easydocker.managers.RepositoriesManager;
import com.include.easydocker.rabbit.DockerRequest;
import com.include.easydocker.rabbit.MessageHandlerImplementations;
import com.include.easydocker.rabbit.Producer;
import com.include.easydocker.session.UsersSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Component
public class EditorService {

    private final RepositoriesManager repositoryManager;
    private final UsersSession usersSession;
    private final Producer producer;

    @Autowired
    public EditorService(RepositoriesManager repositoryManager, UsersSession usersSession, Producer producer) {
        this.repositoryManager = repositoryManager;
        this.usersSession = usersSession;
        this.producer = producer;
    }

    public void createService(long idTemplate, Service service) {
        if (getService(service.getName()) == null) {
            if (usersSession.isLogged()) {
                service.setTemplate(repositoryManager.getTemplateRepository().findById(idTemplate));
                repositoryManager.getServiceRepository().save(service);
            } else {
                service.setId(System.currentTimeMillis());
                service.setTemplate(usersSession.getTemplate(idTemplate));
                usersSession.addService(service);
            }
        }
    }

    public void createNetwork(long idTemplate, Network network) {
        if (getNetwork(network.getName()) == null) {
            if (usersSession.isLogged()) {
                network.setTemplate(repositoryManager.getTemplateRepository().findById(idTemplate));
                repositoryManager.getNetworkRepository().save(network);
            } else {
                network.setServices(new LinkedList<>());
                network.setId(System.currentTimeMillis());
                network.setTemplate(usersSession.getTemplate(idTemplate));
                usersSession.addNetwork(network);
            }
        }
    }

    public void createVolume(long idTemplate, Volume volume) {
        if (getVolume(volume.getName()) == null) {
            if (usersSession.isLogged()) {
                volume.setTemplate(repositoryManager
                        .getTemplateRepository().findById(idTemplate));
                repositoryManager.getVolumesRepository().save(volume);
            } else {
                volume.setServices(new LinkedList<>());
                volume.setId(System.currentTimeMillis());
                volume.setTemplate(usersSession.getTemplate(idTemplate));
                usersSession.addVolume(volume);
            }
        }
    }

    public UsersSession getUsersSession() {
        return usersSession;
    }

    public Network networkProperties(long idNetwork) {
        if (usersSession.isLogged())
            return repositoryManager.getNetworkRepository().findById(idNetwork);
        else
            return usersSession.getNetwork(idNetwork);
    }

    public Volume volumeProperties(long idVolume) {
        if (usersSession.isLogged())
            return repositoryManager.getVolumesRepository().findById(idVolume);
        else
            return usersSession.getVolume(idVolume);
    }

    public Service serviceProperties(long id) {
        if (usersSession.isLogged())
            return repositoryManager.getServiceRepository().findById(id);
        else
            return usersSession.getService(id);
    }

    public Service getService(String name){
        if (usersSession.isLogged())
            return repositoryManager.getServiceRepository().findByName(name);
        else
            return usersSession.getService(name);
    }

    public Network getNetwork(String name){
        if (usersSession.isLogged())
            return repositoryManager.getNetworkRepository().findByName(name);
        else
            return usersSession.getNetwork(name);
    }

    public Volume getVolume(String name){
        if (usersSession.isLogged())
            return repositoryManager.getVolumesRepository().findByName(name);
        else
            return usersSession.getVolume(name);
    }

    public void addServiceToNetwork(Service s, long id) {
        Network n = networkProperties(id);
        n.getServices().add(s);
        if (usersSession.isLogged())
            repositoryManager.getNetworkRepository().save(n);
    }

    public void addServiceToVolume(Service s, long id) {
        Volume v = volumeProperties(id);
        v.getServices().add(s);
        if (usersSession.isLogged())
            repositoryManager.getVolumesRepository().save(v);
    }

    public void deleteNetwork(long id) {
        if(usersSession.isLogged())
            repositoryManager.getNetworkRepository().deleteById(id);
        else
            usersSession.deleteNetwork(id);
    }

    public void deleteVolume(long id) {
        if(usersSession.isLogged())
            repositoryManager.getVolumesRepository().deleteById(id);
        else
            usersSession.deleteVolume(id);
    }

    public void deleteService(long id) {
        if(usersSession.isLogged()) {
            Service s = repositoryManager.getServiceRepository().findById(id);
            for (Network n: repositoryManager.getNetworkRepository().findByTemplate(s.getTemplate())){
                n.getServices().remove(s);
            }
            for (Volume v: repositoryManager.getVolumesRepository().findByTemplate(s.getTemplate())){
                v.getServices().remove(s);
            }
            repositoryManager.getServiceRepository().deleteById(id);
        } else {
            Service s = usersSession.getService(id);
            for (Network n: usersSession.getTemplate(s.getTemplate().getId()).getNetworks()){
                n.getServices().remove(s);
            }
            for (Volume v: usersSession.getTemplate(s.getTemplate().getId()).getVolumes()){
                v.getServices().remove(s);
            }
            usersSession.deleteServices(id);
        }
    }

    public void savePropertiesNetwork(long id, String properties) {
        if (usersSession.isLogged()){
            Network n = repositoryManager.getNetworkRepository().findById(id);
            n.setProperties(properties);
            repositoryManager.getNetworkRepository().save(n);
        }
        else
            usersSession.getNetwork(id).setProperties(properties);
    }

    public void savePropertiesVolume(long id, String properties) {
        if (usersSession.isLogged()){
            Volume v = repositoryManager.getVolumesRepository().findById(id);
            v.setProperties(properties);
            repositoryManager.getVolumesRepository().save(v);
        }
        else
            usersSession.getVolume(id).setProperties(properties);
    }

    public void savePropertiesService(long id, String properties) {
        if (usersSession.isLogged()){
            Service s = repositoryManager.getServiceRepository().findById(id);
            s.setProperties(properties);
            repositoryManager.getServiceRepository().save(s);
        }
        else
            usersSession.getService(id).setProperties(properties);
    }

    public void buildPushList (String function, String tag, String dockerfile, String repository){
        Map<String, String> body = new HashMap<>();
        if (tag != null)
            body.put("tag", tag);
        if (dockerfile != null)
            body.put("dockerfile", dockerfile);
        if (repository != null)
            body.put("repository", repository);
        DockerRequest request = new DockerRequest(function, body);
        System.out.println(request.getBody().toString());

        switch(function){
            case "build":
                producer.sendRealTimeResponse(request, MessageHandlerImplementations.factory(MessageHandlerImplementations.AFTER_BUILD));
                break;
            case "push":
                producer.sendRealTimeResponse(request, MessageHandlerImplementations.factory(MessageHandlerImplementations.AFTER_PUSH));
                break;
            case "list":
                producer.sendRealTimeResponse(request, MessageHandlerImplementations.factory(MessageHandlerImplementations.AFTER_LIST));
                break;
        }
    }
}













