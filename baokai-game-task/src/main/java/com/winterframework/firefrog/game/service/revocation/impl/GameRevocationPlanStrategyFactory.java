package com.winterframework.firefrog.game.service.revocation.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationPlanNewService;

/**
 * 追号明细撤销策略工厂（根据追号明细状态选择追号明细撤销处理策略类）
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月3日
 */ 
public class GameRevocationPlanStrategyFactory implements ApplicationContextAware {
	private ApplicationContext context;
	public IGameRevocationPlanNewService getInstance(GamePlanDetail gamePlanDetail){
		int status=gamePlanDetail.getStatus().intValue();
		IGameRevocationPlanNewService seriveImpl=null;
		if(status==GamePlanDetail.Status.UN_EXEC.getValue()){
			seriveImpl= (IGameRevocationPlanNewService)this.context.getBean("gameRevocationPlanUnexecServiceImpl");
		}else if(status==GamePlanDetail.Status.EXEC.getValue()){
			seriveImpl= (IGameRevocationPlanNewService)this.context.getBean("gameRevocationPlanExecServiceImpl");
		}else if(status==GamePlanDetail.Status.PAUSE.getValue()){
			seriveImpl= (IGameRevocationPlanNewService)this.context.getBean("gameRevocationPlanPauseServiceImpl");
		}else if(status==GamePlanDetail.Status.CANCEL.getValue()){
			seriveImpl= (IGameRevocationPlanNewService)this.context.getBean("gameRevocationPlanCancelServiceImpl");
		}
		
		return seriveImpl;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationcontext)
			throws BeansException {
		this.context=applicationcontext;
		
	}
}
