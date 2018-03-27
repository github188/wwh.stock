package com.skoo.stock.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.skoo.common.SystemConfig;
import com.skoo.commons.StringUtils;
import com.skoo.stock.common.Constant;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class HtmlUnitUtil {
    private static WebClient webClient;

    public static void init(Integer timeout) {
        webClient = new WebClient(BrowserVersion.getDefault());
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        setTimeout(timeout);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);
    }

    private static void setTimeout(Integer timeout) {
        webClient.getOptions().setTimeout(timeout * 1000);
        webClient.setJavaScriptTimeout(timeout * 1000);
        //webClient.setJavaScriptTimeout(timeout / 5 * 1000);
        webClient.waitForBackgroundJavaScript(timeout * 1000);
    }

    public static HtmlPage collect(String url) {
        HtmlPage page;
        try {
            if (webClient == null) init(60);
            page = webClient.getPage(url);
        } catch (Exception e) {
            return null;
            //page = collect(url);
            //e.printStackTrace();
        }
        return page;
    }

    public static HtmlPage anchorClick(HtmlPage nextPage, String text) {
        HtmlPage page;
        try {
            HtmlAnchor anchor = nextPage.getAnchorByText(text);
            page = anchor.click();
        } catch (Exception e) {
            e.printStackTrace();
            page = null;
        }
        return page;
    }

    public static HtmlAnchor getAnchor(HtmlPage page, String text) {
        HtmlAnchor anchor;
        try {
            anchor = page.getAnchorByText(text);
        } catch (Exception e) {
            anchor = null;
            e.printStackTrace();
        }
        return anchor;
    }

    public static HtmlPage anchorClick(HtmlAnchor anchor) {
        HtmlPage page;
        try {
            page = anchor.click();
        } catch (Exception e) {
            page = null;
            e.printStackTrace();
        }
        return page;
    }

    public static HtmlPage getHtmlPage(String name) {
        String url = SystemConfig.INSTANCE.getValue(name);
        HtmlPage page = collect(url);

        return page;
    }

    public static HtmlPage getHrefPage(String addr, String arg) {
        HtmlPage page = HtmlUnitUtil.collect(addr);
        Document doc = Jsoup.parse(page.asXml());
        String href = doc.select(arg).select("a[href]").attr("abs:href");
        HtmlPage nextPage = HtmlUnitUtil.collect(href);

        return nextPage;
    }

    public static HtmlPage getHrefPage(HtmlPage page, String arg) {
        Document doc;

        doc = Jsoup.parse(page.asXml());
        String href = doc.select(arg).select("a[href]").attr("abs:href");
        HtmlPage nextPage = HtmlUnitUtil.collect(href);

        return nextPage;
    }

    public static LinkedHashMap<String, String[]> getEastList(String[] arg) {
        String href = Constant.EASTADDR + arg[0];
        Document doc;
        if (arg[0].indexOf("html") < 0) {
            HtmlPage page = HtmlUnitUtil.collect(Constant.EASTADDR);
            doc = Jsoup.parse(page.asXml());
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
    }

    public static LinkedHashMap<String, String[]> getHrefList(String[] arg) {
        Document doc;

        HtmlPage nextPage = HtmlUnitUtil.collect(arg[0]);
        if (StringUtils.isNotEmpty(arg[1])) {
            doc = Jsoup.parse(nextPage.asXml());
            String href = arg[1];
            if (href.indexOf("html") < 0) href = doc.select(href).select("a[href]").attr("abs:href");
            nextPage = HtmlUnitUtil.collect(href);
        }
        LinkedHashMap<String, String[]> map = new LinkedHashMap<>();

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
    }

    public static LinkedHashMap<String, String[]> getDataList(String[] arg) {
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
    }

    public static LinkedHashMap<String, String[]> getBasicList(String[] arg) {
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

        return map;
    }

    public Document test() {
        init(60);
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
        init(60);
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

    public static String[] getEastValue(String code) {
        String href = getNetAddr(Constant.NETFLG) + StockUtil.getStockCode(code) + ".html";
        Document doc;
        HtmlPage nextPage = HtmlUnitUtil.collect(href);
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        doc = Jsoup.parse(nextPage.asXml());
        JsoupUtil.getDocValue(map, doc, "[class=qphox header-title mb7]");
        JsoupUtil.getDocValue(map, doc, "[class=box-x1 line24]");
        JsoupUtil.getDocValue(map, doc, "#sszj");
        //JsoupUtil.getDocValue(map, doc, "#jdzf");
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
        if (Constant.EASTFLG.equals(netFlag)) {
            name = "http://quote." + Constant.EAST + ".com/";
        } else if (Constant.STARFLG.equals(netFlag)) {
            name = "http://quote." + Constant.STAR + ".com/";
        }

        return name;
    }

    public static void main(String[] args) {
        //HtmlPage page = getHtmlPage("eastAddress");
        HtmlPage page = collect("http://hq.newone.com.cn/");
        //HtmlPage page = collect("http://finance.sina.com.cn/realstock/company/sz000938/nc.shtml");
/*
        page.getElementById("tt6_03").focus();
        ScriptResult sr = page.executeJavaScript("javascript:void(0)");
        page = (HtmlPage) sr.getNewPage();
*/
        Document doc = Jsoup.parse(page.asXml());
        //Elements elements = doc.select("#listview");
        //Elements elements = doc.select("#bklist");
        //LinkedHashMap<String, String[]> map = JsoupUtil.getDocElement(doc,"#listview");
    }
}
