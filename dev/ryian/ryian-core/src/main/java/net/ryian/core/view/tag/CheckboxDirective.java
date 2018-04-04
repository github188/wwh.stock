package net.ryian.core.view.tag;

import net.ryian.commons.BeanUtils;
import net.ryian.commons.ExceptionUtils;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by allenwc on 14/11/13.
 */
public class CheckboxDirective extends Directive {
    @Override
    public String getName() {
        return "checkbox";
    }

    @Override
    public int getType() {
        return LINE;
    }

    @Override
    public boolean render(InternalContextAdapter internalContextAdapter, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        String name = (String) node.jjtGetChild(0).value(internalContextAdapter);
        String value = (String) node.jjtGetChild(1).value(internalContextAdapter);
        String realValue = (String) node.jjtGetChild(2).value(internalContextAdapter);
        StringBuilder html = new StringBuilder();
        html.append("<input type=\"checkbox\"").append(" name=\"").append(name).append("\" value=\"").append(value).append("\"");
        if(realValue.equals("1") || realValue.equals("true")) {
            html.append(" checked=\"checked\"");
        }
        html.append("</>");
        html.append("<input type=\"hidden\"").append(" name=\"").append(name).append("\"/>");
        writer.write(html.toString());
        return true;
    }
}
