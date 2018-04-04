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
public class ComboBoxDirective extends Directive {
    @Override
    public String getName() {
        return "combobox";
    }

    @Override
    public int getType() {
        return LINE;
    }

    @Override
    public boolean render(InternalContextAdapter internalContextAdapter, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        String name = (String) node.jjtGetChild(0).value(internalContextAdapter);
        Collection collection = (Collection) node.jjtGetChild(1).value(internalContextAdapter);
        Iterator iter = collection.iterator();
        String key = (String) node.jjtGetChild(2).value(internalContextAdapter);
        String value = (String) node.jjtGetChild(3).value(internalContextAdapter);
        int number = node.jjtGetNumChildren();
        String selected = null;
        if (number > 4)
            selected = (String) node.jjtGetChild(4).value(internalContextAdapter);
        StringBuilder html = new StringBuilder();
        html.append("<select class=\"easyui-combobox\"").append(" id=\"").append(name).append("\" name=\"").append(name).append("\" style=\"width:200px;\">\">");
        while (iter.hasNext()) {
            Object o = iter.next();
            try {
                html.append("<option value=\"").append(BeanUtils.getProperty(o, key)).append("\"");
                if (BeanUtils.getProperty(o, key).equals(selected)) {
                    html.append(" selected=\"selected\"");
                }
                html.append(">").append(BeanUtils.getProperty(o, value)).append("</option>");

            } catch (Exception e) {
                ExceptionUtils.unchecked(e);
            }
        }
        html.append("</select>");
        writer.write(html.toString());
        return true;
    }
}
