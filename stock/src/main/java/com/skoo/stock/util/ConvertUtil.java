package com.skoo.stock.util;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * JavaBean,Map,Json互转器
 * 1:将JavaBean 转换成Map、JSONObject
 * 2:将JSONObject 转换成Map
 *
 * @author gomiten@163.com
 */
public class ConvertUtil {

    private static final Log logger = LogFactory.getLog(ConvertUtil.class);

    /**
     * 将javaBean转换成Map
     *
     * @param javaBean javaBean
     * @return Map对象
     */
    public static Map<String, String> toMap(Object javaBean) {
        Map<String, String> result = new HashMap<>();
        Method[] methods = javaBean.getClass().getDeclaredMethods();

        for (Method method : methods) {
            try {
                if (method.getName().startsWith("get")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);

                    Object value = method.invoke(javaBean, (Object[]) null);
                    result.put(field, null == value ? "" : value.toString());
                }
            } catch (Exception ex) {
                logger.error("ConvertUtil.toMap", ex);
            }
        }

        return result;
    }

    /**
     * 将json对象转换成Map
     *
     * @param jsonObject json对象
     * @return Map对象
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> toMap(JSONObject jsonObject) {
        Map<String, String> result = new HashMap<>();
        Iterator<String> iterator = jsonObject.keys();
        String key;
        String value;
        while (iterator.hasNext()) {
            key = iterator.next();
            value = jsonObject.getString(key);
            result.put(key, value);
        }
        return result;
    }

    /**
     * 将javaBean转换成JSONObject
     *
     * @param bean javaBean
     * @return json对象
     */
    public static JSONObject toJSON(Object bean) {
        return JSONObject.fromObject(toMap(bean));
    }

    /**
     * 将map转换成Javabean
     *
     * @param javabean javaBean
     * @param data     map数据
     */
    public static Object toJavaBean(Object javabean, Map<String, String> data) {
        Method[] methods = javabean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            try {
                if (method.getName().startsWith("set")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("set") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);
                    method.invoke(javabean, new Object[]{data.get(field)});
                }
            } catch (Exception ex) {
                logger.error("ConvertUtil.toJavaBean", ex);
            }
        }

        return javabean;
    }

    /**
     * 将javaBean转换成JSONObject
     *
     * @param javabean javaBean
     * @param data     json对象
     * @throws ParseException json解析异常
     */
    public static void toJavaBean(Object javabean, String data) throws ParseException {
        JSONObject jsonObject = JSONObject.fromObject(data);
        Map<String, String> datas = toMap(jsonObject);
        toJavaBean(javabean, datas);
    }
}
