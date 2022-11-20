package com.nhk.thesis.restApi;

import com.nhk.thesis.entity.Sample;
import com.nhk.thesis.service.interfaces.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/sample")
@RestController
public class SampleApi {

    private SampleService sampleService;

    @Autowired
    public SampleApi(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @GetMapping("")
    public ResponseEntity<Object> getNewestSample() {
        Sample sample = sampleService.findNewSample();
        if(sample == null)
            return new ResponseEntity<>("Có lỗi đã xảy ra, vui lòng thử lại!", HttpStatus.INTERNAL_SERVER_ERROR);
        else return new ResponseEntity<>(sample, HttpStatus.OK);
    }
}
