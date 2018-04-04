package com.javaear.koubeiblog.system.entity.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.javaear.koubeiblog.system.entity.IBaseModel;
import org.springframework.util.Assert;

/**
 * @author aooer
 */
@TableName("user")
public final class UserModel extends IBaseModel {

    private Integer role;
    private String name;
    private String password;
    private String email;
    private String ip;
    private String alias;
    private String intro;
    private String sign;
    private Integer articles;
    private Integer pages;
    private Integer comments;
    private Integer uploads;

    public UserModel() {
    }

    public UserModel(String name) {
        this.name = name;
    }

    public UserModel(Integer role, String name, String password, String email, String ip) {
        this.role = role;
        this.name = name;
        this.password = password;
        this.email = email;
        this.ip = ip;
        this.articles=0;
        this.pages=0;
        this.comments=0;
        this.uploads=0;
        this.status=0;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getArticles() {
        return articles;
    }

    public void setArticles(Integer articles) {
        this.articles = articles;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getUploads() {
        return uploads;
    }

    public void setUploads(Integer uploads) {
        this.uploads = uploads;
    }

    @Override
    public void check() {
        Assert.notNull(name);
        Assert.notNull(password);
        Assert.notNull(email);
    }
}
