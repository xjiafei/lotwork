/**   
* @Title: IUserBankLockedDao.java 
* @Package com.winterframework.firefrog.fund.dao 
* @Description: 银行卡管理锁定状态操作DAO接口
* @author Denny  
* @date 2013-7-2 下午4:13:31 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao;

import java.util.Date;

import com.winterframework.firefrog.fund.dao.vo.UserBankLocked;

/** 
* @ClassName: IUserBankLockedDao 
* @Description: 银行卡管理锁定状态操作DAO接口
* @author Denny 
* @date 2013-7-2 下午4:13:31 
*  
*/
public interface IUserBankLockedDao {

	/**
	 * 
	* @Title: addUserBankLocked 
	* @Description: 添加锁定数据
	 */
	public void addUserBankLocked(UserBankLocked ubl);
	
	public boolean firstLock(Long userId,Long bindcardType);
	public void deleteLock(Long userId);

	/**
	 * 
	* @Title: getOverTime 
	* @Description: 查询超时时间
	 */
	public long getOverTime(long userId,long bindcardType);

	/**
	 * 
	* @Title: userCardLocking 
	* @Description: 后台管理员对用户绑卡加解锁
	* @param operator
	* @param overTime
	* @param lockId
	* @throws Exception
	 */
	public void userCardLocking(String operator, Date overTime, Long lockId);

	/**
	 * 
	* @Title: userCardLocking 
	* @Description: 更新操作人
	* @param operator
	* @param userId
	* @throws Exception
	 */
	public void userCardLocking(String operator, Long userId) ;
	/**
	 * 
	* @Title: queryStatusByLockID 
	* @Description: 根据锁定ID查询对应的overTime
	* @param lockId
	* @return
	 */
	public Date queryStatusByLockID(Long lockId);
	
	public void updateOverTime(long userId);
	
	public long queryDBNowTime();
	
	public void updateCardTime(String userId);
}
