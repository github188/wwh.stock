package net.ryian.core.action;

import net.ryian.commons.BeanUtils;
import net.ryian.core.utils.WebUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Action基类，系统中所有基类均继承自该类
 *
 * @author cheng.wang
 */
@SuppressWarnings("serial")
public abstract class BaseAction {

    protected Logger logger = Logger.getLogger(this.getClass());

    protected Map<String, String> bindMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String name = (String) enumeration.nextElement();
            String value = request.getParameter(name);
            // 如果是排序列，并且列名带点的，则两边加双引号
            if ("sort".equals(name)) {
                if (value.indexOf(".") > -1) {
                    value = "\"" + value + "\"";
                }
            }
            value = value.replace("\'", "\"");
            logger.debug("NAME:" + name + ", VALUE:" + value);
            map.put(name, value);
        }
        return map;
    }

    protected Map<String, Object> bindMapObj(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String name = (String) enumeration.nextElement();
            String value = request.getParameter(name);
            // 如果是排序列，并且列名带点的，则两边加双引号
            if ("sort".equals(name)) {
                if (value.indexOf(".") > -1) {
                    value = "\"" + value + "\"";
                }
            }
            value = value.replace("\'", "\"");
            logger.debug("NAME:" + name + ", VALUE:" + value);
            map.put(name, value);
        }
        return map;
    }

    /**
     * 直接输出纯Json.
     */
    protected void printJson(HttpServletResponse reponse, String text) {
        doPrint(reponse, text, "application/json;charset=UTF-8");
    }

    /**
     * 直接输出纯XML.
     */
    protected void printXML(HttpServletResponse reponse, String text)
            throws IOException {
        doPrint(reponse, text, "text/xml;charset=UTF-8");
    }

    /**
     * 直接输出纯HTML.
     */
    protected void printHtml(HttpServletResponse reponse, String text)
            throws IOException {
        doPrint(reponse, text, "text/html;charset=UTF-8");
    }

    /**
     * 直接输出纯字符串.
     */
    protected void printText(HttpServletResponse reponse, String text)
            throws IOException {
        doPrint(reponse, text, "text/plain;charset=UTF-8");
    }

    /**
     * 直接输出.
     *
     * @param contentType 内容的类型.html,text,xml的值见后，json为"text/x-json;charset=UTF-8"
     */
    private void doPrint(HttpServletResponse response, String text,
                         String contentType) {

        PrintWriter out = null;
        try {
            logger.debug("输出的字符串: " + text + "");
            response.setContentType(contentType);
            out = response.getWriter();
            out.write(text);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.print("");
                out.close();
            }
        }

    }

    /**
     * @description：根据传入的字段名称typeStr获取其get方法名称
     * @method：getMethodNameByType
     */
    protected String getMethodNameByType(String typeStr) {
        String firstStr = typeStr.substring(0, 1).toUpperCase();
        String methodNameString = "get" + firstStr + typeStr.substring(1);
        return methodNameString;
    }

    /**
     * @param obj Object对象
     * @return Map<String, Object>
     * @method：strutsMapByObj
     * @description：将javaBean(obj)对象的属性及值组装到Map<String, Object>
     */
    protected Map<String, Object> strutsMapByObj(Object obj)
            throws NoSuchFieldException {
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            String nameString = field.getName();
            if (field.getType().isAssignableFrom(String.class))
                map.put(nameString, BeanUtils.forceGetProperty(obj, nameString));
            else
                map.put(nameString,
                        BeanUtils.forceGetProperty(obj,
                                String.valueOf(nameString)));
        }
        return map;
    }

    /**
     * 错误消息外包 </p> 后台往前台打印消息时，添加的外包，以便前台展现技术获取消息. 根据不同的前台展现技术变更。当前支持Ext.
     */
    protected String messageFailureWrap(String message) {
        return WebUtils.getFailureMessage(message);
    }

    /**
     * 成功消息外包 </p> 后台往前台打印消息时，添加的外包，以便前台展现技术获取消息. 根据不同的前台展现技术变更。当前支持Ext.
     */
    protected String messageSuccuseWrap(String message) {
        return WebUtils.getSuccuseMessage(message);
    }

    /**
     * 成功消息外包 </p> 后台往前台打印消息时，添加的外包，以便前台展现技术获取消息. 根据不同的前台展现技术变更。当前支持Ext.
     */
    protected String messageSuccuseWrap() {
        return WebUtils.getSuccuseMessage(null);
    }

}
