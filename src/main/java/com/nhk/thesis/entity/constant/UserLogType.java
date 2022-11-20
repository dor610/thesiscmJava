package com.nhk.thesis.entity.constant;


public enum UserLogType {
    CREATE("1", "Tạo mới"), UPDATE("2", "Cập nhật"), DEACTIVATE("3", "Vô hiệu hoá"), ACTIVATE("4", "Kích hoạt");

    private String code;
    private String text;

    UserLogType(String code, String text){
        this.code = code;
        this.text = text;
    }

    public static UserLogType getUserLogTypeByCode(String code) {
        for (UserLogType type: UserLogType.values()) {
            if(type.getCode().equals(code))
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
