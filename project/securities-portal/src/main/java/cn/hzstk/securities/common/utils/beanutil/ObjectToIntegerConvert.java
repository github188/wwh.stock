package cn.hzstk.securities.common.utils.beanutil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;

public class ObjectToIntegerConvert implements Convert<Object, Integer> {

    @Override
    public Integer convert(Object source) {
        if (source == null || StringUtils.isEmpty(source.toString())) {
            return null;
        } else {
            try {
                return Integer.parseInt(source.toString().trim());
            } catch (NumberFormatException e) {
                LogFactory.getLog(getClass()).info("ObjectToIntegerConvert failed: " + source, e);
                return null;
            }
        }
    }

}  