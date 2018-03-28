package cn.hzskt.util.beanutil;

import org.apache.commons.logging.LogFactory;

public class ObjectArrToDoublArrConvert implements Convert<Object[], double[]> {

    @Override
    public double[] convert(Object[] source) {
        if (source == null)
            return null;
        double[] res = new double[source.length];
        for (int i = 0; i < source.length; i++) {
            try {
                res[i] = Double.parseDouble(source[i].toString());
            } catch (NumberFormatException e) {
                LogFactory.getLog(getClass()).info(
                        "ObjectArrToDoublArrConvert failed, bad value: "
                                + source[i].toString(), e);
                return null;
            }
        }
        return res;
    }
}
