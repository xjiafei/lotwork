package com.winterframework.firefrog.game.service.revocation.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.service.impl.GamePlanServiceImpl;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;

/** 
* @ClassName GameRevocationPlanUndoServiceImpl 
* @Description 解决追号未执行的情况
* @author  hugh
* @date 2014年5月7日 下午3:28:01 
*  
*/
@Service("gameRevocationPlanUndoServiceImpl")
@Transactional
public class GameRevocationPlanUndoServiceImpl extends AbstractGameRevocationPlanService {

	private static final Logger log = LoggerFactory.getLogger(GameRevocationPlanUndoServiceImpl.class);
	
	/**
	* @Title: doRevocation
	* @Description:追号没执行的撤销
	* @param plan
	* @param detail
	* @param order
	* @return 
	* @see com.winterframework.firefrog.game.service.revocation.impl.AbstractGameRevocationPlanService#doRevocation(com.winterframework.firefrog.game.dao.vo.GamePlan, com.winterframework.firefrog.game.dao.vo.GamePlanDetail, com.winterframework.firefrog.game.dao.vo.GameOrder) 
	*/
	@Override
	public List<TORiskDTO> doRevocation(GamePlan plan, GamePlanDetail detail, GameOrder order, boolean isAskPlan) throws Exception{
		super.doRevocation(plan, detail, order, isAskPlan);		
		
		//只需要修改detail状态
		//在GamePlanServerImpl进行撤销
//		detail.setCancelTime(new Date());
//		detail.setStatus(2);
//		gamePlanDetailDaoImpl.update(detail);
		try{
			updateGamePlanRevocation(plan, detail, order, isAskPlan);
		}catch(Exception e){	
			log.error("计划更新状态和销售金额出错");
			return null;
		}		
		log.info("撤销未执行追号");
		
		return getUnfreezePlanDetialDto(plan, detail, order, isAskPlan);
	}

}
