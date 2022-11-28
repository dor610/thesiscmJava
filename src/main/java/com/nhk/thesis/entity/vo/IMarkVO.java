package com.nhk.thesis.entity.vo;

import com.nhk.thesis.entity.IMark;
import com.nhk.thesis.entity.common.DocumentFile;

import java.util.List;

public class IMarkVO {
    private String id;

    private StudentVO student;
    private UserVO lecturer;
    private String timestamp;
    private String expirationDate;
    private DocumentFile document;
    private String deanComment;
    private String lecturerComment;
    private String reason;
    private String status;
    private String statusCode;
    private SemesterVO semester;
    private String other;
    private boolean confirm;
    private boolean complete;

    public IMarkVO() {}

    public IMarkVO(IMark iMark, StudentVO student, UserVO user, SemesterVO semester) {
        this.id = iMark.getId();
        this.timestamp = iMark.getTimestamp();
        this.student = student;
        this.lecturer = user;
        this.document = iMark.getDocument();
        this.semester = semester;
        this.expirationDate = iMark.getExpirationDate();
        this.deanComment = iMark.getDeanComment();
        this.lecturerComment = iMark.getLecturerComment();
        this.reason = iMark.getReason();
        this.other = iMark.getOther();
        this.status = iMark.getStatus().getText();
        this.statusCode = iMark.getStatus().getCode();
        this.confirm = iMark.isConfirm();
        this.complete = iMark.isComplete();
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

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public DocumentFile getDocument() {
        return document;
    }

    public void setDocument(DocumentFile document) {
        this.document = document;
    }

    public String getDeanComment() {
        return deanComment;
    }

    public void setDeanComment(String deanComment) {
        this.deanComment = deanComment;
    }

    public String getLecturerComment() {
        return lecturerComment;
    }

    public void setLecturerComment(String lecturerComment) {
        this.lecturerComment = lecturerComment;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public SemesterVO getSemester() {
        return semester;
    }

    public void setSemester(SemesterVO semester) {
        this.semester = semester;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
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

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
