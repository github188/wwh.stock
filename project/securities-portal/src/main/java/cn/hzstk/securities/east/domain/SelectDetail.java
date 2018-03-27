package cn.hzstk.securities.east.domain;

import cn.hzstk.securities.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "east_select_detail")
public class SelectDetail {

    //上层指标标识
    @Column(name = "ul")
    private String ul;
    //指标标识
    @Column(name = "id")
    private String id;
    //指标条件
    @Column(name = "ck")
    private String ck;
    //指标内容
    @Column(name = "text")
    private String text;
    //指标单位
    @Column(name = "unit")
    private String unit;
    //指标类别
    @Column(name = "clf")
    private String clf;

    public void setUl(String ul){
        this.ul = ul;
    }

    public String getUl(){
        return ul;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCk(String ck){
        this.ck = ck;
    }

    public String getCk(){
        return ck;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }

    public void setUnit(String unit){
        this.unit = unit;
    }

    public String getUnit(){
        return unit;
    }

    public void setClf(String clf){
        this.clf = clf;
    }

    public String getClf(){
        return clf;
    }
}
