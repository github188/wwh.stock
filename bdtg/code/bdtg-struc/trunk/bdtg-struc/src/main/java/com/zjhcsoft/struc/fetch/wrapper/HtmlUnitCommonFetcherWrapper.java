package com.zjhcsoft.struc.fetch.wrapper;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.zjhcsoft.struc.fetch.CommonFetcher;
import com.zjhcsoft.struc.util.HtmlUnitUtil;

/**
 * Created by allenwc on 14/11/19.
 */
public class HtmlUnitCommonFetcherWrapper extends CommonFetcherWrapper {

	public HtmlUnitCommonFetcherWrapper(CommonFetcher fetcher) {
		super(fetcher);
	}

	public static String Htmlunitexecute(String url) {
		String content = null;
		try {
			content = HtmlUnitUtil.getHtmlContent(url);
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}

	@Override
	public Object fetch() {
		return Htmlunitexecute(fetcher.getUrl());
	}
}
