package com.stns.crudapi.repository;

import com.stns.crudapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findById(int id);
}
