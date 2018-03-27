package com.bm.wanma.entity;

import java.io.Serializable;

public class MalfunctionBean implements Serializable {

	private String pkMprId;
	private String pkMeiId;
	private String pkPowerstation;
	private String mprName;
	private String mprCreatedate;
	private String address;
	public String getPkMprId() {
		return pkMprId;
	}
	public void setPkMprId(String pkMprId) {
		this.pkMprId = pkMprId;
	}
	public String getPkMeiId() {
		return pkMeiId;
	}
	public void setPkMeiId(String pkMeiId) {
		this.pkMeiId = pkMeiId;
	}
	public String getPkPowerstation() {
		return pkPowerstation;
	}
	public void setPkPowerstation(String pkPowerstation) {
		this.pkPowerstation = pkPowerstation;
	}
	public String getMprName() {
		return mprName;
	}
	public void setMprName(String mprName) {
		this.mprName = mprName;
	}
	public String getMprCreatedate() {
		return mprCreatedate;
	}
	public void setMprCreatedate(String mprCreatedate) {
		this.mprCreatedate = mprCreatedate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
