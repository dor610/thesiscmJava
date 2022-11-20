package com.nhk.thesis.entity;

import com.nhk.thesis.entity.common.DocumentFile;
import com.nhk.thesis.entity.constant.TopicStatus;
import com.nhk.thesis.entity.constant.TopicType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.Normalizer;
import java.util.List;

@Document(collection = "")
public class Topic {
    @Id
    private String id;

    private String normalizedName;
    private String name; // tên đề tài
    private String enName;
    private String lecturer;
    private TopicType type; // cá nhân hoặc nhóm
    private TopicStatus status;
    private List<String> member; // id của 1 hoặc một nhóm sinh viên
    private String semester;
    private DocumentFile reportFile;
    private DocumentFile sourceCode;

    public Topic(String name, String enName, TopicType type, String lecturer, List<String> member, String semester) {
        ObjectId id = ObjectId.get();
        this.id = "Topic_"+id;
        this.name = name;
        this.enName = enName;
        this.lecturer = lecturer;
        this.type = type;
        this.status = TopicStatus.WORKING;
        this.member = member;
        this.semester = semester;
    }

    public String normalizeString(String str) {
        return Normalizer
                .normalize(str, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

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

    public TopicType getType() {
        return type;
    }

    public void setType(TopicType type) {
        this.type = type;
    }

    public List<String> getMember() {
        return member;
    }

    public void setMember(List<String> member) {
        this.member = member;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
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

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public TopicStatus getStatus() {
        return status;
    }

    public void setStatus(TopicStatus status) {
        this.status = status;
    }
}
