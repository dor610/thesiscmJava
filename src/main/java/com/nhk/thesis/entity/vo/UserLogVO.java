package com.nhk.thesis.entity.vo;

import com.nhk.thesis.entity.UserLog;

public class UserLogVO {
    private String userId;
    private String type;
    private String typeCode;
    private String timestamp;
    private String note;
    private String change;

    public UserLogVO() {
    }

    public UserLogVO(String userId, String type, String timestamp, String change) {
        this.userId = userId;
        this.type = type;
        this.timestamp = timestamp;
        this.change = change;
    }

    public UserLogVO(UserLog log){
        this.change = log.getChange();
        this.userId = log.getUserId();
        this.timestamp = log.getTimestamp();
        this.type = log.getType().getText();
        this.typeCode = log.getType().getCode();
        this.note = log.getNote();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
