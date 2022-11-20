package com.nhk.thesis.repository;

import com.nhk.thesis.entity.PointSheet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PointSheetRepository extends MongoRepository<PointSheet, String> {
    PointSheet findByCreatorAndPresentation(String creator, String presentation);

    List<PointSheet> findAllByPresentation(String presentation);
}
