package com.winterframework.firefrog.game.web.dto;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.vo.EnumTypeConverts;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GamePackageItem;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.GameUserAward;
import com.winterframework.firefrog.game.dao.vo.WinsSumReport;
import com.winterframework.firefrog.game.entity.BetLimit;
import com.winterframework.firefrog.game.entity.BetLimitAssist;
import com.winterframework.firefrog.game.entity.BetMethodDescription;
import com.winterframework.firefrog.game.entity.CancelMode;
import com.winterframework.firefrog.game.entity.Channel;
import com.winterframework.firefrog.game.entity.FileMode;
import com.winterframework.firefrog.game.entity.GameAwardEntity;
import com.winterframework.firefrog.game.entity.GameAwardGroupEntity;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameIssueRuleEntity;
import com.winterframework.firefrog.game.entity.GameIssueRuleEntity.RuleType;
import com.winterframework.firefrog.game.entity.GameIssueTemplateEntity;
import com.winterframework.firefrog.game.entity.GameMultipleEntity;
import com.winterframework.firefrog.game.entity.GameOrder;
import com.winterframework.firefrog.game.entity.GameOrder.OrderStatus;
import com.winterframework.firefrog.game.entity.GameOrderOperationsEntity;
import com.winterframework.firefrog.game.entity.GamePackage;
import com.winterframework.firefrog.game.entity.GamePackage.GamePackageType;
import com.winterframework.firefrog.game.entity.GamePlan;
import com.winterframework.firefrog.game.entity.GamePlan.GamePlanStatus;
import com.winterframework.firefrog.game.entity.ChannelType;
import com.winterframework.firefrog.game.entity.GamePlanDetail;
import com.winterframework.firefrog.game.entity.GamePlanOperationsEntity;
import com.winterframework.firefrog.game.entity.GameRiskConfig;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.firefrog.game.entity.GameSlipOperationsEntity;
import com.winterframework.firefrog.game.entity.GameSpiteOperationsEntity;
import com.winterframework.firefrog.game.entity.LockPoint;
import com.winterframework.firefrog.game.entity.Lottery;
import com.winterframework.firefrog.game.entity.Points;
import com.winterframework.firefrog.game.entity.SellingStatus;
import com.winterframework.firefrog.game.entity.SingleIssueIncomeAndExpenseEntity;
import com.winterframework.firefrog.game.entity.UserAwardGroupEntity;
import com.winterframework.firefrog.game.entity.UserBetAwardGroupEntity;
import com.winterframework.firefrog.game.entity.UserCenterReportInfo;
import com.winterframework.firefrog.game.entity.VOConvert;
import com.winterframework.firefrog.game.entity.WinsReport;
import com.winterframework.firefrog.game.web.util.K3GameLimitUtil;
import com.winterframework.firefrog.game.web.util.SSCBetNameUtil;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.web.jsonresult.Request;

public class DTOConvert {
	public static GameOrder convertGamePlanRequest2GameOrder(GamePlanRequest gamePlanRequest, Long userId)
			throws Exception {

		GameOrder order = new GameOrder();
		GamePlan gamePlan = new GamePlan();
		order.setGamePackage(gamePlan);

		// 初始化用户以及彩种信息
		User user = new User();
		user.setId(userId);
		gamePlan.setUser(user);

		Lottery lottery = new Lottery();
		lottery.setLotteryId(gamePlanRequest.getLotteryid());
		order.setLottery(lottery);
		gamePlan.setLottery(lottery);

		// 初始化奖期信息
		GameIssueEntity gameIssue = new GameIssueEntity();
		gameIssue.setIssueCode(gamePlanRequest.getStartIssueCode());
		gamePlan.setGameIssue(gameIssue);
		order.setGameIssue(gameIssue);

		// 初始化投注信息
		gamePlan.setItemList(convertBetDetailsStruc(gamePlanRequest.getBetDetailsStruc(), gamePlan));

		gamePlan.setServerip(gamePlanRequest.getServerip());
		gamePlan.setUserIp(gamePlanRequest.getUserip());
		gamePlan.setTotalIssue(gamePlanRequest.getTotalIssue().intValue());
		gamePlan.setStopParms(gamePlanRequest.getStopParms());
		gamePlan.setStopMode(EnumTypeConverts.getStopModeByValue(gamePlanRequest.getStopMode()));
		gamePlan.setOptionParms(gamePlanRequest.getOptionParms());
		gamePlan.setType(GamePackageType.PLAN);
		gamePlan.setStatus(GamePlanStatus.WAITING);

		boolean isFileMode = false;
		List<com.winterframework.firefrog.game.entity.GamePackageItem> itemList = gamePlan.getItemList();
		if (itemList != null && itemList.size() > 0) {
			for (com.winterframework.firefrog.game.entity.GamePackageItem item : itemList) {
				if (item.getFileMode().getValue() == FileMode.FILE.getValue()) {
					isFileMode = true;
					break;
				}
			}
		}
		gamePlan.setFileMode(isFileMode ? FileMode.FILE : FileMode.NUFILE);

		// soleAmount为当期金额
		gamePlan.setSoldAmount(packageAmount(gamePlanRequest.getStartIssueCode(), gamePlanRequest.getBetDetailsStruc())
				.longValue());

		// packageAmount在追号时为整个追号的金额
		gamePlan.setPackageAmount(gamePlanRequest.getPlanAmount());

		order.setSaleTime(DataConverterUtil.convertLong2Date(gamePlanRequest.getSaleTime()));
		order.setStatus(OrderStatus.WAITING);
		order.setCancelModes(CancelMode.DEFAULTS);
		// 订单的packageAmount 应该是某一期的所有getBetDetailsStruc之和
		order.setTotalAmount(gamePlan.getSoldAmount());
		order.setSlipList(convertGamePlanRequest2GameOrderDetail(gamePlanRequest, userId, order));
		order.setFileMode(gamePlan.getFileMode());

		Channel channe = new Channel();
		channe.setChannelType(ChannelType.getChannelType(new Long(gamePlanRequest.getChannelId())));
		channe.setVersion(gamePlanRequest.getChannelVersion());
		gamePlan.setChannel(channe);
		gamePlan.setActivityType(gamePlanRequest.getActivityType());
		return order;
	}

	public static List<com.winterframework.firefrog.game.entity.GameSlip> convertGamePlanRequest2GameOrderDetail(
			GamePlanRequest gamePlanRequest, Long userId, GameOrder order) throws Exception {

		List<com.winterframework.firefrog.game.entity.GameSlip> list = new ArrayList<com.winterframework.firefrog.game.entity.GameSlip>();
		for (BetDetailStruc detailStruc : gamePlanRequest.getBetDetailsStruc()) {
			com.winterframework.firefrog.game.entity.GameSlip detail = new com.winterframework.firefrog.game.entity.GameSlip();
			// 设置奖期
			GameIssueEntity issue = new GameIssueEntity();
			issue.setIssueCode(detailStruc.getIssueCode());
			detail.setIssueCode(issue);
			detail.setFileMode(detailStruc.getFileMode());
			// 初始化圆角模式
			detail.setMoneyMode(EnumTypeConverts.convertMoneyMode(detailStruc.getMoneyMode()));
			detail.setBetDetail(detailStruc.getBetdetail());
			// 初始化玩法信息
			GameBetType betType = new GameBetType();
			betType.setGameGroupCode(detailStruc.getGameGroupCode());
			betType.setGameSetCode(detailStruc.getGameSetCode());
			betType.setBetMethodCode(detailStruc.getBetMethodCode());
			detail.setGameBetType(betType);

			detail.setTotalBet(Long.valueOf(detailStruc.getTotbets()));
			detail.setTotalAmount(Long.valueOf(detailStruc.getTotamount()));
			detail.setMultiple(detailStruc.getMultiple());

			// 初始化注单状态
			detail.setGameSlipStatus(EnumTypeConverts.convertGameSlipStatus(1));
			detail.setCrateTime(new Date());
			detail.setAwardMode(detailStruc.getAwardMode());
			// 初始化变价信息
			if (detailStruc.getPointsList() != null && !detailStruc.getPointsList().isEmpty()) {
				LockPoint lockPoint = new LockPoint();
				List<Points> points = new ArrayList<Points>();
				for (PointsRequestDTO pointDTO : detailStruc.getPointsList()) {
					Points point = new Points();
					point.setMult(pointDTO.getMult());
					point.setSingleBet(pointDTO.getSingleBet());
					point.setPoint(pointDTO.getPoint());
					point.setRetValue(pointDTO.getRetValue());
					points.add(point);
				}
				lockPoint.setPoints(points);
				detail.setLockPoints(lockPoint);
			} else if (gamePlanRequest.getIsFirstSubmit() != null && gamePlanRequest.getIsFirstSubmit().intValue() == 1) {
				LockPoint lockPoint = new LockPoint();
				detail.setLockPoints(lockPoint);
			}
			detail.setGameOrder(order);
			list.add(detail);
		}
		return list;
	}

	/**
	 * 
	 * @Title: packageAmount
	 * @Description: 获取方案金额
	 * @param betDetailStrucs
	 * @return
	 * @throws Exception
	 */
	public static BigDecimal packageAmount(Long issueCode, List<BetDetailStruc> betDetailStrucs) throws Exception {
		Long count = 0L;
		for (BetDetailStruc betDetailStruc : betDetailStrucs) {
			if (issueCode.longValue() == betDetailStruc.getIssueCode().longValue()) {
				count += (betDetailStruc.getItemAmount().longValue() * betDetailStruc.getMultiple().longValue() * betDetailStruc
						.getTotbets().longValue());
			}
		}
		return new BigDecimal(count);
	}

	/**
	 * @Title: convertBetDetailsStruc
	 * @Description: 将结构体转换为方案细节信息
	 * @param betDetailStrucs
	 * @return
	 * @throws Exception
	 */
	public static List<com.winterframework.firefrog.game.entity.GamePackageItem> convertBetDetailsStruc(
			List<BetDetailStruc> betDetailStrucs, GamePackage gamePackage) throws Exception {

		List<com.winterframework.firefrog.game.entity.GamePackageItem> betDetails = new ArrayList<com.winterframework.firefrog.game.entity.GamePackageItem>();

		for (BetDetailStruc detailStruc : betDetailStrucs) {
			com.winterframework.firefrog.game.entity.GamePackageItem item = new com.winterframework.firefrog.game.entity.GamePackageItem();
			item.setBetDetail(detailStruc.getBetdetail());
			item.setItemAmount(Long.valueOf(detailStruc.getItemAmount()));
			item.setMoneyMode(EnumTypeConverts.convertMoneyMode(detailStruc.getMoneyMode()));
			item.setMultiple(detailStruc.getMultiple());
			item.setTotamount(detailStruc.getTotamount());
			item.setTotbets(Long.valueOf(detailStruc.getTotbets()));
			item.setFileMode(EnumTypeConverts.converFileMode(detailStruc.getFileMode()));
			item.setGameBetType(new GameBetType(detailStruc.getGameGroupCode(), detailStruc.getGameSetCode(),
					detailStruc.getBetMethodCode()));
			item.setCreateTime(new Date());
			item.setDiamondAmount(detailStruc.getDiamondAmount());
			//六合彩賠率
			if(gamePackage.getLottery().getLotteryId() == 99701){
				item.setOdds(detailStruc.getOdds());
			}
			// 将细节与package相关联
			item.setGamePackage(gamePackage);
			item.setAwardMode(detailStruc.getAwardMode());
			betDetails.add(item);
		}
		return betDetails;
	}

	/**
	 * @Title: convertGameOrderRequest2GameOrder
	 * @Description: 将request对象转换为gameOrder对象
	 * @param gameOrderRequest
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static GameOrder convertGameOrderRequest2GameOrder(GameOrderRequest gameOrderRequest, Long userId)
			throws Exception {

		GameOrder order = new GameOrder();
		GamePackage gamePackage = new GamePackage();
		order.setGamePackage(gamePackage);

		// 设置用户
		User user = new User();
		user.setId(userId);
		gamePackage.setUser(user);

		// 设置彩种
		Lottery lottery = new Lottery();
		lottery.setLotteryId(gameOrderRequest.getLotteryid());
		order.setLottery(lottery);
		order.setAwardGroupId(gameOrderRequest.getAwardGroupId());
		gamePackage.setLottery(lottery);
		gamePackage.setAwardId(gameOrderRequest.getAwardGroupId());

		// 设置奖期信息
		GameIssueEntity gameIssueEntity = new GameIssueEntity();
		gameIssueEntity.setIssueCode(gameOrderRequest.getIssueCode());
		order.setGameIssue(gameIssueEntity);
		gamePackage.setGameIssue(gameIssueEntity);

		gamePackage.setServerip(gameOrderRequest.getServerIp());
		gamePackage.setUserip(gameOrderRequest.getUserIp());
		gamePackage.setPackageAmount(gameOrderRequest.getPackageAmount());
		gamePackage.setSaleTime(DataConverterUtil.convertLong2Date(gameOrderRequest.getSaleTime()));
		gamePackage.setType(GamePackageType.PACKAGES);
		gamePackage.setActivityType(gameOrderRequest.getActivityType());
		// 设置packageItem信息
		gamePackage.setItemList(convertBetDetailsStruc(gameOrderRequest.getBetDetailStruc(), gamePackage));

		boolean isFileMode = false;
		List<com.winterframework.firefrog.game.entity.GamePackageItem> itemList = gamePackage.getItemList();
		if (itemList != null && itemList.size() > 0) {
			for (com.winterframework.firefrog.game.entity.GamePackageItem item : itemList) {
				if (item.getFileMode().getValue() == FileMode.FILE.getValue()) {
					isFileMode = true;
					break;
				}
			}
		}
		gamePackage.setFileMode(isFileMode ? FileMode.FILE : FileMode.NUFILE);

		// 初始化购买该订单的总金额
		order.setTotalAmount(gameOrderRequest.getPackageAmount());
		order.setSaleTime(DataConverterUtil.convertLong2Date(gameOrderRequest.getSaleTime()));
		order.setStatus(OrderStatus.WAITING);
		order.setCancelModes(CancelMode.DEFAULTS);
		order.setFileMode(gamePackage.getFileMode());

		// 设置slip信息
		order.setSlipList(convertGameOrderRequest2GameOrderDetail(order, gameOrderRequest, userId));

		// 设置渠道信息，此处先使用固定值
		Channel channe = new Channel();
		channe.setChannelType(ChannelType.getChannelType(new Long(gameOrderRequest.getChannelId())));
		channe.setVersion(gameOrderRequest.getChannelVersion());
		gamePackage.setChannel(channe);

		return order;
	}

	/**
	 * @Title: convertGameOrderRequest2GameOrderDetail
	 * @Description: 将request转换为gameSlip对象
	 * @param order
	 * @param gameOrderRequest
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static List<com.winterframework.firefrog.game.entity.GameSlip> convertGameOrderRequest2GameOrderDetail(
			GameOrder order, GameOrderRequest gameOrderRequest, Long userId) throws Exception {
		List<com.winterframework.firefrog.game.entity.GameSlip> list = new ArrayList<com.winterframework.firefrog.game.entity.GameSlip>();
		for (BetDetailStruc detailStruc : gameOrderRequest.getBetDetailStruc()) {
			com.winterframework.firefrog.game.entity.GameSlip detail = new com.winterframework.firefrog.game.entity.GameSlip();

			// 设置奖期
			GameIssueEntity issue = new GameIssueEntity(detailStruc.getIssueCode());
			detail.setIssueCode(issue);

			detail.setFileMode(detailStruc.getFileMode());//设置文件模式

			// 初始化圆角模式
			detail.setMoneyMode(EnumTypeConverts.convertMoneyMode(detailStruc.getMoneyMode()));
			detail.setBetDetail(detailStruc.getBetdetail());

			// 初始化玩法信息
			GameBetType betType = new GameBetType(detailStruc.getGameGroupCode(), detailStruc.getGameSetCode(),
					detailStruc.getBetMethodCode());
			detail.setGameBetType(betType);

			detail.setTotalBet(Long.valueOf(detailStruc.getTotbets()));
			detail.setTotalAmount(Long.valueOf(detailStruc.getTotamount()));
			detail.setMultiple(detailStruc.getMultiple());
			detail.setDiamondAmount(detailStruc.getDiamondAmount());
			// 初始化注单状态
			detail.setGameSlipStatus(EnumTypeConverts.convertGameSlipStatus(1));
			detail.setCrateTime(new Date());
			detail.setAwardMode(detailStruc.getAwardMode());
			
			//六合彩賠率
			if(gameOrderRequest.getLotteryid() == 99701){
				detail.setSingleWin( new Double(detailStruc.getOdds() * 10000).longValue() );
			}
			// 初始化变价信息
			if (detailStruc.getPointsList() != null && !detailStruc.getPointsList().isEmpty()) {
				LockPoint lockPoint = new LockPoint();
				List<Points> points = new ArrayList<Points>();
				for (PointsRequestDTO pointDTO : detailStruc.getPointsList()) {
					Points point = new Points();
					point.setMult(pointDTO.getMult());
					point.setSingleBet(pointDTO.getSingleBet());
					point.setPoint(pointDTO.getPoint());
					point.setRetValue(pointDTO.getRetValue());
					point.setCurrPhase(pointDTO.getCurrentPhase());
					points.add(point);
				}
				lockPoint.setPoints(points);
				detail.setLockPoints(lockPoint);
			} else if (gameOrderRequest.getIsFirstSubmit() != null
					&& gameOrderRequest.getIsFirstSubmit().intValue() == 1) {
				LockPoint lockPoint = new LockPoint();
				detail.setLockPoints(lockPoint);
			}
			// 将订单与slip相关联的
			detail.setGameOrder(order);

			list.add(detail);
		}

		return list;
	}

	public static OrdersStruc gameOrder2OrdersStruc(GameOrder go) {
		OrdersStruc os = new OrdersStruc();
		os.setOrderCode(go.getOrderCode());
		os.setLotteryid(go.getLottery().getLotteryId());
		GameIssueEntity gameIssueEntity = go.getGameIssue();
		if (null != gameIssueEntity) {

			os.setIssueCode(gameIssueEntity.getIssueCode());
			if(go.getLottery().getLotteryId() == 99701 && gameIssueEntity.getWebIssueCode()!=null){
				StringBuilder sb = new StringBuilder(gameIssueEntity.getWebIssueCode());
				sb.insert(2, "/");
				os.setWebIssueCode(sb.toString());
			}else {
				os.setWebIssueCode(gameIssueEntity.getWebIssueCode());
			}
			os.setEndSaleTime(gameIssueEntity.getSaleEndTime());
		}
		os.setTotamount(go.getTotalAmount());
		os.setTotwin(go.getTotalWin());
		os.setStatus(go.getStatus() != null ? go.getStatus().getValue() : null);
		os.setCanCancel(go.getEndCanCancelTime() == null ? false : DateUtils.currentDate().before(
				go.getEndCanCancelTime()));
		if (go.getParentType() != null) {
			os.setParentType((int) go.getParentType().getValue());
		}
		os.setParentid(go.getParentid());
		if (go.getSaleTime() != null) {
			os.setSaleTime(go.getSaleTime().getTime());
		}
		os.setNumberRecord(go.getNumberRecord());
		os.setOrderId(go.getId());
		os.setCancelModels(go.getCancelModes().getValue());
		GamePackage pk = go.getGamePackage();
		if (null != pk) {
			User user = pk.getUser();
			if (null != user) {
				os.setUserid(user.getId());
				if(user.getUserProfile() != null){
				os.setAccount(user.getUserProfile().getAccount());
				}
				
			}
		}
		os.setTotDiamondWin(go.getTotDiamondWin());
		os.setDiamondMultiple(go.getDiamondMultiple());
		return os;
	}

	public static OrdersStruc gameOrderOperationsEntity2OrdersStruc(GameOrderOperationsEntity go) {
		OrdersStruc os = new OrdersStruc();
		os.setOrderCode(go.getOrderCode());
		os.setLotteryid(go.getLotteryid());
		os.setLotteryName(go.getLotteryName());
		os.setWebIssueCode(go.getWebIssueCode());
		os.setIssueCode(go.getIssueCode());
		os.setIssueStatus(go.getIssueStatus());
		os.setTotamount(go.getTotamount());
		os.setTotwin(go.getTotwin());
		os.setTotDiamondWin(go.getTotDiamondWin());
		os.setDiamondMultiple(go.getDiamondMultiple());
		os.setStatus(go.getStatus());
		os.setParentType(go.getParentType());
		os.setParentid(go.getParentid());
		os.setSaleTime(go.getSaleTime().getTime());
		os.setNumberRecord(go.getNumberRecord());
		os.setOrderId(go.getOrderId());
		os.setUserid(go.getUserid());
		os.setAccount(go.getAccount());
		if (go.getTotwin() != null && go.getTotamount() != null) {
			Double rate = ((go.getTotwin().doubleValue()+go.getTotDiamondWin().doubleValue()) / go.getTotamount()) * 100;
			os.setWinsRatio(rate.longValue());
		}

		os.setCanCancel(go.getEndCanCancelTime() == null ? false : DateUtils.currentDate().before(
				go.getEndCanCancelTime()));

		os.setChannelId(go.getChannelId());
		os.setMultiple(go.getMultiple());
		os.setPlanid(go.getPlanid());
		os.setPlanCode(go.getPlanCode());
		os.setCancelModels(go.getCancelModes());
		os.setChannelVersion(go.getChannelVersion());
		os.setEndSaleTime(go.getEndSaleTime());
		os.setAdminCanCancel(go.getCaculateWinTime() == null ? true : DateUtils.currentDate().before(
				DateUtils.addMinutes(go.getCaculateWinTime(), go.getAdminCanCancelTime() == null ? 0 : go
						.getAdminCanCancelTime().intValue())));
		if (os.getStatus() == 4) {
			os.setAdminCanCancel(false);
		}
		return os;
	}

	public static SlipsStruc gameOrderDetail2SlipStruc(com.winterframework.firefrog.game.entity.GameSlip god) {
		SlipsStruc ss = new SlipsStruc();
		if (god.getId() != null) {
			ss.setSlipid(god.getId());
		}
		GameBetType gameBetType = god.getGameBetType();
		ss.setGameGroupCode(gameBetType.getGameGroupCode());
		ss.setGameSetCode(gameBetType.getGameSetCode());
		ss.setBetMethodCode(gameBetType.getBetMethodCode());
		ss.setTotbets(god.getTotalBet().intValue());
		ss.setMultiple(god.getMultiple());
		ss.setTotamount(god.getTotalAmount());
		ss.setMoneyMode(god.getMoneyMode().getValue());
		ss.setStatus(god.getGameSlipStatus().getValue());
		ss.setBetDetail(god.getBetDetail());
		ss.setAward(god.getEvaluateWin());
		ss.setAwardMode(god.getAwardMode());
		ss.setRetPoint(god.getRetPoint());
		ss.setRetAward(god.getRetAward());
		ss.setDiamondAmount(god.getDiamondAmount());
		ss.setDiamondWin(god.getDiamondWin());
        if(god.getSingleWin()!=null){
			ss.setSingleWin(god.getSingleWin());			
		}
        ss.setOrderId(god.getOrderId());

		return ss;
	}

	public static SlipsStruc gameSlipOperationsEntity2SlipStruc(GameSlipOperationsEntity gs) {
		SlipsStruc ss = new SlipsStruc();
		ss.setOrderId(gs.getOrderId());
		if (null != gs.getSlipid()) {
			ss.setSlipid(gs.getSlipid());
		}
		String str[] = gs.getBetTypeCode().split("_");
		ss.setGameGroupCode(Integer.valueOf(str[0]));
		ss.setGameSetCode(Integer.valueOf(str[1]));
		if (!str[2].equals("null")) {
			ss.setBetMethodCode(Integer.valueOf(str[2]));
		}
		if (null != gs.getTotbets()) {
			ss.setTotbets(gs.getTotbets());
		}
		if (null != gs.getMultiple()) {
			ss.setMultiple(gs.getMultiple());
		}
		if (null != gs.getTotamount()) {
			ss.setTotamount(gs.getTotamount());
		}
		if (null != gs.getMoneyMode()) {
			ss.setMoneyMode(gs.getMoneyMode());
		}
		if (null != gs.getStatus()) {
			ss.setStatus(gs.getStatus());
		}
		if (null != gs.getAward()) {
			ss.setAward(gs.getAward());
			Double rate = (gs.getAward().doubleValue() / gs.getTotamount()) * 100;
			ss.setWinsRadio(rate.longValue());

		}
		ss.setAwardMode(gs.getAwardMode());
		ss.setRetPoint(gs.getRetPoint());
		ss.setRetAward(gs.getRetAward());
		ss.setGroupAward(gs.getGroupAward());
		ss.setGroupAwardDown(gs.getGroupAwardDown());
		ss.setSingleWin(gs.getSingleWin());
		// 重新计算中投比
		if (gs.getStatus() == 2) {
			if (ss.getAward() != null) {
				Double rate = ((gs.getAward().doubleValue()+gs.getDiamondWin().doubleValue()) / (gs.getTotamount()+gs.getDiamondAmount())) * 100;
				ss.setWinsRadio(rate.longValue());
			} else {
				ss.setWinsRadio(0L);
			}
		} else {
			ss.setWinsRadio(0L);
		}
		if (null != gs.getWinsRadio()) {
			ss.setWinsRadio(gs.getWinsRadio());
		}
		if (gs.getFileMode() == 1) {
			// 文件模式将文件内容读取出来
			File file = new File(gs.getBetDetail());
			String content = null;
			try {
				content = FileUtils.readFileToString(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ss.setBetDetail(content);
		} else {
			if (null != gs.getBetDetail() && !"".equals(gs.getBetDetail())) {
				ss.setBetDetail(gs.getBetDetail());
			}
		}
		ss.setDiamondAmount(gs.getDiamondAmount());
		ss.setDiamondWin(gs.getDiamondWin());
		ss.setWinNumber(gs.getWinNumber());
		return ss;
	}

	public static PlansStruc gamePlan2PlansStruc(GamePlan gp) {
		PlansStruc ps = new PlansStruc();
		if (gp.getPlanCode() != null) {
			ps.setPlanCode(gp.getPlanCode());
		}
		if (gp.getLottery() != null) {
			ps.setLotteryid(gp.getLottery().getLotteryId());
		}
		ps.setStartIssueCode(gp.getStartIsuueCode());
		if (gp.getStartWebIssue() != null) {
			ps.setStartWebIssueCode(gp.getStartWebIssue());
		}

		if (gp.getFinishIssue() != null) {
			ps.setFinishIssue(gp.getFinishIssue());
		}
		if (gp.getTotalIssue() != null) {
			ps.setTotalIssue(gp.getTotalIssue());
		}
		ps.setUsedAmount(gp.getSoldAmount());
		ps.setTotamount(gp.getTotamount());
		if (gp.getStopMode() != null) {
			ps.setStopMode(gp.getStopMode().getValue());
		}
		ps.setStopParms(gp.getStopParms());
		ps.setStatus(gp.getStatus().getValue());
		if (gp.getSaleTime() != null) {
			ps.setSaleTime(gp.getSaleTime().getTime());
		}

		ps.setPlanid(gp.getId());

		ps.setLotteryName(gp.getLottery().getLotteryName());

		if (gp.getWinAmount() != null) {
			ps.setTotalWin(gp.getWinAmount());
		}

		return ps;
	}

	/**
	 * @Title: gamePlanDetail2PlansFuturesStruc
	 * @Description: 追号计划详情实体Bean转换为PlansFuturesStruc
	 * @param gamePlanDetail
	 * @return PlansFuturesStruc 返回类型
	 * @throws
	 */
	public static PlansFuturesStruc gamePlanDetail2PlansFuturesStruc(GamePlanDetail gpd) {

		PlansFuturesStruc pfs = new PlansFuturesStruc();
		pfs.setMutiple(gpd.getMutiple().intValue());
		GameIssueEntity gameIssue = gpd.getGameIssue();
		pfs.setIssueCode(gameIssue.getIssueCode());
		pfs.setWebIssueCode(gameIssue.getWebIssueCode());
		pfs.setCanCancel(gameIssue.getSaleEndTime() == null ? true : DateUtils.currentDate().before(
				gameIssue.getSaleEndTime()));
		pfs.setPlanDetailsId(gpd.getId());
		if (gpd.getDetailStatus() != null) {
			pfs.setStatus(gpd.getDetailStatus().getValue());
		}
		if (gpd.getTotamount() != null) {
			pfs.setTotamount(gpd.getTotamount().longValue());
		}
		pfs.setNumberRecord(gameIssue.getNumberRecord());

		return pfs;
	}

	/**
	 * @Title: gameIssueEntity2IssueStruc
	 * @Description: 奖期实体转换成奖期结构
	 * @param gie
	 * @return
	 */
	public static PreviewIssueStruc gameIssueEntity2IssueStruc(GameIssueEntity gie) {
		PreviewIssueStruc issueStruc = new PreviewIssueStruc();
		issueStruc.setSaleStartTime(DataConverterUtil.convertDate2Long(gie.getSaleStartTime()));
		issueStruc.setSaleEndTime(DataConverterUtil.convertDate2Long(gie.getSaleEndTime()));
		issueStruc.setWebIssueCode(gie.getWebIssueCode());
		issueStruc.setIssueCode(gie.getIssueCode());
		issueStruc.setOpenAwardTime(DataConverterUtil.convertDate2Long(gie.getOpenDrawTime()));
		issueStruc.setId(gie.getId());
		if(gie.getPeriodStatus()!=null){
			issueStruc.setPeriodStatus(gie.getPeriodStatus().getValue());
		}
		return issueStruc;
	}

	/**
	 * 
	 * @Title: convertEntity2AwardBonusStruc
	 * @Description: 转换Entity 为AwardBonusStruc
	 * @param awardEntity
	 * @return
	 */
	public static AwardBonusStruc convertEntity2AwardBonusStruc(GameAwardEntity awardEntity, GameAwardEntity entity) {

		AwardBonusStruc struc = new AwardBonusStruc();

		struc.setActualBonus(awardEntity.getActualBonus().longValue());
		struc.setCompanyProfit(awardEntity.getCompanyProfit().longValue());
		struc.setTheoryBonus(awardEntity.getTheoryBonus().longValue());
		struc.setTopAgentPoint(awardEntity.getTopAgentPoint().longValue());
		struc.setTotalProfit(awardEntity.getTotatProfit().longValue());
		if (null != entity && !(awardEntity.getActualBonus().compareTo(entity.getActualBonus()) != 0)) {
			struc.setActualBonusBak(entity.getActualBonus().longValue());
		}

		return struc;
	}

	public static GameIssueRuleQueryResponse ruleEntity2GameIssueRuleQueryResponse(GameIssueRuleEntity rule) {
		GameIssueRuleQueryResponse gameIssueRuleQueryResponse = new GameIssueRuleQueryResponse();
		gameIssueRuleQueryResponse.setIssueRulesName(rule.getIssueRulesName());
		gameIssueRuleQueryResponse.setLotteryId(rule.getLottery().getLotteryId());
		gameIssueRuleQueryResponse.setOpenAwardPeriod(rule.getOpenAwardPeriod());
		gameIssueRuleQueryResponse.setRuleEndTime(DataConverterUtil.convertDate2Long(rule.getRuleEndTime()));
		gameIssueRuleQueryResponse.setRuleStartTime(DataConverterUtil.convertDate2Long(rule.getRuleStartTime()));
		gameIssueRuleQueryResponse.setRuleStatus(rule.getStatus().intValue());
		gameIssueRuleQueryResponse.setRuleType(rule.getRuleType().getValue());
		gameIssueRuleQueryResponse.setRuleId(rule.getId());
		gameIssueRuleQueryResponse.setStopStartTime(DataConverterUtil.convertDate2Long(rule.getStopStartTime()));
		gameIssueRuleQueryResponse.setStopEndTime(DataConverterUtil.convertDate2Long(rule.getStopEndTime()));
		gameIssueRuleQueryResponse.setSeriesIssueCode(rule.getSeriesIssueCode());
		List<SalesIssueStruc> salesIssueStrucs = new ArrayList<SalesIssueStruc>();
		for (GameIssueTemplateEntity template : rule.getGameIssueTemplateEntitys()) {
			SalesIssueStruc salesIssueStruc = new SalesIssueStruc();
			salesIssueStruc.setFirstAwardTime(DataConverterUtil.convertDate2Long(template.getFirstAwardTime()));
			salesIssueStruc.setLastAwardTime(DataConverterUtil.convertDate2Long(template.getLastAwardTime()));
			salesIssueStruc.setSalePeriodTime(template.getSalePeriodTime().intValue());
			salesIssueStruc.setSaleStartTime(DataConverterUtil.convertDate2Long(template.getSaleStartTime()));
			salesIssueStruc.setScheduleStopTime(template.getScheduleStopTime().intValue());
			salesIssueStruc.setId(template.getId());
			salesIssueStruc.setSaleTimeSn(template.getSaleTimeSn());
			salesIssueStrucs.add(salesIssueStruc);
		}
		gameIssueRuleQueryResponse.setSalesIssueStrucs(salesIssueStrucs);
		return gameIssueRuleQueryResponse;
	}

	/**
	 * @Title: specialGameIssueRuleAddOrUpdateRequest2GameIssueRuleEntity
	 * @Description: 将特殊奖期规则请求转化为奖期规则实体
	 * @param request
	 * @param stop
	 * @return
	 */
	public static GameIssueRuleEntity specialGameIssueRuleAddOrUpdateRequest2GameIssueRuleEntity(
			Request<CommonOrSpecialGameIssueRuleAddOrUpdateRequest> request) {
		CommonOrSpecialGameIssueRuleAddOrUpdateRequest issueRequest = request.getBody().getParam();
		GameIssueRuleEntity gameIssueRuleEntity = new GameIssueRuleEntity();
		if (issueRequest.getOperationType() == 1) {
			gameIssueRuleEntity.setCreateTime(new Date());
			gameIssueRuleEntity.setUpdateTime(new Date());
		} else {
			gameIssueRuleEntity.setUpdateTime(new Date());
		}
		List<GameIssueTemplateEntity> gameIssueTemplateEntitys = new ArrayList<GameIssueTemplateEntity>();
		if (issueRequest.getSalesIssueStrucs() != null && issueRequest.getSalesIssueStrucs().size() != 0) {
			for (SalesIssueStruc salesIssueStruc : issueRequest.getSalesIssueStrucs()) {
				GameIssueTemplateEntity gameIssueTemplateEntity = new GameIssueTemplateEntity();
				if (issueRequest.getOperationType() == 1) {
					gameIssueTemplateEntity.setCreateTime(new Date());
					gameIssueTemplateEntity.setUpdateTime(new Date());
				} else {
					gameIssueTemplateEntity.setUpdateTime(new Date());
				}
				gameIssueTemplateEntity.setFirstAwardTime(DataConverterUtil.convertLong2Date(salesIssueStruc
						.getFirstAwardTime()));
				gameIssueTemplateEntity.setLastAwardTime(DataConverterUtil.convertLong2Date(salesIssueStruc
						.getLastAwardTime()));
				gameIssueTemplateEntity.setRule(gameIssueRuleEntity);
				gameIssueTemplateEntity
						.setSalePeriodTime(new Long((salesIssueStruc.getSalePeriodTime() != null && salesIssueStruc
								.getSalePeriodTime() != 0) ? salesIssueStruc.getSalePeriodTime() : 90000000000l));
				gameIssueTemplateEntity.setSaleStartTime(salesIssueStruc.getSaleStartTime() != null ? DataConverterUtil
						.convertLong2Date(salesIssueStruc.getSaleStartTime()) : null);
				gameIssueTemplateEntity.setScheduleStopTime(new Long(salesIssueStruc.getScheduleStopTime()));
				gameIssueTemplateEntity.setId(salesIssueStruc.getId());
				gameIssueTemplateEntitys.add(gameIssueTemplateEntity);
			}
		}
		gameIssueRuleEntity.setGameIssueTemplateEntitys(gameIssueTemplateEntitys);
		gameIssueRuleEntity.setIssueRulesName(issueRequest.getIssueRulesName());
		Lottery lottery = new Lottery();
		lottery.setLotteryId(issueRequest.getLotteryId());
		gameIssueRuleEntity.setLottery(lottery);
		gameIssueRuleEntity.setOpenAwardPeriod(issueRequest.getOpenAwardPeriod());
		gameIssueRuleEntity.setRuleEndTime(DataConverterUtil.convertLong2Date(issueRequest.getRuleEndTime()));
		gameIssueRuleEntity.setRuleStartTime(DataConverterUtil.convertLong2Date(issueRequest.getRuleStartTime()));

		if (issueRequest.getRuleType() == null || issueRequest.getRuleType().intValue() == 1) {
			gameIssueRuleEntity.setRuleType(RuleType.COMMEN);
		} else if (issueRequest.getRuleType().intValue() == 2) {
			gameIssueRuleEntity.setRuleType(RuleType.SPECIAL);
		} else if (issueRequest.getRuleType().intValue() == 3) {
			gameIssueRuleEntity.setRuleType(RuleType.STOP);
		}
		gameIssueRuleEntity.setStatus(GameIssueRuleEntity.STATUS_UNAUDIT);

		gameIssueRuleEntity.setId(issueRequest.getRuleId());
		return gameIssueRuleEntity;
	}

	public static GameIssueRuleEntity stopGameIssueRuleAddOrUpdateRequest2GameIssueRuleEntity(
			Request<StopGameIssueRuleAddOrUpdateRequest> request) {
		StopGameIssueRuleAddOrUpdateRequest issueRequest = request.getBody().getParam();
		GameIssueRuleEntity gameIssueRuleEntity = new GameIssueRuleEntity();
		if (issueRequest.getOperationType() == 1) {
			gameIssueRuleEntity.setCreateTime(new Date());
			gameIssueRuleEntity.setUpdateTime(new Date());
		} else {
			gameIssueRuleEntity.setUpdateTime(new Date());
		}
		gameIssueRuleEntity.setIssueRulesName(issueRequest.getIssueRulesName());
		Lottery lottery = new Lottery();
		lottery.setLotteryId(issueRequest.getLotteryId());
		gameIssueRuleEntity.setLottery(lottery);
		gameIssueRuleEntity.setRuleEndTime(DataConverterUtil.convertLong2Date(issueRequest.getRuleEndTime()));
		gameIssueRuleEntity.setRuleStartTime(DataConverterUtil.convertLong2Date(issueRequest.getRuleStartTime()));
		gameIssueRuleEntity.setStatus(GameIssueRuleEntity.STATUS_UNAUDIT);
		gameIssueRuleEntity.setRuleType(RuleType.STOP);
		gameIssueRuleEntity.setRuleid(issueRequest.getRuleId());
		gameIssueRuleEntity.setOpenAwardPeriod(issueRequest.getOpenAwardPeriod());
		gameIssueRuleEntity.setStopStartTime(DataConverterUtil.convertLong2Date(issueRequest.getStopStartTime()));
		gameIssueRuleEntity.setStopEndTime(DataConverterUtil.convertLong2Date(issueRequest.getStopEndTime()));
		gameIssueRuleEntity.setSeriesIssueCode(issueRequest.getSeriesIssueCode());
		gameIssueRuleEntity.setId(issueRequest.getRuleId());
		return gameIssueRuleEntity;
	}

	public static BetMethodMultipleStruc betLimit2BetMethodMultipleStruc(BetLimit bl) {
		BetMethodMultipleStruc bmms = new BetMethodMultipleStruc();
		bmms.setGameGroupCode(bl.getGameGroupCode());
		bmms.setGameSetCode(bl.getGameSetCode());
		bmms.setBetMethodCode(bl.getBetMethodCode());
		bmms.setMultiple(bl.getMaxMultiple());
		bmms.setMultiple_bak(bl.getMaxMultiple_bak());
		bmms.setMaxBonus(bl.getMaxBonus());
		bmms.setStatus(bl.getStatus() == null ? 0 : bl.getStatus());
		bmms.setSpecialMultiple(bl.getSpecialLimit());
		bmms.setSpecialMultiple_bak(bl.getSpecialLimit_bak());
		bmms.setSpecialMaxBonus(bl.getSpecialMaxBonus());
				if (bl.getBetLimitAssist() != null && bl.getBetLimitAssist().size() > 0) {
			Integer[] multiples = new Integer[bl.getBetLimitAssist().size()];
			int i = 0;
			for (BetLimitAssist betLimitAssist : bl.getBetLimitAssist()) {
				multiples[i] = betLimitAssist.getMaxMultiple().intValue();
				i++;
			}
			bmms.setMultiples(multiples);
		}
		return bmms;
	}

	public static BetMethodMultipleStruc betLimit2BetMethodMultipleStrucForBetPage(BetLimit bl) {
		BetMethodMultipleStruc bmms = new BetMethodMultipleStruc();
		bmms.setGameGroupCode(bl.getGameGroupCode());
		bmms.setGameSetCode(bl.getGameSetCode());
		bmms.setBetMethodCode(bl.getBetMethodCode());
		bmms.setMultiple(bl.getMaxMultiple());
		bmms.setMultiple_bak(bl.getMaxMultiple_bak());
		bmms.setMaxBonus(bl.getMaxBonus());
		bmms.setStatus(bl.getStatus() == null ? 0 : bl.getStatus());
		bmms.setSpecialMultiple(bl.getSpecialLimit());
		bmms.setSpecialMultiple_bak(bl.getSpecialLimit_bak());
		//先看是否有骰寶相關特殊投注限制
		if(K3GameLimitUtil.processorSpecialGameLimit(bl).size()>0){
			bmms.setK3hezhiMultiple(K3GameLimitUtil.processorSpecialGameLimit(bl));
		}else{
			if (bl.getBetLimitAssist() != null && bl.getBetLimitAssist().size() > 0) {
				Integer[] multiples = new Integer[bl.getBetLimitAssist().size()];
				int i = 0;
				for (BetLimitAssist betLimitAssist : bl.getBetLimitAssist()) {
					multiples[i] = betLimitAssist.getMaxMultiple().intValue();
					i++;
				}
				bmms.setMultiples(multiples);
			}
		}

		return bmms;
	}

	public static BetLimit betMethodMultipleStruc2BetLimit(BetMethodMultipleStruc bmms, Long lotteryid) {
		BetLimit bl = new BetLimit();
		bl.setLotteryid(lotteryid);
		bl.setGameGroupCode(bmms.getGameGroupCode());
		bl.setGameSetCode(bmms.getGameSetCode());
		bl.setBetMethodCode(bmms.getBetMethodCode());
		bl.setMaxMultiple(bmms.getMultiple());
		bl.setMultipleIndex(bmms.getMultipleIndex());
		return bl;
	}

	public static BetMethodHelpStruc betMethodDescription2BetMethodHelpStruc(BetMethodDescription bmd) {
		BetMethodHelpStruc bmhs = new BetMethodHelpStruc();
		bmhs.setGameGroupCode(bmd.getGameGroupCode());
		bmhs.setGameSetCode(bmd.getGameSetCode());
		bmhs.setBetMethodCode(bmd.getBetMethodCode());
		bmhs.setGamePromptDes(bmd.getGamePromptDes());
		if (null != bmd.getGamePromptDes_bak()) {
			bmhs.setGamePromptDes_bak(bmd.getGamePromptDes_bak());
		}
		bmhs.setGameExamplesDes(bmd.getGameExamplesDes());
		if (null != bmd.getGameExamplesDes_bak()) {
			bmhs.setGameExamplesDes_bak(bmd.getGameExamplesDes_bak());
		}
		return bmhs;
	}

	public static BetMethodDescription betMethodHelpStruc2BetMethodDescription(BetMethodHelpStruc bmms, long lotteryid) {
		BetMethodDescription bmd = new BetMethodDescription();
		bmd.setLotteryid(lotteryid);
		bmd.setGameGroupCode(bmms.getGameGroupCode());
		bmd.setGameSetCode(bmms.getGameSetCode());
		bmd.setBetMethodCode(bmms.getBetMethodCode());
		bmd.setGameExamplesDes(bmms.getGameExamplesDes());
		bmd.setGamePromptDes(bmms.getGamePromptDes());
		return bmd;
	}

	public static GameMultipleEntity gameMutipleQueryRequest2GameMutipleEntity(GameMultipleQueryRequest request) {
		GameMultipleEntity entity = new GameMultipleEntity();

		Lottery lottery = new Lottery();
		lottery.setLotteryId(request.getLotteryId());
		entity.setLottery(lottery);

		entity.setGameGroupCode(request.getGameGroupCode());
		entity.setGameSetCode(request.getGameSetCode());
		entity.setBetMethodCode(request.getBetMethodCode());

		return entity;
	}

	public static AwardListStruc convertEntity2Struc(GameAwardGroupEntity entity) {

		AwardListStruc struc = new AwardListStruc();

		struc.setAwardGroupId(entity.getId());
		struc.setAwardName(entity.getAwardName());
		struc.setCreateTime(entity.getCreateTime().getTime());
		struc.setDirectRet(entity.getDirectRet().intValue());
		struc.setLotteryId(entity.getLottery().getId());
		struc.setStatus(entity.getStatus().getValue());
		struc.setSysAwardGroup(entity.getSysAwardGroup().getValue());
		struc.setThreeoneRet(entity.getThreeoneRet().intValue());
		if (null != entity.getUpdateTime()) {
			struc.setUpdateTime(entity.getUpdateTime().getTime());
		}

		return struc;
	}

	public static BetMethodStatusStruc sellingStatus2BetMethodStatusStruc(SellingStatus ss) {
		BetMethodStatusStruc bmss = new BetMethodStatusStruc();
		bmss.setGameGroupCode(ss.getGameGroupCode());
		bmss.setGameSetCode(ss.getGameSetCode());
		bmss.setBetMethodCode(ss.getBetMethodCode());
		bmss.setStatus(ss.getStatus());
		bmss.setStatus_changed(ss.isStatus_changed());
		return bmss;
	}

	public static SellingStatus betMethodStatusStruc2SellingStatus(BetMethodStatusStruc bmms, long lotteryid) {
		SellingStatus ss = new SellingStatus();
		ss.setLotteryid(lotteryid);
		ss.setGameGroupCode(bmms.getGameGroupCode());
		ss.setGameSetCode(bmms.getGameSetCode());
		ss.setBetMethodCode(bmms.getBetMethodCode());
		ss.setStatus(bmms.getStatus());
		return ss;
	}

	public static QuerySeriesConfigResponse GameSeriesConfigEntity2QuerySeriesConfigResponse(
			GameSeriesConfigEntity entity) {
		QuerySeriesConfigResponse config = new QuerySeriesConfigResponse();

		config.setLotteryid(entity.getLottery().getLotteryId());
		config.setBackoutRatio(entity.getBackoutRatio());
		config.setBackoutRatio_bak(entity.getBackoutRatio_bak());
		config.setBackoutStartFee(entity.getBackoutStartFee());
		config.setBackoutStartFee_bak(entity.getBackoutStartFee_bak());
		config.setIssuewarnExceptionTime(entity.getIssuewarnExceptionTime());
		config.setIssuewarnExceptionTime_bak(entity.getIssuewarnExceptionTime_bak());
		config.setIssuewarnBackoutTime(entity.getIssuewarnBackoutTime());
		config.setIssuewarnBackoutTime_bak(entity.getIssuewarnBackoutTime_bak());
		config.setStatus(entity.getStatus() == null ? 0 : entity.getStatus().getValue());
		config.setEmail(entity.getEmail());
		config.setEmail_bak(entity.getEmail_bak());
		config.setIssuewarnUserBackoutTime(entity.getIssuewarnUserBackoutTime());
		config.setIssuewarnUserBackoutTime_bak(entity.getIssuewarnUserBackoutTime_bak());
		config.setHasVedio(entity.getHasVideo());
		config.setVedioStrucs(entity.getVedioStrucs());
		return config;
	}

	public static QuerySeriesConfigRiskResponse GameRiskConfig2QuerySeriesConfigRiskResponse(GameRiskConfig entity) {
		QuerySeriesConfigRiskResponse config = new QuerySeriesConfigRiskResponse();

		config.setOrderwarnContinuousWins(entity.getOrderwarnContinuousWins());
		config.setOrderwarnMaxwins(entity.getOrderwarnMaxwins());
		config.setOrderwarnSlipWinsRatio(entity.getOrderwarnSlipWinsratio());
		config.setOrderwarnWinsRatio(entity.getOrderwarnWinsRatio());
		config.setOrderwarnMaxslipWins(entity.getOrderwarnMaxslipWins());

		return config;
	}

	public static UserCenterReportStruc UserCenterReportInfo2UserCenterReportStruc(UserCenterReportInfo info) {
		UserCenterReportStruc ut = new UserCenterReportStruc();
		ut.setAccount(info.getUser().getUserProfile().getAccount());
		ut.setActualTotalSubuserSaleroom(info.getActualTotalSubuserSaleroom());
		ut.setTotalProfit(info.getTotalProfit());
		ut.setTotalSubuserPoint(info.getTotalSubuserPoint());
		ut.setTotalSubuserSaleroom(info.getTotalSubuserSaleroom());
		ut.setTotalSubuserWins(info.getTotalSubuserWins());
		ut.setActivityGifts(info.getActivityGifts());
		ut.setUserId(info.getUser().getId());
		ut.setUserLvl(info.getUser().getUserLevel());
		ut.setHasNextUser(info.getHasNextUser());
		return ut;
	}

	public static UserAwardListStruc userAwardGroupEntity2UserAwardListStruc(UserAwardGroupEntity entity,
			UserAwardListStruc uals) {
		uals.setLotterySeriesCode(entity.getLotterySeriesCode());
		uals.setLotterySeriesName(entity.getLotterySeriesName());
		uals.setLotteryId(entity.getLotteryId());
		uals.setLotteryName(entity.getLotteryName());
		// 解决0001402: admin总代下二代用户开户失败 问题，现在awardGroupId对于game项目来说没有作用，统一用系统奖金组。
		uals.setAwardGroupId(entity.getAwardGroupId());
		uals.setAwardName(entity.getAwardName());
		uals.setDirectRet(entity.getDirectRet());
		uals.setDirectLimitRet(entity.getDirectRet());
		uals.setMaxDirectRet(entity.getMaxDirectRet());
		uals.setThreeoneRet(entity.getThreeoneRet());
		uals.setThreeLimitRet(entity.getThreeoneRet());
		uals.setMaxThreeOneRet(entity.getMaxThreeOneRet());
		uals.setSuperRet(entity.getSuperRet());
		uals.setSuperLimitRet(entity.getSuperRet());
		uals.setMaxSuperRet(entity.getMaxSuperRet());
		uals.setStatus(entity.getStatus());
		uals.setIsUsed(entity.getIsUsed());
		uals.setSetType(entity.getSetType());
		uals.setBetType(entity.getBetType());
		uals.setSysAwardGrouId(String.valueOf(entity.getSysGroupAward().getId()));
		uals.setLhcColor(entity.getLhcColor());
		uals.setLhcYear(entity.getLhcYear());
		uals.setLhcFlatcode(entity.getLhcFlatcode());
		uals.setLhcHalfwave(entity.getLhcHalfwave());
		uals.setLhcOneyear(entity.getLhcOneyear());
		uals.setLhcNotin(entity.getLhcNotin());
		uals.setLhcContinuein23(entity.getLhcContinuein23());
		uals.setLhcContinuein4(entity.getLhcContinuein4());
		uals.setLhcContinuein5(entity.getLhcContinuein5());
		uals.setLhcContinuenotin23(entity.getLhcContinuenotin23());
		uals.setLhcContinuenotin4(entity.getLhcContinuenotin4());
		uals.setLhcContinuenotin5(entity.getLhcContinuenotin5());
		uals.setLhcContinuecode(entity.getLhcContinuecode());
		
		uals.setLhcColorLimit(entity.getLhcColorLimit());		
		uals.setLhcYearLimit(entity.getLhcYearLimit());
		uals.setSbThreeoneRet(entity.getSbThreeoneRet());
		uals.setMaxSbThreeoneRet(entity.getMaxSbThreeoneRet());
		uals.setSbThreeoneRetLimit(entity.getSbThreeoneRetLimit());
		uals.setLhcFlatcodeLimit(entity.getLhcFlatcodeLimit());
		uals.setLhcHalfwaveLimit(entity.getLhcHalfwaveLimit());
		uals.setLhcOneyearLimit(entity.getLhcOneyearLimit());
		uals.setLhcNotinLimit(entity.getLhcNotinLimit());
		uals.setLhcContinuein23Limit(entity.getLhcContinuein23Limit());
		uals.setLhcContinuein4Limit(entity.getLhcContinuein4Limit());
		uals.setLhcContinuein5Limit(entity.getLhcContinuein5Limit());
		uals.setLhcContinuenotin23Limit(entity.getLhcContinuenotin23Limit());
		uals.setLhcContinuenotin4Limit(entity.getLhcContinuenotin4Limit());
		uals.setLhcContinuenotin5Limit(entity.getLhcContinuenotin5Limit());
		uals.setLhcContinuecodeLimit(entity.getLhcContinuecodeLimit());
		
		return uals;
	}

	public static UserAwardListStruc gameAwardGroupEntity2UserAwardListStruc(GameAwardGroupEntity entity,
			UserAwardListStruc uals) {

		uals.setLotterySeriesCode(entity.getLottery().getLotterySeriesCode());
		uals.setLotterySeriesName(entity.getLottery().getLotterySeriesName());
		uals.setLotteryName(entity.getLottery().getLotteryName());
		uals.setLotteryId(entity.getLottery().getLotteryId());
		uals.setAwardGroupId(entity.getId());
		uals.setAwardName(entity.getAwardName());
		uals.setDirectRet(entity.getDirectRet().longValue());
		uals.setDirectLimitRet(entity.getDirectRet().longValue());
		uals.setThreeoneRet(entity.getThreeoneRet().longValue());
		uals.setThreeLimitRet(entity.getThreeoneRet().longValue());
		uals.setSuperRet(entity.getSuperRet().longValue());
		uals.setSuperLimitRet(entity.getSuperRet().longValue());
		uals.setStatus(1);
		uals.setIsUsed(true);
		uals.setSysAwardGrouId(String.valueOf(entity.getId()));
		uals.setLhcYear(entity.getLhcYear().longValue());
		uals.setLhcColor(entity.getLhcColor().longValue());
		uals.setSbThreeoneRet(entity.getSbThreeoneRet().longValue());
		uals.setLhcFlatcode(entity.getLhcFlatcode().longValue());
		uals.setLhcHalfwave(entity.getLhcHalfwave().longValue());
		uals.setLhcOneyear(entity.getLhcOneyear().longValue());
		uals.setLhcNotin(entity.getLhcNotin().longValue());
		uals.setLhcContinuein23(entity.getLhcContinuein23().longValue());
		uals.setLhcContinuein4(entity.getLhcContinuein4().longValue());
		uals.setLhcContinuein5(entity.getLhcContinuein5().longValue());
		uals.setLhcContinuenotin23(entity.getLhcContinuenotin23().longValue());
		uals.setLhcContinuenotin4(entity.getLhcContinuenotin4().longValue());
		uals.setLhcContinuenotin5(entity.getLhcContinuenotin5().longValue());
		uals.setLhcContinuecode(entity.getLhcContinuecode().longValue());
		
		uals.setLhcYearLimit(entity.getLhcYear().longValue());
		uals.setLhcColorLimit(entity.getLhcColor().longValue());
		uals.setSbThreeoneRetLimit(entity.getSbThreeoneRet().longValue());
		uals.setLhcFlatcodeLimit(entity.getLhcFlatcode().longValue());
		uals.setLhcHalfwaveLimit(entity.getLhcHalfwave().longValue());
		uals.setLhcOneyearLimit(entity.getLhcOneyear().longValue());
		uals.setLhcNotinLimit(entity.getLhcNotin().longValue());
		uals.setLhcContinuein23Limit(entity.getLhcContinuein23().longValue());
		uals.setLhcContinuein4Limit(entity.getLhcContinuein4().longValue());
		uals.setLhcContinuein5Limit(entity.getLhcContinuein5().longValue());
		uals.setLhcContinuenotin23Limit(entity.getLhcContinuenotin23().longValue());
		uals.setLhcContinuenotin4Limit(entity.getLhcContinuenotin4().longValue());
		uals.setLhcContinuenotin5Limit(entity.getLhcContinuenotin5().longValue());
		uals.setLhcContinuecodeLimit(entity.getLhcContinuecode().longValue());
		return uals;
	}

	public static UserAwardListByBetStruc userAwardGroupEntity2UserAwardListByBetStruc(UserBetAwardGroupEntity entity) {
		UserAwardListByBetStruc uals = new UserAwardListByBetStruc();
		uals.setLotterySeriesCode(entity.getLotterySeriesCode());
		uals.setLotteryId(entity.getLotteryid());
		uals.setAwardGroupId(entity.getAwardGroupid());
		uals.setAwardName(entity.getAwardName());
		uals.setUiLotteryName(SSCBetNameUtil.getUILotteryName(entity.getLotteryid()));
		uals.setIsMaxAward(entity.getIsMaxAward());
		return uals;
	}

	public static UserAwardGroupEntity userAwardListStruc2UserAwardGroupEntity(UserAwardListStruc uals, Long userid) {
		UserAwardGroupEntity entity = new UserAwardGroupEntity();
		entity.setAwardGroupId(uals.getAwardGroupId());
		entity.setAwardName(uals.getAwardName());
		entity.setDirectRet(uals.getDirectRet() == null ? 0 : uals.getDirectRet());
		entity.setDirectLimitRet(uals.getDirectLimitRet() == null ? 0 : uals.getDirectLimitRet());
		entity.setMaxDirectRet(uals.getMaxDirectRet());
		entity.setThreeLimitRet(uals.getThreeLimitRet() == null ? 0 : uals.getThreeLimitRet());
		entity.setThreeoneRet(uals.getThreeoneRet() == null ? 0 : uals.getThreeoneRet());
		entity.setMaxThreeOneRet(uals.getMaxThreeOneRet());
		entity.setSuperLimitRet(uals.getSuperLimitRet() == null ? 0 : uals.getSuperLimitRet());
		entity.setSuperRet(uals.getSuperRet() == null ? 0 : uals.getSuperRet());
		entity.setMaxSuperRet(uals.getMaxSuperRet());
		entity.setLotteryId(uals.getLotteryId());
		entity.setLotterySeriesCode(uals.getLotterySeriesCode());
		entity.setLotterySeriesName(uals.getLotterySeriesName()); 
		entity.setStatus(uals.getStatus() == null ? 0 : uals.getStatus());
		entity.setUserid(userid);
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		entity.setSetType(uals.getSetType() == null ? 1 : uals.getSetType());
		entity.setBetType(uals.getBetType());
		entity.setGroupChain(uals.getSysAwardGrouId());
		GameAwardGroupEntity sysAwardGroup = new GameAwardGroupEntity();
		sysAwardGroup.setId(uals.getSysAwardGrouId() == null ? uals.getAwardGroupId() : Long.valueOf(uals
				.getSysAwardGrouId()));
		entity.setSysGroupAward(sysAwardGroup);
		entity.setLhcYearLimit(uals.getLhcYearLimit() == null ? 0 : uals.getLhcYearLimit());
		entity.setLhcYear(uals.getLhcYear());
		entity.setMaxLhcYear(uals.getMaxLhcYear());
		entity.setLhcColorLimit(uals.getLhcColorLimit() == null ? 0 : uals.getLhcColorLimit());
		entity.setLhcColor(uals.getLhcColor());
		entity.setMaxLhcColor(uals.getMaxLhcColor());
		entity.setSbThreeoneRet(uals.getSbThreeoneRet());
		entity.setMaxSbThreeoneRet(uals.getMaxSbThreeoneRet());
		entity.setLhcFlatcodeLimit(uals.getLhcFlatcodeLimit() == null ? 0 : uals.getLhcFlatcodeLimit());
		entity.setLhcFlatcode(uals.getLhcFlatcode());
		entity.setMaxLhcFlatcode(uals.getMaxLhcFlatcode());
		entity.setLhcHalfwaveLimit(uals.getLhcHalfwaveLimit() == null ? 0 : uals.getLhcHalfwaveLimit());
		entity.setLhcHalfwave(uals.getLhcHalfwave());
		entity.setMaxLhcHalfwave(uals.getMaxLhcHalfwave());
		entity.setLhcOneyearLimit(uals.getLhcOneyearLimit() == null ? 0 : uals.getLhcOneyearLimit());
		entity.setLhcOneyear(uals.getLhcOneyear());
		entity.setMaxLhcOneyear(uals.getMaxLhcOneyear());
		entity.setLhcNotinLimit(uals.getLhcNotinLimit() == null ? 0 : uals.getLhcNotinLimit());
		entity.setLhcNotin(uals.getLhcNotin());
		entity.setMaxLhcNotin(uals.getMaxLhcNotin());
		entity.setLhcContinuein23Limit(uals.getLhcContinuein23Limit() == null ? 0 : uals.getLhcContinuein23Limit());
		entity.setLhcContinuein23(uals.getLhcContinuein23());
		entity.setMaxLhcContinuein23(uals.getMaxLhcContinuein23());
		entity.setLhcContinuein4Limit(uals.getLhcContinuein4Limit() == null ? 0 : uals.getLhcContinuein4Limit());
		entity.setLhcContinuein4(uals.getLhcContinuein4());
		entity.setMaxLhcContinuein4(uals.getMaxLhcContinuein4());
		entity.setLhcContinuein5Limit(uals.getLhcContinuein5Limit() == null ? 0 : uals.getLhcContinuein5Limit());
		entity.setLhcContinuein5(uals.getLhcContinuein5());
		entity.setMaxLhcContinuein5(uals.getMaxLhcContinuein5());
		entity.setLhcContinuenotin23Limit(uals.getLhcContinuenotin23Limit() == null ? 0 : uals.getLhcContinuenotin23Limit());
		entity.setLhcContinuenotin23(uals.getLhcContinuenotin23());
		entity.setMaxLhcContinuenotin23(uals.getMaxLhcContinuenotin23());
		entity.setLhcContinuenotin4Limit(uals.getLhcContinuenotin4Limit() == null ? 0 : uals.getLhcContinuenotin4Limit());
		entity.setLhcContinuenotin4(uals.getLhcContinuenotin4());
		entity.setMaxLhcContinuenotin4(uals.getMaxLhcContinuenotin4());
		entity.setLhcContinuenotin5Limit(uals.getLhcContinuenotin5Limit() == null ? 0 : uals.getLhcContinuenotin5Limit());
		entity.setLhcContinuenotin5(uals.getLhcContinuenotin5());
		entity.setMaxLhcContinuenotin5(uals.getMaxLhcContinuenotin5());
		entity.setLhcContinuecodeLimit(uals.getLhcContinuecodeLimit() == null ? 0 : uals.getLhcContinuecodeLimit());
		entity.setLhcContinuecode(uals.getLhcContinuecode());
		entity.setMaxLhcContinuecode(uals.getMaxLhcContinuecode());

		return entity;
	}

	public static LotteryListStruc gameSeries2LotteryListStruc(GameSeries gs) {
		LotteryListStruc lls = new LotteryListStruc();
		lls.setLotteryTypeCode(gs.getLotteryTypeCode());
		lls.setLotterySeriesCode(gs.getLotterySeriesCode());
		lls.setLotteryid(gs.getLotteryid());
		lls.setLotteryName(gs.getLotteryName());
		lls.setMiniLotteryProfit(gs.getMiniLotteryProfit());
		lls.setLotteryHelpDes(gs.getLotteryHelpDes());
		lls.setStatus(gs.getStatus());
		lls.setCreateTime(gs.getCreateTime() != null ? gs.getCreateTime().getTime() : null);
		lls.setUpdateTime(gs.getUpdateTime() != null ? gs.getUpdateTime().getTime() : null);
		lls.setOperator(gs.getOperator());
		
		return lls;
	}

	public static OperationReportStruc winsReport2OperationReportStruc(WinsReport wr) {
		OperationReportStruc ors = new OperationReportStruc();
		ors.setIssueCode(wr.getIssueCode());
		ors.setWebIssueCode(wr.getWebIssueCode());
		ors.setLotteryid(wr.getLotteryid());
		ors.setLotteryName(wr.getLotteryName());
		ors.setReportDate(wr.getReportDate().getTime());
		ors.setTotalCancel(wr.getTotalCancel());
		ors.setTotalPoints(wr.getTotalPoints());
		ors.setTotalProfit(wr.getTotalProfit());
		ors.setTotalSales(wr.getTotalSales());
		ors.setTotalWins(wr.getTotalWins());
		return ors;
	}

	public static WinsSumReportQueryResponse winsSumReport2WinsSumReportQueryResponse(WinsSumReport wsr) {
		WinsSumReportQueryResponse res = new WinsSumReportQueryResponse();
		res.setLotteryName(wsr.getLotteryName());
		res.setTotalCancelSum(wsr.getTotalCancelSum());
		res.setTotalIssueCount(wsr.getTotalIssueCount());
		res.setTotalPointsSum(wsr.getTotalPointsSum());
		res.setTotalProfitSum(wsr.getTotalProfitSum());
		res.setTotalSalesSum(wsr.getTotalSalesSum());
		res.setTotalWinsSum(wsr.getTotalWinsSum());
		return res;
	}

	public static String convert2OrderSortType(Integer sortType) {
		switch (sortType) {
		case 1:
			return "saleTime";
		case 2:
			return "saleTime desc";
		case 3:
			return "totamount";
		case 4:
			return "totamount desc";
		case 5:
			return "totwin";
		case 6:
			return "totwin desc";
		case 7:
			return "winsRatio";
		case 8:
			return "winsRatio desc";
		default:
			return "saleTime desc";
		}
	}

	public static String convert2PlanSortType(Integer sortType) {
		switch (sortType) {
		case 1:
			return "saleTime";
		case 2:
			return "saleTime desc";
		case 3:
			return "totamount";
		case 4:
			return "totamount desc";
		case 5:
			return "totwin";
		case 6:
			return "totwin desc";
		case 7:
			return "stopMode";
		case 8:
			return "stopMode desc";
		default:
			return "saleTime desc";
		}
	}

	public static PlansStruc gamePlanOperationsEntity2PlansStruc(GamePlanOperationsEntity gp) {
		PlansStruc ps = new PlansStruc();
		ps.setPlanCode(gp.getPlanCode());
		ps.setLotteryid(gp.getLotteryid());
		ps.setLotteryName(gp.getLotteryName());
		ps.setStartIssueCode(gp.getStartIssueCode());
		ps.setStartWebIssueCode(gp.getStartWebIssueCode());
		ps.setFinishIssue(gp.getFinishIssue());
		ps.setTotalIssue(gp.getTotalIssue());
		ps.setCancelIssue(gp.getCancelIssue());
		ps.setUsedAmount(gp.getUsedAmount());
		ps.setCanceledAmount(gp.getCanceledAmount());
		ps.setTotamount(gp.getTotamount());
		ps.setTotalWin(gp.getTotalWin());
		ps.setStopMode(gp.getStopMode());
		ps.setStopParms(gp.getStopParms());
		ps.setStatus(gp.getStatus());
		ps.setSaleTime((gp.getSaleTime() == null) ? 0l : gp.getSaleTime().getTime());
		ps.setPlanid(gp.getPlanid());
		ps.setUserid(gp.getUserid());
		ps.setAccount(gp.getAccount());
		ps.setChannelid(gp.getChannelid());
		ps.setChannelName(ChannelType.getName(new Long(gp.getChannelid())));
		ps.setChannelVersion(gp.getChannelVersion());
		return ps;
	}

	public static OperationDetailReportStruc winsReport2OperationDetailReportStruc(WinsReport wr) {
		OperationDetailReportStruc odrs = new OperationDetailReportStruc();
		odrs.setLotteryId(wr.getLotteryid());
		odrs.setGameGroupCode(wr.getGameGroupCode());
		odrs.setGameSetCode(wr.getGameSetCode());
		odrs.setBetMethodCode(wr.getBetMethodCode());
		odrs.setTotalSales(wr.getTotalSales());
		odrs.setTotalPoints(wr.getTotalPoints());
		odrs.setTotalCancel(wr.getTotalCancel());
		odrs.setTotalWins(wr.getTotalWins());
		odrs.setTotalProfit(wr.getTotalProfit());

		return odrs;
	}

	public static SpiteOrderStruc gameSpiteOperationsEntity2SpiteOrderStruc(GameSpiteOperationsEntity gso) {
		SpiteOrderStruc sos = new SpiteOrderStruc();
		sos.setOrderId(gso.getOrderId());
		sos.setLotteryid(gso.getLotteryid());
		sos.setLotteryName(gso.getLotteryName());
		sos.setOrderCode(gso.getOrderCode());
		sos.setIssueCode(gso.getIssueCode());
		sos.setWebIssueCode(gso.getWebIssueCode());
		sos.setTotamount(gso.getTotamount());
		sos.setUserId(gso.getUserId());
		sos.setAccount(gso.getAccount());
		sos.setSaleTime(gso.getSaleTime().getTime());
		sos.setStatus(convertWarnOrderStatus(gso.getStatus()));
		sos.setUserStatus(gso.getUserStatus());
		return sos;
	}

	private static String convertWarnOrderStatus(Integer status) {
		switch (status) {
		case 0:
			return "待审核";
		case 1:
			return "已审核";
		case 2:
			return "未通过审核";
		default:
			return "";
		}
	}

	public static UserAwardListByLoginStruc userAwardGroupEntity2UserAwardListByLoginStruc(
			UserBetAwardGroupEntity entity) {
		UserAwardListByLoginStruc uals = new UserAwardListByLoginStruc();
		uals.setLotteryId(entity.getLotteryid());
		uals.setAwardGroupId(entity.getAwardGroupid());
		return uals;
	}

	public static SlipsStruc gameSlip2SlipsStruc(GameSlip gs) {
		SlipsStruc ss = new SlipsStruc();
		String[] betTypeCodes = gs.getBetTypeCode().split("_");
		if (null != betTypeCodes && betTypeCodes.length > 0) {
			ss.setBetMethodCode(Integer.parseInt(betTypeCodes[2]));
			ss.setGameGroupCode(Integer.parseInt(betTypeCodes[0]));
			ss.setGameSetCode(Integer.parseInt(betTypeCodes[1]));
		}
		ss.setBetDetail(gs.getBetDetail());
		ss.setMoneyMode(gs.getMoneyMode().intValue());
		ss.setMultiple(gs.getMultiple().intValue());
		ss.setTotbets(gs.getTotbets().intValue());
		ss.setStatus(gs.getStatus());
		ss.setTotamount(gs.getTotamount());
		ss.setTotbets(gs.getTotbets().intValue());
		return ss;
	}

	public static List<SlipsStruc> pamePackageItems2SlipsStruc(List<GamePackageItem> items) {
		List<SlipsStruc> slipsStrucs = new ArrayList<SlipsStruc>();
		for (GamePackageItem item : items) {
			SlipsStruc ss = new SlipsStruc();

			if (item.getFileMode() == 1) {
				File file = new File(item.getBetDetail());
				String content = null;
				try {
					content = FileUtils.readFileToString(file);
					ss.setBetDetail(content);
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else {
				ss.setBetDetail(item.getBetDetail());
			}
			String[] betTypeCodes = item.getBetTypeCode().split("_");
			if (null != betTypeCodes && betTypeCodes.length > 0) {
				ss.setBetMethodCode(betTypeCodes[2].equals("null") ? null : Integer.parseInt(betTypeCodes[2]));
				ss.setGameGroupCode(Integer.parseInt(betTypeCodes[0]));
				ss.setGameSetCode(Integer.parseInt(betTypeCodes[1]));
			}
			ss.setMoneyMode(item.getMoneyMode());
			ss.setTotbets(item.getTotbets().intValue());
			ss.setMultiple(item.getMultiple());
			slipsStrucs.add(ss);
		}
		return slipsStrucs;
	}

	public static SingleIssueIncomeAndExpenseStruc SingleIssueIncomeAndExpenseEntity2SingleIssueIncomeAndExpenseStruc(
			SingleIssueIncomeAndExpenseEntity e) {
		SingleIssueIncomeAndExpenseStruc s = new SingleIssueIncomeAndExpenseStruc();
		s.setActualSoldAmount(e.getActualSoldAmount());
		s.setCanceledAmount(e.getCanceledAmount());
		s.setCancellationsFee(e.getCancellationsFee());
		s.setDistributedBonuses(e.getDistributedBonuses());
		s.setDistributedRetPoint(e.getDistributedRetPoint());
		s.setLotteryName(e.getLotteryName());
		s.setRevenue(e.getRevenue());
		s.setSaleTimePeriod(e.getSaleTimePeriod());
		s.setToDistributeBonuses(e.getToDistributeBonuses());
		s.setWebIssueCode(e.getWebIssueCode());
		return s;
	}

	public static ECReceivesNumbersDTO ecReceivesNumbersRequest2ECReceivesNumbersDTO(ECReceivesNumbersRequest request) {
		ECReceivesNumbersDTO entity = new ECReceivesNumbersDTO();

		entity.setCustomer(request.getCustomer());
		entity.setDrawingTime(request.getDrawingTime());
		entity.setEarliestTime(request.getEarliestTime());
		entity.setIssue(request.getIssue());
		entity.setLottery(request.getLottery());
		entity.setNumber(request.getNumber());
		entity.setRecordId(request.getRecordId());
		entity.setStopSaleTime(request.getStopSaleTime());
		entity.setTime(request.getTime());
		entity.setVerifiedTime(request.getVerifiedTime());

		return entity;
	}

	public static GameOrderResponseDTO convertGameOrderToDTO(GameOrder gameOrder) {
		GameOrderResponseDTO gameOrderDTo = new GameOrderResponseDTO();
		gameOrderDTo.setGameIssueCode(gameOrder.getGameIssue().getIssueCode());
		gameOrderDTo.setLotteryName(gameOrder.getLottery().getLotteryName());
		List<GameSlipResponseDTO> slipResonseDTOList = new ArrayList<GameSlipResponseDTO>();
		for (com.winterframework.firefrog.game.entity.GameSlip slipTemp : gameOrder.getSlipList()) {
			slipTemp.setLockPoints(slipTemp.getLockPoints());
			slipResonseDTOList.add(new GameSlipResponseDTO(slipTemp));
		}

		gameOrderDTo.setSlipResonseDTOList(slipResonseDTOList);
		gameOrderDTo.setTotalAmount(gameOrder.getTotalAmount());
		gameOrderDTo.setWebIssueCode(gameOrder.getGameIssue().getWebIssueCode());
		gameOrderDTo.setIsTrace(0);
		gameOrderDTo.setStopMode(0);
		gameOrderDTo.setStopParms(0);
		gameOrderDTo.setAwardGroupId(gameOrder.getAwardGroupId());
		List<GamePlanParm> gamePlanParm = new ArrayList<GamePlanParm>();
		GamePlanParm temp = new GamePlanParm();
		temp.setIssueCode(gameOrder.getGameIssue().getIssueCode());
		temp.setMultiple(1);
		temp.setNumber(gameOrder.getGameIssue().getWebIssueCode());
		gamePlanParm.add(temp);
		gameOrderDTo.setGamePlanParm(gamePlanParm);
		return gameOrderDTo;
	}

	public static GameOrderResponseDTO convertGameOrderToPlanDTO(GameOrder gameOrder) throws Exception {
		GameOrderResponseDTO gameOrderDTo = new GameOrderResponseDTO();
		gameOrderDTo.setGameIssueCode(gameOrder.getGameIssue().getIssueCode());
		gameOrderDTo.setLotteryName(gameOrder.getLottery().getLotteryName());
		List<GameSlipResponseDTO> slipResonseDTOList = new ArrayList<GameSlipResponseDTO>();
		GamePlan gamePlan = VOConvert.convertGameOrder2GamePlan(gameOrder);
		for (com.winterframework.firefrog.game.entity.GameSlip slipTemp : gameOrder.getSlipList()) {
			if (slipTemp.getIssueCode().getIssueCode().longValue() == gamePlan.getStartIsuueCode().longValue()) {
				slipTemp.setLockPoints(slipTemp.getLockPoints());
				slipResonseDTOList.add(new GameSlipResponseDTO(slipTemp));
			}
		}
		gameOrderDTo.setSlipResonseDTOList(slipResonseDTOList);
		gameOrderDTo.setTotalAmount(gameOrder.getGamePackage().getPackageAmount());
		gameOrderDTo.setWebIssueCode(gameOrder.getGameIssue().getWebIssueCode());

		gameOrderDTo.setIsTrace(1);
		gameOrderDTo.setStopMode(gamePlan.getStopMode().getValue());
		gameOrderDTo.setStopParms(gamePlan.getStopParms().intValue());
		List<GamePlanDetail> planDetails = gamePlan.getGamePlanDetails();
		List<GamePlanParm> gamePlanParm = new ArrayList<GamePlanParm>();
		for (GamePlanDetail gamePlanDetail : planDetails) {
			GamePlanParm temp = new GamePlanParm();
			temp.setIssueCode(gamePlanDetail.getGameIssue().getIssueCode());
			temp.setMultiple(gamePlanDetail.getMutiple().intValue());
			temp.setNumber(gamePlanDetail.getGameIssue().getWebIssueCode());
			gamePlanParm.add(temp);
		}
		gameOrderDTo.setGamePlanParm(gamePlanParm);
		return gameOrderDTo;
	}

	public static GameDrawResultStruc gameDrawResult2Struc(GameDrawResult drawResult) {
		GameDrawResultStruc struc = new GameDrawResultStruc();
		struc.setLotteryId(drawResult.getLotteryid());
		struc.setIssueCode(drawResult.getIssueCode());
		struc.setWebIssueCode(drawResult.getWebIssueCode());
		struc.setType(drawResult.getType());
		struc.setNumber(drawResult.getNumberRecord());
		struc.setDrawTime(drawResult.getOpenDrawTime());
		return struc;
	}

	public static GameUserAward userAwardStruc2UserAward(GameUserAwardStruc userAwardStruc) {
		GameUserAward userAward = new GameUserAward();
		userAward.setId(userAwardStruc.getId());
		userAward.setLotteryId(userAwardStruc.getLotteryId());
		userAward.setAwardGroupId(userAwardStruc.getAwardGroupId());
		userAward.setBetTypeCode(userAwardStruc.getBetTypeCode());
		userAward.setStatus(userAwardStruc.getStatus());
		userAward.setRetValue(userAwardStruc.getRetValue());
		userAward.setUserId(userAwardStruc.getUserId());
		userAward.setCreateTime(userAwardStruc.getCreateTime());
		userAward.setUpdateTime(userAwardStruc.getUpdateTime());
		return userAward;
	}
	public static GameSeriesListStruc gameSeries2GameSeriesListStruc(GameSeries gameSeries) {
		GameSeriesListStruc gameSeriesListStruc = new GameSeriesListStruc();
		gameSeriesListStruc.setLotteryTypeCode(gameSeries.getLotteryTypeCode());
		gameSeriesListStruc.setLotteryTypeName(gameSeries.getLotteryTypeName());
		gameSeriesListStruc.setLotterySeriesCode(gameSeries.getLotterySeriesCode());
		gameSeriesListStruc.setLotterySeriesName(gameSeries.getLotterySeriesName()); 
		
		return gameSeriesListStruc;		 
	}
}