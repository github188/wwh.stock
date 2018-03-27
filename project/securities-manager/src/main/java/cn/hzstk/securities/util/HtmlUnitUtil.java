package cn.hzstk.securities.util;

import cn.hzstk.securities.common.Constant;
import cn.hzstk.securities.common.VarConfig;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import net.ryian.commons.StringUtils;
import net.ryian.core.SystemConfig;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HtmlUnitUtil {
    private static Logger logger = Logger.getLogger(Constant.LOG_ERROR);
    //private final static WebClient webClient = VarConfig.getWebClient();

    public static HtmlPage getPage(String url, WebClient webClient) {
        HtmlPage page;
        try {
            if (webClient == null) webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
            webClient.getOptions().setCssEnabled(false);//是否启用CSS
            webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常
            webClient.getOptions().setTimeout(10 * Constant.SECOND);//设置“浏览器”的请求超时时间

            page = webClient.getPage(url);
        } catch (Exception e) {
            return null;
            //page = collect(url);
            //e.printStackTrace();
        }

        return page;
    }

    public static String collect(String url, String text) {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);//设置浏览器的User-Agent
        String xml = "";
        try {
            HtmlPage page = getPage(url, webClient);
            if (StringUtils.isNotEmpty(text)) {
                HtmlAnchor anchor = page.getAnchorByText(text);
                if (anchor == null || anchor.asXml().indexOf("nolink") >= 0) {
                    page = null;
                } else {
                    page = anchor.click();
                    Thread.sleep(3 * Constant.SECOND);
                }
            }
            if (page != null) xml = page.asXml();
        } catch (Exception e) {
            return "";
        } finally {
            webClient.close();
        }

        return xml;
    }

    public static String collect(String url) {
        return collect(url, null);
    }
    public static HtmlPage anchorClick(HtmlPage nextPage, String text) {
        HtmlPage page;
        try {
            HtmlAnchor anchor = nextPage.getAnchorByText(text);
            if (anchor == null || anchor.asXml().indexOf("nolink") >= 0) {
                page = null;
            } else {
                page = anchor.click();
                Thread.sleep(3 * Constant.SECOND);
            }
        } catch (Exception e) {
            page = null;
            logger.info(e.getMessage());
        }
        return page;
    }

    public static HtmlAnchor getAnchor(HtmlPage page, String text) {
        HtmlAnchor anchor;
        try {
            anchor = page.getAnchorByText(text);

        } catch (Exception e) {
            anchor = null;
            logger.info(e.getMessage());
        }
        return anchor;
    }

    public static HtmlPage anchorClick(HtmlAnchor anchor) {
        HtmlPage page;
        try {
            page = anchor.click();
            Thread.sleep(3 * Constant.SECOND);
        } catch (Exception e) {
            page = null;
            e.printStackTrace();
        }
        return page;
    }

    /*public static HtmlPage getHtmlPage(String name) {
        String url = SystemConfig.INSTANCE.getValue(name);
        HtmlPage page = collect(url);

        return page;
    }*/

    public static String getDocText(String[] arg) {
        String text = StringUtils.EMPTY;
        HtmlPage[] page = new HtmlPage[arg.length - 1];
        Document doc = Jsoup.parse(HtmlUnitUtil.collect(arg[0]));

        Elements el;
        for (int i = 1; i < arg.length - 1; i++) {
            el = doc.select(arg[i]);
            page[i] = HtmlUnitUtil.anchorClick(page[i - 1], el.get(0).text());
            if (page[i] == null) continue;
            doc = Jsoup.parse(page[i].asXml());

            text += getDocText (doc, arg[arg.length - 1]);
        }

        text += getDocText(doc, arg[arg.length - 1]);
        return text;
    }

    private static String getDocText(Document doc, String selId) {
        String text = StringUtils.EMPTY;
        Elements elements = doc.select(selId);
        for (Element element:elements) {
            text += element.text();
        }

        return text;
    }

    /*public static HtmlPage getHrefPage(HtmlPage page, String arg) {
        Document doc;

        doc = Jsoup.parse(page.asXml());
        String href = doc.select(arg).select("a[href]").attr("abs:href");
        HtmlPage nextPage = HtmlUnitUtil.collect(href);

        return nextPage;
    }*/

    /*public static LinkedHashMap<String, String[]> getEastList(String[] arg) {
        String href = Constant.EAST_ADDRESS + arg[0];
        Document doc;
        if (arg[0].indexOf("html") < 0) {
            doc = Jsoup.parse(HtmlUnitUtil.collect(Constant.EAST_ADDRESS));
            href = doc.select(arg[0]).select("a[href]").attr("abs:href");
        }
        HtmlPage nextPage = HtmlUnitUtil.collect(href);
        LinkedHashMap<String, String[]> map = new LinkedHashMap<>();
        if (arg[0].indexOf("html") > 0) {
            for (int i = 0; i < 4; i++) {
                if (i > 0) {
                    nextPage = HtmlUnitUtil.collect(href);
                    HtmlSelect changeSelected = (HtmlSelect) nextPage.getElementById("change");
                    changeSelected.getOption(i - 1).setSelected(true);
                    ScriptResult sr = nextPage.executeJavaScript("javascript:onSelectChange()");
                    nextPage = (HtmlPage) sr.getNewPage();
                }
                while (nextPage != null) {
                    doc = Jsoup.parse(nextPage.asXml());
                    JsoupUtil.getDocElemAdd(map, doc, arg[1], i);
                    nextPage = anchorClick(nextPage, "下一页");
                }
            }
        } else {
            doc = Jsoup.parse(nextPage.asXml());
            JsoupUtil.getDocElement(map, doc, arg[1]);
        }
        return map;
        *//*for (int i = 0; i < 20; i++) {
            if (columns.size()>0) {
                logger.info("等待ajax执行完毕");
                break;
            }
            synchronized (resultPage) {
                page.wait(500);
            }
        }*//*
    }*/

    /*public static LinkedHashMap<String, String[]> getBasicList(String[] arg) {
        Document doc;

        HtmlPage nextPage = HtmlUnitUtil.collect(arg[0]);
        ScriptResult sr = nextPage.executeJavaScript(arg[1]);
        nextPage = (HtmlPage) sr.getNewPage();
        LinkedHashMap<String, String[]> map = new LinkedHashMap<>();

        while (nextPage != null) {
            doc = Jsoup.parse(nextPage.asXml());
            JsoupUtil.getDocElement(map, doc, arg[2]);
            nextPage = anchorClick(nextPage, "下一页");
        }

        //webClient.close();
        return map;
    }*/

    public static LinkedHashMap<String, String[]> getSiteList(String[] arg) {
        return getSiteList(arg, null);
    }

    public static LinkedHashMap<String, String[]> getSiteList(String[] arg, String script) {
        LinkedHashMap<String, String[]> map = new LinkedHashMap<>();
        HtmlPage nextPage = getPage(arg[0], null);
        if (StringUtils.isNotEmpty(script)) {
            ScriptResult sr = nextPage.executeJavaScript(script);
            HtmlPage page = (HtmlPage) sr.getNewPage();
            if (page.equals(nextPage)) return map;
            nextPage = anchorClick(nextPage, script);
        }

        Document doc = null;
        if (nextPage != null) doc = Jsoup.parse(nextPage.asXml());
        while (doc != null) {
            JsoupUtil.getDocElement(map, doc, arg[1]);
            if (map.size() > 0 && arg.length > 2) {
                String[] val = map.get(String.valueOf(map.size() - 1));
                int no = Integer.valueOf(arg[2]);
                if (val.length > no && val[no].compareTo(arg[3]) < 0) {
                    break;
                }
            }
            if (anchorClick(nextPage, "下一页") == null) {
                doc = null;
            } else {
                doc = Jsoup.parse(nextPage.asXml());
            }
        }

        return map;
    }

    /*public static LinkedHashMap<String, String[]> getDataList(String[] arg) {
        Document doc;
        LinkedHashMap<String, String[]> map = new LinkedHashMap<>();

        HtmlPage page = HtmlUnitUtil.collect(arg[0]);
        HtmlPage nextPage = HtmlUnitUtil.anchorClick(page, arg[1]);
        while (nextPage != null) {
            doc = Jsoup.parse(nextPage.asXml());
            JsoupUtil.getDocElement(map, doc, arg[2]);
            HtmlAnchor anchor = getAnchor(nextPage, "下一页");
            if (anchor == null || anchor.asXml().indexOf("nolink") >= 0) {
                nextPage = null;
            } else {
                nextPage = anchorClick(anchor);
            }
        }

        return map;
    }*/

    /*public Document test() {
        String url = System.getProperty("eastAddress");
        HtmlPage page = collect(url);
        ScriptResult sr = page.executeJavaScript("setBoard('concept')");
        HtmlPage page1 = (HtmlPage) sr.getNewPage();
        HtmlPage nextPage = anchorClick(page1, "次新股");
        //ScriptResult sr1 = page1.executeJavaScript("http://vip.stock.finance.sina.com.cn/mkt/#gn_zncd");
        //HtmlPage page2 = (HtmlPage) sr1.getNewPage();
        ScriptResult sr1 = nextPage.executeJavaScript("javascript:list_page_show_list('list_page_text2')");
        HtmlPage page2 = (HtmlPage) sr1.getNewPage();
        Document doc = Jsoup.parse(page2.asXml());
        return doc;
    }

    public Document getConceptInfo() {
        String url = System.getProperty("eastAddress");
        HtmlPage page = collect(url);
        ScriptResult sr = page.executeJavaScript("setBoard('concept')");
        HtmlPage page1 = (HtmlPage) sr.getNewPage();
        HtmlPage nextPage = anchorClick(page1, "次新股");
        //ScriptResult sr1 = page1.executeJavaScript("http://vip.stock.finance.sina.com.cn/mkt/#gn_zncd");
        //HtmlPage page2 = (HtmlPage) sr1.getNewPage();
        ScriptResult sr1 = nextPage.executeJavaScript("javascript:list_page_show_list('list_page_text2')");
        HtmlPage page2 = (HtmlPage) sr1.getNewPage();
        Document doc = Jsoup.parse(page2.asXml());
        return doc;
    }*/

    public static String[] getEastValue(String code, String[] group) {
        String href = getNetAddr(Constant.NETFLG) + StkCommUtil.getStockCode(code) + ".html";

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        Document doc = Jsoup.parse(collect(href));
        for (String aGroup : group) {
            JsoupUtil.getDocValue(map, doc, aGroup);
        }
        String[] val =  new String[map.size() + 1];
        val[0] = code;
        int i = 1;
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            val[i] = (String)entry.getValue();
            i++;
        }

        return val;
    }

    private static String getNetAddr(String netFlag){
        String name = "";
        if (Constant.EAST_NET.equals(netFlag)) {
            name = "http://quote." + Constant.EAST + ".com/";
        } else if (Constant.STAR_NET.equals(netFlag)) {
            name = "http://quote." + Constant.STAR + ".com/";
        }

        return name;
    }

    public static void main(String[] args) {
        //HtmlPage page = getHtmlPage("eastAddress");
        //HtmlPage page = collect("http://hq.newone.com.cn/");
        //HtmlPage page = collect("http://finance.sina.com.cn/realstock/company/sz000938/nc.shtml");

        /*page.getElementById("tt6_03").focus();
        ScriptResult sr = page.executeJavaScript("javascript:void(0)");
        page = (HtmlPage) sr.getNewPage();

        Document doc = Jsoup.parse(page.asXml());*/
        //Elements elements = doc.select("#listview");
        //Elements elements = doc.select("#bklist");
        //LinkedHashMap<String, String[]> map = JsoupUtil.getDocElement(doc,"#listview");
    }
}
