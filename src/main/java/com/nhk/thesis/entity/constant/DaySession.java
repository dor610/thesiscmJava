package com.nhk.thesis.entity.constant;

public enum DaySession {
    MORNING("1", "Buổi sáng"), AFTERNOON("2", "Buổi chiều");

    private String code;
    private String text;

    private DaySession(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public static final DaySession getByCode(String code) {
        for (DaySession session: DaySession.values()) {
            if(session.getCode().equals(code))
                return session;
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
