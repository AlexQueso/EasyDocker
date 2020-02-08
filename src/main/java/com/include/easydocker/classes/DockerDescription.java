package com.include.easydocker.classes;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

//todo: terminar de definir la clase DockerDescription
@Entity
public class DockerDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //puertos, volumenes... (?)

    @ManyToOne
    private Template template;
    @OneToMany
    private List<Docker> dockers; // TODO: cambiar por HashMap

    /*CONSTRUCTORS*/
    public DockerDescription() {
    }

    //todo: falta el constructor con todos los parámetros

    /*GETTERS AND SETTERS*/
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
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
        DockerDescription that = (DockerDescription) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
