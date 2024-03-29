package com.stns.crudapi.repository;

import com.stns.crudapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findById(int id);

    Optional<User> findByName(String username);
}
