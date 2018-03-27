package com.skoo.stock.common;

public class Constant {
    public static final String LOGFILE = "mytest";
    public static final String EASTADDR = "http://quote.eastmoney.com/center/";
    public static final String TFPADDR = "http://data.eastmoney.com/gsrl/tfpts.html";
    public static final String CNADDR = "http://data.cnstock.com/gpsj/cwsj/fhsz.html";
    public static final String CODETITLE = "代码";
    public static final String DEFAULTSTOCK = "002024";
    public static final String CONCEPTFLG = "4";
    public static final String INDUSTRYFLG = "4";
    public static final String NETFLG = "2";
    public static final String STARFLG = "1";    //证券之星
    public static final String EASTFLG = "2";    //东方财富网
    public static final String STAR = "stockstar";
    public static final String EAST = "eastmoney";

    /**
     * 判别是否
     */
    public static final String YES = "Y";
    public static final String NO = "N";

    /**
     * 判别是否
     */
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    /**
     * 用户类型
     */
    public static final String USER_TYPE_STAFF = "1";
    public static final String USER_TYPE_MEMBER = "2";

    /**
     * 退出登录URL
     */
    public static final String LOGOUT_URL = "/logout";

    /**
     * 免检URL
     */
    public static final String NOT_CHECK_URL = "notcheckurl";

    /**
     * 接口URL
     */
    public static final String API_URL = "apiurl";

    /**
     * 路径分隔符
     */
    public static final String SPT = "/";
    /**
     * 索引页
     */
    public static final String INDEX = "index";
    /**
     * 默认模板
     */
    public static final String DEFAULT = "default";
    /**
     * UTF-8编码
     */
    public static final String UTF8 = "UTF-8";
    /**
     * 提示信息
     */
    public static final String MESSAGE = "message";
    /**
     * cookie中的JSESSIONID名称
     */
    public static final String JSESSION_COOKIE = "JSESSIONID";
    /**
     * url中的jsessionid名称
     */
    public static final String JSESSION_URL = "jsessionid";
    /**
     * HTTP POST请求
     */
    public static final String POST = "POST";
    /**
     * HTTP GET请求
     */
    public static final String GET = "GET";

    /**
     * 系统权限相关配置
     */
    public static final String AUTH_CONFIG = "authconfig";
    public static final String AUTH_ADMIN = "admin";
    public static final String AUTH_ADMIN_ROLE = "adminRole";

    /**
     * 短信网关相关配置
     */
    public static final String SMS_CONFIG = "smsgate";


    /**
     * 上传类型
     */
    public static final String UPLOAD_TYPE_LOCAL = "local";     // 本地
    public static final String UPLOAD_TYPE_REMOTE = "remote";   // 远程
    public static final String UPLOAD_REMOTE_PRC = "picprocess";   // 图片上传处理
    public static final String UPLOAD_SUCC_STATUS = "100";   // 上传成功返回状态值

    /**
     * 返回值
     */
    public static final String RETURN_CODE_OK = "100";           // 返回值:正常
    public static final String RETURN_CODE_NO_DATA = "101";     // 返回值:无数据
    public static final String RETURN_CODE_CREATE_NG = "102";   // 返回值:创建失败
    public static final String RETURN_CODE_UPDATE_NG = "103";   // 返回值:更新失败
    public static final String RETURN_CODE_PARAM_ERROR = "099"; // 返回值:参数错误
    public static final String RETURN_CODE_DB_ERROR = "999";   // 返回值:数据库异常
    public static final String RETURN_CODE_UPLOAD_ERROR = "998";   // 返回值:上传异常
    public static final String RETURN_CODE_UNKOWN_ERROR = "997";   // 返回值:未知异常

    public static final String PUBLISH_STATUS_INIT = "0"; // 发布状态（0：初始）
    public static final String PUBLISH_STATUS_PUB = "1";  // 发布状态（1：发布）
    public static final String PUBLISH_STATUS_DOWN = "2"; // 发布状态（2：下架）


}
