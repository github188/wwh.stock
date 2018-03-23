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

public class ChinabiddingParser extends Parser {

	@Override
	public Map<Object, String> parse(Object object, String url) {
		String base_url = "http://www.chinabidding.cn";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<Object, String> beanmap = new HashMap<Object, String>();
		String content = object.toString();
		Document doc = DocumentParser.parse(content, "utf-8");
		@SuppressWarnings("unchecked")
		List<Node> list = doc.selectNodes("/HTML/BODY/DIV[1]/DIV/DIV[6]/DIV[2]/DIV[3]/TABLE/TBODY/TR");
		for(int i=2;i<=list.size();i++){
				String province = ((Element) doc.selectSingleNode("/HTML/BODY/DIV[1]/DIV/DIV[6]/DIV[2]/DIV[3]/TABLE/TBODY/TR["+i+"]/TD[5]")).getStringValue().trim();
				String name = ((Element) doc.selectSingleNode("/HTML/BODY/DIV[1]/DIV/DIV[6]/DIV[2]/DIV[3]/TABLE/TBODY/TR["+i+"]/TD[2]/A")).attributeValue("title");
				String trade = ((Element) doc.selectSingleNode("/HTML/BODY/DIV[1]/DIV/DIV[6]/DIV[2]/DIV[3]/TABLE/TBODY/TR["+i+"]/TD[3]")).getStringValue().trim();
				String phase = ((Element) doc.selectSingleNode("/HTML/BODY/DIV[1]/DIV/DIV[6]/DIV[2]/DIV[3]/TABLE/TBODY/TR["+i+"]/TD[4]")).getStringValue().trim();
				String publish_time = ((Element) doc.selectSingleNode("/HTML/BODY/DIV[1]/DIV/DIV[6]/DIV[2]/DIV[3]/TABLE/TBODY/TR["+i+"]/TD[6]")).getStringValue().trim();
				String site_url = ((Element) doc.selectSingleNode("/HTML/BODY/DIV[1]/DIV/DIV[6]/DIV[2]/DIV[3]/TABLE/TBODY/TR["+i+"]/TD[2]/A")).attributeValue("href");
				CrawlerBidInfo cbi = new CrawlerBidInfo();
				cbi.setProjectName(name);
				cbi.setProjectPhase(phase);
				cbi.setProvince(province);
				cbi.setTradeType(trade);
				cbi.setUrl(base_url+site_url);
				try {
					cbi.setPublishDate(sdf.parse(publish_time));
				} catch (ParseException e) {
					e.printStackTrace();
					continue;
				}
				
				beanmap.put(cbi, url);
			}
		
		return beanmap;
	}
	
	
	public static void main(String args[]) throws Exception{
		Fetcher fetcher = new HgzbwFetcher();
		fetcher.setUrl("http://www.chinabidding.cn/cblcn/xmxx/list?rp=20&numa=3&keywords=&numb=0&page=1&classb_id=4&areaid=0&table_type=0&search_type=CONTEXT&b_date=year");
		Parser parser = new ChinabiddingParser();
		parser.parse(fetcher.call(), "http://www.chinabidding.cn/cblcn/xmxx/list?rp=20&numa=3&keywords=&numb=0&page=1&classb_id=4&areaid=0&table_type=0&search_type=CONTEXT&b_date=year");
	}
}