package com.jeecms.bbs.manager;


import java.util.List;

import com.jeecms.bbs.entity.BbsMessage;
import com.jeecms.bbs.entity.BbsUser;
import com.jeecms.common.page.Pagination;


public interface BbsMessageMng {
	public BbsMessage findById(Integer id);

	public BbsMessage save(BbsMessage bean);

	public BbsMessage update(BbsMessage bean);

	public BbsMessage deleteById(Integer id);

	public BbsMessage[] deleteByIds(Integer[] ids);
	
	public void sendMsg(BbsUser sender, BbsUser receiver, BbsMessage sMsg);
	
	public void sendSysMsg(BbsUser sender, BbsMessage sMsg);
	
	public Pagination getPageByUserId(Integer userId,Integer typeId, Integer pageNo, Integer pageSize);
	
	public List<BbsMessage> getListUserIdStatus(Integer userId,Integer typeId, Boolean status);
	
	public List<BbsMessage> getListByUsername(String username);
	
	public Pagination getPagination(Boolean sys,Integer pageNo,Integer pageSize);
	
	public boolean hasUnReadMessage(Integer userId,Integer typeId);
	
	public BbsMessage getMsg(String username,String sendername,String receivername,Integer typeId);
}