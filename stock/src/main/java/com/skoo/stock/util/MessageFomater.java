package com.skoo.stock.util;

import com.skoo.common.SystemConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Properties;

public class MessageFomater {

    protected static final Log logger = LogFactory.getLog(SystemConfig.class);
    public static MessageFomater INSTANCE = new MessageFomater();

    private Properties prop;

    protected MessageFomater() {
        prop = new Properties();
        try {
            prop.load(new InputStreamReader(this.getClass().getClassLoader()
                    .getResourceAsStream("message/message.properties"), "UTF-8"));
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public String format(String key, Object... value) {
        return MessageFormat.format(prop.getProperty(key), value);
    }

    public static void main(String[] s) {
        System.out.println(MessageFomater.INSTANCE.format("msg0004", "aa"));
    }

}
