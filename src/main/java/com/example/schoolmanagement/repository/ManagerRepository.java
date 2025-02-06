package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.entity.Manager;
import com.example.schoolmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager,Long> {
    Optional<Manager> findByUser(User user);
}
