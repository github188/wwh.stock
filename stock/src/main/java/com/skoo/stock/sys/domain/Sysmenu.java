package com.skoo.stock.sys.domain;


import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("Sysmenu")
public class Sysmenu extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 父菜单编号*
     */
    private Long upId;

    /**
     * 上级菜单 *
     */
    private Sysmenu upCategory;

    /**
     * 菜单编码*
     */
    private String menuCode;

    /**
     * 菜单名称*
     */
    private String menuName;

    /**
     * 组织机构*
     */
    private Long menuOrg;

    /**
     * 菜单URL*
     */
    private String menuAct;

    /**
     * 菜单图标*
     */
    private String menuIcon;

    /**
     * 1:显示，0：不显示*
     */
    private String isShow;

    /**
     * 顺序*
     */
    private Integer orderBy;


    public Sysmenu getUpCategory() {
        return upCategory;
    }

    public void setUpCategory(Sysmenu upCategory) {
        this.upCategory = upCategory;
    }

    public Long getUpId() {
        return upId;
    }

    public void setUpId(Long upId) {
        this.upId = upId;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuAct() {
        return menuAct;
    }

    public void setMenuAct(String menuAct) {
        this.menuAct = menuAct;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public Long getMenuOrg() {
        return menuOrg;
    }

    public void setMenuOrg(Long menuOrg) {
        this.menuOrg = menuOrg;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }
}
