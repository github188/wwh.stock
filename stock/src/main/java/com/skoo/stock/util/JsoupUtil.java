package com.skoo.stock.util;

import com.skoo.common.SystemConfig;
import com.skoo.commons.StringUtils;
import com.skoo.stock.common.Constant;
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


public class JsoupUtil {
	private static Logger log = Logger.getLogger("mylogger");

	public static Document getDocument(String sAddr){
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

	public static void getDocElement(LinkedHashMap<String, String[]> map, Document doc, String sel){
		String val[];
		Elements elements,es,tdes,tmp;
		Element tmpElem;
		int cnt = map.size();
		boolean titleFlg = false;
		if (cnt > 0) titleFlg = true;

		elements = doc.select(sel);
		for(Element element:elements) {
			//if (titleFlg) continue;
			if (element.text() != null && !"".equals(element.text())) {
				es = element.select("tr");
				for (Element tdelement : es) {
					if (titleFlg) {
						titleFlg = false;
						continue;
					}
					tdes = tdelement.select("td");
					if (tdes.size() == 0) tdes = tdelement.select("th");
					val =  new String[tdes.size()*2];
					for(int i = 0; i < tdes.size(); i++){
						tmp = tdes.get(i).getAllElements();
						if (tmp.size() > 3) {
							tmpElem = tmp.get(2);
						} else {
							tmpElem = tmp.get(tmp.size() - 1);
						}
						val[i] = tmpElem.text();
						if (tmpElem.select("a[href]").size() > 0) val[tdes.size() + i] = tmpElem.select("a[href]").attr("href");
					}
					map.put(String.valueOf(cnt), val);
					cnt++;
				}
			}
		}

		return;
	}

	public static void getDocElemAdd(LinkedHashMap<String, String[]> map, Document doc, String sel, int ii){
		String key;
		String val[],valTmp[];
		Elements elements,es,tdes,tmp;
		Element tmpElem;

		elements = doc.select(sel);
		for(Element element:elements) {
			//if (titleFlg) continue;
			if (element.text() != null && !"".equals(element.text())) {
				es = element.select("tr");
				for (Element tdelement : es) {
					tdes = tdelement.select("td");
					if (tdes.size() == 0) tdes = tdelement.select("th");
					val =  new String[tdes.size()*2];
					for(int i = 0; i < tdes.size(); i++){
						tmp = tdes.get(i).getAllElements();
						if (tmp.size() > 3) {
							tmpElem = tmp.get(2);
						} else {
							tmpElem = tmp.get(tmp.size() - 1);
						}
						val[i] = tmpElem.text();
						if (tmpElem.select("a[href]").size() > 0) val[tdes.size() + i] = tmpElem.select("a[href]").attr("href");
					}
					key = val[1];
					if (ii == 0) {
						map.put(key, val);
						continue;
					}
					valTmp = map.get(key);
					if (valTmp == null) {
						map.put(key, val);
					} else if (!Constant.CODETITLE.equals(key)) {
						valTmp[valTmp.length-5+ii]=val[val.length/2-2];
						map.put(key,valTmp);
					}
				}
			}
		}

		return;
	}

	public static void getDocValue(LinkedHashMap<String, String> map, Document doc, String sel){
		Elements elements,es,tdes;
		int cnt = map.size();

		elements = doc.select(sel);
		for(Element element:elements) {
			if (element.text() != null && !"".equals(element.text())) {
				es = element.select("tr");
				if (es.size() == 0) {
					if (element.select("h2").size() > 0) {
						map.put(String.valueOf(cnt), element.select("h2").get(0).text());
						cnt++;
					}
				}
				for (Element tdelement : es) {
					tdes = tdelement.select("td");
					for(int i = 0; i < tdes.size(); i++){
						if (tdes.get(i).getAllElements().size() < 2 ) {
							map.put(String.valueOf(cnt), tdes.get(i).getAllElements().get(0).text());
						} else {
							map.put(String.valueOf(cnt), tdes.get(i).getAllElements().get(1).text());
						}
						cnt++;
					}
				}
			}
		}

		return;
	}

	public static String cutTrim(String s, int width) {
		if (StringUtils.isEmpty(s)) return "";
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }

    public static double cvtDouble(String s) {
		if (StringUtils.isEmpty(s) || "-".equals(s) || s.indexOf("--")>=0) return 0;
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
