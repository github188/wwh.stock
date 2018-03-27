package cn.hzstk.securities.sys.service;

import cn.hzstk.securities.sys.domain.Permission;
import net.ryian.commons.StringUtils;
import org.apache.shiro.config.Ini;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by allenwc on 14/11/13.
 */
public class DBChainDefinition implements FactoryBean<Ini.Section> {

    @Autowired
    private PermissionService permissionService;

    private String filterChainDefinitions;

    public static final String PERMISSION_STRING = "perms[\"{0}\"]";


    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }

    @Override
    public Ini.Section getObject() throws Exception {
        List<Permission> permissions = permissionService.getAll();
        Ini ini = new Ini();
        ini.load(filterChainDefinitions);
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        for(Permission permission : permissions) {
            if(!StringUtils.isEmpty(permission.getUrl()) && !StringUtils.isEmpty(permission.getCode())) {
                section.put(permission.getUrl(), MessageFormat.format(PERMISSION_STRING,permission.getCode()));
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
