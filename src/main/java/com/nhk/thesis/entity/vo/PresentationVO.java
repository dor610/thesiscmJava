package com.nhk.thesis.entity.vo;

import com.nhk.thesis.entity.Presentation;

public class PresentationVO {

    private String id;

    private StudentVO student;
    private TopicVO topic;
    private SemesterVO semester;
    private UserVO lecturer;
    private String timestamp;
    private String place;
    private String time;
    private String date;
    private String daySession;
    private String daySessionCode;
    private String status;
    private String statusCode;
    private UserVO president;
    private UserVO secretary;
    private UserVO member;

    public PresentationVO() {
    }

    public PresentationVO(Presentation presentation, StudentVO student, TopicVO topic, SemesterVO semester, UserVO lecturer,
                          UserVO president, UserVO secretary, UserVO member){
        this. id = presentation.getId();
        this.timestamp = presentation.getTimestamp();
        this.place = presentation.getPlace();
        this.time = presentation.getTime();
        this.date = presentation.getDate();
        this.daySession = presentation.getSession().getText();
        this.daySessionCode = presentation.getSession().getCode();
        this.student = student;
        this.topic = topic;
        this.semester = semester;
        this.status = presentation.getStatus().getText();
        this.statusCode = presentation.getStatus().getCode();
        this.lecturer = lecturer;
        this.president = president;
        this.secretary = secretary;
        this.member = member;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StudentVO getStudent() {
        return student;
    }

    public void setStudent(StudentVO student) {
        this.student = student;
    }

    public TopicVO getTopic() {
        return topic;
    }

    public void setTopic(TopicVO topic) {
        this.topic = topic;
    }

    public SemesterVO getSemester() {
        return semester;
    }

    public void setSemester(SemesterVO semester) {
        this.semester = semester;
    }

    public UserVO getLecturer() {
        return lecturer;
    }

    public void setLecturer(UserVO lecturer) {
        this.lecturer = lecturer;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public UserVO getPresident() {
        return president;
    }

    public void setPresident(UserVO president) {
        this.president = president;
    }

    public UserVO getSecretary() {
        return secretary;
    }

    public void setSecretary(UserVO secretary) {
        this.secretary = secretary;
    }

    public UserVO getMember() {
        return member;
    }

    public void setMember(UserVO member) {
        this.member = member;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDaySession() {
        return daySession;
    }

    public void setDaySession(String daySession) {
        this.daySession = daySession;
    }

    public String getDaySessionCode() {
        return daySessionCode;
    }

    public void setDaySessionCode(String daySessionCode) {
        this.daySessionCode = daySessionCode;
    }
}
