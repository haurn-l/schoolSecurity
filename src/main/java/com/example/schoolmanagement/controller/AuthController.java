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
