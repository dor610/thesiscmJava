package com.nhk.thesis.entity;

import com.nhk.thesis.entity.constant.UserLogType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userLog")
public class UserLog {
    @Id
    private String id;

    private UserLogType type;
    private String userId;
    private String note;
    private String timestamp;
    private String change;

    public UserLog() {
    }

    public UserLog(String userId, UserLogType type, String note, String timestamp, String change) {
        ObjectId id = ObjectId.get();
        this.id = "userlog_"+id;
        this.userId = userId;
        this.type = type;
        this.note = note;
        this.timestamp = timestamp;
        this.change = change;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserLogType getType() {
        return type;
    }

    public void setType(UserLogType type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timeStamp) {
        this.timestamp = timestamp;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
