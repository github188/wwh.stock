package com.skoo.stock.sys.domain;

import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("SysroleMenu")
public class SysroleMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 角色编号*
     */
    private Long roleId;

    /**
     * 菜单编号*
     */
    private Long menuId;

    /**
     * 菜单附加信息*
     */
    private String menuAtt;

    /**
     * 顺序*
     */
    private Integer orderBy;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuAtt() {
        return menuAtt;
    }

    public void setMenuAtt(String menuAtt) {
        this.menuAtt = menuAtt;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }
}
