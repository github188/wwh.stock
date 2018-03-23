package cn.hzskt.bdtg.common.view.tag;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.directive.DirectiveConstants;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by allenwc on 16/6/3.
 */
public class AdDirective extends Directive {
    @Override
    public String getName() {
        return "ad";
    }

    @Override
    public int getType() {
        return DirectiveConstants.LINE;
    }

    @Override
    public boolean render(InternalContextAdapter internalContextAdapter, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        String code = (String)node.jjtGetChild(0).value(internalContextAdapter);
        //根据code查询有效的广告,将广告中的图片地址以及链接拼装进行输出
        StringBuilder html = new StringBuilder();
        html.append("<a class='imgLink' href='#'>");
        html.append("<img src='").append("http://101.201.33.18:8081/static/newindex/images/banner2.png").append("'>");
        html.append("</a>");
        writer.write(html.toString());
        return true;
    }
}
