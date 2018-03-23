package cn.hzskt.bdtg.common.action;

import cn.hzskt.bdtg.sys.utils.json.DictFilter;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import net.ryian.commons.BeanUtils;
import net.ryian.commons.GenericsUtils;
import net.ryian.orm.domain.BaseEntity;
import net.ryian.orm.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Enumeration;

/**
 * Created by allenwc on 15/9/9.
 */
public class MagicAction<K extends BaseEntity,T extends BaseService> extends BaseMagicAction{

    @Autowired
    protected T entityService;

    protected Class<K> entityClass;

    public MagicAction() {
        entityClass = GenericsUtils.getSuperClassGenricType(getClass());
    }

    @RequestMapping(value = "queryPaged")
    public void queryPaged(HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        try {
            @SuppressWarnings("unchecked")
			PageInfo<?> page = entityService.queryPaged(getParameterMap(request));
            JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(page,new DictFilter()));
            o.put("rows", o.get("list"));
            o.remove("list");
            o.put("totalPageCount", o.get("lastPage"));
            o.put("countPerPage", o.get("pageSize"));
            o.put("currentPage",o.get("pageNum"));
            printJson(response,o.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存单条Dictionary记录.
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public void save(HttpServletRequest request, HttpServletResponse response) {
        try {
            K o = bindEntity(request, entityClass);
            entityService.saveOrUpdate(o);
            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("save", e);
            printJson(response, messageFailureWrap("保存失败！"));
        }
    }

    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public void delete(HttpServletRequest request,
                       HttpServletResponse response) {
        try {
            String ids = request.getParameter("ids");
            for (String id : ids.split(",")) {
                entityService.logicRemove(Long.parseLong(id));
            }
            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("delete", e);
            printJson(response, messageFailureWrap("删除失败！"));
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request,Model model) {
        beforeIndex(request,model);
        return getNameSpace() + "index";
    }

    protected void beforeIndex(HttpServletRequest request,Model model) {

    }

    @RequestMapping(value = "add")
    public String add(HttpServletRequest request,Model model) {
        beforeAdd(request,model);
        return getNameSpace() + "add";
    }

    protected void beforeAdd(HttpServletRequest request,Model model) {

    }

    @RequestMapping(value = "edit/{id}")
    public String edit(HttpServletRequest request,@PathVariable("id") Long id, Model model)
            throws Exception {
        if (id != null) {
            BaseEntity entity = entityService.get(id);
            model.addAttribute("model", entity);
            beforeEdit(request,model,entity);
        }
        return getNameSpace() + "edit";
    }

    protected void beforeEdit(HttpServletRequest request,Model model, BaseEntity entity) {

    }

    protected String getNameSpace() {
        String ns = null;
        RequestMapping r = getClass().getAnnotation(RequestMapping.class);
        ns = r.value()[0];
        if (!ns.endsWith("/"))
            ns += "/";
        return ns;
    }

    protected <T extends BaseEntity> T bindEntity(HttpServletRequest request, Class<T> clazz)
            throws Exception {
        T entity = clazz.newInstance();
        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String propertyName = (String) enumeration.nextElement();
            String propertyValue = request.getParameter(propertyName).trim();
            propertyValue = propertyValue.replace("\'", "\"");
            BeanUtils.setBeanPropertyByName(entity, propertyName, propertyValue);
        }
        return entity;
    }

}
