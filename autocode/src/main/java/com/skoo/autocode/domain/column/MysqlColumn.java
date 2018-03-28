package com.skoo.autocode.domain.column;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class MysqlColumn extends Column {

    static Map<String, String> map;

    static {
        map = new HashMap<String, String>();
        map.put("CHAR", "String");
        map.put("VARCHAR", "String");
        map.put("BLOB", "String");
        map.put("TEXT", "String");
        map.put("LONGTEXT", "String");
        map.put("ENUM", "String");

        map.put("FLOAT", "Double");
        map.put("REAL", "Double");
        map.put("DOUBLE", "Double");
        map.put("PRECISION", "Integer");
        map.put("NUMERIC", "Integer");
        map.put("DECIMAL", "java.math.BigDecimal");
        map.put("TINYINT", "Integer");
        map.put("SMALLINT", "Integer");
        map.put("INT", "Integer");
        map.put("MEDIUMINT", "Integer");
        map.put("INTEGER", "Integer");
        map.put("BIGINT", "Long");

        map.put("DATE", "java.util.Date");
        map.put("TIME", "java.util.Date");
        map.put("DATETIME", "java.util.Date");
        map.put("TIMESTAMP", "java.util.Date");
    }

    @Override
    public String getFieldType() {
        if (StringUtils.isBlank(this.getDataType())) {
            return null;
        }
        return map.get(this.getDataType().toUpperCase());
    }

}
