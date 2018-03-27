package com.bm.wanma.model.net;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class NetUtil {
    //根据string获取jsonObject
    public static JSONObject getJsonObject(Object jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString.toString());
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //根据接口定义的规则得出status
    public static boolean getStatus(JSONObject jsonObject){
        if("1".equals(jsonObject.optString("1"))){
            return true;
        }
        return false;
    }

    //根据接口定义的规则得出errorMsg
    public static String getErrorMsg(JSONObject jsonObject){
        return jsonObject.optString("message");
    }

    //根据接口规则获取到内容
    public static Object getData(JSONObject jsonObject ,Class classOfT ){
        Gson gson = new Gson();
        return gson.fromJson(jsonObject.optString("resultData"),classOfT);
    }

    public static JSONObject getDataObj(JSONObject jsonObject) {
        return jsonObject.optJSONObject("resultData");
    }

    public static String getDataStr(JSONObject jsonObject) {
        return jsonObject.optString("resultData");
    }

    //此方法只能针对基本数据类型
    public static <T> List<T> getData(String jsonText) {
        Gson gson = new Gson();
        List<T> tempList = gson.fromJson(jsonText, new TypeToken<List<T>>() {}.getType());
        return tempList;
    }
}
