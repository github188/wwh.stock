package cn.hzstk.securities.net.domain;

import cn.hzstk.securities.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "net_tiger_data")
public class TigerData extends BaseDomain {

    private static final long serialVersionUID = 1L;

    //股票代码
    @Column(name = "stock_code")
    private String stockCode;
    //名称
    @Column(name = "stock_name")
    private String stockName;
    //现价
    @Column(name = "price")
    private String price;
    //涨跌幅
    @Column(name = "change_rate")
    private String changeRate;
    //换手率
    @Column(name = "turn_rate")
    private String turnRate;
    //流通值
    @Column(name = "current_value")
    private String currentValue;
    //近1月上榜
    @Column(name = "up_count_1M")
    private String upCount1m;
    //近3月上榜
    @Column(name = "up_count_3M")
    private String upCount3m;
    //近6月上榜
    @Column(name = "up_count_6M")
    private String upCount6m;
    //近一个月涨幅
    @Column(name = "change_width_1M")
    private String changeWidth1m;
    //近三个月涨幅
    @Column(name = "change_width_3M")
    private String changeWidth3m;
    //近六个月涨幅
    @Column(name = "change_width_6M")
    private String changeWidth6m;
    //近一年涨幅
    @Column(name = "change_width_1Y")
    private String changeWidth1y;
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

    public void setPrice(String price){
        this.price = price;
    }

    public String getPrice(){
        return price;
    }

    public void setChangeRate(String changeRate){
        this.changeRate = changeRate;
    }

    public String getChangeRate(){
        return changeRate;
    }

    public void setTurnRate(String turnRate){
        this.turnRate = turnRate;
    }

    public String getTurnRate(){
        return turnRate;
    }

    public void setCurrentValue(String currentValue){
        this.currentValue = currentValue;
    }

    public String getCurrentValue(){
        return currentValue;
    }

    public void setUpCount1m(String upCount1m){
        this.upCount1m = upCount1m;
    }

    public String getUpCount1m(){
        return upCount1m;
    }

    public void setUpCount3m(String upCount3m){
        this.upCount3m = upCount3m;
    }

    public String getUpCount3m(){
        return upCount3m;
    }

    public void setUpCount6m(String upCount6m){
        this.upCount6m = upCount6m;
    }

    public String getUpCount6m(){
        return upCount6m;
    }

    public void setChangeWidth1m(String changeWidth1m){
        this.changeWidth1m = changeWidth1m;
    }

    public String getChangeWidth1m(){
        return changeWidth1m;
    }

    public void setChangeWidth3m(String changeWidth3m){
        this.changeWidth3m = changeWidth3m;
    }

    public String getChangeWidth3m(){
        return changeWidth3m;
    }

    public void setChangeWidth6m(String changeWidth6m){
        this.changeWidth6m = changeWidth6m;
    }

    public String getChangeWidth6m(){
        return changeWidth6m;
    }

    public void setChangeWidth1y(String changeWidth1y){
        this.changeWidth1y = changeWidth1y;
    }

    public String getChangeWidth1y(){
        return changeWidth1y;
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
