package com.nhk.thesis.entity.vo;

import com.nhk.thesis.entity.Student;

import java.util.List;
import java.util.Map;

public class StudentVO {
    private String id;

    private String studentCode;
    private String normalizedName;
    private String name;
    private String dateOfBirth;
    private Map<String, String> courses;
    private String classCode;
    private String email;
    private boolean isIMark;
    private String iMarkSemester;

    public StudentVO() {
    }

    public StudentVO(Student student) {
        this.id = student.getId();
        this.studentCode = student.getStudentCode();
        this.name = student.getName();
        this.dateOfBirth = student.getDateOfBirth()+"";
        this.classCode = student.getClassCode();
        this.normalizedName = student.getNormalizedName();
        this.courses = student.getCourses();
        this.email = student.getEmail();
        this.iMarkSemester = student.getiMarkSemester();
        this.isIMark = student.isIMark();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getNormalizedName() {
        return normalizedName;
    }

    public void setNormalizedName(String normalizedName) {
        this.normalizedName = normalizedName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public Map<String, String> getCourses() {
        return courses;
    }

    public void setCourses(Map<String, String> courses) {
        this.courses = courses;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isIMark() {
        return isIMark;
    }

    public void setIMark(boolean IMark) {
        isIMark = IMark;
    }

    public String getiMarkSemester() {
        return iMarkSemester;
    }

    public void setiMarkSemester(String iMarkSemester) {
        this.iMarkSemester = iMarkSemester;
    }
}
