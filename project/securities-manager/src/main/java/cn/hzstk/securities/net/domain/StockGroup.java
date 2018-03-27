package cn.hzstk.securities.net.domain;

import cn.hzstk.securities.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "net_stock_group")
public class StockGroup extends BaseDomain {

    private static final long serialVersionUID = 1L;

    //组合编码
    @Column(name = "group_code")
    private String groupCode;
    //组合名称
    @Column(name = "group_name")
    private String groupName;
    //组合标志
    @Column(name = "group_flag")
    private String groupFlag;
    //顺序
    @Column(name = "order_by")
    private Integer orderBy;

    public void setGroupCode(String groupCode){
        this.groupCode = groupCode;
    }

    public String getGroupCode(){
        return groupCode;
    }

    public void setGroupName(String groupName){
        this.groupName = groupName;
    }

    public String getGroupName(){
        return groupName;
    }

    public void setGroupFlag(String groupFlag){
        this.groupFlag = groupFlag;
    }

    public String getGroupFlag(){
        return groupFlag;
    }

    public void setOrderBy(Integer orderBy){
        this.orderBy = orderBy;
    }

    public Integer getOrderBy(){
        return orderBy;
    }
}
