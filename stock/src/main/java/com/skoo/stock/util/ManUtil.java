package com.skoo.stock.util;

import com.skoo.common.SystemConfig;
import com.skoo.stock.common.Constant;
import com.skoo.stock.sys.utils.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ManUtil帮助类
 */
public class ManUtil {
    private static final Logger log = LoggerFactory.getLogger(ManUtil.class);

    /**
     * 两列表比较
     *
     * @param dbList  数据库List
     * @param nowList 当前List
     */
    public static List<List<String>> getDiff(List<String> dbList, List<String> nowList) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>(dbList.size() + nowList.size());
        List<List<String>> diff= new ArrayList<List<String>>();
        List<String> diffAdd = new ArrayList<>();  //新增
        List<String> diffDel = new ArrayList<>();  //删除
        List<String> diffSame = new ArrayList<>(); //共有
        List<String> maxList = dbList;
        List<String> minList = nowList;

        int iflg = 0;//0:db数据 1：当前数据
        if (nowList.size() > dbList.size()) {
            maxList = nowList;
            minList = dbList;
            iflg = 1;
        }

        for (String string : maxList) {
            map.put(string, iflg);//私有标志1
        }

        for (String string : minList) {
            Integer cc = map.get(string);
            if (cc != null) {
                map.put(string, 22 + cc);//共有标志
                continue;
            }
            map.put(string, iflg == 1 ? 0 : 1);//私有标志2
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                diffAdd.add(entry.getKey());
            } else if (entry.getValue() == 0) {
                diffDel.add(entry.getKey());
            } else {
                diffSame.add(entry.getKey());
            }
        }

        diff.add(diffAdd);
        diff.add(diffDel);
        diff.add(diffSame);

        return diff;
    }

    public static Object getAuthConfig(String config) {
        Map authMap = JsonUtils.parseJSON2Map(SystemConfig.INSTANCE.getValue(Constant.AUTH_CONFIG));
        return authMap.get(config);
    }

    public static Object getSmsConfig(String config) {
        Map authMap = JsonUtils.parseJSON2Map(SystemConfig.INSTANCE.getValue(Constant.SMS_CONFIG));
        return authMap.get(config);
    }

    public static void main(String[] args) {

        List<String> list1 = new ArrayList<String>();

        List<String> list2 = new ArrayList<String>();

        for (int i = 0; i < 10000; i++) {

            list1.add("test" + i);

            list2.add("test" + i * 2);

        }

        getDiff(list1, list2);

        System.out.println(generateToken("13355780131","pass131aaaaaaaaaaaaaa","20150426111230","aaa"));

    }

    /**
     * 生成Token
     *
     * @param logintype  1：手机；2：QQ；3：微信；4：微博
     * @param account 账号
     * @param deviceid 设备id
     * @param loginTime 登录时间
     *
     */
    public static String generateToken(String logintype, String account, String deviceid, String loginTime) {
        return EncryptUtil.encrypt(logintype + ";" + account + ";" + deviceid + ";" + loginTime);
    }

    public static boolean isRedis() {
        String cacheEnable = SystemConfig.INSTANCE.getValue("redisactive");
        //判断是否开启缓存
        return Constant.TRUE.equals(cacheEnable);
    }

    public static String getMsg(String code, Object... arg) {
        return MessageFomater.INSTANCE.format(code, arg);
    }
}
