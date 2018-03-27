package com.bm.wanma.entity;

import java.io.Serializable;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;
/*省份表 */

@Table(name = "tb_m_province")
public class ProvinceBean implements Serializable {
	@Id(column="PROVINCE_ID")
    private String PROVINCE_ID;//省份编码
    private String PROVINCE_NAME;//省份名称
    
	//必须包含这个默认的构造方法，否则在进行数据查找时，会报错
	public ProvinceBean() {
		
	}

	public String getPROVINCE_ID() {
		return PROVINCE_ID;
	}

	public void setPROVINCE_ID(String pROVINCE_ID) {
		PROVINCE_ID = pROVINCE_ID;
	}

	public String getPROVINCE_NAME() {
		return PROVINCE_NAME;
	}

	public void setPROVINCE_NAME(String pROVINCE_NAME) {
		PROVINCE_NAME = pROVINCE_NAME;
	}

    

  
}
