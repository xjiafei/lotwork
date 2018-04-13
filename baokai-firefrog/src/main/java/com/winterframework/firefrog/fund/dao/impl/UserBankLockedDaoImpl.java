/**   
* @Title: UserBankLockedDaoImpl.java 
* @Package com.winterframework.firefrog.fund.dao.impl 
* @Description: 银行卡管理锁定状态操作DAO 
* @author Denny  
* @date 2013-7-2 下午4:17:29 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.IUserBankLockedDao;
import com.winterframework.firefrog.fund.dao.vo.UserBankLocked;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: UserBankLockedDaoImpl 
* @Description: 银行卡管理锁定状态操作DAO 
* @author Denny 
* @date 2013-7-2 下午4:17:29 
*  
*/
@Repository("userBankLockedDaoImpl")
public class UserBankLockedDaoImpl extends BaseIbatis3Dao<UserBankLocked> implements IUserBankLockedDao {
	@Override
	public void addUserBankLocked(UserBankLocked ubl) {
		this.insert(ubl);
	}

	@Override
	public void deleteLock(Long userId) {
		sqlSessionTemplate.selectOne("deleteLockByUserId", userId);
	
	}
	
	@Override
	public long getOverTime(long userId,long bindcardType) {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("bindcardType", bindcardType);
		Date d = (Date) sqlSessionTemplate.selectOne("getOverTimeByUserId",map);
		if (d != null) {
			return d.getTime();
		}
		return 0;
	}
	
	@Override
	public long queryDBNowTime(){
		Date d = (Date) sqlSessionTemplate.selectOne("queryDBNowTime");
		if (d != null) {
			return d.getTime();
		}
		return 0;
	}
	
	@Override
	public void userCardLocking(String operator, Date overTime, Long lockId) {
		UserBankLocked vo = new UserBankLocked();
		vo.setId(lockId);
		vo.setOperator(operator);
		vo.setOverTime(overTime);
		this.update(vo);
	}
	
	@Override
	public void userCardLocking(String operator, Long userId) {
		UserBankLocked vo = new UserBankLocked();
		vo.setOperator(operator);
		vo.setUserId(userId);
		sqlSessionTemplate.update("updateByUserId", vo);
	}
	
	@Override
	public Date queryStatusByLockID(Long id) {
		Date d = (Date) sqlSessionTemplate.selectOne("getOverTimeByLockId", id);
		return d;
	}

	@Override
	public boolean firstLock(Long userId,Long bindcardType) {
		UserBankLocked entity = new UserBankLocked();
		entity.setUserId(userId);
		entity.setBindcardType(bindcardType);
		long count = this.getCountByEntity(entity);
		return count == 0l ? true : false;
	}

	@Override
	public void updateOverTime(long userId) {
		sqlSessionTemplate.update(this.getQueryPath("updateOverTime"),userId);
	}
	
	@Override
	public void updateCardTime(String userId) {
		sqlSessionTemplate.update(this.getQueryPath("updateCardTime"),userId);
	}
}
