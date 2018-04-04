package com.javaear.koubeiblog.system.entity.dto;

import com.javaear.koubeiblog.system.entity.model.PermissionModel;
import org.springframework.beans.BeanUtils;

import java.util.Map;

/**
 * @author aooer 2016/10/19.
 * @version 2.0.0
 * @since 2.0.0
 */
public class PermissionDTO extends PermissionModel {

    //角色是否包含此权限 key角色roleId value是否包含 1包含，0不包含
    private Map<Integer,Integer> roleHas;

    public PermissionDTO() {
    }

    public PermissionDTO(PermissionModel permissionModel, Map<Integer, Integer> roleHas) {
        BeanUtils.copyProperties(permissionModel, this);
        this.roleHas = roleHas;
    }

    /*********
     * getter setter
     *******/
    public Map<Integer, Integer> getRoleHas() {
        return roleHas;
    }

    public void setRoleHas(Map<Integer, Integer> roleHas) {
        this.roleHas = roleHas;
    }
}
