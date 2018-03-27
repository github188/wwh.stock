package cn.hzstk.securities.stk.domain;

import cn.hzstk.securities.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "stk_hq_details")
public class HqDetails extends BaseDomain {

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
    //涨跌
    @Column(name = "change_amount")
    private String changeAmount;
    //买价
    @Column(name = "buy_price")
    private String buyPrice;
    //卖价
    @Column(name = "sale_price")
    private String salePrice;
    //总量
    @Column(name = "total_volume")
    private String totalVolume;
    //现量
    @Column(name = "volume")
    private String volume;
    //涨速
    @Column(name = "change_rate")
    private String changeRate;
    //换手
    @Column(name = "turn_over")
    private String turnOver;
    //今开
    @Column(name = "cur_open")
    private String curOpen;
    //最高
    @Column(name = "high")
    private String high;
    //最低
    @Column(name = "low")
    private String low;
    //昨收
    @Column(name = "pre_close")
    private String preClose;
    //市盈(动)
    @Column(name = "pe")
    private String pe;
    //总金额
    @Column(name = "amount")
    private String amount;
    //量比
    @Column(name = "volume_ratio")
    private String volumeRatio;
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

    public void setChangeAmount(String changeAmount){
        this.changeAmount = changeAmount;
    }

    public String getChangeAmount(){
        return changeAmount;
    }

    public void setBuyPrice(String buyPrice){
        this.buyPrice = buyPrice;
    }

    public String getBuyPrice(){
        return buyPrice;
    }

    public void setSalePrice(String salePrice){
        this.salePrice = salePrice;
    }

    public String getSalePrice(){
        return salePrice;
    }

    public void setTotalVolume(String totalVolume){
        this.totalVolume = totalVolume;
    }

    public String getTotalVolume(){
        return totalVolume;
    }

    public void setVolume(String volume){
        this.volume = volume;
    }

    public String getVolume(){
        return volume;
    }

    public void setChangeRate(String changeRate){
        this.changeRate = changeRate;
    }

    public String getChangeRate(){
        return changeRate;
    }

    public void setTurnOver(String turnOver){
        this.turnOver = turnOver;
    }

    public String getTurnOver(){
        return turnOver;
    }

    public void setCurOpen(String curOpen){
        this.curOpen = curOpen;
    }

    public String getCurOpen(){
        return curOpen;
    }

    public void setHigh(String high){
        this.high = high;
    }

    public String getHigh(){
        return high;
    }

    public void setLow(String low){
        this.low = low;
    }

    public String getLow(){
        return low;
    }

    public void setPreClose(String preClose){
        this.preClose = preClose;
    }

    public String getPreClose(){
        return preClose;
    }

    public void setPe(String pe){
        this.pe = pe;
    }

    public String getPe(){
        return pe;
    }

    public void setAmount(String amount){
        this.amount = amount;
    }

    public String getAmount(){
        return amount;
    }

    public void setVolumeRatio(String volumeRatio){
        this.volumeRatio = volumeRatio;
    }

    public String getVolumeRatio(){
        return volumeRatio;
    }

    public void setOrderBy(Integer orderBy){
        this.orderBy = orderBy;
    }

    public Integer getOrderBy(){
        return orderBy;
    }
}
