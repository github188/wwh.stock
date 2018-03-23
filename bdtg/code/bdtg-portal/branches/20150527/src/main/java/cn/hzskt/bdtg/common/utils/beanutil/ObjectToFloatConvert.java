package cn.hzskt.bdtg.common.utils.beanutil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;

public class ObjectToFloatConvert implements Convert<Object, Float> {

    @Override
    public Float convert(Object source) {
        if (source == null || StringUtils.isEmpty(source.toString())) {
            return null;
        } else {
            try {
                return Float.parseFloat(source.toString().trim());
            } catch (NumberFormatException e) {
                LogFactory.getLog(getClass()).info("ObjectToFloatConvert failed: " + source, e);
                return null;
            }
        }
    }

}  