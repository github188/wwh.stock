package cn.hzstk.securities.east.domain;

import cn.hzstk.securities.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "east_select_list")
public class SelectList {

    //上层指标
    @Column(name = "list_cont")
    private String listCont;
    //指标标题
    @Column(name = "pre_tit")
    private String preTit;
    //指标名称
    @Column(name = "name")
    private String name;
    //指标折叠标识
    @Column(name = "colsable")
    private Integer colsable;
    //指标列表内容
    @Column(name = "ul")
    private String ul;
    //
    @Column(name = "selectorcache")
    private String selectorcache;

    public void setListCont(String listCont){
        this.listCont = listCont;
    }

    public String getListCont(){
        return listCont;
    }

    public void setPreTit(String preTit){
        this.preTit = preTit;
    }

    public String getPreTit(){
        return preTit;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setColsable(Integer colsable){
        this.colsable = colsable;
    }

    public Integer getColsable(){
        return colsable;
    }

    public void setUl(String ul){
        this.ul = ul;
    }

    public String getUl(){
        return ul;
    }

    public void setSelectorcache(String selectorcache){
        this.selectorcache = selectorcache;
    }

    public String getSelectorcache(){
        return selectorcache;
    }
}
