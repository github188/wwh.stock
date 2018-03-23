package com.zjhcsoft.struc.fetch.wrapper.pollution;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.dom4j.Document;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjhcsoft.struc.conf.Configuration;
import com.zjhcsoft.struc.fetch.CommonFetcher;
import com.zjhcsoft.struc.fetch.wrapper.CommonFetcherWrapper;
import com.zjhcsoft.struc.fetch.wrapper.HttpCommonFetcherWrapper;
import com.zjhcsoft.struc.parse.wrapper.HtmlCommonParserWrapper;

public class FjEnterpriseCommonFetcherWrapper extends CommonFetcherWrapper {

	public FjEnterpriseCommonFetcherWrapper(CommonFetcher fetcher) {
		super(fetcher);
	}

	@Override
	public Object fetch() {
		JSONArray arr = new JSONArray();
		String fetchUrl = fetcher.getUrl();
		String content = HttpCommonFetcherWrapper.httpExecute(fetchUrl,
				"utf-8", "get");
		Document document = HtmlCommonParserWrapper.parseDocument(content,
				"utf-8");
		Element e = (Element) document
				.selectSingleNode("//*[@id=\"context\"]/TABLE/TBODY");
		for (int i = 2; i <= e.elements().size(); i++) {
			JSONObject o = new JSONObject();
			Element aElement = (Element) document
					.selectSingleNode("//*[@id=\"context\"]/TABLE/TBODY/TR["
							+ i + "]/TD[1]/A");
			String title = aElement.getText();
			String pageUrl = aElement.attributeValue("href").replaceFirst(".",
					fetchUrl.substring(0, fetchUrl.lastIndexOf("/")));
			String pageContent = HttpCommonFetcherWrapper.httpExecute(pageUrl,
					"utf-8", "get");
			String publishDate = ((Element) document
					.selectSingleNode("//*[@id=\"context\"]/TABLE/TBODY/TR["
							+ i + "]/TD[2]")).getText();
			o.put("publishDate", publishDate);
			o.put("year",
					Integer.valueOf(title.substring(0, title.indexOf("年"))));
			if (title.contains("福建省国省控重点企业信息")) {
				o.put("type", "excel");
				Document d = HtmlCommonParserWrapper.parseDocument(pageContent,
						"utf-8");
				String excelUrl = ((Element) d
						.selectSingleNode("/HTML/BODY/DIV[2]/DIV[2]/DIV[5]/DIV/P/A"))
						.attributeValue("href").replaceFirst(".",
								pageUrl.substring(0, pageUrl.lastIndexOf("/")));
				o.put("url", getExcelFile(excelUrl));
			} else if (title.contains("福建省重金属污染重点防控企业名单")) {
				o.put("type", "html");
				o.put("content", pageContent);
			}
			arr.add(o);
		}
		return arr;
	}

	private static String getExcelFile(String fileUrl) {
		String filename = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
		File file = new File(Configuration.INSTANCE().getValue(
				Configuration.TEMP_DIR)
				+ filename);
		if (!file.exists()) {
			byte[] fileData = new byte[1024];// 从输入流中获取数据
			// 创建连接的地址
			URLConnection connection = null;
			URL url = null;
			InputStream is = null;
			OutputStream os = null;
			int availableLength = 0;//
			try {
				file.createNewFile();
				url = new URL(fileUrl);
				connection = url.openConnection();
				is = connection.getInputStream();
				os = new FileOutputStream(file);
				while (is.read(fileData) != -1) {
					os.write(fileData);
					availableLength = is.available();
					if (availableLength < 1024) {
						fileData = new byte[availableLength];
					}

				}
				os.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					is.close();
					os.close();
					is = null;
					os = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return file.getPath();
	}

	public static void main(String[] s) {
		getExcelFile("http://www.fjepb.gov.cn/zwgk/zfxxgkzl/wrygk/zdwr/201309/W020130903457467013809.xls");
	}
}
