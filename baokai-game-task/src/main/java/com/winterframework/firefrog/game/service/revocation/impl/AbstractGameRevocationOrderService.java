package com.winterframework.firefrog.game.service.revocation.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.service.IGameOrderFundService;
import com.winterframework.firefrog.game.service.IGameReturnPointFundService;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationOrderService;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationPlanService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;

/** 
* @ClassName AbstractGameRevocationOrderService 
* @Description 撤销订单执行父类 
* @author  hugh
* @date 2014年5月12日 下午2:37:57 
*  
*/
public abstract class AbstractGameRevocationOrderService extends AbstractGameRevocationService implements
IGameRevocationOrderService{

	@Resource(name = "gameOrderFundServcieImpl")
	protected IGameOrderFundService gameOrderFundServcie;

	@Resource(name = "gameReturnPointFundServcieImpl")
	protected IGameReturnPointFundService gameReturnPointFundServcie;

	@Resource(name = "gameRevocationPlanStatusMachineImpl")
	protected IGameRevocationPlanService gameRevocationPlanStatusMachineImpl;

	public abstract List<TORiskDTO> doRevocation(ProcessResult result,GameOrder order) throws Exception;

	public abstract List<TORiskDTO> doRevocationNotAskPlan(ProcessResult result,GameOrder order) throws Exception;

	public abstract List<TORiskDTO> doRevocationToBeforeDraw(ProcessResult result,GameOrder order) throws Exception;
	
	/** 
	* @Title: doRevocationAndAskRisk 
	* @Description: 撤销订单且调用资金交易接口
	* @param order
	*/
	public void doRevocationAndAskRisk(ProcessResult result,GameOrder order) throws Exception{
		List<TORiskDTO> dtos = doRevocation(result,order);
		fundRequest(dtos);
	}

	/** 
	* @Title: doRevocationNotAskPlanAskRisk 
	* @Description: 撤销订单但不执行追号，调用资金交易接口
	* @param order
	*/
	public void doRevocationNotAskPlanAskRisk(ProcessResult result,GameOrder order) throws Exception{
		List<TORiskDTO> dtos = doRevocationNotAskPlan(result,order);
		fundRequest(dtos);
	}
	
	@Override
	public void doRevocationNotAskPlanAskRisk(GameContext ctx,
			ProcessResult result, GameOrder order) throws Exception {
		List<TORiskDTO> dtos = doRevocationNotAskPlan(result,order);
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

}
