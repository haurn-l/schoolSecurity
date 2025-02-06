package com.example.schoolmanagement.dto;

public class RefreshTokenResponseDTO {
    private String accessToken;
    private String refreshToken;

    public RefreshTokenResponseDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public RefreshTokenResponseDTO() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

}
