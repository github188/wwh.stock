package cn.hzskt.util.beanutil;

import org.apache.commons.logging.LogFactory;

public class ObjectArrToFloaArrConvert implements Convert<Object[], float[]> {

    @Override
    public float[] convert(Object[] source) {
        if (source == null)
            return null;
        float[] res = new float[source.length];
        for (int i = 0; i < source.length; i++) {
            try {
                res[i] = Float.parseFloat(source[i].toString());
            } catch (NumberFormatException e) {
                LogFactory.getLog(getClass()).info(
                        "ObjectArrToFloaArrConvert failed, bad value: "
                                + source[i].toString(), e);
                return null;
            }
        }
        return res;
    }

}