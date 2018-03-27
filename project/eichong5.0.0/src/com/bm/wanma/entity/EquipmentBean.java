package com.bm.wanma.entity;

import java.io.Serializable;

/**
 * cm
 * 获取设备保修类型
 */
public class EquipmentBean implements Serializable {
	
    private String pk_ConfigContent;//保修项id
    private String coCo_Content;//保修项名称
    
	public String getPk_ConfigContent() {
		return pk_ConfigContent;
	}
	public void setPk_ConfigContent(String pk_ConfigContent) {
		this.pk_ConfigContent = pk_ConfigContent;
	}
	public String getCoCo_Content() {
		return coCo_Content;
	}
	public void setCoCo_Content(String coCo_Content) {
		this.coCo_Content = coCo_Content;
	}

   
    
    
}
