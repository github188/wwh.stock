package com.javaear.koubeiblog.system.entity.dto;

import com.javaear.koubeiblog.system.entity.enums.ArticleStatusEnum;
import com.javaear.koubeiblog.system.entity.model.CategoryModel;
import com.javaear.koubeiblog.system.entity.model.PermissionModel;
import com.javaear.koubeiblog.system.entity.model.RoleModel;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 缓存模型
 *
 * @author aooer
 */
public class CacheDTO implements Serializable{

    //当前版本
    private String version;
    //全局设置缓存
    private SettingDTO settingDTO;
    //缓存分类数据
    private List<CategoryModel> categoryModelList;
    //缓存权限数据
    private List<PermissionModel> permissionModelList;
    //缓存角色数据
    private List<RoleModel> roleModelList;
    //缓存文章状态
    private List<ArticleStatusEnum> articleStatusEnumList= Arrays.asList(ArticleStatusEnum.values());

    /**************   getter setter   ************/
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public SettingDTO getSettingDTO() {
        return settingDTO;
    }

    public void setSettingDTO(SettingDTO settingDTO) {
        this.settingDTO = settingDTO;
    }

    public List<CategoryModel> getCategoryModelList() {
        return categoryModelList;
    }

    public void setCategoryModelList(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    public List<PermissionModel> getPermissionModelList() {
        return permissionModelList;
    }

    public void setPermissionModelList(List<PermissionModel> permissionModelList) {
        this.permissionModelList = permissionModelList;
    }

    public List<RoleModel> getRoleModelList() {
        return roleModelList;
    }

    public void setRoleModelList(List<RoleModel> roleModelList) {
        this.roleModelList = roleModelList;
    }

    public List<ArticleStatusEnum> getArticleStatusEnumList() {
        return articleStatusEnumList;
    }

    public void setArticleStatusEnumList(List<ArticleStatusEnum> articleStatusEnumList) {
        this.articleStatusEnumList = articleStatusEnumList;
    }
}
