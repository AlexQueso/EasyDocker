package com.include.easydocker.repositories;

import com.include.easydocker.classes.Network;
import com.include.easydocker.classes.Project;
import com.include.easydocker.classes.User;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@CacheConfig(cacheNames = "cache")
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Cacheable
    Project findById(long id);

    @Cacheable
    List<Project> findByUser(User user);

    @Cacheable
    List<Project> findAll();

    @CacheEvict(allEntries = true)
    Project save(Project project);
}
