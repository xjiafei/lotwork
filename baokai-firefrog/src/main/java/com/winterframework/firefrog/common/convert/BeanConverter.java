package com.winterframework.firefrog.common.convert;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.fund.entity.FundStatus;

/**
 * 
* @ClassName: BeanConverter 
* @Description: Bean转换工具类，支持自动转换同类型的相同字段名和不同字段名（需通过注解）属性，以及通用类型转换（如Date转换成Long）。
* @author Tod
* @date 2013-7-18 下午3:56:13 
*
 */
public class BeanConverter {

	private static Logger logger = LoggerFactory.getLogger(BeanConverter.class);

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	public @interface Alias {
		public String field();
	}

	/**
	 * 
	* @Title: convert 
	* @Description: 把obj2的属性赋值给obj1
	* @param obj1
	* @param obj2
	* @throws Exception
	 */
	public static void convert(Object obj1, Object obj2) throws Exception {
		Map<String, String> map1 = parseField(obj1);
		Map<String, String> map2 = parseField(obj2);

		if (!map1.isEmpty() && !map2.isEmpty()) {
			String s1 = map1.get("aliasFlag");
			if (s1.equals("false")) {
				map1.clear();
			} else {
				map2.clear();
			}
		}

		if (!map2.isEmpty()) {
			for (Entry<String, String> e : map2.entrySet()) {
				String getterName = e.getKey();
				String setterName = e.getValue();
				singleConvert(getterName, setterName, obj1, obj2);
			}
		} else if (!map1.isEmpty()) {
			for (Entry<String, String> e : map1.entrySet()) {
				String setterName = e.getKey();
				String getterName = e.getValue();
				singleConvert(getterName, setterName, obj1, obj2);
			}
		}
	}

	private static void singleConvert(String getterName, String setterName, Object obj1, Object obj2) {
		 String mSetterName=null;
		try {
			mSetterName = "set" + Character.toUpperCase(setterName.charAt(0)) + setterName.substring(1);
			String mGetterName = "get" + Character.toUpperCase(getterName.charAt(0)) + getterName.substring(1);
			Method m1 = getMethodByName(obj1.getClass().getMethods(), mSetterName);
			Method m2 = getMethodByName(obj2.getClass().getMethods(), mGetterName);
			if (m2 == null || m1 == null) {
				return;
			}
			Object val = m2.invoke(obj2);

			try {
				Field f1 = null, f2 = null;
				try {
					f1 = obj1.getClass().getDeclaredField(setterName);
				} catch (Exception e1) {
					f1 = obj1.getClass().getSuperclass().getDeclaredField(setterName);
				}
				try {
					f2 = obj2.getClass().getDeclaredField(getterName);
				} catch (Exception e1) {
					f2 = obj2.getClass().getSuperclass().getDeclaredField(getterName);
				}
				if (f2.getType().equals(Date.class) && f1.getType().equals(Long.class)) {
					val = DataConverterUtil.convertDate2Long((Date) val);
				}else if (f1.getType().equals(Date.class) && f2.getType().equals(Long.class)) {
					val = DataConverterUtil.convertLong2Date((Long) val);
				}else if (f1.getType().isEnum() && f1.getType().getInterfaces()[0].equals(FundStatus.class) && f1.getType().equals(Long.class)) {
					val =((FundStatus)val).getStatis();
				}
				
			} catch (Exception e1) {
				logger.error("object convert error.", e1);
			}
			if(val!=null)
			m1.invoke(obj1, val);
		} catch (Exception e) {
			logger.error("field:["+mSetterName+"]object convert error.", e);
		}
	}

	private static Map<String, String> parseField(Object obj) {
		Class<?> clazz = obj.getClass();
		Field[] fields1 = clazz.getDeclaredFields();
		Field[] fields2 = clazz.getSuperclass().getDeclaredFields();
		Map<String, String> map = new HashMap<String, String>();
		boolean isAlias = false;
		for (Field f : fields1) {
			Annotation ano = f.getAnnotation(Alias.class);
			String original = f.getName();
			String alias = "";
			if (ano != null) {
				alias = ((Alias) ano).field();
				isAlias = true;
			} else {
				alias = original;
			}
			map.put(original, alias);
		}
		for (Field f : fields2) {
			Annotation ano = f.getAnnotation(Alias.class);
			String original = f.getName();
			String alias = "";
			if (ano != null) {
				alias = ((Alias) ano).field();
				isAlias = true;
			} else {
				alias = original;
			}
			map.put(original, alias);
		}
		map.put("aliasFlag", String.valueOf(isAlias));
		return map;
	}

	/**
	 * 在一个方法对象数组里面通过方法名获得方法
	 * 
	 * @param methods
	 * @param methodName
	 * @return
	 */
	private static Method getMethodByName(Method[] methods, String methodName) {
		for (Method m : methods) {
			if (m.getName().equals(methodName)) {
				return m;
			}
		}
		return null;
	}
}
