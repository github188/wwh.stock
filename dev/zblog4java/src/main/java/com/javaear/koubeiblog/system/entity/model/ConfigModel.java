package com.javaear.koubeiblog.system.entity.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.javaear.koubeiblog.system.entity.IBaseModel;
import org.springframework.util.Assert;

@TableName("config")
public class ConfigModel extends IBaseModel {

    private String name;
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ConfigModel() {
    }

    public ConfigModel(Integer id) {
        this.id = id;
    }

    public ConfigModel(Integer id, String content) {
        this.id = id;
        this.content = content;
    }

    @Override
    public void check() {
        Assert.notNull(name);
        Assert.notNull(content);
    }
}
