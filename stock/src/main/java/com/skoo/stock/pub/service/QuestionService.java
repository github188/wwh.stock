package com.skoo.stock.pub.service;

import com.skoo.stock.pub.domain.Question;
import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class QuestionService extends BaseService<Question> {

	/**
	 * 根据条件查询分页
	 * 
	 * @param condition
	 * @return
	 */
	public PageInfo<Question> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(Question.class);
		String title = condition.get("title");
		String isReply = condition.get("isReply");
		if (!StringUtils.isEmpty(title))
			c.add(Restrictions.like("title", "%" + title + "%"));
		if (StringUtils.isNotEmpty(isReply))
			c.add(Restrictions.eq("isReply", isReply));
		return super.queryPaged(condition);
	}

	/**
	 * 更新
	 * 
	 * @param condition
	 */
	public void update(Condition condition) {
		Criteria c = condition.createCriteria(Question.class);
		String id = condition.get("id");
		if (!StringUtils.isEmpty(id))
			c.add(Restrictions.eq("id", Long.valueOf(id)));
		String reply = condition.get("reply");
		Question question = new Question();
		question.setReply(reply);
		question.setIsReply("1");
		question.setReplyDate(new Date());
		super.updateSelective(question, condition);
	}

}
