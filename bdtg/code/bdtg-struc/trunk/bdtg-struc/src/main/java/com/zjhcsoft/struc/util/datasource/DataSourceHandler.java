package com.zjhcsoft.struc.util.datasource;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjhcsoft.struc.util.Dom4jFactory;
import com.zjhcsoft.struc.util.StringUtil;
import com.zjhcsoft.struc.util.datasource.exception.DataSourceConfigLoadingException;

public class DataSourceHandler {

	private static final Logger LOG = LoggerFactory.getLogger(DataSourceHandler.class);

	private static Document dataSource = null;
	private static final String DEFAULT_MAPPING_FORMATTER_PATH = "com.zjhcsoft.struc.util.datasource.formatter.";

	public static String httpMethod = "POST";
	public static Tables tables;
	public static URLPattern urlPattern;
	public static String datatype = "html/xml";
	public static String charset = "utf-8";
	public static String fetchtype = "";

	/**
	 * 根据数据源名称获得数据源配置文件
	 */
	public static void loadConfig(String dataSourceName) {
		try {
			if (dataSource == null) {
				synchronized (DataSourceHandler.class) {
					try {
						dataSource = Dom4jFactory.get("datasource/" + dataSourceName + ".xml");
					} catch (DocumentException e) {
						LOG.error(e.getMessage(), e);
					}
				}
			}
			if (dataSource == null) {
				LOG.error("数据源配置文件加载失败!");
				System.exit(-1);
			}

			parseURLPattern();

			parseTables();

		} catch (DataSourceConfigLoadingException e) {
			LOG.error(e.getMsg(), e);
			System.exit(-1);
		}
	}

	private static void parseURLPattern() throws DataSourceConfigLoadingException {
		// url-pattern
		Node urlPatternNode = dataSource.selectSingleNode("/config/url-pattern");
		if (urlPatternNode == null) {
			throw new DataSourceConfigLoadingException("找不到/config/url-pattern");
		}
		urlPattern = new URLPattern();

		// url
		Node urlNode = dataSource.selectSingleNode("/config/url-pattern/url");
		if (urlNode == null) {
			throw new DataSourceConfigLoadingException("找不到URL");
		}
		urlPattern.setUrl(urlNode.getStringValue().trim());

		// fetchtype
		Node fetchtypeNode = dataSource.selectSingleNode("/config/url-pattern/fetchtype");
		if (fetchtypeNode != null) {
			String fetchtype = fetchtypeNode.getStringValue().trim();
			urlPattern.setFetchtype(fetchtype);
		}

		// fetchtype
		Node httpmethodNode = dataSource.selectSingleNode("/config/url-pattern/fetchtype");
		if (fetchtypeNode != null) {
			String httpmethod = fetchtypeNode.getStringValue().trim();
			urlPattern.setHttpmethod(httpmethod);
		}

		// datatype
		Node datatypeNode = dataSource.selectSingleNode("/config/url-pattern/datatype");
		if (datatypeNode != null) {
			String datatype = datatypeNode.getStringValue().trim();
			urlPattern.setDatatype(datatype);
		}

		// charset
		Node charsetNode = dataSource.selectSingleNode("/config/url-pattern/charset");
		if (charsetNode != null) {
			String charset = charsetNode.getStringValue().trim();
			if (!StringUtil.isEmpty(charset) && !Charset.isSupported(charset)) {
				throw new DataSourceConfigLoadingException("charset:该编码方式不支持");
			}
			urlPattern.setCharset(charset);
		}

		// mappingList
		List<Element> mappingElementList = dataSource.selectNodes("/config/url-pattern/mapping");
		List<Mapping> mappingList = parseMappingList(mappingElementList);
		if (mappingList != null) {
			for (Mapping mapping : mappingList) {
				if (mapping.getId() == null) {
					throw new DataSourceConfigLoadingException("/config/url-pattern/mapping ID未定义");
				}
			}
		}
		urlPattern.setMappingList(mappingList);

		// outlink
		Element outlinkElement = (Element) dataSource.selectSingleNode("/config/url-pattern/outlink");
		if(outlinkElement != null) {
			Outlink outlink = parseOutlink(outlinkElement);
			urlPattern.setOutlink(outlink);
		}

	}

	private static void parseTables() throws DataSourceConfigLoadingException {

		Node tablesNode = dataSource.selectSingleNode("/config/tables");
		if (tablesNode == null) {
			throw new DataSourceConfigLoadingException("找不到/config/tables");
		}
		tables = new Tables();
		Node parsetypeNode = dataSource.selectSingleNode("/config/tables/parsetype");
		if (parsetypeNode != null) {
			String parsetype = parsetypeNode.getStringValue().trim();
			tables.setParsetype(parsetype);
		}
		Node savetypeNode = dataSource.selectSingleNode("/config/tables/savetype");
		if (savetypeNode != null) {
			String savetype = savetypeNode.getStringValue().trim();
			tables.setSavetype(savetype);
		}
		List<Element> tableElementList = dataSource.selectNodes("/config/tables/table");
		List<Table> tableList = new ArrayList<Table>();
		tables.setTableList(tableList);
		if(tableElementList != null) {
			for (Element tableElement : tableElementList) {
				Table table = new Table();
				tableList.add(table);
				// schema
				String schema = tableElement.attributeValue("schema");
				if (schema != null) {
					table.setSchema(schema);
				} else {
					throw new DataSourceConfigLoadingException(tableElement.getPath() + " 找不到schema");
				}
				// tableName
				String tableName = tableElement.attributeValue("tableName");
				if (tableName != null) {
					table.setTableName(tableName);
				} else {
					throw new DataSourceConfigLoadingException(tableElement.getPath() + ":找不到tableName");
				}
				// insertJudge
				String insertJudge = tableElement.attributeValue("insertJudge");
				if (insertJudge != null) {
					table.setInsertJudge(Boolean.parseBoolean(insertJudge));
				}
				// updateJudge
				String updateJudge = tableElement.attributeValue("updateJudge");
				if (updateJudge != null) {
					table.setUpdateJudge(Boolean.parseBoolean(updateJudge));
				}
				//checkpath
				String checkpath = tableElement.attributeValue("checkpath");
				if (checkpath != null) {
					table.setCheckpath(checkpath);;
				}
				// mappingList
				List<Element> mappingElementList = tableElement.elements("mapping");
				List<Mapping> mappingList = parseMappingList(mappingElementList);
				table.setMappingList(mappingList);

				// repeatMapping
				Element repeatMappingElement = tableElement.element("repeatMapping");
				RepeatMapping repeatMapping = parseRepeatMapping(repeatMappingElement);
				table.setRepeatMapping(repeatMapping);
			}
		}
	}

	private static RepeatMapping parseRepeatMapping(Element repeatMappingElement)
	        throws DataSourceConfigLoadingException {
		RepeatMapping repeatMapping = null;
		if (repeatMappingElement != null) {
			repeatMapping = new RepeatMapping();

			// lengthXpath
			String lengthXpath = repeatMappingElement.attributeValue("lengthXpath");
			if (lengthXpath == null) {
				throw new DataSourceConfigLoadingException(repeatMappingElement.getPath() + " 找不到lengthXpath");
			}
			repeatMapping.setLengthXpath(lengthXpath);

			// startNum
			String startNumString = repeatMappingElement.attributeValue("startNum");
			Integer startNum = null;
			if (startNumString != null && NumberUtils.isDigits(startNumString)) {
				startNum = Integer.parseInt(startNumString);
			}
			if (startNum == null) {
				throw new DataSourceConfigLoadingException(repeatMappingElement.getPath() + " 找不到lengthXpath");
			}
			repeatMapping.setStartNum(startNum);

			// endNum
			String endNumString = repeatMappingElement.attributeValue("endNum");
			Integer endNum = null;
			if (endNumString != null && NumberUtils.isDigits(endNumString)) {
				endNum = Integer.parseInt(endNumString);
				repeatMapping.setEndNum(endNum);
			}

			// ignoreNumArray
			String ignoreNumArrayString = repeatMappingElement.attributeValue("ignoreNumArray");
			if (ignoreNumArrayString != null) {
				List<Integer> ignoreNumList = new ArrayList<>();
				String ignoreNumArrayStringArray[] = ignoreNumArrayString.split(",");
				for (String ignoreNumString : ignoreNumArrayStringArray) {
					if (NumberUtils.isDigits(ignoreNumString)) {
						ignoreNumList.add(Integer.parseInt(ignoreNumString));
					}
				}
				Integer[] ignoreNumArray = new Integer[ignoreNumList.size()];
				ignoreNumList.toArray(ignoreNumArray);
				repeatMapping.setIgnoreNumArray(ignoreNumArray);
			}

			// mappingList
			List<Element> repeatMappingElementList = repeatMappingElement.elements("mapping");
			if (repeatMappingElementList == null) {
				throw new DataSourceConfigLoadingException(repeatMappingElement.getPath() + " 找不到mapping");
			}
			List<Mapping> repeatMappingList = parseMappingList(repeatMappingElementList);
			repeatMapping.setMappingList(repeatMappingList);

			// repeatMapping
			Element repeatMapping2Element = repeatMappingElement.element("repeatMapping");
			RepeatMapping repeatMapping2 = parseRepeatMapping(repeatMapping2Element);
			repeatMapping.setRepeatMapping(repeatMapping2);
		}
		return repeatMapping;
	}

	private static List<Mapping> parseMappingList(List<Element> mappingElementList)
	        throws DataSourceConfigLoadingException {
		if (mappingElementList != null && mappingElementList.size() > 0) {
			List<Mapping> mappingList = new ArrayList<Mapping>();
			for (Element mappingElement : mappingElementList) {
				Mapping mapping = parseMapping(mappingElement);
				mappingList.add(mapping);
			}
			return mappingList;
		}
		return null;
	}

	private static Mapping parseMapping(Element mappingElement) throws DataSourceConfigLoadingException {
		Mapping mapping = new Mapping();

		// id
		String id = mappingElement.attributeValue("id");
		if (id != null) {
			mapping.setId(id);
		}

		// refId
		String refId = mappingElement.attributeValue("refId");
		if (refId != null) {
			mapping.setRefId(refId);
		}
		
		//value
		String value = mappingElement.attributeValue("value");
		if (value != null) {
			mapping.setValue(value);;
		}
				
		// dbColumn
		String dbColumn = mappingElement.attributeValue("dbColumn");
		if (dbColumn != null) {
			mapping.setDbColumn(dbColumn);
		} else {
			if (mapping.getId() == null && mapping.getRefId() == null) {
				throw new DataSourceConfigLoadingException(mappingElement.getPath() + ":找不到dbColumn");
			}
		}

		// xpath
		String xpath = mappingElement.attributeValue("xpath");
		if (xpath != null) {
			mapping.setXpath(xpath);
		} else {
			if (mapping.getRefId() == null&&mapping.getValue() ==null) {
				throw new DataSourceConfigLoadingException(mappingElement.getPath() + ":找不到xpath");
			}
		}

		// type
		String type = mappingElement.attributeValue("type");
		if (type != null) {
			mapping.setType(type);
		}

		// replace
		String replace = mappingElement.attributeValue("replace");
		if (replace != null) {
			mapping.setReplace(replace);
		}
		// Xbias targetX偏差值
		String XbiasString = mappingElement.attributeValue("Xbias");
		if (XbiasString != null && checkDigits(XbiasString)) {
			mapping.setXbias(Integer.valueOf(XbiasString));
		}
		// Ybias targetY偏差值
		String YbiasString = mappingElement.attributeValue("Ybias");
		if (YbiasString != null && checkDigits(YbiasString)) {
			mapping.setYbias(Integer.valueOf(YbiasString));
		}
		// startString
		String startString = mappingElement.attributeValue("startString");
		if (startString != null) {
			mapping.setStartString(startString);
		}
		// endString
		String endString = mappingElement.attributeValue("endString");
		if (endString != null) {
			mapping.setEndString(endString);
		}
		// insertJudge
		String mappingInsertJudge = mappingElement.attributeValue("insertJudge");
		if (mappingInsertJudge != null) {
			mapping.setInsertJudge(Boolean.parseBoolean(mappingInsertJudge));
		}
		// updateJudge
		String mappingUpdateJudge = mappingElement.attributeValue("updateJudge");
		if (mappingUpdateJudge != null) {
			mapping.setUpdateJudge(Boolean.parseBoolean(mappingUpdateJudge));
		}
		// formatter
		String formatter = mappingElement.attributeValue("formatter");
		if (formatter != null) {
			if (formatter.indexOf(".") < 0) {
				// 默认路径
				formatter = DEFAULT_MAPPING_FORMATTER_PATH + formatter;
			}
			mapping.setFormatter(formatter);
		}
		// defaulfValue
		String defaultValue = mappingElement.attributeValue("defaultValue");
		if (defaultValue != null) {
			mapping.setDefaultValue(defaultValue);
		}
		// targetX
		String targetX = mappingElement.attributeValue("targetX");
		if (targetX != null) {
			mapping.setTargetX(targetX);
		}
		// targetY
		String targetY = mappingElement.attributeValue("targetY");
		if (targetY != null) {
			mapping.setTargetY(targetY);
		}
		// preid
		String preid = mappingElement.attributeValue("preid");
		if (preid != null) {
			mapping.setPreid(preid);
		}
		
		
		return mapping;
	}

	/*
	 * private static List<Outlink> parseOutlinkList(List<Element>
	 * outlinkElementList) throws DataSourceConfigLoadingException { if
	 * (outlinkElementList != null && outlinkElementList.size() > 0) {
	 * List<Outlink> outlinkList = new ArrayList<Outlink>(); for (Element
	 * outlinkElement : outlinkElementList) { Outlink outlink =
	 * parseOutlink(outlinkElement); outlinkList.add(outlink); } return
	 * outlinkList; } return null; }
	 */

	private static Outlink parseOutlink(Element outlinkElement) throws DataSourceConfigLoadingException {
		Outlink outlink = new Outlink();
		if(outlinkElement != null) {
			// datatype
			Node datatypeNode = outlinkElement.element("datatype");
			if (datatypeNode != null) {
				String datatype = datatypeNode.getStringValue().trim();
				if (!isDataType(datatype)) {
					throw new DataSourceConfigLoadingException(outlinkElement.getPath() + " datatype:内容格式不正确");
				}
				outlink.setDatatype(datatype);
			}
			outlink.setDatatype(datatype);
			DataSourceHandler.datatype = datatype;
		}

		// charset
		Node charsetNode = outlinkElement.element("charset");
		if (charsetNode != null) {
			String charset = charsetNode.getStringValue().trim();
			if (!StringUtil.isEmpty(charset) && !Charset.isSupported(charset)) {
				throw new DataSourceConfigLoadingException(
						outlinkElement.getPath() + " charset:该编码方式不支持");
			}
			outlink.setCharset(charset);
			DataSourceHandler.charset = charset;
		}
		// fetchtype
		Node fetchtypeNode = outlinkElement.element("fetchtype");
		if (fetchtypeNode != null) {
			String fetchtype = fetchtypeNode.getStringValue().trim();
			outlink.setFetchtype(fetchtype);
			DataSourceHandler.fetchtype = fetchtype;
		}

		// xpath
		String xpath = outlinkElement.attributeValue("xpath");
		if (xpath != null) {
			outlink.setXpath(xpath);
		} else {
			throw new DataSourceConfigLoadingException(outlinkElement.getPath()
					+ " 找不到xpath");
		}
		// xpathType
		String xpathType = outlinkElement.attributeValue("xpathType");
		if (xpathType != null) {
			outlink.setXpathType(xpathType);
		} else {
			throw new DataSourceConfigLoadingException(outlinkElement.getPath()
					+ ":找不到xpathType");
		}
		// number
		String number = outlinkElement.attributeValue("number");
		if (number != null) {
			if (!NumberUtils.isDigits(number)) {
				throw new DataSourceConfigLoadingException(
						outlinkElement.getPath() + ":number内容格式错误");
			}
			outlink.setNumber(Integer.parseInt(number));
		}
		// mappingList
		List<Element> mappingElementList = outlinkElement.elements("mapping");
		List<Mapping> mappingList = parseMappingList(mappingElementList);
		if (mappingList != null) {
			for (Mapping mapping : mappingList) {
				if (mapping.getId() == null) {
					throw new DataSourceConfigLoadingException(
							outlinkElement.getPath() + "mapping ID未定义");
				}
			}
		}
		outlink.setMappingList(mappingList);
		// extractList
		List<Element> extractElementList = outlinkElement.elements("extract");
		List<Extract> extractList = parseExtractList(extractElementList);

		outlink.setExtractList(extractList);
		// outlink
		Element outlinkElement1 = outlinkElement.element("outlink");
		if (outlinkElement1 != null) {
			Outlink outlink1 = parseOutlink(outlinkElement1);
			outlink.setOutlink(outlink1);
		}

		// numberpath
		String numberpath = outlinkElement.attributeValue("numberpath");
		if (numberpath != null) {
			outlink.setNumberpath(numberpath);
		}
		
		// timeparam
		String timeparam = outlinkElement.attributeValue("timeparam");
		if (timeparam != null) {
			outlink.setTimeparam(timeparam);
		}
		return outlink;
	}

	private static Extract parseExtract(Element extractElement)
			throws DataSourceConfigLoadingException {
		Extract extract = new Extract();
		// extractAttr
		String extractAttr = extractElement.attributeValue("extractAttr");
		if (extractAttr != null) {
			extract.setExtractAttr(extractAttr);
		}
		// include
		String include = extractElement.attributeValue("include");
		if (include != null) {
			extract.setInclude(include);
		}
		// exclude
		String exclude = extractElement.attributeValue("exclude");
		if (exclude != null) {
			extract.setExclude(exclude);
		}
		return extract;
	}

	private static List<Extract> parseExtractList(
			List<Element> extractElementList)
			throws DataSourceConfigLoadingException {
		if (extractElementList != null && extractElementList.size() > 0) {
			List<Extract> extractList = new ArrayList<Extract>();
			for (Element extractElement : extractElementList) {
				Extract extract = parseExtract(extractElement);
				extractList.add(extract);
			}
			return extractList;
		}
		return null;
	}

	private static boolean isDataType(String value) {
		switch (value) {
		case "json":
		case "html/xml":
			return true;
		default:
			return false;
		}
	}

	/**
	 * 判断是否为整数，忽略是否为负数
	 * 
	 * @return
	 */
	private static boolean checkDigits(String numberString) {
		if (numberString.startsWith("-")) {
			numberString = numberString.substring(1);
		}
		return NumberUtils.isDigits(numberString);
	}
}
