package cn.hzstk.securities.common.utils.east;

import cn.hzstk.securities.api.service.WebUtils;
import cn.hzstk.securities.common.constants.Constant;
import cn.hzstk.securities.common.constants.EastConstant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class StkCommUtil {

    private static String getSplit(String val, String sign) {
        if (val.startsWith("{")) return val;

        String[] tmp = val.split(sign);
        if (tmp.length > 1) return tmp[1].trim();
        tmp = val.split("\\(");
        if (tmp.length > 1) return "{'default':" + tmp[1].replace(")", "}");
        /*tmp = val.split("\\(");
        if (tmp.length > 1) return tmp[1].replace(")", "");*/

        return val.trim();
    }

    public static Map<String, Object> getJsonMap(String[] param, String val) throws IOException {
        String responseBody = WebUtils.doGet(param[0].replace(Constant.REPLACE_STR, val));
        responseBody = getSplit(responseBody, Constant.EQUAL_SIGN);

        Map<String, Object> result = JSON.parseObject(responseBody);
        if ("JSONArray".equals(result.get(param[1]).getClass().getSimpleName())) return result;
        Map<String, Object> map = (Map<String, Object>)result.get(param[1]);
        return map;
    }

    public static Map<String, Object> getJsonMap(String param, String param1) throws IOException {
        String responseBody = WebUtils.doGet(param);
        responseBody = getSplit(responseBody, Constant.EQUAL_SIGN);
        if (responseBody.indexOf("{") < 0) return null;

        Map<String, Object> result = JSON.parseObject(responseBody);
        Map<String, Object> map = (Map<String, Object>)result.get(param1);
        return map;
    }

    public static List<Map<String, Object>> getJsonListMap(String[] param, String val) throws IOException {
        List<Map<String, Object>> arr = new ArrayList<>();
        Map<String, Object> result = getJsonMap(param, val);
        JSONArray json = (JSONArray)result.get(param[1]);
        Map<String, Object> tmp;
        for (int i = 0; i < json.size(); i++) {
            tmp = JSON.parseObject(json.get(i).toString());
            arr.add(tmp);
        }
        return arr;
    }

    public static List<String[]> getJsonList(String param1, String param2) throws IOException {
        String[] arg = new String[]{param1, param2};
        return getJsonList(arg);
    }

    public static List<String[]> getJsonList(String[] param) throws IOException {
        String responseBody = WebUtils.doGet(param[0]);

        if (responseBody.startsWith("var") || responseBody.startsWith("call")) responseBody = getSplit(responseBody, Constant.EQUAL_SIGN);
        Map<String, Object> result = JSON.parseObject(responseBody);
        List jsonList = (List)result.get(param[1]);
        List<String[]> list = new ArrayList<>();
        String[] val;
        if (jsonList != null && jsonList.size() > 0) {
            if (jsonList.get(0).toString().indexOf(Constant.COMMA) == -1) {
                val = (String[])jsonList.toArray(new String[jsonList.size()]);
                list.add(val);
                return list;
            }
        }

        for (Object aList : jsonList) {
            val = aList.toString().split(Constant.COMMA);
            if (val != null) list.add(val);
        }

        return list;
    }

    public static List<String[]> getJsonList(String param) throws IOException {
        String responseBody = WebUtils.doGet(param);
        String tmp = getSplit(responseBody, Constant.EQUAL_SIGN);
        Map<String, Object> result = JSON.parseObject(tmp);
        List jsonList = (List)getFirstOrNull(result);

        List<String[]> list = new ArrayList<>();
        String[] val;
        if (jsonList != null && jsonList.size() > 0) {
            if (jsonList.get(0).toString().indexOf(Constant.COMMA) == -1) {
                val = (String[])jsonList.toArray(new String[jsonList.size()]);
                list.add(val);
                return list;
            }
        }

        for (Object aList : jsonList) {
            val = aList.toString().split(Constant.COMMA);
            if (val != null) list.add(val);
        }
        return list;
    }

    public static List<String[]> getexJsonList(String param) throws IOException {
        String responseBody = WebUtils.doGet(param);
        String tmp = responseBody.replace("(", "").replace(")", "").replace("[", "").replace("]", "");
        String[] jsonList = tmp.substring(1,tmp.length()-1).split("\",\"");

        List<String[]> list = new ArrayList<>();
        String[] val;
        for (Object aList : jsonList) {
            val = aList.toString().split(Constant.COMMA);
            if (val != null) list.add(val);
        }
        return list;
    }

    /**
     * 获取map中第一个数据值
     *
     * @param <K> Key的类型
     * @param <V> Value的类型
     * @param map 数据源
     * @return 返回的值
     */
    public static <K, V> V getFirstOrNull(Map<K, V> map) {
        V obj = null;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            obj = entry.getValue();
            if (obj != null) {
                break;
            }
        }
        return obj;
    }
    public static void main(String[] args) throws IOException {
        //Document doc = getDocument("http://vip.stock.finance.sina.com.cn/mkt/#gn_zncd");
        List<String[]> list = StkCommUtil.getJsonList(EastConstant.INDEX_COUNT_PARAM);
        //String[] val = list.toArray(new String[list.size()]);
        //Map<String, Object> map = StkCommUtil.getJsonMap(EastConstant.TIGER_PARAM, "000001");
        //List<Map<String, Object>> map = getJsonListMap(EastConstant.YJFP_PARAM, "2016-12-31");
    }
}
