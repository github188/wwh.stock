package com.bm.wanma.entity;

import java.io.Serializable;

/**
 * 获得车型 
 * cm
 *  pkCarInfo：车型编号
 *  carinfoStylename：车型名称
 */
public class CarNameBean implements Serializable {
    private String pkCarinfo;
    private String carinfoStylename;

    public String getPkCarinfo() {
		return pkCarinfo;
	}

	public void setPkCarinfo(String pkCarinfo) {
		this.pkCarinfo = pkCarinfo;
	}

	public String getCarinfoStylename() {
        return carinfoStylename;
    }

    public void setCarinfoStylename(String carinfoStylename) {
        this.carinfoStylename = carinfoStylename;
    }
}
