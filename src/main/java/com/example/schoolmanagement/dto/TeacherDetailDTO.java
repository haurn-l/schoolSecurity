package com.example.schoolmanagement.dto;

import com.example.schoolmanagement.enums.Branch;
import java.util.List;


public class TeacherDetailDTO {

    private String name;
    private String surname;
    private Branch branch;
    private List<StudentDTO> students;

    public TeacherDetailDTO(String name, String surname, Branch branch, List<StudentDTO> students) {
        this.name = name;
        this.surname = surname;
        this.branch = branch;
        this.students = students;
    }

    public TeacherDetailDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public List<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(List<StudentDTO> students) {
        this.students = students;
    }
}