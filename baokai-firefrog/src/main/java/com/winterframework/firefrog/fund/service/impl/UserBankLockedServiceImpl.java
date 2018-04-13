/**   
* @Title: UserBankLockedServiceImpl.java 
* @Package com.winterframework.firefrog.fund.service.impl 
* @Description: 银行卡管理锁定状态操作Service
* @author Denny  
* @date 2013-7-2 下午4:25:58 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.beginmession.service.BeginMissionService;
import com.winterframework.firefrog.fund.dao.IBankCardDao;
import com.winterframework.firefrog.fund.dao.IUserBankLockedDao;
import com.winterframework.firefrog.fund.dao.vo.UserBank;
import com.winterframework.firefrog.fund.dao.vo.UserBankLocked;
import com.winterframework.firefrog.fund.service.IUserBankLockedService;

/** 
* @ClassName: UserBankLockedServiceImpl 
* @Description: 银行卡管理锁定状态操作Service
* @author Denny 
* @date 2013-7-2 下午4:25:58 
*  
*/
@Service("userBankLockedServiceImpl")
public class UserBankLockedServiceImpl implements IUserBankLockedService {
	@Resource(name = "userBankLockedDaoImpl")
	private IUserBankLockedDao userBankLockedDao;
	@Autowired
	private IBankCardDao ubd;
	@Autowired
	private BeginMissionService beginMissionServiceImpl;
	
	@Override
	public void addLocked(long userId,long bindcardType) {
		UserBankLocked ubl = new UserBankLocked();
		ubl.setUserId(userId);
		Date overTime = new Date(new Date().getTime() + 60 * 60 * 1000);
		ubl.setOverTime(bindcardType==1?null:overTime);
		ubl.setBindcardType(bindcardType);
		userBankLockedDao.addUserBankLocked(ubl);
	}

	@Override
	public boolean isTimeOut(long userId,long bindcardType) {
		long overTime = userBankLockedDao.getOverTime(userId,bindcardType);
		long currentTime = new Date().getTime();
		return overTime==0?false:(currentTime > overTime);
	}

	@Override
	public long queryOverTime(long userId,long bindcardType) {
		return userBankLockedDao.getOverTime(userId,bindcardType);
	}
	
	@Override
	public long queryDBNowTime(){
		return userBankLockedDao.queryDBNowTime();
	}

	@Override
	public void userCardLocking(String operator, Date overTime, Long lockId) {
		userBankLockedDao.userCardLocking(operator, overTime, lockId);
	}

	@Override
	public void updateOverTime(Long userId){
		userBankLockedDao.updateOverTime(userId);
		//新手任務立即綁卡
		beginMissionServiceImpl.bankCardLock(userId);
	}
	
	@Override
	public Long queryStatusByLockID(Long lockId) {
		long overTime = userBankLockedDao.queryStatusByLockID(lockId).getTime();
		long currentTime = new Date().getTime();
		return currentTime < overTime ? 1L : 2L;
	}

	@Override
	public boolean isFirstLock(Long userId,Long bindcardType) {
		boolean isFirst = userBankLockedDao.firstLock(userId,bindcardType);
		if (!isFirst) {
			UserBank bank = new UserBank();
			bank.setUserId(userId);
			bank.setBindcardType(bindcardType);
			Long count = ubd.getCountByEntity(bank);
			if (count == 0) {
				//如果不是第一次别当定，而且以前绑定次数为0的话
				//userBankLockedDao.deleteLock(userId);
				//update 操作人為null
				userBankLockedDao.userCardLocking(null, userId);
				return false;
			}
		}
		return isFirst;
	}
	
	@Override
	public boolean isHaveCard(Long userId,Long bindcardType) {
		
		UserBank bank = new UserBank();
		bank.setUserId(userId);
		bank.setBindcardType(bindcardType);
		Long count = ubd.getCountByEntity(bank);
		if(count == 0){
			return false;
		}else{
			return true;
		}
	}
}
