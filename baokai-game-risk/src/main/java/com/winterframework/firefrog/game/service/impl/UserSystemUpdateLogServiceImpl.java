package com.winterframework.firefrog.game.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IUserSystemUpdateLogDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.UserSystemUpdateLog;
import com.winterframework.firefrog.game.service.IUserSystemUpdateLogService;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName UserSystemUpdateLogServiceImpl 
* @Description 用户升级日志 
* @author  hugh
* @date 2014年12月2日 下午3:33:15 
*  
*/
@Service("userSystemUpdateLogServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class UserSystemUpdateLogServiceImpl implements IUserSystemUpdateLogService{
	
	@Resource(name = "userSystemUpdateLogDaoImpl")
	private IUserSystemUpdateLogDao userSystemUpdateLogDaoImpl; 
	
	@PropertyConfig(value = "betActivityStartTime" ,required = false)
	private String betActivityStartTime;
	
	@PropertyConfig(value = "betActivityEndTime" ,required = false)
	private String betActivityEndTime;
	
	public UserSystemUpdateLog getUserUpdateSystemByUserId(Long userId) throws Exception{
		return userSystemUpdateLogDaoImpl.getById(userId);
	}
	
	
	public void addUserBet(Long userId) throws Exception{
		Date start = DateUtils.parse(betActivityStartTime);
		Date end = DateUtils.parse(betActivityEndTime);
		Date now = new Date();
		if(start != null && end != null && now.before(end) && now.after(start)){
			userUpdateSystem(userId, USER_BET_TYPE);
		}		 
	}
	
	public String userUpdateSystem(Long userId, Integer type) throws Exception{
		UserSystemUpdateLog log = userSystemUpdateLogDaoImpl.getById(userId);
		boolean flag = true;
		Date now = new Date();
		if(log == null){
			flag = false;
			log = new UserSystemUpdateLog();
			log.setUserId(userId);
			log.setGmtCreated(now);
		}
		log.setGmtModified(now);
		if(USER_UPDATE_SYSTEM_TYPE == type){
			if(log.getIsUpdate().longValue() == YES){
				return "have done more one time";
			}
			log.setIsUpdate(YES);
			log.setUpdateTime(now);
		}else if(USER_RECHARGE_TYPE == type){
			if(log.getIsRecharge().longValue() == YES){
				return "have done more one time";
			}
			log.setIsRecharge(YES);
			log.setRechargeTime(now);
		}else if(USER_BET_TYPE == type){
			if(log.getIsBet().longValue() == YES){
				return "have done more one time";
			}
			log.setIsBet(YES);
			log.setBetTime(now);
		}else{
			throw new Exception("fail :not right type");
		}
		
		if(flag){
			userSystemUpdateLogDaoImpl.update(log);
		}else{
			userSystemUpdateLogDaoImpl.insert(log);
		}
		return "success";
	}
	
	private final long YES = 1l;
	private final int USER_UPDATE_SYSTEM_TYPE = 1;
	private final int USER_RECHARGE_TYPE = 2;
	private final int USER_BET_TYPE = 3;
}
