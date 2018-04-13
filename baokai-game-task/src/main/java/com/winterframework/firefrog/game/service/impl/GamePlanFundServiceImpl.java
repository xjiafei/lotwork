package com.winterframework.firefrog.game.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IGamePlanFundService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;
 
@Service("gamePlanFundServiceImpl")
@Transactional
public class GamePlanFundServiceImpl extends AbstractGameFundService implements IGamePlanFundService{

	private Logger log = LoggerFactory.getLogger(GamePlanFundServiceImpl.class);
	 
	@Resource(name = "gamePlanService")
	private ICommonGamePlanService gamePlanService;
	 
	@Override
	public int freeze(GameContext ctx, GamePlan plan,GamePlanDetail planDetail)
			throws Exception {
		TORiskDTO riskDto = this.toRiskDto(plan); 
		riskDto.setIssueCode(planDetail.getIssueCode());
		riskDto.setAmount(planDetail.getTotamount()+"");
		riskDto.setType(GameFundTypesUtils.GAME_PLAN_FREEZER_DETEAIL_TYPE);  
		addRiskDto(ctx, riskDto); 
		return 1; 
	} 
	@Override
	public int unfreeze(GameContext ctx, GamePlan plan,GamePlanDetail planDetail)
			throws Exception { 
		TORiskDTO riskDto = this.toRiskDto(plan); 
		riskDto.setIssueCode(planDetail.getIssueCode());
		riskDto.setAmount(planDetail.getTotamount()+"");
		riskDto.setType(GameFundTypesUtils.GAME_SYS_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE);  
		addRiskDto(ctx, riskDto); 
		return 1; 
	} 
	 
	
}