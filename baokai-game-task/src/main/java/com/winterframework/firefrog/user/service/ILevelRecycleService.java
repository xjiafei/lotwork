package com.winterframework.firefrog.user.service;

import java.util.List;

import com.winterframework.firefrog.fund.entity.BankCard;
import com.winterframework.firefrog.schedule.dto.LevelRecycleRequest;
import com.winterframework.firefrog.schedule.dto.QueryLevelRecycleHistoryRequest;
import com.winterframework.firefrog.user.entity.LevelRecycle;
import com.winterframework.firefrog.user.entity.LevelRecycle.RecycleStatus;


/**
 * 
 * 类功能说明: 一代回收TASK
 */
public interface ILevelRecycleService {
	
	/**
	 * 
	 * 方法描述：一代回收纪录 
	 * @throws Exception
	 */
	public List<LevelRecycle> queryLevelRecycleHistory(LevelRecycle levelRecycle)throws Exception;
	
	/**
	 * 
	 * 方法描述：检查一代回收密码未修改
	 * @throws Exception
	 */
	public boolean isLevelRecycleFirstLogin(QueryLevelRecycleHistoryRequest queryLRHistoryRequest) throws Exception;
	
	/**
	 * 
	 * 方法描述：清除奖金组
	 * @throws Exception
	 */
	public void cleanAwardGroup(LevelRecycleRequest levelRecycleRequest) throws Exception;
	
	/**
	 * 
	 * 方法描述：清理安全中心
	 * @throws Exception
	 */
	public void cleanSafeCenter(LevelRecycleRequest levelRecycleRequest) throws Exception;
	
	/**
	 * 
	 * 方法描述：清理个人资料
	 * @throws Exception
	 */
	public void cleanPersonalInfo(LevelRecycleRequest levelRecycleRequest) throws Exception;
	
	/**
	 * 
	 * 方法描述：清理银行卡信息
	 * @throws Exception
	 */
	public void cleanBindCard(long bindId,long userId, long bankId, long mcBankI) throws Exception;
	
	/**
	 * 
	 * 方法描述：清理投注纪录
	 * @throws Exception
	 */
	public void cleanOrderHistory(LevelRecycleRequest levelRecycleRequest) throws Exception;
	
	/**
	 * 
	 * 方法描述：清理站内信
	 * @throws Exception
	 */
	public void cleanUserMessage(LevelRecycleRequest levelRecycleRequest) throws Exception;
	
	/**
	 * 
	 * 方法描述：重置PT密码
	 * @throws Exception
	 */
	public void resetPtPassword(LevelRecycleRequest levelRecycleRequest) throws Exception;
	
	/**
	 * 
	 * 方法描述：重置平台登录密码
	 * @throws Exception
	 */
	public void resetLoginPassword(LevelRecycleRequest levelRecycleRequest) throws Exception;
	
	/**
	 * 
	 * 方法描述： 查询绑卡 
	 * @throws Exception 
	 */
	public List<BankCard> queryBoundBankCardList (long userId, String cardNumber) throws Exception;
	
	/**
	 * 
	 * 方法描述： 更新TASK状态
	 */
	public void updateTaskStatus (RecycleStatus taskStatus, Long levelRecycleId);
	
	/**
	 * 
	 * 方法描述： 更新recycle状态
	 * @throws Exception 
	 */
	public void updateRecycleStatus (LevelRecycle recycleUser) throws Exception;
			
}
