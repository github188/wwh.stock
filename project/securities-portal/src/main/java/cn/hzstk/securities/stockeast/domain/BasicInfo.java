package cn.hzstk.securities.stockeast.domain;

import cn.hzstk.securities.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "stk_basic_info")
public class BasicInfo extends BaseDomain {

    private static final long serialVersionUID = 1L;

    //代码
    @Column(name = "stock_code")
    private String stockCode;
    //简称
    @Column(name = "stock_name")
    private String stockName;
    //流通股本
    @Column(name = "circulation_equity")
    private String circulationEquity;
    //总股本
    @Column(name = "total_equity")
    private String totalEquity;
    //每股收益
    @Column(name = "per_profit")
    private String perProfit;
    //每股净资产
    @Column(name = "net_assets")
    private String netAssets;
    //每股未分配利润
    @Column(name = "ndistrib_profit")
    private String ndistribProfit;
    //每股资本公积金
    @Column(name = "capital_fund")
    private String capitalFund;
    //净资产收益率
    @Column(name = "assets_yield")
    private String assetsYield;
    //主营业务收入
    @Column(name = "main_revenue")
    private String mainRevenue;
    //净利润
    @Column(name = "net_profit")
    private String netProfit;
    //净利润描述
    @Column(name = "profit_describe")
    private String profitDescribe;
    //主营业务
    @Column(name = "main_business")
    private String mainBusiness;
    //所属板块
    @Column(name = "the_plate")
    private String thePlate;
    //上市日期
    @Column(name = "open_date")
    private String openDate;
    //备注
    @Column(name = "memo")
    private String memo;
    //顺序
    @Column(name = "order_by")
    private Integer orderBy;

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

    public void setCirculationEquity(String circulationEquity){
        this.circulationEquity = circulationEquity;
    }

    public String getCirculationEquity(){
        return circulationEquity;
    }

    public void setTotalEquity(String totalEquity){
        this.totalEquity = totalEquity;
    }

    public String getTotalEquity(){
        return totalEquity;
    }

    public void setPerProfit(String perProfit){
        this.perProfit = perProfit;
    }

    public String getPerProfit(){
        return perProfit;
    }

    public void setNetAssets(String netAssets){
        this.netAssets = netAssets;
    }

    public String getNetAssets(){
        return netAssets;
    }

    public void setNdistribProfit(String ndistribProfit){
        this.ndistribProfit = ndistribProfit;
    }

    public String getNdistribProfit(){
        return ndistribProfit;
    }

    public void setCapitalFund(String capitalFund){
        this.capitalFund = capitalFund;
    }

    public String getCapitalFund(){
        return capitalFund;
    }

    public void setAssetsYield(String assetsYield){
        this.assetsYield = assetsYield;
    }

    public String getAssetsYield(){
        return assetsYield;
    }

    public void setMainRevenue(String mainRevenue){
        this.mainRevenue = mainRevenue;
    }

    public String getMainRevenue(){
        return mainRevenue;
    }

    public void setNetProfit(String netProfit){
        this.netProfit = netProfit;
    }

    public String getNetProfit(){
        return netProfit;
    }

    public void setProfitDescribe(String profitDescribe){
        this.profitDescribe = profitDescribe;
    }

    public String getProfitDescribe(){
        return profitDescribe;
    }

    public void setMainBusiness(String mainBusiness){
        this.mainBusiness = mainBusiness;
    }

    public String getMainBusiness(){
        return mainBusiness;
    }

    public void setThePlate(String thePlate){
        this.thePlate = thePlate;
    }

    public String getThePlate(){
        return thePlate;
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
