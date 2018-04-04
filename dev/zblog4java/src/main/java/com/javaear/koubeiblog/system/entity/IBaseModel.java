package com.javaear.koubeiblog.system.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableId;

/**
 * @author aooer
 */
public abstract class IBaseModel extends BaseModel{

    @TableId(type = IdType.AUTO)
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
