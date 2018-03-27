package com.skoo.stock.common.service;

import com.skoo.stock.common.BDC;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 百度API通用接口
 *
 * @author gomiten@163.com
 */
public class BaiduService {

    private static final Logger LOG = LoggerFactory.getLogger(BaiduService.class);

    /**
     * 根据经纬度获取地址
     *
     * @param parameterMap 请求参数 经度lng 纬度lat
     * @return Json字符串
     */
    public static String getAddress(Map<Object, Object> parameterMap) {
        try {
            String location = parameterMap.get(BDC.LNG) + "," + parameterMap.get(BDC.LAT);

            Map<Object, Object> baiduMap = new HashMap<>();
            baiduMap.put(BDC.LOCACTION, location);
            baiduMap.put(BDC.OUTPUT, "json");
            baiduMap.put(BDC.POIS, "1");

            String json = CommonService.getBaiduJSON(baiduMap);
            JSONObject jsonObject = JSONObject.fromObject(json);

            JSONObject resultObject = JSONObject.fromObject(jsonObject.get("result"));

            return resultObject.get("formatted_address").toString();
        } catch (Exception ex) {
            LOG.error("BaiduService.getAddress", "百度API：根据经纬度获取地址失败！");
            return "";
        }
    }

    /**
     * 根据地址获取经纬度
     *
     * @param parameterMap 请求参数 address, city
     * @return 经纬度字符串
     */
    public static String getLocation(Map<Object, Object> parameterMap) {
        try {
            Object address = parameterMap.get(BDC.ADDRESS);
            Object city = parameterMap.get(BDC.CITY);
            Map<Object, Object> baiduMap = new HashMap<>();
            baiduMap.put(BDC.ADDRESS, address);
            if (city != null) {
                baiduMap.put(BDC.CITY, city);
            }
            baiduMap.put(BDC.OUTPUT, "json");
            baiduMap.put(BDC.POIS, "1");

            String json = CommonService.getBaiduJSON(baiduMap);
            JSONObject jsonObject = JSONObject.fromObject(json);

            JSONObject resultObject = JSONObject.fromObject(jsonObject.get("result"));

            JSONObject locationObject = JSONObject.fromObject(resultObject.get("location"));
            return locationObject.get("lng").toString()+","+locationObject.get("lat").toString();
        } catch (Exception ex) {
            LOG.error("BaiduService.getLocation", "百度API：根据地址获取经纬度失败！");
            return "";
        }
    }
}
