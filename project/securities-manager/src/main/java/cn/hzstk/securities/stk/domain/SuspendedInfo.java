package cn.hzstk.securities.stk.domain;

import cn.hzstk.securities.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "stk_suspended_info")
public class SuspendedInfo extends BaseDomain {

    private static final long serialVersionUID = 1L;

    //代码
    @Column(name = "stock_code")
    private String stockCode;
    //简称
    @Column(name = "stock_name")
    private String stockName;
    //相关资料
    @Column(name = "relative_info")
    private String relativeInfo;
    //停牌时间
    @Column(name = "start_date")
    private String startDate;
    //停牌截止时间
    @Column(name = "end_date")
    private String endDate;
    //预计复牌时间
    @Column(name = "resume_date")
    private String resumeDate;
    //停牌期限
    @Column(name = "suspension_period")
    private String suspensionPeriod;
    //停牌原因
    @Column(name = "suspension_reason")
    private String suspensionReason;
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

    public void setRelativeInfo(String relativeInfo){
        this.relativeInfo = relativeInfo;
    }

    public String getRelativeInfo(){
        return relativeInfo;
    }

    public void setStartDate(String startDate){
        this.startDate = startDate;
    }

    public String getStartDate(){
        return startDate;
    }

    public void setEndDate(String endDate){
        this.endDate = endDate;
    }

    public String getEndDate(){
        return endDate;
    }

    public void setResumeDate(String resumeDate){
        this.resumeDate = resumeDate;
    }

    public String getResumeDate(){
        return resumeDate;
    }

    public void setSuspensionPeriod(String suspensionPeriod){
        this.suspensionPeriod = suspensionPeriod;
    }

    public String getSuspensionPeriod(){
        return suspensionPeriod;
    }

    public void setSuspensionReason(String suspensionReason){
        this.suspensionReason = suspensionReason;
    }

    public String getSuspensionReason(){
        return suspensionReason;
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
