package com.skoo.stock.util;

import com.skoo.common.SystemConfig;
import com.skoo.commons.StringUtils;
import com.skoo.stock.util.beanutil.ConvertFactory;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;


public class StockUtil {
	public static String path = "D:/wwh/stock/";
	static Logger log = Logger.getLogger("mylogger");

    public static String[] getStockValue(String name){
		if (name.startsWith("3") || name.startsWith("00")) {
			name = "sz" + name;
		} else if (!name.startsWith("s")) {
			name = "sh" + name;
		}
	    String responseBody = RequestUtils.doGet("http://hq.sinajs.cn/list="+name,null);
	    String stockdata = "";
	    if (responseBody != null) stockdata = responseBody.split("\"")[1];
	    String[] stockdatasplit = new String[]{};
	    if (stockdata != null) stockdatasplit = stockdata.split(",");

	    return stockdatasplit;
    }

	public static List<String[]> getStockList(String name){
		String responseBody = RequestUtils.doGet("http://hq.sinajs.cn/list="+name,null);
		String[] val = new String[]{};
		if (responseBody != null) val = responseBody.split("\\n");
		String stockdata;
		List<String[]> list = new ArrayList<>();
		for (int i = 0; i < val.length; i++) {
			stockdata = val[i].split("\"")[1];
			if (stockdata != null) list.add(stockdata.split(","));
		}

		return list;
	}

	public static String[] getStockEast(String name){
		if (name.startsWith("3") || name.startsWith("00")) {
			name = "sz" + name;
		} else if (!name.startsWith("s")) {
			name = "sh" + name;
		}
		String responseBody = RequestUtils.doGet("http://hq.sinajs.cn/list="+name,null);
		String stockdata = "";
		if (responseBody != null) stockdata = responseBody.split("\"")[1];
		String[] stockdatasplit = new String[]{};
		if (stockdata != null) stockdatasplit = stockdata.split(",");

		return stockdatasplit;
	}

	public static String getStockCode(String sCode){
		String name = "";
		if (sCode.startsWith("3") || sCode.startsWith("00")) {
			name = "sz" + sCode;
		} else if (!sCode.startsWith("s")) {
			name = "sh" + sCode;
		}

		return name;
	}

	public static String[] getStockstar(String sName){
		Document doc;
		doc = getDocument(SystemConfig.INSTANCE.getValue("starAddress")+sName+".shtml");
		if (doc == null) return null;

		String[] stockdata = new String[13];
		Elements elements = doc.select(".stockInfo");
		stockdata[0] = sName;
		for(Element element:elements) {
			if (element.text() != null && !"".equals(element.text())) {
				Elements tdes = doc.select("h2");
				stockdata[1] = tdes.get(0).text();
				tdes = doc.select("#stock_quoteinfo_xj");
				stockdata[2] = tdes.get(0).text();
				tdes = doc.select("#stock_quoteinfo_zdf");
				stockdata[3] = tdes.get(0).text().replace("(", "").replace("%)", "");
				tdes = doc.select("#stock_quoteinfo_zde");
				stockdata[4] = tdes.get(0).text();
			}
		}

		//select("table#table_style_e");
		elements = doc.select("#stock_quoteinfo");
		for(Element element:elements){
			if(element.text()!=null&& !"".equals(element.text())){
				Elements tdes = doc.select("#stock_quoteinfo_jk");
/*
                stockdata[0] = tdes.get(0).text();
                tdes = doc.select("#stock_quoteinfo_zg");
                stockdata[1] = tdes.get(0).text();
                tdes = doc.select("#stock_quoteinfo_zs");
                stockdata[4] = tdes.get(0).text();
                tdes = doc.select("#stock_quoteinfo_zd");
                stockdata[5] = tdes.get(0).text();
*/
				tdes = doc.select("#stock_quoteinfo_cj");
				stockdata[6] = tdes.get(0).text().replace("万手","");
				tdes = doc.select("#stock_quoteinfo_sy");
				stockdata[12] = tdes.get(0).text();

				tdes = doc.select("#stock_quoteinfo_hs");
				stockdata[8] = tdes.get(0).text();
				tdes = doc.select("#stock_quoteinfo_zf");
				stockdata[9] = tdes.get(0).text();
			}
		}

		return stockdata;
	}

	public static String[] getStockDyn(String sName) {
		Document doc;
		String sAddr = sName;
		if (sName.length() == 6) {
			sAddr = SystemConfig.INSTANCE.getValue("starAddress") + sName + ".shtml";
		} else {
			sName = sAddr.split("com/")[1].split(".shtml")[0];
		}
		doc = getDocument(sAddr);
		if (doc == null) return null;

		String[] stockdata = new String[11];
		Elements elements = doc.select(".stockInfo");
		stockdata[0] = sName;
		for (Element element : elements) {
			if (element.text() != null && !"".equals(element.text())) {
				Elements tdes = doc.select("h2");
				stockdata[1] = tdes.get(0).text();
				tdes = doc.select("#stock_quoteinfo_xj");
				stockdata[2] = tdes.get(0).text();
			}
		}
		elements = doc.select("#stock_quoteinfo");
		for(Element element:elements){
			if(element.text()!=null&& !"".equals(element.text())){
				Elements tdes = doc.select("#stock_quoteinfo_zs");
                stockdata[3] = tdes.get(0).text();
                tdes = doc.select("#stock_quoteinfo_zg");
                stockdata[4] = tdes.get(0).text();
                tdes = doc.select("#stock_quoteinfo_zd");
                stockdata[5] = tdes.get(0).text();
                tdes = doc.select("#stock_quoteinfo_date");
                stockdata[10] = tdes.get(0).text();
			}
		}
		elements = doc.select(".jsfx_wrap");
		for (Element element : elements) {
			if (element.text() != null && !"".equals(element.text())) {
				Elements es = element.select("tr");
				if (es.size() > 0) {
					Elements tdes = es.get(1).select("td");
					for (int i = 1; i < tdes.size(); i++) {
						stockdata[5 + i] = tdes.get(i).text().replace("%", "");
						if (tdes.get(i).text().equals("主力平均 成本")) {
							stockdata[5 + i] = "";
						}
					}
				}
				Elements tdes = element.select("p");
				stockdata[9] = tdes.get(1).text().replace(" ", "").trim();
			}
		}

		return stockdata;
	}

	public static String[] getStockSum(String sName) {
		Document doc;
		String sAddr = SystemConfig.INSTANCE.getValue("headAddress") + "stock/" + sName + ".shtml";
		doc = getDocument(sAddr);
		if (doc == null) return null;

		String[] stockdata = new String[17];
		Elements elements = doc.select(".info");
		stockdata[0] = sName;
		for (Element element : elements) {
			if (element.text() != null && !"".equals(element.text())) {
				Elements es = element.select("tr");
				if (es.size() > 0) {
					Elements tdes = es.get(0).select("td");
					for (int i = 0; i < tdes.size(); i++) {
						stockdata[i+1] = tdes.get(i).text().split(":")[1].replace("+", "").replace("%", "");
					}
				}
			}
		}
		elements = doc.select(".main_square");
		for (Element element : elements) {
			if (element.text() != null && !"".equals(element.text())) {
				Elements es = element.select("tr");
				for (int j = 0; j < es.size(); j++) {
					Elements tdes = es.get(j).select("td");
					for (int i = 0; i < tdes.size(); i++) {
						if (i % 2 == 1) stockdata[8 + j*3 + i/2] = tdes.get(i).text().replace(" ", "").replace("只","");
					}
				}
			}
		}

		return stockdata;
	}

	public static String[] getStockBasic(String sName){
		Document doc;
		String sAddr;
		if (sName.indexOf("<") > 0) {
			sAddr = sName.split("<")[1].replace(">","");
			sName = sAddr.split("com/")[1].split(".")[0];
		} else {
			sAddr = SystemConfig.INSTANCE.getValue("starAddress")+sName+".shtml";
		}
		doc = getDocument(sAddr);
		if (doc == null) return null;

		String[] stockdata = new String[14];
		Elements elements = doc.select(".stockInfo");
		stockdata[0] = sName;
		for(Element element:elements) {
			if (element.text() != null && !"".equals(element.text())) {
				Elements tdes = doc.select("h2");
				stockdata[1] = tdes.get(0).text();
			}
		}
		elements = doc.select(".gbgd_wrap");
		for(Element element:elements) {
			if (element.text() != null && !"".equals(element.text())) {
				Elements es = element.select("tr");
				for(Element tdelement:es){
					Elements tdes = tdelement.select("td");
					if (tdes.size()>0) {
						if ("流通股".equals(tdes.get(0).text())) stockdata[2] = tdes.get(1).text();
						if ("合计".equals(tdes.get(0).text())) stockdata[3] = tdes.get(1).text();
					}
				}
			}
		}
		elements = doc.select(".cwfx_wrap");
		for(Element element:elements) {
			if (element.text() != null && !"".equals(element.text())) {
				Elements es = element.select("tr");
				for(Element tdelement:es){
					Elements tdes = tdelement.select("td");
					if (tdes.size()>0) {
						if (tdes.get(0).text().indexOf("每股收益(元)") == 0) stockdata[4] = tdes.get(1).text();
						if (tdes.get(0).text().indexOf("每股净资产(元)") == 0) stockdata[5] = tdes.get(1).text();
						if (tdes.get(0).text().indexOf("每股未分配利润(元)") == 0) stockdata[6] = tdes.get(1).text();
						if (tdes.get(0).text().indexOf("每股资本公积金(元)") == 0) stockdata[7] = tdes.get(1).text();
						if (tdes.get(0).text().indexOf("净资产收益率(%)") == 0) stockdata[8] = tdes.get(1).text();
						if (tdes.get(0).text().indexOf("主营业务收入(万元)") == 0) stockdata[9] = tdes.get(1).text();
						if (tdes.get(0).text().indexOf("净利润(万元)") == 0) stockdata[10] = tdes.get(1).text();
					}
				}
				Elements tdes = element.select("p");
				if (tdes.size()>2) stockdata[11] = tdes.get(2).text().trim();
			}
		}
		elements = doc.select(".gszl_wrap");
		for(Element element:elements) {
			if (element.text() != null && !"".equals(element.text())) {
				Elements tdes = element.select("p");
				stockdata[12] = tdes.get(1).text();
				if (tdes.size()>5) stockdata[13] = tdes.get(5).text();
			}
		}

		return stockdata;
	}

	public static String[] getStockDetail(String sName){
    	Document doc;
		doc = getDocument(SystemConfig.INSTANCE.getValue("starAddress")+sName+".shtml");
		if (doc == null) return null;

    	String[] stockdata = new String[16];
		Elements elements = doc.select(".stockInfo");
		stockdata[0] = sName;
		for(Element element:elements) {
			if (element.text() != null && !"".equals(element.text())) {
				Elements tdes = doc.select("h2");
				stockdata[1] = tdes.get(0).text();
				tdes = doc.select("#stock_quoteinfo_xj");
				stockdata[2] = tdes.get(0).text();
			}
		}
		elements = doc.select(".jsfx_wrap");
		for(Element element:elements) {
			if (element.text() != null && !"".equals(element.text())) {
				Elements es = element.select("tr");
				if(es.size()>0) {
					Elements tdes = es.get(1).select("td");
					for (int i = 1; i < tdes.size(); i++) {
						stockdata[2+i] = tdes.get(i).text().replace("%","");
					}
				}
				Elements tdes = element.select("p");
				stockdata[15] = tdes.get(1).text().replace(" ", "").trim();
			}
		}
		elements = doc.select(".gbgd_wrap");
		for(Element element:elements) {
			if (element.text() != null && !"".equals(element.text())) {
				Elements es = element.select("tr");
				for(Element tdelement:es){
					Elements tdes = tdelement.select("td");
					if (tdes.size()>0) {
						if ("流通股".equals(tdes.get(0).text())) stockdata[6] = tdes.get(1).text();
						if ("合计".equals(tdes.get(0).text())) stockdata[7] = tdes.get(1).text();
					}
				}
			}
		}
		elements = doc.select(".cwfx_wrap");
		for(Element element:elements) {
			if (element.text() != null && !"".equals(element.text())) {
				Elements es = element.select("tr");
				for(Element tdelement:es){
					Elements tdes = tdelement.select("td");
					if (tdes.size()>0) {
						if ("每股收益(元)".equals(tdes.get(0).text())) stockdata[8] = tdes.get(1).text();
						if ("每股净资产(元)".equals(tdes.get(0).text())) stockdata[9] = tdes.get(1).text();
						if ("每股资本公积金(元)".equals(tdes.get(0).text())) stockdata[10] = tdes.get(1).text();
						if ("净利润(万元)".equals(tdes.get(0).text())) stockdata[11] = tdes.get(1).text();
					}
				}
				Elements tdes = element.select("p");
				if (tdes.size()>2) stockdata[12] = tdes.get(2).text().trim();
			}
		}
		elements = doc.select(".gszl_wrap");
		for(Element element:elements) {
			if (element.text() != null && !"".equals(element.text())) {
				Elements tdes = element.select("p");
				stockdata[13] = tdes.get(1).text();
				if (tdes.size()>5) stockdata[14] = tdes.get(5).text();
			}
		}

        return stockdata;
    }

	public static String[] getStockCur(String sName){
		String[] stockdata = getStockValue(sName);
		if (stockdata.length < 2) return null;
		String[] val = new String[13];
		val[0] = sName;
		val[1] = stockdata[0];
		val[2] = stockdata[3];
		val[6] = String.valueOf(Math.floor(ConvertFactory.convert(Double.TYPE, stockdata[8]) / 100));
		val[7] = String.valueOf(Math.floor(ConvertFactory.convert(Double.TYPE, stockdata[9]) / 10000));
		double zs = ConvertFactory.convert(Double.TYPE, stockdata[2]);
		double dq = ConvertFactory.convert(Double.TYPE, stockdata[3]);
		double zg = ConvertFactory.convert(Double.TYPE, stockdata[4]);
		double zd = ConvertFactory.convert(Double.TYPE, stockdata[5]);
		val[3] = String.format("%.2f", calcWidth(dq, zs, zs));
		val[4] = String.format("%.2f", dq - zs);
		val[5] = String.format("%.2f", zs);
		val[9] = String.format("%.2f", calcWidth(zg, zd, zs));
		return val;
	}

    public static LinkedHashMap<String, String[]> getStockList(String fName, String sAddr) {
        String[] stockdatasplit = null;
        String count = "1";
		if (StringUtils.isEmpty(sAddr)) sAddr = SystemConfig.INSTANCE.getValue(fName+"Address");
		String[] sAddrsplit = StringUtils.split(sAddr, "|");
		if (sAddrsplit.length > 1) {
			sAddr = sAddrsplit[0];
			count = sAddrsplit[1];
		}
		if ("stock".equals(fName)) {
			stockdatasplit = new String[]{"代码", "简称", "最新价", "涨跌幅", "涨跌额", "5分钟涨幅", "成交量(手)", "成交额(万元)", "换手率", "振幅", "量比", "委比", "市盈率"};
		} else if ("keyName".equals(fName)) {
			stockdatasplit = new String[] {"板块名称","股票数量","加权涨跌幅","上涨家数","涨幅","下跌家数","跌幅","成交量","成交额","总流通市值","地址"};
		} else if ("index".equals(fName)) {
			stockdatasplit = new String[] {"合约名称","最新价","涨跌","涨跌幅","昨结","今开","最高","最低","成交量","持仓量"};
		} else if (fName.length() < 6) {
			stockdatasplit = new String[] {"代码", "简称", "最新价", "涨跌幅", "涨跌额", "5分钟涨幅", "成交量(手)", "成交额(万元)", "换手率", "振幅", "量比", "市盈率"};
        }

	    return getList(sAddr, count, stockdatasplit);
    }     

    private static LinkedHashMap<String, String[]> getList(String sAddr, String count, String[] stockdatasplit) {
    	Document doc;
    	Elements elements;
    	String sPrex = "#datalist";
    	if (sAddr.indexOf(".htm") < 0) sPrex = "table.tbody_right";

        LinkedHashMap<String, String[]> map = new LinkedHashMap<>();
        String stockdataold;
		boolean skipFlag = false;
		boolean dtFlag = false;
		int cnt = 0;
		if (stockdatasplit != null) {
			map.put("序号", stockdatasplit);
			cnt++;
    	}
		int uCount;
		String dt = null;
		String[] sCountsplit = StringUtils.split(count, ":");
		if (sCountsplit.length > 1) {
			uCount = Integer.valueOf(sCountsplit[0]);
			dt = sCountsplit[1];
		} else {
			uCount = Integer.valueOf(count);
		}

    	for(int k = 1; k <= uCount; k++){
			if (dtFlag) break;
    		if (sAddr.endsWith(".html")) {
    			sAddr = sAddr.substring(0, sAddr.lastIndexOf("_")+1) + String.valueOf(k) + ".html";
    		} else if (k < uCount) {
    			sAddr = sAddr.substring(0, sAddr.lastIndexOf("=")+1) + String.valueOf(k);
    		}
        	doc = getDocument(sAddr);
        	if (doc == null) {
				if (uCount == 17) dtFlag = true;
				continue;
			}

        	if (k > 1) skipFlag = true;
        	elements = doc.select(sPrex);
        	stockdataold = doc.select("h2").text();
            for(Element element:elements){
                if(element.text()!=null&& !"".equals(element.text())){
                    Elements es = element.select("tr");
                    for(Element tdelement:es){
                    	if (!skipFlag) {
	                        Elements tdes = tdelement.select("td");
	                        if (cnt == 0) stockdatasplit = new String[tdes.size()];
	                        if (tdes.size() < stockdatasplit.length-1) continue;
	                        stockdatasplit = new String[stockdatasplit.length];
	                        for(int i = 0; i < tdes.size(); i++){
	                        	stockdatasplit[i] = tdes.get(i).text();
								if (i==0 && stockdatasplit[i].indexOf("...")>0) stockdatasplit[i] = tdes.get(0).select("a[href]").attr("title");
	                        }
							if (StringUtils.isNotEmpty(dt) && stockdatasplit[0].compareTo(dt) <= 0) {
								dtFlag = true;
								break;
							}
	                        if (cnt == 0) {
	                        	map.put(stockdataold, stockdatasplit);
	                        } else {
								if (tdes.size() == stockdatasplit.length-1) stockdatasplit[stockdatasplit.length-1] = tdes.get(0).select("a[href]").attr("abs:href");
								map.put(String.valueOf(cnt), stockdatasplit);
	                        }
	                        cnt++;
                        } else {
                        	skipFlag = false;
	                    }
                    }
                }
            }
    	}

	    return map;
    }

    public static LinkedHashMap<String, String[]> getStockHist(String sName) {
    	//http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/601006.phtml?year=2015&jidu=1
    	Document doc;
		try {
			doc = Jsoup.connect("http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/"+sName+".phtml?year=2015&jidu=2").get();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    	Elements elements = doc.select("#FundHoldSharesTable");
        LinkedHashMap<String, String[]> map = new LinkedHashMap<String, String[]>();
    	int cnt = 0;
        //select("table#table_style_e");  
        for(Element element:elements){  
            if(element.text()!=null&& !"".equals(element.text())){  
                Elements es = element.select("tr");
                for(Element tdelement:es){  
                    Elements tdes = tdelement.select("td");
                	String[] stockdatasplit = {"日期","收盘价","涨跌幅","前收价","开盘价","最高价","最低价","成交量(手)","成交额(千元)"};
                    if (tdes.size()==0) {
                    	stockdatasplit[0] = tdelement.text();
                    } else {
                    	int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);;
                    	if (cnt > 2 || (cnt == 2 && hour < 15)) {
                    		Elements tmp = tdes.select("a[href]");
                    		stockdatasplit = getDetailHist(tmp.get(0).attr("abs:href"));
                    	} else if (cnt == 2) {
	                        for(int i = 0; i < tdes.size(); i++){
	                        	stockdatasplit[i] = tdes.get(i).text();
	                        }
                    	}
                    	//stockdatasplit[5] = Integer.valueOf(stockdatasplit[5])
                    }
                	map.put(String.valueOf(cnt), stockdatasplit);
                    cnt++;
                }  
            }  
        }  

	    return map;
    }

    private static String[] getDetailHist(String sName) {
    	//http://vip.stock.finance.sina.com.cn/quotes_service/view/vMS_tradehistory.php?symbol=sh601006&date=2015-03-31
    	Document doc;
		try {
			doc = Jsoup.connect(sName).get();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    	Elements elements = doc.select(".marketData");
    	String[] stockdata = new String[9];

    	//select("table#table_style_e");  
        for(Element element:elements){
            if(element.text()!=null&& !"".equals(element.text())){  
                Elements tdes = element.select("td");
                stockdata[0] = sName.split("&")[1].split("=")[1];
                for(int i = 0; i < tdes.size(); i++){
                	if (i % 2 == 1) stockdata[i/2+1] = tdes.get(i).text();
                }
                stockdata[6] = String.valueOf(Math.round((Double.parseDouble(stockdata[5]) - Double.parseDouble(stockdata[6]))/Double.parseDouble(stockdata[3])*100));
            }
        }
        
        return stockdata;
    }

    public static void getStockHistory(String sName) {
    	String url = "http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/"+sName+".phtml";
    	  try {
    	      URL u = new URL(url);
    	      byte[] b = new byte[256];
    	      InputStream in = null;
    	      ByteArrayOutputStream bo = new ByteArrayOutputStream();
    	      while (true) {
    	          try {
    	              in = u.openStream();
    	              int i;
    	              while ((i = in.read(b)) != -1) {
    	                  bo.write(b, 0, i);
    	              }
    	              String result = bo.toString();
    	              String[] stocks = result.split(";");
    	              for (String stock : stocks) {
    	                  String[] datas = stock.split(",");
    	                  //根据对照自己对应数据
    	                  for(int j = 0; j < datas.length; j++){
    	                	  System.out.println(datas[j]);
    	                  }
    	              }
    	              bo.reset();
    	          } catch (Exception e) {
    	              System.out.println(e.getMessage());
    	          } finally {
    	              if (in != null) {
    	                  in.close();
    	              }
    	          }
    	      }
    	  } catch (Exception ex) {
    	      System.out.println(ex.getMessage());
    	  }
    }

	public static String[] getDataHist(String code, String dt) {
		if (code.startsWith("3") || code.startsWith("00")) {
			code = "sz" + code;
		} else if (!code.startsWith("s")) {
			code = "sh" + code;
		}
		String sAddr = "http://vip.stock.finance.sina.com.cn/quotes_service/view/vMS_tradehistory.php?symbol=" + code + "&date=" + dt;
		Document doc = getDocument(sAddr);
		if (doc == null) return null;
		Elements elements = doc.select(".marketData");
		String[] stockdata = new String[8];

		for(Element element:elements){
			if(element.text()!=null&& !"".equals(element.text())){
				Elements tdes = element.select("td");
				for(int i = 0; i < tdes.size(); i++){
					if (i % 2 == 1) stockdata[i/2] = tdes.get(i).text();
				}
			}
		}

		return stockdata;
	}

    private static Document getDocument(String sAddr){ 
    	Document doc;
		try {
			doc = Jsoup.connect(sAddr).timeout(5000).get();
			return doc;
		} catch (IOException e) {
			e.printStackTrace();
			log.info(sAddr);
			return null;
		}
    }

    public static String cutTrim(String s, int width) {
		if (StringUtils.isEmpty(s)) return "";
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }

    public static double cvtDouble(String s) {
		if (StringUtils.isEmpty(s) || "--".equals(s.trim())) return 0;
		if (s.contains("主力") || s.contains("盈亏") || s.contains("成本")) return 0;
/*
		if (StringUtils.indexOf(s,"成本")>0) {
			return 0;
		}
*/
        return ConvertFactory.convert(Double.TYPE, s);
    }

    public static double calcWidth(String s0,String s1,String s2) {
		double zs = cvtDouble(s0);
		if (zs == 0) return 0;
		double zg = cvtDouble(s1);
		double zd = cvtDouble(s2);
		double zf = Math.round((zg - zd) / zs * 100000) / 1000.0;

        return zf;
    }

    public static double calcWidth(double zg,double zd,double zs) {
		if (zs == 0) return 0;
		double zf = Math.round((zg - zd) / zs * 100000) / 1000.0;

        return zf;
    }

	public static double subWidth(double zg,double zd) {
		double zf = Math.round((zg - zd) * 1000) / 1000.0;

		return zf;
	}

	public static void main(String[] args) {
		Document doc = getDocument("http://vip.stock.finance.sina.com.cn/mkt/#gn_zncd");
	}
}
