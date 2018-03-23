package com.jeecms.bbs.dao;

import com.jeecms.bbs.entity.BbsThirdAccount;
import com.jeecms.common.hibernate3.Updater;
import com.jeecms.common.page.Pagination;

public interface BbsThirdAccountDao {
	public Pagination getPage(String username,String source,int pageNo, int pageSize);

	public BbsThirdAccount findById(Long id);
	
	public BbsThirdAccount findByKey(String key);

	public BbsThirdAccount save(BbsThirdAccount bean);

	public BbsThirdAccount updateByUpdater(Updater<BbsThirdAccount> updater);

	public BbsThirdAccount deleteById(Long id);
}