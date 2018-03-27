package com.skoo.stock.sys.service;

import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import com.skoo.stock.sys.domain.Sysrole;
import com.skoo.stock.util.ManUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SysroleService extends BaseService<Sysrole> {

    @Autowired
    private SysroleMenuService sysroleMenuService;

    @Autowired
    private SysuserRoleService sysuserRoleService;

    /**
     * 根据条件查询分页
     *
     * @param condition 查询条件
     * @return 分页数据信息
     */
    @Override
    public PageInfo<Sysrole> manPaged(Condition condition) {
        Assert.notNull(condition);
        Criteria c = condition.createCriteria(Sysrole.class);
        String roleName = condition.get("roleName");
        String roleType = condition.get("roleType");
        if (!StringUtils.isEmpty(roleName)) {
            c.add(Restrictions.like("roleName", "%" + roleName + "%"));
        }

        if (!StringUtils.isEmpty(roleType)) {
            c.add(Restrictions.eq("roleType", roleType));
        }
        return super.manPaged(condition);
    }

    /**
     * 批量处理角色相关数据
     *
     * @param o           角色信息
     * @param condition        角色信息以及其他信息
     */
    public void processRoleExt(Sysrole o, Condition condition) {

        // 处理角色菜单数据
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", o.getId());
        List<String> dbList = sysroleMenuService.selectIdList(map);
        List<String> dbListDel = sysroleMenuService.selectIdListDel(map);
        String checkList = condition.get("checkList");
        List<String> nowList = new ArrayList<>();
        if (!com.skoo.commons.StringUtils.isEmpty(checkList)) {
            nowList = Arrays.asList(checkList.split(","));
        }
        List<List<String>> diff = ManUtil.getDiff(dbList, nowList); // 需删除
        List<List<String>> diffDel = ManUtil.getDiff(dbListDel, diff.get(0)); // 需插入

        // 处理角色用户数据
        String userAllList = condition.get("userAllList");
        String userCheckList = condition.get("userCheckList");
        List<String> nowUserList = new ArrayList<>();
        List<String> allUserList = new ArrayList<>();
        if (!com.skoo.commons.StringUtils.isEmpty(userCheckList)) {
            nowUserList = Arrays.asList(userCheckList.split(","));
        }
        if (!com.skoo.commons.StringUtils.isEmpty(userAllList)) {
            allUserList = Arrays.asList(userAllList.split(","));
        }

        Map<String, Object> mapUser = new HashMap<>();
        mapUser.put("roleId", o.getId());
        mapUser.put("list", allUserList);
        List<String> dbUserList = sysuserRoleService.selectIdList(mapUser);
        List<String> dbUserListDel = sysuserRoleService.selectIdListDel(mapUser);
        List<List<String>> diffUser = ManUtil.getDiff(dbUserList, nowUserList); // 需删除
        List<List<String>> diffUserDel = ManUtil.getDiff(dbUserListDel, diffUser.get(0)); // 需插入

        //super.saveOrUpdate(o);

        sysroleMenuService.processMenuList(o.getId(), diff, diffDel);
        sysuserRoleService.processUserRoleList(o.getId(), diffUser, diffUserDel);

    }

}
