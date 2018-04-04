package com.javaear.koubeiblog.system.entity.dto;

import com.javaear.koubeiblog.system.entity.enums.ArticleStatusEnum;
import com.javaear.koubeiblog.system.entity.model.ArticleModel;
import com.javaear.koubeiblog.system.entity.model.CategoryModel;
import com.javaear.koubeiblog.system.entity.model.UserModel;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class ArticleDTO extends ArticleModel {

    private String cateName;
    private String authName;
    private String statusName;

    public ArticleDTO(List<CategoryModel> categoryModels,List<UserModel> userModels,ArticleModel articleModel) {
        BeanUtils.copyProperties(articleModel, this);
        this.setAuthName(userModels.stream().filter(userModel -> userModel.getId().equals(articleModel.getUserId())).findFirst().get().getName());
        this.setCateName(categoryModels.stream().filter(categoryModel -> categoryModel.getId().equals(articleModel.getCateId())).findFirst().get().getName());
        this.setStatusName(ArticleStatusEnum.findDesc(articleModel.getStatus()));
    }

    /********  getter setter  *******/
    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getStatusName() {
        return this.statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

}
