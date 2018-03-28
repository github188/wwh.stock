package cn.hzskt.common.action;

import java.util.List;

import com.zjhc.cas.clientX.strategy.DefaultUserInfoRetrivedImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.zjhc.cas.clientX.bean.UserBean;
import com.zjhc.cas.clientX.strategy.UserInfoRetrieved;
import com.zjhc.cas.clientX.util.RetrievedImpl;
import com.zjhcsoft.smartcity.magic.core.action.BaseAction;

/**
 * Action基类，系统中所有基类均继承自该类
 * 
 * @author cheng.wang
 */
@SuppressWarnings("serial")
public abstract class BaseMagicAction extends BaseAction{

	protected Logger logger = Logger.getLogger(this.getClass());

	protected UserBean getCurrentUser() {
		UserInfoRetrieved impl = RetrievedImpl.getUserInfoRetriveImpl();
		List<UserBean>  users = impl.getUserBeans();
		if(!CollectionUtils.isEmpty(users))
			return users.get(0);
		return null;

	}

}
