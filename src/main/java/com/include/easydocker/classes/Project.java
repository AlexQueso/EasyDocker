package com.include.easydocker.classes;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @ManyToOne
    private User user;
    @OneToMany
    private List<Template> templates; // TODO: cambiar por HashMap

    /*CONSTRUCTORS*/
    public Project() {
    }

    public Project(String name, User user, List<Template> templates) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String projectName) {
        this.name = projectName;
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
