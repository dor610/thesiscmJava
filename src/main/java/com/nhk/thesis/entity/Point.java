package com.nhk.thesis.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "point")
public class Point {
    @Id
    private String id;

    private String file;
    private List<PointSection> sections;
}
