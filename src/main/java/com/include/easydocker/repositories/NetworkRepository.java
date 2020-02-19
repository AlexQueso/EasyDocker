package com.include.easydocker.repositories;

import com.include.easydocker.classes.Network;
import com.include.easydocker.classes.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NetworkRepository extends JpaRepository<Network, Long> {

    Network findById(long id);

    Network findByName(String name);

    List<Network> findByTemplate(Template template);
}
