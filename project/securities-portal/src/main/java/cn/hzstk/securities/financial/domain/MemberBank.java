package cn.hzstk.securities.financial.domain;
import java.util.Date;

import cn.hzstk.securities.common.domain.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "financial_member_bank")
public class MemberBank extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //用户编号
    @Column(name = "uid")
    private Integer uid;

    //用户名
    @Column(name = "real_name")
    private String realName;

    //企业机构名称
    @Column(name = "company")
    private String company;

    //身份证号码
    @Column(name = "card_id")
    private String cardId;

    //银行名
    @Column(name = "bank_name")
    private String bankName;

    //银行地址
    @Column(name = "bank_address")
    private String bankAddress;

    //支行名称
    @Column(name = "bank_sub_name")
    private String bankSubName;

    //银行卡号
    @Column(name = "card_num")
    private String cardNum;

    //'银行类型（1个人，2企业，3线上）' 
    @Column(name = "bank_type")
    private Integer bankType;

    //绑定银行状态
    @Column(name = "bind_status")
    private Integer bindStatus;

    //时间
    @Column(name = "on_time")
    private Date onTime;

    //银行全称
    @Column(name = "bank_full_name")
    private String bankFullName;

    public void setUid(Integer uid){
        this.uid = uid;
    }

    public Integer getUid(){
        return uid;
    }

    public void setRealName(String realName){
        this.realName = realName;
    }

    public String getRealName(){
        return realName;
    }

    public void setCompany(String company){
        this.company = company;
    }

    public String getCompany(){
        return company;
    }

    public void setCardId(String cardId){
        this.cardId = cardId;
    }

    public String getCardId(){
        return cardId;
    }

    public void setBankName(String bankName){
        this.bankName = bankName;
    }

    public String getBankName(){
        return bankName;
    }

    public void setBankAddress(String bankAddress){
        this.bankAddress = bankAddress;
    }

    public String getBankAddress(){
        return bankAddress;
    }

    public void setBankSubName(String bankSubName){
        this.bankSubName = bankSubName;
    }

    public String getBankSubName(){
        return bankSubName;
    }

    public void setCardNum(String cardNum){
        this.cardNum = cardNum;
    }

    public String getCardNum(){
        return cardNum;
    }

    public void setBankType(Integer bankType){
        this.bankType = bankType;
    }

    public Integer getBankType(){
        return bankType;
    }

    public void setBindStatus(Integer bindStatus){
        this.bindStatus = bindStatus;
    }

    public Integer getBindStatus(){
        return bindStatus;
    }

    public Date getOnTime() {
		return onTime;
	}

	public void setOnTime(Date onTime) {
		this.onTime = onTime;
	}

	public void setBankFullName(String bankFullName){
        this.bankFullName = bankFullName;
    }

    public String getBankFullName(){
        return bankFullName;
    }
}
