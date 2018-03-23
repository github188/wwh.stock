package cn.hzskt.bdtg.config.domain;
import cn.hzskt.bdtg.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "config_district")
public class District extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //上级id
    @Column(name = "upid")
    private Integer upid;

    //名称
    @Column(name = "name")
    private String name;

    //类型
    @Column(name = "type")
    private Integer type;

    //排序
    @Column(name = "displayorder")
    private Integer displayorder;

    public void setUpid(Integer upid){
        this.upid = upid;
    }

    public Integer getUpid(){
        return upid;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setType(Integer type){
        this.type = type;
    }

    public Integer getType(){
        return type;
    }

    public void setDisplayorder(Integer displayorder){
        this.displayorder = displayorder;
    }

    public Integer getDisplayorder(){
        return displayorder;
    }
}
