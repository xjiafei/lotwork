package com.winterframework.firefrog.game.service.revocation.impl;

import java.util.HashMap;

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
import com.winterframework.firefrog.game.service.revocation.IGameRevocationOrderNewService;

/**
 * 订单撤销服务类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月3日
 */
@Service("gameRevocationOrderServiceImpl")
@Transactional
public class GameRevocationOrderServiceImpl implements
		IGameRevocationOrderNewService,ApplicationContextAware {
	private Logger log = LoggerFactory.getLogger(GameRevocationOrderServiceImpl.class);
	 
	private ApplicationContext context;
	@Resource(name = "revocationOrderStrategyMap")
	private HashMap<String, String> revocationOrderStrategyMap;
	
	private static final int ZERO=0;
	private static final int ONE=1;
	@Override
	public int doRevocation(GameContext ctx, GameOrder order) throws Exception {
		log.info("开始：订单撤销");
		if(order==null) return ZERO;
		String revocationBeanName=revocationOrderStrategyMap.get(order.getStatus().intValue());
		if(revocationBeanName==null)  return ZERO;
		IGameRevocationOrderNewService gameRevocationOrderService=(IGameRevocationOrderNewService)this.context.getBean(revocationBeanName);
		int ret=0;
		if(gameRevocationOrderService!=null){
			ret=gameRevocationOrderService.doRevocation(ctx, order);
		} 
		log.info("结束：订单撤销");
		return ret;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		this.context=ctx;		
	}
	
	
}
