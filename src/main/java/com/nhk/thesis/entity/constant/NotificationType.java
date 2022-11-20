package com.nhk.thesis.entity.constant;

public enum NotificationType {
    INFO("1", "Thông báo"), WARN("2", "Cảnh báo"), REMIND("3", "Nhắc nhở");

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
