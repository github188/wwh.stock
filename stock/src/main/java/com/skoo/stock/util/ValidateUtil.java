package com.skoo.stock.util;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {

    /**
     * 验证邮箱地址是否正确
     *
     * @param email
     * @return 是/否
     */
    public static boolean isEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    /**
     * 验证手机号码
     *
     * @param mobiles
     * @return 是/否
     */
    public static boolean isMobile(String mobiles) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static boolean isNum(String number) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^[0-9]{5}$");
            Matcher m = p.matcher(number);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    @SuppressWarnings("unchecked")
    public static boolean isNotCheckURL(String uri, String keyName) {
        Set<String> notCheckURLList = (Set<String>)CacheUtil.getData(keyName);
        for (String nocheck : notCheckURLList) {
            if (uri.contains(nocheck)) {
                return true;
            }
        }

        return false;
    }
}
