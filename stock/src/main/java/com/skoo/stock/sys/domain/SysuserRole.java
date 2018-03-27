package com.skoo.stock.sys.domain;

import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("SysuserRole")
public class SysuserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 用户编号*
     */
    private Long userId;

    /**
     * 角色编号*
     */
    private Long roleId;

    /**
     * 顺序*
     */
    private Integer orderBy;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }
}
