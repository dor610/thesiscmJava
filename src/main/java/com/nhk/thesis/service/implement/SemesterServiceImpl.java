package com.nhk.thesis.service.implement;

import com.nhk.thesis.entity.Semester;
import com.nhk.thesis.entity.constant.SemesterName;
import com.nhk.thesis.entity.vo.SemesterVO;
import com.nhk.thesis.repository.SemesterRepository;
import com.nhk.thesis.service.interfaces.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SemesterServiceImpl implements SemesterService {

    private SemesterRepository semesterRepository;

    @Autowired
    public SemesterServiceImpl(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    @Override
    public SemesterVO getSemester(String id) {
        Semester semester = semesterRepository.findById(id).orElse(null);
        if(semester != null){
            return new SemesterVO(semester);
        }
        return null;
    }

    @Override
    public SemesterVO getCurrentSemester() {
        Calendar calendar = Calendar.getInstance();
        long currentMiliSec = System.currentTimeMillis();
        calendar.setTime(new Date(currentMiliSec));
        String currentYear = calendar.get(Calendar.YEAR) + "";
        List<Semester> semesters = semesterRepository.findSemesterByEndYear(currentYear);
        semesters.addAll(semesterRepository.findSemesterByStartYear(currentYear));
        for (Semester semester: semesters) {
            if(currentMiliSec > Long.parseLong(semester.getStartDate()) && currentMiliSec < Long.parseLong(semester.getEndDate()))
                return new SemesterVO(semester);
        }

        return null;
    }

    @Override
    public SemesterVO getSemesterByStartYearAndEndYearAnSemester(String startYear, String endYear, SemesterName semesterName) {
        Semester semester = semesterRepository.findSemesterByStartYearAndEndYearAndSemesterName(startYear, endYear, semesterName);
        if(semester != null)
            return new SemesterVO(semester);
        return null;
    }

    @Override
    public List<SemesterVO> getAllSemester() {
        List<Semester> semesters = semesterRepository.findAll();

        return semesters.stream().map(SemesterVO::new).collect(Collectors.toList());
    }

    @Override
    public List<SemesterVO> getPassSemester() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        return semesterRepository.findAllByEndYearLessThanEqual(calendar.get(Calendar.YEAR)+"").stream()
                .map(SemesterVO::new).collect(Collectors.toList());
    }

    @Override
    public List<SemesterVO> getUpComingSemester() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        return semesterRepository.findAllByStartYearGreaterThan(calendar.get(Calendar.YEAR)+"").stream()
                .map(SemesterVO::new).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, String>> getAllSchoolYears() {
        Set<String> startYearSet = new HashSet<>();
        List<Semester> semesters = semesterRepository.findAll();
        List<Map<String, String>> maps = new ArrayList<>();
        semesters = semesters.stream().sorted(Comparator.comparing(Semester::getStartDate)).collect(Collectors.toList());
        for(int i = 0; i < semesters.size(); i++){
            Semester semester = semesters.get(i);
            if(!startYearSet.contains(semester.getStartYear())) {
                startYearSet.add(semester.getStartYear());
                Map<String, String> map = new LinkedHashMap<>();
                map.put("value", String.valueOf(i));
                map.put("label", semester.getStartYear() + " - " + semester.getEndYear());
                map.put("startYear", semester.getStartYear());
                map.put("endYear", semester.getEndYear());
                maps.add(map);
            }
        }
        return maps;
    }

    @Override
    public boolean createSemester(long startDate, long endDate, int numberOfWeek, String semesterCode) {
        Semester semester = new Semester(startDate, endDate, numberOfWeek, semesterCode);
        if(!validateSemester(semester.getStartYear(), semester.getEndYear(), semester.getSemesterName())
                && (Integer.parseInt(semester.getEndYear()) - Integer.parseInt(semester.getStartYear()) == 1)) {
            semesterRepository.save(semester);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateSemester(String id, String startDate, String endDate, int numberOfWeek) {
        return false;
    }

    @Override
    public boolean deleteSemester(String id) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        Semester semester = semesterRepository.findById(id).orElse(null);
        if (semester != null) {
            if(Integer.parseInt(semester.getStartYear()) > calendar.get(Calendar.YEAR)){
                semesterRepository.delete(semester);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean validateSemester(String startYear, String endYear, SemesterName semesterName) {
        return semesterRepository.existsByStartYearAndEndYearAndSemesterName(startYear, endYear, semesterName);
    }
}
