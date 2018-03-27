package cn.hzstk.securities.common.utils.beanutil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;

public class ObjectToLongConvert implements Convert<Object, Long> {

    @Override
    public Long convert(Object source) {
        if (source == null || StringUtils.isEmpty(source.toString())) {
            return null;
        } else {
            try {
                return Long.parseLong(source.toString().trim());
            } catch (NumberFormatException e) {
                LogFactory.getLog(getClass()).info("ObjectToLongConvert failed: " + source, e);
                return null;
            }
        }
    }

}  