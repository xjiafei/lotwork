package com.winterframework.firefrog.beginmession.utils;

import java.lang.reflect.Field;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.winterframework.firefrog.beginmession.annotation.MoneyField;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.LogType;
import com.winterframework.firefrog.beginmession.service.BeginNewLogService;

@Component
public class BeginMissionUtils {
	
	private static Logger log = LoggerFactory.getLogger(BeginMissionUtils.class);
	
	public static final Long calcuteUnit = 10000l;
	
	private static BeginNewLogService beginNewLogServiceImpl;
	
	private static final String getMethodPrefix = "get";

	private static final String setMethodPrefix = "set";
	
	/**
	 * 針對新手任務畫面多筆資料進行準備
	 * @param optional
	 * @param pageSize
	 * @param entityClass
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> createPageList(Optional<List<T>> optional , int pageSize,Class<T> entityClass) throws Exception{
		 //每日投注
		 if(optional.isPresent() && optional.get().size()>0){
			 List<T> lists = Lists.newArrayList();
			 for(T entity:optional.get()){
				 lists.add(BeginMissionUtils.convertSelectAmtField(entity));
			 }
			 return lists;
		 }else{
			 List<T> lists = Lists.newArrayList();
			 for(int i=1;i<=pageSize;i++){
				 lists.add(entityClass.newInstance());
			} 
			 return lists;
		 }
	}
	
	/**
	 * 針對新手任務單一物件異動資料寫入Log
	 * @param <T>
	 * @param optional
	 * @param pageSize
	 * @param entityClass
	 * @return
	 * @throws Exception
	 */
	public static <T> void logBeginMissionPageOneRow(Optional<List<T>> begoreOptional,T afterEntity ,LogType type,String updateUser,Class<T> entityClass) throws Exception{
		
		 //每日投注
		 if(begoreOptional.isPresent() && begoreOptional.get().size()>0){
			 beginNewLogServiceImpl.compareBforeAfter(BeginMissionUtils.convertSelectAmtField(begoreOptional.get().get(0)), BeginMissionUtils.convertSelectAmtField(afterEntity), type
						,updateUser);		
		 }else{
			 T entity = entityClass.newInstance();
			 beginNewLogServiceImpl.compareBforeAfter(entity, BeginMissionUtils.convertSelectAmtField(afterEntity), type,updateUser);	
		 }
	}
	
	/**
	 * 針對新手任務多筆資料寫入Log
	 * @param optional
	 * @param pageSize
	 * @param entityClass
	 * @return
	 * @throws Exception
	 */
	public static <T> void logBeginMissionPageManyRow(Optional<List<T>> begoreOptional,List<T> afterList ,LogType type,String updateUser,Class<T> entityClass) throws Exception{
		
		if(begoreOptional.isPresent() && begoreOptional.get().size()==afterList.size()){
			for(int i =0 ;i <afterList.size() ;i++){
				T beforeEntity = begoreOptional.get().get(i);
				T afterEntity = afterList.get(i);
				
				beginNewLogServiceImpl.compareBforeAfter(BeginMissionUtils.convertSelectAmtField(beforeEntity), BeginMissionUtils.convertSelectAmtField(afterEntity), type,updateUser);
			}
		}else{
			for(int i =0 ;i < afterList.size() ;i++){
				T beforeEntity = null;
				if(i<begoreOptional.get().size()-1){
					beforeEntity = begoreOptional.get().get(i);	
				}else{
					beforeEntity = entityClass.newInstance();
				}
				T afterEntity = afterList.get(i);
				beginNewLogServiceImpl.compareBforeAfter(BeginMissionUtils.convertSelectAmtField(beforeEntity), BeginMissionUtils.convertSelectAmtField(afterEntity), type,updateUser);
			}
		}
	}
	
	public static<T> T convertInsertAmtField(T entity){
		try {
			Class entityClass= entity.getClass();
			Field[] fields = entityClass.getDeclaredFields();
			for(Field field: fields){
				if(field.isAnnotationPresent(MoneyField.class)){
					field.setAccessible(true);
					Object value = field.get(entity);
					if(value!=null){
						field.set(entity, (Long)value*calcuteUnit);
					}
					field.setAccessible(false);
				}
			}
		} catch (Exception e) {
			log.error(entity.getClass()+" parse Amt error");
		}
		return entity;
	}
	
	public static<T> T convertSelectAmtField(T entity){
		try{
		Class entityClass= entity.getClass();
		Field[] fields = entityClass.getDeclaredFields();
		for(Field field: fields){
			if(field.isAnnotationPresent(MoneyField.class)){
				field.setAccessible(true);
				Object value = field.get(entity);
				if(value!=null){
					field.set(entity, (Long)value/calcuteUnit);
				}
				field.setAccessible(false);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
			log.error(entity.getClass()+" parse Amt error");
		}
		return entity;
	}
	

	public BeginNewLogService getBeginNewLogServiceImpl() {
		return beginNewLogServiceImpl;
	}
	
	@Autowired
	public void setBeginNewLogServiceImpl(BeginNewLogService beginNewLogServiceImpl) {
		BeginMissionUtils.beginNewLogServiceImpl = beginNewLogServiceImpl;
	}
	
	
	public static String getMethodName(Field field){
		return getMethodPrefix+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1);
	}
	
	public static String setMethodName(Field field){
		return setMethodPrefix+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1);
	}
	
	
}
