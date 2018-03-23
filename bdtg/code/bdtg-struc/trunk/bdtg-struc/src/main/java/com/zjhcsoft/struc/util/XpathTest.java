package com.zjhcsoft.struc.util;

import java.io.IOException;

import com.zjhcsoft.struc.parse.wrapper.HtmlCommonParserWrapper;
import org.dom4j.Document;
import org.dom4j.Element;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * xpath测试类
 * 
 * @author wangtao
 * @since 2014年10月30日
 */
public class XpathTest {

	private static final Logger LOG = LoggerFactory.getLogger(XpathTest.class);

	static Document download() throws IOException {

		// String url = "http://www.weather.com.cn/weather/101010300.shtml";
		// String charset = "utf-8";
		// String method = "POST";
		// CommonFetcher commonFetcher = new CommonFetcher();
		//
		// String content = commonFetcher.httpExecute(url, charset, method);
		 HttpConnection connection = (HttpConnection) Jsoup.connect("http://www.weather.com.cn/weather/101010300.shtml");
//		org.jsoup.nodes.Document doc = connection
//		// .data("query", "Java")
//		        .userAgent("Mozilla")
//		        // .ignoreContentType(true)
//		        // .cookie("auth", "token")
//		        .timeout(100000).get();

		org.jsoup.Connection.Response res  = connection.timeout(100000).execute();
		System.err.println(res.body());
		
		Document document = HtmlCommonParserWrapper.parseDocument(res.body(), "utf-8");

		Element e = (Element) document
		        .selectSingleNode("//*[@id='form1']/TABLE[4]/TBODY/TR/TD[4]/TABLE[2]/TBODY/TR[6]/TD//TABLE/TBODY/TR[2]/TD[1]/P/B/SPAN");
		// .selectSingleNode("//*[@id='form1']/TABLE[4]/TBODY/TR/TD[4]/TABLE[2]/TBODY/TR[6]/TD/P/FONT/*/SPAN/FONT/TABLE/TBODY/TR[2]/TD[1]/P/B/SPAN");
		// //*[@id="form1"]/TABLE[4]/TBODY/TR/TD[4]/TABLE[2]/TBODY/TR[6]/TD/TABLE/TBODY/TR[2]/TD[1]/P/B/SPAN
//		System.out.println(document.asXML());

		return document;
	}

	public static void main(String[] args) throws Exception {
		download();
	}

}
