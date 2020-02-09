package com.include.easydocker.repositories;

import com.include.easydocker.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    User findByNameAndPassword(String name, String password);

}
