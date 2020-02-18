package com.include.easydocker.services;

import com.include.easydocker.classes.Network;
import com.include.easydocker.classes.Service;
import com.include.easydocker.classes.Volume;
import com.include.easydocker.managers.RepositoriesManager;
import com.include.easydocker.session.UsersSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
public class EditorService {

    private final RepositoriesManager repositoryManager;
    private final UsersSession usersSession;

    @Autowired
    public EditorService(RepositoriesManager repositoryManager, UsersSession usersSession) {
        this.repositoryManager = repositoryManager;
        this.usersSession = usersSession;
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
        if (getNetwork(network.getName())== null) {
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
        if(usersSession.isLogged())
            repositoryManager.getServiceRepository().deleteById(id);
        else
            usersSession.deleteServices(id);
    }
}
