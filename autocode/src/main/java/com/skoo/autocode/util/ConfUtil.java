package com.skoo.autocode.util;

import com.thoughtworks.xstream.XStream;
import com.skoo.autocode.conf.Config;
import com.skoo.autocode.conf.GeneratorConfig;
import com.skoo.autocode.conf.JdbcConnection;
import com.skoo.autocode.conf.Table;

import java.io.InputStream;

/**
 * <p>
 * 内容摘要:配置解析类
 * </p>
 * <p>
 * 完成日期: 2013年9月7日 下午5:08:37
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
public class ConfUtil {
    /**
     * @param xmlConfig
     * @return
     * @date：2013年9月7日
     * @Description：方法功能描述
     */
    public static Config parse(InputStream xmlConfig) {

        XStream xstream = new XStream();

        xstream.alias("config", Config.class);
        xstream.useAttributeFor(Config.class, "targetPackage");
        xstream.useAttributeFor(Config.class, "targetProject");
        xstream.alias("generator", GeneratorConfig.class);
        xstream.useAttributeFor(GeneratorConfig.class, "targetPackage");
        xstream.useAttributeFor(GeneratorConfig.class, "template");
        xstream.useAttributeFor(GeneratorConfig.class, "type");

        xstream.alias("jdbcconnection", JdbcConnection.class);
        xstream.useAttributeFor(JdbcConnection.class, "connectionURL");
        xstream.useAttributeFor(JdbcConnection.class, "driverClass");
        xstream.useAttributeFor(JdbcConnection.class, "userId");
        xstream.useAttributeFor(JdbcConnection.class, "password");
        xstream.useAttributeFor(JdbcConnection.class, "infSql");

        xstream.alias("table", Table.class);
        xstream.useAttributeFor(Table.class, "schema");
        xstream.useAttributeFor(Table.class, "tableName");
        xstream.useAttributeFor(Table.class, "className");
        xstream.useAttributeFor(Table.class, "fieldName");
        xstream.useAttributeFor(Table.class, "name");

        return (Config) xstream.fromXML(xmlConfig);
    }

    /**
     * @param type
     * @return
     * @date：2013年9月7日
     * @Description：数据库类型到java数据类型转换
     */
    public static String type2Suffix(String type) {
        /*
		 * xml domain dao service action vm
		 */
        if ("xml".equals(type)) {
            return ".xml";
        } else if ("dao".equals(type)) {
            return "Dao.java";
        } else if ("domain".equals(type)) {
            return ".java";
        } else if ("service".equals(type)) {
            return "Service.java";
        } else if ("action".equals(type)) {
            return "Action.java";
        } else if ("page".equals(type)) {
            return ".vm";
        } else {
            return "." + type;
        }
    }
}
