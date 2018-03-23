package com.zjhcsoft.struc.fetch;

import org.apache.commons.lang3.StringUtils;

import com.zjhcsoft.struc.fetch.wrapper.CommonFetcherWrapper;
import com.zjhcsoft.struc.util.datasource.DataSourceHandler;

public class CommonFetcher extends Fetcher {

	private CommonFetcherWrapper commonFetcherWrapper;

	public CommonFetcher() {
		String fetchtype = DataSourceHandler.urlPattern.getFetchtype();
		if(StringUtils.isEmpty(fetchtype)) {
			fetchtype = "http";
		}
		if(fetchtype.contains(".")) {
			fetchtype = fetchtype.substring(0,fetchtype.lastIndexOf("."))+StringUtils.capitalize(fetchtype.substring(fetchtype.lastIndexOf(".")));
		} else {
			fetchtype = StringUtils.capitalize(fetchtype);
		}
		String className = "com.zjhcsoft.struc.fetch.wrapper."+fetchtype +"CommonFetcherWrapper";
		try {
			commonFetcherWrapper = (CommonFetcherWrapper)Class.forName(className).getConstructor(CommonFetcher.class).newInstance(this);
		}catch (Exception e) {
			LOG.error("",e);
		}
	}

	@Override
	public Object call() {
		return commonFetcherWrapper.fetch();
	}

}
