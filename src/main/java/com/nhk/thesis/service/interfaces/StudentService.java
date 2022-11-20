package com.nhk.thesis.service.interfaces;

import com.nhk.thesis.entity.Semester;
import com.nhk.thesis.entity.Student;
import com.nhk.thesis.entity.vo.StudentVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

public interface StudentService {

    StudentVO getStudentById(String id);

    StudentVO getStudent(String studentCode);

    List<StudentVO> getStudents(List<String> studentCodes);

    List<StudentVO> getStudentsOfCurrentSemester(String account);

    Student addStudent(String studentCode, String name, String date, String classCode) throws ParseException;

    void importStudent(MultipartFile file, String account) throws IOException, ParseException;

    List<Student> extractDataFromPDF(InputStream inputFile) throws IOException, ParseException;

    List<Student> search(String keyword);
}
