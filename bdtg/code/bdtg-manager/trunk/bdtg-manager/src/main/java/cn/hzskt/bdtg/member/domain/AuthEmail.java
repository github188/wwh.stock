package cn.hzskt.bdtg.member.domain;
import cn.hzskt.bdtg.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "member_auth_email")
public class AuthEmail extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //邮箱地址
    @Column(name = "email")
    private String email;

    //验证地址
    @Column(name = "check_url")
    private String checkUrl;

    //随机编码
    @Column(name = "ucode")
    private String ucode;

    //关联用户ID
    @Column(name = "user_id")
    private Long userId;

    //关联用户名
    @Column(name = "user_name")
    private String userName;

    //0待审核；1审核通过；2审核驳回
    @Column(name = "email_status")
    private Integer emailStatus;

    //
    @Column(name = "create_Date")
    private java.util.Date createDate;

    //
    @Column(name = "update_Date")
    private java.util.Date updateDate;

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setCheckUrl(String checkUrl){
        this.checkUrl = checkUrl;
    }

    public String getCheckUrl(){
        return checkUrl;
    }

    public void setUcode(String ucode){
        this.ucode = ucode;
    }

    public String getUcode(){
        return ucode;
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

    public void setEmailStatus(Integer emailStatus){
        this.emailStatus = emailStatus;
    }

    public Integer getEmailStatus(){
        return emailStatus;
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
