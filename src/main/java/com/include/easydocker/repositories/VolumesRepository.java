package com.include.easydocker.repositories;

import com.include.easydocker.classes.Volume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolumesRepository extends JpaRepository<Volume, Long> {

    Volume findById(long id);
}
