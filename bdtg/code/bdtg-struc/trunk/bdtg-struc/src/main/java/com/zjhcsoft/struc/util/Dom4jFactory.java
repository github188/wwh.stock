package com.zjhcsoft.struc.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * dom4j工厂
 * 
 * @author wangtao
 * 
 */
public class Dom4jFactory {

	private static Logger LOG = LoggerFactory.getLogger(Dom4jFactory.class);

	/**
	 * 读取Classpath下的文件，并进行解析
	 */
	public static Document get(String name) throws DocumentException {
		URL url = Thread.currentThread().getContextClassLoader().getResource(name);
		if (url == null) {
			LOG.error("File Not Found!");
			return null;
		}
		InputStream in = null;
		Document document = null;
		try {
			in = url.openStream();
			SAXReader reader = new SAXReader();
			document = reader.read(in);
		} catch (IOException e) {
			LOG.error("Error reading xml", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				//
			}
		}
		return document;
	}

}
