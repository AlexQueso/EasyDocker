package com.include.easydocker.repositories;

import com.include.easydocker.classes.Template;
import com.include.easydocker.classes.Volume;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@CacheConfig(cacheNames = "cache")
public interface VolumesRepository extends JpaRepository<Volume, Long> {
    @Cacheable
    Volume findById(long id);

    @Cacheable
    Volume findByName(String name);

    @Cacheable
    List<Volume> findByTemplate(Template template);

    @CacheEvict(allEntries = true)
    Volume save(Volume volume);
}
