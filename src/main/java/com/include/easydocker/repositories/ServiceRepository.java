package com.include.easydocker.repositories;

import com.include.easydocker.classes.Network;
import com.include.easydocker.classes.Service;
import com.include.easydocker.classes.Volume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findByNetwork(Network network);

    List<Service> findByVolume(Volume volume);
}
