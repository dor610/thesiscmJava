package com.nhk.thesis.entity.constant;

public enum PresentationStatus {
    UPCOMING("1", "Sắp diễn ra"), HAPPENING("2", "Đang diễn ra"), FINISHED("3", "Đã hoàn thành");

    private String code;
    private String text;

    PresentationStatus(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public static PresentationStatus getByCode(String code) {
        for (PresentationStatus p: PresentationStatus.values()) {
            if(p.getCode().equals(code))
                return p;
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
