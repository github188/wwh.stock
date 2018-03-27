package com.skoo.stock.common.action;

import com.skoo.commons.BeanUtils;
import com.skoo.commons.GenericsUtils;
import com.skoo.orm.domain.BaseEntity;
import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.stock.sys.domain.OperateLog;
import com.skoo.stock.sys.service.OperateLogService;
import com.skoo.stock.sys.utils.json.JsonUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * 增删改查基类.
 * <p/>
 * 提供了访问路径校验、异常处理机制.
 * <p/>
 * 同时也提供了请求数据组装(组装到Map/EntityBean)、结果数据和消息打印的公共方法.
 *
 * @author: gomiten@163.com
 * @date: 2014年9月10日 上午10:27:52
 */
public abstract class ManAction<T extends BaseEntity, K extends BaseService>
        extends BaseManAction {
    protected static final Logger logger = Logger.getLogger(ManAction.class);

    protected Class<T> entityClass;
    @Autowired
    protected K entityService;

    @Autowired
    protected OperateLogService operateLogService;

    @SuppressWarnings("unchecked")
    public ManAction() {
        entityClass = GenericsUtils.getSuperClassGenricType(getClass());
    }

    protected <T extends BaseEntity> T bindEntity(HttpServletRequest request,
                                                  Class<T> clazz) {
        T entity = null;
        try {
            entity = clazz.newInstance();
        } catch (InstantiationException e) {
            logger.error("bindEntity", e);
        } catch (IllegalAccessException e) {
            logger.error("bindEntity", e);
        }
        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String propertyName = (String) enumeration.nextElement();
            String propertyValue = request.getParameter(propertyName).trim();
            propertyValue = propertyValue.replace("\'", "\"");
            try {
                BeanUtils.setBeanPropertyByName(entity, propertyName,
                        propertyValue);
            } catch (Exception e) {
                logger.error("bindEntity", e);
            }
        }
//        if (entity.getId() == null) {
//            entity.setCreator(this.getCurrentUser().getId());
//        } else {
//            entity.setUpdator(this.getCurrentUser().getId());
//        }
        return entity;
    }

    /**
     * 分页查询Dictionary列表.
     */
    @RequestMapping(value = "queryPaged")
    @SuppressWarnings("unchecked")
    public void queryPaged(HttpServletRequest request,
                           HttpServletResponse response) {
        try {
            Condition condition = bindCondition(request);
            PageInfo<T> page = entityService.queryPaged(condition);
            printText(response, JsonUtils.bean2Json(page, "yyyy-MM-dd"));
        } catch (Exception e) {
            logger.error("queryPaged", e);
        }
    }

    /**
     * 分页查询Dictionary列表.
     */
    @RequestMapping(value = "manPaged")
    @SuppressWarnings("unchecked")
    public void manPaged(HttpServletRequest request,
                         HttpServletResponse response) {
        try {
            Condition condition = bindCondition(request);
            PageInfo<T> page = entityService.manPaged(condition);
            printText(response, JsonUtils.bean2Json(page, null, "yyyy-MM-dd", entityClass));
        } catch (Exception e) {
            logger.error("manPaged", e);
        }
    }

    /**
     * 保存单条Dictionary记录.
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            T o = bindEntity(request, entityClass);
            String title = OperateLog.OPERATING_UPDATE_TITLE;
            if (o.getId() == null) {
                title = OperateLog.OPERATING_INSERT_TITLE;
            }
            entityService.saveOrUpdate(o);
            String content = "id=" + o.getId();
            operateLogService.wirteLog(request, title, content);
            printText(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("save", e);
            printText(response, messageFailureWrap("保存失败！"));
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String ids = request.getParameter("ids");
            for (String id : ids.split(",")) {
                entityService.logicRemove(Long.parseLong(id));
                String content = "id=" + id;
                operateLogService.wirteLog(request, OperateLog.OPERATING_DELETE_TITLE, content);
            }
            printText(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("delete", e);
            printText(response, messageFailureWrap("删除失败！"));
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return getNameSpace() + "index";
    }

    @RequestMapping(value = "add")
    public String add() {
        return getNameSpace() + "add";
    }

    @RequestMapping(value = "edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        if (id != null) {
            model.addAttribute("model", entityService.get(id));
        }
        return getNameSpace() + "edit";
    }

    protected String getNameSpace() {
        String ns = null;
        RequestMapping r = getClass().getAnnotation(RequestMapping.class);
        ns = r.value()[0];
        if (!ns.endsWith("/")) {
            ns += "/";
        }
        return ns;
    }

}
