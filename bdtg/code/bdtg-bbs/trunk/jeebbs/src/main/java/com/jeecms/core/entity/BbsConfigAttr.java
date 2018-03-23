package com.jeecms.core.entity;

import java.util.HashMap;
import java.util.Map;


public class BbsConfigAttr {
	public BbsConfigAttr() {
	}

	public BbsConfigAttr(Map<String, String> attr) {
		this.attr = attr;
	}

	private Map<String, String> attr;

	public Map<String, String> getAttr() {
		if (attr == null) {
			attr = new HashMap<String, String>();
		}
		return attr;
	}

	public void setAttr(Map<String, String> attr) {
		this.attr = attr;
	}
	
	public String getKeepMinute() {
		 return getAttr().get(KEEPMINUTE);
	}
	
	public void setKeepMinute(String keepMinute) {
		getAttr().put(KEEPMINUTE, keepMinute);
	}
	
	public String getDefaultActiveLevel() {
		 return getAttr().get(DEFAULT_ACTIVE_LEVEL);
	}
	
	public void setDefaultActiveLevel(String level) {
		getAttr().put(DEFAULT_ACTIVE_LEVEL, level);
	}
	
	public Boolean getQqEnable() {
		String enable = getAttr().get(QQ_ENABLE);
		return !"false".equals(enable);
	}
	
	public String getQqID() {
		return getAttr().get(QQ_ID);
	}
	
	public String getQqKey() {
		return getAttr().get(QQ_KEY);
	}
	
	public Boolean getSinaEnable() {
		String enable = getAttr().get(SINA_ENABLE);
		return !"false".equals(enable);
	}
	
	public String getSinaID() {
		return getAttr().get(SINA_ID);
	}
	
	public String getSinaKey() {
		return getAttr().get(SINA_KEY);
	}
	
	public Boolean getQqWeboEnable() {
		String enable = getAttr().get(QQWEBO_ENABLE);
		return !"false".equals(enable);
	}
	
	public String getQqWeboID() {
		return getAttr().get(QQWEBO_ID);
	}
	
	public String getQqWeboKey() {
		return getAttr().get(QQWEBO_KEY);
	}
	
	public String getUserOnlineTopDay() {
		return getAttr().get(USER_ONLINE_TOP_DAY);
	}
	
	public String getUserOnlineTopNum() {
		return getAttr().get(USER_ONLINE_TOP_NUM);
	}
	
	public void setUserOnlineTopDay(String day) {
		getAttr().put(USER_ONLINE_TOP_DAY, day);
	}
	
	public void setUserOnlineTopNum(String num) {
		getAttr().put(USER_ONLINE_TOP_NUM, num);
	}
	
	public void setQqEnable(Boolean enable) {
		getAttr().put(QQ_ENABLE, String.valueOf(enable));
	}
	
	public void setQqID(String id) {
		getAttr().put(QQ_ID, id);
	}
	
	public void setQqKey(String key) {
		getAttr().put(QQ_KEY, key);
	}
	
	
	public void setSinaEnable(Boolean enable) {
		getAttr().put(SINA_ENABLE, String.valueOf(enable));
	}
	
	public void setSinaID(String id) {
		getAttr().put(SINA_ID,id);
	}
	
	public void setSinaKey(String key) {
		getAttr().put(SINA_KEY,key);
	}
	
	public void setQqWeboEnable(Boolean enable) {
		getAttr().put(QQWEBO_ENABLE, String.valueOf(enable));
	}
	
	public void setQqWeboID(String id) {
		getAttr().put(QQWEBO_ID, id);
	}
	
	public void setQqWeboKey(String key) {
		getAttr().put(QQWEBO_KEY, key);
	}
	
	public static final String KEEPMINUTE = "keepMinute";
	public static final String DEFAULT_ACTIVE_LEVEL = "defaultActiveLevel";
	public static final String QQ_ENABLE = "qqEnable";
	public static final String QQ_ID = "qqID";
	public static final String QQ_KEY = "qqKey";
	public static final String SINA_ENABLE = "sinaEnable";
	public static final String SINA_ID = "sinaID";
	public static final String SINA_KEY = "sinaKey";
	public static final String QQWEBO_ENABLE = "qqWeboEnable";
	public static final String QQWEBO_ID = "qqWeboID";
	public static final String QQWEBO_KEY = "qqWeboKey";
	public static final String USER_ONLINE_TOP_DAY = "useronlinetopday";
	public static final String USER_ONLINE_TOP_NUM = "useronlinetopnum";

}
