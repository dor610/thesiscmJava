package com.nhk.thesis.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

//Biên bản
@Document(collection = "report")
public class Report {
    @Id
    private String id;

    private String creator;
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

    public Report(){}

    public Report(String creator, String presentation, String qna, String advices, String comment, String result, String finalPoint, String endTime, String other) {
        ObjectId id = ObjectId.get();
        this.id = "Report_" + id;
        this.timestamp = String.valueOf(System.currentTimeMillis());
        this.isApproved = false;
        this.isSubmitted = false;
        this.creator = creator;
        this.endTime = endTime;
        this.other = other;
        this.presentation = presentation;
        this.qna = qna;
        this.advices = advices;
        this.comment = comment;
        this.result = result;
        this.finalPoint = finalPoint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
