package com.javaear.koubeiblog.system.entity.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.javaear.koubeiblog.system.entity.IBaseModel;
import org.springframework.util.Assert;

/**
 * @author aooer
 */
@TableName("upload")
public final class UploadModel extends IBaseModel {

    @TableField("user_id")
    private Integer userId;
    private Integer size;
    private String name;
    private String source;
    @TableField("mime_type")
    private String mimeType;
    @TableField("down_nums")
    private String downNums;
    @TableField("article_id")
    private Integer articleId;
    private String intro;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getDownNums() {
        return downNums;
    }

    public void setDownNums(String downNums) {
        this.downNums = downNums;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Override
    public void check() {
        Assert.notNull(size);
        Assert.notNull(name);
        Assert.notNull(mimeType);
        Assert.notNull(source);
    }
}
