package net.ryian.commons;

import org.apache.log4j.Logger;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Bean工具类，对org.apache.commons.beanutils.BeanUtils的扩展
 * 
 * @author: wangcheng
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
	protected static final Logger logger = Logger.getLogger(BeanUtils.class);

	public static Field getDeclaredField(Object object, String propertyName)
			throws NoSuchFieldException {
		return getDeclaredField(object.getClass(), propertyName);
	}

	public static Field getDeclaredField(Class clazz, String propertyName)
			throws NoSuchFieldException {
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(propertyName);
			} catch (NoSuchFieldException e) {
				logger.error(e);
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName()
				+ '.' + propertyName);
	}

	public static Object forceGetProperty(Object object, String propertyName)
			throws NoSuchFieldException {
		Field field = getDeclaredField(object, propertyName);

		boolean accessible = field.isAccessible();
		field.setAccessible(true);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.info("error wont' happen");
		}
		field.setAccessible(accessible);
		return result;
	}

	public static void forceSetProperty(Object object, String propertyName,
			Object newValue) throws NoSuchFieldException {
		Field field = getDeclaredField(object, propertyName);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		try {
			field.set(object, newValue);
		} catch (IllegalAccessException e) {
			logger.info("Error won't happen");
		}
		field.setAccessible(accessible);
	}

	public static Object invokePrivateMethod(Object object, String methodName,
			Object... params) throws NoSuchMethodException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Class[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			types[i] = params[i].getClass();
		}

		Class<?> clazz = object.getClass();
		Method method = null;
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				method = superClass.getDeclaredMethod(methodName, types);
				break;
			} catch (NoSuchMethodException e) {
				logger.error(e);
			}
		}

		if (method == null)
			throw new NoSuchMethodException("No Such Method:"
					+ clazz.getSimpleName() + methodName);

		boolean accessible = method.isAccessible();
		method.setAccessible(true);
		Object result = method.invoke(object, params);
		method.setAccessible(accessible);
		return result;
	}

	public static List<Field> getFieldsByType(Object object, Class type) {
		List<Field> list = new ArrayList<Field>();
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getType().isAssignableFrom(type)) {
				list.add(field);
			}
		}
		return list;
	}

	public static Class getPropertyType(Class type, String name)
			throws NoSuchFieldException {
		return getDeclaredField(type, name).getType();
	}

	public static String getGetterName(Class type, String fieldName)
			throws NoSuchFieldException {
		Class clazz = getPropertyType(type, fieldName);
		if (clazz.getName().equals("boolean") || clazz.equals(Boolean.class)) {
			return "is" + StringUtils.capitalize(fieldName);
		} else {
			return "get" + StringUtils.capitalize(fieldName);
		}
	}

	public static Method getGetterMethod(Class<?> type, String fieldName)
			throws SecurityException, NoSuchFieldException {
		try {
			return type.getMethod(getGetterName(type, fieldName));
		} catch (NoSuchMethodException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static String getSetterName(String fieldName) {
		return "set" + StringUtils.capitalize(fieldName);
	}

	@SuppressWarnings("unchecked")
	public static <T> void setBeanPropertyByName(T entity, String propertyName,
			String propertyValue) throws Exception {
		try {
			if (!describe(entity).containsKey(propertyName))
				return;

			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(
					propertyName, entity.getClass());
			Class propertyType = propertyDescriptor.getPropertyType();
			Method setMethod = propertyDescriptor.getWriteMethod();

			// String型设置为""，而不是null
			if (propertyType == String.class) {
				setMethod.invoke(entity, propertyValue);
				return;
			}

			if (StringUtils.isBlank(propertyValue))
				return;

			if (propertyType == Long.class) {
				setMethod.invoke(entity, Long.parseLong(propertyValue));
			} else if (propertyType == Byte.class) {
				setMethod.invoke(entity, Byte.parseByte(propertyValue));
			} else if (propertyType == Integer.class) {
				setMethod.invoke(entity, Integer.parseInt(propertyValue));
			} else if (propertyType == Short.class) {
				setMethod.invoke(entity, Short.parseShort(propertyValue));
			} else if (propertyType == Float.class) {
				setMethod.invoke(entity, Float.parseFloat(propertyValue));
			} else if (propertyType == Double.class) {
				setMethod.invoke(entity, Double.parseDouble(propertyValue));
			} else if (propertyType == BigDecimal.class) {
				setMethod.invoke(entity, new BigDecimal(propertyValue));
			} else if (propertyType == java.util.Date.class) {
				setMethod.invoke(entity,
						DateUtils.stringToUtilDate(propertyValue));
			} else if (propertyType == java.sql.Date.class) {
				setMethod.invoke(entity,
						DateUtils.stringToSqlDate(propertyValue));
			}
		} catch (Exception e) {
			throw new Exception("根据属性名称给POJO赋值时，出现异常：" + "[" + propertyName
					+ ": " + propertyValue + "]");
		}
	}

	/**
	 * 将类名根据驼峰规则用指定的分割符分割
	 * @param className
	 * @param separator
	 * @param lowerCase
	 * @return
	 */
	public static String separateClassName(String className,String separator,boolean lowerCase) {
		String actionName = className;

		// Convert to underscores
		char[] ca = actionName.toCharArray();
		StringBuilder build = new StringBuilder("" + ca[0]);
		boolean lower = true;
		for (int i = 1; i < ca.length; i++) {
			char c = ca[i];
			if (Character.isUpperCase(c) && lower) {
				build.append(separator);
				lower = false;
			} else if (!Character.isUpperCase(c)) {
				lower = true;
			}

			build.append(c);
		}

		actionName = build.toString();
		if (lowerCase) {
			actionName = actionName.toLowerCase();
		}

		return actionName;
	}

}
