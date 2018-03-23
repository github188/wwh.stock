package com.zjhcsoft.struc.fetch;

import com.zjhcsoft.struc.fetch.wrapper.HttpCommonFetcherWrapper;

public class HgzbwFetcher extends Fetcher{

	@Override
	public Object call() throws Exception {
		return HttpCommonFetcherWrapper. httpExecute(getUrl(), "utf-8", "GET");
	}

}
