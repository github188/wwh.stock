package cn.hzstk.securities.net.domain;

import cn.hzstk.securities.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "net_performance_forecast")
public class PerformanceForecast extends BaseDomain {

    private static final long serialVersionUID = 1L;

    //业绩日期
    @Column(name = "dt")
    private String dt;
    //代码
    @Column(name = "stock_code")
    private String stockCode;
    //简称
    @Column(name = "stock_name")
    private String stockName;
    //相关资料
    @Column(name = "relative_info")
    private String relativeInfo;
    //业绩变动
    @Column(name = "performance")
    private String performance;
    //业绩变动幅度
    @Column(name = "performance_width")
    private String performanceWidth;
    //预告类型
    @Column(name = "forecast_type")
    private String forecastType;
    //上年同期净利润
    @Column(name = "net_profit")
    private String netProfit;
    //公告日期
    @Column(name = "report_dt")
    private String reportDt;
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

    public void setRelativeInfo(String relativeInfo){
        this.relativeInfo = relativeInfo;
    }

    public String getRelativeInfo(){
        return relativeInfo;
    }

    public void setPerformance(String performance){
        this.performance = performance;
    }

    public String getPerformance(){
        return performance;
    }

    public void setPerformanceWidth(String performanceWidth){
        this.performanceWidth = performanceWidth;
    }

    public String getPerformanceWidth(){
        return performanceWidth;
    }

    public void setForecastType(String forecastType){
        this.forecastType = forecastType;
    }

    public String getForecastType(){
        return forecastType;
    }

    public void setNetProfit(String netProfit){
        this.netProfit = netProfit;
    }

    public String getNetProfit(){
        return netProfit;
    }

    public void setReportDt(String reportDt){
        this.reportDt = reportDt;
    }

    public String getReportDt(){
        return reportDt;
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
