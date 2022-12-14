package com.nhk.thesis.restApi;

import com.dropbox.core.DbxException;
import com.nhk.thesis.entity.vo.TopicVO;
import com.nhk.thesis.service.interfaces.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@RequestMapping("/api/topic")
@RestController
public class TopicApi {
    private TopicService topicService;

    @Autowired
    public TopicApi(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("")
    public ResponseEntity<Object> getTopic(@RequestParam("id") String id) {
        TopicVO topic = topicService.getTopic(id);
        if(topic != null)
            return new ResponseEntity<>(topic, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllTopic() {
        return new ResponseEntity<>(topicService.getAllTopics(), HttpStatus.OK);
    }

    @GetMapping("/student")
    public ResponseEntity<Object> getAllByStudent(@RequestParam("student") String student) {
        return new ResponseEntity<>(topicService.getTopicByStudent(student), HttpStatus.OK);
    }

    @GetMapping("/currentSemester")
    public ResponseEntity<Object> getAllInCurrentSemester() {
        return new ResponseEntity<>(topicService.getTopicInCurrentSemester(), HttpStatus.OK);
    }

    @GetMapping("/currentSemesterByAccount")
    public ResponseEntity<Object> getAllTopicsInCurrentSemesterByAccount(@RequestParam("account") String account) {
        return new ResponseEntity<>(topicService.getTopicInCurrentSemesterByAccount(account), HttpStatus.OK);
    }

    @GetMapping("/getByAccount")
    public ResponseEntity<Object> getAllTopicsByAccount(@RequestParam("account") String account) {
        return new ResponseEntity<>(topicService.getTopicByAccount(account), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Object> create(@RequestParam("account") String account, @RequestParam("name") String name,
                                         @RequestParam("enName") String enName, @RequestParam("type") String type,
                                         @RequestParam("member") String member) {
        return new ResponseEntity<>(topicService.createTopic(name, enName, type, member, account), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestParam("id") String id, @RequestParam("name") String name,
                                         @RequestParam("enName") String enName, @RequestParam("type") String type,
                                         @RequestParam("member") String member) {
        return new ResponseEntity<>(topicService.updateTopic(id, name, enName, type, member), HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> delete(@RequestParam("id") String id) {
        return new ResponseEntity<>(topicService.deleteTopic(id), HttpStatus.OK);
    }

    @GetMapping("/document")
    public ResponseEntity<Object> getDocument(@RequestParam("id") String id) {
        return new ResponseEntity<>(topicService.getTopicDocument(id), HttpStatus.OK);
    }

    @PostMapping("/document/sourceCode")
    public ResponseEntity<Object> uploadSourceCode(@RequestParam("file")MultipartFile file, @RequestParam("id") String id) throws IOException, DbxException {
        return new ResponseEntity<>(topicService.setTopicSourceCode(file, id), HttpStatus.OK);
    }

    @PostMapping("/document/reportFile")
    public ResponseEntity<Object> uploadReportFile(@RequestParam("file") MultipartFile file, @RequestParam("id") String id) throws IOException, DbxException {
        return new ResponseEntity<>(topicService.setTopicReportFile(file, id), HttpStatus.OK);
    }

    @PostMapping("/document/deleteSourceCode")
    public ResponseEntity<Object> deleteSourceCode(@RequestParam("id") String id) throws DbxException {
        return new ResponseEntity<>(topicService.deleteSourceFile(id), HttpStatus.OK);
    }

    @PostMapping("/document/deleteReportFile")
    public ResponseEntity<Object> deleteReportFile(@RequestParam("id") String id) throws DbxException {
        return new ResponseEntity<>(topicService.deleteReportFile(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam("keyWord") String keyWord) {
        return new ResponseEntity<>(topicService.search(keyWord), HttpStatus.OK);
    }

    @GetMapping("/test")
    public void test(){
        topicService.test();
    }

}
