package com.nhk.thesis.repository;

import com.nhk.thesis.entity.PresentaionLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PresentationLogRepository extends MongoRepository<PresentaionLog, String> {

    @Query(value = "{'presentation': ?0}", sort = "{'timestamp': -1}")
    List<PresentaionLog> getAllByPresentation(String presentation);
}
