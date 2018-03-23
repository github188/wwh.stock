package com.zjhcsoft.struc.parse.wrapper;

import com.zjhcsoft.struc.conf.Configuration;
import com.zjhcsoft.struc.fetch.CommonFetcher;
import com.zjhcsoft.struc.fetch.wrapper.CommonFetcherWrapper;
import com.zjhcsoft.struc.fetch.wrapper.HtmlUnitCommonFetcherWrapper;
import com.zjhcsoft.struc.fetch.wrapper.HttpCommonFetcherWrapper;
import com.zjhcsoft.struc.mapper.CommonMapper;
import com.zjhcsoft.struc.parse.CommonParser;
import com.zjhcsoft.struc.util.SpringBeanFactory;
import com.zjhcsoft.struc.util.StringUtil;
import com.zjhcsoft.struc.util.URLUtil;
import com.zjhcsoft.struc.util.datasource.*;
import com.zjhcsoft.struc.util.datasource.formatter.*;

import org.cyberneko.html.parsers.DOMParser;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.DOMReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Formatter;

/**
 * Created by allenwc on 14/11/18.
 */
public class HtmlCommonParserWrapper extends CommonParserWrapper{

    private static final Logger LOG = LoggerFactory.getLogger(CommonParser.class);

    public HtmlCommonParserWrapper(CommonParser commonParser) {
        super(commonParser);
    }

    public Map<Object, String> parse(Object object, String url) {
        Document document = parseDocument(object.toString(), DataSourceHandler.urlPattern.getCharset());
        process(document);
        return new HashMap<>();
    }

    public static Document parseDocument(String content, String charset) {
        DOMParser parser = new DOMParser();

        // 使用nekohtml对源码进行清理、补全等处理
        try {
            parser.setFeature("http://xml.org/sax/features/namespaces", false);
            parser.setProperty("http://cyberneko.org/html/properties/default-encoding", charset);
            parser.parse(new InputSource(new ByteArrayInputStream(content.getBytes(charset))));
        } catch (SAXException | IOException e) {
            LOG.error(e.getMessage(), e);
            e.printStackTrace();
        }
        Document document = new DOMReader().read(parser.getDocument());

        return document;
    }

    private void process(Document document) {
        List<Mapping> mappingList = DataSourceHandler.urlPattern.getMappingList();
        TreeNode valueTreeNode = new TreeNode();
        processURLPatternMapping(mappingList, document, valueTreeNode, DataSourceHandler.urlPattern.getUrl());

        Outlink outlink = DataSourceHandler.urlPattern.getOutlink();

        List<String> outLinkList = new ArrayList<>();

        processOutlink(outlink, document, valueTreeNode, DataSourceHandler.urlPattern.getUrl(), outLinkList);

        System.err.println("底层外链数：" + outLinkList.size());
        for (String bottomURL : outLinkList) {
            String content = null;
        	if(outlink.getFetchtype().equals("html-unit")){
        		   content = HtmlUnitCommonFetcherWrapper.Htmlunitexecute(bottomURL);
        		   content = HtmlUnitCommonFetcherWrapper.Htmlunitexecute(bottomURL);
        	}else{
        	
        		content = HttpCommonFetcherWrapper.httpExecute(bottomURL, DataSourceHandler.urlPattern.getCharset(),
                        DataSourceHandler.urlPattern.getHttpmethod());
        	}
            Document bottomDocument = parseDocument(content, DataSourceHandler.urlPattern.getCharset());
            Map<String, String> mappingValueMap = new HashMap<>();
            getLeafTreeNode(valueTreeNode, bottomURL, mappingValueMap);
            try {
                processTables(bottomDocument, mappingValueMap);
            } catch (ScriptException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    private void processOutlink(Outlink outlink, Document document, TreeNode parentValueTreeNode, String url,
                                List<String> outLinkList) {
        if (outlink != null) {
            List<String> processOutlinkList = new ArrayList<>();
            checkXpath(document, outlink.getXpath());
            Element element = (Element) document.selectSingleNode(outlink.getXpath());
	if (outlink.getNumberpath() != null) {
				Node node = document.selectSingleNode(outlink.getNumberpath());
				if (node == null) {
					outlink.setNumber(0);
				} else {
					List<Node> nodesList = document.selectNodes(outlink
							.getNumberpath());
					if (nodesList.size() > 0) {
						outlink.setNumber(nodesList.size());
					}
				}
			}
            List<Extract> extractList = outlink.getExtractList();
            for (Extract extract : extractList) {
                try {
                    if ("range".equals(outlink.getXpathType())) {
                        List<Link> linkList = URLUtil.extractOutlink(element, url, extract.getExtractAttr());
                        int i = 0;
                        for (Link link : linkList) {
                            if (i >= outlink.getNumber()) {
                                break;
                            }
                            if ((extract.getInclude() == null || link.getAnchor().contains(extract.getInclude())
                                    && (extract.getExclude() == null || link.getAnchor().indexOf(extract.getExclude()) < 0))) {
                                processOutlinkList.add(link.getUrl());
                                LOG.debug(link.getUrl());
                                i++;
                            }
                        }
                    } else if ("single".equals(outlink.getXpathType())) {
                        processOutlinkList.add(URLUtil.toAbsURL(url, element.attributeValue(extract.getExtractAttr()))
                                .toString());
                    }
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
            List<Mapping> mappingList = outlink.getMappingList();
            
            
            if(outlink.getTimeparam()!=null){
            		String timeparam = outlink.getTimeparam();
            	  List<String> templist = new ArrayList<>();
            	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            	  Calendar startcal = Calendar.getInstance();
            	  
            	  startcal.setTime(Configuration.starttime);
            	  Calendar endcal = Calendar.getInstance();
            	  endcal.setTime(Configuration.endtime);
            	  String starttime = sdf.format(Configuration.starttime);
            	  String endtime =sdf.format(Configuration.endtime);
            	while(startcal.before(endcal)){
            	for (String processOutlink : processOutlinkList)
            	{
            		templist.add(processOutlink+"&"+timeparam+sdf.format(startcal.getTime()));
            		startcal.add(Calendar.DATE,1);
            	}
            	}
            	
            	processOutlinkList.clear();
            	processOutlinkList.addAll(templist);
            	
            }
            for (String processOutlink : processOutlinkList) {
            	String content = null;
            	if(processOutlink.contains("222.75.161.242:9003")){
            		continue;
            	}
            	if(outlink.getFetchtype().equals("html-unit")){
            		   content = HtmlUnitCommonFetcherWrapper.Htmlunitexecute(processOutlink);
            		   content = HtmlUnitCommonFetcherWrapper.Htmlunitexecute(processOutlink);
            		   
            	}else{
            	
                content = HttpCommonFetcherWrapper.httpExecute(processOutlink, outlink.getCharset(),
                        DataSourceHandler.urlPattern.getHttpmethod());

            	}
            	
                Document newDocument = parseDocument(content, outlink.getCharset());
                processURLPatternMapping(mappingList, newDocument, parentValueTreeNode, processOutlink);
                if (outlink.getOutlink() == null) {
                    outLinkList.add(processOutlink);
                } else {
                    // 往下继续寻找外链
                    processOutlink(outlink.getOutlink(), newDocument, parentValueTreeNode.getChild(processOutlink),
                            processOutlink, outLinkList);
                }
            }
        }
    }

    private void processTables(Document document, Map<String, String> mappingValueMap) throws ScriptException {
        Tables tables = DataSourceHandler.tables;
        List<Table> tableList = tables.getTableList();

        for (Table table : tableList) {
            // key为selectSQL，value为insertSQL列表
	if(table.getCheckpath()!=null){
			Node node = document.selectSingleNode(table.getCheckpath());
			if (node == null) {
				continue;
			}
		}
            Map<String, List<String>> insertMap = new LinkedHashMap<String, List<String>>();
            // 储存所有字段
            Map<String, String> mappingMap = new LinkedHashMap<>();
            // 存储插入时check用的字段
            Map<String, String> insertConditionMap = new LinkedHashMap<>();
            // 插入前判断『弃用』
            boolean insertJudge = table.isInsertJudge();
            // 是否需要检查更新时间
            boolean updateJudge = table.isUpdateJudge();

            String tableName = table.getTableName();
            String schema = table.getSchema();
            String tableFullName = schema + "." + tableName;
            String updateJudgeField = null;

            List<Mapping> mappingList = table.getMappingList();
            for (Mapping mapping : mappingList) {
                String value = processMappingValue(document, mapping, mappingValueMap);

                mappingMap.put(mapping.getDbColumn(), value);

                if (mapping.isUpdateJudge()) {
                    updateJudgeField = mapping.getDbColumn();
                }
                if (mapping.isInsertJudge()) {
                    insertConditionMap.put(mapping.getDbColumn(), value);
                }
            }

            // 是否存在repeatMapping
            RepeatMapping repeatMapping = table.getRepeatMapping();
            if (repeatMapping != null) {
                List<Mapping> repeatMappingList = repeatMapping.getMappingList();
                RepeatMapping repeatMapping2 = repeatMapping.getRepeatMapping();
                String xpath = repeatMapping.getLengthXpath();
                checkXpath(document, xpath);
                List<Node> nodeList = document.selectNodes(xpath);
                int startNum = repeatMapping.getStartNum();
                int endNum = repeatMapping.getEndNum();
                Integer[] ignoreNumArray = repeatMapping.getIgnoreNumArray();

                // 这段方法中可以提取共通方法
                loop: for (int i = startNum; i < nodeList.size() - endNum + 1; i++) {
                    if (ignoreNumArray != null) {
                        for (Integer ignoreNum : ignoreNumArray) {
                            if (ignoreNum == i) {
                                continue loop;
                            }
                        }
                    }
                    Map<String, String> repeatMappingMap = new LinkedHashMap<>();
                    Map<String, String> repeatInsertConditionMap = new LinkedHashMap<>();
                    repeatMappingMap.putAll(mappingMap);
                    repeatInsertConditionMap.putAll(insertConditionMap);
                    for (Mapping mapping : repeatMappingList) {
                        String tempXpath = mapping.getXpath();
                        mapping.setXpath(processTarget(mapping, String.valueOf(i), null, tempXpath));
                        String value = processMappingValue(document, mapping, mappingValueMap);
                        repeatMappingMap.put(mapping.getDbColumn(), value);
                        mapping.setXpath(tempXpath);
                        if (mapping.isInsertJudge()) {
                            repeatInsertConditionMap.put(mapping.getDbColumn(), value);
                        }
                    }

                    if (repeatMapping2 != null) {
                        String xpath2 = repeatMapping2.getLengthXpath();
                        checkXpath(document, xpath2);
                        List<Node> nodeList2 = document.selectNodes(xpath2);
                        int startNum2 = repeatMapping2.getStartNum();
                        int endNum2 = repeatMapping2.getEndNum();
                        List<Mapping> repeatMappingList2 = repeatMapping2.getMappingList();
                        Integer[] ignoreNumArray2 = repeatMapping.getIgnoreNumArray();

                        loop2: for (int j = startNum2; j < nodeList2.size() - endNum2 + 1; j++) {
                            if (ignoreNumArray2 != null) {
                                for (Integer ignoreNum : ignoreNumArray2) {
                                    if (ignoreNum == j) {
                                        continue loop2;
                                    }
                                }
                            }
                            Map<String, String> repeatMappingMap2 = new LinkedHashMap<>();
                            Map<String, String> repeatInsertConditionMap2 = new LinkedHashMap<>();
                            repeatMappingMap2.putAll(repeatMappingMap);
                            repeatInsertConditionMap2.putAll(repeatInsertConditionMap);
                            for (Mapping mapping : repeatMappingList2) {
                                String tempXpath = mapping.getXpath();
                                mapping.setXpath(processTarget(mapping, String.valueOf(i), String.valueOf(j), tempXpath));
                                String value = processMappingValue(document, mapping, mappingValueMap);
                                repeatMappingMap2.put(mapping.getDbColumn(), value);
                                mapping.setXpath(tempXpath);
                                if (mapping.isInsertJudge()) {
                                    repeatInsertConditionMap2.put(mapping.getDbColumn(), value);
                                }
                            }
                            List<String> insertSQLList = null;
                            if (repeatInsertConditionMap2.size() > repeatInsertConditionMap.size()) {
                                String selectSQL = generateSelectSQL(repeatInsertConditionMap2, tableFullName,
                                        updateJudgeField);
                                insertSQLList = insertMap.get(selectSQL);
                                if (insertSQLList == null) {
                                    insertSQLList = new ArrayList<>();
                                    insertMap.put(selectSQL, insertSQLList);
                                }
                            } else {
                                String selectSQL = generateSelectSQL(repeatInsertConditionMap, tableFullName,
                                        updateJudgeField);
                                insertSQLList = insertMap.get(selectSQL);
                                if (insertSQLList == null) {
                                    insertSQLList = new ArrayList<>();
                                    insertMap.put(selectSQL, insertSQLList);
                                }
                            }
                            String sql = generateInsertSQL(repeatMappingMap2, tableFullName);
                            insertSQLList.add(sql);
                        }
                    } else {
                        List<String> insertSQLList = null;
                        if (repeatInsertConditionMap.size() > insertConditionMap.size()) {
                            String selectSQL = generateSelectSQL(repeatInsertConditionMap, tableFullName,
                                    updateJudgeField);
                            insertSQLList = insertMap.get(selectSQL);
                            if (insertSQLList == null) {
                                insertSQLList = new ArrayList<>();
                                insertMap.put(selectSQL, insertSQLList);
                            }
                        } else {
                            String selectSQL = generateSelectSQL(insertConditionMap, tableFullName, updateJudgeField);
                            insertSQLList = insertMap.get(selectSQL);
                            if (insertSQLList == null) {
                                insertSQLList = new ArrayList<>();
                                insertMap.put(selectSQL, insertSQLList);
                            }
                        }

                        String sql = generateInsertSQL(repeatMappingMap, tableFullName);
                        insertSQLList.add(sql);
                    }
                }
            } else {
                List<String> insertSQLList = new ArrayList<>();
                String insertSQL = generateInsertSQL(mappingMap, tableFullName);
                insertSQLList.add(insertSQL);
                String selectSQL = generateSelectSQL(insertConditionMap, tableFullName, updateJudgeField);
                insertMap.put(selectSQL, insertSQLList);
            }

            CommonMapper commonMapper = (CommonMapper) SpringBeanFactory.getBean("commonMapper");
            for (String selectSQL : insertMap.keySet()) {
                // 1.根据是否存在该时间点的数据来判断是否需要插入
                // SELECT publish_date FROM bdc.water_quality_m_hz WHERE
                // YEAR=2014
                // AND MONTH=9 LIMIT 1
            	String judge = null;
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				if (insertJudge) {
					judge = commonMapper.selectPublish(selectSQL);
				}
                // 1.1如果没有，则插入所有数据
                // INSERT INTO
                // water_quality_m_hz(YEAR,MONTH,ITEM,GB,OUT_WATER,PIPE_WATER,PUBLISH_DATE,GET_TIME)
                // VALUES('2014','9','耐热大肠菌群','GB5749-2006','未检出','未检出','2014-10-17
                // 14:40:39','2014-10-24 10:39:12');
                List<String> insertSQLList = insertMap.get(selectSQL);
               	if (judge == null) {
					for (String sql : insertSQLList) {
						try{
						commonMapper.insert(sql);
						}catch(Exception e){
							//e.printStackTrace();
							continue;							
						}
					}
                } else {// 1.2如果有，则判断更新时间是否一致
                    // 1.2.1如果一致，则退出
                	if (updateJudge
							&& judge.equals(mappingMap.get(updateJudgeField))) {
						return;
					} else {// 1.2.2如果不一致，删除该时间点所有数据并插入
						String deleteSQL = generateDeleteSQL(
								insertConditionMap, tableFullName);
						commonMapper.delete(deleteSQL);
						for (String sql : insertSQLList) {
							commonMapper.insert(sql);
						}
                    }
                }
            }

        }
    }

    private String processTarget(Mapping mapping, String x, String y, String xpath) throws ScriptException {
        if (StringUtil.isEmpty(x)) {
            LOG.error("x不能为空");
            return null;
        }
        String targetX = mapping.getTargetX();
        String targetY = mapping.getTargetY();

        ScriptEngine jse = null;
        if (StringUtil.isEmpty(targetX)) {
            targetX = x;
        } else {
            if (jse == null) {
                jse = new ScriptEngineManager().getEngineByName("JavaScript");
            }
            targetX = targetX.replace("${targetX}", x);
            targetX = jse.eval(targetX).toString().replaceAll("\\..*", "");
        }
        if(targetX!=null&&xpath!=null){
        xpath = xpath.replace("${targetX}", targetX);
        }
        if (!StringUtil.isEmpty(y)) {
            if (StringUtil.isEmpty(targetY)) {
                targetY = y;
            } else {
                if (jse == null) {
                    jse = new ScriptEngineManager().getEngineByName("JavaScript");
                }
                targetY = targetY.replace("${targetY}", y);
                targetY = jse.eval(targetY).toString().replaceAll("\\..*", "");
            }
            if(xpath!=null&&targetY!=null){
            xpath = xpath.replace("${targetY}", targetY);
            }
        }

        return xpath;
    }

    private String processMappingValue(Document document, Mapping mapping, Map<String, String> mappingValueMap) {
        String value = null;
        String refId = mapping.getRefId();
        if (mapping.getValue()!=null) {
            value = mapping.getValue();
        } else if(refId != null){
        	value = mappingValueMap.get(refId);
        }else{
            String xpath = mapping.getXpath();
            checkXpath(document, xpath);
            Node node = document.selectSingleNode(xpath);
            value = node.getStringValue().trim();
        }
        if (value == null) {
            LOG.error("mapping:value为空");
            System.exit(0);
        }

        // startString
        String startString = mapping.getStartString();
        if (startString != null) {
            int startIndex = value.indexOf(startString);
            if (startIndex < 0) {
                LOG.error(startString + " 找不到startString");
                System.exit(-1);
            }
            value = value.substring(startIndex + startString.length());
        }

        // endString
        String endString = mapping.getEndString();
        if (endString != null) {
            int endIndex = value.indexOf(endString);
            if (endIndex < 0) {
                LOG.error(endString + " 找不到endString");
                System.exit(-1);
            }
            value = value.substring(0, endIndex);
        }

        // replace
        String replace = mapping.getReplace();
        if (replace != null) {
            value = value.replaceAll(replace, "");
        }

        String tempValue = value;
        // 反射执行formatter
        String formattingString = mapping.getFormatter();
        if (formattingString != null) {
            try {
            	String preId = mapping.getPreid();
            	String pre = "";
                if (preId != null) {
                	pre = mappingValueMap.get(preId);
                }
                Class formatterClass = Class.forName(formattingString);
                com.zjhcsoft.struc.util.datasource.formatter.Formatter formatter = (com.zjhcsoft.struc.util.datasource.formatter.Formatter) formatterClass.newInstance();
                Method method = formatterClass.getMethod("format", String.class);
                String result = (String) method.invoke(formatter, value);// 调用方法
                if (StringUtil.isEmpty(result)) {
                    value = pre+mapping.getDefaultValue();
                } else {
                    value = pre+result;
                }
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
                e.printStackTrace();
                System.exit(-1);
            }
        }
        if (value == null) {
            LOG.error("格式化异常-" + formattingString + " : " + tempValue);
            System.exit(-1);
        }

        return value;
    }

    private void processURLPatternMapping(List<Mapping> mappingList, Document document, TreeNode parentValueTreeNode,
                                          String url) {
        Map<String, String> valueMap = new HashMap<>();
        if (mappingList != null) {
            for (Mapping mapping : mappingList) {
            	if(!checkXpath1(document, mapping.getXpath())){
            		continue;
            	};
                Node node = document.selectSingleNode(mapping.getXpath());
                String value = node.getStringValue().trim();
                // startString
                String startString = mapping.getStartString();
                if (startString != null) {
                    int startIndex = value.indexOf(startString);
                    if (startIndex < 0) {
                        LOG.error(startString + " 找不到startString");
                        System.exit(-1);
                    }
                    value = value.substring(startIndex + startString.length());
                }

                // endString
                String endString = mapping.getEndString();
                if (endString != null) {
                    int endIndex = value.indexOf(endString);
                    if (endIndex < 0) {
                        LOG.error(endString + " 找不到endString");
                        System.exit(-1);
                    }
                    value = value.substring(0, endIndex);
                }

                // replace
                String replace = mapping.getReplace();
                if (replace != null) {
                    value = value.replaceAll(replace, "");
                }
                valueMap.put(mapping.getId(), value);
            }
        }
        if (parentValueTreeNode.getUrl() != null) {
            TreeNode child = new TreeNode();
            child.setParent(parentValueTreeNode);
            child.setUrl(url);
            child.setValueMap(valueMap);
            parentValueTreeNode.addChild(child);
        } else {
            parentValueTreeNode.setUrl(url);
            parentValueTreeNode.setValueMap(valueMap);
        }
    }

    /**
     *
     * 生成删除SQL
     *
     * @例子:DELETE FROM water_quality_m_hz WHERE YEAR=2014 AND MONTH=8
     */
    private String generateDeleteSQL(Map<String, String> map, String table) {
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append("DELETE FROM ");
        sqlSb.append(table);
        sqlSb.append(" WHERE ");
        for (String key : map.keySet()) {
            sqlSb.append(key);
            sqlSb.append("='");
            sqlSb.append(map.get(key));
            sqlSb.append("' AND ");
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("DeleteSQL:" + sqlSb.substring(0, sqlSb.length() - 5));
        }
        return sqlSb.substring(0, sqlSb.length() - 5);
    }

    private String generateSelectSQL(Map<String, String> map, String table, String updateJudgeField) {
        StringBuilder sqlSb = new StringBuilder();
        StringBuilder headerSb = new StringBuilder();
        headerSb.append("SELECT ");
        headerSb.append(updateJudgeField);
        headerSb.append(" FROM ");
        headerSb.append(table);
        headerSb.append(" WHERE ");
        for (String key : map.keySet()) {
            headerSb.append(key);
            headerSb.append("='");
            headerSb.append(map.get(key));
            headerSb.append("' AND ");
        }
        sqlSb.append(headerSb.substring(0, headerSb.length() - 5));
        sqlSb.append(" LIMIT 1");
        if (LOG.isDebugEnabled()) {
            LOG.debug("SelectSQL:" + sqlSb.toString());
        }
        return sqlSb.toString();
    }

    private String generateInsertSQL(Map<String, String> map, String table) {
        StringBuilder sqlSb = new StringBuilder();
        StringBuilder headerSb = new StringBuilder();
        StringBuilder footerSb = new StringBuilder();
        headerSb.append("INSERT INTO ");
        headerSb.append(table);
        headerSb.append("(");
        footerSb.append("VALUES(");
        map.put("GET_TIME", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        for (String key : map.keySet()) {
            headerSb.append(key);
            headerSb.append(",");
	    if(!map.get(key).equals("null")){
            footerSb.append("'");
            footerSb.append(map.get(key));
            footerSb.append("',");
	    }else{
				footerSb.append(map.get(key));
				footerSb.append(",");
			}
        }
        sqlSb.append(headerSb.substring(0, headerSb.length() - 1));
        sqlSb.append(") ");
        sqlSb.append(footerSb.substring(0, footerSb.length() - 1));
        sqlSb.append(")");
        if (LOG.isDebugEnabled()) {
            LOG.debug("InsertSQL:" + sqlSb.toString());
        }
        return sqlSb.toString();
    }

    private void checkXpath(Document document, String xpath) {
        Node node = document.selectSingleNode(xpath);
        if (node == null) {
            LOG.error("xpath定位失败 " + xpath);
            System.out.print(document.asXML());
            System.exit(-1);
        }
    }
    private boolean checkXpath1(Document document, String xpath) {
        Node node = document.selectSingleNode(xpath);
        if (node == null) {
            LOG.error("xpath定位失败 " + xpath);
          //  System.out.print(document.asXML());
        return false;

        }
        return true;
    }
    private void getLeafTreeNode(TreeNode valueTreeNode, String url, Map<String, String> map) {
        Map<String, TreeNode> children = valueTreeNode.getChildren();
        if (children != null) {
            for (String key : children.keySet()) {
                TreeNode child = children.get(key);
                if (child.getChildren() == null && url.equals(child.getUrl())) {
                    map.putAll(child.getValueMap());
                    TreeNode parentTreeNode = child.getParent();
                    while (parentTreeNode != null) {
                        map.putAll(parentTreeNode.getValueMap());
                        parentTreeNode = parentTreeNode.getParent();
                    }
                } else {
                    getLeafTreeNode(child, url, map);
                }
            }
        }
    }

}
