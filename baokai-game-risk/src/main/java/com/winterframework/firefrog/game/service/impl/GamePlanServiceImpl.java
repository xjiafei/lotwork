package com.winterframework.firefrog.game.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.IGamePlanDetailDao;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.entity.GamePlan.GamePlanStatus;
import com.winterframework.firefrog.game.service.IGamePlanService;


/** 
* @ClassName GamePlanServiceImpl 
* @Description 追号相关服务类 
* @author  hugh
* @date 2014年5月30日 上午11:14:12 
*  
*/
@Service("gamePlanService")
@Transactional
public class GamePlanServiceImpl implements IGamePlanService {

	private static final Logger log = LoggerFactory.getLogger(GamePlanServiceImpl.class);

	@Resource(name = "gamePlanDaoImpl")
	protected IGamePlanDao gamePlanDao;	
	
	@Resource(name = "gamePlanDetailDaoImpl")
	protected IGamePlanDetailDao gamePlanDetailDao;
	

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
	public void continueGamePlan(Long planId) throws Exception {
		
		log.info("继续执行追号计划信息成功，planId="+ planId);
		//根据planId 获取planDetail信息。
		//更新Plan为追号计划为进行中。
		GamePlan gamePlan = gamePlanDao.getById(planId);
		
		gamePlanDetailDao.updateGamePlanDetailContinue(planId);
		gamePlan.setStatus(GamePlan.Status.WAITING.getValue());
		gamePlan.setUpdateTime(DateUtils.currentDate());
		gamePlanDao.update(gamePlan); 
		log.info("继续执行追号计划信息成功，planId="+ planId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false,rollbackFor=Exception.class)
	public void pauseGamePlan(Long planId) throws Exception {
		log.info("暂停追号计划信息，planId="+ planId);
		//暂停planDetail 信息
		GamePlan gamePlan = gamePlanDao.getById(planId);
		
		gamePlanDetailDao.updateGamePlanDetailPause(planId);
		//暂停plan信息
		gamePlan.setStatus(GamePlan.Status.PAUSE.getValue());
		gamePlan.setUpdateTime(DateUtils.currentDate());
		gamePlanDao.update(gamePlan);
		log.info("暂停追号计划信息成功，planId="+ planId);
	}

	
}
