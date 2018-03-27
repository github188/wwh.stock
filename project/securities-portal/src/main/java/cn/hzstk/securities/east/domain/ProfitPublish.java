package cn.hzstk.securities.east.domain;

import cn.hzstk.securities.common.domain.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "net_profit_publish")
public class ProfitPublish extends BaseDomain {

    private static final long serialVersionUID = 1L;

    //报告期
    @Column(name = "dt")
    private String dt;
    //代码
    @Column(name = "stock_code")
    private String stockCode;
    //简称
    @Column(name = "stock_name")
    private String stockName;
    //预约日期
    @Column(name = "order_date")
    private String orderDate;
    //一次变更日期
    @Column(name = "once_change_date")
    private String onceChangeDate;
    //二次变更日期
    @Column(name = "second_change_date")
    private String secondChangeDate;
    //三次变更日期
    @Column(name = "third_change_date")
    private String thirdChangeDate;
    //公告日期
    @Column(name = "report_date")
    private String reportDate;
    //备注
    @Column(name = "memo")
    private String memo;
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

    public void setOrderDate(String orderDate){
        this.orderDate = orderDate;
    }

    public String getOrderDate(){
        return orderDate;
    }

    public void setOnceChangeDate(String onceChangeDate){
        this.onceChangeDate = onceChangeDate;
    }

    public String getOnceChangeDate(){
        return onceChangeDate;
    }

    public void setSecondChangeDate(String secondChangeDate){
        this.secondChangeDate = secondChangeDate;
    }

    public String getSecondChangeDate(){
        return secondChangeDate;
    }

    public void setThirdChangeDate(String thirdChangeDate){
        this.thirdChangeDate = thirdChangeDate;
    }

    public String getThirdChangeDate(){
        return thirdChangeDate;
    }

    public void setReportDate(String reportDate){
        this.reportDate = reportDate;
    }

    public String getReportDate(){
        return reportDate;
    }

    public void setMemo(String memo){
        this.memo = memo;
    }

    public String getMemo(){
        return memo;
    }

    public void setOrderBy(Integer orderBy){
        this.orderBy = orderBy;
    }

    public Integer getOrderBy(){
        return orderBy;
    }
}
