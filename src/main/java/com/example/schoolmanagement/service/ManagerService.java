package com.example.schoolmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import com.example.schoolmanagement.dto.StudentDTO;
import com.example.schoolmanagement.dto.StudentDetailDTO;
import com.example.schoolmanagement.dto.TeacherDetailDTO;
import com.example.schoolmanagement.entity.Student;
import com.example.schoolmanagement.entity.Teacher;
import com.example.schoolmanagement.repository.StudentRepository;
import com.example.schoolmanagement.repository.TeacherRepository;
import com.example.schoolmanagement.exception.CustomException;


@Service
@Slf4j
public class ManagerService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(student -> new StudentDTO(
                        student.getName(),
                        student.getSurname(),
                        student.getStudentClass(),
                        student.getTeacher() != null ? student.getTeacher().getName() : "Öğretmen atanmamış"
                ))
                .collect(Collectors.toList());
    }

    public StudentDetailDTO getStudentDetail(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException("Öğrenci bulunamadı", HttpStatus.NOT_FOUND));

        return new StudentDetailDTO(
                student.getName(),
                student.getSurname(),
                student.getStudentClass(),
                student.getTeacher() != null ? student.getTeacher().getName() : "Öğretmen atanmamış",
                student.getTeacher() != null ? student.getTeacher().getBranch() : null
        );
    }

    public StudentDetailDTO updateStudentClass(Long studentId, String newClass) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException("Öğrenci bulunamadı", HttpStatus.NOT_FOUND));

        student.setStudentClass(newClass);
        studentRepository.save(student);

        return getStudentDetail(studentId);
    }

    public StudentDetailDTO updateStudentTeacher(Long studentId, Long newTeacherId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException("Öğrenci bulunamadı", HttpStatus.NOT_FOUND));

        Teacher newTeacher = teacherRepository.findById(newTeacherId)
                .orElseThrow(() -> new CustomException("Öğretmen bulunamadı", HttpStatus.NOT_FOUND));

        student.setTeacher(newTeacher);
        studentRepository.save(student);

        return getStudentDetail(studentId);
    }

    public List<TeacherDetailDTO> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(teacher -> new TeacherDetailDTO(
                        teacher.getName(),
                        teacher.getSurname(),
                        teacher.getBranch(),
                        teacher.getStudents().stream()
                                .map(student -> new StudentDTO(
                                        student.getName(),
                                        student.getSurname(),
                                        student.getStudentClass(),
                                        teacher.getName()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    public TeacherDetailDTO getTeacherDetail(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new CustomException("Öğretmen bulunamadı", HttpStatus.NOT_FOUND));

        return new TeacherDetailDTO(
                teacher.getName(),
                teacher.getSurname(),
                teacher.getBranch(),
                teacher.getStudents().stream()
                        .map(student -> new StudentDTO(
                                student.getName(),
                                student.getSurname(),
                                student.getStudentClass(),
                                teacher.getName()
                        ))
                        .collect(Collectors.toList())
        );
    }
}
