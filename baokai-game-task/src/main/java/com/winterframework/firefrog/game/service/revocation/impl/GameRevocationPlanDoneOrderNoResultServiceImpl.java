package com.winterframework.firefrog.game.service.revocation.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;

/** 
* @ClassName GameRevocationPlanDoneOrderNoResultServiceImpl 
* @Description 解决追号已执行 订单状态时1 和 5的情况
* @author  hugh
* @date 2014年5月7日 下午3:28:01 
*  
*/
@Service("gameRevocationPlanDoneOrderNoResultServiceImpl")
@Transactional
public class GameRevocationPlanDoneOrderNoResultServiceImpl extends AbstractGameRevocationPlanService {

	/**
	* @Title: doRevocation
	* @Description:追号没执行的撤销
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
		return getUnfreezePlanDetialDto(plan, detail, order, isAskPlan);
	}

}
