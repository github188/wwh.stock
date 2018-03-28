package com.skoo.autocode.domain.generator;

import com.skoo.autocode.conf.Config;
import com.skoo.autocode.conf.GeneratorConfig;
import com.skoo.autocode.conf.Table;
import com.skoo.autocode.util.IOUtil;
import com.skoo.autocode.util.StringUtil;
import com.skoo.commons.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class PageGenerator extends Generator {

    private Map<String, Object> generatorContext;
    private String indexPageTemplate;
    private String addPageTemplate;
    private String editPageTemplate;

    public PageGenerator(Config config, GeneratorConfig gConfig, Table table) {
        super(config, gConfig, table);
        String template = gConfig.getTemplate();
        if (!StringUtils.isEmpty(template) && template.contains(",") && template.split(",").length == 3) {
            String[] templates = template.split(",");
            indexPageTemplate = templates[0];
            addPageTemplate = templates[1];
            editPageTemplate = templates[2];
        } else {
            indexPageTemplate = "/template/pageIndex.vm";
            addPageTemplate = "/template/pageAdd.vm";
            editPageTemplate = "/template/pageEdit.vm";
        }
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
            String code = mergeTemplate(indexPageTemplate, generatorContext);
            IOUtil.writeCodeFile(getIndexPath(), code);
            System.out.print("Generating [page] add.vm file...");
            code = mergeTemplate(addPageTemplate, generatorContext);
            IOUtil.writeCodeFile(getAddPath(), code);
            System.out.print("Generating [page] edit file...");
            code = mergeTemplate(editPageTemplate, generatorContext);
            IOUtil.writeCodeFile(getEditPath(), code);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Generate fail：" + e.getMessage());
        }
    }

}
