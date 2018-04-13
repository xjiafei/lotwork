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
import com.winterframework.firefrog.game.exception.GameRevocationException;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IGameOrderWinFundService;
import com.winterframework.firefrog.game.service.IGamePlanService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;

/** 
* @ClassName GameRevocationOrderRiskedSuccessServiceImpl 
* @Description 撤销已中奖的订单
* @author  hugh
* @date 2014年5月12日 下午1:43:13 
*  
*/
@Service("gameRevocationOrderRiskedSuccessServiceImpl")
@Transactional
public class GameRevocationOrderRiskedSuccessServiceImpl extends AbstractGameRevocationOrderAfterDrawServiceImpl {

	private Logger log = LoggerFactory.getLogger(GameRevocationOrderRiskedSuccessServiceImpl.class);

	@Resource(name = "gameOrderWinFundServcieImpl")
	private IGameOrderWinFundService gameOrderWinFundServcie;
	@Resource(name = "generateGamePlanServiceImpl")
	private IGamePlanService generateGamePlanServiceImpl;
	@Resource(name = "gamePlanService")
	protected ICommonGamePlanService gamePlanService;
	
	/**
	* @Title: doRevocation
	* @Description:撤销已中奖的订单
	* @param order
	* @return 
	* @see com.winterframework.firefrog.game.service.revocation.impl.AbstractGameRevocationOrderAfterDrawServiceImpl#doRevocation(com.winterframework.firefrog.game.dao.vo.GameOrder) 
	*/
	@Override
	public List<TORiskDTO> doRevocation(ProcessResult result,GameOrder order) throws Exception{
		return doRevocation(result,order, true);
	}

	/**
	* @Title: doReDraw
	* @Description:撤销已中奖的订单 到开奖前
	* @param order
	* @return 
	* @see com.winterframework.firefrog.game.service.revocation.impl.AbstractGameRevocationOrderAfterDrawServiceImpl#doReDraw(com.winterframework.firefrog.game.dao.vo.GameOrder) 
	*/
	@Override
	public List<TORiskDTO> doRevocationToBeforeDraw(ProcessResult result,GameOrder order) throws Exception{
		List<TORiskDTO> list = super.doRevocationToBeforeDraw(result,order);
		TORiskDTO dto = gameOrderWinFundServcie.packageCancelTORiskDTO(order.getId());
		list.add(dto);
		GamePlan plan = getGamePlanByOrderId(order);
		
		if (plan != null) {			
			plan.setWinAmount(plan.getWinAmount()-Long.parseLong(dto.getAmount()));
			try {
				gamePlanService.updateGamePlan(plan);
			} catch (Exception e) {
				log.error("更新追号中奖失败", e);
				throw new GameRevocationException("更新追号中奖失败");
			}
		}	
		
		return list;
	}
	@Override
	public List<TORiskDTO> doRevocationToRedraw(ProcessResult result,
			GameOrder order) throws Exception { 
		/**
		 * 中->不中：撤销奖金			--撤销派奖
		 * 中->中：    撤销派奖 &派送奖金	--撤销派奖
		 * 中->异常：撤销奖金，返点，扣款	--撤销派奖
		 */
		super.doRevocationToRedraw(result, order);
		List<TORiskDTO> dtolist = new ArrayList<TORiskDTO>();
		TORiskDTO dto = gameOrderWinFundServcie.packageCancelTORiskDTO(order.getId());
		dtolist.add(dto);
		return dtolist;
	}

	/**
	* @Title: doRevocationNotAskPlan
	* @Description:撤销已中奖的订单 但不执行追号
	* @param order
	* @return 
	* @see com.winterframework.firefrog.game.service.revocation.impl.AbstractGameRevocationOrderAfterDrawServiceImpl#doRevocationNotAskPlan(com.winterframework.firefrog.game.dao.vo.GameOrder) 
	*/
	@Override
	public List<TORiskDTO> doRevocationNotAskPlan(ProcessResult result,GameOrder order) throws Exception{
		return doRevocation(result,order, false);
	}

	private List<TORiskDTO> doRevocation(ProcessResult result,GameOrder order, boolean askPlan) throws Exception{
		List<TORiskDTO> list = super.doRevocation(result,order);

		//更新中奖为已撤销，资金交易加入派奖撤销信息
		list.add(gameOrderWinFundServcie.packageCancelTORiskDTO(order.getId()));
		gameOrderWinFundServcie.updateOrderWinsCancelStatus(order.getId());

		//撤销订单审核信息
		cancelGameOrderWarn(order);

		GamePlan plan = getGamePlanByOrderId(order);
		if (plan != null) {
			//更新order状态为中奖来选择Plan处理类，在Plan中更新order状态为已撤销[事务最后提交，所以订单最终状态为已撤销，后面不需要调用update语句]
			order.setStatus(2);
			//追号撤销
			List<TORiskDTO> planList = gameRevocationPlanStatusMachineImpl.doRevocation(plan,
					getGamePlanDetailByOrderId(plan, order.getIssueCode()), order, askPlan);
			if (planList != null) {
				list.addAll(planList);
			}
			if (askPlan) {
				try {
					//调用追号
					generateGamePlanServiceImpl.generateGamePlan(result,order.getLotteryid(), order.getIssueCode(),
							plan.getId());
				} catch (Exception e) {
					log.error("调用追号失败", e);
					throw new GameRevocationException("调用追号失败");
				}
			}

		}
		return list;
	}

}
