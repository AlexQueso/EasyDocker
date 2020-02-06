package com.include.easydocker.classes;

import javax.persistence.*;
import java.util.Objects;

//todo: terminar de definir la clase Docker
@Entity
public class Docker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    private DockerDescription dockerDescription;
    @ManyToOne
    private Template template;

    /*CONSTRUCTORS*/
    public Docker(){}

    //todo: falta el constructor con todos los paramtros

    /*GETTERS AND SETTERS*/
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DockerDescription getDockerDescription() {
        return dockerDescription;
    }

    public void setDockerDescription(DockerDescription dockerDescription) {
        this.dockerDescription = dockerDescription;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    /*EQUALS AND HASHCODE*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Docker docker = (Docker) o;
        return id == docker.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
