package cn.hzstk.securities.common.utils.beanutil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;

public class ObjectToDoubleConvert implements Convert<Object, Double> {

    @Override
    public Double convert(Object source) {
        if (source == null || StringUtils.isEmpty(source.toString())) {
            return null;
        } else {
            try {
                return Double.parseDouble(source.toString().trim());
            } catch (NumberFormatException e) {
                LogFactory.getLog(getClass()).info("ObjectToDoubleConvert failed: " + source, e);
                return null;
            }
        }
    }

}  