package com.include.easydocker.repositories;

import com.include.easydocker.classes.Docker;
import com.include.easydocker.classes.DockerDescription;
import com.include.easydocker.classes.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DockerRepository extends JpaRepository<Docker, Long> {

    Docker findById(long id);

    List<Docker> findAll();

    List<Docker> findByDockerDescription(DockerDescription dockerDescription);

    List<Docker> findByTemplate(Template template);

}
