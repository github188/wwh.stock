package cn.hzstk.securities.net.domain;

import cn.hzstk.securities.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "net_performance_data")
public class PerformanceData extends BaseDomain {

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
    //每股收益
    @Column(name = "per_profit")
    private String perProfit;
    //营业收入
    @Column(name = "main_revenue")
    private String mainRevenue;
    //营业收入同比增长
    @Column(name = "main_growth")
    private String mainGrowth;
    //营业收入季度环比
    @Column(name = "main_quarter")
    private String mainQuarter;
    //净利润
    @Column(name = "net_profit")
    private String netProfit;
    //净利润同比增长
    @Column(name = "net_growth")
    private String netGrowth;
    //净利润季度环比
    @Column(name = "net_quarter")
    private String netQuarter;
    //每股净资产
    @Column(name = "net_assets")
    private String netAssets;
    //净资产收益率
    @Column(name = "assets_yield")
    private String assetsYield;
    //每股经营现金流量
    @Column(name = "ope_cashflows")
    private String opeCashflows;
    //销售毛利率
    @Column(name = "sale_grossprofit")
    private String saleGrossprofit;
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

    public void setPerProfit(String perProfit){
        this.perProfit = perProfit;
    }

    public String getPerProfit(){
        return perProfit;
    }

    public void setMainRevenue(String mainRevenue){
        this.mainRevenue = mainRevenue;
    }

    public String getMainRevenue(){
        return mainRevenue;
    }

    public void setMainGrowth(String mainGrowth){
        this.mainGrowth = mainGrowth;
    }

    public String getMainGrowth(){
        return mainGrowth;
    }

    public void setMainQuarter(String mainQuarter){
        this.mainQuarter = mainQuarter;
    }

    public String getMainQuarter(){
        return mainQuarter;
    }

    public void setNetProfit(String netProfit){
        this.netProfit = netProfit;
    }

    public String getNetProfit(){
        return netProfit;
    }

    public void setNetGrowth(String netGrowth){
        this.netGrowth = netGrowth;
    }

    public String getNetGrowth(){
        return netGrowth;
    }

    public void setNetQuarter(String netQuarter){
        this.netQuarter = netQuarter;
    }

    public String getNetQuarter(){
        return netQuarter;
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

    public void setOpeCashflows(String opeCashflows){
        this.opeCashflows = opeCashflows;
    }

    public String getOpeCashflows(){
        return opeCashflows;
    }

    public void setSaleGrossprofit(String saleGrossprofit){
        this.saleGrossprofit = saleGrossprofit;
    }

    public String getSaleGrossprofit(){
        return saleGrossprofit;
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
