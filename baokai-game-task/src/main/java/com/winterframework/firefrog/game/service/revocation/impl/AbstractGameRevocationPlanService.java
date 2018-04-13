package com.winterframework.firefrog.game.service.revocation.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGamePackageDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.exception.GameRevocationException;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationPlanService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;

/** 
* @ClassName AbstractGameRevocationPlanService 
* @Description 撤销追号执行父类 
* @author  hugh
* @date 2014年5月12日 下午2:39:04 
*  
*/
@Transactional
public class AbstractGameRevocationPlanService extends AbstractGameRevocationService implements
		IGameRevocationPlanService {
	private Logger log = LoggerFactory.getLogger(AbstractGameRevocationPlanService.class);

	@Resource(name = "gamePlanService")
	protected ICommonGamePlanService gamePlanService;

	@Resource(name = "gamePackDaoImpl")
	protected IGamePackageDao gamePackDaoImpl;
	/**
	* @Title: doRevocation
	* @Description:撤销追号
	* @param plan
	* @param detail
	* @param order
	* @return 
	* @see com.winterframework.firefrog.game.service.revocation.IGameRevocationPlanService#doRevocation(com.winterframework.firefrog.game.dao.vo.GamePlan, com.winterframework.firefrog.game.dao.vo.GamePlanDetail, com.winterframework.firefrog.game.dao.vo.GameOrder) 
	*/
	@Override
	public List<TORiskDTO> doRevocation(GamePlan plan, GamePlanDetail detail, GameOrder order, boolean isAskPlan)
			throws Exception {
		//订单状态设置为已撤销
		if (order != null) {
			order.setStatus(4);
			if(order.getCancelModes().intValue() == -1){
				detail.setCancelUser("-1");
			}
		}
		return null;
	}

	/**
	* @Title: doRevocationAndAskRisk
	* @Description:撤销追号，且请求资金接口
	* @param plan
	* @param detail
	* @param order 
	* @see com.winterframework.firefrog.game.service.revocation.IGameRevocationPlanService#doRevocationAndAskRisk(com.winterframework.firefrog.game.dao.vo.GamePlan, com.winterframework.firefrog.game.dao.vo.GamePlanDetail, com.winterframework.firefrog.game.dao.vo.GameOrder) 
	*/
	@Override
	public void doRevocationAndAskRisk(GamePlan plan, GamePlanDetail detail, GameOrder order) throws Exception {
		List<TORiskDTO> dtos = doRevocation(plan, detail, order, true);
		
		if(dtos!=null)
			log.info("调用资金:" + dtos.get(0).getType());
		
		fundRequest(dtos);
	}
	@Override
	public void doRevocationAndAskRisk(GameContext ctx,GamePlan plan, GamePlanDetail detail, GameOrder order) throws Exception {
		List<TORiskDTO> dtos = doRevocation(plan, detail, order, true);
		
		if(dtos!=null)
			log.info("调用资金:" + dtos.get(0).getType());
		
		this.addRiskDto(ctx, dtos);
	}
	protected void addRiskDto(GameContext ctx, TORiskDTO riskDto) {
		if(riskDto==null) return; 
		String orderCode=riskDto.getOrderCodeList();
		if(orderCode!=null){	 
			Map<String,List<TORiskDTO>> riskDtoMap=(Map<String,List<TORiskDTO>>)ctx.get("RISKDTOLIST");
			if(riskDtoMap==null){
				riskDtoMap=new HashMap<String,List<TORiskDTO>>(); 
			}
			List<TORiskDTO> riskDtoList=riskDtoMap.get(orderCode);
			if(riskDtoList==null){
				riskDtoList=new ArrayList<TORiskDTO>(); 
			}
			if(GameFundTypesUtils.GAME_SYS_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE==riskDto.getType().intValue()){ 
				riskDto.setOrderCodeList(null);
			}
			riskDtoList.add(riskDto);
			riskDtoMap.put(orderCode, riskDtoList);
			ctx.set("RISKDTOLIST", riskDtoMap);
		}
	}
	protected void addRiskDto(GameContext ctx, List<TORiskDTO> riskDtoList) {
		if(riskDtoList!=null && riskDtoList.size()>0){
			for(TORiskDTO riskDto:riskDtoList){
				this.addRiskDto(ctx, riskDto);
			}
		} 
	}
	protected void addRiskDto_bk(GameContext ctx, List<TORiskDTO> riskDtoList) {
		if(riskDtoList==null || riskDtoList.size()==0) return;
		List<TORiskDTO> dtoList=(List<TORiskDTO>)ctx.get("RISKDTOLIST");
		if(dtoList==null){
			dtoList=new ArrayList<TORiskDTO>(); 
		}
		dtoList.addAll(riskDtoList);
		ctx.set("RISKDTOLIST", dtoList);
	}
	/** 
	* @Title: updateGamePlanRevocation 
	* @Description: 更新plan销售金额 detail状态
	* @param plan
	* @param detail
	* @param order
	* @param isAskPlan
	*/
	public void updateGamePlanRevocation(GamePlan plan, GamePlanDetail detail, GameOrder order, boolean isAskPlan) {
		//更新plan销售金额 detail状态
		try {
			gamePlanService.updateGamePlanRevocation(plan.getId(), detail.getIssueCode(), isAskPlan,detail.getCancelUser());

		} catch (Exception e) {
			log.error("计划更新状态和销售金额出错", e);
			throw new GameRevocationException("计划更新状态和销售金额出错");
		}
	}
	
	/** 
	* @Title: getUnfreezePlanDetialDto 
	* @Description: 获取解冻的交易体
	* @param plan
	* @param detail
	* @param order
	* @param isAskPlan
	* @return
	*/
	public List<TORiskDTO> getUnfreezePlanDetialDto(GamePlan plan, GamePlanDetail detail, GameOrder order, boolean isAskPlan){
		List<TORiskDTO> dtos = new ArrayList<TORiskDTO>();
		TORiskDTO dto = new TORiskDTO();
		dto.setLotteryid(plan.getLotteryid());
		dto.setIssueCode(detail.getIssueCode());
		dto.setPlanCodeList(plan.getPlanCode());
		dto.setOrderCodeList(order==null?null:order.getOrderCode());
		
		if(plan.getUserId() == null){
			dto.setUserid(gamePackDaoImpl.getById(plan.getPackageId()).getUserid() + "");
		}else{
			dto.setUserid(plan.getUserId() + "");
		}	
		
		dto.setAmount(detail.getTotamount() + "");
		dto.setType(GameFundTypesUtils.GAME_SYS_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE);
		dtos.add(dto);
		return dtos;
	}

}
