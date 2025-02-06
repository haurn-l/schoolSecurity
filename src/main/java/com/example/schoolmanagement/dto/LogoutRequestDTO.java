package com.example.schoolmanagement.dto;

public class LogoutRequestDTO {
    private String accessToken;
    public LogoutRequestDTO(){

    }
    public LogoutRequestDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
