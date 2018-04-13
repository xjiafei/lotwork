package com.winterframework.firefrog.game.service.revocation.impl;

import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.service.revocation.IGameReInputDrawTimeSerivce;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationOrderService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;

/** 
* @ClassName GameReInputDrawTimeSerivceImpl 
* @Description 重新输入开奖时间 
* @author  hugh
* @date 2014年5月12日 下午1:37:16 
*  
*/
@Service("gameReInputDrawTimeSerivceImpl")
@Transactional
public class GameReInputDrawTimeSerivceImpl extends AbstractGameRevocationService implements
		IGameReInputDrawTimeSerivce {

	@Resource(name = "gameRevocationOrderStatusMachineImpl")
	private IGameRevocationOrderService gameRevocationOrderStatusMachineImpl;
	@Resource(name = "gamePlanService")
	private ICommonGamePlanService gamePlanService; 
	/**
	* @Title: reInputDrawTime
	* @Description:重新输入开奖时间
	* @param lotteryId
	* @param issueCode
	* @param time 
	* @see com.winterframework.firefrog.game.service.revocation.IGameReInputDrawTimeSerivce#reInputDrawTime(java.lang.Long, java.lang.Long, java.util.Date) 
	*/
	@Override
	public void reInputDrawTime(Long lotteryId, Long issueCode, Date time) throws Exception{
		List<GameOrder> orders = getOrders(lotteryId, issueCode, time);
		List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();
		ProcessResult result=new ProcessResult();
		for (GameOrder order : orders) {
			//一单单撤
			List<TORiskDTO> toRiskDTOList1 = gameRevocationOrderStatusMachineImpl.doRevocation(result,order);
			if(toRiskDTOList1!=null){
				toRiskDTOList.addAll(toRiskDTOList1);
			}
			
		}
		//生成调度任务(可能存在多个奖期）
		List<Long> issueCodeList = (List<Long>) result.getFromRetParaMap(String.valueOf(lotteryId));
		gamePlanService.addMakeupOrderDrawEvent(lotteryId, issueCodeList); 

		//调用资金
		fundRequest(toRiskDTOList);

	}

}
