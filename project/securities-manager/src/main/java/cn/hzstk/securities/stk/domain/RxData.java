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
@Table(name = "stk_rx_data")
public class RxData extends BaseDomain {

    private static final long serialVersionUID = 1L;

    //代码
    @Column(name = "stock_code")
    private String stockCode;
    //名称
    @Column(name = "stock_name")
    private String stockName;
    //日期
    @Column(name = "dt")
    private String dt;
    //开盘
    @Column(name = "cur_open")
    private String curOpen;
    //最高
    @Column(name = "high")
    private String high;
    //最低
    @Column(name = "low")
    private String low;
    //收盘
    @Column(name = "cur_close")
    private String curClose;
    //成交量
    @Column(name = "volume")
    private String volume;
    //5日线
    @Column(name = "ma1")
    private String ma1;
    //10日线
    @Column(name = "ma2")
    private String ma2;
    //20日线
    @Column(name = "ma3")
    private String ma3;
    //60日线
    @Column(name = "ma4")
    private String ma4;
    //5日量
    @Column(name = "mavol1")
    private String mavol1;
    //10日量
    @Column(name = "mavol2")
    private String mavol2;
    //KDJ.K
    @Column(name = "k")
    private String k;
    //KDJ.D
    @Column(name = "d")
    private String d;
    //KDJ.J
    @Column(name = "j")
    private String j;
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

    public void setCurClose(String curClose){
        this.curClose = curClose;
    }

    public String getCurClose(){
        return curClose;
    }

    public void setVolume(String volume){
        this.volume = volume;
    }

    public String getVolume(){
        return volume;
    }

    public void setMa1(String ma1){
        this.ma1 = ma1;
    }

    public String getMa1(){
        return ma1;
    }

    public void setMa2(String ma2){
        this.ma2 = ma2;
    }

    public String getMa2(){
        return ma2;
    }

    public void setMa3(String ma3){
        this.ma3 = ma3;
    }

    public String getMa3(){
        return ma3;
    }

    public void setMa4(String ma4){
        this.ma4 = ma4;
    }

    public String getMa4(){
        return ma4;
    }

    public void setMavol1(String mavol1){
        this.mavol1 = mavol1;
    }

    public String getMavol1(){
        return mavol1;
    }

    public void setMavol2(String mavol2){
        this.mavol2 = mavol2;
    }

    public String getMavol2(){
        return mavol2;
    }

    public void setK(String k){
        this.k = k;
    }

    public String getK(){
        return k;
    }

    public void setD(String d){
        this.d = d;
    }

    public String getD(){
        return d;
    }

    public void setJ(String j){
        this.j = j;
    }

    public String getJ(){
        return j;
    }

    public void setOrderBy(Integer orderBy){
        this.orderBy = orderBy;
    }

    public Integer getOrderBy(){
        return orderBy;
    }
}
