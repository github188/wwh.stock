package com.jeecms.core.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jeecms.core.entity.base.BaseCmsConfig;

public class CmsConfig extends BaseCmsConfig {
	private static final long serialVersionUID = 1L;
	
	public BbsConfigAttr getConfigAttr() {
		BbsConfigAttr configAttr = new BbsConfigAttr(getAttr());
		return configAttr;
	}
	

	public void setConfigAttr(BbsConfigAttr configAttr) {
		getAttr().putAll(configAttr.getAttr());
	}
	
	public Map<String,String> getSsoAttr() {
		Map<String,String>ssoMap=new HashMap<String, String>();
		Map<String,String>attr=getAttr();
		for(String ssoKey:attr.keySet()){
			if(ssoKey.startsWith("sso_")){
				ssoMap.put(ssoKey, attr.get(ssoKey));
			}
		}
		return ssoMap;
	}
	
	public List<String> getSsoAuthenticateUrls() {
		Map<String,String>ssoMap=getSsoAttr();
		List<String>values=new ArrayList<String>();
		for(String key:ssoMap.keySet()){
			values.add(ssoMap.get(key));
		}
		return values;
	}
	
	public Boolean getQqEnable(){
		BbsConfigAttr configAttr=getConfigAttr();
		return configAttr.getQqEnable();
	}
	
	public Boolean getSinaEnable(){
		BbsConfigAttr configAttr=getConfigAttr();
		return configAttr.getSinaEnable();
	}
	
	public Boolean getQqWeboEnable(){
		BbsConfigAttr configAttr=getConfigAttr();
		return configAttr.getQqWeboEnable();
	}
	
	public String getQqID(){
		BbsConfigAttr configAttr=getConfigAttr();
		return configAttr.getQqID();
	}
	
	public String getSinaID(){
		BbsConfigAttr configAttr=getConfigAttr();
		return configAttr.getSinaID();
	}
	
	public String getQqWeboID(){
		BbsConfigAttr configAttr=getConfigAttr();
		return configAttr.getQqWeboID();
	}
	
	public Integer getDefaultActiveLevel(){
		BbsConfigAttr configAttr=getConfigAttr();
		String defaultActiveLevel= configAttr.getDefaultActiveLevel();
		int defaultActiveLevelId=1;
		try {
			defaultActiveLevelId=Integer.parseInt(defaultActiveLevel);
		} catch (Exception e) {
		}
		return defaultActiveLevelId;
	}
	
	public int getKeepMinute(){
		BbsConfigAttr configAttr=getConfigAttr();
		String keep= configAttr.getKeepMinute();
		int keepMinut=10;
		try {
			keepMinut=Integer.parseInt(keep);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return keepMinut;
	}
	
	public int getUserOnlineTopNum(){
		BbsConfigAttr configAttr=getConfigAttr();
		String top= configAttr.getUserOnlineTopNum();
		int topNum=0;
		try {
			topNum=Integer.parseInt(top);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return topNum;
	}
	
	
	
	public String getUserOnlineTopDay(){
		BbsConfigAttr configAttr=getConfigAttr();
		return configAttr.getUserOnlineTopDay();
	}
	
	public void blankToNull() {
		// oracle varchar2把空串当作null处理，这里为了统一这个特征，特做此处理。
		if (StringUtils.isBlank(getProcessUrl())) {
			setProcessUrl(null);
		}
		if (StringUtils.isBlank(getContextPath())) {
			setContextPath(null);
		}
		if (StringUtils.isBlank(getServletPoint())) {
			setServletPoint(null);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsConfig () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsConfig (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsConfig (
		java.lang.Integer id,
		java.lang.String dbFileUri,
		java.lang.Boolean uploadToDb,
		java.lang.String defImg,
		java.lang.String loginUrl,
		java.util.Date countClearTime,
		java.util.Date countCopyTime,
		java.lang.String downloadCode,
		java.lang.Integer downloadTime) {

		super (
			id,
			dbFileUri,
			uploadToDb,
			defImg,
			loginUrl,
			countClearTime,
			countCopyTime,
			downloadCode,
			downloadTime);
	}

	/* [CONSTRUCTOR MARKER END] */

}