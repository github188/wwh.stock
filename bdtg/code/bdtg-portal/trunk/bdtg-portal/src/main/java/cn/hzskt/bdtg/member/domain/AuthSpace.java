package cn.hzskt.bdtg.member.domain;
import cn.hzskt.bdtg.common.domain.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Table;
import java.text.SimpleDateFormat;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "member_auth_space")
public class AuthSpace extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //用户ID
    @Column(name = "user_id")
    private Long userId;
    //所属公司
    @Column(name = "company")
    private String company;
    //是否公开
    @Column(name = "company_sect")
    private Integer companySect;
    //用户名
    @Column(name = "user_name")
    private String userName;

    //真实姓名或企业名称
    @Column(name = "name")
    private String name;

    //身份证号或者营业执照编码
    @Column(name = "code")
    private String code;

    //身份证复印件正面照
    @Column(name = "idpic")
    private String idpic;

    //身份证复印件背面照
    @Column(name = "idpic_down")
    private String idpicDown;

    //认证状态(0:未认证1：认证通过2:认证驳回)
    @Column(name = "auth_status")
    private Integer authStatus;

    //邮箱认证状态(0:未认证1：认证通过)
    @Column(name = "email_status")
    private Integer emailStatus;

    //手机认证状态(0:未认证1：认证通过)
    @Column(name = "mobile_status")
    private Integer mobileStatus;

    //认证开始时间
    @Column(name = "auth_stime")
    private java.util.Date authStime;

    //认证结束时间
    @Column(name = "auth_etime")
    private java.util.Date authEtime;

    //所属行业
    @Column(name = "indus_pid")
    private String indusPid;

    //所属子行业
    @Column(name = "indus_id")
    private String indusId;

    //性别
    @Column(name = "sex")
    private Integer sex;

    //出生日期
    @Column(name = "birthday")
    private String birthday;

    //法人代表
    @Column(name = "legal")
    private String legal;

    //员工人数
    @Column(name = "staff_num")
    private Integer staffNum;

    //经营年数
    @Column(name = "run_years")
    private Integer runYears;

    //公司网址
    @Column(name = "url")
    private String url;

    //详细地址
    @Column(name = "address")
    private String address;

    //简介
    @Column(name = "summary")
    private String summary;

    //用户类型(0:默认值,无类型；1：个人用户；2：企业用户)
    @Column(name = "user_type")
    private Integer userType;

    //密码
    @Column(name = "password")
    private String password;

    //安全码
    @Column(name = "sec_code")
    private String secCode;

    //用户头像
    @Column(name = "user_pic")
    private String userPic;

    //用户组编号
    @Column(name = "group_id")
    private Integer groupId;

    //是否是VIP
    @Column(name = "isvip")
    private Integer isvip;

    //用户状态
    @Column(name = "status")
    private Integer status;

    //是否已婚
    @Column(name = "marry")
    private String marry;

    //出生地
    @Column(name = "hometown")
    private String hometown;

    //所在地
    @Column(name = "residency")
    private String residency;

    //传真
    @Column(name = "fax")
    private String fax;

    //技能编号
    @Column(name = "skill_ids")
    private String skillIds;

    //经历
    @Column(name = "experience")
    private String experience;

    //注册时间
    @Column(name = "reg_time")
    private java.util.Date regTime;

    //注册IP
    @Column(name = "reg_ip")
    private String regIp;

    //域名
    @Column(name = "domain")
    private String domain;

    //积分
    @Column(name = "credit")
    private Integer credit;

    //账号余额
    @Column(name = "balance")
    private Double balance;

    //账号状态
    @Column(name = "balance_status")
    private Integer balanceStatus;

    //发布数
    @Column(name = "pub_num")
    private Integer pubNum;

    //承接数
    @Column(name = "take_num")
    private Integer takeNum;

    //接受数目
    @Column(name = "accepted_num")
    private Integer acceptedNum;

    //vip开始时间
    @Column(name = "vip_start_time")
    private java.util.Date vipStartTime;

    //vip结束时间
    @Column(name = "vip_end_time")
    private java.util.Date vipEndTime;

    //支付宝
    @Column(name = "pay_zfb")
    private String payZfb;

    //财付通
    @Column(name = "pay_cft")
    private String payCft;

    //银行
    @Column(name = "pay_bank")
    private String payBank;

    //积分
    @Column(name = "score")
    private Integer score;

    //买家信誉
    @Column(name = "buyer_credit")
    private Integer buyerCredit;

    //买家好评数
    @Column(name = "buyer_good_num")
    private Integer buyerGoodNum;

    //买家等级
    @Column(name = "buyer_level")
    private String buyerLevel;

    //买家出售总数
    @Column(name = "buyer_total_num")
    private Integer buyerTotalNum;

    //卖家信誉
    @Column(name = "seller_credit")
    private Integer sellerCredit;

    //卖家好评数
    @Column(name = "seller_good_num")
    private Integer sellerGoodNum;

    //卖家等级
    @Column(name = "seller_level")
    private Integer sellerLevel;

    //卖家出售总数
    @Column(name = "seller_total_num")
    private Integer sellerTotalNum;

    //工作室编号
    @Column(name = "studio_id")
    private Integer studioId;

    //最后登陆时间
    @Column(name = "last_login_time")
    private java.util.Date lastLoginTime;

    //客户端状态
    @Column(name = "client_status")
    private Integer clientStatus;

    //推荐
    @Column(name = "recommend")
    private Integer recommend;

    //联盟
    @Column(name = "unions")
    private Integer unions;

    //联盟对象
    @Column(name = "union_assoc")
    private Integer unionAssoc;

    //联盟RID
    @Column(name = "union_rid")
    private Integer unionRid;

    //联盟用户
    @Column(name = "union_user")
    private Integer unionUser;

    //0表示没有发送，1表示已经发送
    @Column(name = "is_mail")
    private Integer isMail;

    //是否完善 1：完善
    @Column(name = "is_perfect")
    private Integer isPerfect;

    //邮箱
    @Column(name = "email")
    private String email;

    //邮箱是否公开(1:不公开；2：公开)
    @Column(name = "email_sect")
    private Integer emailSect;

    //手机号
    @Column(name = "mobile")
    private String mobile;

    //手机是否公开(1:不公开；2：公开)
    @Column(name = "mobile_sect")
    private Integer mobileSect;

    //QQ
    @Column(name = "qq")
    private String qq;

    //qq是否公开(1:不公开；2：公开)
    @Column(name = "qq_sect")
    private Integer qqSect;

    //微信
    @Column(name = "msn")
    private String msn;

    //微信是否公开(1:不公开；2：公开)
    @Column(name = "msn_sect")
    private Integer msnSect;

    //固定电话
    @Column(name = "phone")
    private String phone;

    //固定电话是否公开(1:不公开；2：公开)
    @Column(name = "phone_sect")
    private Integer phoneSect;

    //所在省
    @Column(name = "province")
    private String province;

    //所在城市
    @Column(name = "city")
    private String city;

    //所在区域
    @Column(name = "area")
    private String area;
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

    public void setIdpicDown(String idpicDown){
        this.idpicDown = idpicDown;
    }

    public String getIdpicDown(){
        return idpicDown;
    }

    public void setAuthStatus(Integer authStatus){
        this.authStatus = authStatus;
    }

    public Integer getAuthStatus(){
        return authStatus;
    }

    public Integer getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(Integer emailStatus) {
        this.emailStatus = emailStatus;
    }

    public Integer getMobileStatus() {
        return mobileStatus;
    }

    public void setMobileStatus(Integer mobileStatus) {
        this.mobileStatus = mobileStatus;
    }

    public void setAuthStime(java.util.Date authStime){
        this.authStime = authStime;
    }

    public java.util.Date getAuthStime(){
        return authStime;
    }

    public void setAuthEtime(java.util.Date authEtime){
        this.authEtime = authEtime;
    }

    public java.util.Date getAuthEtime(){
        return authEtime;
    }

    public void setIndusPid(String indusPid){
        this.indusPid = indusPid;
    }

    public String getIndusPid(){
        return indusPid;
    }

    public void setIndusId(String indusId){
        this.indusId = indusId;
    }

    public String getIndusId(){
        return indusId;
    }

    public void setSex(Integer sex){
        this.sex = sex;
    }

    public Integer getSex(){
        return sex;
    }

    public void setBirthday(String birthday){
        this.birthday = birthday;
    }

    public String getBirthday(){
        return birthday;
    }

    public void setLegal(String legal){
        this.legal = legal;
    }

    public String getLegal(){
        return legal;
    }

    public void setStaffNum(Integer staffNum){
        this.staffNum = staffNum;
    }

    public Integer getStaffNum(){
        return staffNum;
    }

    public void setRunYears(Integer runYears){
        this.runYears = runYears;
    }

    public Integer getRunYears(){
        return runYears;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return address;
    }

    public void setSummary(String summary){
        this.summary = summary;
    }

    public String getSummary(){
        return summary;
    }

    public void setUserType(Integer userType){
        this.userType = userType;
    }

    public Integer getUserType(){
        return userType;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setSecCode(String secCode){
        this.secCode = secCode;
    }

    public String getSecCode(){
        return secCode;
    }

    public void setUserPic(String userPic){
        this.userPic = userPic;
    }

    public String getUserPic(){
        return userPic;
    }

    public void setGroupId(Integer groupId){
        this.groupId = groupId;
    }

    public Integer getGroupId(){
        return groupId;
    }

    public void setIsvip(Integer isvip){
        this.isvip = isvip;
    }

    public Integer getIsvip(){
        return isvip;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public Integer getStatus(){
        return status;
    }

    public void setMarry(String marry){
        this.marry = marry;
    }

    public String getMarry(){
        return marry;
    }

    public void setHometown(String hometown){
        this.hometown = hometown;
    }

    public String getHometown(){
        return hometown;
    }

    public void setResidency(String residency){
        this.residency = residency;
    }

    public String getResidency(){
        return residency;
    }

    public void setFax(String fax){
        this.fax = fax;
    }

    public String getFax(){
        return fax;
    }

    public void setSkillIds(String skillIds){
        this.skillIds = skillIds;
    }

    public String getSkillIds(){
        return skillIds;
    }

    public void setExperience(String experience){
        this.experience = experience;
    }

    public String getExperience(){
        return experience;
    }

    public void setRegTime(java.util.Date regTime){
        this.regTime = regTime;
    }

    public java.util.Date getRegTime(){
        return regTime;
    }

    public void setRegIp(String regIp){
        this.regIp = regIp;
    }

    public String getRegIp(){
        return regIp;
    }

    public void setDomain(String domain){
        this.domain = domain;
    }

    public String getDomain(){
        return domain;
    }

    public void setCredit(Integer credit){
        this.credit = credit;
    }

    public Integer getCredit(){
        return credit;
    }

    public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public void setBalanceStatus(Integer balanceStatus){
        this.balanceStatus = balanceStatus;
    }

    public Integer getBalanceStatus(){
        return balanceStatus;
    }

    public void setPubNum(Integer pubNum){
        this.pubNum = pubNum;
    }

    public Integer getPubNum(){
        return pubNum;
    }

    public void setTakeNum(Integer takeNum){
        this.takeNum = takeNum;
    }

    public Integer getTakeNum(){
        return takeNum;
    }

    public void setAcceptedNum(Integer acceptedNum){
        this.acceptedNum = acceptedNum;
    }

    public Integer getAcceptedNum(){
        return acceptedNum;
    }

    public void setVipStartTime(java.util.Date vipStartTime){
        this.vipStartTime = vipStartTime;
    }

    public java.util.Date getVipStartTime(){
        return vipStartTime;
    }

    public void setVipEndTime(java.util.Date vipEndTime){
        this.vipEndTime = vipEndTime;
    }

    public java.util.Date getVipEndTime(){
        return vipEndTime;
    }

    public void setPayZfb(String payZfb){
        this.payZfb = payZfb;
    }

    public String getPayZfb(){
        return payZfb;
    }

    public void setPayCft(String payCft){
        this.payCft = payCft;
    }

    public String getPayCft(){
        return payCft;
    }

    public void setPayBank(String payBank){
        this.payBank = payBank;
    }

    public String getPayBank(){
        return payBank;
    }

    public void setScore(Integer score){
        this.score = score;
    }

    public Integer getScore(){
        return score;
    }

    public void setBuyerCredit(Integer buyerCredit){
        this.buyerCredit = buyerCredit;
    }

    public Integer getBuyerCredit(){
        return buyerCredit;
    }

    public void setBuyerGoodNum(Integer buyerGoodNum){
        this.buyerGoodNum = buyerGoodNum;
    }

    public Integer getBuyerGoodNum(){
        return buyerGoodNum;
    }

    public void setBuyerLevel(String buyerLevel){
        this.buyerLevel = buyerLevel;
    }

    public String getBuyerLevel(){
        return buyerLevel;
    }

    public void setBuyerTotalNum(Integer buyerTotalNum){
        this.buyerTotalNum = buyerTotalNum;
    }

    public Integer getBuyerTotalNum(){
        return buyerTotalNum;
    }

    public void setSellerCredit(Integer sellerCredit){
        this.sellerCredit = sellerCredit;
    }

    public Integer getSellerCredit(){
        return sellerCredit;
    }

    public void setSellerGoodNum(Integer sellerGoodNum){
        this.sellerGoodNum = sellerGoodNum;
    }

    public Integer getSellerGoodNum(){
        return sellerGoodNum;
    }

    public void setSellerLevel(Integer sellerLevel){
        this.sellerLevel = sellerLevel;
    }

    public Integer getSellerLevel(){
        return sellerLevel;
    }

    public void setSellerTotalNum(Integer sellerTotalNum){
        this.sellerTotalNum = sellerTotalNum;
    }

    public Integer getSellerTotalNum(){
        return sellerTotalNum;
    }

    public void setStudioId(Integer studioId){
        this.studioId = studioId;
    }

    public Integer getStudioId(){
        return studioId;
    }

    public void setLastLoginTime(java.util.Date lastLoginTime){
        this.lastLoginTime = lastLoginTime;
    }

    public java.util.Date getLastLoginTime(){
        return  lastLoginTime;
    }

    public void setClientStatus(Integer clientStatus){
        this.clientStatus = clientStatus;
    }

    public Integer getClientStatus(){
        return clientStatus;
    }

    public void setRecommend(Integer recommend){
        this.recommend = recommend;
    }

    public Integer getRecommend(){
        return recommend;
    }

    public Integer getUnions() {
        return unions;
    }

    public void setUnions(Integer unions) {
        this.unions = unions;
    }

    public void setUnionAssoc(Integer unionAssoc){
        this.unionAssoc = unionAssoc;
    }

    public Integer getUnionAssoc(){
        return unionAssoc;
    }

    public void setUnionRid(Integer unionRid){
        this.unionRid = unionRid;
    }

    public Integer getUnionRid(){
        return unionRid;
    }

    public void setUnionUser(Integer unionUser){
        this.unionUser = unionUser;
    }

    public Integer getUnionUser(){
        return unionUser;
    }

    public void setIsMail(Integer isMail){
        this.isMail = isMail;
    }

    public Integer getIsMail(){
        return isMail;
    }

    public void setIsPerfect(Integer isPerfect){
        this.isPerfect = isPerfect;
    }

    public Integer getIsPerfect(){
        return isPerfect;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setEmailSect(Integer emailSect){
        this.emailSect = emailSect;
    }

    public Integer getEmailSect(){
        return emailSect;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }

    public String getMobile(){
        return mobile;
    }

    public void setMobileSect(Integer mobileSect){
        this.mobileSect = mobileSect;
    }

    public Integer getMobileSect(){
        return mobileSect;
    }

    public void setQq(String qq){
        this.qq = qq;
    }

    public String getQq(){
        return qq;
    }

    public void setQqSect(Integer qqSect){
        this.qqSect = qqSect;
    }

    public Integer getQqSect(){
        return qqSect;
    }

    public void setMsn(String msn){
        this.msn = msn;
    }

    public String getMsn(){
        return msn;
    }

    public void setMsnSect(Integer msnSect){
        this.msnSect = msnSect;
    }

    public Integer getMsnSect(){
        return msnSect;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhoneSect(Integer phoneSect){
        this.phoneSect = phoneSect;
    }

    public Integer getPhoneSect(){
        return phoneSect;
    }

    public void setProvince(String province){
        this.province = province;
    }

    public String getProvince(){
        return province;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getCity(){
        return city;
    }

    public void setArea(String area){
        this.area = area;
    }

    public String getArea(){
        return area;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getCompanySect() {
        return companySect;
    }

    public void setCompanySect(Integer companySect) {
        this.companySect = companySect;
    }
}
