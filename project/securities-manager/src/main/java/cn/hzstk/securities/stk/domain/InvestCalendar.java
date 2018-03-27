package cn.hzstk.securities.stk.domain;

import cn.hzstk.securities.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "stk_invest_calendar")
public class InvestCalendar extends BaseDomain {

    private static final long serialVersionUID = 1L;

    //日期
    @Column(name = "dt")
    private String dt;
    //股票代码
    @Column(name = "stock_code")
    private String stockCode;
    //名称
    @Column(name = "stock_name")
    private String stockName;
    //类型说明
    @Column(name = "invest_type")
    private String investType;
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

    public void setInvestType(String investType){
        this.investType = investType;
    }

    public String getInvestType(){
        return investType;
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
