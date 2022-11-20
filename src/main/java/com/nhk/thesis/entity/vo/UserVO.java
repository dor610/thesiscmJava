package com.nhk.thesis.entity.vo;

import com.nhk.thesis.entity.User;

public class UserVO {
    private String id;
    private String account;
    private String name;
    private String email;
    private String title;
    private String titleCode;
    private String fullTitle;
    private String role;
    private String fullRole;
    private String phone;
    private String status;
    private String statusCode;
    private String createdDate;

    public UserVO() {
    }

    public UserVO(String id, String account, String name, String email, String title, String role, String phone, String status, String createdDate) {
        this.id = id;
        this.account = account;
        this.name = name;
        this.email = email;
        this.title = title;
        this.role = role;
        this.phone = phone;
        this.status = status;
        this.createdDate = createdDate;
    }

    public UserVO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.account = user.getAccount();
        this.phone = user.getPhone();
        this.title = user.getTitle().getText();
        this.titleCode = user.getTitle().getCode();
        this.fullTitle = user.getTitle().getName();
        this.role = user.getRole().getCode();
        this.fullRole = user.getRole().getText();
        this.status = user.getStatus().getText();
        this.statusCode = user.getStatus().getCode();
        this.createdDate = user.getCreatedDate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public String getFullRole() {
        return fullRole;
    }

    public void setFullRole(String fullRole) {
        this.fullRole = fullRole;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getTitleCode() {
        return titleCode;
    }

    public void setTitleCode(String titleCode) {
        this.titleCode = titleCode;
    }
}
