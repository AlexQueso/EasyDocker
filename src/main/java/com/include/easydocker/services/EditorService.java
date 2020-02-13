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
        service.setTemplate(usersSession.getTemplate(idTemplate));
        usersSession.addService(service);

        if (usersSession.isLogged())
            repositoryManager.getServiceRepository().save(service);
    }

    public void createNetwork(long idTemplate, Network network) {
        network.setTemplate(usersSession.getTemplate(idTemplate));
        usersSession.addNetwork(network);

        if (usersSession.isLogged())
            repositoryManager.getNetworkRepository().save(network);
    }

    public void createVolume(long idTemplate, Volume volume) {
        volume.setTemplate(usersSession.getTemplate(idTemplate));
        usersSession.addVolume(volume);

        if (usersSession.isLogged())
            repositoryManager.getVolumesRepository().save(volume);
    }

}
