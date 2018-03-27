package cn.hzstk.securities.east.domain;

import cn.hzstk.securities.common.domain.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "net_profit_express")
public class ProfitExpress extends BaseDomain {

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
    //每股收益
    @Column(name = "per_profit")
    private String perProfit;
    //营业收入
    @Column(name = "business_profit")
    private String businessProfit;
    //去年同期营业收入
    @Column(name = "business_last_period")
    private String businessLastPeriod;
    //同比增长
    @Column(name = "year_growth")
    private String yearGrowth;
    //季度环比增长
    @Column(name = "chain_growth")
    private String chainGrowth;
    //净利润
    @Column(name = "net_profit")
    private String netProfit;
    //去年同期净利润
    @Column(name = "net_last_period")
    private String netLastPeriod;
    //净利润同比增长
    @Column(name = "net_year_growth")
    private String netYearGrowth;
    //净利润季度环比增长
    @Column(name = "net_chain_growth")
    private String netChainGrowth;
    //每股净资产
    @Column(name = "net_assets")
    private String netAssets;
    //净资产收益率
    @Column(name = "assets_yield")
    private String assetsYield;
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

    public void setPerProfit(String perProfit){
        this.perProfit = perProfit;
    }

    public String getPerProfit(){
        return perProfit;
    }

    public void setBusinessProfit(String businessProfit){
        this.businessProfit = businessProfit;
    }

    public String getBusinessProfit(){
        return businessProfit;
    }

    public void setBusinessLastPeriod(String businessLastPeriod){
        this.businessLastPeriod = businessLastPeriod;
    }

    public String getBusinessLastPeriod(){
        return businessLastPeriod;
    }

    public void setYearGrowth(String yearGrowth){
        this.yearGrowth = yearGrowth;
    }

    public String getYearGrowth(){
        return yearGrowth;
    }

    public void setChainGrowth(String chainGrowth){
        this.chainGrowth = chainGrowth;
    }

    public String getChainGrowth(){
        return chainGrowth;
    }

    public void setNetProfit(String netProfit){
        this.netProfit = netProfit;
    }

    public String getNetProfit(){
        return netProfit;
    }

    public void setNetLastPeriod(String netLastPeriod){
        this.netLastPeriod = netLastPeriod;
    }

    public String getNetLastPeriod(){
        return netLastPeriod;
    }

    public void setNetYearGrowth(String netYearGrowth){
        this.netYearGrowth = netYearGrowth;
    }

    public String getNetYearGrowth(){
        return netYearGrowth;
    }

    public void setNetChainGrowth(String netChainGrowth){
        this.netChainGrowth = netChainGrowth;
    }

    public String getNetChainGrowth(){
        return netChainGrowth;
    }

    public void setNetAssets(String netAssets){
        this.netAssets = netAssets;
    }

    public String getNetAssets(){
        return netAssets;
    }

    public void setAssetsYield(String assetsYield){
        this.assetsYield = assetsYield;
    }

    public String getAssetsYield(){
        return assetsYield;
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
