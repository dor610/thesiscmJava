package com.nhk.thesis.restApi;

import com.nhk.thesis.service.interfaces.PresentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@RequestMapping("/api/presentation")
@RestController
public class PresentationApi {

    private PresentationService presentationService;

    @Autowired
    public PresentationApi(PresentationService presentationService) {
        this.presentationService = presentationService;
    }

    @PostMapping("/import")
    public ResponseEntity<Object> importData(@RequestParam("file") MultipartFile file) throws IOException, ParseException {
        presentationService.importData(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Object> get(@RequestParam("id") String id) {
        return new ResponseEntity<>(presentationService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/topic")
    public ResponseEntity<Object> getByTopicId(@RequestParam("id") String id) {
        return new ResponseEntity<>(presentationService.getByTopicId(id), HttpStatus.OK);
    }

    @GetMapping("/semester/current")
    public ResponseEntity<Object> getByCurrentSemester(){
        return new ResponseEntity<>(presentationService.getByCurrentSemester(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll() {
        return new ResponseEntity<>(presentationService.getALl(), HttpStatus.OK);
    }

    @GetMapping("/account/current")
    public ResponseEntity<Object> getByAccountInCurrentSemester(@RequestParam("account") String account) {
        return new ResponseEntity(presentationService.getByAccountInCurrentSemester(account), HttpStatus.OK);
    }

    @GetMapping("/account")
    public ResponseEntity<Object> getByAccount(@RequestParam("account") String account) {
        return new ResponseEntity<>(presentationService.getAllByAccount(account), HttpStatus.OK);
    }

    @GetMapping("/log")
    public ResponseEntity<Object> getLogById(@RequestParam("id") String id) {
        return new ResponseEntity<>(presentationService.getAllLogById(id), HttpStatus.OK);
    }

    @PostMapping("/log")
    public ResponseEntity<Object> writelog(@RequestParam("id") String id, @RequestParam("content") String content) {
        return new ResponseEntity<>(presentationService.writeLog(id, content), HttpStatus.OK);
    }
}
