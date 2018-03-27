package com.skoo.stock.util.beanutil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ConvertFactory {

    private static Log log = LogFactory.getLog(ConvertFactory.class);

    public static Map<String, Convert<?, ?>> convertHandlers = new HashMap<String, Convert<?, ?>>();

    /**
     * 注册转换器
     */
    static {
        convertHandlers.put(String[].class.getName() + "To" + Float[].class.getName(), new ObjectArrToFloatArrConvert());
        convertHandlers.put(String[].class.getName() + "To" + float[].class.getName(), new ObjectArrToFloaArrConvert());

        convertHandlers.put(String[].class.getName() + "To" + Double[].class.getName(), new ObjectArrToDoubleArrConvert());
        convertHandlers.put(String[].class.getName() + "To" + double[].class.getName(), new ObjectArrToDoublArrConvert());

        convertHandlers.put(String[].class.getName() + "To" + Integer[].class.getName(), new ObjectArrToIntegerArrConvert());
        convertHandlers.put(String[].class.getName() + "To" + int[].class.getName(), new ObjectArrToIntArrConvert());

        convertHandlers.put(String.class.getName() + "To" + Date.class.getName(), new ObjectToDateConvert());

        convertHandlers.put(String.class.getName() + "To" + Double.class.getName(), new ObjectToDoubleConvert());
        convertHandlers.put(String.class.getName() + "To" + double.class.getName(), new ObjectToDoubleConvert());

        convertHandlers.put(String.class.getName() + "To" + Float.class.getName(), new ObjectToFloatConvert());
        convertHandlers.put(String.class.getName() + "To" + float.class.getName(), new ObjectToFloatConvert());

        convertHandlers.put(String.class.getName() + "To" + Long.class.getName(), new ObjectToLongConvert());
        convertHandlers.put(String.class.getName() + "To" + long.class.getName(), new ObjectToLongConvert());

        convertHandlers.put(String.class.getName() + "To" + Integer.class.getName(), new ObjectToIntegerConvert());
        convertHandlers.put(String.class.getName() + "To" + int.class.getName(), new ObjectToIntegerConvert());

        Set<Entry<String, Convert<?, ?>>> entites = convertHandlers.entrySet();
        StringBuilder b = new StringBuilder();
        b.append("{");
        for (Entry<String, Convert<?, ?>> entry : entites) {
            b.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
        }
        b.append("}");
        log.debug("all support convert type: " + b.toString().replaceFirst(",}", "}"));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T convert(Class<T> clazz, Object val) {
        Convert cv = convertHandlers.get(val.getClass().getName() + "To" + clazz.getName());
        if (cv == null) {
            log.info(clazz.getName() + "To" + val.getClass().getName() + " convert failed: unsupport type");
            return null;
        }
        return (T) cv.convert(val);
    }
}