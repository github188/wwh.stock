package com.skoo.autocode.domain.generator;

import com.skoo.autocode.conf.Config;
import com.skoo.autocode.conf.GeneratorConfig;
import com.skoo.autocode.conf.Table;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

public abstract class Generator {

    protected GeneratorConfig gConfig;
    protected Table table;

    protected String targetProject;
    protected String targetPackage;
    protected String basePackage;

    public Generator(Config config, GeneratorConfig gConfig, Table table) {
        this.gConfig = gConfig;
        this.table = table;
        this.basePackage = config.getTargetPackage();
        this.targetProject = config.getTargetProject();
        this.targetPackage = config.getTargetPackage();
    }

    protected String mergeTemplate(String targetTemplate, Map<String, Object> generatorContext) throws Exception {
        Properties p = new Properties();
        p.setProperty("resource.loader", "class");
        p.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        Velocity.init(p);
        VelocityContext context = new VelocityContext();
        context.put("table", table);
        context.put("basePackage", basePackage);
        context.put("targetPackage", targetPackage);
        context.put("tableName", table.getTableName());
        context.put("className", table.getClassName());
        context.put("generatorContext", generatorContext);

        StringWriter w = new StringWriter();
        Velocity.mergeTemplate(targetTemplate, "UTF-8", context, w);

        return w.getBuffer().toString();
    }

    public abstract void generate();

}
