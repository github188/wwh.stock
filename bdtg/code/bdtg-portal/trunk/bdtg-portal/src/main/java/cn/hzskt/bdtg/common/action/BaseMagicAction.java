package cn.hzskt.bdtg.common.action;

import net.ryian.commons.BeanUtils;
import net.ryian.core.action.BaseAction;
import net.ryian.orm.domain.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Action基类，系统中所有基类均继承自该类
 * 
 * @author cheng.wang
 */
@SuppressWarnings("serial")
public abstract class BaseMagicAction extends BaseAction{

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected Map<String,String> getParameterMap(HttpServletRequest request) {
		Map<String,String> map = new HashMap<String,String>();
		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String propertyName = enumeration.nextElement();
			String propertyValue = request.getParameter(propertyName.trim());
			map.put(propertyName,propertyValue);
		}
		return map;
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
