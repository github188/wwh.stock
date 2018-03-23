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
@Table(name = "financial_finance")
public class Finance extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //进出方向
    @Column(name = "fina_type")
    @Dict(key="fina_type")
    private String finaType;

    //收支说明
    @Column(name = "fina_action")
    @Dict(key="fina_action")
    private String finaAction;

    //订单编号
    @Column(name = "order_id")
    private Integer orderId;

    //用户编号
    @Column(name = "uid")
    private Integer uid;

    //用户名
    @Column(name = "username")
    private String username;

    //对象类型
    @Column(name = "obj_type")
    private String objType;

    //对象编号
    @Column(name = "obj_id")
    private Integer objId;

    //交易金额
    @Column(name = "fina_cash")
    private Double finaCash;

    //用户余额
    @Column(name = "user_balance")
    private Double userBalance;

    //交易代金券
    @Column(name = "fina_credit")
    private Integer finaCredit;

    //用户余券
    @Column(name = "user_credit")
    private Integer userCredit;

    //来源
    @Column(name = "fina_source")
    private String finaSource;

    //提交时间
    @Column(name = "fina_time")
    private long finaTime;

    //充值金额
    @Column(name = "recharge_cash")
    private Integer rechargeCash;

    //站长利润
    @Column(name = "site_profit")
    private Integer siteProfit;

    //财务去向说明
    @Column(name = "fina_mem")
    private String finaMem;

    //是否已托管
    @Column(name = "is_trust")
    private Integer isTrust;

    //托管类型
    @Column(name = "trust_type")
    private String trustType;

    public void setFinaType(String finaType){
        this.finaType = finaType;
    }

    public String getFinaType(){
        return finaType;
    }

    public void setFinaAction(String finaAction){
        this.finaAction = finaAction;
    }

    public String getFinaAction(){
        return finaAction;
    }

    public void setOrderId(Integer orderId){
        this.orderId = orderId;
    }

    public Integer getOrderId(){
        return orderId;
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

    public void setObjType(String objType){
        this.objType = objType;
    }

    public String getObjType(){
        return objType;
    }

    public void setObjId(Integer objId){
        this.objId = objId;
    }

    public Integer getObjId(){
        return objId;
    }

    public Double getFinaCash() {
		return finaCash;
	}

	public void setFinaCash(Double finaCash) {
		this.finaCash = finaCash;
	}

    public Double getUserBalance() {
		return userBalance;
	}

	public void setUserBalance(Double userBalance) {
		this.userBalance = userBalance;
	}

	public void setFinaCredit(Integer finaCredit){
        this.finaCredit = finaCredit;
    }

    public Integer getFinaCredit(){
        return finaCredit;
    }

    public void setUserCredit(Integer userCredit){
        this.userCredit = userCredit;
    }

    public Integer getUserCredit(){
        return userCredit;
    }

    public void setFinaSource(String finaSource){
        this.finaSource = finaSource;
    }

    public String getFinaSource(){
        return finaSource;
    }

	public long getFinaTime() {
		return finaTime;
	}

	public void setFinaTime(long finaTime) {
		this.finaTime = finaTime;
	}

	public void setRechargeCash(Integer rechargeCash){
        this.rechargeCash = rechargeCash;
    }

    public Integer getRechargeCash(){
        return rechargeCash;
    }

    public void setSiteProfit(Integer siteProfit){
        this.siteProfit = siteProfit;
    }

    public Integer getSiteProfit(){
        return siteProfit;
    }

    public void setFinaMem(String finaMem){
        this.finaMem = finaMem;
    }

    public String getFinaMem(){
        return finaMem;
    }

    public void setIsTrust(Integer isTrust){
        this.isTrust = isTrust;
    }

    public Integer getIsTrust(){
        return isTrust;
    }

    public void setTrustType(String trustType){
        this.trustType = trustType;
    }

    public String getTrustType(){
        return trustType;
    }
}
