package com.winterframework.firefrog.game.service.revocation.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGamePackageDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.entity.CancelMode;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGamePlanDetailService;
import com.winterframework.firefrog.game.service.IGamePlanFundService;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationPlanNewService;

 
/**
 * 追号撤销处理基类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月7日
 */
@Transactional
public abstract class AbstractGameRevocationPlanNewService implements IGameRevocationPlanNewService {
	private Logger log = LoggerFactory.getLogger(AbstractGameRevocationPlanNewService.class);

	@Resource(name = "gamePlanDetailServiceImpl")
	private IGamePlanDetailService gamePlanDetailService;
	@Resource(name = "gamePlanFundServiceImpl")
	private IGamePlanFundService gamePlanFundService;
	@Resource(name = "gamePlanService")
	private ICommonGamePlanService gamePlanService;
	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderService;
	
	protected static final int ZERO=0;
	protected static final int ONE=1;

	@Override
	public int doRevocation(GameContext ctx, GamePlanDetail detail)
			throws Exception {
		/**
		 * 1.资金处理 		
		 * 2.处理追号相关
		 * 3.处理追号明细
		 */  
		log.info("开始：撤销追号明细");
		if(detail==null) return ZERO; 
		
		log.info("资金处理");  
		doFund(ctx, null, detail);
		
		log.info("订单处理");
		doOrder(ctx, null, detail);
		
		log.info("更新追号信息");
		doPlan(ctx, null, detail);
		
		log.error("更新追号明细"); 
		doPlanDetail(ctx, null, detail);
		
		log.info("结束：撤销追号明细");
		return ONE; 
	}
	
	/**
	 * 处理资金
	 * @param ctx
	 * @param plan
	 * @param detail
	 * @throws Exception
	 */
	protected abstract void doFund(GameContext ctx,GamePlan plan,GamePlanDetail detail) throws Exception;
	/**
	 * 处理订单
	 * @param ctx
	 * @param plan
	 * @param detail
	 * @throws Exception
	 */
	protected void doOrder(GameContext ctx,GamePlan plan,GamePlanDetail detail) throws Exception{ 
		
	}
	/**
	 * 处理追号
	 * @param ctx
	 * @param plan
	 * @param detail
	 * @throws Exception
	 */
	protected void doPlan(GameContext ctx,GamePlan plan,GamePlanDetail detail) throws Exception{   
		
	}
	/**
	 * 处理追号明细
	 * @param ctx
	 * @param plan
	 * @param detail
	 * @throws Exception
	 */
	protected void doPlanDetail(GameContext ctx,GamePlan plan,GamePlanDetail detail) throws Exception{ 
		detail.setStatus(GamePlanDetail.Status.CANCEL.getValue());
		detail.setCancelUser("-1");	//系统撤销??????
		detail.setCancelTime(DateUtils.currentDate());
		gamePlanDetailService.save(ctx,detail); 
	}

}
