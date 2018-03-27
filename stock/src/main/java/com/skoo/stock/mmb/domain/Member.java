package com.skoo.stock.mmb.domain;
import com.skoo.orm.domain.BaseEntity;
import com.skoo.stock.sys.utils.json.DictAnnotation;
import org.apache.ibatis.type.Alias;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("Member")
public class Member extends BaseEntity {

	private static final long serialVersionUID = 1L;


	/**
	 * 账号（邮箱）*
	 */
	private String email;

	/**
	 * 密码*
	 */
	private String password;

	/**
	 * 注册类型*
	 */
	@DictAnnotation(key = "member_type")
	private String registrationType;

	/**
	 * 用户区域*
	 */
	private String userArea;

	/**
	 * 手机号码*
	 */
	private String mobileNumber;

	/**
	 * 真实姓名*
	 */
	private String realName;

	/**
	 * 身份证号*
	 */
	private String idNumber;

	/**
	 * 联系地址*
	 */
	private String address;

	/**
	 * 所在地类型*
	 */
	private String locationType;

	/**
	 * 所在地*
	 */
	private String location;

	/** **/
	private String organization;

	/**
	 * 行业类别*
	 */
	private String industry;

	/**
	 * 联系人*
	 */
	private String contact;

	/** **/
	private String phone;

	/** **/
	private String fax;

	/**
	 * 单位简介*
	 */
	private String synopsis;

	/**
	 * 营业执照*
	 */
	private String businessLicense;

	/**
	 * 组织机构代码证*
	 */
	private String organizationCodeCertificate;

	/** **/
	private String officialLetter;

	/**
	 * 激活码*
	 */
	private String activation;

	/** **/
	@DictAnnotation(key = "mmb_sts")
	private String status;

	/**
	 * 会员等级*
	 */
	private String mmbLevel;

	/**
	 * 排序*
	 */
	private Integer orderBy;

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}

	public String getRegistrationType() {
		return registrationType;
	}

	public void setUserArea(String userArea) {
		this.userArea = userArea;
	}

	public String getUserArea() {
		return userArea;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRealName() {
		return realName;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOrganization() {
		return organization;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getIndustry() {
		return industry;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContact() {
		return contact;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getFax() {
		return fax;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setOrganizationCodeCertificate(String organizationCodeCertificate) {
		this.organizationCodeCertificate = organizationCodeCertificate;
	}

	public String getOrganizationCodeCertificate() {
		return organizationCodeCertificate;
	}

	public void setOfficialLetter(String officialLetter) {
		this.officialLetter = officialLetter;
	}

	public String getOfficialLetter() {
		return officialLetter;
	}

	public void setActivation(String activation) {
		this.activation = activation;
	}

	public String getActivation() {
		return activation;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setMmbLevel(String mmbLevel) {
		this.mmbLevel = mmbLevel;
	}

	public String getMmbLevel() {
		return mmbLevel;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getOrderBy() {
		return orderBy;
	}
	}
