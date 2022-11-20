package com.nhk.thesis.restApi;

import com.nhk.thesis.service.interfaces.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/semester")
@RestController
public class SemesterApi {

    private SemesterService semesterService;

    @Autowired
    public SemesterApi(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @GetMapping("")
    public ResponseEntity<Object> getSemester(@RequestParam("id") String id) {
        return new ResponseEntity<>(semesterService.getSemester(id), HttpStatus.OK);
    }

    @GetMapping("/current")
    public ResponseEntity<Object> getCurrentSemester() {
        return new ResponseEntity<>(semesterService.getCurrentSemester(), HttpStatus.OK);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<Object> getUpcomingSemester(){
        return new ResponseEntity<>(semesterService.getUpComingSemester(), HttpStatus.OK);
    }

    @GetMapping("/passed")
    public ResponseEntity<Object> getPassedSemester() {
        return new ResponseEntity<>(semesterService.getPassSemester(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Object> createSemester(@RequestParam("startDate") long startDate,
                                                 @RequestParam("endDate") long endDate,
                                                 @RequestParam("numberOfWeek") int numberOfWeek,
                                                 @RequestParam("code") String code) {
        if(semesterService.createSemester(startDate, endDate, numberOfWeek, code))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> deleteSemester(@RequestParam("id") String id) {
        if(semesterService.deleteSemester(id))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllSemester() {
        return new ResponseEntity<>(semesterService.getAllSemester(), HttpStatus.OK);
    }

    @GetMapping("/school-year")
    public ResponseEntity<Object> getAllSemesterValue() {
        return new ResponseEntity<>(semesterService.getAllSchoolYears(), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateSemester() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
