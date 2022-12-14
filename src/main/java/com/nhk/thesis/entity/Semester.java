package com.nhk.thesis.entity;

import com.nhk.thesis.entity.constant.SemesterName;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Calendar;
import java.util.Date;

@Document(collection = "semester")
public class Semester {
    @Id
    private String id;

    private String startDate;
    private String endDate;
    private String startYear;
    private String endYear;
    private int numberOfWeeks;
    private SemesterName semesterName;

    public Semester() {
    }

    public Semester(long startDate, long endDate, int numberOfWeeks, String semesterCode) {
        ObjectId id = ObjectId.get();
        this.id = "semester_" + id;
        this.startDate = startDate + "";
        this.endDate = endDate + "";
        this.numberOfWeeks = numberOfWeeks;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(startDate));
        this.startYear = calendar.get(Calendar.YEAR) + "";
        calendar.setTime(new Date(endDate));
        this.endYear = calendar.get(Calendar.YEAR) + "";
        this.semesterName = SemesterName.getSemesterByCode(semesterCode);
        if(Integer.parseInt(this.endYear) == Integer.parseInt(this.startYear)){
            if(this.semesterName.equals(SemesterName.FIRST_SEMESTER)){
                this.endYear = String.valueOf(Integer.parseInt(this.endYear) + 1);
            }else {
                this.startYear = String.valueOf(Integer.parseInt(this.startYear) - 1);
            }
        }
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

    public int getNumberOfWeeks() {
        return numberOfWeeks;
    }

    public void setNumberOfWeeks(int numberOfWeeks) {
        this.numberOfWeeks = numberOfWeeks;
    }

    public SemesterName getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(SemesterName semesterName) {
        this.semesterName = semesterName;
    }
}
