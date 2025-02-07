package com.example.schoolmanagement.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import com.example.schoolmanagement.dto.StudentDTO;
import com.example.schoolmanagement.dto.StudentDetailDTO;
import com.example.schoolmanagement.exception.CustomException;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.entity.Teacher;
import com.example.schoolmanagement.entity.Student;
import com.example.schoolmanagement.repository.TeacherRepository;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    public List<StudentDTO> getTeacherStudents(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("Öğretmen bulunamadı", HttpStatus.NOT_FOUND));

        Teacher teacher = teacherRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Öğretmen bilgileri bulunamadı", HttpStatus.NOT_FOUND));

        return teacher.getStudents().stream()
                .map(student -> new StudentDTO(
                        student.getId(),
                        student.getName(),
                        student.getSurname(),
                        student.getStudentClass(),
                        teacher.getName()
                ))
                .collect(Collectors.toList());
    }

    public StudentDetailDTO getStudentDetail(Long studentId, String teacherUsername) {
        // Önce öğretmeni bul
        User teacherUser = userRepository.findByUsername(teacherUsername)
                .orElseThrow(() -> new CustomException("Öğretmen bulunamadı", HttpStatus.NOT_FOUND));

        Teacher teacher = teacherRepository.findByUser(teacherUser)
                .orElseThrow(() -> new CustomException("Öğretmen bilgileri bulunamadı", HttpStatus.NOT_FOUND));

        // Öğrenciyi bul
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException("Öğrenci bulunamadı", HttpStatus.NOT_FOUND));

        // Öğrencinin bu öğretmene ait olup olmadığını kontrol et
        if (!student.getTeacher().getId().equals(teacher.getId())) {
            throw new CustomException("Bu öğrencinin bilgilerini görüntüleme yetkiniz yok", HttpStatus.FORBIDDEN);
        }

        return new StudentDetailDTO(
                student.getName(),
                student.getSurname(),
                student.getStudentClass(),
                student.getTeacher().getName(),
                student.getTeacher().getBranch()
        );
    }
}
