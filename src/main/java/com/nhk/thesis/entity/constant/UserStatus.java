package com.nhk.thesis.entity.constant;

import java.util.Arrays;

public enum UserStatus {
     INACTIVATE("1", "Chưa kích hoạt"), DISABLED("2", "Vô hiệu hoá"), ENABLED("3", "Hoạt động");

     private String code;
     private String text;

     UserStatus(String code, String text){
          this.code = code;
          this.text = text;
     }

     public static UserStatus getUserStatusByCode(String code){
          for (UserStatus userStatus: UserStatus.values()){
               if(userStatus.getCode().equals(code)){
                    return userStatus;
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
