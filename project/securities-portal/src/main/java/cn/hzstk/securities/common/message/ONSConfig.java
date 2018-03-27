package cn.hzstk.securities.common.message;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * Created by allenwc on 16/4/28.
 */
public class ONSConfig {

    protected static final Logger logger = Logger.getLogger(ONSConfig.class);
    public static ONSConfig INSTANCE = new ONSConfig();
    private Properties prop = new Properties();

    protected ONSConfig() {
        try {
            this.prop.load(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("/spring/application.properties"), "UTF-8"));
        } catch (IOException e) {
            logger.error(e);
        }

    }

    public String getValue(String key) {
        return this.getValue(key, (String) null);
    }

    public String getValue(String key, String defaultVal) {
        String s = this.prop.getProperty(key);
        return s == null ? defaultVal : s;
    }

    public boolean getValue(String key, boolean defaultVal) {
        String s = this.prop.getProperty(key);
        return s == null ? defaultVal : Boolean.parseBoolean(s);
    }

    public int getValue(String key, int defaultVal) {
        String s = this.prop.getProperty(key);
        return s == null ? defaultVal : Integer.parseInt(s);
    }

    public String getTopicUser() {
        return this.getValue("ons.topic_user");
    }

    public String getTopicTaskStatus() {
        return this.getValue("ons.topic_task_status");
    }

    public boolean isDebug() {
        return this.getValue("debug", false);
    }

    public String format(String key, String... value) {
        return MessageFormat.format(this.getValue(key), value);
    }


}
