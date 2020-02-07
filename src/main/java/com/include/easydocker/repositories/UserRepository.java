package com.include.easydocker.repositories;

import com.include.easydocker.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    User findById(long id);

    // User findByHashedPassWord(String hashedPassword); TODO: fix
}
