package com.nhk.thesis.restApi;

import com.nhk.thesis.entity.vo.StudentVO;
import com.nhk.thesis.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@RequestMapping("/api/student")
@RestController
public class StudentApi {

    private StudentService studentService;

    @Autowired
    public StudentApi(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/import")
    public ResponseEntity<Object> importStudents(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("account") String id) throws IOException, ParseException {
        studentService.importStudent(file, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/current")
    public ResponseEntity<Object> getStudentsOfCurrentSemester(@RequestParam("account") String account) {
        return new ResponseEntity<>(studentService.getStudentsOfCurrentSemester(account), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Object> getStudent(@RequestParam("id") String id){
        StudentVO student = studentService.getStudentById(id);
        if(student == null) {
            return new ResponseEntity<>("Không tồn tại dữ liệu về sinh viên", HttpStatus.BAD_REQUEST);
        }

        return  new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam("keyWord") String keyWord) {
        return new ResponseEntity<>(studentService.search(keyWord), HttpStatus.OK);
    }
}
