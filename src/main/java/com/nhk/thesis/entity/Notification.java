package com.nhk.thesis.entity;

import com.nhk.thesis.entity.constant.NotificationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notification")
public class Notification {

    @Id
    private String id;

    private String recipient;
    private String content;
    private NotificationType type;
    private boolean isSeen;
    private String timestamp;
    private String url;


}
