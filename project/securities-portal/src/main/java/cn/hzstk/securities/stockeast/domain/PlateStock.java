package cn.hzstk.securities.stockeast.domain;

import cn.hzstk.securities.common.domain.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "net_plate_stock")
public class PlateStock extends BaseDomain {

    private static final long serialVersionUID = 1L;

    //板块类型
    @Column(name = "plate_type")
    private String plateType;
    //板块代码
    @Column(name = "plate_code")
    private String plateCode;
    //股票代码
    @Column(name = "stock_code")
    private String stockCode;
    //龙头次数
    @Column(name = "cock_num")
    private Integer cockNum;
    //备注
    @Column(name = "memo")
    private String memo;
    //顺序
    @Column(name = "order_by")
    private Integer orderBy;

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

    public void setStockCode(String stockCode){
        this.stockCode = stockCode;
    }

    public String getStockCode(){
        return stockCode;
    }

    public void setCockNum(Integer cockNum){
        this.cockNum = cockNum;
    }

    public Integer getCockNum(){
        return cockNum;
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
