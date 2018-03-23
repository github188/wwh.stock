package com.zjhcsoft.struc.save;

import org.apache.commons.lang3.StringUtils;

import com.zjhcsoft.struc.save.wrapper.CommonSaverWrapper;
import com.zjhcsoft.struc.util.datasource.DataSourceHandler;

public class CommonSaver extends Saver {

	private CommonSaverWrapper commonSaverWrapper;
	
	public CommonSaver() {
		String savetype = DataSourceHandler.tables.getSavetype();
		if(StringUtils.isEmpty(savetype)) {
			savetype = "default";
		}
		if(savetype.contains(".")) {
			savetype = savetype.substring(0,savetype.lastIndexOf("."))+StringUtils.capitalize(savetype.substring(savetype.lastIndexOf(".")));
		} else {
			savetype = StringUtils.capitalize(savetype);
		}
		String className = "com.zjhcsoft.struc.save.wrapper."+ savetype+"CommonSaverWrapper";
		try {
			commonSaverWrapper = (CommonSaverWrapper)Class.forName(className).getConstructor(CommonSaver.class).newInstance(this);
		}catch (Exception e) {
			LOG.error("",e);
		}
	}
	
	public boolean save(Object object, String url) {
		return commonSaverWrapper.save(object, url);
	}

}
