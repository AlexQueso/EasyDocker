package com.include.easydocker.repositories;

import com.include.easydocker.classes.Network;
import com.include.easydocker.classes.Template;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@CacheConfig(cacheNames = "cache")
public interface NetworkRepository extends JpaRepository<Network, Long> {
    @Cacheable
    Network findById(long id);
    @Cacheable
    Network findByName(String name);
    @Cacheable
    List<Network> findByTemplate(Template template);
}
