package com.winterframework.firefrog.game.service.revocation.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.game.dao.IGameOrderWinDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.exception.GameRevocationException;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IGameOrderFundService;
import com.winterframework.firefrog.game.service.impl.GameIssueFacadeServiceImpl;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationIssueService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;

/** 
* @ClassName GameRevocationIssueToBeforeDrawServiceImpl 
* @Description 撤销一期到开奖前 （重新输入开奖号码时调用）
* @author  hugh
* @date 2014年5月7日 下午4:12:54 
*  
*/
@Service("gameRevocationIssueToBeforeDrawServiceImpl")
@Transactional
public class GameRevocationIssueToBeforeDrawServiceImpl extends AbstractGameRevocationService implements
		IGameRevocationIssueService {
	private static final Logger logger = LoggerFactory.getLogger(GameIssueFacadeServiceImpl.class);

	@Resource(name = "gameRevocationIssueSimplePlanServiceImpl")
	private IGameRevocationIssueService gameRevocationIssueSimplePlanServiceImpl;

	@Resource(name = "gameOrderWinDaoImpl")
	private IGameOrderWinDao gameOrderWinDaoImpl;
	@Resource(name = "gamePlanService")
	private ICommonGamePlanService gamePlanService; 
	@Resource(name = "gameOrderFundServcieImpl")
	protected IGameOrderFundService gameOrderFundServcie;
	
	
	/**
	* @Title: doRevocation
	* @Description:撤销一期到开奖前 （重新输入开奖号码时调用）
	* @param lotteryId
	* @param issueCode 
	* @see com.winterframework.firefrog.game.service.revocation.IGameRevocationIssueService#cancel(java.lang.Long, java.lang.Long) 
	*/
	@Override
	public void doRevocation(Long lotteryId, Long issueCode) throws Exception{
		try {
			List<GameOrder> orders = getOrders(lotteryId, issueCode);
			List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();
			ProcessResult result=new ProcessResult();
			for (GameOrder order : orders) {
				/**
				 * 管理员撤销的订单(不重开），不处理  注释掉
				 * 未开奖的订单，不处理
				 * 已开奖的订单：
				 * 	中->中	处理
					中->不中	处理
					不中->中	处理
					不中->不中  不处理
				 */
				//其余订单处理
				List<TORiskDTO> toRiskDTOList1 = gameRevocationOrderStatusMachineImpl.doRevocationToRedraw(result,order);
				if (toRiskDTOList1 != null) {
					toRiskDTOList.addAll(toRiskDTOList1);
				}
				/*if(order!=null && order.getStatus().intValue()==OrderStatus.CANCEL.getValue() && order.getPlanId()!=null && order.getPlanId()!=0L) { 
					//如果已经撤销（非系统撤销）的订单 状态恢复至开奖前
					gameOrderFundServcie.updateOrderFreezeStatus(order);
				}else{
					//其余订单处理
					List<TORiskDTO> toRiskDTOList1 = gameRevocationOrderStatusMachineImpl.doRevocationToBeforeDraw(result,order);
					if (toRiskDTOList1 != null) {
						toRiskDTOList.addAll(toRiskDTOList1);
					}
				}*/
			} 
			//清空缓存信息（包含缓存）
			clearWarn(lotteryId, issueCode);

			//删除中奖信息
			gameOrderWinDaoImpl.deleteByLotteryIssue(lotteryId, issueCode); 
			//调用资金交易
			fundRequest(toRiskDTOList);

		} catch (Exception e) {
			logger.error("撤销奖期错误", e);
			throw new GameRevocationException("撤销奖期错误"+e.getMessage());
		}
	}
	/**
	* @Title: doRevocation
	* @Description:撤销一期到开奖前 （重新输入开奖号码时调用）
	* @param lotteryId
	* @param issueCode 
	* @see com.winterframework.firefrog.game.service.revocation.IGameRevocationIssueService#cancel(java.lang.Long, java.lang.Long) 
	*/
	//@Override
	public void doRevocation_bk(Long lotteryId, Long issueCode) throws Exception{
		try {
			List<GameOrder> orders = getOrders(lotteryId, issueCode);
			List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();
			ProcessResult result=new ProcessResult();
			for (GameOrder order : orders) {
				/**
				 * 管理员撤销的订单(不重开），不处理  注释掉
				 * 未开奖的订单，不处理
				 * 已开奖的订单：
				 * 	中->中	处理
					中->不中	处理
					不中->中	处理
					不中->不中  不处理
				 */
				//其余订单处理
				List<TORiskDTO> toRiskDTOList1 = gameRevocationOrderStatusMachineImpl.doRevocationToBeforeDraw(result,order);
				if (toRiskDTOList1 != null) {
					toRiskDTOList.addAll(toRiskDTOList1);
				}
				/*if(order!=null && order.getStatus().intValue()==OrderStatus.CANCEL.getValue() && order.getPlanId()!=null && order.getPlanId()!=0L) { 
					//如果已经撤销（非系统撤销）的订单 状态恢复至开奖前
					gameOrderFundServcie.updateOrderFreezeStatus(order);
				}else{
					//其余订单处理
					List<TORiskDTO> toRiskDTOList1 = gameRevocationOrderStatusMachineImpl.doRevocationToBeforeDraw(result,order);
					if (toRiskDTOList1 != null) {
						toRiskDTOList.addAll(toRiskDTOList1);
					}
				}*/
			} 
			//清空缓存信息（包含缓存）
			clearWarn(lotteryId, issueCode);

			//删除中奖信息
			gameOrderWinDaoImpl.deleteByLotteryIssue(lotteryId, issueCode);
			//生成调度任务(可能存在多个奖期）
			List<Long> issueCodeList = (List<Long>) result.getFromRetParaMap(String.valueOf(lotteryId));
			gamePlanService.addMakeupOrderDrawEvent(lotteryId, issueCodeList); 
			//调用资金交易
			fundRequest(toRiskDTOList);

		} catch (Exception e) {
			logger.error("撤销奖期错误", e);
			throw new GameRevocationException("撤销奖期错误"+e.getMessage());
		}
	}

}