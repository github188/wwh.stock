package com.zjhcsoft.struc.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import com.zjhcsoft.struc.util.datasource.Link;

/**
 * URL相关的工具类
 * 
 * @author wangtao
 * @since 2014年10月26日
 */
public class URLUtil {

	/**
	 * 
	 * 从指定的DOM节点Node下提取外链
	 * 
	 * @param url
	 *            抓取URL
	 * @param href
	 *            href属性，部分网站的attr不是为href，此时需要自定义
	 */
	public static List<Link> extractOutlink(Element element, String url, String href) throws IOException,
	        URISyntaxException {
		element.elements();
		List<Element> list = new ArrayList<>();
		getAllChildrenElement(element, list);
		List<Link> outlinkList = new ArrayList<>();
		for (Element linkElement : list) {
			Link link = new Link();
			if("A".equals(linkElement.getName())){
				link.setUrl(toAbsURL(url, linkElement.attributeValue(href)).toString());
				link.setAnchor(linkElement.getStringValue().trim());
				outlinkList.add(link);
			}
			if("AREA".equals(linkElement.getName())){
				link.setUrl(toAbsURL(url, linkElement.attributeValue(href)).toString());
				link.setAnchor(linkElement.attributeValue("title").trim());
				outlinkList.add(link);
			}
		}
		return outlinkList;
	}

	/**
	 * 获取某节点下所有子节点
	 */
	public static void getAllChildrenElement(Element element, List<Element> list) {
		List<Element> elementList = element.elements();
		if (elementList.size() > 0) {
			for (Element child : elementList) {
				list.add((Element)child.clone());
				getAllChildrenElement(child, list);
			}
		}
	}

	/**
	 * 相对路径转绝对路径
	 * 
	 * @param baseURL
	 *            基本网页URL
	 * @param relativeURL
	 *            相对路径
	 */
	public static URL toAbsURL(String baseURL, String relativeURL) throws URISyntaxException, MalformedURLException {
		//relativeURL 判断url是否为javascript的，如果是，则解析，获取真实url地址
		relativeURL = getJavascriptUrl(relativeURL);
		URI base = new URI(baseURL);// 基本网页URI
		URI abs = base.resolve(relativeURL);// 解析于上述网页的相对URL，得到绝对URI
		URL absURL = abs.toURL();// 转成URL
		return absURL;
	}

	/**
	 * 解析a标签herf，如：javascript:onclick=opendocWindow('/website/xsxx.nsf/0/DCE0E73D922966E548257D94002EA83A?opendocument&type=sz')
	 * @param relativeURL a标签的href值
	 * @return
	 */
	public static String getJavascriptUrl(String relativeURL) {
		if (relativeURL.lastIndexOf("javascript:") !=-1 ) {
			relativeURL = StringUtils.substringBetween(relativeURL, "('", "')");
			return relativeURL;
		}
		return relativeURL;
	}
	
	public static void main(String[] args) {
		String url = "/website/xsxx.nsf/0/DCE0E73D922966E548257D94002EA83A?opendocument&type=sz";
		url = getJavascriptUrl(url);
		System.out.println(url);
	}
}
