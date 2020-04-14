package com.include.easydocker.repositories;

import com.include.easydocker.classes.User;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@CacheConfig(cacheNames = "cache")
public interface UserRepository extends JpaRepository<User, Long> {
    @Cacheable
    List<User> findAll();
    @Cacheable
    User findByNameAndPassword(String name, String password);

}
