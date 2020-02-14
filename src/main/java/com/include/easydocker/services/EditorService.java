package com.include.easydocker.services;

import com.include.easydocker.classes.Network;
import com.include.easydocker.classes.Service;
import com.include.easydocker.classes.Volume;
import com.include.easydocker.managers.RepositoriesManager;
import com.include.easydocker.session.UsersSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

        if (usersSession.isLogged()) {
            service.setTemplate(repositoryManager.getTemplateRepository().findById(idTemplate));
            repositoryManager.getServiceRepository().save(service);
        }
        else {
            service.setTemplate(usersSession.getTemplate(idTemplate));
            usersSession.addService(service);
        }

    }

    public void createNetwork(long idTemplate, Network network) {

        if (usersSession.isLogged()){
            network.setTemplate(repositoryManager.getTemplateRepository().findById(idTemplate));
            repositoryManager.getNetworkRepository().save(network);
        }
        else {
            network.setTemplate(usersSession.getTemplate(idTemplate));
            usersSession.addNetwork(network);
        }

    }

    public void createVolume(long idTemplate, Volume volume) {

        if (usersSession.isLogged()) {
            volume.setTemplate(repositoryManager
                    .getTemplateRepository().findById(idTemplate));
            repositoryManager.getVolumesRepository().save(volume);
        }
        else {
            volume.setTemplate(usersSession.getTemplate(idTemplate));
            usersSession.addVolume(volume);
        }

    }

}
