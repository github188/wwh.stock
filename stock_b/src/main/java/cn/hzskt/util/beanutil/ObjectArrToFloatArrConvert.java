package cn.hzskt.util.beanutil;

import org.apache.commons.logging.LogFactory;

public class ObjectArrToFloatArrConvert implements Convert<Object[], Float[]> {

    @Override
    public Float[] convert(Object[] source) {
        if (source == null)
            return null;
        Float[] res = new Float[source.length];
        for (int i = 0; i < source.length; i++) {
            try {
                res[i] = Float.parseFloat(source[i].toString());
            } catch (NumberFormatException e) {
                LogFactory.getLog(getClass()).info(
                        "ObjectArrToFloatArrConvert failed, bad value: "
                                + source[i].toString(), e);
                return null;
            }
        }
        return res;
    }

}