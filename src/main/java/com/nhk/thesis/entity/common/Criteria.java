package com.nhk.thesis.entity.common;

import java.util.List;
import java.util.Map;

public class Criteria {
    private List<CriteriaDetail> data;
    private double weight; // điểm tối đa của mỗi dòng


    public Criteria() {
    }

    public Criteria(List<CriteriaDetail> data, double weight) {
        this.data = data;
        this.weight = weight;
    }

    public List<CriteriaDetail> getData() {
        return data;
    }

    public void setData(List<CriteriaDetail> data) {
        this.data = data;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
