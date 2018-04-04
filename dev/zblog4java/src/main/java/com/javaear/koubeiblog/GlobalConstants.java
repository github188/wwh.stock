package com.javaear.koubeiblog;

/**
 * 全局常量配置
 *
 * @author aooer
 */
public class GlobalConstants {

    //db配置 版本号 数据库主键
    public static final Integer ConfigVersionId = 1;

    //db配置 全局设置 数据库主键
    public static final Integer ConfigSettingId = 2;


    //ehcache 缓存配置 globalSetting为全局设置名称
    public static final String Global_Setting = "globalSetting";

    //ehcache 缓存配置 cacheDto为全局配置缓存Key
    public static final String CacheDTO = "CacheDTO";


}
