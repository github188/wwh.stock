package cn.hzstk.securities.util;

import cn.hzstk.securities.common.Constant;
import net.ryian.commons.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;


public class JsoupUtil {
	private static Logger logger = Logger.getLogger(Constant.LOG_ERROR);
	public static Document getDocument(String url){
    	Document doc;
		try {
            doc = Jsoup.connect(url).timeout(5 * Constant.SECOND).get();
            //doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
			return doc;
		} catch (IOException e) {
			e.printStackTrace();
            logger.info(url);
			return null;
		}
    }

    /**
     * 获取页面所有代码页面链接
     * @param doc
     * @param selId
     * @return
     */
    public static List<String[]> _getHtmlHref(Document doc, String selId) {
        List<String[]> href = new ArrayList<>();
        Elements el = doc.select(selId + " a");
        String[] val;
        for (Element e : el) {
            val = new String[2];
            val[0] = e.attr("href");
            if (StringUtils.isEmpty(val[0])) val[0] = e.attr("value");
            val[1] = e.text();
            href.add(val);
        }
        return href;
    }

    /**
     * 获取下一页的链接
     * @param doc
     * @return
     */
    private static String _getNextPageUrl(Document doc) {
        String s = null;
        if (doc.select(".pager .next a").size() > 0) {
            s = doc.select(".pager .next a").first().attr("abs:href");
        }
        return s;
    }

    public static List<String[]> getSiteHref(String[] arg) {
        Document doc = getDocument(arg[0]);
        return _getHtmlHref(doc, arg[1]);
    }

    public static void getDocElement(LinkedHashMap<String, String[]> map, Document doc, String sel){
		String val[];
		Elements elements,es,tdes,tmp;
		Element tmpElem;
		int cnt = map.size();

		elements = doc.select(sel);
		for(Element element:elements) {
			//if (titleFlg) continue;
			if (element.text() != null && !"".equals(element.text())) {
				es = element.select("tbody").select("tr");
				for (Element tdelement : es) {
					tdes = tdelement.select("td");
					if (tdes.size() == 0) tdes = tdelement.select("th");
					val =  new String[tdes.size()*2];
					for(int i = 0; i < tdes.size(); i++){
						tmp = tdes.get(i).getAllElements();
						if (tmp.size() > 1) {
                            tmpElem = tmp.get(0);
							if (!tmpElem.text().contains("～")) tmpElem = tmp.get(1);
						} else {
							tmpElem = tmp.get(tmp.size() - 1);
						}
						val[i] = tmpElem.text();
						if (tmpElem.select("a[href]").size() > 0) val[tdes.size() + i] = tmpElem.select("a[href]").attr("href");
					}
                    if (StringUtils.isNotEmpty(val[1]) && !val[1].startsWith("9") && StringUtils.isNotEmpty(val[0]) && !"序号".equals(val[0].trim())) {
                        map.put(String.valueOf(cnt), val);
                        cnt++;
                    }
				}
			}
		}
	}

	public static void getDocElemAdd(LinkedHashMap<String, String[]> map, Document doc, String sel, int ii){
		String key;
		String val[],valTmp[];
		Elements elements,es,tdes,tmp;
		Element tmpElem;

		elements = doc.select(sel);
		for(Element element:elements) {
			//if (titleFlg) continue;
			if (element.text() != null && !"".equals(element.text())) {
				es = element.select("tr");
				for (Element tdelement : es) {
					tdes = tdelement.select("td");
					if (tdes.size() == 0) tdes = tdelement.select("th");
					val =  new String[tdes.size()*2];
					for(int i = 0; i < tdes.size(); i++){
						tmp = tdes.get(i).getAllElements();
						if (tmp.size() > 3) {
							tmpElem = tmp.get(2);
						} else {
							tmpElem = tmp.get(tmp.size() - 1);
						}
						val[i] = tmpElem.text();
						if (tmpElem.select("a[href]").size() > 0) val[tdes.size() + i] = tmpElem.select("a[href]").attr("href");
					}
					key = val[1];
					if (ii == 0) {
						map.put(key, val);
						continue;
					}
					valTmp = map.get(key);
					if (valTmp == null) {
						map.put(key, val);
					} else if (!Constant.CODETITLE.equals(key)) {
						valTmp[valTmp.length-5+ii]=val[val.length/2-2];
						map.put(key,valTmp);
					}
				}
			}
		}
	}

	public static void getDocValue(LinkedHashMap<String, String> map, Document doc, String sel){
		Elements elements,es,tdElements;
		int cnt = map.size();

		elements = doc.select(sel);
		for(Element element:elements) {
			if (element.text() != null && !"".equals(element.text())) {
				es = element.select("tr");
				if (es.size() == 0) {
					if (element.select("h2").size() > 0) {
						map.put(String.valueOf(cnt), element.select("h2").get(0).text());
						cnt++;
					}
				}
				for (Element tdElement : es) {
                    tdElements = tdElement.select("td");
                    for (Element tde : tdElements) {
                        /*if (tde.getAllElements().size() < 2) {
                            map.put(String.valueOf(cnt), tde.getAllElements().get(0).text());
                        } else {
                            map.put(String.valueOf(cnt), tde.getAllElements().get(1).text());
                        }*/
                        map.put(String.valueOf(cnt), tde.getAllElements().get(0).text());
                        cnt++;
                    }
				}
			}
		}
	}

	public static List<String[]> getTextList(String text){
        List<String[]> list = new ArrayList<>();
        if (StringUtils.isEmpty(text)) return list;

        String[] arrSplit = new String[]{Constant.TEXT_BR, Constant.FULL_STOP, Constant.FULL_COLON, Constant.FULL_COMMA};
        String[] arrValue = new String[]{text},arr;
        List<String> lTmp;
        for (String splt : arrSplit) {
            lTmp = new ArrayList<>();
            for (String tmp : arrValue) {
                arr = tmp.split(splt);
                lTmp.addAll(Arrays.asList(arr));
            }
            arrValue = lTmp.toArray(new String[]{});
        }

        String code,name;
        for (String sTmp : arrValue) {
            if (sTmp.length() < Constant.CODE_LENGTH) continue;
            if (list.size() == 0) sTmp = sTmp.replace("年", "").replace("月", "").replace("日", "");
            code = DigitFormat.getNumbers(sTmp);
            if (list.size() == 0 && code.length() == 8) {
                arr = new String[]{code, StringUtils.EMPTY};
                list.add(arr);
                continue;
            }
            if (code.length() != Constant.CODE_LENGTH) code = StringUtils.EMPTY;
            name = sTmp.trim().replace(code, StringUtils.EMPTY);
            name = name.replace(Constant.LEFT_BRACKET, StringUtils.EMPTY);
            name = name.replace(Constant.RIGHT_BRACKET, StringUtils.EMPTY);
            if (name.length() != Constant.NAME_LENGTH) name = StringUtils.EMPTY;
            if (StringUtils.isNotEmpty(code) || StringUtils.isNotEmpty(name)) {
                arr = new String[]{code, name};
                list.add(arr);
            }
        }

        return list;
	}

	public static void main(String[] args) {
		Document doc = getDocument("http://vip.stock.finance.sina.com.cn/mkt/#gn_zncd");
	}
}
