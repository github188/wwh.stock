package cn.hzskt.bdtg.common.message;

import cn.hzskt.bdtg.common.util.WebUtil;
import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

    public String getTopicJobStatus() {
        return this.getValue("ons.topic_job_status");
    }

    public boolean isDebug() {
        return this.getValue("debug", false);
    }

    public String format(String key, String... value) {
        return MessageFormat.format(this.getValue(key), value);
    }

    /**
     *
     * @param producer
     * @param request
     * @param content 操作具体内容
     * @param type (A:成员操作；B：主项操作；C：职责分配；D：项目实施；E：发起完工；F：确认完工；G：点评)
     */
    public static void sendJobStatusMessage(Producer producer,HttpServletRequest request,String content,String type) {
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("tid", WebUtil.getTid(request).toString());
            data.put("content", content);
            data.put("statusTime", new Date().toString());
            data.put("creator", WebUtil.getUser(request).getId().toString());
            data.put("createName", WebUtil.getUser(request).getUserName());
            data.put("type", type);
            String topic = INSTANCE.getTopicJobStatus();
            String tags = "";
            String body = JSON.toJSONString(data);
            Message msg = new Message(topic, tags, body.getBytes());
            SendResult result = producer.send(msg);
        }catch (Exception e){
e.printStackTrace();
        }

    }

}
