#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' )
        package ${package}.sys.service;

        import net.ryian.commons.StringUtils;
        import net.ryian.core.service.BaseService;
        import net.ryian.core.service.support.paging.PageInfo;
        import net.ryian.core.service.support.query.Condition;
        import net.ryian.core.service.support.query.Criteria;
        import net.ryian.core.service.support.query.Restrictions;
        import ${package}.sys.domain.Role;
        import org.apache.ibatis.session.SqlSession;
        import org.springframework.stereotype.Component;
        import org.springframework.util.Assert;

        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class RoleService extends BaseService<Role> {

    /**
     * 根据条件查询分页
     *
     * @param condition
     * @return
     */
    public PageInfo<Role> queryPaged(Condition condition) {
        Assert.notNull(condition);
        Criteria c = condition.createCriteria(Role.class);
        String name = condition.get("name");
        if (!StringUtils.isEmpty(name))
            c.add(
                    Restrictions.like("name", "%" + name
                            + "%"));
        return super.queryPaged(condition);
    }

    public List getRolesByUser(Long userId) {
        return this.getSqlSession().selectList("Role_list_by_user", userId);
    }

    public void saveUserRoles(Long userId, String roles, Long currentUserId) {
        SqlSession session = this.getSqlSession();
        session.delete("Role_delete_by_user", Long.valueOf(userId));
        if (!StringUtils.isEmpty(roles)) {
            Map map = new HashMap();
            map.put("userId", Long.valueOf(userId));
            map.put("creator", currentUserId);
            for (String roleId : roles.split(",")) {
                map.put("roleId", Long.valueOf(roleId));
                session.insert("Role_insert_by_user", map);
            }
        }
    }

}
