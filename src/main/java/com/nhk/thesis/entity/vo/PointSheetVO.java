package com.nhk.thesis.entity.vo;

import com.nhk.thesis.entity.PointSheet;

public class PointSheetVO {
    private String id;

    private String aPoint;
    private String bPoint;
    private String cPoint;
    private double aTotalPoint;
    private double bTotalPoint;
    private double cTotalPoint;
    private boolean isSubmitted;
    private String sample;
    private String presentation;
    private UserVO creator;

    public PointSheetVO(PointSheet pointSheet, UserVO creator) {
        this.id = pointSheet.getId();
        this.aPoint = pointSheet.getaPoint();
        this.bPoint = pointSheet.getbPoint();
        this.cPoint = pointSheet.getcPoint();
        this.aTotalPoint = pointSheet.getaTotalPoint();
        this.bTotalPoint = pointSheet.getbTotalPoint();
        this.cTotalPoint = pointSheet.getcTotalPoint();
        this.isSubmitted = pointSheet.isSubmitted();
        this.sample = pointSheet.getSample();
        this.presentation = pointSheet.getPresentation();
        this.creator = creator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getaPoint() {
        return aPoint;
    }

    public void setaPoint(String aPoint) {
        this.aPoint = aPoint;
    }

    public String getbPoint() {
        return bPoint;
    }

    public void setbPoint(String bPoint) {
        this.bPoint = bPoint;
    }

    public String getcPoint() {
        return cPoint;
    }

    public void setcPoint(String cPoint) {
        this.cPoint = cPoint;
    }

    public double getaTotalPoint() {
        return aTotalPoint;
    }

    public void setaTotalPoint(double aTotalPoint) {
        this.aTotalPoint = aTotalPoint;
    }

    public double getbTotalPoint() {
        return bTotalPoint;
    }

    public void setbTotalPoint(double bTotalPoint) {
        this.bTotalPoint = bTotalPoint;
    }

    public double getcTotalPoint() {
        return cTotalPoint;
    }

    public void setcTotalPoint(double cTotalPoint) {
        this.cTotalPoint = cTotalPoint;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public UserVO getCreator() {
        return creator;
    }

    public void setCreator(UserVO creator) {
        this.creator = creator;
    }
}
