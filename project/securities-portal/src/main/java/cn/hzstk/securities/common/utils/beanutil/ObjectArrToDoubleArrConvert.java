package cn.hzstk.securities.common.utils.beanutil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;

public class ObjectArrToDoubleArrConvert implements Convert<Object[], Double[]> {

    @Override
    public Double[] convert(Object[] source) {
        if (source == null || StringUtils.isEmpty(source.toString())) {
            return null;
        } else {
            Double[] res = new Double[source.length];
            for (int i = 0; i < source.length; i++) {
                try {
                    res[i] = Double.parseDouble(source[i].toString());
                } catch (NumberFormatException e) {
                    LogFactory.getLog(getClass()).info(
                            "ObjectArrToDoubleArrConvert failed, bad value: "
                                    + source[i].toString(), e);
                    return null;
                }
            }
            return res;
        }
    }

}