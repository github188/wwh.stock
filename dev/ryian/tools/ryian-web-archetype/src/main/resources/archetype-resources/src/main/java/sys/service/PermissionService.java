#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' )
        package ${package}.sys.service;

        import net.ryian.commons.StringUtils;
        import net.ryian.core.domain.BaseEntity;
        import net.ryian.core.service.BaseService;
        import net.ryian.core.service.support.query.Condition;
        import net.ryian.core.service.support.query.Restrictions;
        import ${package}.sys.domain.Permission;
        import org.apache.ibatis.session.SqlSession;
        import org.springframework.stereotype.Component;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class PermissionService extends BaseService<Permission> {

    @Override
    public Long saveOrUpdate(BaseEntity entity) {
        Permission permission = (Permission) entity;
        Long id = super.saveOrUpdate(permission);
        String pTreePath;
        if (permission.getPid() == 0) {
            pTreePath = "0";
        } else {
            pTreePath = this.get(permission.getPid()).getTreePath();
        }
        permission.setTreePath(pTreePath + "." + Long.valueOf(id));
        return super.saveOrUpdate(permission);
    }

    public List<Permission> getPermissionsByUser(Long userId) {
        List<Permission> allPermissions = this.getAll();
        if (userId == 0) {
            return allPermissions;
        } else {
            List<Permission> permissions = new ArrayList<Permission>();
            List<Permission> thePermissions = this.getSqlSession().selectList("Permission_list_by_user", userId);
            for (Permission permission : thePermissions) {
                for (Permission p : allPermissions) {
                    if (permission.getPid().equals(p.getId())) {
                        if (!permissions.contains(p)) {
                            permissions.add(p);
                        }
                        break;
                    }
                }
                permissions.add(permission);
            }
            return permissions;
        }
    }

    public List<Permission> getPermissionsByPid(Long pid) {
        Condition condition = new Condition();
        condition.createCriteria(Permission.class).add(Restrictions.eq("pid", pid));
        return this.query(condition);
    }

    public List<Permission> getPermissionsByRole(Long roleId) {
        return this.getSqlSession().selectList("Permission_list_by_role", roleId);
    }

    public void saveRolePermissions(Long roleId, String permissions, Long currentUserId) {
        SqlSession session = this.getSqlSession();
        session.delete("Permission_delete_by_role", roleId);
        if (!StringUtils.isEmpty(permissions)) {
            Map map = new HashMap();
            map.put("roleId", roleId);
            map.put("creator", currentUserId);
            for (String permission : permissions.split(",")) {
                map.put("permissionId", Long.valueOf(permission));
                getSqlSession().insert("Permission_insert_by_role", map);
            }
        }
    }

}
