package com.zjhcsoft.smartcity.cas.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by allenwc on 16/3/23.
 */
public class SystemConfig {
    public static SystemConfig INSTANCE = new SystemConfig();

    private Properties prop;

    protected SystemConfig() {
        prop = new Properties();
        try {
            prop.load(new InputStreamReader(this.getClass().getClassLoader()
                    .getResourceAsStream("system.properties"), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getValue(String key) {
        return getValue(key, null);
    }

    public String getValue(String key, String defaultVal) {
        String s = prop.getProperty(key);
        if (s == null)
            return defaultVal;
        else
            return s;
    }

    public boolean getValue(String key, boolean defaultVal) {
        String s = prop.getProperty(key);
        if (s == null)
            return defaultVal;
        else
            return Boolean.parseBoolean(s);
    }

    public int getValue(String key, int defaultVal) {
        String s = prop.getProperty(key);
        if (s == null)
            return defaultVal;
        else
            return Integer.parseInt(s);
    }

    public String getSystemName() {
        return getValue("system.name","统一身份认证平台");
    }

}
