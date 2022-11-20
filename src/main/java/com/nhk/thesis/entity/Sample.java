package com.nhk.thesis.entity;

import com.nhk.thesis.entity.common.Criteria;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashMap;
import java.util.Map;

@Document(collection = "sample")
public class Sample {
    @Id
    private String id;

    private String fileName;
    private String timestamp;
    private LinkedHashMap<String, Criteria> aData;
    private String aLabel;
    private LinkedHashMap<String, Criteria> bData;
    private String bLabel;
    private LinkedHashMap<String, Criteria> cData;
    private String cLabel;

    public Sample() {
    }

    public Sample(String id, String fileName, String timestamp, LinkedHashMap<String, Criteria> aData,
                  String aLabel, LinkedHashMap<String, Criteria> bData, String bLabel, LinkedHashMap<String, Criteria> cData, String cLabel) {
        this.id = id;
        this.fileName = fileName;
        this.timestamp = timestamp;
        this.aData = aData;
        this.aLabel = aLabel;
        this.bData = bData;
        this.bLabel = bLabel;
        this.cData = cData;
        this.cLabel = cLabel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public LinkedHashMap<String, Criteria> getAData() {
        return aData;
    }

    public void setAData(LinkedHashMap<String, Criteria> aData) {
        this.aData = aData;
    }

    public String getALabel() {
        return aLabel;
    }

    public void setALabel(String aLabel) {
        this.aLabel = aLabel;
    }

    public LinkedHashMap<String, Criteria> getBData() {
        return bData;
    }

    public void setBData(LinkedHashMap<String, Criteria> bData) {
        this.bData = bData;
    }

    public String getBLabel() {
        return bLabel;
    }

    public void setBLabel(String bLabel) {
        this.bLabel = bLabel;
    }

    public LinkedHashMap<String, Criteria> getCData() {
        return cData;
    }

    public void setCData(LinkedHashMap<String, Criteria> cData) {
        this.cData = cData;
    }

    public String getCLabel() {
        return cLabel;
    }

    public void setCLabel(String cLabel) {
        this.cLabel = cLabel;
    }
}
