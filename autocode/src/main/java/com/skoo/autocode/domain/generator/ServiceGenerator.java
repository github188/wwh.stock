package com.skoo.autocode.domain.generator;

import com.skoo.autocode.conf.Config;
import com.skoo.autocode.conf.GeneratorConfig;
import com.skoo.autocode.conf.Table;
import com.skoo.autocode.util.IOUtil;
import com.skoo.autocode.util.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ServiceGenerator extends Generator {

    private Map<String, Object> generatorContext;

    public ServiceGenerator(Config config, GeneratorConfig gConfig, Table table) {
        super(config, gConfig, table);
        generatorContext = new HashMap<String, Object>();
        generatorContext.put("domain",
                this.targetPackage + "." + table.getModuleName() + ".domain."
                        + table.getClassName());
        this.targetPackage = this.targetPackage + "." + table.getModuleName()
                + ".service";
    }

    private String getPath() {
        StringBuffer buf = new StringBuffer();
        buf.append(targetProject).append("/src/main/java/")
                .append(StringUtil.packge2path(targetPackage)).append("/");
        buf.append(table.getClassName());
        buf.append("Service.java");
        return buf.toString();
    }

    private String getTestPath() {
        StringBuffer buf = new StringBuffer();
        buf.append(targetProject).append("/src/test/java/")
                .append(StringUtil.packge2path(targetPackage)).append("/");
        buf.append(table.getClassName());
        buf.append("ServiceTest.java");
        return buf.toString();
    }

    @Override
    public void generate() {
        try {
            System.out.print("Generating [service] class...");
            String code = mergeTemplate(StringUtils.defaultIfEmpty(
                            gConfig.getTemplate(), "/template/service.vm"),
                    generatorContext);
            IOUtil.writeCodeFile(getPath(), code);
            System.out.print("Generating [service test] class...");
            code = mergeTemplate(StringUtils.defaultIfEmpty(
                            gConfig.getTemplate(), "/template/serviceTest.vm"),
                    generatorContext);
            IOUtil.writeCodeFile(getTestPath(), code);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Generate fail：" + e.getMessage());
        }
    }

}
