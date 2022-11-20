package com.nhk.thesis.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "presentationLog")
public class PresentaionLog {
    @Id
    private String id;

    private String timestamp;
    private String content;
    private String presentation;

    public PresentaionLog() {
    }

    public PresentaionLog(String timestamp, String content, String presentation) {
        ObjectId id = ObjectId.get();
        this.id = "Presentaion_log_" + id;
        this.timestamp = timestamp;
        this.content = content;
        this.presentation = presentation;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }
}
