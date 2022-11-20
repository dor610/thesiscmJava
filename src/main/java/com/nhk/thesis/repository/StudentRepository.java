package com.nhk.thesis.repository;

import com.nhk.thesis.entity.Student;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student, String> {

    boolean existsStudentByStudentCode(String studentCode);

    Student findByStudentCode(String studentCode);

    @Aggregation({"{ $search: { index: 'searchStudent', text: {query: ?0, path: { 'wildcard': '*' }}}}", "{$project: {id: 1, studentCode: 1, name: 1}}"})
    List<Student> search(String keyword);
}
