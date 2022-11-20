package com.nhk.thesis.service.interfaces;

import com.nhk.thesis.entity.Report;
import com.nhk.thesis.entity.vo.ReportVO;

public interface ReportService {

    ReportVO get(String id);

    ReportVO getByPresentation(String id);

    boolean save(String presentation, String account, String qna, String advices, String comment, String result, String finalPoint, String endTime, String other);

    boolean submit(String presentation, String account, String qna, String advices, String comment, String result, String finalPoint, String endTime, String other);

    boolean approve(String id);
}
