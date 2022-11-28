package com.nhk.thesis.entity.constant;

import java.util.LinkedHashMap;
import java.util.Map;

public enum UserRole {
    ADMIN("3", "Trưởng bộ môn"), CO_ADMIN("2", "Thư ký bộ môn"), USER("1", "Giảng viên"), MARKER("0", "Uỷ Viên");

    private String code;
    private String text;

    private UserRole(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public static UserRole getRoleByCode(String code) {
        for (UserRole role : UserRole.values()) {
            if (role.code.equals(code)) {
                return role;
            }
        }
        return null;
    }

    public static Map<String, String> getAllRole() {
        Map<String, String> roles = new LinkedHashMap<>();
        for (UserRole role: UserRole.values()){
            roles.put(role.getCode(), role.getText());
        }

        return roles;
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
