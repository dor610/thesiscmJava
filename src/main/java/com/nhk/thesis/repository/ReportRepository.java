package com.nhk.thesis.repository;

import com.nhk.thesis.entity.Report;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ReportRepository extends MongoRepository<Report, String> {

    Report findByPresentationAndCreator(String presentation, String creator);

    Report findByPresentation(String presentation);

    Report findByStudentAndPresentation(String student, String presentation);

    Report findByStudent(String student);
}
