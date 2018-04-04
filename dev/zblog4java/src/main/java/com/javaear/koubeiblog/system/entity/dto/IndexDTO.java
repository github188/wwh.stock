package com.javaear.koubeiblog.system.entity.dto;

import java.io.Serializable;

public class IndexDTO implements Serializable {

    private String currentVersion;
    private int articleCount;
    private int cateCount;
    private int templateCount;
    private int tagCount;
    private int commentCount;
    private int userCount;

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public int getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(int articleCount) {
        this.articleCount = articleCount;
    }

    public int getCateCount() {
        return cateCount;
    }

    public void setCateCount(int cateCount) {
        this.cateCount = cateCount;
    }

    public int getTemplateCount() {
        return templateCount;
    }

    public void setTemplateCount(int templateCount) {
        this.templateCount = templateCount;
    }

    public int getTagCount() {
        return tagCount;
    }

    public void setTagCount(int tagCount) {
        this.tagCount = tagCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }
}
