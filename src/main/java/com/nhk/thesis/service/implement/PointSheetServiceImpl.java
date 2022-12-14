package com.nhk.thesis.service.implement;

import com.nhk.thesis.entity.Notification;
import com.nhk.thesis.entity.PointSheet;
import com.nhk.thesis.entity.Presentation;
import com.nhk.thesis.entity.constant.NotificationType;
import com.nhk.thesis.entity.vo.PointSheetVO;
import com.nhk.thesis.entity.vo.UserVO;
import com.nhk.thesis.repository.PointSheetRepository;
import com.nhk.thesis.repository.PresentationRepository;
import com.nhk.thesis.service.interfaces.NotificationService;
import com.nhk.thesis.service.interfaces.PointSheetService;
import com.nhk.thesis.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointSheetServiceImpl implements PointSheetService {

    private PointSheetRepository pointSheetRepository;
    private UserService userService;
    private NotificationService notificationService;
    private PresentationRepository presentationRepository;

    @Autowired
    public PointSheetServiceImpl(PointSheetRepository pointSheetRepository, UserService userService, NotificationService notificationService,
                                 PresentationRepository presentationRepository) {
        this.pointSheetRepository = pointSheetRepository;
        this.userService = userService;
        this.notificationService = notificationService;
        this.presentationRepository = presentationRepository;
    }

    @Override
    public boolean save(String aPoint, String bPoint, String cPoint, double aTotalPoint, double bTotalPoint,
                        double cTotalPoint, String sample, String presentation, String creator) {
        UserVO user = userService.getUserByAccount(creator);
        if(user != null){
            PointSheet pointSheet = pointSheetRepository.findByCreatorAndPresentation(creator, presentation);
            if(pointSheet == null) {
                pointSheet = new PointSheet(aPoint, bPoint, cPoint, aTotalPoint, bTotalPoint, cTotalPoint, sample, presentation, creator);
            } else {
                pointSheet.update(aPoint, bPoint, cPoint, aTotalPoint, bTotalPoint, cTotalPoint, sample, presentation, creator);
            }

            pointSheetRepository.save(pointSheet);
            return true;
        }
        return false;
    }

    @Override
    public boolean submit(String aPoint, String bPoint, String cPoint, double aTotalPoint, double bTotalPoint, double cTotalPoint, String sample, String presentation, String creator) {
        UserVO user = userService.getUserByAccount(creator);
        if(user != null){
            PointSheet pointSheet = pointSheetRepository.findByCreatorAndPresentation(creator, presentation);
            Notification notification = new Notification();
            if(pointSheet == null) {
                pointSheet = new PointSheet(aPoint, bPoint, cPoint, aTotalPoint, bTotalPoint, cTotalPoint, sample, presentation, creator);
                notification.setContent(user.getTitle()+". " +user.getName() +" đã xác nhận bản điểm.");
                notification.setType(NotificationType.POINT_APPROVE);
            } else {
                pointSheet.update(aPoint, bPoint, cPoint, aTotalPoint, bTotalPoint, cTotalPoint, sample, presentation, creator);
                notification.setContent(user.getTitle()+". " +user.getName() +" đã chỉnh sửa bản điểm.");
                notification.setType(NotificationType.POINT_RE_APPROVE);
            }
            pointSheet.setSubmitted(true);
            pointSheetRepository.save(pointSheet);
            Presentation p = presentationRepository.findById(presentation).orElse(null);
            if(presentation != null) {

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
        return false;
    }

    @Override
    public PointSheetVO getById(String id) {
        PointSheet pointSheet = pointSheetRepository.findById(id).orElse(null);
        if(pointSheet != null) {
            UserVO user = userService.getUser(pointSheet.getId());
            return new PointSheetVO(pointSheet, user);
        }

        return null;
    }

    @Override
    public List<PointSheetVO> getByPresentation(String presentation) {
        return pointSheetRepository.findAllByPresentation(presentation).stream().map(pointSheet -> {
            UserVO user = userService.getUserByAccount(pointSheet.getCreator());
            return new PointSheetVO(pointSheet, user);
        }).collect(Collectors.toList());
    }

    @Override
    public PointSheetVO getByCreatorAndPresentation(String creator, String presentation) {
        UserVO user = userService.getUserByAccount(creator);
        if(user != null) {
            PointSheet pointSheet = pointSheetRepository.findByCreatorAndPresentation(creator, presentation);
            if(pointSheet != null) {
                return new PointSheetVO(pointSheet, user);
            }
        }
        return null;
    }
}
