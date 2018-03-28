package com.skoo.autocode.conf;

/**
 * <p>内容摘要: 数据库连接信息</p>
 * <p>完成日期: 2013年9月7日 下午5:11:25</p>
 * <p>修改记录:</p>
 * <pre>
 *    修改日期:
 *    修 改 人:
 *    修改内容:
 * </pre>
 *
 * @author gomiten@163.com
 */
public class JdbcConnection {

    /**
     * jdbc驱动*
     */
    private String driverClass;
    /**
     * 数据库连接url*
     */
    private String connectionURL;
    private String userId;
    private String password;
    /**
     * 查询表信息的sql*
     */
    private String infSql;


    public JdbcConnection() {
        StringBuffer sb = new StringBuffer();
        sb.append("	SELECT	");
        sb.append("	  c.COLUMN_COMMENT AS COLUMN_COMMENT,");
        sb.append("	  c.COLUMN_NAME AS COLUMN_NAME,");
        sb.append("	  c.COLUMN_TYPE AS COLUMN_TYPE,");
        sb.append("	  c.COLUMN_KEY AS COLUMN_KEY,");
        sb.append("	  c.DATA_TYPE AS DATA_TYPE,");
        sb.append("	  c.IS_NULLABLE AS IS_NULLABLE,");
        sb.append("	  c.COLUMN_DEFAULT AS COLUMN_DEFAULT");
        sb.append("	FROM information_schema.COLUMNS c");
        sb.append("	WHERE c.TABLE_NAME = '${TABLE_NAME}'");
        sb.append("	    AND c.TABLE_SCHEMA = '${TABLE_SCHEMA}'");
        infSql = sb.toString();

    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getConnectionURL() {
        return connectionURL;
    }

    public void setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInfSql() {
        return infSql;
    }

    public void setInfSql(String infSql) {
        this.infSql = infSql;
    }

}
