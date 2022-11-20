package com.nhk.thesis.service.interfaces;

import com.nhk.thesis.entity.vo.PointSheetVO;

import java.util.List;
import java.util.Map;

public interface PointSheetService {

    boolean save(String aPoint, String bPoint, String cPoint, double aTotalPoint, double bTotalPoint, double cTotalPoint,
              String sample, String presentation, String creator);

    boolean submit(String aPoint, String bPoint, String cPoint, double aTotalPoint, double bTotalPoint, double cTotalPoint,
                String sample, String presentation, String creator);

    PointSheetVO getById(String id);

    List<PointSheetVO> getByPresentation(String presentation);

    PointSheetVO getByCreatorAndPresentation(String creator, String presentation);
}
