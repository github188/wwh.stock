package com.skoo.autocode.domain.column;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class OracleColumn extends Column {

    static Map<String, String> map;

    static {
        map = new HashMap<String, String>();
        map.put("CHAR", "String");
        map.put("NVARCHAR2", "String");
        map.put("VARCHAR2", "String");
        map.put("BLOB", "String");
        map.put("CLOB", "String");
        map.put("TEXT", "String");
        map.put("ENUM", "String");

        map.put("FLOAT", "Double");
        map.put("REAL", "Double");
        map.put("DOUBLE ", "Double");
        map.put("PRECISION", "Integer");
        map.put("NUMBER", "Integer");
        map.put("DECIMAL", "Integer");
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

    public String getFieldType() {
        if (StringUtils.isBlank(this.getColumnType())) {
            return null;
        }
        return map.get(this.getColumnType().toUpperCase());
    }

}
