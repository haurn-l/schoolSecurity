package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndRole(String username, Role role);

}
