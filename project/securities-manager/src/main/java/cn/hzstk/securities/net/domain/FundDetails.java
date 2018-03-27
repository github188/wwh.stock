package cn.hzstk.securities.net.domain;

import cn.hzstk.securities.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "net_fund_details")
public class FundDetails extends BaseDomain {

    private static final long serialVersionUID = 1L;

    //日期
    @Column(name = "dt")
    private String dt;
    //代码
    @Column(name = "stock_code")
    private String stockCode;
    //名称
    @Column(name = "stock_name")
    private String stockName;
    //涨幅
    @Column(name = "change_width")
    private String changeWidth;
    //现价
    @Column(name = "price")
    private String price;
    //主力净流入
    @Column(name = "main_amount")
    private String mainAmount;
    //主力净占比
    @Column(name = "main_duty")
    private String mainDuty;
    //超大单净流入
    @Column(name = "large_amount")
    private String largeAmount;
    //超大单净占比
    @Column(name = "large_duty")
    private String largeDuty;
    //大单净流入
    @Column(name = "big_amount")
    private String bigAmount;
    //大单净占比
    @Column(name = "big_duty")
    private String bigDuty;
    //中单净流入
    @Column(name = "middle_amount")
    private String middleAmount;
    //中单净占比
    @Column(name = "middle_duty")
    private String middleDuty;
    //小单净流入
    @Column(name = "small_amount")
    private String smallAmount;
    //小单净占比
    @Column(name = "small_duty")
    private String smallDuty;
    //顺序
    @Column(name = "order_by")
    private Integer orderBy;

    public void setDt(String dt){
        this.dt = dt;
    }

    public String getDt(){
        return dt;
    }

    public void setStockCode(String stockCode){
        this.stockCode = stockCode;
    }

    public String getStockCode(){
        return stockCode;
    }

    public void setStockName(String stockName){
        this.stockName = stockName;
    }

    public String getStockName(){
        return stockName;
    }

    public void setChangeWidth(String changeWidth){
        this.changeWidth = changeWidth;
    }

    public String getChangeWidth(){
        return changeWidth;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public String getPrice(){
        return price;
    }

    public void setMainAmount(String mainAmount){
        this.mainAmount = mainAmount;
    }

    public String getMainAmount(){
        return mainAmount;
    }

    public void setMainDuty(String mainDuty){
        this.mainDuty = mainDuty;
    }

    public String getMainDuty(){
        return mainDuty;
    }

    public void setLargeAmount(String largeAmount){
        this.largeAmount = largeAmount;
    }

    public String getLargeAmount(){
        return largeAmount;
    }

    public void setLargeDuty(String largeDuty){
        this.largeDuty = largeDuty;
    }

    public String getLargeDuty(){
        return largeDuty;
    }

    public void setBigAmount(String bigAmount){
        this.bigAmount = bigAmount;
    }

    public String getBigAmount(){
        return bigAmount;
    }

    public void setBigDuty(String bigDuty){
        this.bigDuty = bigDuty;
    }

    public String getBigDuty(){
        return bigDuty;
    }

    public void setMiddleAmount(String middleAmount){
        this.middleAmount = middleAmount;
    }

    public String getMiddleAmount(){
        return middleAmount;
    }

    public void setMiddleDuty(String middleDuty){
        this.middleDuty = middleDuty;
    }

    public String getMiddleDuty(){
        return middleDuty;
    }

    public void setSmallAmount(String smallAmount){
        this.smallAmount = smallAmount;
    }

    public String getSmallAmount(){
        return smallAmount;
    }

    public void setSmallDuty(String smallDuty){
        this.smallDuty = smallDuty;
    }

    public String getSmallDuty(){
        return smallDuty;
    }

    public void setOrderBy(Integer orderBy){
        this.orderBy = orderBy;
    }

    public Integer getOrderBy(){
        return orderBy;
    }
}
