package com.skoo.stock.login.domain;

import com.skoo.orm.domain.BaseEntity;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("UserInfo")
public class UserInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID 加S表示内部员工 *
     */
    public String ids;

    /**
     * 用户ID *
     */
    public String userId;

    /**
     * 用户名 *
     */
    public String userName;

    /**
     * 用户类型 *
     */
    public String userType;

    /**
     * 企业ID *
     */
    public Long assocId;

    /**
     * 企业Logo *
     */
    public String logo;

    /**
     * 统一用户ID *
     */
    public Long orgId;

    /**
     * 所属部门 *
     */
    public Long deptId;

    /**
     * 所属部门 *
     */
    public int topDeptId;

    /**
     * 域名 *
     */
    public String domain;

    /**
     * 域名 *
     */
    public String memberType;

    /**
     * 角色ID *
     */
    public String roleId;

    /**
     * 是否会员 *
     */
    public String ismember;

    /**
     * 支付状态 *
     */
    public String chargests;

    /**
     * 协会名称 *
     */
    public String assName;


    /**
     * 是否内部员工 1：内部员工*
     */
    public String isstaff;

    /**
     * 会员类型：1：个人会员 2：企业会员
     */
    public String mmbType;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Long getAssocId() {
        return assocId;
    }

    public void setAssocId(Long assocId) {
        this.assocId = assocId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getIsmember() {
        return ismember;
    }

    public void setIsmember(String ismember) {
        this.ismember = ismember;
    }

    public String getChargests() {
        return chargests;
    }

    public void setChargests(String chargests) {
        this.chargests = chargests;
    }

    public String getAssName() {
        return assName;
    }

    public void setAssName(String assName) {
        this.assName = assName;
    }

    public String getIsstaff() {
        return isstaff;
    }

    public void setIsstaff(String isstaff) {
        this.isstaff = isstaff;
    }

    public String getMmbType() {
        return mmbType;
    }

    public void setMmbType(String mmbType) {
        this.mmbType = mmbType;
    }

    public int getTopDeptId() {
        return topDeptId;
    }

    public void setTopDeptId(int topDeptId) {
        this.topDeptId = topDeptId;
    }
}
