package cn.hzstk.securities.east.domain;

import cn.hzstk.securities.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "east_select_first")
public class SelectFirst {

    //指标名称
    @Column(name = "name")
    private String name;
    //选中指标
    @Column(name = "focus")
    private String focus;
    //指标条目
    @Column(name = "items")
    private Integer items;
    //指标列表内容
    @Column(name = "list_cont")
    private String listCont;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setFocus(String focus){
        this.focus = focus;
    }

    public String getFocus(){
        return focus;
    }

    public void setItems(Integer items){
        this.items = items;
    }

    public Integer getItems(){
        return items;
    }

    public void setListCont(String listCont){
        this.listCont = listCont;
    }

    public String getListCont(){
        return listCont;
    }
}
