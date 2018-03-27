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
@Table(name = "stk_plate")
public class Plate extends BaseDomain {

    private static final long serialVersionUID = 1L;

    //日期
    @Column(name = "dt")
    private String dt;
    //代码
    @Column(name = "plate_code")
    private String plateCode;
    //名称
    @Column(name = "plate_name")
    private String plateName;
    //涨幅
    @Column(name = "change_width")
    private String changeWidth;
    //现价
    @Column(name = "price")
    private String price;
    //涨跌
    @Column(name = "change_amount")
    private String changeAmount;
    //涨速
    @Column(name = "change_rate")
    private String changeRate;
    //量比
    @Column(name = "volume_ratio")
    private String volumeRatio;
    //涨跌数
    @Column(name = "change_num")
    private String changeNum;
    //连涨天
    @Column(name = "even_up")
    private String evenUp;
    //3日涨幅
    @Column(name = "three_change_width")
    private String threeChangeWidth;
    //换手
    @Column(name = "turn_over")
    private String turnOver;
    //净流入
    @Column(name = "amount")
    private String amount;
    //顺序
    @Column(name = "order_by")
    private Integer orderBy;

    public void setDt(String dt){
        this.dt = dt;
    }

    public String getDt(){
        return dt;
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

    public void setChangeRate(String changeRate){
        this.changeRate = changeRate;
    }

    public String getChangeRate(){
        return changeRate;
    }

    public void setVolumeRatio(String volumeRatio){
        this.volumeRatio = volumeRatio;
    }

    public String getVolumeRatio(){
        return volumeRatio;
    }

    public void setChangeNum(String changeNum){
        this.changeNum = changeNum;
    }

    public String getChangeNum(){
        return changeNum;
    }

    public void setEvenUp(String evenUp){
        this.evenUp = evenUp;
    }

    public String getEvenUp(){
        return evenUp;
    }

    public void setThreeChangeWidth(String threeChangeWidth){
        this.threeChangeWidth = threeChangeWidth;
    }

    public String getThreeChangeWidth(){
        return threeChangeWidth;
    }

    public void setTurnOver(String turnOver){
        this.turnOver = turnOver;
    }

    public String getTurnOver(){
        return turnOver;
    }

    public void setAmount(String amount){
        this.amount = amount;
    }

    public String getAmount(){
        return amount;
    }

    public void setOrderBy(Integer orderBy){
        this.orderBy = orderBy;
    }

    public Integer getOrderBy(){
        return orderBy;
    }
}
