package com.include.easydocker.repositories;

import com.include.easydocker.classes.Docker;
import com.include.easydocker.classes.DockerDescription;
import com.include.easydocker.classes.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DockerDescriptionRepository extends JpaRepository<DockerDescription, Long> {

    DockerDescription findById(long id);

    // DockerDescription findByDocker(Docker docker); Todo: Fix

    List<DockerDescription> findAll();

    List<DockerDescription> findByTemplate(Template template);
}
