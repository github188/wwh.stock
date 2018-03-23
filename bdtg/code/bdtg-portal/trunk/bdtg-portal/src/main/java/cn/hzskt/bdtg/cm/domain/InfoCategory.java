package cn.hzskt.bdtg.cm.domain;
import cn.hzskt.bdtg.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
public class InfoCategory extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //父栏目编号
    private Long upId;

    //栏目编码
    private String catCode;

    //栏目名称
    private String catName;

    //1:显示，0：不显示
    private String isShow;

    //顺序
    private Integer catOrder;

    //节点
    private String treePath;

    public void setUpId(Long upId){
        this.upId = upId;
    }

    public Long getUpId(){
        return upId;
    }

    public void setCatCode(String catCode){
        this.catCode = catCode;
    }

    public String getCatCode(){
        return catCode;
    }

    public void setCatName(String catName){
        this.catName = catName;
    }

    public String getCatName(){
        return catName;
    }

    public void setIsShow(String isShow){
        this.isShow = isShow;
    }

    public String getIsShow(){
        return isShow;
    }

    public void setCatOrder(Integer catOrder){
        this.catOrder = catOrder;
    }

    public Integer getCatOrder(){
        return catOrder;
    }

    public void setTreePath(String treePath){
        this.treePath = treePath;
    }

    public String getTreePath(){
        return treePath;
    }
}
