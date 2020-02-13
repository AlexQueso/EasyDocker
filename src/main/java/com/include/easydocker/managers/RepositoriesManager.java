package com.include.easydocker.managers;

import com.include.easydocker.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepositoriesManager {

    NetworkRepository networkRepository;
    ProjectRepository projectRepository;
    ServiceRepository serviceRepository;
    VolumesRepository volumesRepository;
    TemplateRepository templateRepository;
    UserRepository userRepository;

    @Autowired
    public RepositoriesManager(
            NetworkRepository networkRepository,
            ProjectRepository projectRepository,
            ServiceRepository serviceRepository,
            VolumesRepository volumesRepository,
            TemplateRepository templateRepository,
            UserRepository userRepository) {

        this.networkRepository = networkRepository;
        this.projectRepository = projectRepository;
        this.serviceRepository = serviceRepository;
        this.volumesRepository = volumesRepository;
        this.templateRepository = templateRepository;
        this.userRepository = userRepository;
    }

    public NetworkRepository getNetworkRepository() {
        return networkRepository;
    }

    public ProjectRepository getProjectRepository() {
        return projectRepository;
    }

    public ServiceRepository getServiceRepository() {
        return serviceRepository;
    }

    public VolumesRepository getVolumesRepository() {
        return volumesRepository;
    }

    public TemplateRepository getTemplateRepository() {
        return templateRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

}
