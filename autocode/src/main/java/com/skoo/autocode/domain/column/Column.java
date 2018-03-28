package com.skoo.autocode.domain.column;

import com.skoo.autocode.util.StringUtil;

/**
 * <p>
 * 内容摘要: 数据库列相关信息
 * </p>
 * <p>
 * 完成日期: 2013年9月7日 下午5:09:03
 * </p>
 * <p>
 * 修改记录:
 * </p>
 * <p/>
 * <pre>
 *    修改日期:
 *    修 改 人:
 *    修改内容:
 * </pre>
 *
 * @author gomiten@163.com
 */
public abstract class Column {

    private String columnName;
    private String columnType;
    private String columnDateType;
    private String columnKey;
    private String dataType;
    private String columnCommnet;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnDateType() {
        return columnDateType;
    }

    public void setColumnDateType(String columnDateType) {
        this.columnDateType = columnDateType;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getColumnCommnet() {
        return columnCommnet;
    }

    public void setColumnCommnet(String columnCommnet) {
        this.columnCommnet = columnCommnet;
    }

    public String getFieldName() {
        return StringUtil.toCamel(columnName, false);
    }

    public String getGetName() {
        return StringUtil.toCamel("get_" + columnName, false);
    }

    public String getSetName() {
        return StringUtil.toCamel("set_" + columnName, false);
    }

    abstract public String getFieldType();

}
