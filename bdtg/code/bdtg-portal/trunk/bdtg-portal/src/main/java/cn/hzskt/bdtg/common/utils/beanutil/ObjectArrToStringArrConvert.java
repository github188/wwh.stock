package cn.hzskt.bdtg.common.utils.beanutil;

import org.apache.commons.lang3.StringUtils;

public class ObjectArrToStringArrConvert implements Convert<Object[], String[]> {

    @Override
    public String[] convert(Object[] source) {
        if (source == null || StringUtils.isEmpty(source.toString())) {
            return null;
        } else {
            String[] res = new String[source.length];
            for (int i = 0; i < source.length; i++) {
                res[i] = source[i].toString();
            }
            return res;
        }
    }

}