package com.nhk.thesis.entity.constant;

public enum TopicStatus {
    WORKING("1", "Đang thực hiện"), FINISHED("2", "Đã hoàn thành"), CANCELED("3", "Đã huỷ"), PAUSED("4", "Tạm dừng");
    private String code;
    private String text;

    TopicStatus(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public static TopicStatus getByCode(String code) {
        for (TopicStatus status: TopicStatus.values()) {
            if (status.getCode().equals(code))
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
