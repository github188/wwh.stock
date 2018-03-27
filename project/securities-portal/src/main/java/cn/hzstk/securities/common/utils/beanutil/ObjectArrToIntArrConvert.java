package cn.hzstk.securities.common.utils.beanutil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;

public class ObjectArrToIntArrConvert implements Convert<Object[], int[]> {

    @Override
    public int[] convert(Object[] source) {
        if (source == null || StringUtils.isEmpty(source.toString())) {
            return null;
        } else {
            int[] res = new int[source.length];
            for (int i = 0; i < source.length; i++) {
                try {
                    res[i] = Integer.parseInt(source[i].toString());
                } catch (NumberFormatException e) {
                    LogFactory.getLog(getClass()).info(
                            "ObjectArrToIntArrConvert failed, bad value: "
                                    + source[i].toString(), e);
                    return null;
                }
            }
            return res;
        }
    }

}