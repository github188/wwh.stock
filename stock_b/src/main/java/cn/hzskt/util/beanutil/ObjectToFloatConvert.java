package cn.hzskt.util.beanutil;

import org.apache.commons.logging.LogFactory;

public class ObjectToFloatConvert implements Convert<Object, Float> {

    @Override
    public Float convert(Object source) {
        if (source == null) return null;
        try {
            return Float.parseFloat(source.toString().trim());
        } catch (NumberFormatException e) {
            LogFactory.getLog(getClass()).info("ObjectToFloatConvert failed: " + source, e);
            return null;
        }
    }

}  