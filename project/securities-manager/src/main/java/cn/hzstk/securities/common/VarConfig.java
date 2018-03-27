package cn.hzstk.securities.common;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import org.apache.commons.logging.LogFactory;

import java.util.logging.Level;

public class VarConfig {
    private static boolean investOpen = false;
    private static boolean rxFlag = false;
    private static WebClient webClient;

    static {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
                "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        //Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        /*Jdk14Logger logger = (Jdk14Logger) LogFactory.getLog("com.gargoylesoftware.htmlunit");
        logger.getLogger().setLevel(Level.OFF);*/
        //protected static Logger log = Logger.getLogger(Constant.LOG_ERROR);
        webClient = new WebClient(BrowserVersion.CHROME);//设置浏览器的User-Agent
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.getOptions().setCssEnabled(false);//是否启用CSS
        webClient.getOptions().setActiveXNative(false);
        webClient.setJavaScriptTimeout(10 * Constant.SECOND);//设置JS执行的超时时间
        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常
        webClient.getOptions().setTimeout(10 * Constant.SECOND);//设置“浏览器”的请求超时时间
        webClient.waitForBackgroundJavaScript(30 * Constant.SECOND);//设置JS后台等待执行时间
        //webClient.getOptions().setRedirectEnabled(true);
        //webClient.waitForBackgroundJavaScript(10 * Constant.SECOND);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//设置支持AJAX

        /*webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setTimeout(60 * Constant.SECOND);
        webClient.setJavaScriptTimeout(60 * Constant.SECOND);
        webClient.waitForBackgroundJavaScript(60 * Constant.SECOND);*/
        //webClient.setJavaScriptEngine(new MyJavaScriptEngine(webClient));
    }

    public static boolean isInvestOpen() {
        return investOpen;
    }

    public static void setInvestOpen(boolean investOpen) {
        VarConfig.investOpen = investOpen;
    }

    public static boolean isRxFlag() {
        return rxFlag;
    }

    public static void setRxFlag(boolean rxFlag) {
        VarConfig.rxFlag = rxFlag;
    }

    public static WebClient getWebClient() {
        return webClient;
    }
}
