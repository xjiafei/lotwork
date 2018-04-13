package com.winterframework.firefrog.game.service.revocation.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationPlanNewService;

/**
 * 订单撤销服务类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月3日
 */
@Service("gameRevocationPlanNewServiceImpl")
@Transactional
public class GameRevocationPlanServiceImpl implements
		IGameRevocationPlanNewService,ApplicationContextAware {
	private Logger log = LoggerFactory.getLogger(GameRevocationPlanServiceImpl.class);
	
	@Resource(name="revocationPlanStrategyMap")
	Map<String,String> revocationPlanStrategyMap; 
	
	private ApplicationContext context;
	private static final int ZERO=0;
	private static final int ONE=1;
	@Override
	public int doRevocation(GameContext ctx, GamePlanDetail gamePlanDetail) throws Exception {
		log.info("开始：追号明细撤销");
		if(gamePlanDetail==null) return ZERO; 
		IGameRevocationPlanNewService gameRevocationPlanService=(IGameRevocationPlanNewService)this.context.getBean(revocationPlanStrategyMap.get(gamePlanDetail.getStatus().intValue()));
		int ret= gameRevocationPlanService.doRevocation(ctx, gamePlanDetail); 
		log.info("结束：追号明细撤销");
		return ret;
	}
	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		this.context=ctx;
	}
}
