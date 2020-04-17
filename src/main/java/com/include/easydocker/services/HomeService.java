package com.include.easydocker.services;

import com.include.easydocker.classes.*;
import com.include.easydocker.managers.RepositoriesManager;
import com.include.easydocker.session.UsersSession;
import com.include.easydocker.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HomeService {

    private final RepositoriesManager repositoryManager;
    private final UsersSession usersSession;

    @Autowired
    public HomeService(RepositoriesManager repositoryManager, UsersSession usersSession) {
        this.repositoryManager = repositoryManager;
        this.usersSession = usersSession;
    }

    ///*
    @PostConstruct
    public void init() {
        User sa = new User("sa", Utils.hash("sa"));
        repositoryManager.getUserRepository().save(sa);
        User user1 = new User("user1", Utils.hash("1234"));
        repositoryManager.getUserRepository().save(user1);
        User user2 = new User("AlexQuesada", Utils.hash("alex"));
        repositoryManager.getUserRepository().save(user2);
        User user3 = new User("JoseZamora", Utils.hash("jose"));
        repositoryManager.getUserRepository().save(user3);
        User user4 = new User("DavidCorreas", Utils.hash("david"));
        repositoryManager.getUserRepository().save(user4);

        Project project1 = new Project("project example");
        project1.setUser(user1);
        repositoryManager.getProjectRepository().save(project1);
        Project project2 = new Project("example");
        project2.setUser(sa);
        repositoryManager.getProjectRepository().save(project2);
        Project project3 = new Project("Diseño de Aplicaciones Distribuidas");
        project3.setUser(user2);
        repositoryManager.getProjectRepository().save(project3);
        Project project4 = new Project("Jenkins");
        project4.setUser(user3);
        repositoryManager.getProjectRepository().save(project4);
        Project project5 = new Project("TFG");
        project5.setUser(user4);
        repositoryManager.getProjectRepository().save(project5);

        Template template1 = new Template("template example");
        template1.setProject(project1);
        repositoryManager.getTemplateRepository().save(template1);
        Template template2 = new Template("example");
        template2.setProject(project2);
        repositoryManager.getTemplateRepository().save(template2);
        Template template3 = new Template("MySQL");
        template3.setProject(project3);
        repositoryManager.getTemplateRepository().save(template3);
        Template template4 = new Template("Linux");
        template4.setProject(project4);
        repositoryManager.getTemplateRepository().save(template4);
        Template template5 = new Template("Linux");
        template5.setProject(project5);
        repositoryManager.getTemplateRepository().save(template5);

        Service service1 = new Service("service example");
        service1.setTemplate(template1);
        repositoryManager.getServiceRepository().save(service1);
        Service service2 = new Service("service1");
        service2.setTemplate(template2);
        repositoryManager.getServiceRepository().save(service2);
        Service service3 = new Service("service2");
        service3.setTemplate(template2);
        repositoryManager.getServiceRepository().save(service3);
        Service service4 = new Service("Docker1");
        service4.setTemplate(template4);
        repositoryManager.getServiceRepository().save(service4);
        Service service5 = new Service("Docker1");
        service5.setTemplate(template5);
        repositoryManager.getServiceRepository().save(service5);

        Network network1 = new Network("network example");
        network1.setTemplate(template1);
        repositoryManager.getNetworkRepository().save(network1);
        Network network2 = new Network("net1");
        network2.setTemplate(template2);
        repositoryManager.getNetworkRepository().save(network2);
        Network network3 = new Network("net1");
        network3.setTemplate(template3);
        repositoryManager.getNetworkRepository().save(network3);
        Network network4 = new Network("network1");
        network4.setTemplate(template4);
        repositoryManager.getNetworkRepository().save(network4);
        Network network5 = new Network("network1");
        network5.setTemplate(template5);
        repositoryManager.getNetworkRepository().save(network5);

        Volume volume1 = new Volume("volume example");
        volume1.setTemplate(template1);
        repositoryManager.getVolumesRepository().save(volume1);
        Volume volume2 = new Volume("vol1");
        volume2.setTemplate(template2);
        repositoryManager.getVolumesRepository().save(volume2);
        Volume volume3 = new Volume("MySQL");
        volume3.setTemplate(template3);
        repositoryManager.getVolumesRepository().save(volume3);
    }
    //*/
    
    public boolean signedInSuccessfully(String user, String password) {
        User welcomeUser = checkUser(user, Utils.hash(password));

        if (welcomeUser == null)
            return false;

        usersSession.setLogged(true);
        usersSession.setUser(welcomeUser);
        usersSession.getUser()
                .setProjects(repositoryManager.getProjectRepository().findByUser(welcomeUser));

        return true;
    }

    public boolean signedUpSuccessfully(String user, String password) {
        String hashedPwd = Utils.hash(password);
        User newUser = checkUser(user, hashedPwd);

        if (newUser != null)
            return false;

        newUser = new User(user, hashedPwd);
        repositoryManager.getUserRepository().save(newUser);

        usersSession.setUser(newUser);
        usersSession.setLogged(true);

        return true;
    }

    private User checkUser(String user, String hash) {
        return repositoryManager.getUserRepository()
                .findByNameAndPassword(user, hash);
    }

    public void createUserTemporal(){
        User unknownUser = new User();
        unknownUser.setId(System.currentTimeMillis());
        usersSession.initTemporalUserInformation();
        usersSession.setUser(unknownUser);
    }

}
