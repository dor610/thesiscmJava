package com.nhk.thesis.entity;

import com.nhk.thesis.entity.common.DocumentFile;
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
    private DocumentFile document;
    private List<String> documentContent;

    private String semester;

    public IMark() {}

    public IMark(String studentCode, String lecturer, String timestamp, DocumentFile document, List<String> documentContent, String semester) {
        ObjectId id = ObjectId.get();
        this.id = "IMark_" + id;
        this.studentCode = studentCode;
        this.lecturer = lecturer;
        this.timestamp = timestamp;
        this.document = document;
        this.documentContent = documentContent;
        this.semester = semester;
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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
