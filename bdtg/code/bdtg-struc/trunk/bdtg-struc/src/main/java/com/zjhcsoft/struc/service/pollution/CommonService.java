package com.zjhcsoft.struc.service.pollution;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zjhcsoft.struc.mapper.CommonMapper;
import com.zjhcsoft.struc.util.SpringBeanFactory;
import com.zjhcsoft.struc.util.TimingUtil;

public class CommonService {

	/**
	 * 获取最新监测时间
	 * @param entName 企业名称
	 * @param siteName 站点名称
	 * @param itemName 项目名称
	 * @param selfMonitorMethod 类型（手动监测|自动监测）
	 * @return
	 */
	public static Date getMaxMonitorData(String sql,String entName,String siteName,String itemName,String selfMonitorMethod){
		CommonMapper mapper = (CommonMapper) SpringBeanFactory.getBean("commonMapper");
		String monitorTime = mapper.selectPublish(sql);
		if (monitorTime == null || "".equals(monitorTime)) {
			return null;
		}
		try {
			return TimingUtil.sdf10.parse(monitorTime) ;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 获取最新监测时间
	 * @param entName 企业名称
	 * @return
	 */
	public static Date getMaxMonitorData(String sql,String entName){
		CommonMapper mapper = (CommonMapper) SpringBeanFactory.getBean("commonMapper");
		String monitorTime = mapper.selectPublish(sql);
		if (monitorTime == null || "".equals(monitorTime)) {
			return null;
		}
		try {
			return TimingUtil.sdf10.parse(monitorTime) ;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取海南监测类型(污染源类型)
	 * @return
	 */
	public static Map<String, Object> getHnTypes() {
		Map<String, Object> types = new HashMap<String, Object>();
		types.put("FQ", "国控废气");
		types.put("FS", "国控废水");
		types.put("ZJSFQ", "重金属废气");
		types.put("ZJSFS", "重金属废水");
		types.put("XQYZC", "畜禽养殖场");
		types.put("WSC", "污水处理场");
		return types;
	}
}
