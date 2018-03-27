package cn.hzstk.securities.sys.service;

import cn.hzstk.securities.sys.domain.Role;
import cn.hzstk.securities.sys.mapper.RoleMapper;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class  RoleService extends BaseService<Role,RoleMapper> {

	/**
	 * 根据条件查询分页
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<Role> query(Map<String,String> paramMap) {
		Example example = new Example(Role.class);
		Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
		String name = paramMap.get("name");
		if (!StringUtils.isEmpty(name)) {
			criteria.andLike("name",name);
		}
		return mapper.selectByExample(example);
	}

	public List<Role> getRolesByUser(Long userId) {
		return mapper.getRolesByUser(userId);
	}

	public void saveUserRoles(Long userId,String roles,Long currentUserId) {
		mapper.deleteByUser(userId);
		if(!StringUtils.isEmpty(roles)) {
			Map map = new HashMap();
			map.put("userId",Long.valueOf(userId));
			map.put("creator",currentUserId);
			for(String roleId : roles.split(",")) {
				map.put("roleId", Long.valueOf(roleId));
				mapper.insertRoleUser(map);
			}
		}
	}

}
