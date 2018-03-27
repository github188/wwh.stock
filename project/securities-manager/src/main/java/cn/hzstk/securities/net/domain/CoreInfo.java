package cn.hzstk.securities.net.domain;

import cn.hzstk.securities.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "net_core_info")
public class CoreInfo extends BaseDomain {

    private static final long serialVersionUID = 1L;

    //日期
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
    //市盈(动)
    @Column(name = "pe")
    private String pe;
    //每股净资产
    @Column(name = "net_assets")
    private String netAssets;
    //市净率
    @Column(name = "pb_ratio")
    private String pbRatio;
    //主营业务收入
    @Column(name = "main_revenue")
    private String mainRevenue;
    //营收同比
    @Column(name = "main_yoy")
    private String mainYoy;
    //净利润
    @Column(name = "net_profit")
    private String netProfit;
    //净利润同比
    @Column(name = "net_yoy")
    private String netYoy;
    //毛利率
    @Column(name = "gross_profit_yield")
    private String grossProfitYield;
    //净利率
    @Column(name = "net_profit_ratio")
    private String netProfitRatio;
    //净资产收益率
    @Column(name = "assets_yield")
    private String assetsYield;
    //负债率
    @Column(name = "debit_ratio")
    private String debitRatio;
    //总股本
    @Column(name = "total_equity")
    private String totalEquity;
    //总值
    @Column(name = "total_amount")
    private String totalAmount;
    //流通股本
    @Column(name = "circulation_equity")
    private String circulationEquity;
    //流值
    @Column(name = "flow_amount")
    private String flowAmount;
    //每股未分配利润
    @Column(name = "ndistrib_profit")
    private String ndistribProfit;
    //上市日期
    @Column(name = "open_date")
    private String openDate;
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

    public void setPe(String pe){
        this.pe = pe;
    }

    public String getPe(){
        return pe;
    }

    public void setNetAssets(String netAssets){
        this.netAssets = netAssets;
    }

    public String getNetAssets(){
        return netAssets;
    }

    public void setPbRatio(String pbRatio){
        this.pbRatio = pbRatio;
    }

    public String getPbRatio(){
        return pbRatio;
    }

    public void setMainRevenue(String mainRevenue){
        this.mainRevenue = mainRevenue;
    }

    public String getMainRevenue(){
        return mainRevenue;
    }

    public void setMainYoy(String mainYoy){
        this.mainYoy = mainYoy;
    }

    public String getMainYoy(){
        return mainYoy;
    }

    public void setNetProfit(String netProfit){
        this.netProfit = netProfit;
    }

    public String getNetProfit(){
        return netProfit;
    }

    public void setNetYoy(String netYoy){
        this.netYoy = netYoy;
    }

    public String getNetYoy(){
        return netYoy;
    }

    public void setGrossProfitYield(String grossProfitYield){
        this.grossProfitYield = grossProfitYield;
    }

    public String getGrossProfitYield(){
        return grossProfitYield;
    }

    public void setNetProfitRatio(String netProfitRatio){
        this.netProfitRatio = netProfitRatio;
    }

    public String getNetProfitRatio(){
        return netProfitRatio;
    }

    public void setAssetsYield(String assetsYield){
        this.assetsYield = assetsYield;
    }

    public String getAssetsYield(){
        return assetsYield;
    }

    public void setDebitRatio(String debitRatio){
        this.debitRatio = debitRatio;
    }

    public String getDebitRatio(){
        return debitRatio;
    }

    public void setTotalEquity(String totalEquity){
        this.totalEquity = totalEquity;
    }

    public String getTotalEquity(){
        return totalEquity;
    }

    public void setTotalAmount(String totalAmount){
        this.totalAmount = totalAmount;
    }

    public String getTotalAmount(){
        return totalAmount;
    }

    public void setCirculationEquity(String circulationEquity){
        this.circulationEquity = circulationEquity;
    }

    public String getCirculationEquity(){
        return circulationEquity;
    }

    public void setFlowAmount(String flowAmount){
        this.flowAmount = flowAmount;
    }

    public String getFlowAmount(){
        return flowAmount;
    }

    public void setNdistribProfit(String ndistribProfit){
        this.ndistribProfit = ndistribProfit;
    }

    public String getNdistribProfit(){
        return ndistribProfit;
    }

    public void setOpenDate(String openDate){
        this.openDate = openDate;
    }

    public String getOpenDate(){
        return openDate;
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
