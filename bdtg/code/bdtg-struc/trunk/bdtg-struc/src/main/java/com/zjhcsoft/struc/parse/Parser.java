package com.zjhcsoft.struc.parse;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjhcsoft.struc.util.StrucContext;

public abstract class Parser {

	protected static final Logger LOG = LoggerFactory.getLogger(Parser.class);

	public abstract Map<Object, String> parse(Object object, String url);
	
	private StrucContext context;
	
	public StrucContext getContext() {
		return context;
	}

	public void setContext(StrucContext context) {
		this.context = context;
	}
}
