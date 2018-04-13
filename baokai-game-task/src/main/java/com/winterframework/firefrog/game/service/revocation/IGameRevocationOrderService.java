package com.winterframework.firefrog.game.service.revocation;

import java.util.List;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;

/** 
* @ClassName IGameRevocationOrderService 
* @Description 撤销单个订单接口 
* @author  hugh
* @date 2014年5月12日 下午3:27:34 
*  
*/
public interface IGameRevocationOrderService {
	/** 
	* @Title: cancel 
	* @Description: 撤销订单 获取资金交互数据
	* @param lotteryId
	* @param issueCode
	* @param isRevocation	是否真正的业务撤销
	*/
	List<TORiskDTO> doRevocation(ProcessResult result,GameOrder order) throws Exception;
	/** 
	* @Title: cancel 
	* @Description: 撤销订单 并执行资金交易
	* @param lotteryId
	* @param issueCode
	*/
	void doRevocationAndAskRisk(ProcessResult result,GameOrder order) throws Exception;
	
	/** 
	* @Title: doRevocationNotAskPlan 
	* @Description: 撤销订单但不执行追号
	* @param order
	* @return
	*/
	List<TORiskDTO> doRevocationNotAskPlan(ProcessResult result,GameOrder order) throws Exception;
	
	/** 
	* @Title: doRevocationNotAskPlanAskRisk 
	* @Description: 撤销订单但不执行追号 并执行资金交易
	* @param order
	*/
	void doRevocationNotAskPlanAskRisk(ProcessResult result,GameOrder order) throws Exception;
	void doRevocationNotAskPlanAskRisk(GameContext ctx,ProcessResult result,GameOrder order) throws Exception;
	
	/** 
	* @Title: doRevocationToBeforeDraw 
	* @Description: 撤销订单到开奖前(重新开奖使用)
	* @param order
	* @return
	*/
	List<TORiskDTO> doRevocationToBeforeDraw(ProcessResult result,GameOrder order) throws Exception;
	
	/**
	 * 撤销订单至重开
	 * @param ctx
	 * @param order
	 * @return
	 * @throws Exception
	 */
	List<TORiskDTO> doRevocationToRedraw(ProcessResult result,GameOrder order) throws Exception;
}
