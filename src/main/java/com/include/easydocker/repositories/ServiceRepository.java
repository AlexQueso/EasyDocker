package com.include.easydocker.repositories;

import com.include.easydocker.classes.Service;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ServiceRepository extends JpaRepository<Service, Long> {

    Service findById(long id);

    Service findByName(String name);
}
