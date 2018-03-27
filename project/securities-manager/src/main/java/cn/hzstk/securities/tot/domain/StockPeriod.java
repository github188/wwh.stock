package cn.hzstk.securities.tot.domain;

import cn.hzstk.securities.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "tot_stock_period")
public class StockPeriod extends BaseDomain {

    private static final long serialVersionUID = 1L;

    //代码
    @Column(name = "stock_code")
    private String stockCode;
    //名称
    @Column(name = "stock_name")
    private String stockName;
    //开始日期
    @Column(name = "start_dt")
    private String startDt;
    //结束日期
    @Column(name = "end_dt")
    private String endDt;
    //开始价格
    @Column(name = "start_price")
    private String startPrice;
    //结束价格
    @Column(name = "end_price")
    private String endPrice;
    //涨跌幅
    @Column(name = "up_width")
    private String upWidth;
    //开始收盘价
    @Column(name = "start_close")
    private String startClose;
    //结束收盘价
    @Column(name = "end_close")
    private String endClose;
    //收盘价涨跌幅
    @Column(name = "close_width")
    private String closeWidth;
    //总交易天数
    @Column(name = "sum_total")
    private Integer sumTotal;
    //上涨天数
    @Column(name = "up_total")
    private Integer upTotal;
    //下跌天数
    @Column(name = "down_total")
    private Integer downTotal;
    //平盘天数
    @Column(name = "balance_total")
    private Integer balanceTotal;
    //涨跌标志
    @Column(name = "up_flag")
    private Integer upFlag;
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

    public void setStartDt(String startDt){
        this.startDt = startDt;
    }

    public String getStartDt(){
        return startDt;
    }

    public void setEndDt(String endDt){
        this.endDt = endDt;
    }

    public String getEndDt(){
        return endDt;
    }

    public void setStartPrice(String startPrice){
        this.startPrice = startPrice;
    }

    public String getStartPrice(){
        return startPrice;
    }

    public void setEndPrice(String endPrice){
        this.endPrice = endPrice;
    }

    public String getEndPrice(){
        return endPrice;
    }

    public void setUpWidth(String upWidth){
        this.upWidth = upWidth;
    }

    public String getUpWidth(){
        return upWidth;
    }

    public void setStartClose(String startClose){
        this.startClose = startClose;
    }

    public String getStartClose(){
        return startClose;
    }

    public void setEndClose(String endClose){
        this.endClose = endClose;
    }

    public String getEndClose(){
        return endClose;
    }

    public void setCloseWidth(String closeWidth){
        this.closeWidth = closeWidth;
    }

    public String getCloseWidth(){
        return closeWidth;
    }

    public void setSumTotal(Integer sumTotal){
        this.sumTotal = sumTotal;
    }

    public Integer getSumTotal(){
        return sumTotal;
    }

    public void setUpTotal(Integer upTotal){
        this.upTotal = upTotal;
    }

    public Integer getUpTotal(){
        return upTotal;
    }

    public void setDownTotal(Integer downTotal){
        this.downTotal = downTotal;
    }

    public Integer getDownTotal(){
        return downTotal;
    }

    public void setBalanceTotal(Integer balanceTotal){
        this.balanceTotal = balanceTotal;
    }

    public Integer getBalanceTotal(){
        return balanceTotal;
    }

    public void setUpFlag(Integer upFlag){
        this.upFlag = upFlag;
    }

    public Integer getUpFlag(){
        return upFlag;
    }

    public void setOrderBy(Integer orderBy){
        this.orderBy = orderBy;
    }

    public Integer getOrderBy(){
        return orderBy;
    }
}
