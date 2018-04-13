package com.winterframework.firefrog.beginmession.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.beginmession.annotation.LogContent;
import com.winterframework.firefrog.beginmession.annotation.MoneyField;
import com.winterframework.firefrog.beginmession.dao.BeginNewLogDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginLotterySet;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewLog;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.LogType;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.LotteryType;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.LotteryUnit;
import com.winterframework.firefrog.beginmession.service.BeginNewLogService;
import com.winterframework.firefrog.beginmession.utils.BeginMissionUtils;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

@Service
@Transactional
@Lazy(value=false)
public class BeginNewLogServiceImpl implements BeginNewLogService{
	
	private static Logger logger = LoggerFactory.getLogger(BeginNewLogServiceImpl.class);
	
	@Autowired
	private BeginNewLogDao beginNewLogDao;
	
	

	
	private final String orderColumn = "rownum";
	
	@Override
	public void insert(List<BeginNewLog> logs) {
		beginNewLogDao.insert(logs); 
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> void compareBforeAfter(T befor, T after,LogType type,String logUser) throws Exception {
		Class beforeClass=  befor.getClass();
		Field[] beforeFields= beforeClass.getDeclaredFields();
		Class afterClass = after.getClass();
		
		//是否需要顯示序號
		String orderSeq = "";
		//是否需要顯示序號
		String lotterySetTitle = "";
		try{
			Field rowNumField= afterClass.getDeclaredField(orderColumn);
			@SuppressWarnings("unchecked")
			Method orderMethod= afterClass.getMethod(BeginMissionUtils.getMethodName(rowNumField), new Class[0]);
			Object orderVal = orderMethod.invoke(after, new Object[0]);
			if(orderVal!=null){
				orderSeq = orderVal.toString();
			}
		}catch(Exception e){
		}
		
		if(befor instanceof BeginLotterySet){
			Field  field = befor.getClass().getDeclaredField("lotteryType");
			@SuppressWarnings("unchecked")
			Method getMeothd = afterClass.getMethod(BeginMissionUtils.getMethodName(field), new Class[0]);
			Object val = getMeothd.invoke(after, new Object[0]);
			lotterySetTitle = LotteryType.mapping((Long)val).getText();
		}
		
		
		for(Field beforeField:beforeFields){
			try {
				if(beforeField.isAnnotationPresent(LogContent.class)){
					LogContent contentAnnoatation = beforeField.getAnnotation(LogContent.class);
					BeginNewLog log = new BeginNewLog();
					log.setLogType(type.getType());
					log.setLogTime(DateUtils.currentDate());
					log.setLogUser(logUser);
					@SuppressWarnings("unchecked")
					Method afterGet = afterClass.getMethod(BeginMissionUtils.getMethodName(beforeField), new Class[0]);
					@SuppressWarnings("unchecked")
					Method beforeGet = beforeClass.getMethod(BeginMissionUtils.getMethodName(beforeField), new Class[0]);
					Object beforeVal = beforeGet.invoke(befor, new Object[0]);
					Object afterVal = afterGet.invoke(after, new Object[0]);
					Boolean isUpdate = false;
					if(beforeVal!=null && afterVal!=null){
						//若變更前與變更後不同
						if(!beforeVal.equals(afterVal)){
							isUpdate = true;
							log.setBeforeUpdate(getLogValue(contentAnnoatation,beforeVal,beforeField));
							log.setAfterUpdate(getLogValue(contentAnnoatation,afterVal,beforeField));						
						}
					}else if(beforeVal==null && afterVal!=null){
						isUpdate = true;
						log.setAfterUpdate(getLogValue(contentAnnoatation,afterVal,beforeField));						
					}else if(beforeVal!=null && afterVal==null){
						isUpdate = true;
						log.setBeforeUpdate(getLogValue(contentAnnoatation,beforeVal,beforeField));				
					}
					log.setTitle(lotterySetTitle+beforeField.getAnnotation(LogContent.class).title()+orderSeq);					
					
					
					if(isUpdate){
						beginNewLogDao.insert(log);							
					}

				}
			} catch (Exception e) {
				logger.error("compareBforeAfter err field ="+beforeField.getName() );
			}
		}
	}
	
	
	private String getLogValue(LogContent annotation,Object val,Field field){
		if(val==null){
			return "";
		}
		String str = val.toString();
		if(field.isAnnotationPresent(MoneyField.class)){
			Long lonVal=(Long)val;
			str =  lonVal.toString();
		}else{
			if(annotation.lotteryType()){
				str =  LotteryType.mapping((Long)val).getText();
			}else if(annotation.lotteryUnit()){
				str =  LotteryUnit.mapping((Long)val).getText();
			}			
		}

		return str;
	}
	
	@Override
	public Page<BeginNewLog> getAllByPage(
			PageRequest<BeginNewLog> pageRequest) throws Exception {
		Page<BeginNewLog> page= beginNewLogDao.selectByPage(pageRequest);
		return page;
	}
	
}