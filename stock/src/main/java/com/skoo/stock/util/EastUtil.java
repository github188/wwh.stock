package com.skoo.stock.util;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.skoo.stock.common.Constant;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.LinkedHashMap;

public class EastUtil {
    /*private static HtmlPage page;

    private static void init(String addr) {
        page = HtmlUnitUtil.getHtmlPage(addr);
    }*/

    public static LinkedHashMap<String, String[]> getEastList(String name) {
        HtmlPage page = HtmlUnitUtil.getHtmlPage(Constant.EASTADDR);
        Document doc = Jsoup.parse(page.asXml());
        String href = doc.select(name).select("a[href]").attr("abs:href");
        HtmlPage nextPage = HtmlUnitUtil.collect(href);
        doc = Jsoup.parse(nextPage.asXml());
        LinkedHashMap<String, String[]> map = new LinkedHashMap<>();
        JsoupUtil.getDocElement(map, doc, "#bklist");
        return map;
    }

    public static void main(String args[]) {
        EastUtil suning = new EastUtil();
        String url = "http://vip.stock.finance.sina.com.cn/moneyflow/";
        LinkedHashMap<String, String[]> map = suning.getEastList("#gnmore");
        System.out.println(map);
    }
}
