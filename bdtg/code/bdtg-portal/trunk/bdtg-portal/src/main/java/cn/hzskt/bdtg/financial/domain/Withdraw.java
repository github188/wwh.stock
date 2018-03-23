package cn.hzskt.bdtg.financial.domain;
import cn.hzskt.bdtg.common.domain.BaseDomain;
import cn.hzskt.bdtg.sys.utils.json.Dict;

import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "financial_withdraw")
public class Withdraw extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //提现金额
    @Column(name = "withdraw_cash")
    private Double withdrawCash;

    //提现用户编号
    @Column(name = "uid")
    private Integer uid;

    //提现用户名
    @Column(name = "username")
    private String username;

    //收款账户人名
    @Column(name = "pay_username")
    private String payUsername;

    //提现状态
    @Column(name = "withdraw_status")
    @Dict(key="withdraw_status")
    private Integer withdrawStatus;

    //申请时间
    @Column(name = "applic_time")
    private java.util.Date applicTime;

    //受理人编号
    @Column(name = "process_uid")
    private Integer processUid;

    //受理人用户名
    @Column(name = "process_username")
    private String processUsername;

    //受理时间
    @Column(name = "process_time")
    private java.util.Date processTime;

    //提现账号
    @Column(name = "pay_account")
    private String payAccount;

    //提现类型
    @Column(name = "pay_type")
    private String payType;

    //提现手续费
    @Column(name = "fee")
    private Integer fee;

    public Double getWithdrawCash() {
		return withdrawCash;
	}

	public void setWithdrawCash(Double withdrawCash) {
		this.withdrawCash = withdrawCash;
	}

	public void setUid(Integer uid){
        this.uid = uid;
    }

    public Integer getUid(){
        return uid;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setPayUsername(String payUsername){
        this.payUsername = payUsername;
    }

    public String getPayUsername(){
        return payUsername;
    }

    public void setWithdrawStatus(Integer withdrawStatus){
        this.withdrawStatus = withdrawStatus;
    }

    public Integer getWithdrawStatus(){
        return withdrawStatus;
    }

    public void setApplicTime(java.util.Date applicTime){
        this.applicTime = applicTime;
    }

    public java.util.Date getApplicTime(){
        return applicTime;
    }

    public void setProcessUid(Integer processUid){
        this.processUid = processUid;
    }

    public Integer getProcessUid(){
        return processUid;
    }

    public void setProcessUsername(String processUsername){
        this.processUsername = processUsername;
    }

    public String getProcessUsername(){
        return processUsername;
    }

    public void setProcessTime(java.util.Date processTime){
        this.processTime = processTime;
    }

    public java.util.Date getProcessTime(){
        return processTime;
    }

    public void setPayAccount(String payAccount){
        this.payAccount = payAccount;
    }

    public String getPayAccount(){
        return payAccount;
    }

    public void setPayType(String payType){
        this.payType = payType;
    }

    public String getPayType(){
        return payType;
    }

    public void setFee(Integer fee){
        this.fee = fee;
    }

    public Integer getFee(){
        return fee;
    }
}
