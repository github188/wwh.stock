package cn.hzskt.util.beanutil;

import org.apache.commons.logging.LogFactory;

public class ObjectToDoubleConvert implements Convert<Object, Double> {

    @Override
    public Double convert(Object source) {
        if (source == null) return null;
        try {
            return Double.parseDouble(source.toString().trim());
        } catch (NumberFormatException e) {
            LogFactory.getLog(getClass()).info("ObjectToDoubleConvert failed: " + source, e);
            return null;
        }
    }

}  