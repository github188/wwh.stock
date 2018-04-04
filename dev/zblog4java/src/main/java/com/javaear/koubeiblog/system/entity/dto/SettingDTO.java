package com.javaear.koubeiblog.system.entity.dto;

import java.io.Serializable;

public class SettingDTO implements Serializable {

    /* 基础设置 */
    private String domain;
    private String blogTitle;
    private String blogSubTitle;
    private String copyRight;
    /* 全局设置 */
    private String allowUploadFileType;
    private int allowUplaodPerFileSize;
    private String enableGzip;//true启用 false禁用
    /* 页面设置 */
    private int deskPageSize;
    private int systemPageSize;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogSubTitle() {
        return blogSubTitle;
    }

    public void setBlogSubTitle(String blogSubTitle) {
        this.blogSubTitle = blogSubTitle;
    }

    public String getCopyRight() {
        return copyRight;
    }

    public void setCopyRight(String copyRight) {
        this.copyRight = copyRight;
    }

    public String getAllowUploadFileType() {
        return allowUploadFileType;
    }

    public void setAllowUploadFileType(String allowUploadFileType) {
        this.allowUploadFileType = allowUploadFileType;
    }

    public int getAllowUplaodPerFileSize() {
        return allowUplaodPerFileSize;
    }

    public void setAllowUplaodPerFileSize(int allowUplaodPerFileSize) {
        this.allowUplaodPerFileSize = allowUplaodPerFileSize;
    }

    public String getEnableGzip() {
        return enableGzip;
    }

    public void setEnableGzip(String enableGzip) {
        this.enableGzip = enableGzip;
    }

    public int getDeskPageSize() {
        return deskPageSize;
    }

    public void setDeskPageSize(int deskPageSize) {
        this.deskPageSize = deskPageSize;
    }

    public int getSystemPageSize() {
        return systemPageSize;
    }

    public void setSystemPageSize(int systemPageSize) {
        this.systemPageSize = systemPageSize;
    }
}
