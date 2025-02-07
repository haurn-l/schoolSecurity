package com.example.schoolmanagement.controller;

import com.example.schoolmanagement.dto.StudentDTO;
import com.example.schoolmanagement.dto.StudentDetailDTO;
import com.example.schoolmanagement.dto.TeacherDetailDTO;
import com.example.schoolmanagement.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/managers")
@Slf4j
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    // Öğrenci işlemleri
    @GetMapping("/students")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(managerService.getAllStudents());
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<StudentDetailDTO> getStudentDetail(@PathVariable Long studentId) {
        return ResponseEntity.ok(managerService.getStudentDetail(studentId));
    }

    @PutMapping("/students/{studentId}/class")
    public ResponseEntity<StudentDetailDTO> updateStudentClass(
            @PathVariable Long studentId,
            @RequestParam String newClass) {
        return ResponseEntity.ok(managerService.updateStudentClass(studentId, newClass));
    }

    @PutMapping("/students/{studentId}/teacher")
    public ResponseEntity<StudentDetailDTO> updateStudentTeacher(
            @PathVariable Long studentId,
            @RequestParam Long newTeacherId) {
        return ResponseEntity.ok(managerService.updateStudentTeacher(studentId, newTeacherId));
    }

    // Öğretmen işlemleri
    @GetMapping("/teachers")
    public ResponseEntity<List<TeacherDetailDTO>> getAllTeachers() {
        return ResponseEntity.ok(managerService.getAllTeachers());
    }

    @GetMapping("/teachers/{teacherId}")
    public ResponseEntity<TeacherDetailDTO> getTeacherDetail(@PathVariable Long teacherId) {
        return ResponseEntity.ok(managerService.getTeacherDetail(teacherId));
    }
}
