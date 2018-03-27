package cn.hzstk.securities.financial.domain;
import cn.hzstk.securities.common.domain.BaseDomain;
import cn.hzstk.securities.sys.utils.json.Dict;

import javax.persistence.Column;
import javax.persistence.Table;


/**
* @description:
* @author: autoCode
* @history:
*/
@Table(name = "financial_order_charge")
public class OrderCharge extends BaseDomain {

    private static final long serialVersionUID = 1L;


    //冲值类型
    @Column(name = "order_type")
    @Dict(key="orderType")
    private String orderType;

    //支付方式
    @Column(name = "pay_type")
    private String payType;

    //返回订单编号
    @Column(name = "return_order_id")
    private Integer returnOrderId;

    //对象编号
    @Column(name = "obj_id")
    private Integer objId;

    //用户编号
    @Column(name = "uid")
    private String uid;

    //用户名
    @Column(name = "username")
    private String username;

    //支付时间
    @Column(name = "pay_time")
    private java.util.Date payTime;

    //支付金额
    @Column(name = "pay_money")
    private Double payMoney;

    //订单状态
    @Column(name = "order_status")
    @Dict(key="order_status")
    private String orderStatus;

    //支付信息
    @Column(name = "pay_info")
    private String payInfo;

    public void setOrderType(String orderType){
        this.orderType = orderType;
    }

    public String getOrderType(){
        return orderType;
    }

    public void setPayType(String payType){
        this.payType = payType;
    }

    public String getPayType(){
        return payType;
    }

    public void setReturnOrderId(Integer returnOrderId){
        this.returnOrderId = returnOrderId;
    }

    public Integer getReturnOrderId(){
        return returnOrderId;
    }

    public void setObjId(Integer objId){
        this.objId = objId;
    }

    public Integer getObjId(){
        return objId;
    }

    public void setUid(String uid){
        this.uid = uid;
    }

    public String getUid(){
        return uid;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setPayTime(java.util.Date payTime){
        this.payTime = payTime;
    }

    public java.util.Date getPayTime(){
        return payTime;
    }
    
    public Double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}

	public void setOrderStatus(String orderStatus){
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus(){
        return orderStatus;
    }

    public void setPayInfo(String payInfo){
        this.payInfo = payInfo;
    }

    public String getPayInfo(){
        return payInfo;
    }
}
