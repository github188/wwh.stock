package com.skoo.stock.sys.service;

import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import com.skoo.stock.sys.domain.ModuleBean;
import com.skoo.stock.sys.domain.Sysmenu;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
public class SysmenuService extends BaseService<Sysmenu> {

    /**
     * 根据条件查询分页
     *
     * @param condition
     * @return
     */
    public PageInfo<Sysmenu> queryPaged(Condition condition) {
        Assert.notNull(condition);
        Criteria c = condition.createCriteria(Sysmenu.class);
        String upId = condition.get("upId");
        if (!StringUtils.isEmpty(upId))
            c.add(Restrictions.eq("upId", upId));
        return super.queryPaged(condition);
    }

    /**
     * 菜单信息取得
     *
     * @param map
     * @return
     */
    public Sysmenu queryCategory(Map map) {
        Sysmenu category = this.getSqlSession().selectOne("Sysmenu_custlist", map);
        return category;
    }


    /**
     * 清除用户菜单缓存
     *
     * @return
     */
    @CacheEvict(value = "sysmenuforole", allEntries = true)
    public void removeSysmenuForRole() {
    }

    /**
     * 菜单信息取得（spEL验证）
     *
     * @param map 亲子参数(复核KEY参数)
     * @return 菜单
     */
    @Cacheable(value = "sysmenuforole", key = "#map['id'] + ';' + #map['upId']")
    public List<Sysmenu> getMenuList(Map map) {
        return this.manQuery("Sysmenu_tree_list", map);
    }

    /**
     * 菜单信息取得
     *
     * @param userId 用户编号
     * @return
     */
    @Cacheable(value = "sysmenu", key = "#userId")
    public List<ModuleBean> getModuleBean(String userId) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        return this.manQuery("Sysmenu_module_tree", map);
    }

    public List<ModuleBean> getModuleBeanForRedis(String userId) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        return this.manQuery("Sysmenu_module_tree", map);
    }

    /**
     * 菜单信息取得
     *
     * @param roleId 角色编号
     * @return
     */
    @Cacheable(value = "sysmenu", key = "'member'+#roleId")
    public List<ModuleBean> getModuleBeanMmb(String roleId) {
        Map<String, String> map = new HashMap<>();
        map.put("roleId", roleId);
        return this.manQuery("Sysmenu_module_tree_role", map);
    }

    public List<ModuleBean> getModuleBeanMmbForRedis(String roleId) {
        Map<String, String> map = new HashMap<>();
        map.put("roleId", roleId);
        return this.manQuery("Sysmenu_module_tree_role", map);
    }

}
