package com.nhk.thesis.entity;

import com.nhk.thesis.entity.constant.DaySession;
import com.nhk.thesis.entity.constant.PresentationStatus;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "presentation")
public class Presentation {

    @Id
    private String id;

    private String student;
    private String topic;
    private String semester;
    private String lecturer;
    private String timestamp;
    private PresentationStatus status;
    private String place;
    private String time;
    private String date;
    private DaySession session;
    private String president;
    private String secretary;
    private String member;

    public Presentation() {
    }

    public Presentation(String student, String topic, String semester, String lecturer, String timestamp, PresentationStatus status,
                        String place, String time, String date, DaySession session, String president, String secretary, String member) {
        ObjectId id = ObjectId.get();
        this.id = "Presentation_" + id;
        this.student = student;
        this.topic = topic;
        this.semester = semester;
        this.lecturer = lecturer;
        this.timestamp = timestamp;
        this.place = place;
        this.time = time;
        this.date = date;
        this.session = session;
        this.status = status;
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

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
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

    public String getPresident() {
        return president;
    }

    public void setPresident(String president) {
        this.president = president;
    }

    public String getSecretary() {
        return secretary;
    }

    public void setSecretary(String secretary) {
        this.secretary = secretary;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public PresentationStatus getStatus() {
        return status;
    }

    public void setStatus(PresentationStatus status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DaySession getSession() {
        return session;
    }

    public void setSession(DaySession session) {
        this.session = session;
    }
}
