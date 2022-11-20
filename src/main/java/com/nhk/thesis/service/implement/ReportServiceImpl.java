package com.nhk.thesis.service.implement;

import com.nhk.thesis.entity.Report;
import com.nhk.thesis.entity.vo.ReportVO;
import com.nhk.thesis.entity.vo.UserVO;
import com.nhk.thesis.repository.ReportRepository;
import com.nhk.thesis.service.interfaces.ReportService;
import com.nhk.thesis.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private ReportRepository reportRepository;

    private UserService userService;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, UserService userService) {
        this.reportRepository = reportRepository;
        this.userService = userService;
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
    public boolean save(String presentation, String account, String qna, String advices, String comment, String result, String finalPoint, String endTime, String other) {
        UserVO user = userService.getUserByAccount(account);
        Report report = reportRepository.findByPresentationAndCreator(presentation, user.getId());
        if(report == null) {
            report = new Report(user.getId(), presentation, qna, advices, comment, result, finalPoint, endTime, other);
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
    public boolean submit(String presentation, String account, String qna, String advices, String comment, String result, String finalPoint, String endTime, String other) {
        UserVO user = userService.getUserByAccount(account);
        Report report = reportRepository.findByPresentationAndCreator(presentation, user.getId());
        if(report == null) {
            report = new Report(user.getId(), presentation, qna, advices, comment, result, finalPoint, endTime, other);
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
            report.setApproved(true);
            reportRepository.save(report);
            return true;
        }
        return false;
    }
}
