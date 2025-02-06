package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.entity.Student;
import com.example.schoolmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student , Long> {
    Optional<Student> findByUser(User user);

}
