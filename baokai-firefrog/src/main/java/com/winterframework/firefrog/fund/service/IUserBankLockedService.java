/**   
* @Title: IUserBankLockedService.java 
* @Package com.winterframework.firefrog.fund.service 
* @Description: 银行卡管理锁定状态操作Service接口 
* @author Denny  
* @date 2013-7-2 下午4:21:00 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service;

import java.util.Date;

/** 
* @ClassName: IUserBankLockedService 
* @Description: 银行卡管理锁定状态操作Service接口 
* @author Denny 
* @date 2013-7-2 下午4:21:00 
*  
*/
public interface IUserBankLockedService {

	/**
	 * 
	* @Title: addLocked 
	* @Description: 添加锁定状态
	 */
	public void addLocked(long userId,long bindcardType);

	/**
	 * 
	* @Title: isTimeOut 
	* @Description: 查询是否超时
	 */
	public boolean isTimeOut(long userId,long bindcardType);

	/** 
	* @Title: queryOverTime 
	* @Description: 查询超时时间 
	*/
	public long queryOverTime(long userId,long bindcardType);

	/**
	 * 
	* @Title: userCardLocking 
	* @Description: 对绑定状态加解锁
	* @param operator
	* @param overTime
	* @param lockId
	 */
	public void userCardLocking(String operator, Date overTime, Long lockId);

	/**
	 * 
	* @Title: queryStatusByLockID 
	* @Description: 根据锁定ID得到对应的状态
	* @param lockId
	* @return
	 */
	public Long queryStatusByLockID(Long lockId);
	
	public boolean isFirstLock(Long userId,Long bindcardType);
	
	public void updateOverTime(Long userId);
	
	public long queryDBNowTime();
	
	/**
	 * 
	* @Title: isHaveCard 
	* @Description: 取得是否有綁卡
	* @param userid bindcardType
	* @return
	 */
	public boolean isHaveCard(Long userId,Long bindcardType);

}
