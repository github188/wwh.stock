package cn.hzskt.bdtg.sys.service;

import cn.hzskt.bdtg.sys.mapper.UserMapper;
import cn.hzskt.bdtg.sys.domain.User;
import net.ryian.commons.StringUtils;
import net.ryian.core.SystemConfig;
import net.ryian.orm.service.BaseService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class  UserService extends BaseService<User,UserMapper> {

	/**
	 * 根据条件查询分页
	 * @param paramMap
	 * @return
	 */
	public List<User> query(Map<String, String> paramMap) {
		Assert.notNull(paramMap);
		Example example = new Example(User.class);
		Example.Criteria criteria = example.createCriteria();
		String userName = paramMap.get("userName");
		if (!StringUtils.isEmpty(userName)) {
			criteria.andLike("userName","%"+userName+"%");
		}
		return mapper.selectByExample(example);
	}

	public User findUserByUserName(String userName) {
		User user = new User();
		user.setValid(1);
		user.setUserName(userName);
		return mapper.selectOne(user);
	}

	@Override
	public Long saveOrUpdate(User user) {
		if (user.getId() == null) {
			if(StringUtils.isEmpty(user.getPassword())) {
				user.encryptUserPassword(SystemConfig.INSTANCE.getValue("DEFAULT_PASSWORD"));
			}
		}
		return super.saveOrUpdate(user);
	}

	public User validate(String username,String password) {
		User user = findUserByUserName(username);
		if(user != null) {
			String pwd = user.getPassword();
			user.encryptUserPassword(password);
			if(pwd.equals(user.getPassword())) {
				return user;
			}
		}
		return null;
	}

}
