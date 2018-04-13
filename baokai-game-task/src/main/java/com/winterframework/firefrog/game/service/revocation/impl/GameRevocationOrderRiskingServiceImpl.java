package com.winterframework.firefrog.game.service.revocation.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IGameOrderWinFundService;
import com.winterframework.firefrog.game.service.IGamePlanService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;

/** 
* @ClassName GameRevocationOrderRiskingServiceImpl 
* @Description 撤销异常订单 
* @author  hugh
* @date 2014年5月12日 下午1:44:46 
*  
*/
@Service("gameRevocationOrderRiskingServiceImpl")
@Transactional
public class GameRevocationOrderRiskingServiceImpl extends AbstractGameRevocationOrderAfterDrawServiceImpl {

	private Logger log = LoggerFactory.getLogger(GameRevocationOrderRiskingServiceImpl.class);

	@Resource(name = "generateGamePlanServiceImpl")
	private IGamePlanService generateGamePlanServiceImpl;

	@Resource(name = "gamePlanService")
	private ICommonGamePlanService gamePlanService;

	@Resource(name = "gameOrderWinFundServcieImpl")
	private IGameOrderWinFundService gameOrderWinFundServcie;
	/**
	* @Title: doRevocation
	* @Description:撤销异常订单
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
	
	@Override
	public List<TORiskDTO> doRevocationToRedraw(ProcessResult result,
			GameOrder order) throws Exception { 
		super.doRevocationToRedraw(result, order);
		//删除风险订单--外围类已经调用 
		return null;
	}
	
	private List<TORiskDTO> doRevocation(ProcessResult result,GameOrder order, boolean askPlan) throws Exception{
		super.doRevocation(result,order);
		List<TORiskDTO> list = new ArrayList<TORiskDTO>();
		
		//撤销订单审核信息
		cancelGameOrderWarn(order);
		gameOrderWinFundServcie.updateOrderWinsCancelStatus(order.getId());
		
		GamePlan plan = getGamePlanByOrderId(order);
		if (plan != null) {

			//更新order状态为异常来选择Plan处理类，在Plan中更新order状态为已撤销[事务最后提交，所以订单最终状态为已撤销，后面不需要调用update语句]
			order.setStatus(5);
			List<TORiskDTO> planList = gameRevocationPlanStatusMachineImpl.doRevocation(plan,
					getGamePlanDetailByOrderId(plan, order.getIssueCode()), order, askPlan);
			if (planList != null) {
				list.addAll(planList);
			}
			/*
			 * 订单进入审核  改为 不暂停追号 此处撤销也无需继续追号和执行追号
			try {
				gamePlanService.continueGamePlan(plan.getId());
			} catch (Exception e) {
				log.error("调用追号继续失败", e);
				throw new GameRevocationException("调用追号继续失败");
			}
			try {
				//调用追号
				generateGamePlanServiceImpl.generateGamePlan(result,order.getLotteryid(), order.getIssueCode(), plan.getId());
			} catch (Exception e) {
				log.error("调用追号失败", e);
				throw new GameRevocationException("调用追号失败");
			}*/
		}
		return list;
	}

}
