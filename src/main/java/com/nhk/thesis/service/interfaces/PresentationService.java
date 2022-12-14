package com.nhk.thesis.service.interfaces;

import com.nhk.thesis.entity.PresentaionLog;
import com.nhk.thesis.entity.Presentation;
import com.nhk.thesis.entity.constant.PresentationStatus;
import com.nhk.thesis.entity.vo.PresentationVO;
import com.nhk.thesis.entity.vo.SemesterVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface PresentationService {
    boolean create();

    boolean delete();

    List<PresentationVO> getByStudent(String student);

    PresentationVO getByStudentAndSemester(String student, String semester);

    List<PresentationVO> getBySemester(String semester);

    List<PresentationVO> getByAccountInCurrentSemester(String account);

    List<PresentationVO> getAllByAccount(String account);

    List<PresentationVO> getByCurrentSemester();

    List<PresentationVO> getByTopicId(String topic);

    PresentationVO getById(String id);

    List<PresentationVO> getALl();

    List<PresentationVO> getByStatus(PresentationStatus status);

    void importData(MultipartFile file) throws IOException, ParseException;

    void start(String presentation);


    void processData(List<String> data, String dateAndPlace, SemesterVO semester) throws ParseException;

    String processEnName(List<String> lst);

    String getLecturerAndTime(List<String> lst);

    boolean startPresentation(String id);

    boolean writeLog(String id, String content);

    List<PresentaionLog> getAllLogById(String id);
}
