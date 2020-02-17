package com.include.easydocker.services;

import com.include.easydocker.classes.*;
import com.include.easydocker.managers.RepositoriesManager;
import com.include.easydocker.session.UsersSession;
import com.include.easydocker.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostConstruct
    public void init() {
        User user = new User("user1", Utils.hash("1234"));
        repositoryManager.getUserRepository().save(user);

        Project project = new Project("project example");
        project.setUser(user);
        repositoryManager.getProjectRepository().save(project);

        Template template = new Template("template example");
        template.setProject(project);
        repositoryManager.getTemplateRepository().save(template);

        Service service = new Service("service example");
        service.setTemplate(template);
        repositoryManager.getServiceRepository().save(service);

        Network network = new Network("network example");
        network.setTemplate(template);
        repositoryManager.getNetworkRepository().save(network);

        Volume volume = new Volume("volume example");
        volume.setTemplate(template);
        repositoryManager.getVolumesRepository().save(volume);
    }
    
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
