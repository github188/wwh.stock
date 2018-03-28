package cn.hzskt.common.tag;

import cn.hzskt.sys.domain.Dict;
import cn.hzskt.sys.service.DictService;
import com.zjhcsoft.smartcity.magic.core.utils.SpringContextUtil;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 数据字典标签  根据 key_name 和 value 获取content
 *
 * @author lujy@zjhcsoft.com 2014年6月12日
 */
public class DicOptions extends Directive {

    private final static String NAME = "dicOptions";

    private String[] showArr = null;

//	@Autowired
    /**
     * 标签未在spring 容器内管理 无法使用autowired
     */
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
            String keyName = (String) node.jjtGetChild(0).value(context);
            String value = null;
            String shows = null;

            if (node.jjtGetNumChildren() > 1) {
                value = (String) node.jjtGetChild(1).value(context);
            }
            if (node.jjtGetNumChildren() > 2) {
                shows = (String) node.jjtGetChild(2).value(context);
                showArr = shows.split(",");
            }
            Map<String, Dict> dicDomain = dictService.getDictsByKey(keyName);
            StringBuilder html = new StringBuilder();

            Iterator<Entry<String, Dict>> dicIter = dicDomain.entrySet().iterator();
            while (dicIter.hasNext()) {
                Entry<String, Dict> entry = dicIter.next();
                if (!isContain(entry.getValue().getValue())) {
                    continue;
                }
                if (value != null && entry.getValue().getValue().equals(value)) {
                    html.append("<option selected=\"selected\" value=\"");
                } else {
                    html.append("<option value=\"");
                }
                html.append(entry.getValue().getValue()).append("\">");
                html.append(entry.getValue().getContent());
                html.append("</option>");
            }
            writer.write(html.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isContain(String str) {
        if (showArr == null) {
            return true;
        } else {
            for (int i = 0; i < showArr.length; i++) {
                if (showArr[i].equals(str)) {
                    return true;
                }
            }
        }

        return false;
    }
}
