package com.nhk.thesis.entity.vo;

import com.nhk.thesis.entity.Topic;
import com.nhk.thesis.entity.common.DocumentFile;

import java.util.List;

public class TopicVO {
    private String id;

    private String normalizedName;
    private String name; // tên đề tài
    private String enName;
    private UserVO lecturer;
    private String type; // cá nhân hoặc nhóm
    private String typeCode;
    private String status;
    private String statusCode;
    private List<StudentVO> member; // id của 1 hoặc một nhóm sinh viên
    private SemesterVO semester;
    private DocumentFile reportFile;
    private DocumentFile sourceCode;

    public TopicVO(Topic topic, SemesterVO semester, UserVO user, List<StudentVO> students) {
        this.id = topic.getId();
        this.normalizedName = topic.getNormalizedName();
        this.name = topic.getName();
        this.enName = topic.getEnName();
        this.type = topic.getType().getText();
        this.typeCode = topic.getType().getCode();
        this.status = topic.getStatus().getText();
        this.statusCode = topic.getStatus().getCode();
        this.reportFile = topic.getReportFile();
        this.sourceCode = topic.getSourceCode();
        this.lecturer = user;
        this.semester =semester;
        this.member = students;
    }

    public TopicVO() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNormalizedName() {
        return normalizedName;
    }

    public void setNormalizedName(String normalizedName) {
        this.normalizedName = normalizedName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public UserVO getLecturer() {
        return lecturer;
    }

    public void setLecturer(UserVO lecturer) {
        this.lecturer = lecturer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
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

    public List<StudentVO> getMember() {
        return member;
    }

    public void setMember(List<StudentVO> member) {
        this.member = member;
    }

    public SemesterVO getSemester() {
        return semester;
    }

    public void setSemester(SemesterVO semester) {
        this.semester = semester;
    }

    public DocumentFile getReportFile() {
        return reportFile;
    }

    public void setReportFile(DocumentFile reportFile) {
        this.reportFile = reportFile;
    }

    public DocumentFile getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(DocumentFile sourceCode) {
        this.sourceCode = sourceCode;
    }
}
