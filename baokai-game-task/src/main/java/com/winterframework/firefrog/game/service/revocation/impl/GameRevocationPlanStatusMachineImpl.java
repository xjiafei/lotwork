package com.winterframework.firefrog.game.service.revocation.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.exception.GameRevocationException;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationPlanService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;

@Service("gameRevocationPlanStatusMachineImpl")
@Transactional
public class GameRevocationPlanStatusMachineImpl implements IGameRevocationPlanService,ApplicationContextAware {

	private static final Logger log = LoggerFactory.getLogger(GameRevocationPlanStatusMachineImpl.class);
	
	@Resource(name = "revocationPlanMap")
	private HashMap<String, String> revocationPlanMap;

	/**
	* @Title: doRevocation
	* @Description:执行追号撤销
	* @param plan
	* @param detail
	* @param order
	* @return 
	* @see com.winterframework.firefrog.game.service.revocation.IGameRevocationPlanService#doRevocation(com.winterframework.firefrog.game.dao.vo.GamePlan, com.winterframework.firefrog.game.dao.vo.GamePlanDetail, com.winterframework.firefrog.game.dao.vo.GameOrder) 
	*/
	@Override
	public List<TORiskDTO> doRevocation(GamePlan plan, GamePlanDetail detail, GameOrder order, boolean isAskPlan) throws Exception{
		IGameRevocationPlanService service = getRevocationPlanService(detail, order);
		if(service != null){
			return getRevocationPlanService(detail, order).doRevocation(plan, detail, order, isAskPlan);
		}else{
			return null;
		}		
	}

	/**
	* @Title: doRevocationAndAskRisk
	* @Description:执行追号撤销 并调用资金接口
	* @param plan
	* @param detail
	* @param order 
	* @see com.winterframework.firefrog.game.service.revocation.IGameRevocationPlanService#doRevocationAndAskRisk(com.winterframework.firefrog.game.dao.vo.GamePlan, com.winterframework.firefrog.game.dao.vo.GamePlanDetail, com.winterframework.firefrog.game.dao.vo.GameOrder) 
	*/
	@Override
	public void doRevocationAndAskRisk(GamePlan plan, GamePlanDetail detail, GameOrder order) throws Exception{
		IGameRevocationPlanService service = getRevocationPlanService(detail, order);
		if(service != null){
			getRevocationPlanService(detail, order).doRevocationAndAskRisk(plan, detail, order);
		}		
	}
	@Override
	public void doRevocationAndAskRisk(GameContext ctx,GamePlan plan, GamePlanDetail detail, GameOrder order) throws Exception{
		IGameRevocationPlanService service = getRevocationPlanService(detail, order);
		if(service != null){
			getRevocationPlanService(detail, order).doRevocationAndAskRisk(ctx,plan, detail, order);
		}		
	}

	/** 
	* @Title: getRevocationPlanService 
	* @Description: 选择执行类
	* @param detail
	* @param order
	* @return
	*/
	public IGameRevocationPlanService getRevocationPlanService(GamePlanDetail detail, GameOrder order) {
		Integer orderSatus = 0;
		if (order != null) {//未生产订单，默认状态为0
			orderSatus = order.getStatus();
		}
		log.info("detail.getStatus() = " + detail.getStatus() );
		
		//追號撤銷異常，讓task進行處理
		if(detail.getStatus() == 5 || detail.getStatus() == 6){
			return null;
		}
		if(revocationPlanMap.get(detail.getStatus() + "_" + orderSatus)!=null){
			log.info(context.getBean(revocationPlanMap.get(detail.getStatus() + "_" + orderSatus)).getClass().getName());
			return (IGameRevocationPlanService)context.getBean(revocationPlanMap.get(detail.getStatus() + "_" + orderSatus));
		}else if(orderSatus == 4 || orderSatus == 6 || detail.getStatus() == 2){
			//数据已撤销 或 归档 不执行
			return null;
		} else {
			throw new GameRevocationException("没有撤销该plan的处理执行类");
		}
		
	}

	@Override
	public void setApplicationContext(ApplicationContext contex) throws BeansException {
		context = contex;
	} 

	private  ApplicationContext context;
}
