package com.nhk.thesis.entity.vo;

import com.nhk.thesis.entity.Semester;
import com.nhk.thesis.entity.constant.SemesterName;

public class SemesterVO {
    private String id;

    private String startDate;
    private String endDate;
    private String startYear;
    private String endYear;
    private int numberOfWeek;
    private String semesterName;
    private String semesterCode;

    public SemesterVO () {

    }

    public SemesterVO(String id, String startDate, String endDate, String startYear, String endYear, int numberOfWeeks, String semesterName, String semesterCode) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startYear = startYear;
        this.endYear = endYear;
        this.numberOfWeek = numberOfWeeks;
        this.semesterName = semesterName;
        this.semesterCode = semesterCode;
    }

    public SemesterVO(Semester semester) {
        this.id = semester.getId();
        this.endDate = semester.getEndDate();
        this.startDate = semester.getStartDate();
        this.startYear = semester.getStartYear();
        this.endYear = semester.getEndYear();
        this.numberOfWeek = semester.getNumberOfWeeks();
        this.semesterName = semester.getSemesterName().getText();
        this.semesterCode = semester.getSemesterName().getCode();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public int getNumberOfWeek() {
        return numberOfWeek;
    }

    public void setNumberOfWeek(int numberOfWeeks) {
        this.numberOfWeek = numberOfWeeks;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public String getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(String semesterCode) {
        this.semesterCode = semesterCode;
    }
}
