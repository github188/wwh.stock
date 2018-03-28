package com.ijustyce.fastandroiddev3.baseLib.utils;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yc on 15-12-24.   string 工具类
 */
public class StringUtils {

    public static boolean isEmpty(String value){
        if (value == null || value.isEmpty()) return true;
        Pattern p = Pattern.compile("([\\s]+)");
        Matcher matcher = p.matcher(value);
        return matcher.matches();
    }

    public static boolean allEmpty(String... values){
        if (values == null || values.length < 1) return true;
        for (String tmp : values){
            if (!isEmpty(tmp)) return false;
        }
        return true;
    }

    public static String getAfterHostUrl(String url){
        if (!RegularUtils.isCommonUrl(url)) return null;
        String deleteHttp = url.replace("https://", "").replace("http://", "");
        String array[] = deleteHttp.split("/");
        return array.length <=1 ? null : deleteHttp.substring(array[0].length());
    }

    public static boolean isIpSiteUrl(String url){
        String host = getOriginDomain(url);
        if (host == null) return false;
        return RegularUtils.isNumber(host.replaceAll("\\.", ""));
    }

    public static String getOriginDomain(String url) {
        String host = getHost(url);
        if (host == null) return null;
        int lastIndex = host.lastIndexOf(".");
        String end = lastIndex != -1 ? host.substring(lastIndex) : host;
        String begin = host.substring(0, lastIndex);
        lastIndex = begin.lastIndexOf(".");
        if (lastIndex != -1 ) {
            begin = begin.substring(lastIndex + 1);
        }
        return begin + end;
    }

    public static String getHost(String url){
        if (!RegularUtils.isCommonUrl(url)) return null;
        String delteHttp = url.replace("https://", "").replace("http://", "");
        String array[] = delteHttp.split("/");
        return array.length == 0 ? null : array[0];
    }

    public static int getInt(String value){

        return getInt(value, 0);
    }

    public static int getInt(String value, int defaultValue){

        if (!RegularUtils.isInt(value)) return defaultValue;
        try {
            return Integer.parseInt(value);
        }catch (Exception e){
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static double getDouble(String value){

        return getDouble(value, 0.0);
    }

    /**
     * 返回几千，保留两位小数！
     * @param v
     */
    public static String getKValue(Object v){
        String s = String.valueOf(v);
        int value = getInt(s);
        if (value > 1000){
            int result = value / 1000 + (value % 1000 == 0 ? 0 : 1);
            return result + "k";
        }
        return s;
    }

    public static double getDouble(String value, double defaultValue){

        if (!RegularUtils.isNumber(value)) return defaultValue;
        try {
            return Double.parseDouble(value);
        }catch (Exception e){
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static float getFloat(String value){

        return getFloat(value, 0);
    }

    public static float getFloat(String value, float defaultValue){

        if (!RegularUtils.isNumber(value)) return defaultValue;
        try {
            return Float.parseFloat(value);
        }catch (Exception e){
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static long getLong(String value, long defaultValue){

        if (!RegularUtils.isInt(value)) return defaultValue;
        try {
            return Long.parseLong(value);
        }catch (Exception e){
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static long getLong(String value){

        return getLong(value, 0);
    }

    /**
     * 解析并返回url的参数, url 必须为uft-8编码
     * @param url url 比如： https://mclient.alipay.com/home/exterfaceAssign.htm?alipay_exterface
     *            _invoke_assign_client_ip=115.192.220.130&body=测试
     */
    @NonNull
    public static HashMap<String, String> getUrlParams(String url){
        return getUrlParams(url, "utf-8");
    }

    /**
     * 解析并返回url的参数
     * @param encode url 编码
     * @param url url 比如： https://mclient.alipay.com/home/exterfaceAssign.htm?alipay_exterface
     *            _invoke_assign_client_ip=115.192.220.130&body=测试
     */
    @NonNull
    public static HashMap<String, String> getUrlParams(String url, String encode){

        HashMap<String, String> map = new HashMap<>();
        try {
            url = java.net.URLDecoder.decode(url, encode);
        }catch (Exception ignore) {}
        String[] urls = url.split("\\?", 2);
        if (urls.length < 2) return map;
        return getKeyValue(urls[1]);
    }

    public static HashMap<String, String> getKeyValue(String keyValues) {

        HashMap<String, String> map = new HashMap<>();
        String keyAndValue[] = keyValues.split("&");
        if (keyAndValue.length < 1) return map;
        for (String keyValue : keyAndValue) {
            if (keyValue == null) continue;
            String[] tmp = keyValue.split("=", 2);
            if (tmp.length < 2) continue;
            map.put(tmp[0], tmp[1]);
        }
        return map;

        // 也可以用以下的正则，可是正则慢了不少！
//        HashMap<String, String> map = new HashMap<>();
//        Pattern p = Pattern.compile("([^?&=]+)([=])([^&]+)");
//        Matcher m = p.matcher(url);
//        while(m.find()){
//            map.put(m.group(1), m.group(3));
//        }
//        return map;
    }

    /**
     * 判断是否为新版本
     * @param net   网络返回的版本 比如 0.5.09
     * @param local 本地版本      比如 0.5.1
     * @return  是否为新版本
     */
    public static boolean isNewVersion(String net, String local){

        net = RegularUtils.deleteNoNumber(net);
        local = RegularUtils.deleteNoNumber(local);
        if (StringUtils.isEmpty(net) || StringUtils.isEmpty(local)) return false;
        String[] netVersion = net.split("\\.");
        String[] localVersion = local.split("\\.");

        int size = netVersion.length > localVersion.length ? localVersion.length : netVersion.length;
        for (int i =0; i < size; i++){
            double netTmp = getDouble("0." + netVersion[i]);
            double localTmp = getDouble("0." + localVersion[i]);
            if (netTmp > localTmp){
                return true;
            }if (netTmp < localTmp){
                return false;
            }
        }
        return netVersion.length > localVersion.length; //  除非长度不一且前面每位都相等，否则不可能到这步，
    }

    public static String arabicNumberToChinese(int number) {
        String fuhao = "";
        if (number < 0) {
            fuhao = "负";
        }
        if (number < 100000) {
            return getChineseNumberLessShiWan(String.valueOf(number));
        }
        if (number < 10000_0000) {
            String s1 = arabicNumberToChinese(number / 10000);
            String s2 = arabicNumberToChinese(number % 10000);
            return s1 + "万零" + s2;
        }
        int index = 1;  //  int 类型最大 十亿
        String value = String.valueOf(number);
        if (number > 10000_00000){
            index = 2;
        }
        String value1 = value.substring(0, index);
        String value2 = value.substring(index);
        String s1 = arabicNumberToChinese(Integer.parseInt(value1));
        String s2 = arabicNumberToChinese(Integer.parseInt(value2));
        return fuhao + s1 + "亿" + s2;
    }

    private static int getLessThanYiValue(int number){
        if (number > 10000_00000){
            return Integer.parseInt(String.valueOf(number).substring(2));
        }
        else if (number > 10000_0000){
            return Integer.parseInt(String.valueOf(number).substring(1));
        }
        return number;
    }

    private static String getChineseNumberLessShiWan(String value){
        StringBuilder stringBuilder = new StringBuilder();
        int size = value.length();
        for (int i = 0; i < size; i++){
            stringBuilder.append(getChineseNumberByLength(value.substring(i, size)));
        }
        return stringBuilder.toString();
    }

    private static String getChineseNumberByLength(String value){

        if (value == null) return "";
        String s = getChineseNumber(value);
        if (s == null) return "";
        switch (value.length()){

            case 2:
                return s.equals("一") ? "十" : s + "十";

            case 3:
                return s + "百";

            case 4:
                return s + "千";

            case 5:
                return s + "万";

            default:
                return s;
        }
    }

    private static String getChineseNumber(String value){
        if (value == null || value.length() < 1) return "";
        switch (value.substring(0, 1)){

            case "1":
                return "一";

            case "2":
                return "二";

            case "3":
                return "三";

            case "4":
                return "四";

            case "5":
                return "五";

            case "6":
                return "六";

            case "7":
                return "七";

            case "8":
                return "八";

            case "9":
                return "九";

            default:
                return null;

        }
    }
}