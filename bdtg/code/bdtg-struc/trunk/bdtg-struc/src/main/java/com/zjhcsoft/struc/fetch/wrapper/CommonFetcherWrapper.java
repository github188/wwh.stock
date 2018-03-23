package com.zjhcsoft.struc.fetch.wrapper;

import com.zjhcsoft.struc.fetch.CommonFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by allenwc on 14/11/19.
 */
public abstract class CommonFetcherWrapper {

    protected static final Logger LOG = LoggerFactory.getLogger(CommonFetcherWrapper.class);

    protected CommonFetcher fetcher;

    public CommonFetcherWrapper(CommonFetcher fetcher) {
        this.fetcher = fetcher;
    }

    public abstract Object fetch();

}
