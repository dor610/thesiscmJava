package com.nhk.thesis.entity.constant;

public enum SemesterName {
    FIRST_SEMESTER("1", "Học kỳ I"), SECOND_SEMESTER("2", "Học kỳ II");
    private String code;
    private String text;

    SemesterName(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public static SemesterName getSemesterByCode(String code){
        for(SemesterName name: SemesterName.values()) {
            if(name.getCode().equals(code)) {
                return name;
            }
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
