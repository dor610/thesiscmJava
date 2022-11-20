package com.nhk.thesis.service.interfaces;

import com.nhk.thesis.entity.Sample;
import org.springframework.web.multipart.MultipartFile;

public interface SampleService {
    Sample findNewSample();

    boolean importSample(MultipartFile file);
}
