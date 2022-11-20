package com.nhk.thesis.repository;

import com.nhk.thesis.entity.Semester;
import com.nhk.thesis.entity.constant.SemesterName;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SemesterRepository extends MongoRepository<Semester, String> {

    boolean existsByStartYearAndEndYearAndSemesterName(String startYear, String endYear, SemesterName semesterName);

    List<Semester> findSemesterByStartYear(String startYear);

    List<Semester> findSemesterByEndYear(String endYear);

    Semester findSemesterByStartYearAndEndYearAndSemesterName(String startYear, String endYear, SemesterName semesterName);

    @Query(value = "{'endYear': {$lte: ?0}}" , sort = "{'endYear': -1}")
    List<Semester> findAllByEndYearLessThanEqual(String endYear);

    @Query(value = "{'startYear': {$gte: ?0}}" , sort = "{'startYear': 1}")
    List<Semester> findAllByStartYearGreaterThan(String startYear);
}
