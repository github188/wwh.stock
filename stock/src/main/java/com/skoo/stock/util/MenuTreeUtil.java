package com.skoo.stock.util;

import com.skoo.core.utils.SpringContextUtil;
import com.skoo.stock.common.RedisKey;
import com.skoo.stock.distredis.service.RedisCache;
import com.skoo.stock.login.SSOHelper;
import com.skoo.stock.login.UserSession;
import com.skoo.stock.login.domain.UserInfo;
import com.skoo.stock.sys.domain.ModuleBean;
import com.skoo.stock.sys.service.SysmenuService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 取得模块菜单显示
 * 默认的一、二级菜单采用二分法进行查询
 * 三级及以下，则遍历 TODO
 *
 * @author Administrator
 */
public class MenuTreeUtil {
    /**
     * 一级菜单LEV
     */
    private final static String FIRST_LEV = "2";


    /**
     * 菜单和按钮以外的页面元素类型
     */
    private final static String APP = "A";

    private static List<ModuleBean> hasGeted = null;

    private static Log log = LogFactory.getLog(MenuTreeUtil.class);

    private final static SysmenuService sysmenuService = SpringContextUtil.getBean(SysmenuService.class);
    private final static RedisCache redisCache = SpringContextUtil.getBean(RedisCache.class);

    /**
     * 获取菜单
     *
     * @param moduleId        父菜单编号，id为空时返回顶级菜单
     * @param unAuthorityShow 是否显示没有权限的菜单【可选，默认状态下不显示无权限的菜单】
     *                        <p/>
     *                        <p>history:</p>
     *                        <p>该方法是用于针对树形菜单的展示</p>
     * @return
     */
    public static List<ModuleBean> getChildMenuList(String moduleId, boolean... unAuthorityShow) {

        List<ModuleBean> beans = sysmenuService.getModuleBean("admin");
        List<ModuleBean> children = new ArrayList<ModuleBean>();
        log.info("菜单获取开始");
        // 获取一级菜单
        if (moduleId == null) {
            hasGeted = new ArrayList<ModuleBean>();
            for (ModuleBean bean : beans) {
                // 对于APP类型不加入到列表显示
                if (APP.equals(bean.getMenu_type())) {
                    continue;
                }
                if (FIRST_LEV.equals(String.valueOf(bean.getLvl())) && (unAuthorityShow.length > 0 ? (unAuthorityShow[0] ? true : bean.getFlg() != null) : (bean.getFlg() != null))) {
                    if (hasChild(bean, beans)) {
                        bean.setHasChild("");
                    }
                    children.add(bean);
                }
            }
            hasGeted.addAll(children);
            return children;
        }
        List<ModuleBean> temp = new ArrayList<ModuleBean>(beans);
        temp.removeAll(hasGeted);

        // 获取子菜单
        for (ModuleBean bean : temp) {
            // 对于APP类型不加入到列表显示
            if (APP.equals(bean.getMenu_type())) {
                continue;
            }
            if (moduleId.equals(String.valueOf(bean.getUp_id())) && (unAuthorityShow.length > 0 ? (unAuthorityShow[0] ? true : bean.getFlg() != null) : (bean.getFlg() != null))) {
                if (hasChild(bean, beans)) {
                    bean.setHasChild("");
                }
                children.add(bean);
            }
        }
        hasGeted.addAll(children);
        return children;
    }


    /**
     * 获取节点的子菜单
     *
     * @param moduleId        父菜单编号，id为空时返回顶级菜单
     * @param unAuthorityShow 是否显示没有权限的菜单【可选】
     * @return
     */
    public static List<ModuleBean> getChildMenu(String moduleId, HttpServletRequest request, boolean... unAuthorityShow) {

        List<ModuleBean> beans = new ArrayList<>();

        Cookie cookieTicket = SSOHelper.getTicket(request);
        String jsessionId = cookieTicket == null ? "" : cookieTicket.getValue();
        // redis need Session检测
        if (ManUtil.isRedis()) {
            if (redisCache.exists(RedisKey.USERINFO + jsessionId)) {
                UserInfo userInfo = redisCache.get(RedisKey.USERINFO + jsessionId, UserInfo.class);

                // 普通员工登录
                if ("1".equals(userInfo.getUserType())) {
                    String userId = userInfo.getUserId();
                    beans = sysmenuService.getModuleBean(userId);
                } else {
                    String roleId = ManUtil.getAuthConfig("mmbRoleId").toString();
                    beans = sysmenuService.getModuleBeanMmb(roleId);
                }
            }
        } else {
            // 普通员工登录
            if ("1".equals(UserSession.getUserType())) {
                String userId = UserSession.getUserInfo().getUserId();
                beans = sysmenuService.getModuleBean(userId);
            } else {// 会员登录
                String roleId = ManUtil.getAuthConfig("mmbRoleId").toString();
                beans = sysmenuService.getModuleBeanMmb(roleId);
            }
        }

        List<ModuleBean> children = new ArrayList<>();
        log.info("菜单获取开始");
        // 获取一级菜单
        if (moduleId == null) {
            for (ModuleBean bean : beans) {
                // 对于APP类型不加入到列表显示
                if (APP.equals(bean.getMenu_type())) {
                    continue;
                }
                if (FIRST_LEV.equals(String.valueOf(bean.getLvl())) && (unAuthorityShow.length > 0 ? (unAuthorityShow[0] ? true : bean.getFlg() != null) : bean.getFlg() != null)) {
                    if (hasChild(bean, beans)) {
                        bean.setHasChild("");
                    }
                    children.add(bean);
                }
            }
            return children;
        }
        List<ModuleBean> temp = new ArrayList<>(beans);

        // 获取子菜单
        for (ModuleBean bean : temp) {
            // 对于APP类型不加入到列表显示
            if (APP.equals(bean.getMenu_type())) {
                continue;
            }
            //if (moduleId.equals(String.valueOf(bean.getUp_id()))&& (unAuthorityShow.length > 0 ? (unAuthorityShow[0]? true : bean.getFlg()!=null) : bean.getFlg()!=null)) {
            if (moduleId.equals(String.valueOf(bean.getUp_id()))) {
                if (hasChild(bean, beans)) {
                    bean.setHasChild("");
                }
                children.add(bean);
            }
        }
        return children;
    }


    public static boolean hasChild(ModuleBean bean, List<ModuleBean> beans) {
        // 采用二分法查找模块信息
        return indexedBinarySearch(beans, bean.getId());
    }


    /**
     * 对模块信息的上级菜单id进行了排序
     * 采用二分法判定是否拥有子节点
     *
     * @param list
     * @param key
     * @return
     */
    private static boolean indexedBinarySearch(List<ModuleBean> list, Long key) {
        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            ModuleBean midVal = list.get(mid);
            boolean cmp = midVal.getUp_id().equals(key);
            if (cmp) {
                return true;
            } else {
                if (key.compareTo(midVal.getUp_id()) > 0)
                    low = mid + 1;
                else if (key.compareTo(midVal.getUp_id()) < 0)
                    high = mid - 1;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        System.out.println((new Long("123")) < (new Long(123)));
        ModuleBean bean = new ModuleBean();
        bean.setId(123L);
        bean.setUp_id(123L);

        ModuleBean bean1 = new ModuleBean();
        bean1.setId(234L);
        bean1.setUp_id(231L);

        List<ModuleBean> beans = new ArrayList<>();
        beans.add(bean);
        beans.add(bean1);

        System.out.println(hasChild(bean1, beans));

    }

}
