package com.nhk.thesis.service.implement;

import com.nhk.thesis.entity.*;
import com.nhk.thesis.entity.constant.IMarkStatus;
import com.nhk.thesis.entity.constant.NotificationType;
import com.nhk.thesis.entity.constant.PresentationStatus;
import com.nhk.thesis.entity.vo.ReportVO;
import com.nhk.thesis.entity.vo.UserVO;
import com.nhk.thesis.repository.IMarkRepository;
import com.nhk.thesis.repository.PresentationRepository;
import com.nhk.thesis.repository.ReportRepository;
import com.nhk.thesis.repository.StudentRepository;
import com.nhk.thesis.service.interfaces.IMarkService;
import com.nhk.thesis.service.interfaces.NotificationService;
import com.nhk.thesis.service.interfaces.ReportService;
import com.nhk.thesis.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private ReportRepository reportRepository;

    private StudentRepository studentRepository;

    private UserService userService;

    private IMarkRepository iMarkRepository;

    private PresentationRepository presentationRepository;

    private NotificationService notificationService;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, UserService userService, PresentationRepository presentationRepository,
                             IMarkRepository iMarkRepository, StudentRepository studentRepository, NotificationService notificationService) {
        this.reportRepository = reportRepository;
        this.presentationRepository = presentationRepository;
        this.userService = userService;
        this.iMarkRepository = iMarkRepository;
        this.studentRepository = studentRepository;
        this.notificationService = notificationService;
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
    public Map<String, String> getStudentMark(String studentCode, String semester) {
        Student student = studentRepository.findByStudentCode(studentCode);
        Presentation presentation = presentationRepository.findByStudentAndSemester(student.getId(), semester);
        Report report = null;
        if(presentation != null)
            report = reportRepository.findByStudentAndPresentation(student.getId(), presentation.getId());
        Map<String, String> mark = new HashMap<>();
        mark.put("number", "");
        mark.put("letter", "");
        if(report == null) {
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
    public Map<String, Integer> getByLecturerAndSemester(String account, String semester) {
        UserVO user = userService.getUserByAccount(account);
        List<Presentation> presentations = presentationRepository.findAllByLecturerAndSemester(user.getId(), semester);
        Map<String, Integer> map = new HashMap<>();
        map.put("F", 0);
        map.put("D", 0);
        map.put("D+", 0);
        map.put("C", 0);
        map.put("C+", 0);
        map.put("B", 0);
        map.put("B+", 0);
        map.put("A", 0);
        for(Presentation presentation: presentations){
            Report report = reportRepository.findByPresentation(presentation.getId());
            if(report != null && report.isApproved()){
                if(report.getLetterPoint().equals("F")){
                    increaseDataInMap(map, "F");
                }
                if(report.getLetterPoint().equals("D")){
                    increaseDataInMap(map, "D");
                }
                if(report.getLetterPoint().equals("D+")){
                    increaseDataInMap(map, "D+");
                }
                if(report.getLetterPoint().equals("C")){
                    increaseDataInMap(map, "C");
                }
                if(report.getLetterPoint().equals("C+")){
                    increaseDataInMap(map, "C+");
                }
                if(report.getLetterPoint().equals("B")){
                    increaseDataInMap(map, "B");
                }
                if(report.getLetterPoint().equals("B+")){
                    increaseDataInMap(map, "B+");
                }
                if(report.getLetterPoint().equals("A")){
                    increaseDataInMap(map, "A");
                }
            }
        }
        return map;
    }

    @Override
    public Map<String, Integer> getBySemester(String semester) {
        List<Presentation> presentations = presentationRepository.findAllBySemester(semester);
        Map<String, Integer> map = new HashMap<>();
        map.put("F", 0);
        map.put("D", 0);
        map.put("D+", 0);
        map.put("C", 0);
        map.put("C+", 0);
        map.put("B", 0);
        map.put("B+", 0);
        map.put("A", 0);
        for(Presentation presentation: presentations){
            Report report = reportRepository.findByPresentation(presentation.getId());
            if(report != null && report.isApproved()){
                if(report.getLetterPoint().equals("F")){
                    increaseDataInMap(map, "F");
                }
                if(report.getLetterPoint().equals("D")){
                    increaseDataInMap(map, "D");
                }
                if(report.getLetterPoint().equals("D+")){
                    increaseDataInMap(map, "D+");
                }
                if(report.getLetterPoint().equals("C")){
                    increaseDataInMap(map, "C");
                }
                if(report.getLetterPoint().equals("C+")){
                    increaseDataInMap(map, "C+");
                }
                if(report.getLetterPoint().equals("B")){
                    increaseDataInMap(map, "B");
                }
                if(report.getLetterPoint().equals("B+")){
                    increaseDataInMap(map, "B+");
                }
                if(report.getLetterPoint().equals("A")){
                    increaseDataInMap(map, "A");
                }
            }
        }
        return map;
    }

    private void increaseDataInMap(Map<String, Integer> map, String key) {
            if(map.get(key) != null){
                map.put(key, map.get(key) + 1);
            } else {
                map.put(key, 1);
            }
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
        Notification notification = new Notification();
        if(report == null) {
            report = new Report(user.getId(), student, presentation, qna, advices, comment, result, finalPoint, endTime, other);
            notification.setContent(user.getTitle()+". " +user.getName()+" đã hoàn thành biên bản báo cáo.");
            notification.setType(NotificationType.REPORT_SEND);
        } else {
            report.setQna(qna);
            report.setAdvices(advices);
            report.setComment(comment);
            report.setResult(result);
            report.setFinalPoint(finalPoint);
            report.setEndTime(endTime);
            report.setOther(other);
            notification.setContent(user.getTitle()+". " +user.getName()+" đã chỉnh sửa biên bản báo cáo.");
            notification.setType(NotificationType.REPORT_CHANGE);
        }
        report.setSubmitted(true);
        reportRepository.save(report);
        Presentation p = presentationRepository.findById(presentation).orElse(null);
        if(p != null) {
            if(!p.getPresident().equals(user.getId())){
                UserVO president = userService.getUser(p.getPresident());
                notification.setRecipient(president.getAccount());
                notificationService.sendNotification(notification);
            }
            if(!p.getSecretary().equals(user.getId())){
                UserVO secretary = userService.getUser(p.getSecretary());
                notification.setRecipient(secretary.getAccount());
                notificationService.sendNotification(notification);
            }
            if(!p.getMember().equals(user.getId())){
                UserVO member = userService.getUser(p.getMember());
                notification.setRecipient(member.getAccount());
                notificationService.sendNotification(notification);
            }
        }
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
            UserVO userVO = userService.getUser(presentation.getPresident());
            Notification notification = new Notification(userVO.getTitle() +". " + userVO.getName() + " đã xác nhận báo cáo, kết thúc buổi báo cáo.",NotificationType.REPORT_APPROVE);
            UserVO secretary = userService.getUser(presentation.getSecretary());
            notification.setRecipient(secretary.getAccount());
            notificationService.sendNotification(notification);
            UserVO member = userService.getUser(presentation.getMember());
            notification.setRecipient(member.getAccount());
            notificationService.sendNotification(notification);
            return true;
        }
        return false;
    }
}
