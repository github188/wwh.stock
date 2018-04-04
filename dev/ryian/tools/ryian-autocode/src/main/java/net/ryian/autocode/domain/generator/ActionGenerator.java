package net.ryian.autocode.domain.generator;

import net.ryian.autocode.conf.Config;
import net.ryian.autocode.conf.GeneratorConfig;
import net.ryian.autocode.conf.Table;
import net.ryian.autocode.util.IOUtil;
import net.ryian.autocode.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class ActionGenerator extends Generator {

    private Map<String, Object> generatorContext;

    public ActionGenerator(Config config, GeneratorConfig gConfig, Table table) {
        super(config, gConfig, table);
        generatorContext = new HashMap<String, Object>();
        generatorContext.put("module", table.getModuleName());
        generatorContext.put("domain", this.targetPackage + "." + table.getModuleName() + ".domain." + table.getClassName());
        generatorContext.put("service", this.targetPackage + "." + table.getModuleName() + ".service." + table.getClassName() + "Service");
        this.targetPackage = this.targetPackage + "." + table.getModuleName() + ".action";
    }

    private String getPath() {
        StringBuffer buf = new StringBuffer();
        buf.append(targetProject).append("/src/main/java/")
                .append(StringUtil.packge2path(targetPackage)).append("/");
        buf.append(table.getClassName());
        buf.append("Action.java");
        return buf.toString();
    }

    @Override
    public void generate() {
        try {
            System.out.print("Generating [action] class...");
            String code = mergeTemplate("/template/action.vm", generatorContext);
            IOUtil.writeCodeFile(getPath(), code);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Generate fail：" + e.getMessage());
        }
    }

}
