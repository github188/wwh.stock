package cn.hzstk.securities.stockeast.domain;

import cn.hzstk.securities.common.domain.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "net_plate")
public class Plate extends BaseDomain {

    private static final long serialVersionUID = 1L;

    //日期
    @Column(name = "dt")
    private String dt;
    //板块类型
    @Column(name = "plate_type")
    private String plateType;
    //板块代码
    @Column(name = "plate_code")
    private String plateCode;
    //板块名称
    @Column(name = "plate_name")
    private String plateName;
    //涨跌幅
    @Column(name = "change_width")
    private String changeWidth;
    //总市值
    @Column(name = "amount")
    private String amount;
    //换手率
    @Column(name = "turn_over")
    private String turnOver;
    //上涨家数
    @Column(name = "up_num")
    private String upNum;
    //下跌家数
    @Column(name = "down_num")
    private String downNum;
    //领涨股票代码
    @Column(name = "stock_code")
    private String stockCode;
    //领涨股票名称
    @Column(name = "stock_name")
    private String stockName;
    //涨跌幅
    @Column(name = "stock_change_width")
    private String stockChangeWidth;
    //顺序
    @Column(name = "order_by")
    private Integer orderBy;

    public void setDt(String dt){
        this.dt = dt;
    }

    public String getDt(){
        return dt;
    }

    public void setPlateType(String plateType){
        this.plateType = plateType;
    }

    public String getPlateType(){
        return plateType;
    }

    public void setPlateCode(String plateCode){
        this.plateCode = plateCode;
    }

    public String getPlateCode(){
        return plateCode;
    }

    public void setPlateName(String plateName){
        this.plateName = plateName;
    }

    public String getPlateName(){
        return plateName;
    }

    public void setChangeWidth(String changeWidth){
        this.changeWidth = changeWidth;
    }

    public String getChangeWidth(){
        return changeWidth;
    }

    public void setAmount(String amount){
        this.amount = amount;
    }

    public String getAmount(){
        return amount;
    }

    public void setTurnOver(String turnOver){
        this.turnOver = turnOver;
    }

    public String getTurnOver(){
        return turnOver;
    }

    public void setUpNum(String upNum){
        this.upNum = upNum;
    }

    public String getUpNum(){
        return upNum;
    }

    public void setDownNum(String downNum){
        this.downNum = downNum;
    }

    public String getDownNum(){
        return downNum;
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

    public void setStockChangeWidth(String stockChangeWidth){
        this.stockChangeWidth = stockChangeWidth;
    }

    public String getStockChangeWidth(){
        return stockChangeWidth;
    }

    public void setOrderBy(Integer orderBy){
        this.orderBy = orderBy;
    }

    public Integer getOrderBy(){
        return orderBy;
    }
}
