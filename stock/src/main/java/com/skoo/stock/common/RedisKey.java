package com.skoo.stock.common;

public class RedisKey {

    public static final String KEY = "_redis_key";
    public static final String FIELD = "_redis_field";
    public static final String TYPE = "_redis_type";

    /**
     * string形式
     */
    public static final String TYPE_STRING = "string";

    /**
     * byte形式
     */
    public static final String TYPE_BYTE = "byte";

    /**
     * Hash形式
     */
    public static final String TYPE_HASH = "hash";

    /**
     * key存放时间
     */
    public static final int KEY_TIME = 300;

    /**
     * USERINFO Object UserInfo
     */
    public static final String USERINFO = "_userinfo_";

    /**
     * COOKIE Object Cookie
     */
    public static final String COOKIE = "_cookie_";

    /**
     * VALIDATE_CODE
     */
    public static final String VALIDATE_CODE = "_validateCode_";

    /**
     * NEED_VERIFY
     */
    public static final String NEED_VERIFY = "needVerify_";

    /**
     * TOKEN
     */
    public static final String APP_TOKEN = "_token_";

    /**
     * APP_PHONE_CODE
     */
    public static final String APP_PHONE_CODE = "_phone_";

    /**
     * API_COUNTER
     */
    public static final String API_COUNTER = "_apicounter_";

    /**
     * API_COUNTER_SERVER
     */
    public static final String API_COUNTER_SERVER = "_apicounter_server_";

}
