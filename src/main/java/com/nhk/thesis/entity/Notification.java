package com.nhk.thesis.entity;

import com.nhk.thesis.entity.constant.NotificationType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notification")
public class Notification {

    @Id
    private String id;

    private String recipient;
    private String content;
    private NotificationType type;
    private String typeCode;
    private String typeName;
    private boolean isSeen;
    private String timestamp;
    private String url;

    public Notification() {
    }

    public Notification(String content, NotificationType type) {
        this.content = content;
        this.type = type;
        this.timestamp = String.valueOf(System.currentTimeMillis());
        this.typeCode = type.getCode();
        this.typeName = type.getText();
    }

    public String getId() {
        return id;
    }

    public void setId() {
        ObjectId id = ObjectId.get();
        this.id = "Notification_"+id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
