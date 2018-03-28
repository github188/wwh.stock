package com.skoo.autocode.domain.generator;

import com.skoo.autocode.conf.Config;
import com.skoo.autocode.conf.GeneratorConfig;
import com.skoo.autocode.conf.Table;
import com.skoo.autocode.util.IOUtil;
import com.skoo.autocode.util.StringUtil;
import com.skoo.commons.StringUtils;

public class DomainGenerator extends Generator {

    public DomainGenerator(Config config, GeneratorConfig gConfig, Table table) {
        super(config, gConfig, table);
        this.targetPackage = this.targetPackage + "." + table.getModuleName() + ".domain";
    }

    private String getPath() {
        StringBuffer buf = new StringBuffer();
        buf.append(targetProject).append("/src/main/java/")
                .append(StringUtil.packge2path(targetPackage)).append("/");
        buf.append(table.getClassName());
        buf.append(".java");
        return buf.toString();
    }

    @Override
    public void generate() {
        try {
            System.out.print("Generating [domain] class...");
            String code = mergeTemplate(StringUtils.defaultString(gConfig.getTemplate(), "/template/domain.vm"), null);
            IOUtil.writeCodeFile(getPath(), code);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Generate fail：" + e.getMessage());
        }
    }
}
