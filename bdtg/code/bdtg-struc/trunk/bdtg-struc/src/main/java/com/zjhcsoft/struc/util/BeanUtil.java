package com.zjhcsoft.struc.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 对象操作
 * 
 * @author wangtao
 * @since 2014-7-16
 */
public class BeanUtil {
	/**
	 * 利用反射实现对象之间属性复制
	 * 
	 * @param from
	 * @param to
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public static void copyProperties(Object from, Object to) throws IllegalAccessException, InvocationTargetException {
		copyPropertiesExclude(from, to, null);
	}

	/**
	 * 复制对象属性
	 * 
	 * @param from
	 * @param to
	 * @param excludsArray
	 *            排除属性列表
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void copyPropertiesExclude(Object from, Object to, String[] excludsArray)
	        throws IllegalAccessException, InvocationTargetException {
		List<String> excludesList = null;
		if (excludsArray != null && excludsArray.length > 0) {
			excludesList = Arrays.asList(excludsArray); // 构造列表对象
		}
		Method[] fromMethods = from.getClass().getDeclaredMethods();
		Method[] toMethods = to.getClass().getDeclaredMethods();
		Method fromMethod = null, toMethod = null;
		String fromMethodName = null, toMethodName = null;
		for (int i = 0; i < fromMethods.length; i++) {
			fromMethod = fromMethods[i];
			fromMethodName = fromMethod.getName();
			if (!fromMethodName.contains("get"))
				continue;
			// 排除列表检测
			if (excludesList != null && excludesList.contains(fromMethodName.substring(3).toLowerCase())) {
				continue;
			}
			toMethodName = "set" + fromMethodName.substring(3);
			toMethod = findMethodByName(toMethods, toMethodName);
			if (toMethod == null)
				continue;
			Object value = fromMethod.invoke(from, new Object[0]);
			if (value == null)
				continue;
			// 集合类判空处理
			if (value instanceof Collection) {
				Collection newValue = (Collection) value;
				if (newValue.size() <= 0)
					continue;
			}
			toMethod.invoke(to, new Object[] { value });
		}
	}

	/**
	 * 对象属性值复制，仅复制指定名称的属性值
	 * 
	 * @param from
	 * @param to
	 * @param includsArray
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void copyPropertiesInclude(Object from, Object to, String[] includsArray)
	        throws IllegalAccessException, InvocationTargetException {
		List<String> includesList = null;
		if (includsArray != null && includsArray.length > 0) {
			includesList = Arrays.asList(includsArray); // 构造列表对象
		} else {
			return;
		}
		Method[] fromMethods = from.getClass().getDeclaredMethods();
		Method[] toMethods = to.getClass().getDeclaredMethods();
		Method fromMethod = null, toMethod = null;
		String fromMethodName = null, toMethodName = null;
		for (int i = 0; i < fromMethods.length; i++) {
			fromMethod = fromMethods[i];
			fromMethodName = fromMethod.getName();
			if (!fromMethodName.contains("get"))
				continue;
			// 排除列表检测
			String str = fromMethodName.substring(3);
			if (!includesList.contains(str.substring(0, 1).toLowerCase() + str.substring(1))) {
				continue;
			}
			toMethodName = "set" + fromMethodName.substring(3);
			toMethod = findMethodByName(toMethods, toMethodName);
			if (toMethod == null)
				continue;
			Object value = fromMethod.invoke(from, new Object[0]);
			if (value == null)
				continue;
			// 集合类判空处理
			if (value instanceof Collection) {
				Collection newValue = (Collection) value;
				if (newValue.size() <= 0)
					continue;
			}
			toMethod.invoke(to, new Object[] { value });
		}
	}

	/**
	 * 从方法数组中获取指定名称的方法
	 * 
	 * @param methods
	 * @param name
	 * @return
	 */
	public static Method findMethodByName(Method[] methods, String name) {
		for (int j = 0; j < methods.length; j++) {
			if (methods[j].getName().equals(name))
				return methods[j];
		}
		return null;
	}

	/**
	 * 打印对象的成员属性列表
	 * 
	 * @param obj
	 */
	public static void printFieldInfo(Object obj) {
		Map<String, Object> fieldMap = getFeildStringMap(obj);
		for (String fieldName : fieldMap.keySet()) {
			System.out.println(fieldName + " = " + fieldMap.get(fieldName));
		}
	}

	/**
	 * 提取对象的成员属性列表
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> getFeildStringMap(Object obj) {
		Field fields[] = obj.getClass().getDeclaredFields();
		Map<String, Object> fieldMap = new LinkedHashMap<>();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				fieldMap.put(field.getName(), field.get(obj));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return fieldMap;
	}
}