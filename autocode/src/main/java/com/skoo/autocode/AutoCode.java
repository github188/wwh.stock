package com.skoo.autocode;

import com.skoo.autocode.conf.Config;
import com.skoo.autocode.conf.Table;
import com.skoo.autocode.dao.JdbcDao;
import com.skoo.autocode.domain.column.Column;
import com.skoo.autocode.domain.generator.Generator;
import com.skoo.autocode.util.ConfUtil;
import com.skoo.autocode.util.StringUtil;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <p>
 * 内容摘要: 代码生成执行类
 * </p>
 * <p>
 * 完成日期: 2013年9月7日 下午5:00:23
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
public class AutoCode {
    /**
     * @param configPath
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws SQLException
     * @date：2013年9月7日
     * @Description：方法功能描述
     */
    public static void compiler(String configPath)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, SQLException {
        if (null == configPath || "".equals(configPath)) {
            configPath = "/config.xml";
        }
        Config config = ConfUtil.parse(AutoCode.class
                .getResourceAsStream(configPath));
        // jdbc info
        String url = config.getJdbcConnection().getConnectionURL();
        String userId = config.getJdbcConnection().getUserId();
        String password = config.getJdbcConnection().getPassword();
        String driver = config.getJdbcConnection().getDriverClass();
        String infSql = config.getJdbcConnection().getInfSql();
        JdbcDao columnDao = new JdbcDao(url, userId, password, driver);

        Map<String, Object> generatorContext = new HashMap<String, Object>();

        for (Table table : config.getTables()) {
            table.setColumns(columnDao.getColumns(infSql,
                    table.getSchema(), table.getTableName()));
            table.setFieldName(StringUtil.toCamel(table.getTableName(), false));
//			addFields(columns);
//			addTableId(table, columns);
            for (Generator generator : config.getGeneratorList(table)) {
                generator.generate();
//				String targetPackage = generator.getTargetPackage();
//				String targetTemplate = generator.getTemplate();
//				String generatorType = generator.getType();
//
//				String code = null;
//				String classFullName = targetPackage + "."
//						+ table.getClassName();
//				generatorContext.put(generatorType, classFullName);
//				if (Generator.TYPE_PAGE.equals(generatorType)) {
//					generatorContext.put("actionUrl", new SEOActionNameBuilder(
//							"true", "-").build(table.getClassName()));
//				}
//
//				String path = generator.getPath(table.getClassName());
//				try {
//					code = mergeTemplate(targetTemplate, targetPackage, table,
//							columns, generatorContext);
////					IOUtil.writeCodeFile(path, code);
//				} catch (Exception e) {
//					// FIXME:ADD LOG
//					e.printStackTrace();
//				}
            }
        }

    }

    private static String mergeTemplate(String targetTemplate,
                                        String targetPackage, Table table, List<Column> columns,
                                        Map<String, Object> generatorContext) throws Exception {
        Properties p = new Properties();
        p.setProperty("resource.loader", "class");
        p.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        Velocity.init(p);
        VelocityContext context = new VelocityContext();
        context.put("columns", columns);
        context.put("targetPackage", targetPackage);
        context.put("tableName", table.getTableName());
        context.put("className", table.getClassName());
        context.put("idFieldName", table.getIdFieldName());
        context.put("idFieldGetName", table.getIdFieldGetName());
        context.put("idColumnName", table.getIdCoulmnName());
        context.put("generatorContext", generatorContext);

        StringWriter w = new StringWriter();
        Velocity.mergeTemplate(targetTemplate, "UTF-8", context, w);

        return w.getBuffer().toString();
    }

    /**
     * @date：2013年9月7日
     * @Description：添加方法
     * @param columns
     */
//	private static void addFields(List<Column> columns) {
//		for (Column column : columns) {
//			column.setFieldName(StringUtil.toCamel(column.getColumnName(),
//					false));
//			column.setSetName(StringUtil.toCamel(
//					"set_" + column.getColumnName(), false));
//			column.setGetName(StringUtil.toCamel(
//					"get_" + column.getColumnName(), false));
//		}
//	}

    /**
     * @param table
     * @param columns
     * @date：2013年9月7日
     * @Description：查找表的主键字段
     */
    public static void addTableId(Table table, List<Column> columns) {
        for (Column column : columns) {
            if (!StringUtil.isBlank(column.getColumnKey())) {
                table.setIdFieldName(StringUtil.toCamel(column.getColumnName(),
                        false));
                table.setIdCoulmnName(column.getColumnName());
            }
        }
    }

    /**
     * @param args
     * @date：2013年9月7日
     * @Description：程序入口
     */
    public static void main(String[] args) {

        try {
            compiler(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
