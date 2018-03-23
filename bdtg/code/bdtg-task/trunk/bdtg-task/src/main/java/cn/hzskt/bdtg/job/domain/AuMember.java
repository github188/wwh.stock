package cn.hzskt.bdtg.job.domain;

import cn.hzskt.bdtg.common.domain.BaseDomain;
import cn.hzskt.bdtg.sys.utils.json.Dict;


/**
* @description:
* @author: autoCode
* @history:
*/
public class AuMember extends BaseDomain {

    private static final long serialVersionUID = 1L;

    //用户ID
    private Long userId;

    //用户名
    private String userName;
    //所属公司
    private String company;

    //真实姓名或企业名称
    private String name;

    //认证状态(0:未认证1：认证通过2认证驳回)
    private Integer authStatus;
    //用户类型
    @Dict(key = "mb_type")
    private Integer mbType ;

    //用户类型(0:默认值,无类型；1：个人用户；2：企业用户)
    private Integer userType;

    //邮箱
    private String email;

    //手机号
    private String mobile;

    //QQ
    private String qq;
    //专业
    private Long major;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Integer authStatus) {
        this.authStatus = authStatus;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Long getMajor() {
        return major;
    }

    public void setMajor(Long major) {
        this.major = major;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getMbType() {
        return mbType;
    }

    public void setMbType(Integer mbType) {
        this.mbType = mbType;
    }
}
