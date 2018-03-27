package com.bm.wanma.entity;

import java.io.Serializable;

/**
 * 开票记录item详情
 * cm
 */
public class InvoiceRecordDetailBean implements Serializable {

	
	/* 0受理中 1处理完成 2未支付邮费 */
	private String status;
	/*税号 */
	private String taxNum;
	/*提交时间 */
	private String time;
	/*开票类型：0-个人开票 ，1-公司开票*/
	private String type;
	/* 消费金额 */
	private String amount;
	/* 区县代码 */
	private String aCode;
	/* 对公账号 */
	private String bankAcc;
	/*开户行名称 */
	private String bankName;
	private String cCode;
	/*公司地址 */
	private String compAddr;
	/*公司抬头*/
	private String compName;
	/*公司电话 */
	private String compPhone;
	/*收件地址 */
	private String conAddr;
	/*发票内容*/
	private String content;
	private String pCode;
	/*支付类型：1-支付宝支付，2-微信支付，4-货到付款 */
	private String payMod;
	/*消费记录数 */
	private String purNum;
	/* 收件人电话 */
	private String recPhone;
	/*收件人 */
	private String recipient;
	private String fAmount;


	public String getfAmount() {
		return fAmount;
	}
	public void setfAmount(String fAmount) {
		this.fAmount = fAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTaxNum() {
		return taxNum;
	}
	public void setTaxNum(String taxNum) {
		this.taxNum = taxNum;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getaCode() {
		return aCode;
	}
	public void setaCode(String aCode) {
		this.aCode = aCode;
	}
	public String getBankAcc() {
		return bankAcc;
	}
	public void setBankAcc(String bankAcc) {
		this.bankAcc = bankAcc;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getcCode() {
		return cCode;
	}
	public void setcCode(String cCode) {
		this.cCode = cCode;
	}
	public String getCompAddr() {
		return compAddr;
	}
	public void setCompAddr(String compAddr) {
		this.compAddr = compAddr;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public String getCompPhone() {
		return compPhone;
	}
	public void setCompPhone(String compPhone) {
		this.compPhone = compPhone;
	}
	public String getConAddr() {
		return conAddr;
	}
	public void setConAddr(String conAddr) {
		this.conAddr = conAddr;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getpCode() {
		return pCode;
	}
	public void setpCode(String pCode) {
		this.pCode = pCode;
	}
	public String getPayMod() {
		return payMod;
	}
	public void setPayMod(String payMod) {
		this.payMod = payMod;
	}
	public String getPurNum() {
		return purNum;
	}
	public void setPurNum(String purNum) {
		this.purNum = purNum;
	}
	public String getRecPhone() {
		return recPhone;
	}
	public void setRecPhone(String recPhone) {
		this.recPhone = recPhone;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	
	
	
}
