package com.javaear.koubeiblog.system.entity.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.javaear.koubeiblog.system.entity.IBaseModel;
import org.springframework.util.Assert;

/**
 * @author aooer
 */
@TableName("comment")
public final class CommentModel extends IBaseModel {

    @TableField("article_id")
    private Integer articleId;
    @TableField("parent_id")
    private Integer parentId;
    @TableField("user_id")
    private Integer userId;
    private String title;
    private String content;
    private String ip;
    private String agent;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    @Override
    public void check() {
        Assert.notNull(articleId);
        Assert.notNull(userId);
        Assert.notNull(title);
        Assert.notNull(content);
    }
}
