package com.winterframework.firefrog.game.service.revocation.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.IGamePlanDetailDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.exception.GameRiskException;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.game.service.IGameWarnService;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationOrderService;
import com.winterframework.firefrog.game.web.dto.LockServiceReqeust;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName AbstractGameRevocationService 
* @Description 撤销父类 
* @author  hugh
* @date 2014年5月12日 下午2:40:18 
*  
*/
public abstract class AbstractGameRevocationService {
	protected Logger log = LoggerFactory.getLogger(AbstractGameRevocationService.class);

	@Resource(name = "gameOrderDaoImpl")
	protected IGameOrderDao gameOrderDao;

	@Resource(name = "gameWarnServiceImpl")
	protected IGameWarnService gameWarnServiceImpl;

	@Resource(name = "gameFundRiskServiceImpl")
	protected IGameFundRiskService gameFundRiskServiceImpl;

	@Resource(name = "gamePlanDaoImpl")
	protected IGamePlanDao gamePlanDao;

	@Resource(name = "gamePlanDetailDaoImpl")
	protected IGamePlanDetailDao gamePlanDetailDaoImpl;

	@Resource(name = "gameRevocationOrderStatusMachineImpl")
	protected IGameRevocationOrderService gameRevocationOrderStatusMachineImpl;

	@Resource(name = "gameIssueDaoImpl")
	protected IGameIssueDao gameIssueDao;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.business.connect")
	private String serverPath;
	
	@PropertyConfig(value = "url.game.nudoLock")
	private String nudoLock;
	
	void undoLock(GameOrder order){
		LockServiceReqeust lockServiceReqeust = new LockServiceReqeust();
		lockServiceReqeust.setIssueCode(order.getIssueCode());
		lockServiceReqeust.setLotteryId(order.getLotteryid());
		lockServiceReqeust.setOrderId(order.getId());
		lockServiceReqeust.setUserId(order.getUserid());
		try {
			httpClient.invokeHttpWithoutResultType(serverPath + nudoLock, lockServiceReqeust);
		} catch (Exception e) {
			log.error("请求 nudoLock失败");
		}
	}
	
	/** 
	* @Title: fundRequest 
	* @Description: 调用资金接口
	* @param toRiskDTOList
	*/
	public void fundRequest(List<TORiskDTO> toRiskDTOList) {
		if (toRiskDTOList == null) {
			return;
		}
		log.info("开始请求审核中心资金系统");
		try {
			List<TORiskDTO> distributeAwardList = new ArrayList<TORiskDTO>();
			List<TORiskDTO> betAmountFreezerList = new ArrayList<TORiskDTO>();

			for (TORiskDTO toRiskDTO : toRiskDTOList) {
				log.info("撤销中心  toRiskDTO" + toRiskDTO.getType());
				if (toRiskDTO.getType().intValue() == GameFundTypesUtils.GAME_USER_CANCEL_BET_UNFREEZER_DETEAIL_TYPE) {
					log.info("撤销中心  撤销订单");
					distributeAwardList.add(toRiskDTO);
				} else if (toRiskDTO.getType().intValue() == GameFundTypesUtils.GAME_SYS_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE) {
					log.info("撤销中心  撤销追号");
					//distributeAwardList.add(toRiskDTO);
				} else if (toRiskDTO.getType().intValue() == GameFundTypesUtils.GAME_BET_FREEZER_DETEAIL_TYPE) {
					log.info("撤销中心  冻结");
					betAmountFreezerList.add(toRiskDTO);
				}
			}
			toRiskDTOList.removeAll(distributeAwardList);
			toRiskDTOList.removeAll(betAmountFreezerList);

			if (!distributeAwardList.isEmpty()) {
				log.info("撤销distributeAward"+distributeAwardList.size());
				gameFundRiskServiceImpl.distributeAward(distributeAwardList);
			}
			if (!toRiskDTOList.isEmpty()) {
				log.info("撤销cancelFee"+toRiskDTOList.size());
				gameFundRiskServiceImpl.cancelFee(toRiskDTOList);
			}
			if (!betAmountFreezerList.isEmpty()) {
				log.info("撤销betAmountFreezer"+betAmountFreezerList.size());
				gameFundRiskServiceImpl.betAmountFreezer(betAmountFreezerList);
			}
		} catch (Exception e) {
			log.error("请求审核系统资金接口出错", e);
			throw new GameRiskException("请求审核系统资金接口出错" +e.getMessage());
		}
	}

	/** 
	* @Title: getGamePlanByOrderId 
	* @Description: 通过订单获取追号信息
	* @param order
	* @return
	*/
	public GamePlan getGamePlanByOrderId(GameOrder order) {
		GamePlan plan = gamePlanDao.getGamePlanByOrderId(order.getId());
		if (plan != null) {
			plan.setUserId(order.getUserid());
		}
		return plan;
	}

	/** 
	* @Title: getGamePlanDetailByOrderId 
	* @Description: 通过订单，和期号获取追号详情
	* @param plan
	* @param issueCode
	* @return
	*/
	public GamePlanDetail getGamePlanDetailByOrderId(GamePlan plan, Long issueCode) {
		return gamePlanDetailDaoImpl.queryGamePlanDetailByPlanIDWithOutStatus(plan.getId(), issueCode);
	}

	/** 
	* @Title: selectOneIssueUndoGamePlanDetailsByLotteryIssue 
	* @Description: 获取一期未执行的追号详情
	* @param lotteryId
	* @param issueCode
	* @return
	*/
	public List<GamePlanDetail> selectOneIssueUndoGamePlanDetailsByLotteryIssue(Long lotteryId, Long issueCode) {
		return gamePlanDetailDaoImpl.selectOneIssueUndoGamePlanDetailsByLotteryIssue(issueCode, lotteryId);
	}

	/** 
	* @Title: clearWarnCache 
	* @Description: 清空警告缓存数据
	* @param lotteryId
	* @param issueCode
	*/
	public void clearWarnCache(Long lotteryId, Long issueCode) {
		gameWarnServiceImpl.clearWarn(lotteryId, issueCode);
	}

	/** 
	* @Title: clearWarn 
	* @Description: 清空所有警告数据及缓存
	* @param lotteryId
	* @param issueCode
	*/
	public void clearWarn(Long lotteryId, Long issueCode) {
		gameWarnServiceImpl.clearWarn(lotteryId, issueCode);
	}

	/** 
	* @Title: getOrders 
	* @Description: 通过期号奖期获取订单
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception
	*/
	public List<GameOrder> getOrders(Long lotteryId, Long issueCode) throws Exception {

		List<GameOrder> orders = gameOrderDao.queryOrderByLotteryIdAndIssueCode(lotteryId, issueCode);
		if (orders == null) {
			orders = new ArrayList<GameOrder>();
		}
		return orders;
	}

	/** 
	* @Title: getOrders 
	* @Description: 查询开奖时间前的一期订单
	* @param lotteryId
	* @param issueCode
	* @param time
	* @return
	*/
	public List<GameOrder> getOrders(Long lotteryId, Long issueCode, Date time) {

		List<GameOrder> orders = gameOrderDao.getBetAfterDrawTimeOrders(lotteryId, issueCode, time);
		if (orders == null) {
			orders = new ArrayList<GameOrder>();
		}
		return orders;
	}

}
