package com.nhk.thesis.entity.vo;

import com.nhk.thesis.entity.Report;

import java.util.List;

public class ReportVO {
    private String id;

    private UserVO creator;
    private String timestamp;
    private boolean isApproved;
    private boolean isSubmitted;
    private String presentation;
    private String qna;
    private String advices;
    private String comment;
    private String result;
    private String finalPoint;
    private String endTime;
    private String other;

    public ReportVO(Report report, UserVO user) {
        this.id = report.getId();
        this.creator = user;
        this.timestamp = report.getTimestamp();
        this.isApproved = report.isApproved();
        this.isSubmitted = report.isSubmitted();
        this.presentation = report.getPresentation();
        this.qna = report.getQna();
        this.advices = report.getAdvices();
        this.comment = report.getComment();
        this.result = report.getResult();
        this.finalPoint = report.getFinalPoint();
        this.endTime = report.getEndTime();
        this.other = report.getOther();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserVO getCreator() {
        return creator;
    }

    public void setCreator(UserVO creator) {
        this.creator = creator;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getQna() {
        return qna;
    }

    public void setQna(String qna) {
        this.qna = qna;
    }

    public String getAdvices() {
        return advices;
    }

    public void setAdvices(String advices) {
        this.advices = advices;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFinalPoint() {
        return finalPoint;
    }

    public void setFinalPoint(String finalPoint) {
        this.finalPoint = finalPoint;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
