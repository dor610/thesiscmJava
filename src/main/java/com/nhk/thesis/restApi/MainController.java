package com.nhk.thesis.restApi;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.nhk.thesis.service.implement.MailServiceImpl;
import com.nhk.thesis.service.implement.TestImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class MainController {

    private TestImpl test;
    private MailServiceImpl mailService;

    @Autowired
    public MainController(TestImpl test, MailServiceImpl mailService) {
        this.test = test;
        this.mailService = mailService;
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> hi() throws UnirestException {
        Map<String, String> haha = new HashMap<>();
        haha.put("message", "Hello there!");
        mailService.sendSimpleMessage();
        return new ResponseEntity<>(haha, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, String>> hello(@RequestParam(name = "file")MultipartFile file) throws IOException, UnirestException {
        test.extractDataFromPDF(file.getInputStream());
        Map<String, String> haha = new HashMap<>();
        haha.put("message", "Hello there!");
        mailService.sendSimpleMessage();
        return new ResponseEntity<>(haha, HttpStatus.OK);
    }
}
