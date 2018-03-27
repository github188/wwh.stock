package com.skoo.stock.common.tag;

import com.skoo.stock.sys.domain.Dict;
import com.skoo.stock.sys.service.DictService;
import com.skoo.core.utils.SpringContextUtil;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * 数据字典标签  根据 key_name 和 value 获取content
 *
 * @author gomiten@163.com 2014年6月12日
 */
public class DicContent extends Directive {

    private final static String NAME = "dicContent";

    private final static DictService dictService = SpringContextUtil.getBean(DictService.class);

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getType() {
        return LINE;
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node)
            throws IOException, ResourceNotFoundException, ParseErrorException,
            MethodInvocationException {
        try {
            String keyName = String.valueOf(node.jjtGetChild(0).value(context));
            String value = String.valueOf(node.jjtGetChild(1).value(context));

            Map<String, Dict> dicDomain = dictService.getDictsByKey(keyName);

            writer.write(dicDomain.get(value).getContent());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getCodeName(String keyName, String code) {
        Map<String, Dict> dicDomain = dictService.getDictsByKey(keyName);
        if (dicDomain == null) {
            return "";
        } else if (dicDomain.get(code) == null) {
            return "";
        }
        return dicDomain.get(code).getContent();
    }

}
