package net.ryian.core.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

public class WebUtils {

    public static String getFailureMessage(String message) {
        JSONObject o = new JSONObject();
        o.put("success", false);
        o.put("message", message);
        return o.toString();
    }

    public static String getSuccuseMessage(String message) {
        JSONObject o = new JSONObject();
        o.put("success", true);
        if (!StringUtils.isEmpty(message))
            o.put("message", message);
        return o.toString();
    }

}
