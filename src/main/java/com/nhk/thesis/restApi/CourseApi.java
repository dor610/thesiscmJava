package com.nhk.thesis.restApi;

import com.dropbox.core.DbxException;
import com.nhk.thesis.entity.constant.IMarkStatus;
import com.nhk.thesis.entity.vo.CourseVO;
import com.nhk.thesis.service.interfaces.CourseService;
import com.nhk.thesis.service.interfaces.IMarkService;
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
    private IMarkService iMarkService;

    @Autowired
    public CourseApi(CourseService courseService, IMarkService iMarkService) {
        this.iMarkService = iMarkService;
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

    @GetMapping("/count")
    public ResponseEntity<Object> getNumberOfStudent(@RequestParam("semester") String semester){
        return new ResponseEntity<>(courseService.countCourseAndStudent(semester), HttpStatus.OK);
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
        httpHeaders.setContentDispositionFormData("file", "diem_ct594.xlsx");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setContentLength(data != null? data.length: 0);
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, "application/force-download");
        return new ResponseEntity<>(data, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/imark")
    public ResponseEntity<Object> getIMark(@RequestParam("id") String id){
        return new ResponseEntity<>(iMarkService.get(id), HttpStatus.OK);
    }

    @GetMapping("/imark/status/lecturer")
    public ResponseEntity<Object> getImarkByStatusAndLecturer(@RequestParam("status") String status, @RequestParam("lecturer") String lecturer){
        if (status.equals("1"))
            return new ResponseEntity<>(iMarkService.getByLecturerAndStatus(lecturer, IMarkStatus.NEW), HttpStatus.OK);
        if (status.equals("2"))
            return new ResponseEntity<>(iMarkService.getByLecturerAndStatus(lecturer, IMarkStatus.PENDING), HttpStatus.OK);
        if (status.equals("3"))
            return new ResponseEntity<>(iMarkService.getByLecturerAndStatus(lecturer, IMarkStatus.EXPIRED), HttpStatus.OK);
        return new ResponseEntity<>("Sai tham số", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/imark/status/semester")
    public ResponseEntity<Object> getImarkByStatusAndSemester(@RequestParam("status") String status, @RequestParam("semester") String semester){
        if (status.equals("1"))
            return new ResponseEntity<>(iMarkService.getByStatusAndSemester(IMarkStatus.NEW, semester), HttpStatus.OK);
        if (status.equals("2"))
            return new ResponseEntity<>(iMarkService.getByStatusAndSemester(IMarkStatus.PENDING, semester), HttpStatus.OK);
        if (status.equals("3"))
            return new ResponseEntity<>(iMarkService.getByStatusAndSemester(IMarkStatus.EXPIRED, semester), HttpStatus.OK);
        return new ResponseEntity<>("Sai tham số", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/imark/student")
    public ResponseEntity<Object> getStudentWithoutIMark(@RequestParam("account") String account) {
        return new ResponseEntity<>(iMarkService.getStudentWithoutIMark(account), HttpStatus.OK);
    }

    @PostMapping("/imark")
    public ResponseEntity<Object> createIMark(@RequestParam("studentCode") String studentCode, @RequestParam("lecturer") String lecturer,
                                              @RequestParam("file") MultipartFile file,@RequestParam("lecturerComment") String lecturerComment,
                                              @RequestParam("deanComment") String deanComment,@RequestParam("reason") String reason,
                                              @RequestParam("other") String other) throws IOException, DbxException {
        return new ResponseEntity<>(iMarkService.create(studentCode, lecturer, file, lecturerComment, deanComment, reason, other), HttpStatus.OK);
    }

    @GetMapping("/imark/confirm")
    public ResponseEntity<Object> getConfirmIMark(@RequestParam("lecturer") String lecturer){
        return new ResponseEntity<>(iMarkService.getByCompleteAndConfirmAndStatusAndLecturer(false, true, IMarkStatus.PENDING, lecturer), HttpStatus.OK);
    }

    @GetMapping("/imark/confirm/status")
    public ResponseEntity<Object> getConfirmIMarkByStatus(@RequestParam("lecturer") String lecturer){
        return new ResponseEntity<>(iMarkService.getByCompleteAndConfirmAndStatusAndLecturer(false,false, IMarkStatus.PENDING, lecturer), HttpStatus.OK);
    }

    @PostMapping("/imark/confirm")
    public ResponseEntity<Object> confirmIMark(@RequestParam("id") String id) {
        iMarkService.confirm(id);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("/imark/update")
    public ResponseEntity<Object> updateIMark(@RequestParam("id") String id, @RequestParam(name = "file", required = false) MultipartFile file,
                                              @RequestParam("lecturerComment") String lecturerComment, @RequestParam("deanComment") String deanComment,
                                              @RequestParam("reason") String reason, @RequestParam("other") String other) throws IOException, DbxException {
        return new ResponseEntity<>(iMarkService.update(id, file, lecturerComment, deanComment, reason, other), HttpStatus.OK);
    }

    @PostMapping("/imark/delete")
    public ResponseEntity<Object> deleteIMark(@RequestParam("id") String id) throws DbxException {
        return new ResponseEntity<>(iMarkService.delete(id), HttpStatus.OK);
    }

    @GetMapping("/imark/lecturer")
    public ResponseEntity<Object> getIMarkByLecturer(@RequestParam("lecturer") String lecturer){
        return new ResponseEntity<>(iMarkService.getByLecturer(lecturer), HttpStatus.OK);
    }


    @GetMapping("/imark/user-semester")
    public ResponseEntity<Object> countIMarkByUserAndSemester(@RequestParam("account") String account, @RequestParam("semester") String semester){
        return new ResponseEntity<>(iMarkService.getCountIMarkByUserAndSemester(account, semester), HttpStatus.OK);
    }

    @GetMapping("/imark/count/semester")
    public ResponseEntity<Object> countIMarkBySemester(@RequestParam("semester") String semester) {
        return new ResponseEntity<>(iMarkService.getCountIMarkBySemester(semester), HttpStatus.OK);
    }

    @GetMapping("/imark/semester")
    public ResponseEntity<Object> getIMarkBySemester(@RequestParam("semester") String semester){
        return new ResponseEntity<>(iMarkService.getBySemester(semester), HttpStatus.OK);
    }

    @GetMapping("/imark/all")
    public ResponseEntity<Object> getAllIMark() {
        return new ResponseEntity<>(iMarkService.getAll(), HttpStatus.OK);
    }
}
