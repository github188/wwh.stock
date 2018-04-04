package net.ryian.commons;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigUtil {
	private Properties config;
	private static Map configMap = new HashMap();

	private ConfigUtil(String fileName) throws Exception {
		this.config = new Properties();
		try {
			InputStream fis = super.getClass().getResourceAsStream(fileName);
			if(fis == null)
				 throw new IllegalStateException(fileName+"文件不存在.");
			this.config.load(fis);
			fis.close();
		} catch (IOException ex) {
			throw ex;
		}
	}

	public static synchronized ConfigUtil getInstance() throws Exception {
		ConfigUtil conf = null;
		try {
			String ini = "/dbConfig.properties";
			conf = (ConfigUtil) configMap.get(ini);
			if (conf == null) {
				conf = new ConfigUtil(ini);
				configMap.put(ini, conf);
			}
		} catch (Exception e) {
			throw e;
		}
		return conf;
	}

	public static synchronized ConfigUtil getInstance(String fileName)
			throws Exception {
		ConfigUtil conf = null;
		try {
			conf = (ConfigUtil) configMap.get(fileName);
			if (conf == null) {
				conf = new ConfigUtil(fileName);
				configMap.put(fileName, conf);
			}
		} catch (Exception e) {
			throw e;
		}
		return conf;
	}

	public String getValue(String itemName, String defaultValue)
			throws Exception {
		return this.config.getProperty(itemName, defaultValue);
	}

	public String getValue(String itemName) {
		return this.config.getProperty(itemName);
	}

	public static Map getConfigMap() {
		return configMap;
	}

	public static void setConfigMap(Map configMap) {
		configMap = configMap;
	}

}
