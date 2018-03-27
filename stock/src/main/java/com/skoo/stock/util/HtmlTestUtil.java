package com.skoo.stock.util;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.List;

public class HtmlTestUtil {

    //private static String TARGET_URL = "http://money.finance.sina.com.cn/mkt/frames/sl_bk.html";
    private static String TARGET_URL = "http://hq.newone.com.cn/";
    //private static String TARGET_URL = "http://vip.stock.finance.sina.com.cn/mkt/#gn_zncd";
    //http://finance.sina.com.cn/data/index.html#stock-schq-hsgs-gnbk/gn_zndw
    public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        //模拟一个浏览器
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //设置webClient的相关参数
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setTimeout(35000);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        //模拟浏览器打开一个目标网址
        HtmlPage rootPage= webClient.getPage(TARGET_URL);
        //HtmlAnchor anchor = rootPage.getAnchorByText("板块汇总行情");
        //HtmlSubmitInput button = form.getInputByValue("板块汇总行情");
        //HtmlPage nextPage = anchor.click();
        ScriptResult sr = rootPage.executeJavaScript("setBoard('concept')");
        HtmlPage page1 = (HtmlPage) sr.getNewPage();
        HtmlAnchor anchor = rootPage.getAnchorByText("次新股");
        HtmlPage nextPage = anchor.click();
        //ScriptResult sr1 = page1.executeJavaScript("http://vip.stock.finance.sina.com.cn/mkt/#gn_zncd");
        //HtmlPage page2 = (HtmlPage) sr1.getNewPage();
        ScriptResult sr1 = nextPage.executeJavaScript("javascript:list_page_show_list('list_page_text2')");
        HtmlPage page2 = (HtmlPage) sr1.getNewPage();
        System.out.println(page1.getUrl().toString());
        String pageContent = rootPage.getWebResponse().getContentAsString();
        //执行按钮出发的js事件
        //ScriptResult sr = rootPage.executeJavaScript("javascript:HistoryCtrl.hashJump('blocktol_conc')");

        //跳转到第二个页面，选择国家
        HtmlPage countrySelect = (HtmlPage) sr.getNewPage();
//获得包含全部国家信息的选择框页面
        HtmlPage framePage=(HtmlPage)countrySelect.getFrameByName("frmTree1").getEnclosedPage();
//获得selectAll按钮，触发js事件
                framePage.executeJavaScript("javascript:TransferListAll(‘countrylst’,'countrylstselected’,'no’);SetSelectedCount(‘countrylstselected’,'tdcount’);");
//获取Next按钮，触发js事件
        ScriptResult electricityScriptResult = framePage.executeJavaScript("javascript:wrapperSetCube(‘/ddp’)");

        System.out.println("正在跳转…");
//跳转到下一个页面electricitySelect
        HtmlPage electricitySelect = (HtmlPage) electricityScriptResult.getNewPage();
//获得electricity选择的iframe
        HtmlPage electricityFrame = (HtmlPage) electricitySelect.getFrameByName("frmTree1").getEnclosedPage();
//获得选择框
                HtmlSelect seriesSelect = (HtmlSelect) electricityFrame.getElementById("countrylst");
//获得所有的选择框内容
        List optionList = seriesSelect.getOptions();
//将指定的选项选中
        //optionList.get(1).setSelected(true);
//模拟点击select按钮
        electricityFrame.executeJavaScript("javascript:TransferList(‘countrylst’,'countrylstselected’,'no’);SetSelectedCount(‘countrylstselected’,'tdcount’);");
//获取选中后，下面的选择框
        HtmlSelect electricitySelected = (HtmlSelect) electricityFrame.getElementById("countrylstselected");
        List list = electricitySelected.getOptions();
//模拟点击Next按钮，跳转到选择时间的页面
        ScriptResult timeScriptResult = electricityFrame.executeJavaScript("javascript:wrapperSetCube(‘/ddp’)");

        System.out.println("正在跳转…");
        HtmlPage timeSelectPage = (HtmlPage) timeScriptResult.getNewPage();
//获取选中时间的选择框
        timeSelectPage = (HtmlPage) timeSelectPage.getFrameByName("frmTree1").getEnclosedPage();
//选中所有的时间
                timeSelectPage.executeJavaScript("javascript:TransferListAll(‘countrylst’,'countrylstselected’,'no’);SetSelectedCount(‘countrylstselected’,'tdcount’);");
//点击Next按钮
        ScriptResult exportResult = timeSelectPage.executeJavaScript("javascript:wrapperSetCube(‘/ddp’)");

        System.out.println("正在跳转…");
//转到export页面
        HtmlPage exportPage = (HtmlPage) exportResult.getNewPage();
//点击页面上的Export按钮,进入下载页面
        ScriptResult downResult = exportPage.executeJavaScript("javascript:exportData(‘/ddp’ ,’EXT_BULK’ ,’WDI_Time=51||WDI_Series=1||WDI_Ctry=244||’ );");

        System.out.println("正在跳转…");
        HtmlPage downLoadPage = (HtmlPage) downResult.getNewPage();
//点击Excel图标，开始下载
        ScriptResult downLoadResult = downLoadPage.executeJavaScript("javascript:exportData(‘/ddp’,'BULKEXCEL’);");
//下载Excel文件
        InputStream is = downLoadResult.getNewPage().getWebResponse().getContentAsStream();

        OutputStream fos = new FileOutputStream("d://test.xls");
        byte[] buffer=new byte[1024*30];
        int len=-1;
        while((len=is.read(buffer))>0){
            fos.write(buffer, 0, len);
        }
        fos.close();
        fos.close();
        System.out.println("Success!");
    }
}
