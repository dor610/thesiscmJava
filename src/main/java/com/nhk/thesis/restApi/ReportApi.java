package com.nhk.thesis.restApi;

import com.nhk.thesis.entity.vo.ReportVO;
import com.nhk.thesis.service.interfaces.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
public class ReportApi {
    private ReportService reportService;

    @Autowired
    public ReportApi(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("")
    public ResponseEntity<Object> get(@RequestParam("id") String id) {
        return new ResponseEntity<>(reportService.get(id), HttpStatus.OK);
    }

    @GetMapping("/presentaion")
    public ResponseEntity<Object> getByAccountAndPresentation(@RequestParam("id") String id) {
        ReportVO report = reportService.getByPresentation(id);
        if (report == null) {
            return new ResponseEntity<>("Không tìm được dữ liệu", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @GetMapping("/mark")
    public ResponseEntity<Object> getStudentMark(@RequestParam("student") String student){
        return new ResponseEntity<>(reportService.getStudentMark(student), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestParam("presentation") String presentation, @RequestParam("account") String account,
                                       @RequestParam("qna") String qna, @RequestParam("advices") String advices, @RequestParam("comment") String comment,
                                       @RequestParam("result") String result, @RequestParam("finalPoint") String finalPoint, @RequestParam("endTime") String endTime,
                                       @RequestParam("other") String other, @RequestParam("student") String student) {
        return new ResponseEntity<>(reportService.save(presentation, account, student, qna, advices, comment, result, finalPoint, endTime, other), HttpStatus.OK);
    }
    @PostMapping("/submit")
    public ResponseEntity<Object> submit(@RequestParam("presentation") String presentation, @RequestParam("account") String account,
                                       @RequestParam("qna") String qna, @RequestParam("advices") String advices, @RequestParam("comment") String comment,
                                       @RequestParam("result") String result, @RequestParam("finalPoint") String finalPoint, @RequestParam("endTime") String endTime,
                                       @RequestParam("other") String other, @RequestParam("student") String student) {
        return new ResponseEntity<>(reportService.submit(presentation, account, student, qna, advices, comment, result, finalPoint, endTime, other), HttpStatus.OK);
    }

    @PostMapping("/approve")
    public ResponseEntity<Object> approve(@RequestParam("id") String id) {
        return new ResponseEntity<>(reportService.approve(id), HttpStatus.OK);
    }

}
