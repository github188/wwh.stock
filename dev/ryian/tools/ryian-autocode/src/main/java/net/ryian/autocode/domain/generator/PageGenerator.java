package net.ryian.autocode.domain.generator;

import net.ryian.autocode.conf.Config;
import net.ryian.autocode.conf.GeneratorConfig;
import net.ryian.autocode.conf.Table;
import net.ryian.autocode.util.IOUtil;
import net.ryian.autocode.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class PageGenerator extends Generator {

    private Map<String, Object> generatorContext;

    public PageGenerator(Config config, GeneratorConfig gConfig, Table table) {
        super(config, gConfig, table);
        generatorContext = new HashMap<String, Object>();
        generatorContext.put("module", table.getModuleName());
        this.targetPackage = table.getModuleName();
    }

    private String getIndexPath() {
        StringBuffer buf = new StringBuffer();
        buf.append(targetProject).append("/src/main/webapp/WEB-INF/content/")
                .append(StringUtil.packge2path(targetPackage)).append("/");
        buf.append(table.getUrlName()).append("/").append("index");
        buf.append(".vm");
        return buf.toString();
    }

    private String getAddPath() {
        StringBuffer buf = new StringBuffer();
        buf.append(targetProject).append("/src/main/webapp/WEB-INF/content/")
                .append(StringUtil.packge2path(targetPackage)).append("/");
        buf.append(table.getUrlName()).append("/").append("add");
        buf.append(".vm");
        return buf.toString();
    }

    private String getEditPath() {
        StringBuffer buf = new StringBuffer();
        buf.append(targetProject).append("/src/main/webapp/WEB-INF/content/")
                .append(StringUtil.packge2path(targetPackage)).append("/");
        buf.append(table.getUrlName()).append("/").append("edit");
        buf.append(".vm");
        return buf.toString();
    }

    @Override
    public void generate() {
        try {
            System.out.print("Generating [page] index.vm file...");
            String code = mergeTemplate("/template/pageIndex.vm", generatorContext);
            IOUtil.writeCodeFile(getIndexPath(), code);
            System.out.print("Generating [page] add.vm file...");
            code = mergeTemplate("/template/pageAdd.vm", generatorContext);
            IOUtil.writeCodeFile(getAddPath(), code);
            System.out.print("Generating [page] edit file...");
            code = mergeTemplate("/template/pageEdit.vm", generatorContext);
            IOUtil.writeCodeFile(getEditPath(), code);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Generate fail：" + e.getMessage());
        }
    }

}
