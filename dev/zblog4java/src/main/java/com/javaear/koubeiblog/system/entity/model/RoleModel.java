package com.javaear.koubeiblog.system.entity.model;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.javaear.koubeiblog.system.entity.NBaseModel;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author aooer
 */
@TableName("role")
public final class RoleModel extends NBaseModel {

    private String name;
    private String permissions;
    @TableField(exist = false)
    private List<Integer> permissionList;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
        this.permissionList = JSON.parseArray(this.permissions, Integer.class);
    }

    public List<Integer> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Integer> permissionList) {
        this.permissionList = permissionList;
        this.permissions = JSON.toJSONString(this.permissionList);
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
        Assert.notNull(permissions);
    }
}
