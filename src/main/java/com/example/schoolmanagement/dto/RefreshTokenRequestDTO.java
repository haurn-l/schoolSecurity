package com.example.schoolmanagement.dto;

public class RefreshTokenRequestDTO {
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public RefreshTokenRequestDTO(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public RefreshTokenRequestDTO() {
    }

}
