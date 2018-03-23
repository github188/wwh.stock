package cn.hzskt.bdtg.common.utils.beanutil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;

public class ObjectArrToIntegerArrConvert implements
        Convert<Object[], Integer[]> {

    @Override
    public Integer[] convert(Object[] source) {
        if (source == null || StringUtils.isEmpty(source.toString())) {
            return null;
        } else {
            Integer[] res = new Integer[source.length];
            for (int i = 0; i < source.length; i++) {
                try {
                    res[i] = Integer.parseInt(source[i].toString());
                } catch (NumberFormatException e) {
                    LogFactory.getLog(getClass()).info(
                            "ObjectArrToIntegerArrConvert failed, bad value: "
                                    + source[i].toString(), e);
                    return null;
                }
            }
            return res;
        }
    }

}