package com.nhk.thesis.entity;

import com.nhk.thesis.entity.constant.PointSheetStatus;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "pointSheet")
public class PointSheet {

    @Id
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
    private String creator;

    public PointSheet() {
    }

    public PointSheet(String aPoint, String bPoint, String cPoint, double aTotalPoint, double bTotalPoint, double cTotalPoint, String sample, String presentation, String creator) {
        ObjectId id = ObjectId.get();
        this.id = "Point_" + id;
        this.aPoint = aPoint;
        this.bPoint = bPoint;
        this.cPoint = cPoint;
        this.aTotalPoint = aTotalPoint;
        this.bTotalPoint = bTotalPoint;
        this.cTotalPoint = cTotalPoint;
        this.sample = sample;
        this.presentation = presentation;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }

    public void update(String aPoint, String bPoint, String cPoint, double aTotalPoint, double bTotalPoint,
                       double cTotalPoint, String sample, String presentation, String creator) {
        this.aPoint = aPoint;
        this.bPoint = bPoint;
        this.cPoint = cPoint;
        this.aTotalPoint = aTotalPoint;
        this.bTotalPoint = bTotalPoint;
        this.cTotalPoint = cTotalPoint;
        this.sample = sample;
        this.presentation = presentation;
        this.creator = creator;
    }
}
