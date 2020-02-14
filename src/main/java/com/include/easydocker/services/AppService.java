package com.include.easydocker.services;

import com.include.easydocker.classes.Project;
import com.include.easydocker.classes.Template;
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

    @Autowired
    public AppService(RepositoriesManager repositoryManager, UsersSession usersSession) {
        this.repositoryManager = repositoryManager;
        this.usersSession = usersSession;
    }

    public void createProject(Project project){
        project.setUser(usersSession.getUser());
        if (usersSession.isLogged())
            repositoryManager.getProjectRepository().save(project);
        else
            usersSession.addProject(project);
    }

    public void createTemplate(long idProject, Template template){

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
}
