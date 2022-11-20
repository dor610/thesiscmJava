package com.nhk.thesis.entity;

import com.nhk.thesis.entity.constant.CourseStatus;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;

@Document(collection = "")
public class Course {
    @Id
    private String id;

    private String classCode; // tên lớp
    private String lecturer; //id của user
    private String semester; // học kỳ
    private List<String> students;
    private CourseStatus status;

    public Course() {}

    public Course(String classCode, String lecturer, String semester, List<String> students, CourseStatus status) {
        ObjectId id = ObjectId.get();
        this.id = "course_"+id;
        this.classCode = classCode;
        this.lecturer = lecturer;
        this.semester = semester;
        this.students = students;
        this.status = status;
    }

    public Course(String classCode, String lecturer, String semester, CourseStatus status) {
        ObjectId id = ObjectId.get();
        this.id = "course_" + id;
        this.classCode = classCode;
        this.lecturer = lecturer;
        this.semester = semester;
        this.students = Collections.emptyList();
        this.status = status;
    }

    public void addStudents(List<String> students) {
        students.forEach(student -> {
            if(!this.students.contains(student))
                this.students.add(student);
        });
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

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }
}
