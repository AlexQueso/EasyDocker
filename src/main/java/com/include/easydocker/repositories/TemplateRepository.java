package com.include.easydocker.repositories;

import com.include.easydocker.classes.Project;
import com.include.easydocker.classes.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateRepository extends JpaRepository<Template, Long> {

    Template findById(long id);

    List<Template> findByProject(Project project);

}
