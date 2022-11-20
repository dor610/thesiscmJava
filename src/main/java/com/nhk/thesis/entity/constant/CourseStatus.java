package com.nhk.thesis.entity.constant;

public enum CourseStatus {
    UPCOMING("1", "Sắp diễn ra"), HAPPENING("2", "Đang diễn ra"), FINISHED("3", "Đã hoàn thành"), DELETED("4", "Đã xoá");

    private String code;
    private String text;

    CourseStatus(String code, String text){
        this.code = code;
        this.text = text;
    }

    public static CourseStatus getCourseByCode(String code){
        for (CourseStatus status: CourseStatus.values()) {
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
