package com.javaear.koubeiblog.system.entity;

import com.baomidou.mybatisplus.annotations.TableId;

/**
 * @author aooer
 */
public abstract class NBaseModel extends BaseModel {

    @TableId
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
