package cn.hzskt.bdtg.task.domain;
import javax.persistence.Column;
import javax.persistence.Table;

import cn.hzskt.bdtg.common.domain.BaseDomain;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "task_cash_range")
public class CashRange extends BaseDomain {

    private static final long serialVersionUID = 1L;

    //
    @Column(name = "name")
    private String name;

    //范围最小值
    @Column(name = "min_val")
    private Double minVal;

    //范围最大值
    @Column(name = "max_val")
    private Double maxVal;

    //排序号
    @Column(name = "orderNum")
    private Integer ordernum;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setMinVal(Double minVal){
        this.minVal = minVal;
    }

    public Double getMinVal(){
        return minVal;
    }

    public void setMaxVal(Double maxVal){
        this.maxVal = maxVal;
    }

    public Double getMaxVal(){
        return maxVal;
    }

    public void setOrdernum(Integer ordernum){
        this.ordernum = ordernum;
    }

    public Integer getOrdernum(){
        return ordernum;
    }
}
