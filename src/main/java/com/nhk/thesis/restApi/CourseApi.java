package com.nhk.thesis.restApi;

import com.nhk.thesis.entity.vo.CourseVO;
import com.nhk.thesis.service.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/course")
@RestController
public class CourseApi {

    private CourseService courseService;

    @Autowired
    public CourseApi(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("")
    public ResponseEntity<Object> getCourse(@RequestParam("id") String id) {
        CourseVO course = courseService.getCourse(id);
        if(course != null ){
            return new ResponseEntity<>(course, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/current")
    public ResponseEntity<Object> getCurrentCourses() {
        List<CourseVO> courses = courseService.getCurrentCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/student")
    public ResponseEntity<Object> getCoursesByStudent(@RequestParam("student") String student) {
        return new ResponseEntity<>(courseService.getCourseByStudent(student), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllCourses() {
        List<CourseVO> courses = courseService.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<Object> getCourseBySemester(@RequestParam("semester") String semester) {
        List<CourseVO> courses = courseService.getCoursesBySemester(semester);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/account")
    public ResponseEntity<Object> getCoursesByAccount(@RequestParam("account") String account) {
        return new ResponseEntity<>(courseService.getCourseByAccount(account), HttpStatus.OK);
    }

    @GetMapping("/account/current")
    public ResponseEntity<Object> getCurrentCourseByAccount(@RequestParam("account") String account) {
        CourseVO course = courseService.getCurrentCourseByAccount(account);
        if(course == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Object> importCourses(@RequestParam("file")MultipartFile file) throws IOException {
        Boolean result = courseService.importCourses(file.getInputStream());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> deleteCourse(@RequestParam("id") String id) {
        return new ResponseEntity<>(courseService.deleteCourse(id), HttpStatus.OK);
    }

    @GetMapping("/export")
    public ResponseEntity<Object> export(@RequestParam("id") String id){
        byte[] data = null;
        try{
            data = courseService.export(id);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDispositionFormData("file", "hahaha.xlsx");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setContentLength(data != null? data.length: 0);
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, "application/force-download");
        return new ResponseEntity<>(data, httpHeaders, HttpStatus.OK);
    }
}
