package com.zjhcsoft.struc.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.cyberneko.html.parsers.DOMParser;
import org.dom4j.Document;
import org.dom4j.io.DOMReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DocumentParser {
	public static Document parse(String content, String charset) {
		DOMParser parser = new DOMParser();

		// 使用nekohtml对源码进行清理、补全等处理
		try {
			parser.setFeature("http://xml.org/sax/features/namespaces", false);
			parser.setProperty(
					"http://cyberneko.org/html/properties/default-encoding",
					charset);
			parser.parse(new InputSource(new ByteArrayInputStream(content
					.getBytes(charset))));
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		Document document = new DOMReader().read(parser.getDocument());

		return document;
	}
}
