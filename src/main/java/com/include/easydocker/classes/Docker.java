package com.include.easydocker.classes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Docker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private DockerDescription dockerDescription;
    private Template template;

    public Docker(){}

}
