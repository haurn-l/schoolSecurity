package com.example.schoolmanagement.dto;

import com.example.schoolmanagement.enums.Branch;

public class StudentDetailDTO {
    private String name;
    private String surname;
    private String studentClass;
    private String teacherName;
    private Branch teacherBranch;

    public StudentDetailDTO(String name, String surname, String studentClass, String teacherName, Branch teacherBranch) {
        this.name = name;
        this.surname = surname;
        this.studentClass = studentClass;
        this.teacherName = teacherName;
        this.teacherBranch = teacherBranch;
    }

    public StudentDetailDTO() {
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

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Branch getTeacherBranch() {
        return teacherBranch;
    }

    public void setTeacherBranch(Branch teacherBranch) {
        this.teacherBranch = teacherBranch;
    }
}