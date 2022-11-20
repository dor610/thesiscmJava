package com.nhk.thesis.entity.common;

public class CriteriaDetail {
    private double value;
    private String label;

    public CriteriaDetail() {}

    public CriteriaDetail(double value, String label) {
        this.value = value;
        this.label = label;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
