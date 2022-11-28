package com.nhk.thesis.service.implement;

import com.nhk.thesis.entity.IMark;
import com.nhk.thesis.entity.Presentation;
import com.nhk.thesis.entity.Report;
import com.nhk.thesis.entity.Student;
import com.nhk.thesis.entity.constant.IMarkStatus;
import com.nhk.thesis.entity.constant.PresentationStatus;
import com.nhk.thesis.entity.vo.ReportVO;
import com.nhk.thesis.entity.vo.UserVO;
import com.nhk.thesis.repository.IMarkRepository;
import com.nhk.thesis.repository.PresentationRepository;
import com.nhk.thesis.repository.ReportRepository;
import com.nhk.thesis.repository.StudentRepository;
import com.nhk.thesis.service.interfaces.IMarkService;
import com.nhk.thesis.service.interfaces.ReportService;
import com.nhk.thesis.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    private ReportRepository reportRepository;

    private StudentRepository studentRepository;

    private UserService userService;

    private IMarkRepository iMarkRepository;

    private PresentationRepository presentationRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, UserService userService, PresentationRepository presentationRepository,
                             IMarkRepository iMarkRepository, StudentRepository studentRepository) {
        this.reportRepository = reportRepository;
        this.presentationRepository = presentationRepository;
        this.userService = userService;
        this.iMarkRepository = iMarkRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public ReportVO get(String id) {
        Report report = reportRepository.findById(id).orElse(null);
        if(report != null) {
            UserVO user = userService.getUser(report.getCreator());
            return new ReportVO(report, user);
        }
        return null;
    }

    @Override
    public ReportVO getByPresentation(String id) {
        Report report = reportRepository.findByPresentation(id);
        if(report != null) {
            UserVO user = userService.getUser(report.getCreator());
            return new ReportVO(report, user);
        }
        return null;
    }

    @Override
    public Map<String, String> getStudentMark(String studentCode) {
        Report report = reportRepository.findByStudent(studentCode);
        Map<String, String> mark = new HashMap<>();
        mark.put("number", "");
        mark.put("letter", "");
        if(report == null) {
            Student student = studentRepository.findByStudentCode(studentCode);
            IMark iMark = iMarkRepository.findByStudentCode(student.getId());
            if(iMark != null && iMark.getStatus().equals(IMarkStatus.NEW)){
                mark.put("number", "-1.0");
                mark.put("letter", "I");
            }
        } else {
            mark.put("number", report.getFinalPoint());
            mark.put("letter", report.getLetterPoint());
        }
        return mark;
    }

    @Override
    public boolean save(String presentation, String account, String student, String qna, String advices, String comment, String result, String finalPoint, String endTime, String other) {
        UserVO user = userService.getUserByAccount(account);
        Report report = reportRepository.findByPresentationAndCreator(presentation, user.getId());
        if(report == null) {
            report = new Report(user.getId(), student, presentation, qna, advices, comment, result, finalPoint, endTime, other);
        } else {
            report.setQna(qna);
            report.setAdvices(advices);
            report.setComment(comment);
            report.setResult(result);
            report.setFinalPoint(finalPoint);
            report.setEndTime(endTime);
            report.setOther(other);
        }

        reportRepository.save(report);

        return true;
    }

    @Override
    public boolean submit(String presentation, String account, String student, String qna, String advices, String comment, String result, String finalPoint, String endTime, String other) {
        UserVO user = userService.getUserByAccount(account);
        Report report = reportRepository.findByPresentationAndCreator(presentation, user.getId());
        if(report == null) {
            report = new Report(user.getId(), student, presentation, qna, advices, comment, result, finalPoint, endTime, other);
        } else {
            report.setQna(qna);
            report.setAdvices(advices);
            report.setComment(comment);
            report.setResult(result);
            report.setFinalPoint(finalPoint);
            report.setEndTime(endTime);
            report.setOther(other);
        }
        report.setSubmitted(true);
        reportRepository.save(report);

        return true;
    }

    @Override
    public boolean approve(String id) {
        Report report = reportRepository.findById(id).orElse(null);
        if(report != null) {
            Presentation presentation = presentationRepository.findById(report.getPresentation()).orElse(null);
            if(presentation != null) {
                presentation.setStatus(PresentationStatus.FINISHED);
                presentationRepository.save(presentation);
            }
            report.setApproved(true);
            reportRepository.save(report);
            return true;
        }
        return false;
    }
}
