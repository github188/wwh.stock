package cn.hzskt.sys.utils.json;

import com.zjhcsoft.smartcity.magic.core.utils.json.BaseProcessor;
import com.zjhcsoft.smartcity.magic.core.utils.json.DateJsonValueProcessor;
import com.zjhcsoft.smartcity.magic.core.utils.json.SqlDateProcessor;
import com.zjhcsoft.smartcity.magic.orm.service.support.paging.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.apache.commons.collections.map.ListOrderedMap;

import java.util.*;

public class JsonUtils extends com.zjhcsoft.smartcity.magic.core.utils.JsonUtils {


    /**
     * 对象转换成Json字符串
     *
     * @param bean     将转换为Json字符串的对象，不可为<code>null<code>
     * @param excludes 不转换的属性数组
     */
    public static String bean2Json(Object o, String[] excludes,
                                   Class... dictClazz) {
        if (o == null) {
            throw new IllegalArgumentException(
                    "object is null while write the Json string...");
        }

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        jsonConfig.setIgnoreDefaultExcludes(false);
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.registerJsonValueProcessor(java.sql.Date.class,
                new SqlDateProcessor());
        jsonConfig.registerJsonValueProcessor(Integer.class,
                new BaseProcessor());
        jsonConfig.registerJsonValueProcessor(Long.class,
                new BaseProcessor());
        jsonConfig.registerJsonValueProcessor(Date.class,
                new DateJsonValueProcessor());
        for (Class clazz : dictClazz) {
            jsonConfig.registerJsonBeanProcessor(clazz,
                    new DictJsonBeanProcessor());
        }
        String jsonString = JSONObject.fromObject(o, jsonConfig).toString();
        jsonString = jsonString.replace("result", "rows");
        return jsonString;
    }


    /**
     * 自定义日期格式转换
     *
     * @param o
     * @param excludes
     * @param dateFormat
     * @return
     */
    public static String bean2Json(Object o, String[] excludes,
                                   String dateFormat, Class... dictClazz) {
        if (o == null) {
            throw new IllegalArgumentException(
                    "object is null while write the Json string...");
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        jsonConfig.setIgnoreDefaultExcludes(false);
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.registerJsonValueProcessor(java.sql.Date.class,
                new SqlDateProcessor());
        jsonConfig.registerJsonValueProcessor(Integer.class,
                new BaseProcessor());
        jsonConfig.registerJsonValueProcessor(Long.class,
                new BaseProcessor());
        jsonConfig.registerJsonValueProcessor(Date.class,
                new DateJsonValueProcessor(dateFormat));
        jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,
                new DateJsonValueProcessor(dateFormat));
        for (Class clazz : dictClazz) {
            jsonConfig.registerJsonBeanProcessor(clazz,
                    new DictJsonBeanProcessor(dateFormat));
        }
        String jsonString = JSONArray.fromObject(o, jsonConfig).toString();

        // 默认输出的格式如下：[{"result":[{"id":0},{"id":1}],"totalCount":2}]
        // Ext.data.JsonReader支持的格式则为：{"result":[{"id":0},{"id":1}],"totalCount":2}
        // 去掉JsonArray自动生成最前面的"["和最后的"]"，暂时没找到对应的API处理:)
        jsonString = jsonString.substring(1, jsonString.length());
        jsonString = jsonString.substring(0, jsonString.length() - 1);
        jsonString = jsonString.replace("result", "rows");
        return jsonString;
    }

    public static String bean2Json(Object o, String dateFormat, Class... dictClazz) {
        return bean2Json(o, EXCLUDES, dateFormat, dictClazz);
    }

    /**
     * 对象转换成Json字符串
     *
     * @param bean 将转换为Json字符串的对象
     */
    public static String bean2Json(Object o, Class... dictClazz) {
        return bean2Json(o, EXCLUDES, dictClazz);
    }

    public static String bean2Json(PageInfo page, Class... dictClazz) {
        return bean2Json(page, EXCLUDES, dictClazz);
    }

    public static String bean2Json(PageInfo page, String dateFormat, Class... dictClazz) {
        return bean2Json(page, dateFormat, dictClazz);
    }

    public static String bean2JsonArray(Object o, String[] excludes,
                                        Class... dictClazz) {
        if (o == null) {
            throw new IllegalArgumentException(
                    "object is null while write the Json string...");
        }

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        jsonConfig.setIgnoreDefaultExcludes(false);
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.registerJsonValueProcessor(java.sql.Date.class,
                new SqlDateProcessor());
        jsonConfig.registerJsonValueProcessor(Integer.class,
                new BaseProcessor());
        jsonConfig.registerJsonValueProcessor(Long.class,
                new BaseProcessor());
        jsonConfig.registerJsonValueProcessor(Date.class,
                new DateJsonValueProcessor());
        for (Class clazz : dictClazz) {
            jsonConfig.registerJsonBeanProcessor(clazz,
                    new DictJsonBeanProcessor());
        }
        String jsonString = JSONArray.fromObject(o, jsonConfig).toString();
        jsonString = jsonString.replace("result", "rows");
        return jsonString;
    }

    public static String bean2JsonArray(Object o, String dateFormat, String[] excludes,
                                        Class... dictClazz) {
        if (o == null) {
            throw new IllegalArgumentException(
                    "object is null while write the Json string...");
        }

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        jsonConfig.setIgnoreDefaultExcludes(false);
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.registerJsonValueProcessor(java.sql.Date.class,
                new SqlDateProcessor());
        jsonConfig.registerJsonValueProcessor(Integer.class,
                new BaseProcessor());
        jsonConfig.registerJsonValueProcessor(Long.class,
                new BaseProcessor());
        jsonConfig.registerJsonValueProcessor(Date.class,
                new DateJsonValueProcessor());
        jsonConfig.registerJsonValueProcessor(Date.class,
                new DateJsonValueProcessor(dateFormat));
        jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,
                new DateJsonValueProcessor(dateFormat));
        for (Class clazz : dictClazz) {
            jsonConfig.registerJsonBeanProcessor(clazz,
                    new DictJsonBeanProcessor(dateFormat));
        }
        String jsonString = JSONArray.fromObject(o, jsonConfig).toString();
        jsonString = jsonString.replace("result", "rows");
        return jsonString;
    }

    public static String bean2JsonArray(Object o, String dateFormat,
                                        Class... dictClazz) {
        return bean2JsonArray(o, dateFormat, null, dictClazz);
    }

    /**
     * 将json格式的字符串解析成Map对象 <li>
     * json格式：{"name":"admin","sex":"M"}
     */
    public static HashMap<String, String> toHashMap(Object object) {
        HashMap<String, String> data = new HashMap<String, String>();
        // 将json字符串转换成jsonObject
        JSONObject jsonObject = JSONObject.fromObject(object);
        Iterator it = jsonObject.keys();
        // 遍历jsonObject数据，添加到Map对象
        while (it.hasNext()) {
            String key = String.valueOf(it.next());
            String value = String.valueOf(jsonObject.get(key));
            data.put(key, value);
        }
        return data;
    }

    /**
     * json转换map.
     * <br>详细说明
     *
     * @param jsonStr json字符串
     * @return Map<String,Object> 集合
     * @throws
     * @author slj
     */
    public static Map<String, Object> parseJSON2Map(String jsonStr) {
        ListOrderedMap map = new ListOrderedMap();
        //最外层解析
        JSONObject json = JSONObject.fromObject(jsonStr);
        for (Object k : json.keySet()) {
            Object v = json.get(k);
            //如果内层还是数组的话，继续解析
            if (v instanceof JSONArray) {
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                Iterator<JSONObject> it = ((JSONArray) v).iterator();
                while (it.hasNext()) {
                    JSONObject json2 = it.next();
                    list.add(parseJSON2Map(json2.toString()));
                }
                map.put(k.toString(), list);
            } else {
                map.put(k.toString(), v);
            }
        }
        return map;
    }

    /**
     * 自定义日期格式转换
     *
     * @param o
     * @param excludes
     * @param dateFormat
     * @return
     */
    public static JSONArray bean2JSONRow(Object o, String[] excludes,
                                         String dateFormat, Class... dictClazz) {
        if (o == null) {
            throw new IllegalArgumentException(
                    "object is null while write the Json string...");
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        jsonConfig.setIgnoreDefaultExcludes(false);
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.registerJsonValueProcessor(java.sql.Date.class,
                new SqlDateProcessor());
        jsonConfig.registerJsonValueProcessor(Integer.class,
                new BaseProcessor());
        jsonConfig.registerJsonValueProcessor(Long.class,
                new BaseProcessor());
        jsonConfig.registerJsonValueProcessor(Date.class,
                new DateJsonValueProcessor(dateFormat));
        jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,
                new DateJsonValueProcessor(dateFormat));
        for (Class clazz : dictClazz) {
            jsonConfig.registerJsonBeanProcessor(clazz,
                    new DictJsonBeanProcessor(dateFormat));
        }

        JSONArray jObj = JSONArray.fromObject(o, jsonConfig).getJSONObject(0).getJSONArray("rows");

        return jObj;
    }

}
