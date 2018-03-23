package com.zjhcsoft.struc.fetch.wrapper;

import com.zjhcsoft.struc.fetch.CommonFetcher;

/**
 * Created by allenwc on 14/11/19.
 */
public class TestCommonFetcherWrapper extends CommonFetcherWrapper{

    public TestCommonFetcherWrapper(CommonFetcher fetcher) {
        super(fetcher);
    }

    @Override
    public Object fetch() {
        System.out.println("自定义抓取");
        return "helloworld";
    }
}
