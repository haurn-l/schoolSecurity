package com.example.schoolmanagement.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.access.expiration}")
    private long accessTokenExpirationInMs;

    @Value("${jwt.refresh.expiration}")
    public long refreshTokenExpirationInMs;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // Constructor'ı kaldırıyoruz ve @Autowired kullanıyoruz
    public JwtUtil() {
    }

    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(String username, String role) {
        return generateToken(username, role, accessTokenExpirationInMs);
    }

    public String generateRefreshToken(String username, String role) {
        return generateToken(username, role, refreshTokenExpirationInMs);
    }

    public String generateToken(String username, String role, long expirationTime) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);
        
        log.info("Token oluşturuluyor - Başlangıç: {}, Bitiş: {}", now, expiryDate); // Debug için log ekleyelim
        
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(now)  // Token oluşturma zamanı
                .setExpiration(expiryDate)  // Token bitiş zamanı
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isRefreshTokenExpired(String refreshToken) {
        Date expiration = extractExpiration(refreshToken);
        return expiration.before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, String username) {
        String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }
}