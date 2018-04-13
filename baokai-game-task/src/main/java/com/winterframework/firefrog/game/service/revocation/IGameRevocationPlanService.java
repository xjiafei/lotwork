package com.winterframework.firefrog.game.service.revocation;

import java.util.List;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;

/** 
* @ClassName IGameRevocationPlanService 
* @Description 单个追号撤销接口 
* @author  hugh
* @date 2014年5月12日 下午3:26:43 
*  
*/
public interface IGameRevocationPlanService {

	/** 
	* @Title: doRevocation 
	* @Description: 撤销单个追号
	* @param plan
	* @param detail
	* @param order
	* @return
	*/
	List<TORiskDTO> doRevocation(GamePlan plan , GamePlanDetail detail, GameOrder order, boolean isAskPlan) throws Exception;;
	
	/** 
	* @Title: doRevocationAndAskRisk 
	* @Description: 撤销单个追号 并执行资金交易
	* @param plan
	* @param detail
	* @param order
	*/
	void doRevocationAndAskRisk(GamePlan plan , GamePlanDetail detail, GameOrder order) throws Exception;;
	void doRevocationAndAskRisk(GameContext ctx,GamePlan plan , GamePlanDetail detail, GameOrder order) throws Exception;;

}
