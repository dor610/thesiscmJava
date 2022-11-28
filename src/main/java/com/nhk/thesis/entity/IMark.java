package com.nhk.thesis.entity;

import com.nhk.thesis.entity.common.DocumentFile;
import com.nhk.thesis.entity.constant.IMarkStatus;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "imark")
public class IMark {

    @Id
    private String id;

    private String studentCode;
    private String lecturer;
    private String timestamp;
    private String expirationDate;
    private DocumentFile document;
    private String deanComment;
    private String lecturerComment;
    private IMarkStatus status;
    private String reason;
    private String semester;
    private boolean confirm;
    private boolean complete;
    private String other;

    public IMark() {}

    public IMark(String studentCode, String lecturer, String timestamp, String expirationDate, DocumentFile document, String deanComment, String lecturerComment, String reason, String semester, String other) {
        ObjectId id = ObjectId.get();
        this.id = "IMark_" + id;
        this.studentCode = studentCode;
        this.lecturer = lecturer;
        this.timestamp = timestamp;
        this.expirationDate = expirationDate;
        this.document = document;
        this.deanComment = deanComment;
        this.lecturerComment = lecturerComment;
        this.status = IMarkStatus.NEW;
        this.reason = reason;
        this.semester = semester;
        this.other = other;
        this.confirm = false;
        this.complete = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public IMarkStatus getStatus() {
        return status;
    }

    public void setStatus(IMarkStatus status) {
        this.status = status;
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
