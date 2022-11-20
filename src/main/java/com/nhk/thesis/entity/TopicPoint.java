package com.nhk.thesis.entity;

import com.nhk.thesis.entity.common.Point;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "point")
public class TopicPoint {
    @Id
    private String id;

    private String studentCode;
    private String topic;
    private String presentation;
    private Point presidentPoint;
    private Point secretaryPoint;
    private Point memberPoint;
    private Point result;
    private String report;
    private boolean isApproved;
}
