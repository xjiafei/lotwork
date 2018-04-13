package com.winterframework.firefrog.game.service.revocation.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.service.IGameOrderFundService;
import com.winterframework.firefrog.game.service.IGameReturnPointFundService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;

/** 
* @ClassName GameRevocationOrderNotWinServiceImpl 
* @Description 撤销没有中奖的订单 
* @author  hugh
* @date 2014年5月12日 下午1:42:26 
*  
*/
@Service("gameRevocationOrderNotWinServiceImpl")
@Transactional
public class GameRevocationOrderNotWinServiceImpl extends AbstractGameRevocationOrderAfterDrawServiceImpl {

	@Resource(name = "gameOrderFundServcieImpl")
	private IGameOrderFundService gameOrderFundServcie;

	@Resource(name = "gameReturnPointFundServcieImpl")
	private IGameReturnPointFundService gameReturnPointFundServcie;

	/**
	* @Title: doRevocation
	* @Description:撤销没有中奖的订单
	* @param order
	* @return 
	* @see com.winterframework.firefrog.game.service.revocation.impl.AbstractGameRevocationOrderAfterDrawServiceImpl#doRevocation(com.winterframework.firefrog.game.dao.vo.GameOrder) 
	*/
	@Override
	public List<TORiskDTO> doRevocation(ProcessResult result,GameOrder order) throws Exception{
		return doRevocation(result,order, true);
	}

	@Override
	public List<TORiskDTO> doRevocationNotAskPlan(ProcessResult result,GameOrder order) throws Exception{
		return doRevocation(result,order, false);
	}
	
	private List<TORiskDTO> doRevocation(ProcessResult result,GameOrder order, boolean askPlan) throws Exception{
		List<TORiskDTO> list = super.doRevocation(result,order);
		GamePlan plan = getGamePlanByOrderId(order);
		if (plan != null) {
			//更新order状态为没中奖来选择Plan处理类，在Plan中更新order状态为已撤销[事务最后提交，所以订单最终状态为已撤销，后面不需要调用update语句]
			order.setStatus(3);
			List<TORiskDTO> planList = gameRevocationPlanStatusMachineImpl.doRevocation(plan,
					getGamePlanDetailByOrderId(plan, order.getIssueCode()), order, askPlan);
			if (planList != null) {
				list.addAll(planList);
			}

		}

		return list;
	}
	
}
