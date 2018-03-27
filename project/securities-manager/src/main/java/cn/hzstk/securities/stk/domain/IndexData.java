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
@Table(name = "stk_index_data")
public class IndexData extends BaseDomain {

    private static final long serialVersionUID = 1L;

    //代码
    @Column(name = "code")
    private String code;
    //简称
    @Column(name = "name")
    private String name;
    //日期
    @Column(name = "dt")
    private String dt;
    //今开
    @Column(name = "cur_open")
    private String curOpen;
    //昨收
    @Column(name = "pre_close")
    private String preClose;
    //当前点数
    @Column(name = "price")
    private String price;
    //涨跌
    @Column(name = "change_amount")
    private String changeAmount;
    //涨幅
    @Column(name = "change_width")
    private String changeWidth;
    //最高
    @Column(name = "high")
    private String high;
    //最低
    @Column(name = "low")
    private String low;
    //成交量(手)
    @Column(name = "volume")
    private String volume;
    //成交额(万元)
    @Column(name = "amount")
    private String amount;
    //上涨家数
    @Column(name = "rise_cnt")
    private String riseCnt;
    //平盘家数
    @Column(name = "flat_cnt")
    private String flatCnt;
    //下跌家数
    @Column(name = "fall_cnt")
    private String fallCnt;
    //涨速
    @Column(name = "change_rate")
    private String changeRate;
    //顺序
    @Column(name = "order_by")
    private Integer orderBy;

    public void setCode(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setDt(String dt){
        this.dt = dt;
    }

    public String getDt(){
        return dt;
    }

    public void setCurOpen(String curOpen){
        this.curOpen = curOpen;
    }

    public String getCurOpen(){
        return curOpen;
    }

    public void setPreClose(String preClose){
        this.preClose = preClose;
    }

    public String getPreClose(){
        return preClose;
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

    public void setChangeWidth(String changeWidth){
        this.changeWidth = changeWidth;
    }

    public String getChangeWidth(){
        return changeWidth;
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

    public void setVolume(String volume){
        this.volume = volume;
    }

    public String getVolume(){
        return volume;
    }

    public void setAmount(String amount){
        this.amount = amount;
    }

    public String getAmount(){
        return amount;
    }

    public void setRiseCnt(String riseCnt){
        this.riseCnt = riseCnt;
    }

    public String getRiseCnt(){
        return riseCnt;
    }

    public void setFlatCnt(String flatCnt){
        this.flatCnt = flatCnt;
    }

    public String getFlatCnt(){
        return flatCnt;
    }

    public void setFallCnt(String fallCnt){
        this.fallCnt = fallCnt;
    }

    public String getFallCnt(){
        return fallCnt;
    }

    public void setChangeRate(String changeRate){
        this.changeRate = changeRate;
    }

    public String getChangeRate(){
        return changeRate;
    }

    public void setOrderBy(Integer orderBy){
        this.orderBy = orderBy;
    }

    public Integer getOrderBy(){
        return orderBy;
    }
}
