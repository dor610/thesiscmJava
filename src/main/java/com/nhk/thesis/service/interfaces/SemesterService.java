package com.nhk.thesis.service.interfaces;

import com.nhk.thesis.entity.Semester;
import com.nhk.thesis.entity.constant.SemesterName;
import com.nhk.thesis.entity.vo.SemesterVO;

import java.util.List;
import java.util.Map;

public interface SemesterService {

    SemesterVO getSemester(String id);

    SemesterVO getCurrentSemester();

    SemesterVO getSemesterByStartYearAndEndYearAnSemester(String startYear, String endYear, SemesterName semesterName);

    List<SemesterVO> getAllSemester();

    List<SemesterVO> getPassSemester();

    List<SemesterVO> getUpComingSemester();

    List<Map<String, String>> getAllSchoolYears();

    boolean createSemester(long startDate, long endDate, int numberOfWeek, String semesterCode);

    boolean updateSemester(String id, String startDate, String endDate, int numberOfWeek);

    boolean deleteSemester(String id);

    boolean validateSemester(String startYear, String endYear, SemesterName semesterName);
}
