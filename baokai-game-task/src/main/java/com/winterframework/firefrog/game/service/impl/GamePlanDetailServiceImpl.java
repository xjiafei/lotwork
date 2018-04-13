package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.IGamePlanDetailDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGamePlanDetailService;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationPlanNewService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;

/**
 *GamePlanDetailServiceImpl
 * @ClassName
 * @Description 追号明细服务类
 * @author ibm
 * 2014年9月7日
 */
@Repository("gamePlanDetailServiceImpl") 
@Transactional
public class GamePlanDetailServiceImpl implements IGamePlanDetailService {
	
	@Resource(name="gamePlanDetailDaoImpl")
	private IGamePlanDetailDao gamePlanDetailDao;
	@Resource(name="gamePlanDaoImpl")
	private IGamePlanDao gamePlanDao;
	@Resource(name="gameFundRiskServiceImpl")
	private IGameFundRiskService gameFundRiskService;
	@Resource(name="gameOrderServiceImpl")
	private IGameOrderService gameOrderService;
	@Resource(name="gameRevocationPlanNewServiceImpl")
	private IGameRevocationPlanNewService gameRevocationPlanNewService;
	@Resource(name="gamePlanService")
	private ICommonGamePlanService gamePlanService;
	
	@Override
	public int save(GameContext ctx,GamePlanDetail detail) throws Exception {
		if(detail==null) return 0;
		if(detail.getId()!=null){
			return this.gamePlanDetailDao.update(detail);
		}else{ 
			return this.gamePlanDetailDao.insert(detail); 
		}
	}
	@Override
	public int execute(GameContext ctx,GamePlanDetail detail) throws Exception {
		return 0;
		
	}
	@Override
	public int execute(GameContext ctx,Long planDetailId) throws Exception {
		return 0;
		
	}
	@Override
	public int pause(GameContext ctx,GamePlanDetail detail) throws Exception {
		if(detail!=null){  
			//如果非未执行状态则对应生成的订单未开奖才暂停该追号明细，同时计数被暂停期数并更新状态：暂停，且撤销对应的该订单
			//如果未执行状态，则直接更新状态：暂停
			GameOrder order=gameOrderService.getOrderByPlanDetailId(detail.getId());
			int pauseCount=0;
			boolean flag=false;
			if(order.getStatus().intValue()==GameOrder.Status.WAIT.getValue()){
				flag=true;
				pauseCount=1; 
				gameOrderService.cancel(ctx,order); 
			} 
			if(detail.getStatus().intValue()==GamePlanDetail.Status.UN_EXEC.getValue()){ 
				flag=true;
			}
			if(flag){ 
				detail.setStatus(GamePlanDetail.Status.PAUSE.getValue());
				gamePlanDetailDao.update(detail); 
			}
			return pauseCount;
		} 
		return 0;
	}
	@Override
	public int pause(GameContext ctx,Long planDetailId) throws Exception {
		GamePlanDetail detail=gamePlanDetailDao.getById(planDetailId);
		return this.pause(ctx, detail); 		
	}
	@Override
	public int continues(GameContext ctx,GamePlanDetail detail) throws Exception { 
		if(detail!=null && detail.getStatus().intValue()==GamePlanDetail.Status.PAUSE.getValue()){			
			detail.setStatus(GamePlanDetail.Status.UN_EXEC.getValue());
			gamePlanDetailDao.update(detail); 
			return 1;
		}
		return 0;		
	}
	@Override
	public int continues(GameContext ctx,Long planDetailId) throws Exception {
		GamePlanDetail detail=gamePlanDetailDao.getById(planDetailId);
		return this.continues(ctx, detail);
	}
	@Override
	public int cancel(GameContext ctx,GamePlanDetail detail) throws Exception { 
		if(detail==null) return 0;
		/**
		 * 1.委托追号明细撤销服务类
		 */ 
		//委托追号明细撤销服务类处理 
		int ret= this.gameRevocationPlanNewService.doRevocation(ctx, detail); 
		 
		/*//add 撤销封锁变价
		LockServiceReqeust lockServiceReqeust = new LockServiceReqeust();
		lockServiceReqeust.setIssueCode(issueCode);
		lockServiceReqeust.setLotteryId(detail.getPlanid());//这里将lotteryId设置为planId，
		lockServiceReqeust.setUserId(gamePackDaoImpl.getById(gamePlanDao.getById(detail.getPlanid()).getPackageId()).getUserid());			
        httpClient.invokeHttpWithoutResultType(serverPath + undoGamePlanLock, lockServiceReqeust);
 
        //资金处理
		fundRequest(toRiskDTOList); */
		return ret;
	}
	@Override
	public int cancel(GameContext ctx,Long planDetailId) throws Exception {
		GamePlanDetail detail=gamePlanDetailDao.getById(planDetailId);
		return this.cancel(ctx, detail);
	}
	
	@Override
	public List<GamePlanDetail> getGamePlanDetailFollowedByPlanAndIssue(Long planId,Long issueCode) throws Exception { 
		return this.gamePlanDetailDao.getGamePlanDetailFollowedByPlanAndIssue(planId, issueCode);
	}
	
	@Override
	public void reset(GameContext ctx, GamePlanDetail planDetail)
			throws Exception {  
		if(planDetail==null) return;
		/**
		 * 1.更改状态
		 * 2.级联恢复订单（如果存在订单）
		 * 3.投注冻结
		 */
		//1.更改状态
		GamePlanDetail detail=planDetail;
		GameOrder order=gameOrderService.getOrderByPlanDetailId(detail.getId());
		detail.setStatus(order==null?GamePlanDetail.Status.UN_EXEC.getValue():GamePlanDetail.Status.EXEC.getValue()); 
		detail.setCancelUser(null);
		detail.setCancelTime(null);  
		gamePlanDetailDao.update(detail);
		GamePlan gamePlan=gamePlanService.getGamePlanById(detail.getPlanid());
		gamePlan.setCancelIssue(gamePlan.getCancelIssue()-1);
		gamePlanService.updateGamePlan(gamePlan);
		
		//2.级联恢复订单（如果存在订单）			
		gameOrderService.reset(ctx, order);
		
		//3.投注冻结
		//这一段可以封装成资金工具类
		List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();
		GamePlan plan=gamePlanDao.getPlanById(detail.getPlanid());
		if(plan==null){
			throw new Exception("追号信息不存在(id:"+detail.getPlanid()+")");
		}
		//投注DTO生成
		TORiskDTO freezerPlanDTO = new TORiskDTO();
		freezerPlanDTO.setAmount(detail.getTotamount() + "");
		freezerPlanDTO.setIssueCode(detail.getIssueCode());
		freezerPlanDTO.setLotteryid(plan.getLotteryid());
		freezerPlanDTO.setPlanCodeList(plan.getPlanCode());
		freezerPlanDTO.setType(GameFundTypesUtils.GAME_PLAN_FREEZER_DETEAIL_TYPE);
		freezerPlanDTO.setUserid(plan.getPlanUserId().toString()); 
		toRiskDTOList.add(freezerPlanDTO); 

		//调用风控资金冻结接口
		gameFundRiskService.betAmountFreezer(toRiskDTOList);
	}
	@Override
	public Map<String, Long> getSummary(Long planId) throws Exception {
		List<GamePlanDetail> detailList=this.gamePlanDetailDao.selectGamePlanDetailsByPlanId(planId);
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
}
