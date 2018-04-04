#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' )
        package ${package}.sys.service;

        import net.ryian.commons.StringUtils;
        import ${package}.sys.domain.Permission;
        import org.apache.shiro.config.Ini;
        import org.springframework.beans.factory.FactoryBean;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Component;

        import java.text.MessageFormat;
        import java.util.List;

/**
 * Created by allenwc on 14/11/13.
 */
public class DBChainDefinition implements FactoryBean<Ini.Section> {

    @Autowired
    private PermissionService permissionService;

    private String filterChainDefinitions;

    public static final String PERMISSION_STRING = "perms[${symbol_escape}"

    {
        0
    }

    $

    {
        symbol_escape
    }

    "]";


    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }

    @Override
    public Ini.Section getObject() throws Exception {
        List<Permission> permissions = permissionService.getAll();
        Ini ini = new Ini();
        ini.load(filterChainDefinitions);
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        for (Permission permission : permissions) {
            if (!StringUtils.isEmpty(permission.getUrl()) && !StringUtils.isEmpty(permission.getCode())) {
                section.put(permission.getUrl(), MessageFormat.format(PERMISSION_STRING, permission.getCode()));
            }
        }
        return section;
    }

    @Override
    public Class<?> getObjectType() {
        return this.getClass();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
