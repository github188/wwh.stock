package com.zjhcsoft.struc.parse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.util.ajax.JSON;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjhcsoft.struc.bean.CrawlerBidInfo;
import com.zjhcsoft.struc.fetch.Fetcher;
import com.zjhcsoft.struc.fetch.HgzbwFetcher;

public class BhiParser extends Parser {

	@Override
	public Map<Object, String> parse(Object object, String url) {
		String base_url = "http://projectinfo.bhi.com.cn";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<Object, String> beanmap = new HashMap<Object, String>();
		String content = object.toString();
		JSONObject jobj =  JSONObject.parseObject(content);
		JSONArray array = jobj.getJSONArray("docs");
		for(int i=0;i<array.size();i++){
			JSONObject obj = array.getJSONObject(i);
			String name = obj.getString("title3");
			String PhaseName = obj.getString("PhaseName");
			String pfund = obj.getString("pfund");
			String industry = obj.getString("industry");
			String adddate = obj.getString("adddate");
			String title = obj.getString("title");
			
			String site_url =title.substring(title.indexOf("href=")+5,title.indexOf("title"));
			site_url = site_url.replaceAll("'", "");
			
			PhaseName = PhaseName.substring(PhaseName.indexOf(">")+1, PhaseName.lastIndexOf("<"));
			industry = industry.substring(industry.indexOf(">")+1, industry.lastIndexOf("<"));
			
			CrawlerBidInfo cbi = new CrawlerBidInfo();
			cbi.setBidBalance(pfund);
			cbi.setProjectName(name);
			cbi.setProjectPhase(PhaseName);
			cbi.setTradeType(industry);
			try {
				cbi.setPublishDate(sdf.parse(adddate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cbi.setUrl(base_url+site_url);
			
			beanmap.put(cbi, url);
		}
		return beanmap;
	}
	
	
	public static void main(String args[]) throws Exception{
		Fetcher fetcher = new HgzbwFetcher();
		fetcher.setUrl("http://projectinfo.bhi.com.cn/solr/SolrProject.ashx?solr_core=0&solr_rows=200&solr_rsort=0&solr_keywords=%25u8BF7%25u8F93%25u5165%25u5173%25u952E%25u8BCD&solr_area=&solr_industry=%25u77F3%25u6CB9%25u5316%25u5DE5%252C%25u7164%25u5316%25u5DE5%252C%25u7CBE%25u7EC6%25u5316%25u5DE5%252C%25u65E0%25u673A%25u5316%25u5DE5&solr_date=-366&solr_cbcolumns=0,1&solr_currentPage=1&solr_fund=&solr_jinzhan=&solr_xmxingzhi=&solr_qyxingzhi=&solr_leibie=&solr_fenlei=");
		Parser parser = new BhiParser();
		parser.parse(fetcher.call(), "http://projectinfo.bhi.com.cn/solr/SolrProject.ashx?solr_core=0&solr_rows=200&solr_rsort=0&solr_keywords=%25u8BF7%25u8F93%25u5165%25u5173%25u952E%25u8BCD&solr_area=&solr_industry=%25u77F3%25u6CB9%25u5316%25u5DE5%252C%25u7164%25u5316%25u5DE5%252C%25u7CBE%25u7EC6%25u5316%25u5DE5%252C%25u65E0%25u673A%25u5316%25u5DE5&solr_date=-366&solr_cbcolumns=0,1&solr_currentPage=1&solr_fund=&solr_jinzhan=&solr_xmxingzhi=&solr_qyxingzhi=&solr_leibie=&solr_fenlei=");
	}
}