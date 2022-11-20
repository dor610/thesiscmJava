package com.nhk.thesis.repository;

import com.nhk.thesis.entity.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseRepository extends MongoRepository<Course, String> {

    List<Course> getCourseBySemester(String semester);

    List<Course> getCoursesByLecturer(String lecturer);

    Course getCourseByLecturerAndSemester(String lecturer, String semester);

    List<Course> getCourseByStudentsContains(String student);

}
