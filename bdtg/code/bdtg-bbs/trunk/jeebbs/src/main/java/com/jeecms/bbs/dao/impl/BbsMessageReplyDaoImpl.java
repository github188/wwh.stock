package com.jeecms.bbs.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeecms.bbs.dao.BbsMessageReplyDao;
import com.jeecms.bbs.entity.BbsMessageReply;
import com.jeecms.common.hibernate3.Finder;
import com.jeecms.common.hibernate3.HibernateBaseDao;
import com.jeecms.common.page.Pagination;

@Repository
public class BbsMessageReplyDaoImpl extends
		HibernateBaseDao<BbsMessageReply, Integer> implements BbsMessageReplyDao {
	public BbsMessageReply findById(Integer id) {
		BbsMessageReply entity = get(id);
		return entity;
	}

	public BbsMessageReply save(BbsMessageReply bean) {
		getSession().save(bean);
		return bean;
	}

	public BbsMessageReply deleteById(Integer id) {
		BbsMessageReply entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	public Pagination getPageByMsgId(Integer msgId, Integer pageNo,
			Integer pageSize) {
		String hql = "from BbsMessageReply bean where bean.message.id=:msgId order by bean.createTime desc";
		Finder f = Finder.create(hql).setParam("msgId", msgId);
		return find(f, pageNo, pageSize);
	}
	
	public Pagination getPage(Integer pageNo, Integer pageSize){
		String hql = "select bean from BbsMessageReply bean where bean.message.id in(select m.id from BbsMessage m  group by m.sender,m.receiver) order by bean.createTime desc";
		Finder f = Finder.create(hql);
		return find(f,pageNo,pageSize);
	}
	
	public List<BbsMessageReply> getList(Integer msgId,int count){
		String hql = "from BbsMessageReply bean where bean.message.id=:msgId order by bean.createTime desc";
		Finder f = Finder.create(hql).setParam("msgId", msgId);
		f.setMaxResults(count);
		return find(f);
	}
	
	public List<BbsMessageReply> getList(String username){
		String hql = "from BbsMessageReply bean where bean.receiver.username=:username and bean.status=false and bean.isnotification=false order by bean.createTime desc";
		Finder f = Finder.create(hql).setParam("username", username);
		return find(f);
	}
	
	public int countByReceiver(String username) {
		String hql = "select count(*) from BbsMessageReply bean where bean.receiver.username=:username and bean.status=false";
		Query query = getSession().createQuery(hql);
		query.setParameter("username", username);
		return ((Number) query.iterate().next()).intValue();
	}

	@Override
	protected Class<BbsMessageReply> getEntityClass() {
		return BbsMessageReply.class;
	}
}