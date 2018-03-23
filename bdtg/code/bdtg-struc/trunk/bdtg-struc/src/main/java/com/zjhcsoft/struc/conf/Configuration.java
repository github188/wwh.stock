package com.zjhcsoft.struc.conf;

import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import com.zjhcsoft.struc.bean.AreaGranularity;
import com.zjhcsoft.struc.bean.TimeDegree;
import com.zjhcsoft.struc.bean.TimeGranularity;

/**
 * 配置表
 * 
 * @author wangtao
 * 
 */
public class Configuration {
	
	public static final String TEMP_DIR = "temp_dir";
	
	public static final String VPN_IP = "vpn_ip";
	
	public static final String PORT = "port";
	
	private  static Configuration instance = new Configuration();
	private Properties p;
	
	public static Configuration INSTANCE() {
		return instance;
	}
	
	private Configuration() {
		p = new Properties();
		InputStream fis = null;
		try{
			fis = this.getClass().getResourceAsStream("/config.properties");
			p.load(fis);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(fis!=null){
					fis.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	// 线程数
	public static int THREADS = 1;

	// 时间粒度
	public static TimeGranularity timeGranularity = TimeGranularity.DAY;

	// 时间粒度
	public static AreaGranularity areaGranularity = AreaGranularity.CITY;

	// 时间长度
	public static TimeDegree degree = TimeDegree.TODAY;

	// 开始时间
	public static Date starttime;

	// 结束时间
	public static Date endtime;

	// 年数
	public static int withinyear;

	// 月数
	public static int withinmonth;

	// 天数
	public static int withinday;

	// 小时数
	public static int hourNum = 1;

	// 小时数 或 天数
	public static int num = 3;

	public static boolean today;

	public static boolean all;

	// 重试次数
	public static int retries = 3;

	// 整个任务超时时间 秒数
	public static long timeout = 3 * 60 * 60L;

	// 抓取超时时间 秒数
	public static long fetchTimeout = 2 * 60;

	// 数据源名称
	public static String dataSourceName;

	// 单个url地址
	public static String url;
	
	// 抓取类型 单个url或者整个网页
	public static String crawltype ="plural";
	
	
	public String getValue(String key) {
		return p.getProperty(key);
	}
	
	//存放Ip地址(不同地区不同ip)
	public static String ip;

	//污染类型
	public static int wrlx = 2;//默认废气
	public static String timetype = null;//默认废气
	
	//抓取最新数据
	public static int zxsj=1;//默认抓取最新数据
	
	public static int quzhou=0;
	
	public static String year;
	
	public static int vpn=0;
	
	public static int page=5;
}
