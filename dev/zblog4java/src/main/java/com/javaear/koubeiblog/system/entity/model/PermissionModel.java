package com.javaear.koubeiblog.system.entity.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.javaear.koubeiblog.system.entity.NBaseModel;
import org.springframework.util.Assert;

/**
 * @author aooer
 */
@TableName("permission")
public class PermissionModel extends NBaseModel {

    private String name;
    private String url;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void check() {
        Assert.notNull(name);
    }
}
