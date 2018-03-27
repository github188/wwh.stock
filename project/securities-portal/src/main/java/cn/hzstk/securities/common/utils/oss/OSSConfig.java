package cn.hzstk.securities.common.utils.oss;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * Created by allenwc on 16/4/28.
 */
public class OSSConfig {

    protected static final Logger logger = Logger.getLogger(OSSConfig.class);
    public static OSSConfig INSTANCE = new OSSConfig();
    private Properties prop = new Properties();

    protected OSSConfig() {
        try {
            this.prop.load(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("oss.properties"), "UTF-8"));
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

    public String getEndPoint() {
        return this.getValue("endpoint");
    }

    public String getAccessKeyId() {
        return this.getValue("accessKeyId");
    }

    public String getAccessKeySecret() {
        return this.getValue("accessKeySecret");
    }

    public String getOssDomain(String category) {
        if(category.equals("file")){
            return this.getValue("url_file");
        }else{
            return this.getValue("url_img");
        }
    }

    public String getImgBucket() {
        return this.getValue("bucket_img");
    }

    public String getFileBucket() {
        return this.getValue("bucket_file");
    }

    public boolean isDebug() {
        return this.getValue("debug", false);
    }

    public String format(String key, String... value) {
        return MessageFormat.format(this.getValue(key), value);
    }


}
