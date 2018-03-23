package cn.hzskt.bdtg.config.domain;
import cn.hzskt.bdtg.common.domain.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "config_industry")
public class Industry extends BaseDomain {

    private static final long serialVersionUID = 1L;

    //
    @Column(name = "indus_pid")
    @JSONField(name = "parentId")
    private Integer indusPid;

    //
    @Column(name = "indus_name")
    @JSONField(name = "text")
    private String indusName;

    //
    @Column(name = "is_recommend")
    private Integer isRecommend;

    //
    @Column(name = "indus_type")
    private Integer indusType;

    //
    @Column(name = "listorder")
    private Integer listorder;

    //
    @Column(name = "on_time")
    private Integer onTime;

    //
    @Column(name = "list_type")
    private String listType;

    //
    @Column(name = "list_tpl")
    private String listTpl;

    //
    @Column(name = "indus_intro")
    private String indusIntro;

    //
    @Column(name = "list_desc")
    private String listDesc;

    //
    @Column(name = "totask")
    private Integer totask;

    //
    @Column(name = "togoods")
    private Integer togoods;

    //
    @Column(name = "seo_title")
    private String seoTitle;

    //
    @Column(name = "seo_keyword")
    private String seoKeyword;

    //
    @Column(name = "seo_desc")
    private String seoDesc;

    public void setIndusPid(Integer indusPid){
        this.indusPid = indusPid;
    }

    public Integer getIndusPid(){
        return indusPid;
    }

    public void setIndusName(String indusName){
        this.indusName = indusName;
    }

    public String getIndusName(){
        return indusName;
    }

    public void setIsRecommend(Integer isRecommend){
        this.isRecommend = isRecommend;
    }

    public Integer getIsRecommend(){
        return isRecommend;
    }

    public void setIndusType(Integer indusType){
        this.indusType = indusType;
    }

    public Integer getIndusType(){
        return indusType;
    }

    public void setListorder(Integer listorder){
        this.listorder = listorder;
    }

    public Integer getListorder(){
        return listorder;
    }

    public void setOnTime(Integer onTime){
        this.onTime = onTime;
    }

    public Integer getOnTime(){
        return onTime;
    }

    public void setListType(String listType){
        this.listType = listType;
    }

    public String getListType(){
        return listType;
    }

    public void setListTpl(String listTpl){
        this.listTpl = listTpl;
    }

    public String getListTpl(){
        return listTpl;
    }

    public void setIndusIntro(String indusIntro){
        this.indusIntro = indusIntro;
    }

    public String getIndusIntro(){
        return indusIntro;
    }

    public void setListDesc(String listDesc){
        this.listDesc = listDesc;
    }

    public String getListDesc(){
        return listDesc;
    }

    public void setTotask(Integer totask){
        this.totask = totask;
    }

    public Integer getTotask(){
        return totask;
    }

    public void setTogoods(Integer togoods){
        this.togoods = togoods;
    }

    public Integer getTogoods(){
        return togoods;
    }

    public void setSeoTitle(String seoTitle){
        this.seoTitle = seoTitle;
    }

    public String getSeoTitle(){
        return seoTitle;
    }

    public void setSeoKeyword(String seoKeyword){
        this.seoKeyword = seoKeyword;
    }

    public String getSeoKeyword(){
        return seoKeyword;
    }

    public void setSeoDesc(String seoDesc){
        this.seoDesc = seoDesc;
    }

    public String getSeoDesc(){
        return seoDesc;
    }
}
