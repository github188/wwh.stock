package com.javaear.koubeiblog.system.entity.model;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.javaear.koubeiblog.system.entity.IBaseModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * @author aooer
 */
@TableName("article")
public class ArticleModel extends IBaseModel {

    @TableField("cate_id")
    private Integer cateId;
    @TableField("user_id")
    private Integer userId;
    private String alias;
    private String title;
    private String intro;
    private String content;
    private String template;
    @TableField("tag_ids")
    private String tagIds;
    @TableField("tag_num")
    private Integer tagNum;
    @TableField("comment_num")
    private Integer commentNum;
    @TableField("view_num")
    private Integer viewNum;

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Integer getTagNum() {
        return tagNum;
    }

    public void setTagNum(Integer tagNum) {
        this.tagNum = tagNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
        if (StringUtils.isNotBlank(this.tagIds))
            this.tagNum = JSON.parseArray(this.tagIds, String.class).size();
    }

    @Override
    public void check() {
        Assert.notNull(cateId);
        Assert.notNull(userId);
        Assert.notNull(title);
        Assert.notNull(content);
    }
}
