package cn.hzstk.securities.stk.domain;

import cn.hzstk.securities.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@SuppressWarnings("JavaDoc")
@Table(name = "stk_fund_details")
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
    //换手
    @Column(name = "turn_over")
    private String turnOver;
    //净买率
    @Column(name = "net_rate")
    private String netRate;
    //净流入
    @Column(name = "net_amount")
    private String netAmount;
    //相对流量
    @Column(name = "relative_flow")
    private String relativeFlow;
    //大宗流入
    @Column(name = "large_amount")
    private String largeAmount;
    //大宗流量
    @Column(name = "large_flow")
    private String largeFlow;
    //5分钟涨幅
    @Column(name = "change_width5")
    private String changeWidth5;
    //5分钟换手
    @Column(name = "turn_over5")
    private String turnOver5;
    //5分钟净流入
    @Column(name = "net_amount5")
    private String netAmount5;
    //5分钟相对流量
    @Column(name = "relative_flow5")
    private String relativeFlow5;
    //5分钟大宗流入
    @Column(name = "large_amount5")
    private String largeAmount5;
    //5分钟大宗流量
    @Column(name = "large_flow5")
    private String largeFlow5;
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

    public void setTurnOver(String turnOver){
        this.turnOver = turnOver;
    }

    public String getTurnOver(){
        return turnOver;
    }

    public void setNetRate(String netRate){
        this.netRate = netRate;
    }

    public String getNetRate(){
        return netRate;
    }

    public void setNetAmount(String netAmount){
        this.netAmount = netAmount;
    }

    public String getNetAmount(){
        return netAmount;
    }

    public void setRelativeFlow(String relativeFlow){
        this.relativeFlow = relativeFlow;
    }

    public String getRelativeFlow(){
        return relativeFlow;
    }

    public void setLargeAmount(String largeAmount){
        this.largeAmount = largeAmount;
    }

    public String getLargeAmount(){
        return largeAmount;
    }

    public void setLargeFlow(String largeFlow){
        this.largeFlow = largeFlow;
    }

    public String getLargeFlow(){
        return largeFlow;
    }

    public void setChangeWidth5(String changeWidth5){
        this.changeWidth5 = changeWidth5;
    }

    public String getChangeWidth5(){
        return changeWidth5;
    }

    public void setTurnOver5(String turnOver5){
        this.turnOver5 = turnOver5;
    }

    public String getTurnOver5(){
        return turnOver5;
    }

    public void setNetAmount5(String netAmount5){
        this.netAmount5 = netAmount5;
    }

    public String getNetAmount5(){
        return netAmount5;
    }

    public void setRelativeFlow5(String relativeFlow5){
        this.relativeFlow5 = relativeFlow5;
    }

    public String getRelativeFlow5(){
        return relativeFlow5;
    }

    public void setLargeAmount5(String largeAmount5){
        this.largeAmount5 = largeAmount5;
    }

    public String getLargeAmount5(){
        return largeAmount5;
    }

    public void setLargeFlow5(String largeFlow5){
        this.largeFlow5 = largeFlow5;
    }

    public String getLargeFlow5(){
        return largeFlow5;
    }

    public void setOrderBy(Integer orderBy){
        this.orderBy = orderBy;
    }

    public Integer getOrderBy(){
        return orderBy;
    }
}
