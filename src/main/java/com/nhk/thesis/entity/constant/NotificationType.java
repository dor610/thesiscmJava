package com.nhk.thesis.entity.constant;

public enum NotificationType {
    BEGIN("1", "Bắt đầu buổi báo cáo"),
    POINT_APPROVE("2", "Xác nhận điểm"),
    POINT_RE_APPROVE("3", "Chỉnh sửa bản điểm"),
    REPORT_SEND("4", "Gửi xác nhận báo cáo"),
    REPORT_CHANGE("5","Chỉnh sửa báo cáo"),
    REPORT_APPROVE("6", "Báo cáo được xác nhận");

    private String code;
    private String text;

    NotificationType(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public static NotificationType getByCode(String code) {
        for (NotificationType type: NotificationType.values()){
            if(type.code.equals(code))
                return type;
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
