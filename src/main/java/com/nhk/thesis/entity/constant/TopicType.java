package com.nhk.thesis.entity.constant;

public enum TopicType {
    INDIVIDUAL("1", "Cá nhân"), GROUP("2", "Nhóm");

    private String code;
    private String text;

    TopicType(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public static TopicType getByCode(String code) {
        for (TopicType type: TopicType.values()) {
            if (type.code.equals(code))
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
