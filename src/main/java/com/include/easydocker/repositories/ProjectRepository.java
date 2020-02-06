package com.include.easydocker.repositories;

import com.include.easydocker.classes.Project;
import com.include.easydocker.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findById(long id);

    List<Project> findByUser(User user);

    List<Project> findAll();
}
