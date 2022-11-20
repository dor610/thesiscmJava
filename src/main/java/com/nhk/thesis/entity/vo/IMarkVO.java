package com.nhk.thesis.entity.vo;

import com.nhk.thesis.entity.IMark;
import com.nhk.thesis.entity.common.DocumentFile;

import java.util.List;

public class IMarkVO {
    private String id;
    private StudentVO student;
    private UserVO lecturer;
    private String timestamp;
    private DocumentFile document;
    private List<String> documentContent;
    private SemesterVO semester;

    public IMarkVO() {}

    public IMarkVO(IMark iMark, StudentVO student, UserVO user, SemesterVO semester) {
        this.id = iMark.getId();
        this.timestamp = iMark.getTimestamp();
        this.student = student;
        this.lecturer = user;
        this.document = iMark.getDocument();
        this.documentContent = iMark.getDocumentContent();
        this.semester = semester;
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

    public DocumentFile getDocument() {
        return document;
    }

    public void setDocument(DocumentFile document) {
        this.document = document;
    }

    public List<String> getDocumentContent() {
        return documentContent;
    }

    public void setDocumentContent(List<String> documentContent) {
        this.documentContent = documentContent;
    }

    public SemesterVO getSemester() {
        return semester;
    }

    public void setSemester(SemesterVO semester) {
        this.semester = semester;
    }
}
