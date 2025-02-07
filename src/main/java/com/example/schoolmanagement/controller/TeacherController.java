package com.example.schoolmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import java.security.Principal;
import java.util.List;
import com.example.schoolmanagement.dto.StudentDTO;
import com.example.schoolmanagement.dto.StudentDetailDTO;
import com.example.schoolmanagement.service.TeacherService; 

@RestController
@RequestMapping("/teachers")
@Slf4j
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/students")
    public ResponseEntity<List<StudentDTO>> getMyStudents(Principal principal) {
        List<StudentDTO> students = teacherService.getTeacherStudents(principal.getName());
        return ResponseEntity.ok(students);
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<StudentDetailDTO> getStudentDetail(@PathVariable Long studentId, Principal principal) {
        StudentDetailDTO studentDetail = teacherService.getStudentDetail(studentId, principal.getName());
        return ResponseEntity.ok(studentDetail);
    }
}
