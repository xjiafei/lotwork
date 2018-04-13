package com.winterframework.firefrog.game.service.revocation.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationOrderNewService;

/**
 * 订单撤销策略工厂（根据订单状态选择订单撤销处理策略类）
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月3日
 */ 
public class GameRevocationOrderStrategyFactory implements ApplicationContextAware {
	private ApplicationContext context;
	public IGameRevocationOrderNewService getInstance(GameOrder order){
		int status=order.getStatus().intValue();
		IGameRevocationOrderNewService seriveImpl=null;
		if(status==GameOrder.Status.WAIT.getValue()){
			seriveImpl= (IGameRevocationOrderNewService)this.context.getBean("gameRevocationOrderWaitingServiceImpl");
		}else if(status==GameOrder.Status.WIN.getValue()){
			seriveImpl= (IGameRevocationOrderNewService)this.context.getBean("gameRevocationOrderWinServiceImpl");
		}else if(status==GameOrder.Status.UN_WIN.getValue()){
			seriveImpl= (IGameRevocationOrderNewService)this.context.getBean("gameRevocationOrderUnWinServiceImpl");
		}else if(status==GameOrder.Status.CANCEL.getValue()){
			seriveImpl= (IGameRevocationOrderNewService)this.context.getBean("gameRevocationOrderCancelServiceImpl");
		}else if(status==GameOrder.Status.EXCEP.getValue()){
			seriveImpl= (IGameRevocationOrderNewService)this.context.getBean("gameRevocationOrderExcepServiceImpl");
		}else if(status==GameOrder.Status.FILE.getValue()){
			seriveImpl= (IGameRevocationOrderNewService)this.context.getBean("gameRevocationOrderFileServiceImpl");
		}
		
		return seriveImpl;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationcontext)
			throws BeansException {
		this.context=applicationcontext;
		
	}
}
