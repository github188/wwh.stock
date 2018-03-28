package cn.hzskt.util.beanutil;

import org.apache.commons.logging.LogFactory;

public class ObjectToIntegerConvert implements Convert<Object, Integer> {

    @Override
    public Integer convert(Object source) {
        if (source == null) return null;
        try {
            return Integer.parseInt(source.toString().trim());
        } catch (NumberFormatException e) {
            LogFactory.getLog(getClass()).info("ObjectToIntegerConvert failed: " + source, e);
            return null;
        }
    }

}  