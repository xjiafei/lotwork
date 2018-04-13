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
import com.winterframework.firefrog.game.service.IGamePlanService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;

/** 
* @ClassName GameRevocationOrderBeforeDrawServiceImpl 
* @Description 撤销开奖前订单 
* @author  hugh
* @date 2014年5月12日 下午1:38:48 
*  
*/
@Service("gameRevocationOrderBeforeDrawServiceImpl")
@Transactional
public class GameRevocationOrderBeforeDrawServiceImpl extends AbstractGameRevocationOrderService {

	private Logger log = LoggerFactory.getLogger(GameRevocationOrderBeforeDrawServiceImpl.class);

	@Resource(name = "generateGamePlanServiceImpl")
	private IGamePlanService generateGamePlanServiceImpl;

	/**
	* @Title: doRevocation
	* @Description:撤销开奖前订单
	* @param order
	* @return 
	* @see com.winterframework.firefrog.game.service.revocation.impl.AbstractGameRevocationOrderService#doRevocation(com.winterframework.firefrog.game.dao.vo.GameOrder) 
	*/
	@Override
	public List<TORiskDTO> doRevocation(ProcessResult result,GameOrder order) throws Exception{
		return doRevocation(result,order, true);
	}

	/**
	* @Title: doRevocationToBeforeDraw
	* @Description:撤销开奖前订单 到开奖前  （什么都不用做）（重新输入开奖时）
	* @param order
	* @return 
	* @see com.winterframework.firefrog.game.service.revocation.IGameRevocationOrderService#doReDraw(com.winterframework.firefrog.game.dao.vo.GameOrder) 
	*/
	@Override
	public List<TORiskDTO> doRevocationToBeforeDraw(ProcessResult result,GameOrder order) throws Exception{
		return null;
	}
	@Override
	public List<TORiskDTO> doRevocationToRedraw(ProcessResult result,
			GameOrder order) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	* @Title: doRevocationNotAskPlan
	* @Description:撤销开奖前订单 ，不用调追号
	* @param order
	* @return 
	* @see com.winterframework.firefrog.game.service.revocation.impl.AbstractGameRevocationOrderService#doRevocationNotAskPlan(com.winterframework.firefrog.game.dao.vo.GameOrder) 
	*/
	@Override
	public List<TORiskDTO> doRevocationNotAskPlan(ProcessResult result,GameOrder order) throws Exception{
		return doRevocation(result,order, false);
	}

	private List<TORiskDTO> doRevocation(ProcessResult result,GameOrder order, boolean askPlan) throws Exception{
		undoLock(order);
		
		List<TORiskDTO> dtos = new ArrayList<TORiskDTO>();
		GamePlan plan = getGamePlanByOrderId(order);
		if (plan == null) {
			//不是追号，订单冻结的钱还回去
			dtos.add(gameOrderFundServcie.packageOrderUnFreezeTORiskDTO(order));
		} else {
			//是追号，追号冻结的钱还回去
			List<TORiskDTO> planList = gameRevocationPlanStatusMachineImpl.doRevocation(plan,
					getGamePlanDetailByOrderId(plan, order.getIssueCode()), order, askPlan);
			if (planList != null) {
				dtos.addAll(planList);
			}
			if (askPlan) {
				try {
					//执行追号
					generateGamePlanServiceImpl.generateGamePlan(result,order.getLotteryid(), order.getIssueCode(),
							plan.getId());
				} catch (Exception e) {
					log.error("调用追号失败", e);
					throw new GameRevocationException("调用追号失败");
				}
			}

		}
		//更新订单的状态
		gameOrderFundServcie.updateOrderCancelStatus(order);
		return dtos;
	}

}
