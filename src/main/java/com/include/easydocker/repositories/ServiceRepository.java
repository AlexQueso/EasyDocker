package com.include.easydocker.repositories;

import com.include.easydocker.classes.Service;
import com.include.easydocker.classes.Template;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@CacheConfig(cacheNames = "cache")
public interface ServiceRepository extends JpaRepository<Service, Long> {

    Service findById(long id);

    Service findByName(String name);

    List<Service> findByTemplate(Template template);
}
