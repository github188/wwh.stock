package com.zjhcsoft.struc.parse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.zjhcsoft.struc.bean.CrawlerBidInfo;
import com.zjhcsoft.struc.fetch.Fetcher;
import com.zjhcsoft.struc.fetch.HgzbwFetcher;
import com.zjhcsoft.struc.util.DocumentParser;

public class HgzbwParser extends Parser {

	@Override
	public Map<Object, String> parse(Object object, String url) {
		String base_url = "http://www.hgzbw.com";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<Object, String> beanmap = new HashMap<Object, String>();
		String content = object.toString();
		Document doc = DocumentParser.parse(content, "utf-8");
		@SuppressWarnings("unchecked")
		List<Node> list = doc.selectNodes("/HTML/BODY/DIV/DIV/TABLE/TBODY/TR/TD[1]/TABLE[5]/TBODY/TR");
		for(int i=2;i<=list.size()-2;i++){
			Element trade = (Element) doc.selectSingleNode("/HTML/BODY/DIV/DIV/TABLE/TBODY/TR/TD[1]/TABLE[5]/TBODY/TR["+i+"]/TD[5]");
			if("化工化学".equals(trade.getStringValue().trim())){
				String province = ((Element) doc.selectSingleNode("/HTML/BODY/DIV/DIV/TABLE/TBODY/TR/TD[1]/TABLE[5]/TBODY/TR["+i+"]/TD[1]")).getStringValue().trim();
				String name = ((Element) doc.selectSingleNode("/HTML/BODY/DIV/DIV/TABLE/TBODY/TR/TD[1]/TABLE[5]/TBODY/TR["+i+"]/TD[2]")).getStringValue().trim();
				String phase = ((Element) doc.selectSingleNode("/HTML/BODY/DIV/DIV/TABLE/TBODY/TR/TD[1]/TABLE[5]/TBODY/TR["+i+"]/TD[3]")).getStringValue().trim();
				String balance = ((Element) doc.selectSingleNode("/HTML/BODY/DIV/DIV/TABLE/TBODY/TR/TD[1]/TABLE[5]/TBODY/TR["+i+"]/TD[4]")).getStringValue().trim();
				String publish_time = ((Element) doc.selectSingleNode("/HTML/BODY/DIV/DIV/TABLE/TBODY/TR/TD[1]/TABLE[5]/TBODY/TR["+i+"]/TD[6]")).getStringValue().trim();
				String site_url = ((Element) doc.selectSingleNode("/HTML/BODY/DIV/DIV/TABLE/TBODY/TR/TD[1]/TABLE[5]/TBODY/TR["+i+"]/TD[2]/A")).attributeValue("href");
				CrawlerBidInfo cbi = new CrawlerBidInfo();
				cbi.setBidBalance(balance);
				cbi.setProjectName(name);
				cbi.setProjectPhase(phase);
				cbi.setProvince(province);
				cbi.setTradeType("化工化学");
				cbi.setUrl(base_url+site_url);
				try {
					cbi.setPublishDate(sdf.parse(publish_time));
				} catch (ParseException e) {
					e.printStackTrace();
					continue;
				}
				
				beanmap.put(cbi, url);
			}
		}
		
		return beanmap;
	}
	
	
	public static void main(String args[]) throws Exception{
		Fetcher fetcher = new HgzbwFetcher();
		fetcher.setUrl("http://www.hgzbw.com/l_huagong-xiangmu_1.html");
		Parser parser = new HgzbwParser();
		parser.parse(fetcher.call(), "http://www.hgzbw.com/l_huagong-xiangmu_1.html");
	}
}