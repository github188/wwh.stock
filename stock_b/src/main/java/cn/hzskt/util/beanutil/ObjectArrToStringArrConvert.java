package cn.hzskt.util.beanutil;

public class ObjectArrToStringArrConvert implements Convert<Object[], String[]> {

    @Override
    public String[] convert(Object[] source) {
        if (source == null)
            return null;
        String[] res = new String[source.length];
        for (int i = 0; i < source.length; i++) {
            res[i] = source[i].toString();
        }
        return res;
    }

}