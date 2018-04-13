package com.winterframework.firefrog.game.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.common.util.SNUtil;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGameOrderWinDao;
import com.winterframework.firefrog.game.dao.IGamePackageDao;
import com.winterframework.firefrog.game.dao.IGamePackageItemDao;
import com.winterframework.firefrog.game.dao.IGameReturnPointDao;
import com.winterframework.firefrog.game.dao.IGameSeriesConfigDao;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderLog;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GameRetPoint;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.dao.vo.GameOrder.FundStatus;
import com.winterframework.firefrog.game.entity.CancelMode;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameOrder.OrderStatus;
import com.winterframework.firefrog.game.entity.GamePackage;
import com.winterframework.firefrog.game.entity.GamePackage.GamePackageType;
import com.winterframework.firefrog.game.entity.GamePackageItem;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.firefrog.game.exception.GameIssueStatusStopSaleException;
import com.winterframework.firefrog.game.exception.UserBalErrorException;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IGameDrawResultService;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.game.service.IGameOrderDrawService;
import com.winterframework.firefrog.game.service.IGameOrderLogService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGameOrderWinService;
import com.winterframework.firefrog.game.service.IGameReturnPointService;
import com.winterframework.firefrog.game.service.IGameSlipService;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationOrderNewService;
import com.winterframework.firefrog.game.web.dto.NoticeSenderRequest;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

/**
 * 
* @ClassName: GameOrderServiceImpl 
* @Description: 游戏订单接口服务。
* @author Richard, Alan
* @date 2013-11-27 下午3:03:55 
*
 */
@Service("gameOrderServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GameOrderServiceImpl  implements IGameOrderService {

	private static final Logger log = LoggerFactory.getLogger(GameOrderServiceImpl.class);

	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDao;

	@Resource(name = "gameOrderWinDaoImpl")
	private IGameOrderWinDao gameOrderWinDao;

	@Resource(name = "gameIssueDaoImpl")
	private IGameIssueDao gameIssueDao;

	@Resource(name = "SNUtil")
	private SNUtil snUtil;

	@Resource(name = "gameSlipDaoImpl")
	private IGameSlipDao gameSlipDao;

	@Resource(name = "gamePackDaoImpl")
	private IGamePackageDao gamePackageDao;

	@Resource(name = "gamePackageItemDao")
	private IGamePackageItemDao gamePackageItemDao;

	@Resource(name = "gameFundRiskServiceImpl")
	private IGameFundRiskService fundRiskService;

	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao customerDao;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	private String getUserBal;

	@PropertyConfig(value = "url.baseFundUrl")
	private String baseFundUrl;

	@PropertyConfig(value = "url.frontUrl")
	private String frontUrl;

	@PropertyConfig(value = "imageserver.url")
	private String imageUrl;

	@PropertyConfig(value = "url.notice.taskid")
	private String taskId;

	
	@PropertyConfig(value = "url.notice.detail")
	private String noticeDetail;

	@Resource(name = "gameReturnPointDaoImpl")
	private IGameReturnPointDao gameReturnPointDao;

	@Resource(name = "gameSeriesConfigDaoImpl")
	private IGameSeriesConfigDao gameSeriesConfigDao;

	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesDaoImpl;
	@Resource(name = "gameSlipServiceImpl")
	private IGameSlipService gameSlipService;
	@Resource(name = "gameRevocationOrderServiceImpl")
	private IGameRevocationOrderNewService gameRevocationOrderService; 
	@Resource(name = "gameOrderLogServiceImpl")
	private IGameOrderLogService gameOrderLogService;
	@Resource(name = "gameOrderDrawServiceImpl")
	private IGameOrderDrawService gameOrderDrawService;
	
	@Resource(name = "gameDrawResultServiceImpl")
	protected IGameDrawResultService drawResultService;  
	@Resource(name = "gameOrderWinServiceImpl")
	protected IGameOrderWinService gameOrderWinService; 
	@Resource(name = "gameReturnPointServiceImpl")
	protected IGameReturnPointService gameReturnPointService;  
	@Resource(name = "gamePlanService")
	protected ICommonGamePlanService gamePlanService; 
	@Override
	public int save(GameContext ctx, GameOrder order) throws Exception {
		if(order==null) return 0;
		if(order.getId()==null){
			this.gameOrderDao.insert(order);
		}else{
			this.gameOrderDao.update(order);
		}
		return 1;
	}
	@Override
	public int cancel(GameContext ctx, GameOrder order) throws Exception {
		log.debug("调用订单撤销行为");
		if(order==null) return 0;
		int status=order.getStatus();
		if(status==GameOrder.Status.CANCEL.getValue()) return 0;
		
		this.addOrderLog(ctx,order,"cancel","订单撤销"); 
		//委托订单撤销服务类处理 
		int ret= this.gameRevocationOrderService.doRevocation(ctx, order); 
		return ret;
	}
	@Override
	public int cancel(GameContext ctx, Long orderId) throws Exception { 
		return this.cancel(ctx,gameOrderDao.getById(orderId)); 
	}
	@Override
	public int draw(GameContext ctx, GameOrder order) throws Exception {
		return this.gameOrderDrawService.doBusiness(ctx, order); 
	} 

	@Override
	public int excep(GameContext ctx, GameOrder order) throws Exception {
		/**
		 * 1.冻结中奖订单 
		 * 2.冻结返点
		 * 2.更新订单
		 */
		if(order==null) return 0;
		this.addOrderLog(ctx,order,"excep","订单异常");
		
		this.gameOrderWinService.freeze(ctx,order);
		
		this.gameReturnPointService.freeze(ctx, order);
		
		order.setStatus(GameOrder.Status.EXCEP.getValue());
		order.setCalculateWinTime(DateUtils.currentDate());
		order.setCancelTime(null);
		order.setCancelModes(0); 
		this.save(ctx,order);  
		 
		return 1;
	}
	@Override
	public int addOrderLog(GameContext ctx, GameOrder order, String operation,
			String remark) throws Exception {
		log.debug("写订单操作日志");
		if(order==null) return 0;
		GameOrderLog orderLog=new GameOrderLog();
		orderLog.setOrderId(order.getId());
		orderLog.setOperation(operation);
		orderLog.setRemark(remark);
		orderLog.setUserId(-1L);
		orderLog.setCreateTime(DateUtils.currentDate());
		this.gameOrderLogService.save(ctx,orderLog);
		
		return 1;
	} 
	/**
	 * 
	* @Title: getGameOrderByLotteryIdAndIssueCode 
	* @Description: 根据LotteryId和IssueCod获取订单信息
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception
	 */
	@Override
	public List<GameOrder> getGameOrderByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception {

		return gameOrderDao.queryOrderByLotteryIdAndIssueCodeForKaiJiang(lotteryId, issueCode);

	}
	@Override
	public List<GameOrder> getFromPlanByIssueAndLottery(Long lotteryId,
			Long issueCode) {
		return gameOrderDao.getFromPlanByIssueAndLottery(lotteryId, issueCode);
	}

	/**
	 * 取消游戏订单。
	* Title: revocationOrders
	* Description:
	* @param lotteryid
	* @param issueCode
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameOrderService#revocationOrders(java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = false)
	public void updateOrders(Long lotteryid, Long issueCode, Integer status) throws Exception {
		Map<String, Object> orderMap = new HashMap<String, Object>();

		orderMap.put("status", status);
		orderMap.put("lotteryid", lotteryid);
		orderMap.put("issueCode", issueCode);

		gameOrderDao.updateOrders(orderMap);
	}

	/**
	 * 更新中奖订单信息。
	* Title: revocationWinOrders
	* Description:
	* @param lotteryid
	* @param issueCode
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameOrderService#revocationWinOrders(java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = false)
	public void revocationWinOrders(Long lotteryid, Long issueCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("status", 3);
		map.put("lotteryid", lotteryid);
		map.put("issueCode", issueCode);

		gameOrderWinDao.updateGameOrderWins(map);
	}

	/**
	 * 
	* @Title: savePackageAnditems 
	* @Description:保存订单方案信息。
	* @param gameOrder
	* @return
	* @throws Exception
	 */
	private Long savePackageAnditems(com.winterframework.firefrog.game.entity.GameOrder gameOrder,
			GamePackageType packageType) throws Exception {

		GamePackage gamePackage = gameOrder.getGamePackage();
		if(gameOrder.getGameIssue().getWebIssueCode()==null){
			gameOrder.getGameIssue().setWebIssueCode(gameIssueDao.queryGameIssue(gameOrder.getLottery().getLotteryId(),gameOrder.getGameIssue().getIssueCode()).getWebIssueCode());
		}
		gamePackage.setPackageCode(snUtil.createSN(SNUtil.TYPE_ORDER, gameOrder.getLottery().getLotteryId(), gameOrder
				.getGameIssue().getWebIssueCode()));

		//设置返点链以及客户返点链
		User user = customerDao.queryUserById(gamePackage.getUser().getId());
		String userChain = user.getUserProfile().getUserChain();
		String retUserChain = gameReturnPointDao.getRetunPointChain(gamePackage.getItemList(), userChain);
		gamePackage.setRetUserChain(retUserChain);

		Long gamePackageId = gamePackageDao.savePackage(gamePackage);
		//2.生成方案明细表
		saveGamePackageItems(gamePackage.getItemList(), gamePackageId);
		return gamePackageId;
	}

	private void saveGamePackageItems(List<GamePackageItem> packageItemList, Long gamePackageId) throws Exception {
		List<GamePackageItem> list = new ArrayList<GamePackageItem>();
		for (GamePackageItem betDetail : packageItemList) {
			betDetail.getGamePackage().setPackageId(gamePackageId);
			list.add(betDetail);
		}
		gamePackageItemDao.savePackageitemList(list);
	}

	/**
	 * 更新游戏订单信息。
	* Title: updateGameOrder
	* Description:
	* @param order
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameOrderService#updateGameOrder(com.winterframework.firefrog.game.dao.vo.GameOrder)
	 */
	@Override
	@Transactional(readOnly = false)
	public void updateGameOrder(GameOrder order) throws Exception {

		gameOrderDao.update(order);

	}

	@Override
	@Transactional(readOnly = false)
	public void updateGameOrderMMC(GameOrder order) throws Exception {

		gameOrderDao.updateMMC(order);

	}
	
	@Override
	public GameOrder getGameOrderById(Long orderId) throws Exception {
		return gameOrderDao.getById(orderId);
	}

	@Override
	public List<GameOrder> getGameOrderByPreGameIssueAndLotteryId(Long issueCode, Long lotteryId) throws Exception {
		return gameOrderDao.getGameOrderByPreGameIssueAndLotterId(issueCode, lotteryId);
	}

	@Override
	public List<GameOrder> getGameOrderListByLotteryAndIssueCode(Long lotteryId, Long issueCode) throws Exception {
		return gameOrderDao.getGameOrderListByLotteryAndIssueCode(lotteryId, issueCode);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateOrders(Long lotteryid, Long startIssueCode, Long endIssueCode, Integer status) throws Exception {
		Map<String, Object> orderMap = new HashMap<String, Object>();

		orderMap.put("status", status);
		orderMap.put("cancelTime", new Date());
		orderMap.put("cancelModes", 2);
		orderMap.put("lotteryid", lotteryid);
		orderMap.put("startIssueCode", startIssueCode);
		orderMap.put("endIssueCode", endIssueCode);

		gameOrderDao.updateOrders2(orderMap);
	}

	@Override
	public GameOrder selectGameOrderByPlanIdAndIssueCode(Long planid, Long issueCode) {
		return gameOrderDao.selectGameOrderByPlanIdAndIssueCode(planid, issueCode);
	}

	@Override
	public List<GameOrder> selectGameOrderByPlanid(Long planid) {
		return gameOrderDao.selectGameOrderByPlanid(planid);
	}

	@Override
	public Long selectOrderWinByOrderCode(String orderCode) {
		return gameOrderDao.selectOrderWinByOrderCode(orderCode);
	}

	@Override
	public List<GameOrder> selectFollowGameOrdersByPlanid(Long planid, Long orderid) {
		Map<String, Object> orderMap = new HashMap<String, Object>();

		orderMap.put("planid", planid);
		orderMap.put("orderid", orderid);

		return gameOrderDao.selectFollowGameOrderByPlanid(orderMap);
	}

	@Override
	public List<GameOrder> getGameOrderByLotteryIdAndIssueCodeStatus(Long lotteryId, Long issueCode,
			List<Integer> statusList) {
		Map<String, Object> orderMap = new HashMap<String, Object>();

		orderMap.put("lotteryId", lotteryId);
		orderMap.put("issueCode", issueCode);
		orderMap.put("statusList", statusList);
		return gameOrderDao.getGameOrderByLotteryIdAndIssueCodeStatus(orderMap);
	}

	@Override
	public void undoRedoGameOrders(Long lotteryId, Long issueCode, Date saleTime, int aimStatus) {
		Map<String, Object> orderMap = new HashMap<String, Object>();
		orderMap.put("lotteryId", lotteryId);
		orderMap.put("issueCode", issueCode);
		if (saleTime != null) {
			orderMap.put("saleTime", saleTime);
		}
		orderMap.put("status", aimStatus);
		if (aimStatus == OrderStatus.CANCEL.getValue()) {
			orderMap.put("cancelTime", new Date());
			orderMap.put("cancelModes", CancelMode.SYSTEM.getValue());
		}
		gameOrderDao.undoGameOrders(orderMap);
	}

	@Override
	public Date getCalculateTimeByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) {
		Map<String, Object> orderMap = new HashMap<String, Object>();
		orderMap.put("lotteryId", lotteryId);
		orderMap.put("issueCode", issueCode);
		return gameOrderDao.getCalculateTimeByLotteryIdAndIssueCode(orderMap);
	}

	@Override
	public List<GameOrder> getGameOrderByLotteryInfo(Long lotteryId, Long issueCode, List<Integer> aimStatus,
			Date saleTime) {
		Map<String, Object> orderMap = new HashMap<String, Object>();
		orderMap.put("lotteryId", lotteryId);
		orderMap.put("issueCode", issueCode);
		if (saleTime != null) {
			orderMap.put("saleTime", saleTime);
		}
		orderMap.put("aimStatus", aimStatus);
		return gameOrderDao.getGameOrderByLotteryInfo(orderMap);
	}

	public Long getUserBal(Long userId) throws Exception {
		Response<Long> response = new Response<Long>();
		try {
			if (null != userId) {
				response = httpClient.invokeHttp(baseFundUrl + getUserBal, userId, Long.class);
				return response.getBody().getResult().longValue();
			} else {
				throw new RuntimeException("getUserBal error");
			}
		} catch (Exception e) {
			log.error("getUserBal error:", e);
		}
		return response.getBody().getResult().longValue();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public Long saveGameOrder(com.winterframework.firefrog.game.entity.GameOrder gameOrder, boolean isGamePackage,
			GamePackageType packageType, Long planPackageId,List<com.winterframework.firefrog.game.dao.vo.GamePackageItem> itemList,boolean isNeedFreeze) throws Exception {

		log.info("saveGameOrder, isGamePackage=" + isGamePackage + ", packageType=" + packageType + ", planPackageId="
				+ planPackageId);

		Long userId = gameOrder.getGamePackage().getUser().getId();
		GamePackage gamePackage = gameOrder.getGamePackage();
		if (isGamePackage) {
			if (gamePackage.getPackageAmount() > getUserBal(userId)) {//判断余额是否足够
				throw new UserBalErrorException();
			}
		}
		com.winterframework.firefrog.game.dao.vo.GameOrder gameOrderVo = VOConverter.goe2gov(gameOrder);
		GameIssueEntity gameIssueEntity = gameIssueDao.queryGameIssue(gameOrderVo.getLotteryid(),
				gameOrderVo.getIssueCode());
		if (gameIssueEntity.getStatus().getValue() >= GameIssueEntity.STATUS_STOP_SALE && isGamePackage) {
			throw new GameIssueStatusStopSaleException();
		}
		//保存方案详情和方案
		Long packageId = null;
		if (isGamePackage) {
			packageId = savePackageAnditems(gameOrder, packageType);
			gameOrderVo.setParentid(packageId);
		} else {
			gameOrderVo.setParentid(planPackageId);
		}
		if(gameOrder.getGameIssue().getWebIssueCode()==null){
			gameOrder.getGameIssue().setWebIssueCode(gameIssueDao.queryGameIssue(gameOrder.getLottery().getLotteryId(),gameOrder.getGameIssue().getIssueCode()).getWebIssueCode());
		}
		gameOrderVo.setOrderCode(snUtil.createSN(SNUtil.TYPE_ORDER, gameOrder.getLottery().getLotteryId(), gameOrder
				.getGameIssue().getWebIssueCode()));
		GameSeriesConfigEntity gse = gameSeriesConfigDao.getGameSeriesConfigByLotteryId(gameOrderVo.getLotteryid());
		gameOrderVo.setEndCanCancelTime(this.getEndCanCancelTime(gameIssueEntity.getSaleEndTime(),
				gameOrderVo.getLotteryid(), gse));
		gameOrderVo.setAdminCanCancelTime(gse.getIssuewarnBackoutTime());
		//生成订单
		Long orderid = gameOrderDao.saveGameOrder(gameOrderVo);
		log.info("生成订单 息成功，orderid = " + orderid);

		//生成订单详情
		log.info("开始生成注单信息，orderid=" + orderid + ", gameOrderDetails.size = " + gameOrder.getSlipList().size());
		gameSlipDao.saveSlipList(gameOrder.getSlipList(), orderid,itemList);

		//ADD BY David 16.04.26 #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 : 計算單一注單返點 (BY USER) Start
		//補產生紀錄欠缺的資料 PACKAGE_ID、ITEM_ID、ORDER_CODE
		List<com.winterframework.firefrog.game.entity.GameSlip> slipList =  gameOrder.getSlipList();
		com.winterframework.firefrog.game.entity.GameOrder gameOrderTemp = null;
		com.winterframework.firefrog.game.entity.GamePackage gamePackageTemp = null;
		List<com.winterframework.firefrog.game.entity.GamePackageItem> gamePackageItemListTemp = new ArrayList<com.winterframework.firefrog.game.entity.GamePackageItem>();
		if(slipList.size() > 0){
			gameOrderTemp = slipList.get(0).getGameOrder();
			gamePackageTemp = slipList.get(0).getGameOrder().getGamePackage();
			
			gameOrderTemp.setOrderCode(gameOrderVo.getOrderCode()); //ORDER_CODE
			gamePackageTemp.setPackageId(gameOrderVo.getParentid()); //PACKAGE_ID
			for(com.winterframework.firefrog.game.dao.vo.GamePackageItem packageItem : itemList) {
				if(packageItem != null) {
					com.winterframework.firefrog.game.entity.GamePackageItem gamePackageItem = new com.winterframework.firefrog.game.entity.GamePackageItem();
					gamePackageItem.setId(packageItem.getId()); //PACKAGE_ID
					gamePackageItemListTemp.add(gamePackageItem);
				}
			}
			gamePackageTemp.setItemList(gamePackageItemListTemp);
			gameOrderTemp.setGamePackage(gamePackageTemp);
			
			for(int i=0;i<slipList.size();i++){
				slipList.get(i).setGameOrder(gameOrderTemp);
			}
		}		
		
				//记录用户代理层级链的返点信息
		String userChainAndRetPointChain = gameReturnPointDao.saveGameOrderUserReturnPoint(slipList,
				orderid);
		//String userChainAndRetPointChain = gameReturnPointDao.saveGameOrderUserReturnPoint(gameOrder.getSlipList(),
		//		orderid);
		//ADD BY David 16.04.26 #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 : 計算單一注單返點 (BY USER) End

		// 当产生订单时候,对投注资金进行冻结,同时对返点进行冻结,发送给审核中心调用fundRiskService
		String[] temp = userChainAndRetPointChain.split("/");//获取返点用户链 和返点金额链
		//返点

		if (isGamePackage || isNeedFreeze) {
			callBetAmountFreezer(gameOrderVo, temp);
		}
		return orderid;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public Long saveGameOrder(GameContext ctx,com.winterframework.firefrog.game.entity.GameOrder gameOrder, boolean isGamePackage,
			GamePackageType packageType, Long planPackageId,List<com.winterframework.firefrog.game.dao.vo.GamePackageItem> itemList,boolean isNeedFreeze) throws Exception {

		log.info("saveGameOrder, isGamePackage=" + isGamePackage + ", packageType=" + packageType + ", planPackageId="
				+ planPackageId);

		Long userId = gameOrder.getGamePackage().getUser().getId();
		GamePackage gamePackage = gameOrder.getGamePackage();
		if (isGamePackage) {
			if (gamePackage.getPackageAmount() > getUserBal(userId)) {//判断余额是否足够
				throw new UserBalErrorException();
			}
		}
		com.winterframework.firefrog.game.dao.vo.GameOrder gameOrderVo = VOConverter.goe2gov(gameOrder);
		GameIssueEntity gameIssueEntity = gameIssueDao.queryGameIssue(gameOrderVo.getLotteryid(),
				gameOrderVo.getIssueCode());
		if (gameIssueEntity.getStatus().getValue() >= GameIssueEntity.STATUS_STOP_SALE && isGamePackage) {
			throw new GameIssueStatusStopSaleException();
		}
		//保存方案详情和方案
		Long packageId = null;
		if (isGamePackage) {
			packageId = savePackageAnditems(gameOrder, packageType);
			gameOrderVo.setParentid(packageId);
		} else {
			gameOrderVo.setParentid(planPackageId);
		}
		if(gameOrder.getGameIssue().getWebIssueCode()==null){
			gameOrder.getGameIssue().setWebIssueCode(gameIssueDao.queryGameIssue(gameOrder.getLottery().getLotteryId(),gameOrder.getGameIssue().getIssueCode()).getWebIssueCode());
		}
		gameOrderVo.setOrderCode(snUtil.createSN(SNUtil.TYPE_ORDER, gameOrder.getLottery().getLotteryId(), gameOrder
				.getGameIssue().getWebIssueCode()));
		GameSeriesConfigEntity gse = gameSeriesConfigDao.getGameSeriesConfigByLotteryId(gameOrderVo.getLotteryid());
		gameOrderVo.setEndCanCancelTime(this.getEndCanCancelTime(gameIssueEntity.getSaleEndTime(),
				gameOrderVo.getLotteryid(), gse));
		gameOrderVo.setAdminCanCancelTime(gse.getIssuewarnBackoutTime());
		//生成订单
		Long orderid = gameOrderDao.saveGameOrder(gameOrderVo);
		log.info("生成订单 息成功，orderid = " + orderid);

		//生成订单详情
		log.info("开始生成注单信息，orderid=" + orderid + ", gameOrderDetails.size = " + gameOrder.getSlipList().size());
		gameSlipDao.saveSlipList(gameOrder.getSlipList(), orderid,itemList);
		
		//ADD BY David 16.04.26 #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 : 計算單一注單返點 (BY USER) Start
		//補產生紀錄欠缺的資料 PACKAGE_ID、ITEM_ID、ORDER_CODE
		List<com.winterframework.firefrog.game.entity.GameSlip> slipList =  gameOrder.getSlipList();
		com.winterframework.firefrog.game.entity.GameOrder gameOrderTemp = null;
		com.winterframework.firefrog.game.entity.GamePackage gamePackageTemp = null;
		List<com.winterframework.firefrog.game.entity.GamePackageItem> gamePackageItemListTemp = new ArrayList<com.winterframework.firefrog.game.entity.GamePackageItem>();
		if(slipList.size() > 0){
			gameOrderTemp = slipList.get(0).getGameOrder();
			gamePackageTemp = slipList.get(0).getGameOrder().getGamePackage();					
			gameOrderTemp.setOrderCode(gameOrderVo.getOrderCode()); //ORDER_CODE
			gamePackageTemp.setPackageId(gameOrderVo.getParentid()); //PACKAGE_ID
			for(com.winterframework.firefrog.game.dao.vo.GamePackageItem packageItem : itemList) {
				if(packageItem != null) {
					com.winterframework.firefrog.game.entity.GamePackageItem gamePackageItem = new com.winterframework.firefrog.game.entity.GamePackageItem();
					gamePackageItem.setId(packageItem.getId()); //PACKAGE_ID
					gamePackageItemListTemp.add(gamePackageItem);
				}
			}
			gamePackageTemp.setItemList(gamePackageItemListTemp);
			gameOrderTemp.setGamePackage(gamePackageTemp);
			for(int i=0;i<slipList.size();i++){
				slipList.get(i).setGameOrder(gameOrderTemp);
			}
		}
		
				//记录用户代理层级链的返点信息
		String userChainAndRetPointChain = gameReturnPointDao.saveGameOrderUserReturnPoint(slipList,
				orderid);
		//String userChainAndRetPointChain = gameReturnPointDao.saveGameOrderUserReturnPoint(gameOrder.getSlipList(),
		//		orderid);
		//ADD BY David 16.04.26 #7332 4.0前台代理报表查询 新增查询功能 玩法 分類 : 計算單一注單返點 (BY USER) End


		// 当产生订单时候,对投注资金进行冻结,同时对返点进行冻结,发送给审核中心调用fundRiskService
		String[] temp = userChainAndRetPointChain.split("/");//获取返点用户链 和返点金额链
		//返点

		if (isGamePackage || isNeedFreeze) {
			callBetAmountFreezer(ctx,gameOrderVo, temp);
		}
		return orderid;
	}

	private Date getEndCanCancelTime(Date saleEndTime, Long lotteryId, GameSeriesConfigEntity gse) throws Exception {

		Long cancelTime = gse.getIssuewarnUserBackoutTime();//单位为秒
		if (cancelTime != null) {
			return DateUtils.addSeconds(saleEndTime, -cancelTime.intValue());
		} else {
			return saleEndTime;
		}
	}

	private void callBetAmountFreezer(com.winterframework.firefrog.game.dao.vo.GameOrder gameOrderVo, String[] temp)
			throws Exception {
		List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();
		//对于订单投注 生成投注冻结
		//投注DTO生成
		TORiskDTO freezerOrderDTO = new TORiskDTO();
		freezerOrderDTO.setAmount(gameOrderVo.getTotamount() + "");
		freezerOrderDTO.setIssueCode(gameOrderVo.getIssueCode());
		freezerOrderDTO.setLotteryid(gameOrderVo.getLotteryid());
		freezerOrderDTO.setOrderCodeList(gameOrderVo.getOrderCode()); 
		GamePlan gamePlan = gamePlanService.getGamePlanById(gameOrderVo.getPlanId());
		if (gamePlan != null) {
			freezerOrderDTO.setPlanCodeList(gamePlan.getPlanCode());
		} 
		freezerOrderDTO.setType(GameFundTypesUtils.GAME_BET_FREEZER_DETEAIL_TYPE);
		freezerOrderDTO.setUserid(gameOrderVo.getUserid() + "");
		toRiskDTOList.add(freezerOrderDTO);

		//返点DTO生成
		TORiskDTO retPointDTO = new TORiskDTO();
		retPointDTO.setAmount(temp[1]);
		retPointDTO.setIssueCode(gameOrderVo.getIssueCode());
		retPointDTO.setLotteryid(gameOrderVo.getLotteryid());
		retPointDTO.setOrderCodeList(gameOrderVo.getOrderCode()); 
		if (gamePlan != null) {
			retPointDTO.setPlanCodeList(gamePlan.getPlanCode());
		} 
		retPointDTO.setType(GameFundTypesUtils.GAME_RET_FREEZER_DETEAIL_TYPE);
		retPointDTO.setUserid(temp[0]);

		toRiskDTOList.add(retPointDTO);

		log.info("开始生成注单信息，开始发送风控系统， 用户连=" + temp + ",type1 = " + GameFundTypesUtils.GAME_BET_FREEZER_DETEAIL_TYPE
				+ ", type2 =" + GameFundTypesUtils.GAME_RET_FREEZER_DETEAIL_TYPE);
		//调用风控资金冻结接口
		fundRiskService.betAmountFreezer(toRiskDTOList);
	}
	private void callBetAmountFreezer(GameContext ctx,com.winterframework.firefrog.game.dao.vo.GameOrder gameOrderVo, String[] temp)
			throws Exception {
		List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();
		//对于订单投注 生成投注冻结
		//投注DTO生成
		TORiskDTO freezerOrderDTO = new TORiskDTO();
		freezerOrderDTO.setAmount(gameOrderVo.getTotamount() + "");
		freezerOrderDTO.setIssueCode(gameOrderVo.getIssueCode());
		freezerOrderDTO.setLotteryid(gameOrderVo.getLotteryid());
		freezerOrderDTO.setOrderCodeList(gameOrderVo.getOrderCode()); 
		GamePlan gamePlan = gamePlanService.getGamePlanById(gameOrderVo.getPlanId());
		if (gamePlan != null) {
			freezerOrderDTO.setPlanCodeList(gamePlan.getPlanCode());
		} 
		freezerOrderDTO.setType(GameFundTypesUtils.GAME_BET_FREEZER_DETEAIL_TYPE);
		freezerOrderDTO.setUserid(gameOrderVo.getUserid() + "");
		toRiskDTOList.add(freezerOrderDTO);

		//返点DTO生成
		TORiskDTO retPointDTO = new TORiskDTO();
		retPointDTO.setAmount(temp[1]);
		retPointDTO.setIssueCode(gameOrderVo.getIssueCode());
		retPointDTO.setLotteryid(gameOrderVo.getLotteryid());
		retPointDTO.setOrderCodeList(gameOrderVo.getOrderCode()); 
		if (gamePlan != null) {
			retPointDTO.setPlanCodeList(gamePlan.getPlanCode());
		} 
		retPointDTO.setType(GameFundTypesUtils.GAME_RET_FREEZER_DETEAIL_TYPE);
		retPointDTO.setUserid(temp[0]);

		toRiskDTOList.add(retPointDTO);

		log.info("开始生成注单信息，开始发送风控系统， 用户连=" + temp + ",type1 = " + GameFundTypesUtils.GAME_BET_FREEZER_DETEAIL_TYPE
				+ ", type2 =" + GameFundTypesUtils.GAME_RET_FREEZER_DETEAIL_TYPE);
		//调用风控资金冻结接口
		this.addRiskDto(ctx, toRiskDTOList);
	}
	protected void addRiskDto(GameContext ctx, TORiskDTO riskDto) {
		if(riskDto==null) return; 
		String orderCode=riskDto.getOrderCodeList();
		if(orderCode!=null){	 
			Map<String,List<TORiskDTO>> riskDtoMap=(HashMap<String,List<TORiskDTO>>)ctx.get("RISKDTOLIST");
			if(riskDtoMap==null){
				riskDtoMap=new HashMap<String,List<TORiskDTO>>(); 
			}
			List<TORiskDTO> riskDtoList=riskDtoMap.get(orderCode);
			if(riskDtoList==null){
				riskDtoList=new ArrayList<TORiskDTO>(); 
			}
			if(GameFundTypesUtils.GAME_SYS_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE==riskDto.getType().intValue()){ 
				riskDto.setOrderCodeList(null);
			}
			riskDtoList.add(riskDto);
			riskDtoMap.put(orderCode, riskDtoList);
			ctx.set("RISKDTOLIST", riskDtoMap);
		}
	}
	protected void addRiskDto(GameContext ctx, List<TORiskDTO> riskDtoList) {
		if(riskDtoList!=null && riskDtoList.size()>0){
			for(TORiskDTO riskDto:riskDtoList){
				this.addRiskDto(ctx, riskDto);
			}
		} 
	}
	
	protected void addRiskDto_bk(GameContext ctx, List<TORiskDTO> riskDtoList) {
		if(riskDtoList==null || riskDtoList.size()==0) return;
		List<TORiskDTO> dtoList=(List<TORiskDTO>)ctx.get("RISKDTOLIST");
		if(dtoList==null){
			dtoList=new ArrayList<TORiskDTO>(); 
		}
		dtoList.addAll(riskDtoList);
		ctx.set("RISKDTOLIST", dtoList);
	}
	@Override
	public List<GameOrder> getNextIssueGameOrder(Long orderId) {
		return gameOrderDao.getNextIssueGameOrder(orderId);
	}

	@Override
	public List<GameOrder> getGamePlanOrderListByLotteryAndIssueCode(Long lotteryId, Long issueCode) {
		return gameOrderDao.getGamePlanOrderListByLotteryAndIssueCode(lotteryId, issueCode);
	}

	@Override
	public List<GameOrder> getGameOrderByIssueAndLottery(Long lotteryId, Long issueCode) {
		return gameOrderDao.getGameOrderByIssueAndLottery(lotteryId, issueCode);
	}

	/** 
	* @Title: noticeWinOrder 
	* @Description: 中奖订单通知
	*/
	public void noticeWinOrder() {
		try {
			List<GameOrderWin> wins = gameOrderWinDao.selectUnNoticeSendedMondeyWinOrder();
			if (wins != null) {
				log.warn("-----本次查询一共 " + wins.size() + "条中奖派送通知没发--------");

				for (GameOrderWin gameOrderWin : wins) {
					NoticeSenderRequest request = new NoticeSenderRequest();
					request.setTaskId(Long.parseLong(taskId));
					request.setUserId(gameOrderWin.getUserid());
					try {
						User user = customerDao.queryUserById(gameOrderWin.getUserid());
						request.setAccount(user.getUserProfile().getAccount());
					} catch (Exception e) {
						log.error("通知查询用户名失败: userId=" + gameOrderWin.getUserid(), e);
					}
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("lotteryName", "");
					paramMap.put("rewardTerm", "");
					paramMap.put("imagePath", imageUrl);
					paramMap.put("lotteryId", ""+gameOrderWin.getLotteryid());
					try {
						GameSeries game = gameSeriesDaoImpl.getByLotteyId(gameOrderWin.getLotteryid().longValue());
						paramMap.put("lotteryName", game.getLotteryName());
					} catch (Exception e) {
						log.error("通知查询彩种失败: Lotteryid()=" + gameOrderWin.getLotteryid().longValue(), e);
					}
					if(gameOrderWin.getLotteryid().intValue() == 99112|| gameOrderWin.getLotteryid().intValue() == 99306|| gameOrderWin.getLotteryid().intValue() == 99113){
						
						paramMap.put("rewardTerm", "方案"+gameOrderDao.getById(gameOrderWin.getOrderid()).getOrderCode());
					}else{
						try {
							GameIssueEntity issue = gameIssueDao.queryGameIssue(gameOrderWin.getLotteryid().longValue(),
									gameOrderWin.getIssueCode());
							paramMap.put("rewardTerm", "第"+issue.getWebIssueCode()+"期");
						} catch (Exception e) {
							log.error("通知查询奖期失败: Lotteryid()=" + gameOrderWin.getLotteryid().longValue() + " IssueCode()="
									+ gameOrderWin.getIssueCode(), e);
						}
					}
					
					DecimalFormat df = new DecimalFormat(".00");					
					paramMap.put("lotteryMoney", df.format((gameOrderWin.getCountWin()+gameOrderWin.getDiamondCountWin()-50) / 10000.00).toString()+"元" );
					//String url = frontUrl + noticeDetail + gameOrderWin.getOrderid();
					String url = noticeDetail + gameOrderWin.getOrderid();
					//paramMap.put("detailUrl", "<a herf = '"+url+"'> "+url+"</a> " );
					paramMap.put("detailFrontUrl", frontUrl );
					paramMap.put("detailUrl", url );
					request.setParamMap(paramMap);
					httpClient.invokeHttp(baseFundUrl + "/noticeAdmin/SendNotice", request, 31L, "admin", Object.class);
					gameOrderWin.setIsNotice(1L);
					gameOrderWinDao.update(gameOrderWin);
				}
			}
		} catch (Exception e) {
			log.error("-----中奖派送通知失败-------", e);
		}

	}

	@Override
	public List<GameOrder> getOrderFollowedByPlanIdAndIssueCode(Long planId, Long issueCode) {
		return this.gameOrderDao.getOrderFollowedByPlanIdAndIssueCode(planId, issueCode);
	}

	@Override
	public List<GameOrder> getNotPlanByLotteryAndIssue(Long lotteryId, Long issueCode) {
		return this.gameOrderDao.getNotPlanByLotteryAndIssue(lotteryId, issueCode);
	}
	@Override
	public List<GameOrder> getByPackageId(Long packageId) throws Exception {
		return this.gameOrderDao.getByPackageId(packageId);
	}

	@Override
	public GameOrder getOrderByPlanDetailId(Long planDetailId) {
		return this.gameOrderDao.getOrderByPlanDetailId(planDetailId);
	} 
	public List<GameOrder> getGameOrderByLotteryIdAndTime(Long lotteryId,
			Date startTime, Date endTime) throws Exception {
		return this.gameOrderDao.getGameOrderByLotteryIdAndTime(lotteryId,startTime,endTime);
	}

	@Override
	public List<GameOrder> getGameOrderWaiting(Long planId) throws Exception {
		return this.gameOrderDao.getGameOrderWaiting(planId);
	}
	@Override
	public int reset(GameContext ctx, GameOrder order) throws Exception {
		/**
		 * 中奖、未中奖、撤销、异常、归档->等待开奖
		 * 1.写订单操作日志
		 * 2.调用订单撤销行为
		 * 3.调用注单重置行为
		 * 4.调用返点重置行为
		 * 5.重置订单（状态:等待开奖）
		 */
		if(order==null) return 0; 
		 
		int status=order.getStatus();
		if(status==GameOrder.Status.WAIT.getValue()) return 0;
		//写订单操作日志
		this.addOrderLog(ctx,order,"reset","订单重置");
		
		//调用订单撤销行为
		this.cancel(ctx, order);
		
		this.gameSlipService.resetByOrder(ctx, order);
		
		this.gameReturnPointService.resetByOrder(ctx, order);
		
		log.info("重置订单（状态:等待开奖）");
		order.setStatus(Integer.valueOf(GameOrder.Status.WAIT.getValue()));
		order.setCancelModes(0);
		order.setCancelTime(null); 
		order.setCancelFee(0L);	 
		this.save(ctx,order);  
		
		return 1;
	}
	@Override
	public int reset_tmp(GameContext ctx, GameOrder order) throws Exception {
		if(order==null) return 0; 
		 
		int status=order.getStatus();
		if(status==GameOrder.Status.WAIT.getValue()) return 0;
		//写订单操作日志
		this.addOrderLog(ctx,order,"reset","订单重置");
		
		//重置时要冻结金额
		this.callBetAmountFreezer(ctx,order,new String[]{"-1","0"}); 	//返点冻结,暂时没有此需求
		
		//调用订单撤销行为
		//this.cancel(ctx, order);
		
		//this.gameSlipService.resetByOrder(ctx, order);
		List<GameSlip> slipList=this.gameSlipService.getByOrderId(ctx, order.getId());
		if(slipList!=null && slipList.size()>0){
			for(GameSlip slip:slipList){
				slip.setStatus(GameSlip.Status.WAIT.getValue());
				slip.setEvaluateWin(0L);
				slip.setWinNumber(0L);
				slip.setWinLevel(0L);
				this.gameSlipService.save(ctx, slip);
			}
		}
		
		//this.gameReturnPointService.resetByOrder(ctx, order);
		GameRetPoint retPoint=this.gameReturnPointService.getByOrderId(ctx, order.getId()); 
		retPoint.setStatus(GameRetPoint.Status.FREEZE.getValue());
		this.gameReturnPointService.save(ctx, retPoint);
		
		log.info("重置订单（状态:等待开奖）");
		order.setStatus(Integer.valueOf(GameOrder.Status.WAIT.getValue()));
		order.setCancelModes(0);
		order.setCancelTime(null); 
		order.setCancelFee(0L);	 
		this.save(ctx,order);  
		
		return 1;
	}

	@Override
	public void cancelNotPlan(Long lotteryId, Long issueCode) throws Exception {
		/**
		 * 调用撤销体系服务
		 */
		/*List<GameOrder> orderListNotPlan = this.getOrderNotPlanByLotteryAndIssue(lotteryId, issueCode);
		if(null!=orderListNotPlan){
			for(GameOrder order : orderListNotPlan) {
				//调用订单撤销行为接口
				//不是追号，订单冻结的钱还回去
				dtos.add(gameOrderFundServcie.packageOrderUnFreezeTORiskDTO(order));
				//一单撤销
				List<TORiskDTO> toRiskDTOList1 = gameRevocationOrderStatusMachineImpl.doRevocation(result,order);
				if (toRiskDTOList1 != null) {
					toRiskDTOList.addAll(toRiskDTOList1);
				}
			}
		}*/

	}
	@Override
	public List<GameOrder> getByLotteryUserIdTime(Long lotteryId, Long userId,
			Date startTime, Date endTime) throws Exception {
		return  this.gameOrderDao.getByLotteryUserIdTime(lotteryId,userId,startTime,endTime); 
	} 
	
	@Override
	public List<GameOrder> getByLotteryIdAndIssueCode(GameContext ctx,
			Long lotteryId, Long issueCode) throws Exception {
		return this.gameOrderDao.queryOrderByLotteryIdAndIssueCode(lotteryId, issueCode); 
	}
	@Override
	public List<GameOrder> getPlanByLotteryIdAndIssueCode(GameContext ctx,
			Long lotteryId, Long issueCode) throws Exception {
		return this.getByLotteryIdAndIssueCode(ctx, lotteryId, issueCode); 
	}
	public GameOrder getOnePlanByLotteryIdAndIssueCode(GameContext ctx,
			Long lotteryId, Long issueCode) throws Exception {
		List<GameOrder> orderList=this.getPlanByLotteryIdAndIssueCode(ctx, lotteryId, issueCode);
		if(orderList!=null && orderList.size()>0){
			return orderList.get(0);
		}
		return null;
	}
	
	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat(".00");					
		
		System.out.println(df.format(42300L / 10000.00).toString());
	}
	@Override
	public List<GameOrder> getOrderListByGameIssue(Long issueCode) {
		return gameOrderDao.getOrderListByGameIssue(issueCode);
	}
	@Override
	public int getUnfundedCountByLotteryIdAndIssueCode(GameContext ctx,
			Long lotteryId, Long issueCode) throws Exception {
		return gameOrderDao.getCountByLotteryIdAndIssueCodeAndFundStatus(lotteryId,issueCode,GameOrder.FundStatus.NEED);
	}
	@Override
	public List<GameOrder> getUnfundedByLotteryIdAndIssueCodeAndBatchSize(GameContext ctx,
			Long lotteryId, Long issueCode, int batchSize) throws Exception {
		return gameOrderDao.getByLotteryIdAndIssueCodeAndFundStatusAndBatchSize(lotteryId,issueCode,GameOrder.FundStatus.NEED,batchSize);
	}
	@Override
	public List<GameOrder> getByLotteryIdAndIssueCodeAndFundStatusAndIndexes(
			GameContext ctx, Long lotteryId, Long issueCode, int beginIndex,
			int endIndex) throws Exception {
		return gameOrderDao.getByLotteryIdAndIssueCodeAndFundStatusAndIndexes(lotteryId,issueCode,GameOrder.FundStatus.NEED,beginIndex,endIndex);
	}
	@Override
	public int updateFundStatus(GameContext ctx, FundStatus fundStatus,
			List<String> orderCodeList) throws Exception {
		if(orderCodeList==null || orderCodeList.size()==0) return 0;
		return gameOrderDao.updateFundStatus(fundStatus,orderCodeList);
	}
}