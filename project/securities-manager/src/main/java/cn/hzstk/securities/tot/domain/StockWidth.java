package cn.hzstk.securities.tot.domain;

import cn.hzstk.securities.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "tot_stock_width")
public class StockWidth extends BaseDomain {

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
    //当前价格
    @Column(name = "cur_price")
    private String curPrice;
    //最高价格
    @Column(name = "max_price")
    private String maxPrice;
    //最高涨跌幅
    @Column(name = "max_width")
    private String maxWidth;
    //最低价格
    @Column(name = "min_price")
    private String minPrice;
    //最低涨跌幅
    @Column(name = "min_width")
    private String minWidth;
    //当天涨跌幅
    @Column(name = "up_width")
    private String upWidth;
    //上一天涨跌幅
    @Column(name = "up_width1")
    private String upWidth1;
    //上二天涨跌幅
    @Column(name = "up_width2")
    private String upWidth2;
    //上三天涨跌幅
    @Column(name = "up_width3")
    private String upWidth3;
    //下跌天数
    @Column(name = "down_total")
    private Integer downTotal;
    //上涨天数
    @Column(name = "up_total")
    private Integer upTotal;
    //上涨天数3
    @Column(name = "up3_total")
    private Integer up3Total;
    //上涨天数9
    @Column(name = "up9_total")
    private Integer up9Total;
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

    public void setCurPrice(String curPrice){
        this.curPrice = curPrice;
    }

    public String getCurPrice(){
        return curPrice;
    }

    public void setMaxPrice(String maxPrice){
        this.maxPrice = maxPrice;
    }

    public String getMaxPrice(){
        return maxPrice;
    }

    public void setMaxWidth(String maxWidth){
        this.maxWidth = maxWidth;
    }

    public String getMaxWidth(){
        return maxWidth;
    }

    public void setMinPrice(String minPrice){
        this.minPrice = minPrice;
    }

    public String getMinPrice(){
        return minPrice;
    }

    public void setMinWidth(String minWidth){
        this.minWidth = minWidth;
    }

    public String getMinWidth(){
        return minWidth;
    }

    public void setUpWidth(String upWidth){
        this.upWidth = upWidth;
    }

    public String getUpWidth(){
        return upWidth;
    }

    public void setUpWidth1(String upWidth1){
        this.upWidth1 = upWidth1;
    }

    public String getUpWidth1(){
        return upWidth1;
    }

    public void setUpWidth2(String upWidth2){
        this.upWidth2 = upWidth2;
    }

    public String getUpWidth2(){
        return upWidth2;
    }

    public void setUpWidth3(String upWidth3){
        this.upWidth3 = upWidth3;
    }

    public String getUpWidth3(){
        return upWidth3;
    }

    public void setDownTotal(Integer downTotal){
        this.downTotal = downTotal;
    }

    public Integer getDownTotal(){
        return downTotal;
    }

    public void setUpTotal(Integer upTotal){
        this.upTotal = upTotal;
    }

    public Integer getUpTotal(){
        return upTotal;
    }

    public void setUp3Total(Integer up3Total){
        this.up3Total = up3Total;
    }

    public Integer getUp3Total(){
        return up3Total;
    }

    public void setUp9Total(Integer up9Total){
        this.up9Total = up9Total;
    }

    public Integer getUp9Total(){
        return up9Total;
    }

    public void setOrderBy(Integer orderBy){
        this.orderBy = orderBy;
    }

    public Integer getOrderBy(){
        return orderBy;
    }
}
