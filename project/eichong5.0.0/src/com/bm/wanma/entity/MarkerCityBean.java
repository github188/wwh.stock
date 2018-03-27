package com.bm.wanma.entity;

import java.io.Serializable;
/**
 * 市级充电点聚合
 * @author cm
 */
public class MarkerCityBean implements Serializable {

	/*lng,lat*/
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5354311214563725385L;
	private String location;
	private String count;
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	
}
