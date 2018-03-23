package com.zjhcsoft.struc.save.wrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjhcsoft.struc.save.CommonSaver;

public abstract class CommonSaverWrapper {

	protected static final Logger LOG = LoggerFactory.getLogger(CommonSaverWrapper.class);

    protected CommonSaver saver;

    public CommonSaverWrapper(CommonSaver saver) {
        this.saver = saver;
    }

    public abstract boolean save(Object object, String url);
	
}
