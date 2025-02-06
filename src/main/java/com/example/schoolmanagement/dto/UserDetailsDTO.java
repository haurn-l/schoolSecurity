package com.example.schoolmanagement.dto;

public class UserDetailsDTO {
    private String username;
    private String role;

    public UserDetailsDTO(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
