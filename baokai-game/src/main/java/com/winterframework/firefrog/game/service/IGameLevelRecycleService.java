package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.web.dto.QueryLevelRecycleHistoryRequest;
import com.winterframework.firefrog.game.web.dto.QueryLevelRecycleHistoryResponse;



/**
 * 
 * 类功能说明: 一代回收
 */
public interface IGameLevelRecycleService {
	
	/**
	 * 
	 * 方法描述：一代回收纪录 
	 * @throws Exception
	 */
	public List<QueryLevelRecycleHistoryResponse> queryLevelRecycleHistory(QueryLevelRecycleHistoryRequest queryLRHistoryRequest)throws Exception;

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
	
}
