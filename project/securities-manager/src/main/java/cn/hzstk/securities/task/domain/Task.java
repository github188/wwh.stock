package cn.hzstk.securities.task.domain;

import javax.persistence.Column;
import javax.persistence.Table;

import cn.hzstk.securities.common.domain.BaseDomain;

@Table(name = "task_content")
public class Task extends BaseDomain {

	private static final long serialVersionUID = 1L;

	/** 模型编号 **/
	@Column(name = "model_id")
	private String modelId;

	/** 稿件数量 **/
	@Column(name = "work_count")
	private Integer workCount;

	/** 稿件单价 **/
	@Column(name = "single_cash")
	private Double singleCash;

	/** 提成比例 **/
	@Column(name = "profit_rate")
	private Integer profitRate;

	/** 失败提成比例 **/
	@Column(name = "task_fail_rate")
	private Integer taskFailRate;

	/** 任务状态 0、未付款 1、待审核 2、投标中 3、选标中 4、工作中 7、冻结 8、结束 9、失败 10、审核失败 **/
	@Column(name = "task_status")
	private Integer taskStatus;

	/** 任务标题 **/
	@Column(name = "task_title")
	private String taskTitle;

	/** 任务描述 **/
	@Column(name = "task_desc")
	private String taskDesc;

	/** 任务附件 **/
	@Column(name = "task_file")
	private String taskFile;

	/** 任务图片 **/
	@Column(name = "task_pic")
	private String taskPic;

	/** 行业编号 **/
	@Column(name = "indus_id")
	private Integer indusId;

	/** 父行业编号 **/
	@Column(name = "indus_pid")
	private Integer indusPid;

	/** 用户编号 **/
	@Column(name = "uid")
	private Integer uid;

	/** 用户名 **/
	@Column(name = "username")
	private String username;

	/** 开始时间 **/
	@Column(name = "start_time")
	private Integer startTime;

	/** 交稿/选稿截止时间 **/
	@Column(name = "sub_time")
	private Integer subTime;

	/** 任务截止时间 **/
	@Column(name = "end_time")
	private Integer endTime;

	/** 公示截止时间 **/
	@Column(name = "sp_end_time")
	private Integer spEndTime;

	/** 城市地区 **/
	@Column(name = "city")
	private String city;

	/** 任务赏金 **/
	@Column(name = "task_cash")
	private Integer taskCash;

	/** 实际金额 **/
	@Column(name = "real_cash")
	private Integer realCash;

	/** 金额区间 **/
	@Column(name = "task_cash_coverage")
	private Integer taskCashCoverage;

	/** 金额花费 **/
	@Column(name = "cash_cost")
	private Integer cashCost;

	/** 代金券花费 **/
	@Column(name = "credit_cost")
	private Integer creditCost;

	/** 查看次数 **/
	@Column(name = "view_num")
	private Integer viewNum;

	/** 投稿次数 **/
	@Column(name = "work_num")
	private Integer workNum;

	/** 留言次数 **/
	@Column(name = "leave_num")
	private Integer leaveNum;

	/** 关注次数 **/
	@Column(name = "focus_num")
	private Integer focusNum;

	/** 互评次数 **/
	@Column(name = "mark_num")
	private Integer markNum;

	/** **/
	@Column(name = "is_delineas")
	private Integer isDelineas;

	/** 客服UID **/
	@Column(name = "kf_uid")
	private Integer kfUid;

	/** 付费项 **/
	@Column(name = "pay_item")
	private String payItem;

	/** 增值花费 **/
	@Column(name = "att_cash")
	private Integer attCash;

	/** 联系方式 **/
	@Column(name = "contact")
	private String contact;

	/** 唯一编号 **/
	@Column(name = "unique_num")
	private String uniqueNum;

	/** 补充需求的时间 **/
	@Column(name = "ext_time")
	private Integer extTime;

	/** 补充的需求描述 **/
	@Column(name = "ext_desc")
	private String extDesc;

	/** 联盟任务标识 **/
	@Column(name = "task_union")
	private Integer taskUnion;

	/** 支付宝托管 **/
	@Column(name = "alipay_trust")
	private Integer alipayTrust;

	/** 是否延期 **/
	@Column(name = "is_delay")
	private Integer isDelay;

	/** 联盟任务编号 **/
	@Column(name = "r_task_id")
	private Integer rTaskId;

	/** 是否托管 **/
	@Column(name = "is_trust")
	private Integer isTrust;

	/** 托管类型 **/
	@Column(name = "trust_type")
	private String trustType;

	/** 是否置顶 **/
	@Column(name = "is_top")
	private Integer isTop;

	/** 是否自动选稿 **/
	@Column(name = "is_auto_bid")
	private Integer isAutoBid;

	/** 任务坐标 **/
	@Column(name = "point")
	private String point;

	/** 增值项购买时间 **/
	@Column(name = "payitem_time")
	private String payitemTime;

	/** 同意协议 **/
	@Column(name = "age_requirement")
	private Integer ageRequirement;

	/** SEO标题 **/
	@Column(name = "seo_title")
	private String seoTitle;

	/** SEO关键字 **/
	@Column(name = "seo_keyword")
	private String seoKeyword;

	/** SEO描述 **/
	@Column(name = "seo_desc")
	private String seoDesc;

	/** 省份 **/
	@Column(name = "province")
	private Integer province;

	/** 地区 **/
	@Column(name = "area")
	private Integer area;

	/** 任务置顶状态 **/
	@Column(name = "tasktop")
	private Integer tasktop;

	/** 任务加急状态 **/
	@Column(name = "urgent")
	private Integer urgent;

	/** 屏蔽搜索引擎状态 **/
	@Column(name = "seohide")
	private Integer seohide;

	/** 稿件隐藏状态 **/
	@Column(name = "workhide")
	private Integer workhide;

	/** 明确预算 **/
	@Column(name = "budget")
	private Integer budget;

	/** 1,开启工作协同 2，关闭工作协同 **/
	@Column(name = "teamwork")
	private Integer teamwork;

	/** 是否返还 **/
	@Column(name = "is_back")
	private Integer isBack;

	/** 赏金类型 **/
	@Column(name = "goldtype")
	private String goldtype;

	/** 礼品名称 **/
	@Column(name = "giftname")
	private String giftname;

	/** 担保金额 **/
	@Column(name = "guarantee")
	private Double guarantee;

	/** 产品 多个记录以逗号分隔 **/
	@Column(name = "product")
	private String product;

	/** 专业 多个记录以逗号分隔 **/
	@Column(name = "profession")
	private String profession;

	/** 人才选择模式 1、个人 2、团队 3、企业 **/
	@Column(name = "talentType")
	private String talentType;

	/** 个人要求 **/
	@Column(name = "talent_person")
	private String talentPerson;

	/** 团队要求 **/
	@Column(name = "talent_team")
	private String talentTeam;

	/** 团队人员 **/
	@Column(name = "teamNum")
	private String teamNum;

	/** 企业要求 **/
	@Column(name = "talent_ent")
	private String talentEnt;

	/**
	 * 企业支付方式,多种方式以逗号分隔
	 */
	@Column(name = "ent_payment")
	private String entPayment;

	/**
	 * 佣金支付方式 1、全额 2、分期
	 */
	@Column(name = "crash_payType")
	private String crashPayType;

	/**
	 * 分期付款分期数
	 */
	@Column(name = "crash_payNum")
	private String crashPayNum;

	/**
	 * 分期付款百分比，多条记录以逗号分隔
	 */
	@Column(name = "crash_pay_stages")
	private String crashPayStages;

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public Integer getWorkCount() {
		return workCount;
	}

	public void setWorkCount(Integer workCount) {
		this.workCount = workCount;
	}

	public Double getSingleCash() {
		return singleCash;
	}

	public void setSingleCash(Double singleCash) {
		this.singleCash = singleCash;
	}

	public Integer getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(Integer profitRate) {
		this.profitRate = profitRate;
	}

	public Integer getTaskFailRate() {
		return taskFailRate;
	}

	public void setTaskFailRate(Integer taskFailRate) {
		this.taskFailRate = taskFailRate;
	}

	public Integer getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public String getTaskFile() {
		return taskFile;
	}

	public void setTaskFile(String taskFile) {
		this.taskFile = taskFile;
	}

	public String getTaskPic() {
		return taskPic;
	}

	public void setTaskPic(String taskPic) {
		this.taskPic = taskPic;
	}

	public Integer getIndusId() {
		return indusId;
	}

	public void setIndusId(Integer indusId) {
		this.indusId = indusId;
	}

	public Integer getIndusPid() {
		return indusPid;
	}

	public void setIndusPid(Integer indusPid) {
		this.indusPid = indusPid;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getSubTime() {
		return subTime;
	}

	public void setSubTime(Integer subTime) {
		this.subTime = subTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public Integer getSpEndTime() {
		return spEndTime;
	}

	public void setSpEndTime(Integer spEndTime) {
		this.spEndTime = spEndTime;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getTaskCash() {
		return taskCash;
	}

	public void setTaskCash(Integer taskCash) {
		this.taskCash = taskCash;
	}

	public Integer getRealCash() {
		return realCash;
	}

	public void setRealCash(Integer realCash) {
		this.realCash = realCash;
	}

	public Integer getTaskCashCoverage() {
		return taskCashCoverage;
	}

	public void setTaskCashCoverage(Integer taskCashCoverage) {
		this.taskCashCoverage = taskCashCoverage;
	}

	public Integer getCashCost() {
		return cashCost;
	}

	public void setCashCost(Integer cashCost) {
		this.cashCost = cashCost;
	}

	public Integer getCreditCost() {
		return creditCost;
	}

	public void setCreditCost(Integer creditCost) {
		this.creditCost = creditCost;
	}

	public Integer getViewNum() {
		return viewNum;
	}

	public void setViewNum(Integer viewNum) {
		this.viewNum = viewNum;
	}

	public Integer getWorkNum() {
		return workNum;
	}

	public void setWorkNum(Integer workNum) {
		this.workNum = workNum;
	}

	public Integer getLeaveNum() {
		return leaveNum;
	}

	public void setLeaveNum(Integer leaveNum) {
		this.leaveNum = leaveNum;
	}

	public Integer getFocusNum() {
		return focusNum;
	}

	public void setFocusNum(Integer focusNum) {
		this.focusNum = focusNum;
	}

	public Integer getMarkNum() {
		return markNum;
	}

	public void setMarkNum(Integer markNum) {
		this.markNum = markNum;
	}

	public Integer getIsDelineas() {
		return isDelineas;
	}

	public void setIsDelineas(Integer isDelineas) {
		this.isDelineas = isDelineas;
	}

	public Integer getKfUid() {
		return kfUid;
	}

	public void setKfUid(Integer kfUid) {
		this.kfUid = kfUid;
	}

	public String getPayItem() {
		return payItem;
	}

	public void setPayItem(String payItem) {
		this.payItem = payItem;
	}

	public Integer getAttCash() {
		return attCash;
	}

	public void setAttCash(Integer attCash) {
		this.attCash = attCash;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getUniqueNum() {
		return uniqueNum;
	}

	public void setUniqueNum(String uniqueNum) {
		this.uniqueNum = uniqueNum;
	}

	public Integer getExtTime() {
		return extTime;
	}

	public void setExtTime(Integer extTime) {
		this.extTime = extTime;
	}

	public String getExtDesc() {
		return extDesc;
	}

	public void setExtDesc(String extDesc) {
		this.extDesc = extDesc;
	}

	public Integer getTaskUnion() {
		return taskUnion;
	}

	public void setTaskUnion(Integer taskUnion) {
		this.taskUnion = taskUnion;
	}

	public Integer getAlipayTrust() {
		return alipayTrust;
	}

	public void setAlipayTrust(Integer alipayTrust) {
		this.alipayTrust = alipayTrust;
	}

	public Integer getIsDelay() {
		return isDelay;
	}

	public void setIsDelay(Integer isDelay) {
		this.isDelay = isDelay;
	}

	public Integer getrTaskId() {
		return rTaskId;
	}

	public void setrTaskId(Integer rTaskId) {
		this.rTaskId = rTaskId;
	}

	public Integer getIsTrust() {
		return isTrust;
	}

	public void setIsTrust(Integer isTrust) {
		this.isTrust = isTrust;
	}

	public String getTrustType() {
		return trustType;
	}

	public void setTrustType(String trustType) {
		this.trustType = trustType;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public Integer getIsAutoBid() {
		return isAutoBid;
	}

	public void setIsAutoBid(Integer isAutoBid) {
		this.isAutoBid = isAutoBid;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getPayitemTime() {
		return payitemTime;
	}

	public void setPayitemTime(String payitemTime) {
		this.payitemTime = payitemTime;
	}

	public Integer getAgeRequirement() {
		return ageRequirement;
	}

	public void setAgeRequirement(Integer ageRequirement) {
		this.ageRequirement = ageRequirement;
	}

	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoKeyword() {
		return seoKeyword;
	}

	public void setSeoKeyword(String seoKeyword) {
		this.seoKeyword = seoKeyword;
	}

	public String getSeoDesc() {
		return seoDesc;
	}

	public void setSeoDesc(String seoDesc) {
		this.seoDesc = seoDesc;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public Integer getTasktop() {
		return tasktop;
	}

	public void setTasktop(Integer tasktop) {
		this.tasktop = tasktop;
	}

	public Integer getUrgent() {
		return urgent;
	}

	public void setUrgent(Integer urgent) {
		this.urgent = urgent;
	}

	public Integer getSeohide() {
		return seohide;
	}

	public void setSeohide(Integer seohide) {
		this.seohide = seohide;
	}

	public Integer getWorkhide() {
		return workhide;
	}

	public void setWorkhide(Integer workhide) {
		this.workhide = workhide;
	}

	public Integer getBudget() {
		return budget;
	}

	public void setBudget(Integer budget) {
		this.budget = budget;
	}

	public Integer getTeamwork() {
		return teamwork;
	}

	public void setTeamwork(Integer teamwork) {
		this.teamwork = teamwork;
	}

	public Integer getIsBack() {
		return isBack;
	}

	public void setIsBack(Integer isBack) {
		this.isBack = isBack;
	}

	public String getGoldtype() {
		return goldtype;
	}

	public void setGoldtype(String goldtype) {
		this.goldtype = goldtype;
	}

	public String getGiftname() {
		return giftname;
	}

	public void setGiftname(String giftname) {
		this.giftname = giftname;
	}

	public Double getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(Double guarantee) {
		this.guarantee = guarantee;
	}

	public String getTalentType() {
		return talentType;
	}

	public void setTalentType(String talentType) {
		this.talentType = talentType;
	}

	public String getTalentPerson() {
		return talentPerson;
	}

	public void setTalentPerson(String talentPerson) {
		this.talentPerson = talentPerson;
	}

	public String getTalentTeam() {
		return talentTeam;
	}

	public void setTalentTeam(String talentTeam) {
		this.talentTeam = talentTeam;
	}

	public String getTeamNum() {
		return teamNum;
	}

	public void setTeamNum(String teamNum) {
		this.teamNum = teamNum;
	}

	public String getTalentEnt() {
		return talentEnt;
	}

	public void setTalentEnt(String talentEnt) {
		this.talentEnt = talentEnt;
	}

	public String getEntPayment() {
		return entPayment;
	}

	public void setEntPayment(String entPayment) {
		this.entPayment = entPayment;
	}

	public String getCrashPayType() {
		return crashPayType;
	}

	public void setCrashPayType(String crashPayType) {
		this.crashPayType = crashPayType;
	}

	public String getCrashPayNum() {
		return crashPayNum;
	}

	public void setCrashPayNum(String crashPayNum) {
		this.crashPayNum = crashPayNum;
	}

	public String getCrashPayStages() {
		return crashPayStages;
	}

	public void setCrashPayStages(String crashPayStages) {
		this.crashPayStages = crashPayStages;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

}
