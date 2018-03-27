package cn.hzstk.securities.stk.domain;

import cn.hzstk.securities.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@SuppressWarnings("JavaDoc")
@Table(name = "stk_high_dividend")
public class HighDividend extends BaseDomain {

    private static final long serialVersionUID = 1L;

    //代码
    @Column(name = "stock_code")
    private String stockCode;
    //简称
    @Column(name = "stock_name")
    private String stockName;
    //预案公布日
    @Column(name = "plan_date")
    private String planDate;
    //送股比例(10送X)
    @Column(name = "send_scale")
    private String sendScale;
    //转增比例(10转X)
    @Column(name = "turn_scale")
    private String turnScale;
    //派现比例(10派X)
    @Column(name = "cash_scale")
    private String cashScale;
    //股东大会通过日
    @Column(name = "pass_date")
    private String passDate;
    //股权登记日
    @Column(name = "register_date")
    private String registerDate;
    //除权除息日
    @Column(name = "dividend_date")
    private String dividendDate;
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

    public void setPlanDate(String planDate){
        this.planDate = planDate;
    }

    public String getPlanDate(){
        return planDate;
    }

    public void setSendScale(String sendScale){
        this.sendScale = sendScale;
    }

    public String getSendScale(){
        return sendScale;
    }

    public void setTurnScale(String turnScale){
        this.turnScale = turnScale;
    }

    public String getTurnScale(){
        return turnScale;
    }

    public void setCashScale(String cashScale){
        this.cashScale = cashScale;
    }

    public String getCashScale(){
        return cashScale;
    }

    public void setPassDate(String passDate){
        this.passDate = passDate;
    }

    public String getPassDate(){
        return passDate;
    }

    public void setRegisterDate(String registerDate){
        this.registerDate = registerDate;
    }

    public String getRegisterDate(){
        return registerDate;
    }

    public void setDividendDate(String dividendDate){
        this.dividendDate = dividendDate;
    }

    public String getDividendDate(){
        return dividendDate;
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
