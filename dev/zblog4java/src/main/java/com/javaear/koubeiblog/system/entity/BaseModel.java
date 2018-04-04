package com.javaear.koubeiblog.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;
import java.util.Date;

/**
 * @author aooer
 */
public abstract class BaseModel implements Serializable {

    protected String creator;

    @TableField("create_time")
    protected Date createTime;

    protected String modifier;

    @TableField("modify_time")
    protected Date modifyTime;

    protected Integer status;

    //必填字段check，用于更新数据库，检查必须字段是否完善，
    public abstract void check();

    /****************
     * getter setter
     ***************/

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
