package cn.hzskt.bdtg.common.utils.beanutil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;

public class ObjectArrToDoublArrConvert implements Convert<Object[], double[]> {

    @Override
    public double[] convert(Object[] source) {
        if (source == null || StringUtils.isEmpty(source.toString())) {
            return null;
        } else {
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
}
