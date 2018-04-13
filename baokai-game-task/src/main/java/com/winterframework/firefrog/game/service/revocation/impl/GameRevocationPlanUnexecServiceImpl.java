package com.winterframework.firefrog.game.service.revocation.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.entity.CancelMode;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IGamePlanFundService;
 
/**
 * 追号明细撤销处理服务类_未执行
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月4日
 */
@Service("gameRevocationPlanUnexecServiceImpl")
@Transactional
public class GameRevocationPlanUnexecServiceImpl extends AbstractGameRevocationPlanNewService {

	private Logger log = LoggerFactory.getLogger(GameRevocationPlanUnexecServiceImpl.class);
  
	@Resource(name = "gamePlanFundServiceImpl")
	private IGamePlanFundService gamePlanFundService; 
	@Resource(name = "gamePlanService")
	private ICommonGamePlanService gamePlanService;
	
	@Override
	protected void doFund(GameContext ctx, GamePlan plan, GamePlanDetail detail)
			throws Exception {
		log.info("资金处理");  
		this.gamePlanFundService.unfreeze(ctx, plan,detail); 
	}
	@Override
	protected void doPlan(GameContext ctx, GamePlan plan, GamePlanDetail detail)
			throws Exception {  
		super.doPlan(ctx, plan, detail);
		plan=this.gamePlanService.getById(detail.getPlanid()); 
		if(plan!=null){ 
			Long amount=detail.getTotamount();
			plan.setCancelIssue(plan.getCancelIssue()==null ? 1 : plan.getCancelIssue() + 1);
			plan.setCanceledAmount(plan.getCanceledAmount()==null ?0L:(plan.getCanceledAmount()+amount));  
			this.gamePlanService.save(ctx, plan); 
		}
	}
}
