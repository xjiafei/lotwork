package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.exception.GameRiskException;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.game.service.IGameFundService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;

/**
 * 资金服务基类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月14日
 */
public class AbstractGameFundService implements IGameFundService {

	private Logger log = LoggerFactory.getLogger(AbstractGameFundService.class); 
	
	@Resource(name = "gameFundRiskServiceImpl")
	private IGameFundRiskService gameFundRiskServiceImpl;

	@Resource(name = "gamePlanDaoImpl")
	private IGamePlanDao gamePlanDao;
	 
	public void fundRequest(List<TORiskDTO> toRiskDTOList){
		log.info("审核中心开始请求资金系统");
		try {
			gameFundRiskServiceImpl.distributeAward(toRiskDTOList);
		} catch (Exception e) {
			log.error("请求审核系统资金接口出错",e);
			throw new GameRiskException("请求审核系统资金接口出错");
		}
		/*
		log.info("审核中心开始请求资金系统"); 
		if (toRiskDTOList == null) {
			return;
		}
		log.info("开始请求审核中心资金系统");
		try {
			List<TORiskDTO> distributeAwardList = new ArrayList<TORiskDTO>();
			List<TORiskDTO> betAmountFreezerList = new ArrayList<TORiskDTO>();

			for (TORiskDTO toRiskDTO : toRiskDTOList) {
				log.info("撤销中心  toRiskDTO" + toRiskDTO.getType());
				if (toRiskDTO.getType().intValue() == GameFundTypesUtils.GAME_USER_CANCEL_BET_UNFREEZER_DETEAIL_TYPE) {
					log.info("撤销中心  撤销订单");
					distributeAwardList.add(toRiskDTO);
				} else if (toRiskDTO.getType().intValue() == GameFundTypesUtils.GAME_SYS_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE) {
					log.info("撤销中心  撤销追号");
					//distributeAwardList.add(toRiskDTO);
				} else if (toRiskDTO.getType().intValue() == GameFundTypesUtils.GAME_BET_FREEZER_DETEAIL_TYPE) {
					log.info("撤销中心  冻结");
					betAmountFreezerList.add(toRiskDTO);
				}
			}
			toRiskDTOList.removeAll(distributeAwardList);
			toRiskDTOList.removeAll(betAmountFreezerList);

			if (!distributeAwardList.isEmpty()) {
				log.info("撤销distributeAward"+distributeAwardList.size());
				gameFundRiskServiceImpl.distributeAward(distributeAwardList);
			}
			if (!toRiskDTOList.isEmpty()) {
				log.info("撤销cancelFee"+toRiskDTOList.size());
				gameFundRiskServiceImpl.cancelFee(toRiskDTOList);
			}
			if (!betAmountFreezerList.isEmpty()) {
				log.info("撤销betAmountFreezer"+betAmountFreezerList.size());
				gameFundRiskServiceImpl.betAmountFreezer(betAmountFreezerList);
			}
		} catch (Exception e) {
			log.error("请求审核系统资金接口出错", e);
			throw new GameRiskException("请求审核系统资金接口出错" +e.getMessage());
		}*/
	}
	@Override
	public void fundRequestNew(GameContext ctx, List<TORiskDTO> toRiskDTOList)
			throws Exception {
		if(toRiskDTOList!=null && toRiskDTOList.size()>0){			
			try {
				gameFundRiskServiceImpl.fundRequest(toRiskDTOList);
			} catch (Exception e) {
				log.error("请求审核系统资金接口出错",e);
				throw new GameRiskException("请求审核系统资金接口出错");
			}
		}
	}
	
	protected TORiskDTO toRiskDTO(GameOrder order){
		TORiskDTO dto = new TORiskDTO(); 
		dto.setIssueCode(order.getIssueCode());
		dto.setLotteryid(order.getLotteryid().longValue());
		dto.setOrderCodeList(order.getOrderCode());
		GamePlan gamePlan = gamePlanDao.getGamePlanByOrderId(order.getId());
		if(gamePlan!=null){
			dto.setPlanCodeList(gamePlan.getPlanCode());
		}	
		dto.setUserid(order.getUserid()+"");
		return dto;
	}
	protected TORiskDTO toRiskDto(GamePlan plan) throws Exception{
		TORiskDTO dto = new TORiskDTO(); 
		dto.setLotteryid(plan.getLotteryid()); 
		dto.setPlanCodeList(plan.getPlanCode());
		dto.setUserid(plan.getPlanUserId()+ "");  
		
		return dto;
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
	protected void addRiskDto_bk(GameContext ctx, TORiskDTO riskDto) {
		List<TORiskDTO> riskDtoList=(List<TORiskDTO>)ctx.get("RISKDTOLIST");
		if(riskDtoList==null){
			riskDtoList=new ArrayList<TORiskDTO>(); 
		}
		riskDtoList.add(riskDto);
		ctx.set("RISKDTOLIST", riskDtoList);
	}
}
