package com.skoo.autocode.dao;

import com.skoo.autocode.domain.column.Column;
import com.skoo.autocode.domain.column.MysqlColumn;
import com.skoo.autocode.domain.column.OracleColumn;
import com.skoo.autocode.util.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 内容摘要: 数据访问对象
 * </p>
 * <p>
 * 完成日期: 2013年9月7日 下午5:09:45
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
public class JdbcDao {
    String url;
    String userName;
    String password;
    String driver;
    Connection con;
    List<String> excludeColumns;
    private String dbType;

    /**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description: 构建dao
     * </p>
     *
     * @param url
     * @param userName
     * @param password
     * @param driver
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws SQLException
     */
    public JdbcDao(String url, String userName, String password, String driver)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, SQLException {

        excludeColumns = new ArrayList<String>();
        excludeColumns.add("id");
        excludeColumns.add("creator");
        excludeColumns.add("create_date");
        excludeColumns.add("updator");
        excludeColumns.add("update_date");
        excludeColumns.add("valid");

        this.url = url;
        this.userName = userName;
        this.password = password;
        this.driver = driver;
        Class.forName(driver);
        Class.forName(driver).newInstance();
        this.con = DriverManager.getConnection(url, userName, password);
        if ("oracle.jdbc.driver.OracleDriver".equals(driver)) {
            this.dbType = "Oracle";
        } else if ("com.mysql.jdbc.Driver".equals(driver)) {
            this.dbType = "Mysql";
        }
    }

    public List<Column> getColumns(String sql, String schema, String tableName)
            throws SQLException {

        List<Column> columns = new ArrayList<Column>();

        Statement stmt = con.createStatement();

        if (StringUtil.isBlank(tableName)) {
            throw new java.lang.IllegalArgumentException(
                    "tableName is can not Empty!");
        }
        sql = sql.replaceAll("\\$\\{TABLE_NAME\\}", tableName);
        if (!StringUtil.isBlank(schema)) {
            sql = sql.replaceAll("\\$\\{TABLE_SCHEMA\\}", schema);
        }
        //
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (null == rs) {
                return columns;
            }
            while (rs.next()) {
                String columnName = StringUtils.lowerCase(rs.getString("COLUMN_NAME"));
                if (!excludeColumns.contains(columnName)) {
                    Column column = newColumn();
                    column.setColumnCommnet(rs.getString("COLUMN_COMMENT"));
                    column.setColumnName(columnName);
                    column.setColumnType(rs.getString("COLUMN_TYPE"));
                    column.setDataType(rs.getString("DATA_TYPE"));
                    column.setColumnKey(rs.getString("COLUMN_KEY"));
                    columns.add(column);
                }
            }
        } finally {
            stmt.close();
        }
        return columns;
    }

    public Column newColumn() {
        if ("Oracle".equals(this.dbType)) {
            return new OracleColumn();
        } else if ("Mysql".equals(this.dbType)) {
            return new MysqlColumn();
        }
        return null;
    }
}
