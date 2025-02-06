package com.example.schoolmanagement.dto;

import com.example.schoolmanagement.enums.Role;

public class AuthRequestDTO {
    private String username;
    private String password;
    private Role role; // Hangi role sahip olduğu bilgisi

    public AuthRequestDTO() {
    }

    public AuthRequestDTO(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
