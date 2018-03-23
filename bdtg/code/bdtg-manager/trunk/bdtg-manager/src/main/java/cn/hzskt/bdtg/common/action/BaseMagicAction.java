package cn.hzskt.bdtg.common.action;

import cn.hzskt.bdtg.sys.domain.User;
import cn.hzskt.bdtg.sys.service.ShiroDBRealm;
import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import net.ryian.core.action.BaseAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	protected ShiroDBRealm.ShiroUser getCurrentUser(HttpServletRequest request) {
		SSOToken token = SSOHelper.getToken(request);
		if(token != null) {
			String userJSON = token.getData();
			User user = JSON.parseObject(userJSON, User.class);
			ShiroDBRealm.ShiroUser shiroUser = new ShiroDBRealm.ShiroUser(user.getId(),user.getUserName(),user.getName());
			return shiroUser;
		}
		return null;
	}

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

}
