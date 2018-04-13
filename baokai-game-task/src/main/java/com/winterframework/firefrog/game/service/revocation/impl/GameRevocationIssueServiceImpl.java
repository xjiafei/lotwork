package com.winterframework.firefrog.game.service.revocation.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.game.dao.IGamePlanDetailDao;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePackage;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.exception.GameRevocationException;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IGameControlEventService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGamePackageService;
import com.winterframework.firefrog.game.service.IGamePlanDetailService;
import com.winterframework.firefrog.game.service.impl.GameIssueFacadeServiceImpl;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationIssueService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
/** 
* @ClassName GameRevocationIssueServiceImpl 
* @Description 撤销一期方案（包括订单以及这一期没生成订单的追号）
* @author  hugh
* @date 2014年5月12日 下午1:35:50 
*  
*/
@Service("gameRevocationIssueServiceImpl")
@Transactional
public class GameRevocationIssueServiceImpl extends AbstractGameRevocationService implements
		IGameRevocationIssueService {
	private static final Logger logger = LoggerFactory.getLogger(GameIssueFacadeServiceImpl.class);
	
	@Resource(name = "gameRevocationIssueSimplePlanServiceImpl")
	private IGameRevocationIssueService gameRevocationIssueSimplePlanServiceImpl; 
	@Resource(name = "gamePlanService")
	private ICommonGamePlanService gamePlanService; 
	 
	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderService; 
	
	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService; 
	
	@Resource(name="gamePlanDetailDaoImpl")
	private IGamePlanDetailDao gamePlanDetailDao;
	
	@Resource(name = "gamePlanDetailServiceImpl")
	private IGamePlanDetailService gamePlanDetailServiceImpl; 
	@Resource(name = "gamePackageServiceImpl")
	private IGamePackageService gamePackageService; 
	@Resource(name = "gameControlEventServiceImpl")
	private IGameControlEventService gameControlEventService; 
	
	
	/**
	* @Title: doRevocation
	* @Description:撤销一期方案（包括订单以及这一期没生产订单的追号）
	* @param lotteryId
	* @param issueCode 
	* @see com.winterframework.firefrog.game.service.revocation.IGameRe	vocationIssueService#cancel(java.lang.Long, java.lang.Long) 
	*/ 
	public void doRevocation_bk(Long lotteryId, Long issueCode) throws Exception{
		/**
		 * 1.奖期所有方案：
		 * 		追号：以追号发起撤销，通知下一期追号
		 * 		非追号：以订单发起撤销
		 * 3.更新奖期状态：奖期暂停状态（撤销）、奖期状态（结束）
		 */
		GameContext ctx=new GameContext();
		GameIssue issue = gameIssueDao.getGameIssueByLotteryIssue(lotteryId, issueCode);
		List<GamePackage> packageList=this.gamePackageService.getByLotteryIdAndIssueCode(lotteryId, issueCode);
		if(packageList!=null && packageList.size()>0){
			for(GamePackage packagee:packageList){
				if(packagee.getType().intValue()==GamePackage.Type.PLAN.getValue()){
					GamePlan plan=this.gamePlanService.getByPackageId(packagee.getId());
					if(plan!=null){ 
						List<GamePlanDetail> planDetailList=this.gamePlanDetailServiceImpl.getGamePlanDetailFollowedByPlanAndIssue(plan.getId(), issueCode);
						if(planDetailList!=null && planDetailList.size()>0){
							for(GamePlanDetail detail:planDetailList){
								this.gamePlanDetailServiceImpl.cancel(ctx, detail);
								
								List<TORiskDTO> riskDtoList=(List<TORiskDTO>)ctx.get("RISKDTOLIST");  
								fundRequest(riskDtoList);
								ctx.set("RISKDTOLIST", null);
							}
						}
						//通知下一期追号？？？？？？？？？？？？
						//createNextIssueGamePlan(ctx,lotteryId, nextIssue.getIssueCode(), planId,issueCode);
					}
				}else{
					List<GameOrder> orderList=this.gameOrderService.getByPackageId(packagee.getId());
					if(orderList!=null && orderList.size()>0){
						for(GameOrder order:orderList){
							this.gameOrderService.cancel(ctx, order);
						}
					}
				}
			}
		}  
		//撤销审核和缓存
		gameWarnServiceImpl.clearWarn(lotteryId, issueCode);
		
		//调用资金交易
		List<TORiskDTO> riskDtoList=(List<TORiskDTO>)ctx.get("RISKDTOLIST");
		fundRequest(riskDtoList); 
		
		//更新奖期状态 
		issue.setStatus(new Long(GameIssue.Status.FINISH.getValue()));
		issue.setPauseStatus(Integer.valueOf(GameIssue.PauseStatus.CANCEL.getValue()));
		this.gameIssueService.save(ctx, issue); 


		//先注释--Gary
		//查询该撤销奖期所有的中奖即停过程处理后的在当前销售期以及以后的追号detail,必须是系统撤销
		List<GamePlanDetail> resetLists=gamePlanDetailDao.getResetGamePlanDetailList(lotteryId, issueCode);
		if(!resetLists.isEmpty()){
			for(GamePlanDetail planDetail:resetLists){
				gamePlanDetailServiceImpl.reset(new GameContext(), planDetail);
			}
		}
		
		
		//生成调度任务(可能存在多个奖期）
		//List<Long> issueCodeList = (List<Long>) result.getFromRetParaMap(String.valueOf(lotteryId));
		//gamePlanService.addMakeupOrderDrawEvent(lotteryId, issueCodeList); 
	}
	/**
	* @Title: doRevocation
	* @Description:撤销一期方案（包括订单以及这一期没生产订单的追号）
	* @param lotteryId
	* @param issueCode 
	* @see com.winterframework.firefrog.game.service.revocation.IGameRevocationIssueService#cancel(java.lang.Long, java.lang.Long) 
	*/ 
	public void doRevocation(Long lotteryId, Long issueCode) throws Exception{ 
		try {
			List<GameOrder> orders = getOrders(lotteryId, issueCode);
			List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();
			
			ProcessResult result=new ProcessResult();
			for (GameOrder order : orders) {
				//一单撤销
				List<TORiskDTO> toRiskDTOList1 = gameRevocationOrderStatusMachineImpl.doRevocation(result,order);
				if (toRiskDTOList1 != null) {
					toRiskDTOList.addAll(toRiskDTOList1);
				}

			}

			//撤销审核缓存
			clearWarnCache(lotteryId, issueCode);

			//调用资金交易-最后调用
			//fundRequest(toRiskDTOList);

			//撤销未生成订单的追号
			gameRevocationIssueSimplePlanServiceImpl.doRevocation(lotteryId, issueCode);
			
			////（是否追中即停 且 中奖）？恢复后续追号 并通知下一期重开奖 
			//DSD酸
			  
			try {
				//更新奖期状态
				GameIssue gameIssue = gameIssueDao.getGameIssueByLotteryIssue(lotteryId, issueCode);
				gameIssue.setStatus(7L);
				gameIssue.setPauseStatus(2);
				gameIssueDao.update(gameIssue);
			} catch (Exception e) {
				log.error("update issue fail", e);
				throw new GameRevocationException("update issue fail");
			} 
			
			/*先注释--Gary
			//查询该撤销奖期所有的中奖即停过程处理后的在当前销售期以及以后的追号detail,必须是系统撤销
			List<GamePlanDetail> resetLists=gamePlanDetailDao.getResetGamePlanDetailList(lotteryId, issueCode);
			if(!resetLists.isEmpty()){
				for(GamePlanDetail planDetail:resetLists){
					gamePlanDetailServiceImpl.reset(new GameContext(), planDetail);
				}
			}*/
			
			
			//生成调度任务(可能存在多个奖期）
			List<Long> issueCodeList = (List<Long>) result.getFromRetParaMap(String.valueOf(lotteryId));
			gamePlanService.addMakeupOrderDrawEvent(lotteryId, issueCodeList); 
			
			//调用资金交易
			fundRequest(toRiskDTOList);
			
			try {  
				GameIssue issue=new GameIssue();
				issue.setLotteryid(lotteryId);
				issue.setIssueCode(issueCode);
				issue.setSaleStartTime(DateUtils.currentDate());
				issue.setSaleEndTime(DateUtils.currentDate());
				gameControlEventService.addCreateWinReportEvent(issue);
			} catch (Exception e) {
				log.error("生成奖期盈亏报表事件失败", e);
			}
		} catch (Exception e) {
			logger.error("撤销奖期错误", e);
			throw new Exception("撤销奖期错误", e);
		}
	}
}