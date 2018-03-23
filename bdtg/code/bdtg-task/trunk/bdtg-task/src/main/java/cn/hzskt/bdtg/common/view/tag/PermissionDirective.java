package cn.hzskt.bdtg.common.view.tag;

import cn.hzskt.bdtg.sys.domain.Permission;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

/**
 * Created by allenwc on 16/5/6.
 */
public class PermissionDirective extends Directive {
    @Override
    public String getName() {
        return "permission";
    }

    @Override
    public int getType() {
        return BLOCK;
    }

    @Override
    public boolean render(InternalContextAdapter internalContextAdapter, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        String code = (String)node.jjtGetChild(0).value(internalContextAdapter);
        Collection<Permission> permissions = (Collection<Permission>)node.jjtGetChild(1).value(internalContextAdapter);
        for(Permission permission : permissions) {
            if(code.equals(permission.getCode())) {
                node.jjtGetChild(node.jjtGetNumChildren() - 1).render(internalContextAdapter,writer);
                return true;
            }
        }
        return false;
    }
}
