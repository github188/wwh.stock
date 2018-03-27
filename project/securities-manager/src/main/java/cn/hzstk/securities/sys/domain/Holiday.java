package cn.hzstk.securities.sys.domain;

import cn.hzstk.securities.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;

/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "sys_holiday")
public class Holiday extends BaseDomain {

    private static final long serialVersionUID = 1L;

    //日期
    @Column(name = "dt")
    private String dt;
    //描述
    @Column(name = "description")
    private String description;
    //顺序
    @Column(name = "order_by")
    private Integer orderBy;

    public void setDt(String dt){
        this.dt = dt;
    }

    public String getDt(){
        return dt;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setOrderBy(Integer orderBy){
        this.orderBy = orderBy;
    }

    public Integer getOrderBy(){
        return orderBy;
    }
}
