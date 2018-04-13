package com.winterframework.firefrog.game.service.revocation.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;

@Service("gameRevocationPlanDoneNotWinServiceImpl")
@Transactional
public class GameRevocationPlanDoneNotWinServiceImpl extends AbstractGameRevocationPlanService {
	
	/**
	* @Title: doRevocation
	* @Description:执行了追号  但没中奖  --》撤销
	* @param plan
	* @param detail
	* @param order
	* @return 
	* @see com.winterframework.firefrog.game.service.revocation.impl.AbstractGameRevocationPlanService#doRevocation(com.winterframework.firefrog.game.dao.vo.GamePlan, com.winterframework.firefrog.game.dao.vo.GamePlanDetail, com.winterframework.firefrog.game.dao.vo.GameOrder) 
	*/
	@Override
	public List<TORiskDTO> doRevocation(GamePlan plan, GamePlanDetail detail, GameOrder order, boolean isAskPlan) throws Exception{
		super.doRevocation(plan, detail, order, isAskPlan);
		//更新plan销售金额 detail状态
		updateGamePlanRevocation(plan, detail, order, isAskPlan);
		//追号不执行退款，转由订单执行退款，所以不需传递资金数据
		return null;
	}

}
