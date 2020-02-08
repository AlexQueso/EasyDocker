package com.include.easydocker.classes;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String proyectName;

    @ManyToOne
    private User user;
    @OneToMany
    private List<Template> templates;

    /*CONSTRUCTORS*/
    public Project() {
    }

    public Project(long id, String projectName, User user, List<Template> templates) {
        this.id = id;
        this.proyectName = projectName;
        this.user = user;
        this.templates = templates;
    }

    /*GETTERS AND SETTERS*/
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProyectName() {
        return proyectName;
    }

    public void setProyectName(String proyectName) {
        this.proyectName = proyectName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

    /*EQUALS AND HASHCODE*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id == project.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
