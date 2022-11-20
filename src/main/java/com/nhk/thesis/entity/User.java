package com.nhk.thesis.entity;

import com.nhk.thesis.entity.constant.UserRole;
import com.nhk.thesis.entity.constant.UserStatus;
import com.nhk.thesis.entity.constant.UserTitle;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Document(collection = "user")
public class User {
    @Id
    private String id;

    private String account;
    private String password;
    private String name;
    private String email;
    private String phone;
    private UserTitle title;
    private UserRole role;
    private UserStatus status;
    private boolean isOnline;
    private String activationCode;
    private String createdDate;

    public User() {
    }

    @Bean
    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public User(String id, String account, String password, String name, String email, String phone, UserTitle title,
                UserRole role, UserStatus status, boolean isOnline, String createdDate) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.title = title;
        this.role = role;
        this.status = status;
        this.isOnline = isOnline;
        this.createdDate = createdDate;
    }

    public User(String account, String password, String name, String email, String phone, UserRole role, UserTitle title, String activationCode){
        ObjectId objectId = ObjectId.get();
        this.id = String.format("User_%s", objectId);
        this.account = account;
        this.password = passwordEncoder().encode(password);
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.title = title;
        this.activationCode = activationCode;
        this.status = UserStatus.INACTIVATE;
        this.createdDate = System.currentTimeMillis()+"";
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserTitle getTitle() {
        return title;
    }

    public void setTitle(UserTitle title) {
        this.title = title;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
