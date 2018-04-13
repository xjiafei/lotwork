package com.winterframework.firefrog.game.web.controller.utlis;

import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.ReflectionUtils;

public class GameCollectionUtils {

	/**
	 * 依照條件過濾集合
	 * @param paramName 物件比對的參數名稱
	 * @param condition 要符合的值
	 * @param list 集合
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> filterCollectionByCondition(final String paramName, final Object condition, List<T> list) throws Exception{
		try {
			if(CollectionUtils.isNotEmpty(list)){
				for(int i = 0 ; i < list.size() ; i++){
					T t = list.get(i);
					Object value = null;
					if(paramName.indexOf(".") > -1) {
						final String[] paramNames = paramName.split("\\.");
						value = mapEntityValue(t, paramNames, 0);
					} else
						value = findEntityValue(t, paramName);
					
					if(condition.equals(value))
						continue;
					else {
						list.remove(i);
						i--;
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	
	private static <T, R> R mapEntityValue(final T t, final String[] paramNames, final int idx) throws Exception{
		String name = paramNames[idx];
		R value = null;
		try{
			value = findEntityValue(t, name);
		} catch (NoSuchMethodException e) {
			//若取不到值, 則判斷為取方法
			if(null == value){
				value = (R) ReflectionUtils.invokeMethod(t.getClass().getMethod(name, null), t);
			}
		}
		if(idx == paramNames.length - 1){
			return value;
		} else {
			if(value instanceof Collection){
				return mapEntityValue(((List) value).get(0), paramNames, idx + 1);
			} else {
				return mapEntityValue(value, paramNames, idx + 1);
			}
		}
	}
	
	private static <T, R> R findEntityValue(final T t, final String paramName) throws Exception{
		@SuppressWarnings("unchecked")
		R r = (R) PropertyUtils.getProperty(t, paramName);
		return r;
	}
	
	/**
	 * 將集合內所定義的值 加總起來 (Long)
	 * @param collect 集合
	 * @param paramName 加總的元素
	 * @return
	 * @throws Exception
	 */
	public static <T> long sumLongValueByParam(final Collection<T> collect, final String paramName) throws Exception{
		if(CollectionUtils.isNotEmpty(collect)){
			long sumValue = 0;
			for(T t : collect){
				sumValue += (Long)PropertyUtils.getProperty(t, paramName);
			}
			return sumValue;
		} else {
			return 0;
		}
	}
}
