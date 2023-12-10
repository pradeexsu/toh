package com.example.javatemplate.persistance.repository;

import com.example.javatemplate.persistance.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    @Query(value = "select * from user_table", nativeQuery = true)
    List<User> findAll();
}