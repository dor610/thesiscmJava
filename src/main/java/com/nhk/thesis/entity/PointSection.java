package com.nhk.thesis.entity;

import com.nhk.thesis.entity.common.Criteria;

import java.util.List;

public class PointSection {
    private String name; //Tên thành phần
    private List<Criteria> criteria; // Danh sách tiêu chí
    //Tổng điểm sẽ bằng tổng điểm các tiêu chí


    public PointSection() {
    }

    public PointSection(List<Criteria> criteria) {
        this.criteria = criteria;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Criteria> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<Criteria> criteria) {
        this.criteria = criteria;
    }
}
