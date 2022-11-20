package com.nhk.thesis.repository;

import com.nhk.thesis.entity.IMark;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IMarkRepository extends MongoRepository<IMark, String> {

}
