package cn.hzstk.securities.member.domain;
import cn.hzstk.securities.common.domain.BaseDomain;
import cn.hzstk.securities.sys.utils.json.Dict;

import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "member_auth_company")
public class AuthCompany extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //企业名称
    @Column(name = "name")
    private String name;

    //企业登记注册号码
    @Column(name = "code")
    private String code;

    //营业执照照片
    @Column(name = "idpic")
    private String idpic;

    //关联用户ID
    @Column(name = "user_id")
    private Long userId;

    //关联用户名
    @Column(name = "user_name")
    private String userName;

    //0待审核；1审核通过；2审核驳回
    @Column(name = "auth_status")
    @Dict(key = "auth_status")
    private Integer authStatus;

    //
    @Column(name = "create_Date")
    private java.util.Date createDate;

    //
    @Column(name = "update_Date")
    private java.util.Date updateDate;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public void setIdpic(String idpic){
        this.idpic = idpic;
    }

    public String getIdpic(){
        return idpic;
    }

    public void setUserId(Long userId){
        this.userId = userId;
    }

    public Long getUserId(){
        return userId;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }

    public void setAuthStatus(Integer authStatus){
        this.authStatus = authStatus;
    }

    public Integer getAuthStatus(){
        return authStatus;
    }

    public void setCreateDate(java.util.Date createDate){
        this.createDate = createDate;
    }

    public java.util.Date getCreateDate(){
        return createDate;
    }

    public void setUpdateDate(java.util.Date updateDate){
        this.updateDate = updateDate;
    }

    public java.util.Date getUpdateDate(){
        return updateDate;
    }
}
