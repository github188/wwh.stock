package com.zjhcsoft.struc.save;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjhcsoft.struc.util.StrucContext;

public abstract class Saver {

	protected static final Logger LOG = LoggerFactory.getLogger(Saver.class);

	public abstract boolean save(Object object, String url);

	private StrucContext context;

	public StrucContext getContext() {
		return context;
	}

	public void setContext(StrucContext context) {
		this.context = context;
	}
}
