package com.zjhcsoft.struc.save.wrapper;

import com.zjhcsoft.struc.save.CommonSaver;

public class DefaultCommonSaverWrapper extends CommonSaverWrapper{

	public DefaultCommonSaverWrapper(CommonSaver saver) {
		super(saver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean save(Object object, String url) {
		return true;
	}

}
