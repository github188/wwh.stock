package net.ryian.core;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Properties;

public class SystemConfig {

    protected static final Logger logger = Logger.getLogger(SystemConfig.class);
    public static SystemConfig INSTANCE = new SystemConfig();

    private Properties prop;

    protected SystemConfig() {
        prop = new Properties();
        try {
            prop.load(new InputStreamReader(this.getClass().getClassLoader()
                    .getResourceAsStream("system.properties"), "UTF-8"));
        } catch (IOException e) {
            logger.error(e);
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

    public boolean isDebug() {
        return getValue("debug", false);
    }

    public String format(String key, String... value) {
        return MessageFormat.format(getValue(key), value);
    }

    public static void main(String[] s) {
        System.out.println(SystemConfig.INSTANCE.format("test", "aa", "bb", "cc"));
    }

}
