package com.nhk.thesis.entity.common;

public class DocumentFile {
    private String name;
    private String url;
    private String sharedUrl;
    private String viewUrl;
    private String extension;
    private String size;
    private String createdDate;
    private String path;

    public DocumentFile(String name, String url, String sharedUrl, String viewUrl, String extension, String size, String createdDate, String path) {
        this.name = name;
        this.url = url;
        this.sharedUrl = sharedUrl;
        this.viewUrl = viewUrl;
        this.extension = extension;
        this.size = size;
        this.createdDate = createdDate;
        this.path = path;
    }

    public DocumentFile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSharedUrl() {
        return sharedUrl;
    }

    public void setSharedUrl(String sharedUrl) {
        this.sharedUrl = sharedUrl;
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }
}
