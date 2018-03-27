package cn.hzstk.securities.stockeast.domain;

import cn.hzstk.securities.common.domain.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "stk_stocks_info")
public class StocksInfo extends BaseDomain {

    private static final long serialVersionUID = 1L;

    //股票名称  股票中文名称
    @Column(name = "name")
    private String name;
    //股票代码  股票数字代码
    @Column(name = "code")
    private String code;
    //股票简称  股票简易代码
    @Column(name = "abbreviation")
    private String abbreviation;
    //交易场所  上交所：sh 深交所：sz
    @Column(name = "place")
    private String place;
    //股票类型  指数:z A股:a B股:b
    @Column(name = "stype")
    private String stype;
    //所在地区  省级地区
    @Column(name = "area")
    private String area;
    //上市日期
    @Column(name = "open_date")
    private String openDate;
    //状态  交易中:0 停牌:1 下市:2
    @Column(name = "status")
    private Integer status;
    //顺序
    @Column(name = "order_by")
    private Integer orderBy;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public void setAbbreviation(String abbreviation){
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation(){
        return abbreviation;
    }

    public void setPlace(String place){
        this.place = place;
    }

    public String getPlace(){
        return place;
    }

    public void setStype(String stype){
        this.stype = stype;
    }

    public String getStype(){
        return stype;
    }

    public void setArea(String area){
        this.area = area;
    }

    public String getArea(){
        return area;
    }

    public void setOpenDate(String openDate){
        this.openDate = openDate;
    }

    public String getOpenDate(){
        return openDate;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public Integer getStatus(){
        return status;
    }

    public void setOrderBy(Integer orderBy){
        this.orderBy = orderBy;
    }

    public Integer getOrderBy(){
        return orderBy;
    }
}
