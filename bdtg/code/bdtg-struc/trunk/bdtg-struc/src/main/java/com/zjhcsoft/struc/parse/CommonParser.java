package com.zjhcsoft.struc.parse;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjhcsoft.struc.parse.wrapper.CommonParserWrapper;
import com.zjhcsoft.struc.parse.wrapper.HtmlCommonParserWrapper;
import com.zjhcsoft.struc.util.datasource.DataSourceHandler;

public class CommonParser extends Parser {

	private static final Logger LOG = LoggerFactory.getLogger(CommonParser.class);

	private CommonParserWrapper commonParserWrapper;

	public CommonParser() {
		String parsetype = DataSourceHandler.tables.getParsetype();
		if(StringUtils.isEmpty(parsetype)) {
			parsetype = "html/xml";
		}
		if("html/xml".equals(parsetype)) {
			commonParserWrapper = new HtmlCommonParserWrapper(this);
		} else {
			if(parsetype.contains(".")) {
				parsetype = parsetype.substring(0,parsetype.lastIndexOf("."))+StringUtils.capitalize(parsetype.substring(parsetype.lastIndexOf(".")));
			} else {
				parsetype = StringUtils.capitalize(parsetype);
			}
			String className = "com.zjhcsoft.struc.parse.wrapper."+parsetype+"CommonParserWrapper";
			try {
				commonParserWrapper = (CommonParserWrapper)Class.forName(className).getConstructor(CommonParser.class).newInstance(this);
			}catch (Exception e) {
				LOG.error("",e);
			}
		}
	}

	@Override
	public Map<Object, String> parse(Object object, String url) {
		return commonParserWrapper.parse(object,url);
	}
}