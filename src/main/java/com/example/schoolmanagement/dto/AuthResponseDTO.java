package com.example.schoolmanagement.dto;



public class AuthResponseDTO {
    private String token;
    private String refreshToken;

    public AuthResponseDTO() {
    }

    public AuthResponseDTO(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }
    public String getToken() {
        return token;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
}
