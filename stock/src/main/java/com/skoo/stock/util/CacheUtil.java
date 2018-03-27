package com.skoo.stock.util;

import com.skoo.common.SystemConfig;
import com.skoo.stock.common.ApiKey;
import com.skoo.stock.common.Constant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class CacheUtil {

    protected static final HashMap map = new HashMap();   // 缓存Map

    private static final Object lock = new Object();

    private CacheUtil() {
    }   //   防止在外部实例化

    public static Object getData(Object key) {
        Object v = map.get(key);
        if (v == null) {
            synchronized (lock) {
                v = map.get(key);
                // 检查是否存在
                if (v == null) {
                    loadDataSource(key);
                    v = map.get(key);
                }
            }
        }

        return v;
    }

    /*
      * 加载数据到缓存Map
      */
    protected static synchronized void loadDataSource(Object key) {
        Object value;
        // 非检查列表
        if (Constant.NOT_CHECK_URL.equals(key.toString())) {
            value = getNotCheckURL(key.toString());
        } else if (Constant.API_URL.equals(key.toString())) { //接口
            value = getApiList(key.toString());
        } else {
            value = new Object();
        }

        map.put(key, value);
    }

    private static Set<String> getNotCheckURL(String key) {
        Set<String> notCheckURLList = new HashSet<>();
        // 取得过滤外URL列表
        String notCheckURLListStr = SystemConfig.INSTANCE.getValue(key);
        if (notCheckURLListStr != null) {
            String[] params = notCheckURLListStr.split(",");
            for (String param : params) {
                notCheckURLList.add(param.trim());
            }
        }
        return notCheckURLList;
    }

    private static Map<String, String> getApiList(String key) {
        Map<String, String> map = new HashMap<>();
        // 取得接口URL列表
        map.put(ApiKey.FORGOTPASS, ApiKey.KEY_FORGOTPASS);

        return map;
    }
}
