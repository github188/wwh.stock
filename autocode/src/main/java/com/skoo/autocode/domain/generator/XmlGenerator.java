package com.skoo.autocode.domain.generator;

import com.skoo.autocode.conf.Config;
import com.skoo.autocode.conf.GeneratorConfig;
import com.skoo.autocode.conf.Table;
import com.skoo.autocode.util.IOUtil;
import com.skoo.autocode.util.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class XmlGenerator extends Generator {

    private Map<String, Object> generatorContext;

    public XmlGenerator(Config config, GeneratorConfig gConfig, Table table) {
        super(config, gConfig, table);
        generatorContext = new HashMap<String, Object>();
        generatorContext.put("domain", this.targetPackage + "." + table.getModuleName() + ".domain." + table.getClassName());
        this.targetPackage = table.getModuleName();
    }

    private String getPath() {
        StringBuffer buf = new StringBuffer();
        buf.append(targetProject).append("/src/main/resources/mybatis/mapper/")
                .append(StringUtil.packge2path(targetPackage)).append("/");
        buf.append(table.getClassName());
        buf.append(".xml");
        return buf.toString();
    }

    @Override
    public void generate() {
        try {
            System.out.print("Generating [mapper] file...");
            String code = mergeTemplate(StringUtils.defaultIfEmpty(gConfig.getTemplate(), "/template/mapXml.vm"), generatorContext);
            IOUtil.writeCodeFile(getPath(), code);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Generate fail：" + e.getMessage());
        }
    }

}
