package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameWarnOrderLog;
import com.winterframework.firefrog.game.web.dto.GameRiskWarnUserListQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameRiskWarnUserOrderListResponse;
import com.winterframework.firefrog.game.web.dto.GenerateGamePlanRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName IGameWarnUserOrderService 
* @Description 风控用户订单信息 
* @author  hugh
* @date 2014年4月10日 下午3:25:40 
*  
*/
public interface IGameRsikUserOrderService {
	
	/** 
	* @Title: queryWarnUserOrderList 
	* @Description: 查询风控用户订单信息
	* @param req
	* @return
	*/
	Response<GameRiskWarnUserOrderListResponse> queryWarnUserOrderList(
			Request<GameRiskWarnUserListQueryRequest> request) throws Exception;
	
	/** 
	* @Title: updateWarnOrder 
	* @Description: 审核、更新订单
	* @param orderIds
	* @param status
	* @param entity
	 * @return 
	*/
	GenerateGamePlanRequest updateWarnOrder(List<Long> orderIds,Long status,GameWarnOrderLog entity) throws Exception ;
	
	/** 
	* @Title: updateNotPassByLotteryAndIssue 
	* @Description: 奖期期号所有风控订单不通过
	* @param lotteryId
	* @param issueCode
	* @return
	*/
	void updateNotPassByLotteryAndIssue(Long lotteryId, Long issueCode);
}
