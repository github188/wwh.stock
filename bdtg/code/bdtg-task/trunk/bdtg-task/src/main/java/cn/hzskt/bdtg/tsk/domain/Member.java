package cn.hzskt.bdtg.tsk.domain;

import cn.hzskt.bdtg.common.domain.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "s_user")
public class Member extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //用户名
    @Column(name = "name")
    private String name;

    //密码
    @Column(name = "password")
    private String password;

    //类型
    @Column(name = "type")
    private String type;

    //姓名
    @Column(name = "viewname")
    private String viewname;

    //手机号码
    @Column(name = "phone")
    private String phone;

    //邮箱
    @Column(name = "email")
    private String email;

    //帐号有效期
    @Column(name = "valid_date")
    private java.util.Date validDate;

    //登录号
    @Column(name = "login_id")
    private String loginId;

    //登录时间
    @Column(name = "login_date")
    private java.util.Date loginDate;

    //最近登录时间
    @Column(name = "server_login_date")
    private java.util.Date serverLoginDate;

    //密码修改时间
    @Column(name = "pwd_mdf_date")
    private java.util.Date pwdMdfDate;

    //编制
    @Column(name = "workout")
    private String workout;

    //锁定
    @Column(name = "locked")
    private String locked;

    //创建人
    @Column(name = "create_user_id")
    private Integer createUserId;

    //修改人
    @Column(name = "modify_user_id")
    private Integer modifyUserId;

    //修改时间
    @Column(name = "modify_date")
    private java.util.Date modifyDate;

    //状态H:离职,Y：有效,N：无效
    @Column(name = "sts")
    private String sts;

    //扩展_企业ID
    @Column(name = "ext_corp_id")
    private String extCorpId;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public void setViewname(String viewname){
        this.viewname = viewname;
    }

    public String getViewname(){
        return viewname;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getPhone(){
        return phone;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setValidDate(java.util.Date validDate){
        this.validDate = validDate;
    }

    public java.util.Date getValidDate(){
        return validDate;
    }

    public void setLoginId(String loginId){
        this.loginId = loginId;
    }

    public String getLoginId(){
        return loginId;
    }

    public void setLoginDate(java.util.Date loginDate){
        this.loginDate = loginDate;
    }

    public java.util.Date getLoginDate(){
        return loginDate;
    }

    public void setServerLoginDate(java.util.Date serverLoginDate){
        this.serverLoginDate = serverLoginDate;
    }

    public java.util.Date getServerLoginDate(){
        return serverLoginDate;
    }

    public void setPwdMdfDate(java.util.Date pwdMdfDate){
        this.pwdMdfDate = pwdMdfDate;
    }

    public java.util.Date getPwdMdfDate(){
        return pwdMdfDate;
    }

    public void setWorkout(String workout){
        this.workout = workout;
    }

    public String getWorkout(){
        return workout;
    }

    public void setLocked(String locked){
        this.locked = locked;
    }

    public String getLocked(){
        return locked;
    }

    public void setCreateUserId(Integer createUserId){
        this.createUserId = createUserId;
    }

    public Integer getCreateUserId(){
        return createUserId;
    }

    public void setModifyUserId(Integer modifyUserId){
        this.modifyUserId = modifyUserId;
    }

    public Integer getModifyUserId(){
        return modifyUserId;
    }

    public void setModifyDate(java.util.Date modifyDate){
        this.modifyDate = modifyDate;
    }

    public java.util.Date getModifyDate(){
        return modifyDate;
    }

    public void setSts(String sts){
        this.sts = sts;
    }

    public String getSts(){
        return sts;
    }

    public void setExtCorpId(String extCorpId){
        this.extCorpId = extCorpId;
    }

    public String getExtCorpId(){
        return extCorpId;
    }
}
