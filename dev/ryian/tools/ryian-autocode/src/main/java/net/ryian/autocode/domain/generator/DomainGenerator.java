package net.ryian.autocode.domain.generator;

import net.ryian.autocode.conf.Config;
import net.ryian.autocode.conf.GeneratorConfig;
import net.ryian.autocode.conf.Table;
import net.ryian.autocode.util.IOUtil;
import net.ryian.autocode.util.StringUtil;

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
            String code = mergeTemplate("/template/domain.vm", null);
            IOUtil.writeCodeFile(getPath(), code);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Generate fail：" + e.getMessage());
        }
    }
}
