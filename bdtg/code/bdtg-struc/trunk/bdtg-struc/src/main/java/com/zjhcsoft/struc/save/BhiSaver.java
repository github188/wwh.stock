package com.zjhcsoft.struc.save;

import com.zjhcsoft.struc.bean.CrawlerBidInfo;
import com.zjhcsoft.struc.bean.CrawlerBidInfoExample;
import com.zjhcsoft.struc.mapper.CrawlerBidInfoMapper;
import com.zjhcsoft.struc.util.SpringBeanFactory;

public class BhiSaver extends Saver {

	public boolean save(Object object, String url) {

		CrawlerBidInfo crawlerBidInfo = (CrawlerBidInfo) object;

		CrawlerBidInfoExample crawlerBidInfoExample = new CrawlerBidInfoExample();
		crawlerBidInfoExample.createCriteria().andPublishDateEqualTo(crawlerBidInfo.getPublishDate()).andProjectNameEqualTo(crawlerBidInfo.getProjectName());
		CrawlerBidInfoMapper CrawlerBidInfoMapper = (CrawlerBidInfoMapper) SpringBeanFactory
		        .getBean("crawlerBidInfoMapper");
		int count = CrawlerBidInfoMapper.countByExample(crawlerBidInfoExample);
		int insertOrUpdateNum = 0;

		if (count > 0) {
			return true;
		} else {
			// 插入到数据库
			insertOrUpdateNum = CrawlerBidInfoMapper.insertSelective(crawlerBidInfo);
		}

		// 插入或更新成功
		if (insertOrUpdateNum > 0) {
			return true;
		}

		// 插入或更新失败
		LOG.info("插入或更新失败===" + "");
		return false;
	}
}
