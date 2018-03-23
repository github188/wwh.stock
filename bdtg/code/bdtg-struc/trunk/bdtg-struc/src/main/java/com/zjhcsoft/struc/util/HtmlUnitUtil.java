package com.zjhcsoft.struc.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.cyberneko.html.parsers.DOMParser;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.DOMReader;
import org.xml.sax.InputSource;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.gargoylesoftware.htmlunit.WebRequest;

/**
 * 
 * @author wangtao 2013-12-10
 */
public class HtmlUnitUtil {
	private static final ThreadLocal<WebClient> WEBCLIENT_THREAD_LOCAL = new ThreadLocal<WebClient>();

	public static WebClient getWebClient() {
		WebClient webClient = WEBCLIENT_THREAD_LOCAL.get();
		if (webClient == null) {
			webClient = new WebClient(BrowserVersion.CHROME_16);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setCssEnabled(true);
			WEBCLIENT_THREAD_LOCAL.set(webClient);
		}
		return webClient;
	}

	public static WebClient getWebClient(String host, int port) {
		WebClient webClient = WEBCLIENT_THREAD_LOCAL.get();
		if (webClient == null) {
			webClient = new WebClient(BrowserVersion.CHROME_16, host, port);
			webClient.getOptions().setTimeout(120000);
			webClient.waitForBackgroundJavaScript(60000);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setCssEnabled(true);
			WEBCLIENT_THREAD_LOCAL.set(webClient);
		}
		return webClient;
	}

	public static WebClient getPollutionWebClient()
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException {
		WebClient webClient = getWebClient();
		webClient
				.getPage("http://122.224.65.151:8989//nbjcsj/datashow_datashowinit");
		return webClient;
	}

	public static HtmlPage getHtmlPage(String url)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException {
		HtmlPage page = getWebClient().getPage(url);
		return page;
	}

	public static InputSource getHtmlInputSource(String url)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException {
		getWebClient().getPage(
				"http://app.zjepb.gov.cn:8089/nbjcsj/datashow_datashowinit");
		byte[] contentInOctets = getHtmlPage(url).asXml().getBytes();
		InputSource input = new InputSource(new ByteArrayInputStream(
				contentInOctets));
		return input;
	}

	public static String getHtmlContent(String url)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException {
		return ((HtmlPage) getHtmlPage(url)).asXml();
	}

	public static String getHtmlContent(String pageURL, String encoding)
			throws IOException {
		return getHtmlContent(pageURL, encoding, null, null);
	}

	public static String getHtmlContent(String pageURL, String encoding,
			String host, Integer port) throws IOException {
		StringBuilder pageHTML = new StringBuilder();
		URL url = new URL(pageURL);
		HttpURLConnection connection = null;
		if (host == null || port == null) {
			connection = (HttpURLConnection) url.openConnection();
		} else {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
					host, port));
			connection = (HttpURLConnection) url.openConnection(proxy);
		}
		connection.setRequestProperty("User-Agent", "MSIE 7.0");
		connection.usingProxy();
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		BufferedReader br = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), encoding));
		String line = null;
		while ((line = br.readLine()) != null) {
			pageHTML.append(line);
			pageHTML.append("\r\n");
		}
		connection.disconnect();
		return pageHTML.toString();
	}

	private static Document parsetodocument(String content) {
		Document document = null;
		try {
			DOMReader reader = new DOMReader();
			DOMParser parser = new DOMParser();
			parser.setFeature("http://xml.org/sax/features/namespaces", false);

			parser.setProperty(
					"http://cyberneko.org/html/properties/default-encoding",
					"utf-8");
			parser.parse(new InputSource(new ByteArrayInputStream(content
					.getBytes("utf-8"))));
			document = reader.read(parser.getDocument());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String Sd_Ent() {
		WebClient webClient = new WebClient(BrowserVersion.CHROME_16);
		webClient.getOptions().setTimeout(120000);
		webClient.waitForBackgroundJavaScript(60000);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());

		List reqParam = new ArrayList();
		reqParam.add(new NameValuePair("IsBeginZxjc", "1"));
		reqParam.add(new NameValuePair("Method", "LoadGrid"));
		reqParam.add(new NameValuePair("page", "1"));
		reqParam.add(new NameValuePair("rows", "1000"));
		URL Url = null;
		try {
			Url = new URL("http://58.56.98.78:8405/ajax/npublic/Index.ashx");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		WebRequest request = new WebRequest(Url, HttpMethod.POST);
		request.setRequestParameters(reqParam);
		TextPage page = null;
		try {
			page = webClient.getPage(request);
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return page.getContent();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String getClient() {
		WebClient webClient = new WebClient(BrowserVersion.CHROME_16);
		webClient.getOptions().setTimeout(120000);
		webClient.waitForBackgroundJavaScript(60000);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());

		List reqParam = new ArrayList();
		reqParam.add(new NameValuePair("dataPublicKey", "技术支持：江苏梦兰神彩科技发展有限公司"));
		reqParam.add(new NameValuePair("type", "POST"));
		reqParam.add(new NameValuePair("dynamicDirectory", "dynamicDirectory"));
		reqParam.add(new NameValuePair("serviceType", "crossDomainCall"));
		reqParam.add(new NameValuePair("serviceName", "publish"));
		reqParam.add(new NameValuePair("methodName", "queryEnteriseList"));
		reqParam.add(new NameValuePair("serviceSiteRootUrl", "http://218.94.78.61:8080/newPub/"));
		reqParam.add(new NameValuePair("jsonp", "jsoncallback"));
		reqParam.add(new NameValuePair("serviceType", "crossDomainCall"));
		URL Url = null;
		try {
			Url = new URL("http://218.94.78.61:8080/newPub/dynamicDirectory/service/json/call");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		WebRequest request = new WebRequest(Url, HttpMethod.POST);
		request.setRequestParameters(reqParam);
		HtmlPage page = null;
		try {
			page = webClient.getPage(request);
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.print(page.asXml());
		return page.asXml();
	}
	
	public static void similate(String[] args)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException, DocumentException, InterruptedException {
		getClient();
		long start = System.currentTimeMillis();

		WebClient webClient = new WebClient(BrowserVersion.CHROME_16);
		webClient.getOptions().setTimeout(120000);
		webClient.waitForBackgroundJavaScript(60000);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());

		HtmlPage page = webClient.getPage("http://222.177.117.35:808/publish/dataSearch/entDataList.aspx?datatype=1&watergas=1&entid=2&T=1442800450899");
		System.out.print(page.asXml());
		ScriptResult sr = page.executeJavaScript("pgBtn(2)");
		
		HtmlPage p1 = (HtmlPage) sr.getNewPage();
		System.out.print(p1.asXml());
		}
	
	@SuppressWarnings("unchecked")
		public static void main(String args[]) throws FailingHttpStatusCodeException, IOException, InterruptedException{
		WebClient webClient = new WebClient(BrowserVersion.CHROME_16);
		webClient.getOptions().setTimeout(120000);
		webClient.waitForBackgroundJavaScript(60000);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());

		List reqParam = new ArrayList();
		reqParam.add(new NameValuePair("river", "%E5%A4%AA%E6%B9%96"));
		reqParam.add(new NameValuePair("waterplace", ""));
		reqParam.add(new NameValuePair("qualfact", "1"));
		
		URL Url = null;
		try {
			Url = new URL("http://datacenter.mep.gov.cn/report/hjtj/water/water_five_year.jsp");
		}catch (MalformedURLException e) {
			e.printStackTrace();
		}

		WebRequest request = new WebRequest(Url, HttpMethod.POST);
		request.setRequestParameters(reqParam);
		HtmlPage page = null;
		try {
			page = webClient.getPage(request);
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.print(page.asXml());
	}
}
