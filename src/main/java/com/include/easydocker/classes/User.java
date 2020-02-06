package com.include.easydocker.classes;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String Name;
    private String HashedPassword;

    @OneToMany
    private List<Project> projects;

    /*CONSTRUCTORS*/
    public User() {}

    public User(long id, String name, String hashedPassword, List<Project> projects) {
        this.id = id;
        Name = name;
        HashedPassword = hashedPassword;
        this.projects = projects;
    }

    /*GETTERS AND SETTERS*/
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getHashedPassword() {
        return HashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        HashedPassword = hashedPassword;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /*EQUALS AND HASHCODE*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
