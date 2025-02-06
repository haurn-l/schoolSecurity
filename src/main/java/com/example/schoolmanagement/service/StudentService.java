package com.example.schoolmanagement.service;

import com.example.schoolmanagement.dto.StudentDTO;
import com.example.schoolmanagement.entity.Student;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.exception.CustomException;
import com.example.schoolmanagement.repository.StudentRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentService {

    @Autowired
     JwtUtil jwtUtil;

    @Autowired
     StudentRepository studentRepository;

    @Autowired
     UserRepository userRepository;

    public StudentDTO getStudentInfo(String username) {
        log.info("Öğrenci bilgileri alınıyor: {}", username);

        // Kullanıcıyı bul
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("Kullanıcı bulunamadı!"));

        // Kullanıcıya bağlı öğrenciyi bul
        Student student = studentRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Öğrenci bulunamadı!"));

        // Öğrenci bilgilerini DTO'ya dönüştür
        return new StudentDTO(
                student.getName(),
                student.getSurname(),
                student.getStudentClass(),
                student.getTeacher() != null ? student.getTeacher().getName() : null
        );
    }
}
