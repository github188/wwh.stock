package cn.hzskt.bdtg.common.utils.beanutil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 支持的日期格式为：<br>
 * yyyy-MM-dd<br>
 * yyyy-MM-dd HH:mm<br>
 * yyyy-MM-dd HH:mm:ss<br>
 * yyyy/MM/dd<br>
 * yyyy/MM/dd HH:mm<br>
 * yyyy/MM/dd HH:mm:ss<br>
 */
public class ObjectToDateConvert implements Convert<Object, Date> {

    Object[][] patterns = {
            {Pattern.compile("^\\d+-\\d+-\\d+$"), "yyyy-MM-dd"},
            {Pattern.compile("^\\d+-\\d+-\\d+ \\d+:\\d+$"), "yyyy-MM-dd HH:mm"},
            {Pattern.compile("^\\d+-\\d+-\\d+ \\d+:\\d+:\\d+$"), "yyyy-MM-dd HH:mm:ss"},
            {Pattern.compile("^\\d+/\\d+/\\d+$"), "yyyy/MM/dd"},
            {Pattern.compile("^\\d+/\\d+/\\d+ \\d+:\\d+$"), "yyyy/MM/dd HH:mm"},
            {Pattern.compile("^\\d+/\\d+/\\d+ \\d+:\\d+:\\d+$"), "yyyy/MM/dd HH:mm:ss"}
    };

    @Override
    public Date convert(Object source) {
        if (source == null || StringUtils.isEmpty(source.toString())) {
            return null;
        } else {
            String val = source.toString();
            val = val.trim();

            String format = null;
            Pattern p;
            for (Object[] item : patterns) {
                p = (Pattern) item[0];
                if (p.matcher(val).matches()) {
                    format = item[1].toString();
                    break;
                }
            }

            if (format == null) {
                LogFactory.getLog(getClass()).info("ObjectToDateConvert failed, unsupport value: " + source);
                return null;
            }

            try {
                return new SimpleDateFormat(format).parse(val);
            } catch (ParseException e) {
                LogFactory.getLog(getClass()).info("ObjectToDateConvert failed, bad value: " + source, e);
                return null;
            }
        }
    }

}  