package com.nhk.thesis.entity.vo;

import com.nhk.thesis.entity.Course;
import com.nhk.thesis.entity.Student;

import java.util.List;

public class CourseVO {
    private String id;

    private String classCode; // tên lớp
    private UserVO lecturer; //id của user
    private SemesterVO semester; // học kỳ
    private List<StudentVO> studentVO;
    private List<String> students;
    private String statusCode;
    private String statusName;

    public CourseVO() {}

    public CourseVO(Course course, SemesterVO semester, UserVO user) {
        this.id = course.getId();
        this.classCode = course.getClassCode();
        this.students = course.getStudents();
        this.lecturer = user;
        this.semester = semester;
        this.statusCode = course.getStatus().getCode();
        this.statusName = course.getStatus().getText();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public UserVO getLecturer() {
        return lecturer;
    }

    public void setLecturer(UserVO lecturer) {
        this.lecturer = lecturer;
    }

    public SemesterVO getSemester() {
        return semester;
    }

    public void setSemester(SemesterVO semester) {
        this.semester = semester;
    }

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public List<StudentVO> getStudentVO() {
        return studentVO;
    }

    public void setStudentVO(List<StudentVO> studentVO) {
        this.studentVO = studentVO;
    }
}
