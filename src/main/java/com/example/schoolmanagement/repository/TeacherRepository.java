package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.entity.Teacher;
import com.example.schoolmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    Optional<Teacher> findByUser(User user);

}
