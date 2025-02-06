package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.*;
import com.example.schoolmanagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;
   
    @GetMapping("/test-redis")
    public String testRedis() {
        try {
            String testUsername = "testUser";
            String testToken = "testToken123";
            
            // Redis'e token kaydet
            authService.storeRefreshToken(testUsername, testToken);
            
            // Redis'ten token al
            String retrievedToken = authService.getRefreshTokenFromRedis(testUsername);
            
            // Token'ı sil
            authService.invalidateRefreshToken(testUsername);
            
            return "Redis test başarılı! Token kaydedildi ve doğrulandı: " + retrievedToken;
        } catch (Exception e) {
            return "Redis test başarısız: " + e.getMessage();
        }
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequestDTO request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody AuthRequestDTO loginRequest) {
        return authService.login(loginRequest);
    }


    @PostMapping("/refresh")
    public RefreshTokenResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO request) {
        return authService.refreshAccessToken(request.getRefreshToken());
    }

    // Logout endpoint
    public String logout(@RequestBody LogoutRequestDTO logoutRequest) {
        authService.logout(logoutRequest.getAccessToken());
        return "Başarıyla çıkış yapıldı, refresh token silindi.";
    }
}
