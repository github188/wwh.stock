package cn.hzskt.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.LinkedHashMap;

import com.sun.media.jfxmedia.logging.Logger;
import com.zjhcsoft.smartcity.magic.common.SystemConfig;
import com.zjhcsoft.smartcity.magic.commons.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class StockUtil {
	public static String path = "D:/wwh/stock/";
	static Log log = LogFactory.getLog("mylogger");

    public static String[] getStockValue(String sName){ 
	    String responseBody = RequestUtil.doGet("http://hq.sinajs.cn/list="+sName,null);
	    String stockdata = "";
	    if (responseBody != null) stockdata = responseBody.split("\"")[1];
	    String[] stockdatasplit = new String[]{};
	    if (stockdata != null) stockdatasplit = stockdata.split(",");

	    return stockdatasplit;
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
*/
				tdes = doc.select("#stock_quoteinfo_cj");
				stockdata[6] = tdes.get(0).text().replace("万手","");
				tdes = doc.select("#stock_quoteinfo_sy");
				stockdata[12] = tdes.get(0).text();
/*
                tdes = doc.select("#stock_quoteinfo_zs");
                stockdata[4] = tdes.get(0).text();
                tdes = doc.select("#stock_quoteinfo_zd");
                stockdata[5] = tdes.get(0).text();
*/
				tdes = doc.select("#stock_quoteinfo_hs");
				stockdata[8] = tdes.get(0).text();
				tdes = doc.select("#stock_quoteinfo_zf");
				stockdata[9] = tdes.get(0).text();
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
		} else if (fName.length() < 6) {
			stockdatasplit = new String[] {"代码", "简称", "最新价", "涨跌幅", "涨跌额", "5分钟涨幅", "成交量(手)", "成交额(万元)", "换手率", "振幅", "量比", "市盈率"};
        }

	    return getList(sAddr, count, stockdatasplit);
    }     

    private static LinkedHashMap<String, String[]> getList(String sAddr, String count, String[] stockdatasplit) {
    	Document doc;
    	Elements elements;
    	String sPrex = "#datalist";
    	if (!sAddr.endsWith(".html")) sPrex = "table.tbody_right";

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
        	if (doc == null) return null;

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
							if (StringUtils.isNotEmpty(dt) && stockdatasplit[0].equals(dt)) {
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
}
