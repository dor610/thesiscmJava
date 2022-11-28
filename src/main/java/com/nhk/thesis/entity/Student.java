package com.nhk.thesis.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.Normalizer;
import java.util.*;

@Document(collection = "student")
public class Student {
    @Id
    private String id;

    private String studentCode;
    private String normalizedName;
    private String name;
    private long dateOfBirth;
    private String classCode;
    private Map<String, String> courses;
    private String email;
    private boolean isIMark;
    private String iMarkSemester;

    public Student() {};

    public Student(String studentCode, String name, Date dateOfBirth, String classCode){
        ObjectId objectId = ObjectId.get();
        this.id = "Student_"+ objectId;
        this.studentCode = studentCode;
        this.name = name;
        this.normalizedName = normalizeString(name);
        this.dateOfBirth = dateOfBirth.getTime();
        this.classCode = classCode;
        this.email = generateEmail(name, studentCode);
        this.isIMark = false;
        this.iMarkSemester = "";
    }

    public void addCourse(String course, String semester) {
        if(this.courses == null)
            this.courses = new LinkedHashMap<>();
        this.courses.put(semester, course);
    }

    public boolean checkCourse(String semester) {
        if(courses == null)
            return true;
        if(courses.get(semester) != null) {
            return false;
        }

        return true;
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

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String normalizeString(String str) {
        return Normalizer
                .normalize(str, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
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

    public String generateEmail(String name, String studentCode) {
        List<String> lst = Arrays.asList(name.split("\\s+"));
        return (lst.get(lst.size() - 1) + studentCode + "@student.ctu.edu.vn").toLowerCase();
    }
}
