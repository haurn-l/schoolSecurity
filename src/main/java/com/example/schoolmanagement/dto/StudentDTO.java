package com.example.schoolmanagement.dto;

public class StudentDTO {
    private String name;
    private String surname;
    private String studentClass;
    private String teacher;

    public StudentDTO(String name, String surname, String studentClass, String teacher) {
        this.name = name;
        this.surname = surname;
        this.studentClass = studentClass;
        this.teacher = teacher;
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

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
