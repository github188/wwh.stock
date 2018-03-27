package com.skoo.stock.sys.domain;

import com.skoo.orm.domain.BaseEntity;
import com.skoo.stock.sys.utils.json.DictAnnotation;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("Sysrole")
public class Sysrole extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 角色名称*
     */
    private String roleName;

    /**
     * 角色类型*
     */
    @DictAnnotation(key = "role_type")
    private String roleType;

    /**
     * 角色编码*
     */
    private String roleCode;

    /**
     * 角色描述*
     */
    private String roleDescript;

    /**
     * 顺序*
     */
    private Integer orderBy;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleDescript() {
        return roleDescript;
    }

    public void setRoleDescript(String roleDescript) {
        this.roleDescript = roleDescript;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }
}
