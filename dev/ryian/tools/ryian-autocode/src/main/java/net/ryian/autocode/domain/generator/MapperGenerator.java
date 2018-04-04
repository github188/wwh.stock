package net.ryian.autocode.domain.generator;

import net.ryian.autocode.conf.Config;
import net.ryian.autocode.conf.GeneratorConfig;
import net.ryian.autocode.conf.Table;
import net.ryian.autocode.util.IOUtil;
import net.ryian.autocode.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class MapperGenerator extends Generator {

    private Map<String, Object> generatorContext;

    public MapperGenerator(Config config, GeneratorConfig gConfig, Table table) {
        super(config, gConfig, table);
        generatorContext = new HashMap<String, Object>();
        generatorContext.put("domain", this.targetPackage + "." + table.getModuleName() + ".domain." + table.getClassName());
        this.targetPackage = this.targetPackage + "." + table.getModuleName() + ".mapper";
    }

    private String getPath() {
        StringBuffer buf = new StringBuffer();
        buf.append(targetProject).append("/src/main/java/")
                .append(StringUtil.packge2path(targetPackage)).append("/");
        buf.append(table.getClassName());
        buf.append("Mapper.java");
        return buf.toString();
    }

    @Override
    public void generate() {
        try {
            System.out.print("Generating [mapper] class...");
            String code = mergeTemplate("/template/mapper.vm", generatorContext);
            IOUtil.writeCodeFile(getPath(), code);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Generate fail：" + e.getMessage());
        }
    }

}
