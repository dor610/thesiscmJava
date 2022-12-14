package com.nhk.thesis.repository;

import com.nhk.thesis.entity.IMark;
import com.nhk.thesis.entity.constant.IMarkStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IMarkRepository extends MongoRepository<IMark, String> {
    List<IMark> findAllByLecturer(String lecturer);

    List<IMark> findAllBySemester(String semester);

    IMark findByStudentCode(String studentCode);

    IMark findAllByStudentCodeAndSemester(String studentCode, String semester);

    List<IMark> findByLecturerAndStatus(String lecturer, IMarkStatus status);

    List<IMark> findAllByLecturerAndSemester(String lecturer, String semester);

    List<IMark> findByStatus(IMarkStatus status);

    List<IMark> findByConfirmAndLecturer(boolean confirm, String lecturer);

    List<IMark> findByConfirmAndStatusAndLecturer(boolean confirm, IMarkStatus status, String lecturer);

    List<IMark> findByCompleteAndConfirmAndStatusAndLecturer(boolean complete, boolean confirm, IMarkStatus status, String lecturer);

    List<IMark> findByStatusAndSemester(IMarkStatus status, String semester);
}
