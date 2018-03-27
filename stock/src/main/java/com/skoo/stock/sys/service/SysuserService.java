package com.skoo.stock.sys.service;

import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import com.skoo.stock.sys.domain.Sysrole;
import com.skoo.stock.sys.domain.Sysuser;
import com.skoo.stock.util.ManUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
@SuppressWarnings("unchecked")
public class SysuserService extends BaseService<Sysuser> {

    /**
     * 根据条件查询分页
     *
     * @param condition
     * @return
     */
    public PageInfo<Sysuser> queryPaged(Condition condition) {
        Assert.notNull(condition);
        Criteria c = condition.createCriteria(Sysuser.class);
        String userId = condition.get("userId");
        if (!StringUtils.isEmpty(userId)) {
            c.add(Restrictions.like("userId", "%" + userId + "%"));
        }

        String userName = condition.get("userName");
        if (!StringUtils.isEmpty(userName)) {
            c.add(Restrictions.like("userName", "%" + userName + "%"));
        }
        return super.queryPaged(condition);
    }

    /**
     * 根据条件查询角色包含的用户
     *
     * @param condition 条件
     * @return
     */
    public PageInfo<Map> queryRoleUsers(Condition condition) {
        Assert.notNull(condition);
        return super.manPaged("Sysuser_roleusers_list", condition);
    }

    /**
     * 根据条件查询用户所属角色
     *
     * @param condition 条件
     * @return
     */
    public PageInfo<Map> queryUserRoles(Condition condition) {
        Assert.notNull(condition);
        return super.manPaged("Sysuser_userroles_list", condition);
    }

    /**
     * 修改密码
     *
     * @param map 条件
     * @return
     */
    public int changepass(Map map) {
        Assert.notNull(map);
        return super.update("Sysuser_chgpass", map);
    }

    /**
     * 根据条件查询用户
     *
     * @param map 条件
     * @return
     */
    public int selectpass(Map map) {
        Assert.notNull(map);
        List<Map> mapList = super.manQuery("Sysuser_chgpass_count", map);
        return Integer.parseInt(mapList.get(0).get("cnt").toString());
    }

    /**
     * 根据条件查询用户
     *
     * @param map 条件
     * @return 数据列表
     */
    public List<Sysuser> selectUserWithRole(Map map) {
        Assert.notNull(map);
        return this.manQuery("Sysuser_list_group", map);
    }

    /**
     * 查询有效用户角色
     *
     * @param map 参数
     * @return
     */
    public List<String> selectIdList(Map map) {
        Assert.notNull(map);
        return super.getSqlSession().selectList("Sysuser_urole_idlist", map);
    }

    /**
     * 查询无效用户角色
     *
     * @param map 参数
     * @return
     */
    public List<String> selectIdListDel(Map map) {
        Assert.notNull(map);
        return super.getSqlSession().selectList("Sysuser_urole_idlist_del", map);
    }

    /**
     * 批量新增角色菜单数据
     *
     * @param map 参数
     * @return
     */
    public int insertUserRoleList(Map<String, Object> map) {
        Assert.notNull(map);
        return super.getSqlSession().insert("Sysuser_urole_insert_batch", map);
    }

    /**
     * 批量逻辑新增角色菜单数据
     *
     * @param map 参数
     * @return
     */
    public int insertUserRoleListLogic(Map<String, Object> map) {
        Assert.notNull(map);
        return super.getSqlSession().insert("Sysuser_urole_insert_batch_logic", map);
    }

    /**
     * 批量删除角色菜单数据
     *
     * @param map 参数
     * @return
     */
    public int deleteUserRoleListLogic(Map<String, Object> map) {
        Assert.notNull(map);
        return super.getSqlSession().insert("Sysuser_urole_delete_batch_logic", map);
    }

    /**
     * 批量处理用户角色数据
     *
     * @param id   角色ID
     * @param diff 参数
     * @return
     */
    public void processUserRoleList(long id, List<List<String>> diff, List<List<String>> diffDel) {
        Assert.notNull(diff);

        // 删除
        if (diff.get(1).size() > 0) {
            Map<String, Object> delMap = new HashMap<>();
            delMap.put("userId", id);
            delMap.put("list", diff.get(1));
            //删除数据
            this.deleteUserRoleListLogic(delMap);
        }

        // 逻辑新增
        if (diffDel.get(2).size() > 0) {
            Map<String, Object> insMap = new HashMap<>();
            insMap.put("userId", id);
            insMap.put("list", diffDel.get(2));
            // 新增数据
            this.insertUserRoleListLogic(insMap);
        }

        // 物理新增
        if (diffDel.get(0).size() > 0) {
            Map<String, Object> insMap = new HashMap<>();
            insMap.put("userId", id);
            insMap.put("list", diffDel.get(0));
            // 新增数据
            this.insertUserRoleList(insMap);
        }
    }

    /**
     * 批量处理角色相关数据
     *
     * @param o           角色信息
     * @param condition        角色信息以及其他信息
     */
    public void processRoleExt(Sysuser o, Condition condition) {
        // 处理用户角色数据
        String roleAllList = condition.get("roleAllList");
        String roleCheckList = condition.get("roleCheckList");
        List<String> nowRoleList = new ArrayList<>();
        List<String> allRoleList = new ArrayList<>();
        if (!StringUtils.isEmpty(roleCheckList)) {
            nowRoleList = Arrays.asList(roleCheckList.split(","));
        }
        if (!StringUtils.isEmpty(roleAllList)) {
            allRoleList = Arrays.asList(roleAllList.split(","));
        }

        Map<String, Object> mapRole = new HashMap<>();
        mapRole.put("userId", o.getId());
        mapRole.put("list", allRoleList);
        List<String> dbRoleList = this.selectIdList(mapRole);
        List<String> dbRoleListDel = this.selectIdListDel(mapRole);
        List<List<String>> diffRole = ManUtil.getDiff(dbRoleList, nowRoleList); // 需删除
        List<List<String>> diffRoleDel = ManUtil.getDiff(dbRoleListDel, diffRole.get(0)); // 需插入

        this.processUserRoleList(o.getId(), diffRole, diffRoleDel);

    }

}
