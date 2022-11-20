package com.nhk.thesis.entity;

import com.nhk.thesis.entity.common.ScheduleDetail;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "schedule")
public class Schedule {
    @Id
    private String id;

    private String topic;
    private String timestamp;
    private String address;
}
