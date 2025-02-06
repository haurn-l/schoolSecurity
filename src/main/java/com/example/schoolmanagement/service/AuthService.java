package com.example.schoolmanagement.service;


import com.example.schoolmanagement.dto.*;
import com.example.schoolmanagement.entity.Manager;
import com.example.schoolmanagement.entity.Student;
import com.example.schoolmanagement.entity.Teacher;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.exception.CustomException;
import com.example.schoolmanagement.repository.ManagerRepository;
import com.example.schoolmanagement.repository.StudentRepository;
import com.example.schoolmanagement.repository.TeacherRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Service
public class AuthService {
    @Autowired
     AuthenticationManager authenticationManager;

    @Autowired
     UserRepository userRepository;

    @Autowired
     JwtUtil jwtUtil;

    @Autowired
     RedisTemplate<String, String> redisTemplate;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public String register(RegisterRequestDTO request) {
        // Kullanıcı adı kontrolü
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new CustomException("Bu kullanıcı adı zaten kullanılıyor!");
        }

        // Yeni kullanıcı oluştur
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode( request.getPassword() )); // Şifre şifrelenmeyecek
        user.setRole(request.getRole());
        userRepository.save(user);

        // Kullanıcının rolüne göre işlem yap
        switch (request.getRole()) {
            case STUDENT -> registerStudent(request, user);
            case TEACHER -> registerTeacher(request, user);
            case MANAGER -> registerManager(request, user);
            default -> throw new CustomException("Geçersiz rol!");
        }

        return "Kayıt başarılı! " + request.getRole() + " rolüyle hesap oluşturuldu.";
    }

    private void registerStudent(RegisterRequestDTO request, User user) {
        Student student = new Student();
        student.setName(request.getName());
        student.setSurname(request.getSurname());
        student.setStudentClass(request.getStudentClass());
        student.setUser(user);

        // Öğretmen ID kontrolü
        if (request.getTeacherId() != null) {
            Optional<Teacher> teacher = teacherRepository.findById(request.getTeacherId());
            teacher.ifPresentOrElse(student::setTeacher,
                    () -> { throw new CustomException("Öğretmen bulunamadı!"); });
        }

        studentRepository.save(student);
    }

    private void registerTeacher(RegisterRequestDTO request, User user) {
        Teacher teacher = new Teacher();
        teacher.setName(request.getName());
        teacher.setSurname(request.getSurname());
        teacher.setBranch(request.getBranch()); // Branşı DTO'dan alıyor
        teacher.setUser(user);
        teacherRepository.save(teacher);
    }

    private void registerManager(RegisterRequestDTO request, User user) {
        Manager manager = new Manager();
        manager.setName(request.getName());
        manager.setSurname(request.getSurname());
        manager.setUser(user);
        managerRepository.save(manager);
    }

    public AuthResponseDTO login(AuthRequestDTO request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {

            User user = userRepository.findByUsernameAndRole(request.getUsername(), request.getRole())
                    .orElseThrow(() -> new CustomException("Kullanıcı bulunamadı veya rol hatalı!"));

            String accessToken = jwtUtil.generateAccessToken(user.getUsername(),user.getRole().name());
            String refreshToken = jwtUtil.generateRefreshToken(user.getUsername(),user.getRole().name());


            storeRefreshToken(user.getUsername(), refreshToken);

            return new AuthResponseDTO(accessToken, refreshToken);
        } else {
            throw new CustomException("Geçersiz kimlik bilgileri!");
        }
    }

    public RefreshTokenResponseDTO refreshAccessToken(String refreshToken) {
        try {
            // 1. Refresh token'dan username ve role bilgisini çıkar
            String username = jwtUtil.extractUsername(refreshToken);
            String role = jwtUtil.extractRole(refreshToken);

            // 2. Redis'ten kayıtlı token'ı al
            String storedToken = getRefreshTokenFromRedis(username);

            // 3. Token kontrollerini yap
            if (storedToken == null) {
                throw new CustomException("Refresh token bulunamadı. Lütfen tekrar giriş yapın.");
            }
            
            if (!storedToken.equals(refreshToken)) {
                throw new CustomException("Geçersiz refresh token.");
            }
            
            if (jwtUtil.isRefreshTokenExpired(refreshToken)) {
                invalidateRefreshToken(username); // Süresi dolmuş token'ı Redis'ten sil
                throw new CustomException("Refresh token süresi dolmuş. Lütfen tekrar giriş yapın.");
            }

            // 4. Yeni access token oluştur
            String newAccessToken = jwtUtil.generateAccessToken(username, role);

            // 5. İsteğe bağlı: Refresh token rotasyonu
            // Her refresh işleminde yeni bir refresh token da oluşturulabilir
            String newRefreshToken = jwtUtil.generateRefreshToken(username, role);
            storeRefreshToken(username, newRefreshToken);

            // 6. Yeni token'ları dön
            return new RefreshTokenResponseDTO(newAccessToken, newRefreshToken);
        } catch (Exception e) {
            throw new CustomException("Token yenileme işlemi başarısız: " + e.getMessage());
        }
    }

    public void storeRefreshToken(String username, String refreshToken) {
        redisTemplate.opsForValue().set(username + ":refreshToken", refreshToken, jwtUtil.refreshTokenExpirationInMs, TimeUnit.MILLISECONDS);
    }

    public String getRefreshTokenFromRedis(String username) {
        return redisTemplate.opsForValue().get(username + ":refreshToken");
    }

    public void invalidateRefreshToken(String username) {
        redisTemplate.delete(username + ":refreshToken");
    }

    public void logout(String accessToken) {
        try {
            String username = jwtUtil.extractUsername(accessToken);
            invalidateRefreshToken(username);
        } catch (Exception e) {
            throw new CustomException("Çıkış işlemi sırasında hata oluştu: " + e.getMessage());
        }
    }
}
