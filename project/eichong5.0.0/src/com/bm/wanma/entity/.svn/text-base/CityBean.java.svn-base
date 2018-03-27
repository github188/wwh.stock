package com.bm.wanma.entity;


import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;
/*城市表 */

@Table(name = "tb_m_city")
public class CityBean {
	@Id(column="CITY_ID")
	private String CITY_ID;//城市编码
    private String PROVINCE_ID;//省份编码
    private String CITY_NAME;//城市名称
    
	//必须包含这个默认的构造方法，否则在进行数据查找时，会报错
	public CityBean() {
		
	}

	
	public String getCITY_ID() {
		return CITY_ID;
	}


	public void setCITY_ID(String cITY_ID) {
		CITY_ID = cITY_ID;
	}


	public String getPROVINCE_ID() {
		return PROVINCE_ID;
	}

	public void setPROVINCE_ID(String pROVINCE_ID) {
		PROVINCE_ID = pROVINCE_ID;
	}


	public String getCITY_NAME() {
		return CITY_NAME;
	}


	public void setCITY_NAME(String cITY_NAME) {
		CITY_NAME = cITY_NAME;
	}

  
}
