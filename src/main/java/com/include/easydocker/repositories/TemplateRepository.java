package com.include.easydocker.repositories;

import com.include.easydocker.classes.Project;
import com.include.easydocker.classes.Template;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@CacheConfig(cacheNames = "cache")
public interface TemplateRepository extends JpaRepository<Template, Long> {

    @Cacheable
    Template findById(long id);

    @Cacheable
    List<Template> findByProject(Project project);

    @CacheEvict(allEntries = true)
    Template save(Template template);
}
