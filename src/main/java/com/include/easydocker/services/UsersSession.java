package com.include.easydocker.services;

import com.include.easydocker.classes.Project;
import com.include.easydocker.classes.Template;
import com.include.easydocker.classes.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UsersSession {

    boolean logged = false;

    private User user;

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

    public Project findProjectById(long idProject) {
        for (Project p: user.getProjects()){
            if (p.getId() == idProject)
                return p;
        }
        return null;
    }

    public Template findTemplateById(long idTemplate){
        for (Project p: user.getProjects())
            for (Template t: p.getTemplates())
                if (t.getId() == idTemplate)
                    return t;

        return null;
    }
}
