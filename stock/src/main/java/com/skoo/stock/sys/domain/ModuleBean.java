package com.skoo.stock.sys.domain;

import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;

@Alias("ModuleBean")
public class ModuleBean extends BaseEntity {
    /**
     *
     */
    private static final long serialVersionUID = 525600998890405520L;
    private String name;
    private Long up_id;
    private String url;
    private String pattern;
    private String description;
    private String ismenu;
    private String target;
    private String html;
    private String icon;
    private Long lvl;
    private Long orderby;
    private String menu_type;
    private String able_grant;
    private String hasChild;
    /**
     * this is for current user having this module privilege to visit flg
     */
    private String flg;

    public String getHasChild() {
        return hasChild;
    }

    public void setHasChild(String hasChild) {
        this.hasChild = hasChild;
    }

    public String getFlg() {
        return flg;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUp_id() {
        return this.up_id;
    }

    public void setUp_id(Long up_id) {
        this.up_id = up_id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPattern() {
        return this.pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsmenu() {
        return this.ismenu;
    }

    public void setIsmenu(String ismenu) {
        this.ismenu = ismenu;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getHtml() {
        return this.html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getLvl() {
        return this.lvl;
    }

    public void setLvl(Long lvl) {
        this.lvl = lvl;
    }

    public Long getOrderby() {
        return this.orderby;
    }

    public void setOrderby(Long orderby) {
        this.orderby = orderby;
    }

    public String getMenu_type() {
        return this.menu_type;
    }

    public void setMenu_type(String menu_type) {
        this.menu_type = menu_type;
    }

    public String getAble_grant() {
        return this.able_grant;
    }

    public void setAble_grant(String able_grant) {
        this.able_grant = able_grant;
    }
}
