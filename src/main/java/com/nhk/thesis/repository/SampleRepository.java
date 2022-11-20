package com.nhk.thesis.repository;

import com.nhk.thesis.entity.Sample;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SampleRepository extends MongoRepository<Sample, String> {

    @Query(value = "{}", sort = "{'timestamp': -1}")
    List<Sample> findOrderByTimestamp();
}
