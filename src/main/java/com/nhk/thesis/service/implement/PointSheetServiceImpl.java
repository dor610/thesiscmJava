package com.nhk.thesis.service.implement;

import com.nhk.thesis.entity.PointSheet;
import com.nhk.thesis.entity.vo.PointSheetVO;
import com.nhk.thesis.entity.vo.UserVO;
import com.nhk.thesis.repository.PointSheetRepository;
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

    @Autowired
    public PointSheetServiceImpl(PointSheetRepository pointSheetRepository, UserService userService) {
        this.pointSheetRepository = pointSheetRepository;
        this.userService = userService;
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
            if(pointSheet == null) {
                pointSheet = new PointSheet(aPoint, bPoint, cPoint, aTotalPoint, bTotalPoint, cTotalPoint, sample, presentation, creator);
            } else {
                pointSheet.update(aPoint, bPoint, cPoint, aTotalPoint, bTotalPoint, cTotalPoint, sample, presentation, creator);
            }
            pointSheet.setSubmitted(true);
            pointSheetRepository.save(pointSheet);
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
