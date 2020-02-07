package com.include.easydocker.classes;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String templateName;

    @ManyToOne
    private Project project;
    @OneToMany
    private List<DockerDescription> dockerDescriptions;
    @OneToMany
    private List<Docker> dockers;

    /*CONSTRUCTORS*/
    public Template(){}

    public Template(Long id, String templateName, Project project, LinkedList<DockerDescription> dockerDescriptions, LinkedList<Docker> dockers) {
        this.id = id;
        this.templateName = templateName;
        this.project = project;
        this.dockerDescriptions = dockerDescriptions;
        this.dockers = dockers;
    }

    /*GETTERS AND SETTERS*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<DockerDescription> getDockerDescriptions() {
        return dockerDescriptions;
    }

    public void setDockerDescriptions(List<DockerDescription> dockerDescriptions) {
        this.dockerDescriptions = dockerDescriptions;
    }

    public List<Docker> getDockers() {
        return dockers;
    }

    public void setDockers(List<Docker> dockers) {
        this.dockers = dockers;
    }

    /*EQUALS AND HASHCODE*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template template = (Template) o;
        return Objects.equals(id, template.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
