package com.example.schoolmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // Refresh token eklemek (cache'e yazma)
    public void setRefreshToken(String key, String token) {
        redisTemplate.opsForValue().set(key, token);
    }

    // Refresh token almak (cache'den okuma)
    public String getRefreshToken(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // Refresh token silmek (cache'den silme)
    public void deleteRefreshToken(String key) {
        redisTemplate.delete(key);
    }

    // Refresh token'ın var olup olmadığını kontrol etme
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }
}