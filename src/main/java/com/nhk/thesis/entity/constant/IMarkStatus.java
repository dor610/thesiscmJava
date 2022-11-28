package com.nhk.thesis.entity.constant;

public enum IMarkStatus {
    NEW("1", "Tạo mới"), PENDING("2", "Đang thực hiện"), EXPIRED("3", "Quá hạn");

    private String code;
    private String text;

    IMarkStatus(String code, String text) {
        this.text = text;
        this.code = code;
    }

    public static IMarkStatus getByCode(String code) {
        for(IMarkStatus status: IMarkStatus.values()){
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
