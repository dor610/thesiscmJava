package com.nhk.thesis.entity.constant;

public enum PointSheetStatus {
    UNSUBMITTED("1", "Chưa xác nhận") ,SUBMITTED("2", "Đã xác nhận");

    private String code;
    private String text;

    PointSheetStatus(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public static final PointSheetStatus getByCode(String code) {
        for(PointSheetStatus status: PointSheetStatus.values()) {
            if(status.getCode().equals(code))
                return status;
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
