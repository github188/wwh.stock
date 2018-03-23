package com.jeecms.bbs.manager;

import com.jeecms.common.page.Pagination;
import com.jeecms.bbs.entity.BbsThirdAccount;

public interface BbsThirdAccountMng {
	public Pagination getPage(String username,String source,int pageNo, int pageSize);

	public BbsThirdAccount findById(Long id);
	
	public BbsThirdAccount findByKey(String key);

	public BbsThirdAccount save(BbsThirdAccount bean);

	public BbsThirdAccount update(BbsThirdAccount bean);

	public BbsThirdAccount deleteById(Long id);
	
	public BbsThirdAccount[] deleteByIds(Long[] ids);
}