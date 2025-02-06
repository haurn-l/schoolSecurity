package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.StudentDTO;
import com.example.schoolmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping("/me")
    public StudentDTO getStudentDetails(Principal principal) {//aktif giriş yapmış kişi için bilgi alınması
        return studentService.getStudentInfo(principal.getName());
    }

}
