package cn.hzstk.securities.common;

public class Constant {
    public static final String LOG_REAL = "stock";
    public static final String LOG_ERROR = "error";
    public static final String LOG_FILE = "message";
    public static final String EAST_ADDRESS = "http://quote.eastmoney.com/center/";
    public static final String EAST_DATA_ADDRESS = "http://data.eastmoney.com/";
    public static final String STAR_ADDRESS = "http://stock.quote.stockstar.com/";
    public static final String CN_ADDRESS = "http://data.cnstock.com/gpsj/cwsj/fhsz.html";
    public static final String INDEX_ADDRESS = "http://quote.stockstar.com/futures/ifnational.htm";
    public static final String CODETITLE = "代码";
    public static final String CONCEPTFLG = "4";
    public static final String INDUSTRYFLG = "4";
    public static final String NETFLG = "2";
    public static final String STAR_NET = "1";    //证券之星
    public static final String EAST_NET = "2";    //东方财富网
    public static final String STAR = "stockstar";
    public static final String EAST = "eastmoney";
    public static final String[] EAST_FIRST = {"center","bbsj","zjlx","gsrl"};
    public static final int DATA_COUNT = 1000;

    public static final String DATE_FORMAT8_STR = "yyyyMMdd";
    public static final String FILE_SUFFIX_TXT = ".txt";
    public static final String FILE_SUFFIX_XLS = ".xls";
    public static final String STOCK_TEMPLATE = "000000";
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
     * 数字长度
     */
    public static final int CODE_LENGTH = 6;
    public static final int NAME_LENGTH = 4;

    /**
     * 接口URL
     */
    public static final String API_URL = "apiurl";

    /**
     * 路径分隔符
     */
    public static final String PATH_SPLIT = "/";
    public static final String DELIMITER = "\\|";
    public static final String FULL_COLON = "：";
    public static final String FULL_COMMA = "、";
    public static final String FULL_STOP = "。";
    public static final String LEFT_BRACKET = "（";
    public static final String RIGHT_BRACKET = "）";
    public static final String PERIOD = ".";
    public static final String COMMA = ",";
    public static final String EQUAL_SIGN = "=";
    public static final String TEXT_BR = "<br />";
    public static final String REPLACE_STR = "{0}";
    public static final String REPLACE_STR1 = "{1}";
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
     * GBK编码
     */
    public static final String GBK = "GB2312";
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

    public static final String LOG_PATH = "E:/idea/wwh/project"; // 日志目录
    public static final String PUBLISH_STATUS_PUB = "1";  // 发布状态（1：发布）
    public static final String PUBLISH_STATUS_DOWN = "2"; // 发布状态（2：下架）

    /**
     * 秒
     */
    public static final int SECOND = 1000;

    /**
     * 窗口名称
     */
    public static final String ZS_CLASSNAME = "TdxW_MainFrame_Class";
    public static final String GOOGLE_CLASSNAME = "Chrome_WidgetWin_1";
    public static final String EXCEL_CLASSNAME = "XLMAIN";

    /**
     * 招商证券日历坐标位置
     */
    public static final int CALENDAR_START_X = 432;
    public static final int CALENDAR_START_Y = 202;
    public static final int CALENDAR_INCREMENT_X = 26;
    public static final int CALENDAR_INCREMENT_Y = 14;
    public static final int CALENDAR_INCREMENT_MON = 132;

    public static final String SEMI_YEAR = "SemiYear";
    public static final String QUARTER = "Quarter";
}
