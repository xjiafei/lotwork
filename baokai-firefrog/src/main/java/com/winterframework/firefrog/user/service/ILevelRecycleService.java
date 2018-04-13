package com.winterframework.firefrog.user.service;

import java.util.List;

import com.winterframework.firefrog.fund.entity.BankCard;
import com.winterframework.firefrog.user.entity.LevelRecycleDTO;
import com.winterframework.firefrog.user.web.dto.ApplyLevelRecycleRequest;
import com.winterframework.firefrog.user.web.dto.LevelRecycleRequest;
import com.winterframework.firefrog.user.web.dto.QueryLevelRecycleHistoryRequest;
import com.winterframework.firefrog.user.web.dto.QueryLevelRecycleHistoryResponse;
import com.winterframework.firefrog.user.web.dto.QueryLevelRecycleListRequest;
import com.winterframework.firefrog.user.web.dto.QueryLevelRecycleListResponse;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Response;


/**
 * 
 * 类功能说明: 一代回收
 */
public interface ILevelRecycleService {

	/**
	 * 
	 * 方法描述：一代回收纪录
	 * @param  pageReqeust
	 * @throws Exception
	 */
	public Response<List<QueryLevelRecycleHistoryResponse>> queryLevelRecycleHistory(PageRequest<LevelRecycleDTO> pageReqeust)throws Exception;
	
	/**
	 * 
	 * 方法描述：检查一代回收密码未修改
	 * @throws Exception
	 */
	public boolean isLevelRecycleFirstLogin(QueryLevelRecycleHistoryRequest queryLRHistoryRequest) throws Exception;
	
	/**
	 * 
	 * 方法描述：一代回收用户名单
	 * @throws Exception
	 */
	public QueryLevelRecycleListResponse queryLevelRecycleList(QueryLevelRecycleListRequest queryLevelRecycleListRequest) throws Exception;
	
	/**
	 * 
	 * 方法描述：一代回收申请 
	 * @throws Exception
	 */
	public void applyLevelRecycle(ApplyLevelRecycleRequest applyLevelRecycleRequest) throws Exception;
	
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
	public void cleanBindCard(LevelRecycleRequest levelRecycleRequest) throws Exception;
	
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
	 * 方法描述 : 查询最新回收记录
	 * @param userId
	 * @return
	 */
	public QueryLevelRecycleHistoryResponse queryRecycleLastHistory(Long userId);
	
	/**
	 * 方法描述 : 查询最新回收记录
	 * @param account
	 * @return
	 */
	public QueryLevelRecycleHistoryResponse queryRecycleLastHistory(String account);
	
	/**
	 * 
	 * 方法描述：更新登录密码记录
	 * @throws Exception
	 */
	public void updateUserRecyclePwdFlag(LevelRecycleRequest levelRecycleRequest) throws Exception;
	
}
