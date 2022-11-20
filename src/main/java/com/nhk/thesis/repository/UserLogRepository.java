package com.nhk.thesis.repository;

import com.nhk.thesis.entity.UserLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserLogRepository extends MongoRepository<UserLog, String> {

    public UserLog getUserLogById(String id);

    @Query(value = "{'userId': ?0}", sort = "{'timestamp': -1}")
    public List<UserLog> getUserLogsByUserId(String userId);
}
