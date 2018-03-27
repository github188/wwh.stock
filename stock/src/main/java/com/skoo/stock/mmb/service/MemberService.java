package com.skoo.stock.mmb.service;

import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import com.skoo.stock.mmb.domain.Member;
import org.apache.commons.lang.StringUtils;
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
public class  MemberService extends BaseService<Member> {

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<Member> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(Member.class);
		String phone = condition.get("email");
		if (!StringUtils.isEmpty(phone))
			c.add(
					Restrictions.like("email", "%" + phone
							+ "%"));
		return super.queryPaged(condition);
	}

	/**
	 * 根据手机号码取记录
	 * @param phone
	 * @return long
	 */
	public long selectByPhone(String phone) {
		Map map = new HashMap<>();
		map.put("phone",phone);
		return super.getSqlSession().selectOne("Member_byPhone", map);
	}

	/**
	 * 根据条件查询会员
	 *
	 * @param map 条件
	 * @return
	 */
	public int selectpass(Map map) {
		Assert.notNull(map);
		List<Map> mapList = super.manQuery("Member_chgpass_count", map);
		return Integer.parseInt(mapList.get(0).get("cnt").toString());
	}

	/**
	 * 修改密码
	 *
	 * @param map 条件
	 * @return
	 */
	public int changepass(Map map) {
		Assert.notNull(map);
		return super.update("Member_chgpass", map);
	}

	public void examination_passed(long l) {
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("id",l);
		this.getSqlSession().update("Member_examination_passed",m);
	}
}
