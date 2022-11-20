package com.nhk.thesis.repository;

import com.nhk.thesis.entity.Presentation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PresentationRepository extends MongoRepository<Presentation, String> {

    List<Presentation> findAllByPresidentOrSecretaryOrMember(String president, String secretary, String member);

    List<Presentation> findAllByPresidentOrSecretaryOrMemberAndSemester(String president, String secretary, String member, String semester);

    List<Presentation> findByTopic(String topic);

    List<Presentation> findAllBySemester(String semester);

    List<Presentation> findAllByStudent(String student);

    List<Presentation> findAllByStudentAndSemester(String student, String semester);

    List<Presentation> findAllByLecturer(String lecturer);

    List<Presentation> findAllByLecturerAndSemester(String lecturer, String semester);
}
