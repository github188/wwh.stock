package com.zjhcsoft.struc.fetch;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Fetcher implements Callable<Object> {

	protected static final Logger LOG = LoggerFactory.getLogger(Fetcher.class);

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
