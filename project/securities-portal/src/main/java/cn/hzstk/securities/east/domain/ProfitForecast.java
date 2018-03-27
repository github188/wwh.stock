package cn.hzstk.securities.east.domain;

import cn.hzstk.securities.common.domain.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "net_profit_forecast")
public class ProfitForecast extends BaseDomain {

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
    //业绩变动
    @Column(name = "profit_change")
    private String profitChange;
    //业绩变动幅度
    @Column(name = "profit_change_width")
    private String profitChangeWidth;
    //预告类型
    @Column(name = "forecast_type")
    private String forecastType;
    //上年同期净利润
    @Column(name = "net_last_period")
    private String netLastPeriod;
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

    public void setProfitChange(String profitChange){
        this.profitChange = profitChange;
    }

    public String getProfitChange(){
        return profitChange;
    }

    public void setProfitChangeWidth(String profitChangeWidth){
        this.profitChangeWidth = profitChangeWidth;
    }

    public String getProfitChangeWidth(){
        return profitChangeWidth;
    }

    public void setForecastType(String forecastType){
        this.forecastType = forecastType;
    }

    public String getForecastType(){
        return forecastType;
    }

    public void setNetLastPeriod(String netLastPeriod){
        this.netLastPeriod = netLastPeriod;
    }

    public String getNetLastPeriod(){
        return netLastPeriod;
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
