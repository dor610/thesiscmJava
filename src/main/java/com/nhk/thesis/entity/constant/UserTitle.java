package com.nhk.thesis.entity.constant;

import java.util.LinkedHashMap;
import java.util.Map;

public enum UserTitle {

    TS("1", "TS", "Tiến sĩ"), Ths("2", "Ths", "Thạc sĩ"), PGs("3", "PGs", "Phó giáo sư");

    private String code;
    private String text;
    private String name;

    UserTitle(String code, String text, String name) {
        this.code = code;
        this.text = text;
        this.name = name;
    }

    public static Map<String, String> getValues() {
        Map<String, String> map = new LinkedHashMap<>();
        for(UserTitle data: UserTitle.values()){
            map.put(data.getCode(), data.getName());
        }
        return map;
    }

    public static UserTitle getUserTitleByCode(String code){
        for(UserTitle data: UserTitle.values()){
            if(data.getCode().equals(code))
                return data;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
