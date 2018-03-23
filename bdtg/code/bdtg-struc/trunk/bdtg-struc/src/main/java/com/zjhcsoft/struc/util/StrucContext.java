package com.zjhcsoft.struc.util;

import java.util.HashMap;
import java.util.Map;

public class StrucContext {

	Map<String, Object> keyMap;

	public StrucContext() {
		keyMap = new HashMap<String, Object>();
	}

	public void put(String key, Object value) {
		keyMap.put(key, value);
	}

	public Object get(String key) {
		return keyMap.get(key);
	}

	public String getString(String key) {
		Object value = keyMap.get(key);
		return value == null ? null : value.toString();
	}

}
