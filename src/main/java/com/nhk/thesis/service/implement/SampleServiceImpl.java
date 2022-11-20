package com.nhk.thesis.service.implement;

import com.nhk.thesis.entity.Sample;
import com.nhk.thesis.repository.SampleRepository;
import com.nhk.thesis.service.interfaces.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class SampleServiceImpl implements SampleService {

    private SampleRepository sampleRepository;

    @Autowired
    public SampleServiceImpl(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    @Override
    public Sample findNewSample() {
        List<Sample> list = sampleRepository.findOrderByTimestamp();
        if(list.isEmpty())
            return null;
        else return list.get(0);
    }

    @Override
    public boolean importSample(MultipartFile file) {
        return false;
    }
}
