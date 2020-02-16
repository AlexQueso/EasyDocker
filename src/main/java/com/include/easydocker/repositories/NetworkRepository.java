package com.include.easydocker.repositories;

import com.include.easydocker.classes.Network;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NetworkRepository extends JpaRepository<Network, Long> {

    Network findById(long id);
}
