package com.skoo.stock.login;

import com.skoo.stock.login.domain.UserInfo;

import java.util.Hashtable;
import java.util.Set;

@SuppressWarnings("unchecked")
public class UserSession {

    public final static String SESSION_USER_INFO = "SESSION_USER_INFO";
    private static final ThreadLocal<Hashtable<String, Object>> SESSION_LOCAL =
            new ThreadLocal<Hashtable<String, Object>>();

    protected UserSession() {
    }

    public static synchronized Object remove(String key) {
        Hashtable<String, Object> hashtable = SESSION_LOCAL.get();
        if (hashtable == null) {
            SESSION_LOCAL.set(new Hashtable<String, Object>());
            return null;
        }
        return SESSION_LOCAL.get().remove(key);
    }

    public static synchronized void removeAll() {
        Hashtable<String, Object> hashtable = SESSION_LOCAL.get();
        if (hashtable != null) {
            Set<String> set = hashtable.keySet();
            Object[] objs = set.toArray();
            for (int i = 0; i < objs.length; i++) {
                hashtable.remove(objs[i]);
            }
        }
    }

    public static synchronized boolean isEmpty() {
        if (SESSION_LOCAL.get() == null) {
            return true;
        }
        return SESSION_LOCAL.get().isEmpty();
    }

    public static synchronized Object get(String key) {
        Hashtable<String, Object> hashtable = SESSION_LOCAL.get();
        if (hashtable == null) {
            SESSION_LOCAL.set(new Hashtable<String, Object>());
            return null;
        }
        return SESSION_LOCAL.get().get(key);
    }

    public static synchronized void set(String key, Object value) {
        if (value == null) {
            return;
        }
        Hashtable<String, Object> table = SESSION_LOCAL.get();
        if (table == null) {
            table = new Hashtable<String, Object>();
        }
        table.put(key, value);
        SESSION_LOCAL.set(table);
    }

    public static <T> T get(String attribute, Class<T> clazz) {
        return (T) get(attribute);
    }

    public static UserInfo getUserInfo() {
        return get(SESSION_USER_INFO, UserInfo.class);
    }

    public static void setUserInfo(UserInfo userInfo) {
        set(SESSION_USER_INFO, userInfo);
    }

    public static Long getAssocId() {
        UserInfo userInfo = get(SESSION_USER_INFO, UserInfo.class);
        return userInfo != null ? userInfo.getAssocId() : null;
    }

    public static Long getUserId() {
        UserInfo userInfo = get(SESSION_USER_INFO, UserInfo.class);
        return userInfo != null ? userInfo.getId() : null;
    }

    public static String getUserType() {
        UserInfo userInfo = get(SESSION_USER_INFO, UserInfo.class);
        return userInfo != null ? userInfo.getUserType() : null;
    }

    public static Long getOrgId() {
        UserInfo userInfo = get(SESSION_USER_INFO, UserInfo.class);
        return userInfo != null ? userInfo.getOrgId() : null;
    }

    public static Integer getSiteId() {
        UserInfo userInfo = get(SESSION_USER_INFO, UserInfo.class);
        return userInfo != null ? userInfo.getTopDeptId() : null;
    }
}
