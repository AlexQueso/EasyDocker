package com.include.easydocker.repositories;

import com.include.easydocker.classes.Template;
import com.include.easydocker.classes.Volume;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolumesRepository extends JpaRepository<Volume, Long> {

    Volume findById(long id);
    Volume findByName(String name);
    List<Volume> findByTemplate(Template template);
}
