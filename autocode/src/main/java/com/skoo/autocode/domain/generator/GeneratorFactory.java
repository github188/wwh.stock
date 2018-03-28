package com.skoo.autocode.domain.generator;

import com.skoo.autocode.conf.Config;
import com.skoo.autocode.conf.GeneratorConfig;
import com.skoo.autocode.conf.Table;

public class GeneratorFactory {

    public static Generator createGenerator(Config config, GeneratorConfig gConfig, Table table) throws Exception {
        if (GeneratorConfig.TYPE_DOMAIN.equals(gConfig.getType())) {
            return new DomainGenerator(config, gConfig, table);
        } else if (GeneratorConfig.TYPE_ACTION.equals(gConfig.getType())) {
            return new ActionGenerator(config, gConfig, table);
        } else if (GeneratorConfig.TYPE_SERVICE.equals(gConfig.getType())) {
            return new ServiceGenerator(config, gConfig, table);
        } else if (GeneratorConfig.TYPE_XML.equals(gConfig.getType())) {
            return new XmlGenerator(config, gConfig, table);
        } else if (GeneratorConfig.TYPE_PAGE.equals(gConfig.getType())) {
            return new PageGenerator(config, gConfig, table);
        }
        throw new Exception("不支持的生成器类型：" + gConfig.getType());
    }

}
