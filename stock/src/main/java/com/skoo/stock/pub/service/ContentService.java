package com.skoo.stock.pub.service;

import com.skoo.stock.common.Constant;
import com.skoo.stock.login.UserSession;
import com.skoo.stock.pub.domain.Content;
import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import com.skoo.stock.pub.domain.ContentCust;
import com.skoo.stock.pub.domain.ContentExt;
import com.skoo.stock.pub.domain.ContentTxt;
import com.skoo.stock.sys.domain.Dict;
import com.skoo.stock.util.ManUtil;
import org.apache.commons.digester.annotations.rules.AttributeCallParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class  ContentService extends BaseService<Content> {

	@Autowired
	private ContentExtService contentExtService;

	@Autowired
	private ContentTxtService contentTxtService;

	@Autowired
	private ContentChannelService contentChannelService;

	/**
	 * 根据条件查询分页
	 * @param condition
	 * @return
	 */
	public PageInfo<Content> queryPaged(Condition condition) {
		Assert.notNull(condition);
		Criteria c = condition.createCriteria(Content.class);
		
		// 检索项目
		String title = condition.get("title");
		String contentAbstract = condition.get("contentAbstract");
		String typeCode = condition.get("typeCode");
		String status = condition.get("status");
		
		if (!StringUtils.isEmpty(typeCode))
			c.add(Restrictions.eq("typeCode", typeCode));

		if (!StringUtils.isEmpty(status))
			c.add(Restrictions.eq("status", status));
		
		if (!StringUtils.isEmpty(title))
			c.add(Restrictions.like("contentTitle", "%" + title
							+ "%"));
		
		if (!StringUtils.isEmpty(contentAbstract))
			c.add(Restrictions.like("contentAbstract", "%" + contentAbstract
							+ "%"));
		return super.queryPaged(condition);
	}

	/**
	 * 登录更新
	 *
	 * @param map 需要更新的内容附加信息
	 */
	public void updateContentExt(Map<String, Object> map) {
		contentExtService.saveOrUpdate((ContentExt) map.get("Ext"));
		contentTxtService.saveOrUpdate((ContentTxt) map.get("Txt"));

		Map<String, Object> chnMap = (HashMap<String, Object>)map.get("Channel");
		contentChannelService.deleteChannelList(chnMap);
		List<String> insChannelList = contentChannelService.selectNoInsertList(chnMap);
		List<String> nowChannelList = (List<String>)chnMap.get("list");
		List<List<String>> lists = ManUtil.getDiff(insChannelList, nowChannelList);

		if (lists.get(0).size() > 0) {
			Map<String, Object> chnMapNo = new HashMap<>();
			chnMapNo.put("list", lists.get(0));
			chnMapNo.put("contentId", chnMap.get("contentId"));
			contentChannelService.insertChannelList(chnMapNo);
		}
	}

	/**
	 * 更新发布状态
	 *
	 * @param ids
	 * @param status
	 */
	public void updatePublishStatus(String ids, String status) {

		for (String id : ids.split(",")) {
			Content content = this.get(id);
			if (Constant.PUBLISH_STATUS_PUB.equals(content.getStatus()) && Constant.PUBLISH_STATUS_PUB.equals(status)
					|| Constant.PUBLISH_STATUS_DOWN.equals(content.getStatus()) && Constant.PUBLISH_STATUS_DOWN.equals(status)) {
				continue;
			}
			content.setStatus(status);
			content.setUpdator(UserSession.getUserId().toString());
			content.setUpdateDate(new Date());

			this.saveOrUpdate(content);
		}

	}

	/**
	 * 取得内容附加数据
	 *
	 * @param contentId 内容ID
	 */
	public ContentExt getExt(String contentId) {

		Condition condition = new Condition();
		Criteria criteria = condition.createCriteria(ContentExt.class);
		if (!StringUtils.isEmpty(contentId)) {
			criteria.add(Restrictions.eq("contentId", contentId));
		}

		List<ContentExt> list = contentExtService.query(condition);
		if (list.size() == 1) {
			return list.get(0);
		} else {
			return new ContentExt();
		}
	}

	/**
	 * 取得内容富文本数据
	 *
	 * @param contentId 内容ID
	 */
	public ContentTxt getTxt(String contentId) {

		Condition condition = new Condition();
		Criteria criteria = condition.createCriteria(ContentExt.class);
		if (!StringUtils.isEmpty(contentId)) {
			criteria.add(Restrictions.eq("contentId", contentId));
		}

		List<ContentTxt> list = contentTxtService.query(condition);
		if (list.size() == 1) {
			return list.get(0);
		} else {
			return new ContentTxt();
		}
	}

	/**
	 * 取得内容富文本数据
	 *
	 * @param contentId 内容ID
	 */
	public List<String> getChn(String contentId) {
		Map<String, Object> map = new HashMap<>();
		map.put("contentId", contentId);
		map.put("list", new ArrayList<String>());

		List<String> list = contentChannelService.selectNoInsertList(map);
		return list;
	}

	/**
	 * 取得内容自定义列表
	 *
	 * @param condition 条件
	 */
	public PageInfo<ContentCust> queryPageCust(Condition condition) {
		Assert.notNull(condition);
		return super.manPaged("Content_list_cust", condition);
	}

}
