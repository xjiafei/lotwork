package com.winterframework.firefrog.game.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGamePlanDetailDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGamePlanDetailService;

/**
 *GamePlanDetailServiceImpl
 * @ClassName
 * @Description 追号明细服务类
 * @author ibm
 * 2015年3月8日
 */
@Repository("gamePlanDetailServiceImpl") 
@Transactional
public class GamePlanDetailServiceImpl implements IGamePlanDetailService {
	
	@Resource(name="gamePlanDetailDaoImpl")
	private IGamePlanDetailDao gamePlanDetailDao; 
	
	@Resource(name="gameOrderServiceImpl")
	private IGameOrderService gameOrderService;
	
	@Override
	public int save(GameContext ctx,GamePlanDetail detail) throws Exception {
		if(detail==null) return 0;
		/*if(detail.getId()!=null){
			return this.gamePlanDetailDao.update(detail);
		}else{ 
			return this.gamePlanDetailDao.insert(detail); 
		}*/
		return 0;
	} 
	@Override
	public Map<String, Long> getSummary(Long planId) throws Exception {
		List<GamePlanDetail> detailList=this.gamePlanDetailDao.getGamePlanDetailsByPlanId(planId);
		if(detailList!=null && detailList.size()>0){
			Map<String,Long> summaryMap=new HashMap<String,Long>();
			Long soldAmount=0L;
			Long finishedCount=0L;
			Long canceledAmount=0L;
			Long canceledCount=0L;
			for(GamePlanDetail detail:detailList){
				if(detail.getStatus().intValue()!=GamePlanDetail.Status.UN_EXEC.getValue() && detail.getStatus().intValue()!=GamePlanDetail.Status.CANCEL.getValue()){
					soldAmount+=detail.getTotamount();
					finishedCount++;
				}else if(detail.getStatus().intValue()==GamePlanDetail.Status.CANCEL.getValue()){
					canceledAmount+=detail.getTotamount();
					canceledCount++;
				}
			}
			summaryMap.put("soldAmount", soldAmount);
			summaryMap.put("finishedCount",finishedCount);
			summaryMap.put("canceledAmount",canceledAmount); 
			summaryMap.put("canceledCount",canceledCount); 
			return summaryMap;
		}
		return null;
	}
	@Override
	public List<GamePlanDetail> getGamePlanDetailsByPlanId(Long planId)
			throws Exception { 
		return gamePlanDetailDao.getGamePlanDetailsByPlanId(planId);
	}
}
