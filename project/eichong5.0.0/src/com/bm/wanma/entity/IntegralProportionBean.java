package com.bm.wanma.entity;

import java.io.Serializable;


public class IntegralProportionBean  implements Serializable{
	private String ratio_integral_value;//赠送比率 如果为0取固定积分值
    private String fixed_integral_value;//固定积分值 
    private String activity_name;//活动名称
    private String pk_id;//活动id   1 充值活动  2充电活动
	public String getRatio_integral_value() {
		return ratio_integral_value;
	}
	public void setRatio_integral_value(String ratio_integral_value) {
		this.ratio_integral_value = ratio_integral_value;
	}
	public String getFixed_integral_value() {
		return fixed_integral_value;
	}
	public void setFixed_integral_value(String fixed_integral_value) {
		this.fixed_integral_value = fixed_integral_value;
	}
	public String getActivity_name() {
		return activity_name;
	}
	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}
	public String getPk_id() {
		return pk_id;
	}
	public void setPk_id(String pk_id) {
		this.pk_id = pk_id;
	}
   
}
