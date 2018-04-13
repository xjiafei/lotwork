package com.winterframework.firefrog.game.dao.vo;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.firefrog.game.entity.Channel;
import com.winterframework.firefrog.game.entity.ChannelType;
import com.winterframework.firefrog.game.entity.GameAwardEntity;
import com.winterframework.firefrog.game.entity.GameAwardGroupEntity;
import com.winterframework.firefrog.game.entity.GameBetDetailTotAmountEntity;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameIssueRuleEntity;
import com.winterframework.firefrog.game.entity.GameIssueRuleEntity.RuleType;
import com.winterframework.firefrog.game.entity.GameIssueTemplateEntity;
import com.winterframework.firefrog.game.entity.GameMultipleEntity;
import com.winterframework.firefrog.game.entity.GameOrder;
import com.winterframework.firefrog.game.entity.GamePlanDetail;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.entity.GameWarnIssueEntity;
import com.winterframework.firefrog.game.entity.GameWarnIssueEntity.GameWarnStatus;
import com.winterframework.firefrog.game.entity.GameWarnIssueEntity.ReadFlag;
import com.winterframework.firefrog.game.entity.GameWarnOrderEntity;
import com.winterframework.firefrog.game.entity.GameWarnOrderEntity.GameWarnType;
import com.winterframework.firefrog.game.entity.GameWarnOrderEntity.ParentType;
import com.winterframework.firefrog.game.entity.Lottery;
import com.winterframework.firefrog.game.entity.PackageItemAssist;
import com.winterframework.firefrog.game.entity.Points;
import com.winterframework.firefrog.game.entity.SingleIssueIncomeAndExpenseEntity;
import com.winterframework.firefrog.game.entity.SlipItemAssist;
import com.winterframework.firefrog.game.entity.WinsReport;
import com.winterframework.firefrog.game.util.LHCUtil;
import com.winterframework.firefrog.game.web.dto.VedioStruc;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;
import com.winterframework.firefrog.user.entity.User;

/** 
* @ClassName: VOConverter 
* @Description: 
* @author david,richard,denny
* @date 2013-7-22 下午3:19:14 
*  
*/
public class VOConverter {

	public static com.winterframework.firefrog.game.dao.vo.GamePackage gpEntity2gpVo(
			com.winterframework.firefrog.game.entity.GamePackage gamePackageEntity) {
		com.winterframework.firefrog.game.dao.vo.GamePackage gamePackage = new com.winterframework.firefrog.game.dao.vo.GamePackage();
		User user = new User();
		user.setId(gamePackageEntity.getUser().getId());
		gamePackage.setUserid(gamePackageEntity.getUser().getId());

		gamePackage.setIssueCode(gamePackageEntity.getGameIssue().getIssueCode());
		gamePackage.setLotteryid(gamePackageEntity.getLottery().getLotteryId());
		gamePackage.setPackageCode(gamePackageEntity.getPackageCode());
		gamePackage.setType(gamePackageEntity.getType().getValue());
		gamePackage.setSaleTime(gamePackageEntity.getSaleTime());
		gamePackage.setCancelTime(gamePackageEntity.getCancelTime());
		gamePackage.setUserip(gamePackageEntity.getUserip());
		gamePackage.setServerip(gamePackageEntity.getServerip());
		gamePackage.setPackageAmount(gamePackageEntity.getPackageAmount());
		gamePackage.setFileMode(gamePackageEntity.getFileMode().getValue());
		//		gamePackage.setPlanId(gamePackageEntity.getPlanId()); //新方案实例没有PlanId
		//先写固定的
		gamePackage.setChannelId(Long.valueOf(gamePackageEntity.getChannel().getChannelType().getValue()));
		gamePackage.setChannelVersion(gamePackageEntity.getChannel().getVersion());
		gamePackage.setRetUserChain(gamePackageEntity.getRetUserChain());
		gamePackage.setActivityType(gamePackageEntity.getActivityType());
		return gamePackage;
	}

	public static GamePackageItem gpiEntity2gpiVo(
			com.winterframework.firefrog.game.entity.GamePackageItem gamePackageItemEntity) {
		GamePackageItem gamePackageItem = new GamePackageItem();
		gamePackageItem.setPackageid(gamePackageItemEntity.getGamePackage().getPackageId());
		gamePackageItem.setBetTypeCode(gamePackageItemEntity.getGameBetType().getBetTypeCode());
		gamePackageItem.setMoneyMode(gamePackageItemEntity.getMoneyMode().getValue());
		gamePackageItem.setTotbets(gamePackageItemEntity.getTotbets());
		gamePackageItem.setTotamount(gamePackageItemEntity.getTotamount());
		if (gamePackageItemEntity.getGamePackage() != null
				&& gamePackageItemEntity.getGamePackage().getLottery() != null
				&& (gamePackageItemEntity.getGamePackage().getLottery().getLotteryId() == 99601||gamePackageItemEntity.getGamePackage().getLottery().getLotteryId() == 99602
				||gamePackageItemEntity.getGamePackage().getLottery().getLotteryId() == 99603)) {
			gamePackageItem.setMultiple(Integer.parseInt(String.valueOf(gamePackageItemEntity.getTotamount() / 2000)));
		} else {
			gamePackageItem.setMultiple(gamePackageItemEntity.getMultiple());
		}
		gamePackageItem.setBetDetail(gamePackageItemEntity.getBetDetail());
		gamePackageItem.setCreateTime(gamePackageItemEntity.getCreateTime());
		//TODO 待确认该属性
		//		gamePackageItem.setItemAmount(gamePackageItemEntity.getItemAmount());
		gamePackageItem.setFileMode(gamePackageItemEntity.getFileMode().getValue());
		gamePackageItem.setRetPointChain(gamePackageItemEntity.getRetPointChain());
		gamePackageItem.setRetPoint(gamePackageItemEntity.getRetPoint());
		gamePackageItem.setAwardMode(gamePackageItemEntity.getAwardMode());
		gamePackageItem.setRetAward(gamePackageItemEntity.getRetAward());
		gamePackageItem.setDiamondAmount(gamePackageItemEntity.getDiamondAmount());
		return gamePackageItem;
	}

	public static com.winterframework.firefrog.game.dao.vo.GameOrder goe2gov(GameOrder gameOrder) {
		com.winterframework.firefrog.game.dao.vo.GameOrder gameOrderVo = new com.winterframework.firefrog.game.dao.vo.GameOrder();
		gameOrderVo.setParentid(gameOrder.getGamePackage().getPackageId());
		gameOrderVo.setUserid(gameOrder.getGamePackage().getUser().getId());
		gameOrderVo.setIssueCode(gameOrder.getGameIssue().getIssueCode());
		gameOrderVo.setWebIssueCode(gameOrder.getGameIssue().getWebIssueCode());
		gameOrderVo.setLotteryid(gameOrder.getLottery().getLotteryId());
		gameOrderVo.setTotamount(gameOrder.getTotalAmount());
		gameOrderVo.setStatus(gameOrder.getStatus().getValue());
		gameOrderVo.setOrderTime(new Date());
		//		gameOrderVo.setCalculateWinTime(null);
		gameOrderVo.setSaleTime(gameOrder.getSaleTime());
		//		gameOrderVo.setCancelTime(null);
		gameOrderVo.setCancelModes(new Long(gameOrder.getCancelModes().getValue()).intValue());
		//		gameOrderVo.setOrderCode(null);
		gameOrderVo.setFileMode(gameOrder.getFileMode().getValue());
		gameOrderVo.setPlanId(gameOrder.getGamePlanId());
		gameOrderVo.setPlanDetailId(gameOrder.getGamePlanDetailId());
		gameOrderVo.setLastIssueCode(gameOrder.getLastIssueCode());
		gameOrderVo.setCancelFee(gameOrder.getCancelFee());
		gameOrderVo.setAwardGroupId(gameOrder.getAwardGroupId());
		return gameOrderVo;
	}

	/** 
	* @Title: gameSlipEntity2GameSlipVo 
	* @Description: 将gameSlip实体转换为VO对象
	* @param gameSlipEntity
	* @param gameOrder
	* @return
	*/
	public static com.winterframework.firefrog.game.dao.vo.GameSlip gameSlipEntity2GameSlipVo(GameSlip gameSlipEntity,
			com.winterframework.firefrog.game.dao.vo.GameOrder gameOrder) {
		com.winterframework.firefrog.game.dao.vo.GameSlip gameSlip = new com.winterframework.firefrog.game.dao.vo.GameSlip();
		gameSlip.setOrderid(gameOrder.getId());
		gameSlip.setUserid(gameOrder.getUserid());
		gameSlip.setIssueCode(gameOrder.getIssueCode());
		gameSlip.setLotteryid(gameOrder.getLotteryid());
		gameSlip.setBetTypeCode(gameSlipEntity.getGameBetType().getBetTypeCode());
		gameSlip.setMoneyMode(Long.valueOf(gameSlipEntity.getMoneyMode().getValue()));
		gameSlip.setTotbets(gameSlipEntity.getTotalBet());
		gameSlip.setTotamount(gameSlipEntity.getTotalAmount());
		if (gameOrder.getLotteryid() == 99601||gameOrder.getLotteryid() == 99602||gameOrder.getLotteryid() == 99603) {
			gameSlip.setMultiple(new Long(gameSlipEntity.getTotalAmount() / 2000));
		} else {
			gameSlip.setMultiple(new Long(gameSlipEntity.getMultiple()));
		}
		gameSlip.setBetDetail(gameSlipEntity.getBetDetail());
		gameSlip.setFileMode(gameSlipEntity.getFileMode());
		gameSlip.setAwardMode(gameSlipEntity.getAwardMode());
		gameSlip.setRetPoint(gameSlipEntity.getRetPoint());
		gameSlip.setRetAward(gameSlipEntity.getRetAward());
		gameSlip.setPackageItemId(gameSlipEntity.getPackageItemId());
		//在保存的时候设置这两个属性
		//gameSlip.setEvaluateWin(gameSlipEntity.getEvaluateWin());
		//gameSlip.setMutiAward(mutiAward)
		gameSlip.setStatus(gameSlipEntity.getGameSlipStatus().getValue());
		gameSlip.setCreateTime(gameSlipEntity.getCrateTime());
		gameSlip.setDiamondAmount(gameSlipEntity.getDiamondAmount());
		return gameSlip;

	}

	/**
	 * 將 VO 的 GameOrder 轉換成 Entity 的 GameOrder。<br>
	 * 含 GameIssue, GamePackage。
	 * @param gov
	 * @return
	 */
	public static GameOrder gov2goe(com.winterframework.firefrog.game.dao.vo.GameOrder gov) {
		GameOrder goe = new GameOrder();
		goe.setOrderCode(gov.getOrderCode());
		Lottery lottery = new Lottery();
		lottery.setLotteryId(gov.getLotteryid());
		goe.setLottery(lottery);

		GameIssueEntity gameIssue = new GameIssueEntity();
		gameIssue.setIssueCode(gov.getIssueCode());
		gameIssue.setWebIssueCode(gov.getWebIssueCode());

		goe.setGameIssue(gameIssue);
		goe.setTotalAmount(gov.getTotamount());
		goe.setCancelModes(EnumTypeConverts.orderCancelModes2Enum(Long.valueOf(gov.getCancelModes())));
		goe.setId(gov.getId());
		goe.setNumberRecord(gov.getNumberRecord());
		goe.setOrderCode(gov.getOrderCode());
		
		com.winterframework.firefrog.game.entity.GamePackage gamePackage = new com.winterframework.firefrog.game.entity.GamePackage();
		gamePackage.setPackageId(gov.getParentid());
		gamePackage.setPackageAmount(gov.getTotamount());
		User user = new User();
		user.setId(gov.getUserid());
		gamePackage.setUser(user);
		goe.setStatus(EnumTypeConverts.orderStatus3Enum(new Long(gov.getStatus())));
		goe.setGamePackage(gamePackage);
		if (null != gov.getParentType()) {
			goe.setParentType(EnumTypeConverts.convertType2Emun(Long.valueOf(gov.getParentType())));
		}
		goe.setSaleTime(gov.getSaleTime());
		goe.setStatus(EnumTypeConverts.orderStatus3Enum(Long.valueOf(gov.getStatus())));
		goe.setTotalAmount(gov.getTotamount());
		goe.setTotalWin(gov.getTotalWin());
		goe.setEndCanCancelTime(gov.getEndCanCancelTime());
		goe.setNumberRecord(gov.getNumberRecord());
		goe.setAwardGroupId(gov.getAwardGroupId());
		goe.setTotDiamondWin(gov.getTotDiamondWin());
		goe.setDiamondMultiple(gov.getDiamondMultiple());
		goe.setParentid(gov.getParentid());
		return goe;
	}

	public static GameSlip gameSlip2GameOrderDetail(com.winterframework.firefrog.game.dao.vo.GameSlip gs) {
		GameSlip god = new GameSlip();
		String[] betTypeCodes = gs.getBetTypeCode().split("_");
		if (null != betTypeCodes && betTypeCodes.length > 0) {
			god.setGameBetType(new GameBetType(Integer.parseInt(betTypeCodes[0]), Integer.parseInt(betTypeCodes[1]),
					betTypeCodes[2].equals("null") ? null : Integer.parseInt(betTypeCodes[2])));
		}
		god.setId(gs.getId());
		god.setTotalBet(gs.getTotbets());
		god.setMultiple(gs.getMultiple().intValue());
		god.setTotalAmount(gs.getTotamount());
		god.setMoneyMode(EnumTypeConverts.convertMoneyMode(gs.getMoneyMode().intValue()));
		god.setGameSlipStatus(EnumTypeConverts.convertGameSlipStatus(gs.getStatus()));
		god.setEvaluateWin(gs.getEvaluateWin());
		god.setWinNumber(gs.getWinNumber());
		god.setSingleWin(gs.getSingleWin());
		god.setAwardMode(gs.getAwardMode());
		god.setSingleWinDown(gs.getSingleWinDown());
		god.setRetPoint(gs.getRetPoint());
		god.setRetAward(gs.getRetAward());
		god.setMutlAward(gs.getMutiAward().intValue());
		if (gs.getFileMode() == 1) {
			// 文件模式将文件内容读取出来，读取方式直接读取有误问题？
			File file = new File(gs.getBetDetail());
			String content = null;
			try {
				content = FileUtils.readFileToString(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			god.setBetDetail(content);
		} else {
			god.setBetDetail(gs.getBetDetail());
		}

		Lottery lottery = new Lottery();
		lottery.setLotteryId(gs.getLotteryid());
		GameIssueEntity issueEntity = new GameIssueEntity(gs.getIssueCode());
		issueEntity.setLottery(lottery);
		god.setIssueCode(issueEntity);
		//		god.setSaleTime(gs.getSaleTime());
		god.setDiamondAmount(gs.getDiamondAmount());
		god.setDiamondWin(gs.getDiamondWin());
		god.setOrderId(gs.getOrderid());
		return god;
	}

	/** 
	* @Title: gamePlanV2GamePlanE 
	* @Description: Gameplan值对象转换为实体Bean
	* @param @param gp
	* @param @return    设定文件 
	* @return com.winterframework.firefrog.game.entity.GamePlan    返回类型 
	* @throws 
	*/
	public static com.winterframework.firefrog.game.entity.GamePlan gamePlanV2GamePlanE(
			com.winterframework.firefrog.game.dao.vo.GamePlan gpv) {
		com.winterframework.firefrog.game.entity.GamePlan gpe = new com.winterframework.firefrog.game.entity.GamePlan();

		User user = new User();
		user.setId(gpv.getUserId());
		gpe.setUser(user);
		Lottery lottery = new Lottery();
		long lotteryid = gpv.getLotteryid();
		lottery.setLotteryId(lotteryid);
		lottery.setLotteryName(gpv.getLotteryName());
		gpe.setLottery(lottery);
		gpe.setStatus(EnumTypeConverts.convertStatus2GamePlanStatus(gpv.getStatus()));
		gpe.setStartIsuueCode(gpv.getStartIsuueCode());
		gpe.setStartWebIssue(gpv.getStartWebIssue());

		gpe.setPlanCode(gpv.getPlanCode());
		gpe.setFinishIssue(gpv.getFinishIssue());
		gpe.setTotalIssue(gpv.getTotalIssue());
		gpe.setTotamount(gpv.getTotalAmount());
		gpe.setSoldAmount(gpv.getSoldAmount());
		gpe.setStopMode(EnumTypeConverts.getStopModeByValue(gpv.getStopMode()));
		gpe.setStopParms(gpv.getStopParms());
		gpe.setSaleTime(gpv.getCreateTime());
		gpe.setPackageId(gpv.getPackageId());
		gpe.setId(gpv.getId());
		gpe.setWinAmount(gpv.getWinAmount());

		return gpe;
	}

	/** 
	* @Title: gpdv2gpde 
	* @Description: 游戏计划详情值对象转换为游戏计划实体Bean
	* @param @param gpdv
	* @param @return    设定文件 
	* @return GamePlanDetail    返回类型 
	* @throws 
	*/
	public static GamePlanDetail gpdv2gpde(com.winterframework.firefrog.game.dao.vo.GamePlanDetail gpdv) {
		GamePlanDetail gpde = new GamePlanDetail();
		com.winterframework.firefrog.game.entity.GamePlan gamePlan = new com.winterframework.firefrog.game.entity.GamePlan();
		gamePlan.setPackageId(gpdv.getPlanid());
		gpde.setId(gpdv.getId());
		gpde.setGamePlan(gamePlan);

		GameIssueEntity gameIssueEntity = new GameIssueEntity();

		gameIssueEntity.setIssueCode(gpdv.getIssueCode());
		gameIssueEntity.setWebIssueCode(gpdv.getWebIssueCode());
		gpde.setGameIssue(gameIssueEntity);

		gpde.setMutiple(gpdv.getMutiple());
		gpde.setDetailStatus(EnumTypeConverts.convertIntegerStatus2GamePlanDetailStatus(gpdv.getStatus()));
		gpde.setCreateTime(gpdv.getCreateTime());
		gpde.setCancelTime(gpdv.getCancelTime());
		gpde.setTotamount(new BigDecimal(gpdv.getTotamount()));
		gpde.setCancelFee(gpdv.getCancelFee());
		//		gpde.setRetPointChain(gpdv.getRetPointChain()); // 20140402新订单结构无此内容。
		//		gpde.setRetUserChain(gpdv.getRetUserChain());

		return gpde;
	}

	public static GameIssueEntity gameIssue2GameIssueEntity(GameIssue gameIssue) {
		if (gameIssue == null) {
			return new GameIssueEntity();
		}
		GameIssueEntity gie = new GameIssueEntity();
		gie.setCreateTime(gameIssue.getCreateTime());
		gie.setFactionDrawTime(gameIssue.getFactionDrawTime());
		gie.setId(gameIssue.getId());
		gie.setIssueCode(gameIssue.getIssueCode());
		Lottery lottery = new Lottery();
		lottery.setLotteryId(gameIssue.getLotteryid());
		gie.setLottery(lottery);
		gie.setOpenDrawTime(gameIssue.getOpenDrawTime());
		gie.setRecivceDrawTime(gameIssue.getRecivceDrawTime());
		gie.setPeriodStatus(EnumTypeConverts.convertStatus2GameIssuePeriodStatus(gameIssue.getPeriodStatus()));
		gie.setSaleEndTime(gameIssue.getSaleEndTime());
		gie.setSaleStartTime(gameIssue.getSaleStartTime());
		gie.setStatus(EnumTypeConverts.convertStatus2GameIssueStatus(gameIssue.getStatus()));
		gie.setUpdateTime(gameIssue.getUpdateTime());
		gie.setWebIssueCode(gameIssue.getWebIssueCode());
		gie.setPauseStatus(EnumTypeConverts.convertStatus2PauseStatus(gameIssue.getPauseStatus()));
		gie.setEventStatus(EnumTypeConverts.convertStatus2EventStatus(gameIssue.getEventStatus()));
		gie.setSequence(gameIssue.getSequence());
		gie.setPlanFinishStatus(EnumTypeConverts.convertStatus2PlanFinishStatus(gameIssue.getPlanFinishStatus()));
		gie.setLastIssueStop(EnumTypeConverts.convertStatus2LastIssueStop(gameIssue.getLastIssueStop()));
		gie.setNumberRecord(gameIssue.getNumberRecord());
		gie.setNumberUpdateCount(gameIssue.getNumberUpdateCount());
		gie.setNumberUpdateOperator(gameIssue.getNumberUpdateOperator());
		gie.setNumberUpdateTime(gameIssue.getNumberUpdateTime());
		gie.setLastIssue(gameIssue.getLastIssue());
		gie.setRealLastIssue(gameIssue.getRealLastIssue());
		gie.setIsReported(gameIssue.getIsReported());
		gie.setIsTrend(gameIssue.getIsTrend());
		gie.setAwardStruct(gameIssue.getAwardStruct());
		gie.setAdminCanCancelEndTime(gameIssue.getAdminEndCancelTime());
		gie.setIssuewarnExceptionTime(gameIssue.getIssuewarnExceptionTime());
		return gie;
	}

	/**
	 * 
	* @Title: convertGameAwardGroup2Entity 
	* @Description: 转换Vo为Entity 
	* @param gameAwardGroup
	* @return
	 */
	public static GameAwardGroupEntity convertGameAwardGroup2Entity(GameAwardGroup gameAwardGroup) {

		GameAwardGroupEntity entity = new GameAwardGroupEntity();

		entity.setId(gameAwardGroup.getId());
		entity.setAwardName(gameAwardGroup.getAwardName());
		entity.setCreateTime(gameAwardGroup.getCreateTime());
		entity.setDirectRet(gameAwardGroup.getDirectRet());

		Lottery lottery = new Lottery();
		lottery.setId(gameAwardGroup.getLotteryid());
		lottery.setLotteryId(gameAwardGroup.getLotteryid());
		if (null != gameAwardGroup.getLotteryName()) {
			lottery.setLotteryName(gameAwardGroup.getLotteryName());
		}

		if (null != gameAwardGroup.getLotterySeriesName()) {
			lottery.setLotterySeriesName(gameAwardGroup.getLotterySeriesName());
		}
		if (null != gameAwardGroup.getLotterySeriesCode()) {
			lottery.setLotterySeriesCode(gameAwardGroup.getLotterySeriesCode().longValue());
		}

		entity.setLottery(lottery);
		entity.setStatus(EnumTypeConverts.convertValue2Enum(gameAwardGroup.getStatus()));
		entity.setSysAwardGroup(EnumTypeConverts.convertValueToEnum(gameAwardGroup.getSysAwardGroup()));
		entity.setThreeoneRet(gameAwardGroup.getThreeoneRet());
		entity.setSuperRet(gameAwardGroup.getSuperRet());
		entity.setLhcYear(gameAwardGroup.getLhcYear());
		entity.setLhcColor(gameAwardGroup.getLhcColor());		
		entity.setLhcFlatcode(gameAwardGroup.getLhcFlatcode());
		entity.setLhcHalfwave(gameAwardGroup.getLhcHalfwave());
		entity.setLhcOneyear(gameAwardGroup.getLhcOneyear());
		entity.setLhcNotin(gameAwardGroup.getLhcNotin());
		entity.setLhcContinuein23(gameAwardGroup.getLhcContinuein23());
		entity.setLhcContinuein4(gameAwardGroup.getLhcContinuein4());
		entity.setLhcContinuein5(gameAwardGroup.getLhcContinuein5());
		entity.setLhcContinuenotin23(gameAwardGroup.getLhcContinuenotin23());
		entity.setLhcContinuenotin4(gameAwardGroup.getLhcContinuenotin4());
		entity.setLhcContinuenotin5(gameAwardGroup.getLhcContinuenotin5());
		entity.setLhcContinuecode(gameAwardGroup.getLhcContinuecode());
		
		if (null != gameAwardGroup.getUpdateTime()) {
			entity.setUpdateTime(gameAwardGroup.getUpdateTime());
		}
		entity.setSbThreeoneRet(gameAwardGroup.getSbThreeoneRet());

		return entity;
	}

	public static GameAwardGroupEntity convertGameAwardGroupCheck2Entity(GameAwardGroupCheck group) {

		GameAwardGroupEntity entity = new GameAwardGroupEntity();

		entity.setId(group.getId());
		entity.setAwardName(group.getAwardName());
		entity.setCreateTime(group.getCreateTime());
		entity.setDirectRet(group.getDirectRet());

		Lottery lottery = new Lottery();
		lottery.setId(group.getLotteryid());

		entity.setLottery(lottery);
		entity.setStatus(EnumTypeConverts.convertValue2Enum(group.getStatus()));
		entity.setSysAwardGroup(EnumTypeConverts.convertValueToEnum(group.getSysAwardGroup()));
		entity.setThreeoneRet(group.getThreeoneRet());

		if (null != group.getUpdateTime()) {
			entity.setUpdateTime(group.getUpdateTime());
		}

		entity.setAwardGroupid(group.getAwardGroupid());
		return entity;
	}

	/**
	 * 
	* @Title: convertGameAwardGroup2Entity 
	* @Description: 转换Vo为Entity 
	* @param gameAwardGroup
	* @return
	 */
	public static GameAwardGroupEntity convertGameAwardGroup2Entity(GameAwardGroup gameAwardGroup,
			List<GameAwardEntity> list) {

		GameAwardGroupEntity entity = new GameAwardGroupEntity();

		entity.setId(gameAwardGroup.getId());
		entity.setAwardName(gameAwardGroup.getAwardName());
		entity.setCreateTime(gameAwardGroup.getCreateTime());
		entity.setDirectRet(gameAwardGroup.getDirectRet());

		Lottery lottery = new Lottery();
		lottery.setId(gameAwardGroup.getLotteryid());

		entity.setLottery(lottery);
		entity.setStatus(EnumTypeConverts.convertValue2Enum(gameAwardGroup.getStatus()));
		entity.setSysAwardGroup(EnumTypeConverts.convertValueToEnum(gameAwardGroup.getSysAwardGroup()));
		entity.setThreeoneRet(gameAwardGroup.getThreeoneRet());

		if (null != gameAwardGroup.getUpdateTime()) {
			entity.setUpdateTime(gameAwardGroup.getUpdateTime());
		}

		if (null != list && list.size() > 0) {
			entity.setGameAwards(list);
		}

		return entity;
	}

	/**
	 * 
	* @Title: convertGameAward2Entity 
	* @Description:转换Vo2Entity
	* @param gameAward
	* @return
	 */
	public static GameAwardEntity convertGameAward2Entity(GameAward gameAward, GameAwardGroup awardGroup,
			Float miniLotteryProfit, GameBettypeStatus gameBettypeStatus) {

		GameAwardEntity entity = new GameAwardEntity();

		entity.setActualBonus(new BigDecimal(gameAward.getActualBonus()));
		entity.setActualBonusDown(gameAward.getActualBonusDown() != null ? new BigDecimal(gameAward
				.getActualBonusDown()) : null);
		entity.setLhcTheoryBonus(new BigDecimal(gameAward.getLhcTheoryBonus()));
		GameBetType betType = new GameBetType();
		entity.setAwardGroupId(gameAward.getAwardGroupId());
		betType.setBetMethodCode(gameAward.findBetMethodCode());
		betType.setGameGroupCode(gameAward.findGameGroupCode());
		betType.setGameSetCode(gameAward.findGameSetCode());
		betType.setMethodType(gameAward.findMethodType());
		betType.setId(gameBettypeStatus != null ? gameBettypeStatus.getId() : 0);
		entity.setGameBetType(betType);
		entity.setRetValue(gameAward.getRetValue());

		entity.setCreateTime(gameAward.getCreateTime());
		entity.setAwardStatus(EnumTypeConverts.convertValue2GameAwardStatus(gameAward.getStatus()));

		Lottery lottery = new Lottery();
		lottery.setLotteryId(awardGroup.getLotteryid());
		lottery.setLotteryName(awardGroup.getAwardName());

		entity.setLottery(lottery);
		entity.setMaxRetValue(gameAward.getMaxRetValue());

		if (null != gameAward.getUpdateTime()) {
			entity.setUpdateTime(gameAward.getUpdateTime());
		}

		if (null != awardGroup.getThreeoneRet()) {
			entity.setThreeoneRet(new BigDecimal(awardGroup.getThreeoneRet()));
		}

		if (null != awardGroup.getDirectRet()) {
			entity.setDirectRet(new BigDecimal(awardGroup.getDirectRet()));
		}
		if (null != awardGroup.getSuperRet()) {
			entity.setSuperRet(new BigDecimal(awardGroup.getSuperRet()));
		}
		if (null != awardGroup.getLhcYear()) {
			entity.setLhcYear(new BigDecimal(awardGroup.getLhcYear()));
		}
		if (null != awardGroup.getLhcColor()) {
			entity.setLhcColor(new BigDecimal(awardGroup.getLhcColor()));
		}		
		if (null != awardGroup.getLhcFlatcode()) {
			entity.setLhcFlatcode(new BigDecimal(awardGroup.getLhcFlatcode()));
		}
		
		if (null != awardGroup.getLhcHalfwave()) {
			entity.setLhcHalfwave(new BigDecimal(awardGroup.getLhcHalfwave()));
		}
		if (null != awardGroup.getLhcOneyear()) {
			entity.setLhcOneyear(new BigDecimal(awardGroup.getLhcOneyear()));
		}
		if (null != awardGroup.getLhcNotin()) {
			entity.setLhcNotin(new BigDecimal(awardGroup.getLhcNotin()));
		}
		if (null != awardGroup.getLhcContinuein23()) {
			entity.setLhcContinuein23(new BigDecimal(awardGroup.getLhcContinuein23()));
		}
		if (null != awardGroup.getLhcContinuein4()) {
			entity.setLhcContinuein4(new BigDecimal(awardGroup.getLhcContinuein4()));
		}
		if (null != awardGroup.getLhcContinuein5()) {
			entity.setLhcContinuein5(new BigDecimal(awardGroup.getLhcContinuein5()));
		}
		if (null != awardGroup.getLhcContinuenotin23()) {
			entity.setLhcContinuenotin23(new BigDecimal(awardGroup.getLhcContinuenotin23()));
		}
		if (null != awardGroup.getLhcContinuenotin4()) {
			entity.setLhcContinuenotin4(new BigDecimal(awardGroup.getLhcContinuenotin4()));
		}
		if (null != awardGroup.getLhcContinuenotin5()) {
			entity.setLhcContinuenotin5(new BigDecimal(awardGroup.getLhcContinuenotin5()));
		}
		if (null != awardGroup.getLhcContinuecode()) {
			entity.setLhcContinuecode(new BigDecimal(awardGroup.getLhcContinuecode()));
		}
		
		entity.setMiniLotteryProfit(new BigDecimal(miniLotteryProfit * 100));
		Long theoryBonus = gameBettypeStatus != null ? gameBettypeStatus.getTheoryBonus() : null;
		if (theoryBonus != null) {
			entity.setTheoryBonus(new BigDecimal(theoryBonus));
		} else {
			entity.setTheoryBonus(new BigDecimal(0));
		}
		entity.setGroupCodeTitle(gameBettypeStatus != null ? gameBettypeStatus.getGroupCodeTitle() : null);
		entity.setSetCodeTitle(gameBettypeStatus != null ? gameBettypeStatus.getSetCodeTitle() : null);
		entity.setMethodCodeTitle(gameBettypeStatus != null ? gameBettypeStatus.getMethodCodeTitle() : null);
		if(gameAward.getLotteryid() == 99701l){//六合彩輔助玩法表示
			entity.setMethodTypeTitle(LHCUtil.LhcCode.getlhcCodeName(gameAward.getLhcCode()));
		} else{
			if (gameAward.findMethodType() != null) {
			entity.setMethodTypeTitle(GameAwardNameUtil.getAssistBonusTypeName(gameAward.findMethodType()));
			}
		}		
		if (StringUtils.isNotBlank(gameAward.getLhcCode())) {
			entity.setLhcCodeTitle(GameAwardNameUtil.getLhcCodeName(gameAward.getLhcCode()));
		}
		
		return entity;
	}

	public static GameIssueRuleEntity gameIssueRule2GameIssueRuleEntity(GameIssueRule gir) {
		GameIssueRuleEntity gameIssueRuleEntity = new GameIssueRuleEntity();
		gameIssueRuleEntity.setCreateTime(gir.getCreateTime());
		gameIssueRuleEntity.setId(gir.getId());
		gameIssueRuleEntity.setIssueRulesName(gir.getIssueRulesName());
		Lottery lottery = new Lottery();
		lottery.setLotteryId(gir.getLotteryid());
		gameIssueRuleEntity.setLottery(lottery);
		gameIssueRuleEntity.setOpenAwardPeriod(gir.getOpenAwardPeriod());
		gameIssueRuleEntity.setRuleEndTime(gir.getRuleEndTime());
		gameIssueRuleEntity.setRuleid(null);
		gameIssueRuleEntity.setRuleStartTime(gir.getRuleStartTime());
		gameIssueRuleEntity.setStatus(gir.getStatus());
		if (null != gir.getUpdateTime()) {
			gameIssueRuleEntity.setUpdateTime(gir.getUpdateTime());
		}
		gameIssueRuleEntity.setRuleType(convertValueToEnumRuleType(gir.getRuleType().intValue()));
		gameIssueRuleEntity.setStopStartTime(gir.getStopStartTime());
		gameIssueRuleEntity.setStopEndTime(gir.getStopEndTime());
		gameIssueRuleEntity.setSeriesIssueCode(gir.getSeriesIssueCode());
		return gameIssueRuleEntity;
	}

	public static RuleType convertValueToEnumRuleType(Integer ruleType) {

		if (null == ruleType) {
			return null;
		}
		switch (ruleType) {
		case 1:
			return RuleType.COMMEN;
		case 2:
			return RuleType.SPECIAL;
		case 3:
			return RuleType.STOP;
		default:
			return null;
		}
	}

	public static GameIssueRuleEntity gameIssueRuleCheck2GameIssueRuleEntity(GameIssueRuleCheck girc) {
		GameIssueRuleEntity gameIssueRuleEntity = new GameIssueRuleEntity();
		gameIssueRuleEntity.setCreateTime(girc.getCreateTime());
		gameIssueRuleEntity.setId(girc.getId());
		gameIssueRuleEntity.setIssueRulesName(girc.getIssueRulesName());
		Lottery lottery = new Lottery();
		lottery.setLotteryId(girc.getLotteryid());
		gameIssueRuleEntity.setLottery(lottery);
		gameIssueRuleEntity.setOpenAwardPeriod(girc.getOpenAwardPeriod());
		gameIssueRuleEntity.setRuleEndTime(girc.getRuleEndTime());
		gameIssueRuleEntity.setRuleid(girc.getRuleid());
		gameIssueRuleEntity.setRuleStartTime(girc.getRuleStartTime());
		gameIssueRuleEntity.setStatus(girc.getStatus());
		gameIssueRuleEntity.setUpdateTime(girc.getUpdateTime());
		gameIssueRuleEntity.setRuleType(convertValueToEnumRuleType(girc.getRuleType().intValue()));
		gameIssueRuleEntity.setRuleid(girc.getRuleid());
		gameIssueRuleEntity.setStopStartTime(girc.getStopStartTime());
		gameIssueRuleEntity.setStopEndTime(girc.getStopEndTime());
		gameIssueRuleEntity.setSeriesIssueCode(girc.getSeriesIssueCode());
		return gameIssueRuleEntity;
	}

	public static GameIssueTemplateEntity gameIssueTemplate2GameIssueTemplateEntity(GameIssueTemplate gameIssueTemplate) {
		GameIssueTemplateEntity gameIssueTemplateEntity = new GameIssueTemplateEntity();
		gameIssueTemplateEntity.setCreateTime(gameIssueTemplate.getCreateTime());
		gameIssueTemplateEntity.setFirstAwardTime(gameIssueTemplate.getFirstAwardTime());
		gameIssueTemplateEntity.setId(gameIssueTemplate.getId());
		gameIssueTemplateEntity.setLastAwardTime(gameIssueTemplate.getLastAwardTime());
		GameIssueRuleEntity gameIssueRuleEntity = new GameIssueRuleEntity();
		gameIssueRuleEntity.setId(gameIssueTemplate.getId());
		gameIssueTemplateEntity.setRule(gameIssueRuleEntity);
		gameIssueTemplateEntity.setSalePeriodTime(gameIssueTemplate.getSalePeriodTime());
		gameIssueTemplateEntity.setSaleStartTime(gameIssueTemplate.getSaleStartTime());
		gameIssueTemplateEntity.setScheduleStopTime(gameIssueTemplate.getScheduleStopTime());
		gameIssueTemplateEntity.setUpdateTime(gameIssueTemplate.getUpdateTime());
		gameIssueTemplateEntity.setSaleTimeSn(gameIssueTemplate.getSaleTimeSn());
		return gameIssueTemplateEntity;
	}

	public static GameIssueTemplateEntity gameIssueTemplateCheck2GameIssueTemplateEntity(
			GameIssueTemplateCheck gameIssueTemplateCheck) {
		GameIssueTemplateEntity gameIssueTemplateEntity = new GameIssueTemplateEntity();
		gameIssueTemplateEntity.setCreateTime(gameIssueTemplateCheck.getCreateTime());
		gameIssueTemplateEntity.setFirstAwardTime(gameIssueTemplateCheck.getFirstAwardTime());
		gameIssueTemplateEntity.setId(gameIssueTemplateCheck.getId());
		gameIssueTemplateEntity.setLastAwardTime(gameIssueTemplateCheck.getLastAwardTime());
		GameIssueRuleEntity gameIssueRuleEntity = new GameIssueRuleEntity();
		gameIssueRuleEntity.setId(gameIssueTemplateCheck.getId());
		gameIssueTemplateEntity.setRule(gameIssueRuleEntity);
		gameIssueTemplateEntity.setSalePeriodTime(gameIssueTemplateCheck.getSalePeriodTime());
		gameIssueTemplateEntity.setSaleStartTime(gameIssueTemplateCheck.getSaleStartTime());
		gameIssueTemplateEntity.setScheduleStopTime(gameIssueTemplateCheck.getScheduleStopTime());
		gameIssueTemplateEntity.setUpdateTime(gameIssueTemplateCheck.getUpdateTime());
		gameIssueTemplateEntity.setSaleTimeSn(gameIssueTemplateCheck.getSaleTimeSn());
		return gameIssueTemplateEntity;
	}

	public static GameIssueRuleCheck gameIssueRuleEntity2GameIssueRuleCheck(GameIssueRuleEntity gameIssueRuleEntity) {
		GameIssueRuleCheck gameIssueRuleCheck = new GameIssueRuleCheck();
		gameIssueRuleCheck.setCreateTime(gameIssueRuleEntity.getCreateTime());
		gameIssueRuleCheck.setIssueRulesName(gameIssueRuleEntity.getIssueRulesName());
		gameIssueRuleCheck.setLotteryid(gameIssueRuleEntity.getLottery().getLotteryId());
		gameIssueRuleCheck.setOpenAwardPeriod(gameIssueRuleEntity.getOpenAwardPeriod());
		gameIssueRuleCheck.setRuleEndTime(gameIssueRuleEntity.getRuleEndTime());
		gameIssueRuleCheck.setRuleid(gameIssueRuleEntity.getRuleid());
		gameIssueRuleCheck.setRuleStartTime(gameIssueRuleEntity.getRuleStartTime());
		gameIssueRuleCheck.setRuleType(new Long(gameIssueRuleEntity.getRuleType().getValue()));
		gameIssueRuleCheck.setStatus(gameIssueRuleEntity.getStatus());
		gameIssueRuleCheck.setUpdateTime(gameIssueRuleEntity.getUpdateTime());
		gameIssueRuleCheck.setId(gameIssueRuleEntity.getId());
		gameIssueRuleCheck.setStopEndTime(gameIssueRuleEntity.getStopEndTime());
		gameIssueRuleCheck.setStopStartTime(gameIssueRuleEntity.getStopStartTime());
		gameIssueRuleCheck.setSeriesIssueCode(gameIssueRuleEntity.getSeriesIssueCode());
		return gameIssueRuleCheck;
	}

	public static GameIssueTemplateCheck gameIssueTemplateEntity2GameIssueTemplateCheck(GameIssueTemplateEntity gite) {
		GameIssueTemplateCheck gameIssueTemplateCheck = new GameIssueTemplateCheck();
		gameIssueTemplateCheck.setId(gite.getId());
		gameIssueTemplateCheck.setCreateTime(gite.getCreateTime());
		gameIssueTemplateCheck.setFirstAwardTime(gite.getFirstAwardTime());
		gameIssueTemplateCheck.setLastAwardTime(gite.getLastAwardTime());
		gameIssueTemplateCheck.setRuleid(gite.getRule().getId());
		gameIssueTemplateCheck.setSalePeriodTime(gite.getSalePeriodTime());
		gameIssueTemplateCheck.setSaleStartTime(gite.getSaleStartTime());
		gameIssueTemplateCheck.setScheduleStopTime(gite.getScheduleStopTime());
		gameIssueTemplateCheck.setUpdateTime(gite.getUpdateTime());
		if (null != gite.getRule().getId()) {
			gameIssueTemplateCheck.setRuleid(gite.getRule().getId());
		}
		return gameIssueTemplateCheck;
	}

	public static GameIssueRule gameIssueRuleEntity2GameIssueRule(GameIssueRuleEntity gameIssueRuleEntity) {
		GameIssueRule gameIssueRule = new GameIssueRule();
		gameIssueRule.setId(gameIssueRuleEntity.getId());
		gameIssueRule.setCreateTime(gameIssueRuleEntity.getCreateTime());
		gameIssueRule.setIssueRulesName(gameIssueRuleEntity.getIssueRulesName());
		gameIssueRule.setLotteryid(gameIssueRuleEntity.getLottery().getLotteryId());
		gameIssueRule.setOpenAwardPeriod(gameIssueRuleEntity.getOpenAwardPeriod());
		gameIssueRule.setRuleEndTime(gameIssueRuleEntity.getRuleEndTime());
		gameIssueRule.setRuleStartTime(gameIssueRuleEntity.getRuleStartTime());
		gameIssueRule.setRuleType(new Long(gameIssueRuleEntity.getRuleType().getValue()));
		gameIssueRule.setStatus(gameIssueRuleEntity.getStatus());
		gameIssueRule.setUpdateTime(gameIssueRuleEntity.getUpdateTime());
		gameIssueRule.setStopStartTime(gameIssueRuleEntity.getStopStartTime());
		gameIssueRule.setStopEndTime(gameIssueRuleEntity.getStopEndTime());
		gameIssueRule.setSeriesIssueCode(gameIssueRuleEntity.getSeriesIssueCode());
		return gameIssueRule;
	}

	public static GameIssueTemplate gameIssueTemplateEntity2GameIssueTemplate(GameIssueTemplateEntity gite) {
		GameIssueTemplate gameIssueTemplate = new GameIssueTemplate();
		gameIssueTemplate.setCreateTime(gite.getCreateTime());
		gameIssueTemplate.setFirstAwardTime(gite.getFirstAwardTime());
		gameIssueTemplate.setLastAwardTime(gite.getLastAwardTime());
		gameIssueTemplate.setRuleid(gite.getRule().getId());
		gameIssueTemplate.setSalePeriodTime(gite.getSalePeriodTime());
		gameIssueTemplate.setSaleStartTime(gite.getSaleStartTime());
		gameIssueTemplate.setScheduleStopTime(gite.getScheduleStopTime());
		gameIssueTemplate.setUpdateTime(gite.getUpdateTime());
		return gameIssueTemplate;
	}

	public static GameMultiple gameMultipleEntity2GameMultiple(GameMultipleEntity entity) {
		GameMultiple multiple = new GameMultiple();

		multiple.setLotteryid(entity.getLottery().getLotteryId());
		multiple.setBetTypeCode(entity.getGameGroupCode() + "_" + entity.getGameSetCode() + "_"
				+ entity.getBetMethodCode());

		if (entity.getMaxMultiple() != null) {
			multiple.setMaxMultiple(entity.getMaxMultiple());
		}

		if (entity.getCreteatTime() != null) {
			multiple.setCreateTime(entity.getCreteatTime());
		}

		if (entity.getUpdateTime() != null) {
			multiple.setUpdateTime(entity.getUpdateTime());
		}

		return multiple;
	}

	public static GameMultipleEntity gameMutiple2GameMutipleEntity(GameMultiple vo) {
		GameMultipleEntity entity = new GameMultipleEntity();

		if (vo != null) {
			Lottery lottery = new Lottery();
			entity.setLottery(lottery);
			String[] resut = vo.getBetTypeCode().split("_");
			entity.setGameGroupCode(Integer.valueOf(resut[0]));
			entity.setGameSetCode(Integer.valueOf(resut[1]));
			entity.setBetMethodCode(Integer.valueOf(resut[1]));
			entity.setMaxMultiple(vo.getMaxMultiple().intValue());
			entity.setCreteatTime(vo.getGmtCreated());
			entity.setUpdateTime(vo.getGmtModified());
			entity.setId(vo.getId());
			entity.setSpecialMultiple(vo.getSpecialMultiple());
		}

		return entity;
	}

	public static GameAwardEntity convertGameAwardCheck2Entity(GameAwardCheck check, GameAwardGroupCheck groupCheck,
			Long miniLotteryProfit, GameBettypeStatus gameBettypeStatus) {

		GameAwardEntity entity = new GameAwardEntity();

		entity.setActualBonus(new BigDecimal(check.getActualBonus()));
		entity.setAwardGroupId(check.getAwardGroupId());

		GameBetType betType = new GameBetType();
		betType.setBetMethodCode(check.getBetMethodCode());
		betType.setGameSetCode(check.getGameSetCode());
		betType.setGameGroupCode(check.getGameGroupCode());
		betType.setMethodType(check.getMethodType());
		betType.setId(gameBettypeStatus.getId());
		entity.setGameBetType(betType);

		entity.setCreateTime(check.getCreateTime());
		entity.setAwardStatus(EnumTypeConverts.convertValue2GameAwardStatus(check.getStatus()));
		//		entity.setGameAwardName(groupCheck.getAwardName()); //2014 0402 新的实例无AwardName，正确应该在Group实例中。此处需要修改返回类型

		Lottery lottery = new Lottery();
		lottery.setLotteryId(groupCheck.getLotteryid());
		entity.setLottery(lottery);

		if (null != check.getUpdateTime()) {
			entity.setUpdateTime(check.getUpdateTime());
		}

		if (null != groupCheck.getThreeoneRet()) {
			entity.setThreeoneRet(new BigDecimal(groupCheck.getThreeoneRet()));
		}

		if (null != groupCheck.getDirectRet()) {
			entity.setDirectRet(new BigDecimal(groupCheck.getDirectRet()));
		}

		entity.setMiniLotteryProfit(new BigDecimal(miniLotteryProfit * 100));
		Long theoryBonus = gameBettypeStatus.getTheoryBonus();
		theoryBonus = theoryBonus == null ? 0l : theoryBonus;
		entity.setTheoryBonus(new BigDecimal(theoryBonus));

		return entity;
	}

	public static GameSeriesCheck gameSeries2GameSeriesCheck(GameSeries gs) {
		GameSeriesCheck gsc = new GameSeriesCheck();
		gsc.setLotteryTypeCode(gs.getLotteryTypeCode());
		gsc.setLotteryTypeName(gs.getLotteryTypeName());
		gsc.setLotterySeriesCode(gs.getLotterySeriesCode());
		gsc.setLotterySeriesName(gs.getLotterySeriesName());
		gsc.setLotteryid(gs.getLotteryid());
		gsc.setLotteryName(gs.getLotteryName());
		gsc.setStatus(gs.getStatus());
		gsc.setCreateTime(gs.getCreateTime());
		gsc.setUpdateTime(gs.getUpdateTime());
		gsc.setMiniLotteryProfit(gs.getMiniLotteryProfit());
		gsc.setLotteryHelpDes(gs.getLotteryHelpDes());

		return gsc;
	}

	public static GameSeriesConfigEntity gameSeriesConfigCheck2GameSeriesConfigEntity(GameSeriesConfigCheck check) {
		GameSeriesConfigEntity entity = new GameSeriesConfigEntity();

		Lottery lottery = new Lottery();
		lottery.setLotteryId(check.getLotteryid());
		entity.setLottery(lottery);
		entity.setBackoutStartFee(check.getBackoutStartFee());
		entity.setBackoutRatio(check.getBackoutRatio());
		entity.setIssuewarnNotOpenaward(check.getIssuewarnNotOpenaward());
		entity.setIssuewarnAheadOpenaward(check.getIssuewarnAheadOpenaward());
		entity.setIssuewarnDelayOpenaward(check.getIssuewarnDelayOpenaward());
		entity.setIssuewarnBackoutTime(check.getIssuewarnBackoutTime());
		entity.setIssuewarnExceptionTime(check.getIssuewarnExceptionTime());
		entity.setCreateTime(check.getCreateTime());
		entity.setUpdateTime(check.getUpdateTime());
		entity.setStatus(EnumTypeConverts.convertValue2GameSeriesConfigStatusType(check.getStatus()));
		entity.setId(check.getId());
		entity.setEmail(check.getEmail());
		entity.setIssuewarnUserBackoutTime(check.getIssuewarnUserBackoutTime());

		return entity;
	}

	public static GameSeriesConfigCheck gameSeriesConfigEntity2GameSeriesConfigCheck(GameSeriesConfigEntity entity) {
		GameSeriesConfigCheck check = new GameSeriesConfigCheck();

		check.setLotteryid(entity.getLottery().getLotteryId());
		check.setBackoutStartFee(entity.getBackoutStartFee());
		check.setBackoutRatio(entity.getBackoutRatio());
		check.setIssuewarnAheadOpenaward(entity.getIssuewarnAheadOpenaward());
		check.setIssuewarnBackoutTime(entity.getIssuewarnBackoutTime());
		check.setIssuewarnDelayOpenaward(entity.getIssuewarnDelayOpenaward());
		check.setIssuewarnExceptionTime(entity.getIssuewarnExceptionTime());
		check.setIssuewarnNotOpenaward(entity.getIssuewarnNotOpenaward());
		check.setCreateTime(entity.getCreateTime());
		check.setUpdateTime(entity.getUpdateTime());
		check.setEmail(entity.getEmail());
		check.setIssuewarnUserBackoutTime(entity.getIssuewarnUserBackoutTime());

		if (null != entity.getStatus() && entity.getStatus().getValue() > 0) {
			check.setStatus(new Long(entity.getStatus().getValue()));
		}

		if (null != entity.getId() && entity.getId() > 0) {
			check.setId(entity.getId());
		}

		return check;
	}

	public static GameSeriesConfigEntity gameSeriesConfig2GameSeriesConfigEntity(GameSeriesConfig check)
			throws Exception {

		GameSeriesConfigEntity entity = new GameSeriesConfigEntity();

		Lottery lottery = new Lottery();
		lottery.setLotteryId(check.getLotteryid());
		entity.setLottery(lottery);
		entity.setBackoutStartFee(check.getBackoutStartFee());
		entity.setBackoutRatio(check.getBackoutRatio());
		entity.setIssuewarnNotOpenaward(check.getIssuewarnNotOpenaward());
		entity.setIssuewarnAheadOpenaward(check.getIssuewarnAheadOpenaward());
		entity.setIssuewarnDelayOpenaward(check.getIssuewarnDelayOpenaward());
		entity.setIssuewarnBackoutTime(check.getIssuewarnBackoutTime());
		entity.setIssuewarnExceptionTime(check.getIssuewarnExceptionTime());
		entity.setCreateTime(check.getCreateTime());
		entity.setUpdateTime(check.getUpdateTime());
		entity.setId(check.getId());
		entity.setEmail(check.getEmail());
		entity.setIssuewarnUserBackoutTime(check.getIssuewarnUserBackoutTime());
		entity.setHasVideo(check.getHasVideo());
		if (!StringUtils.isEmpty(check.getVideoStruc())) {
			ObjectMapper objectMapper = new ObjectMapper();
			VedioStruc[] vedioStrus = objectMapper.readValue(check.getVideoStruc(), VedioStruc[].class);
			if (vedioStrus != null) {
				entity.setVedioStrucs(Arrays.asList(vedioStrus));
			}
		}

		return entity;
	}

	public static GameSeriesConfig gameSeriesConfigEntity2GameSeriesConfig(GameSeriesConfigEntity entity) {

		GameSeriesConfig config = new GameSeriesConfig();

		config.setLotteryid(entity.getLottery().getLotteryId());
		config.setBackoutStartFee(entity.getBackoutStartFee());
		config.setBackoutRatio(entity.getBackoutRatio());
		config.setIssuewarnNotOpenaward(entity.getIssuewarnNotOpenaward());
		config.setIssuewarnAheadOpenaward(entity.getIssuewarnAheadOpenaward());
		config.setIssuewarnDelayOpenaward(entity.getIssuewarnDelayOpenaward());
		config.setIssuewarnBackoutTime(entity.getIssuewarnBackoutTime());
		config.setIssuewarnExceptionTime(entity.getIssuewarnExceptionTime());
		config.setCreateTime(entity.getCreateTime());
		config.setUpdateTime(entity.getUpdateTime());
		config.setId(entity.getId());

		return config;
	}

	public static GameWarnIssueEntity convertGameWarnIssue2Entity(GameWarnIssue issue) throws Exception {

		if (null == issue) {
			return null;
		}

		GameWarnIssueEntity entity = new GameWarnIssueEntity();

		entity.setCrateTime(issue.getCreateTime());
		entity.setIssueWarnId(issue.getIssueWarnId());

		entity.setId(issue.getId());
		entity.setIssueCode(issue.getIssueCode());
		Lottery lottery = new Lottery();
		lottery.setLotteryId(issue.getLotteryid());
		entity.setLottery(lottery);
		entity.setReadFlag(issue.getReadFlag() == 1 ? ReadFlag.read : ReadFlag.unread);
		entity.setStatus(issue.getStatus() == 2 ? GameWarnStatus.processed : GameWarnStatus.pending);
		if (null != issue.getUpdateTime()) {
			entity.setUpdateTime(issue.getUpdateTime());
		}
		entity.setStatusRout(issue.getStatusRout());
		entity.setWarnParas(issue.getWarnParas());
		entity.setWebIssueCode(issue.getWebIssueCode());
		return entity;

	}

	public static GameWarnOrderEntity convertGameWarnOrder2Entity(GameWarnOrder order) throws Exception {

		if (null == order) {
			return null;
		}

		GameWarnOrderEntity entity = new GameWarnOrderEntity();

		entity.setChannelId(order.getChannelId());
		entity.setCountWin(order.getCountWin());
		entity.setCreateTime(order.getCreateTime());
		entity.setId(order.getId());
		entity.setIssueCode(order.getIssueCode());
		Lottery lottery = new Lottery();
		lottery.setLotteryId(order.getLotteryid());
		entity.setLottery(lottery);
		entity.setMaxslipWins(order.getMaxslipWins());
		entity.setOrderCode(order.getOrderCode());
		entity.setOrderId(order.getOrderId());
		entity.setParantType(order.getParentType() == 1 ? ParentType.issue : ParentType.plan);
		entity.setSaleTime(order.getSaleTime());
		entity.setSlipWinsratio(order.getSlipWinsratio());
		entity.setStatus(EnumTypeConverts.getGameWarnOrderStatus(order.getStatus()));
		entity.setType(order.getType() == 1 ? GameWarnType.RiskOrder : GameWarnType.WasteOrder);
		if (null != order.getUpdateTime()) {
			entity.setUpdateTime(order.getUpdateTime());
		}
		User user = new User();
		user.setId(order.getUserid());
		entity.setUser(user);
		entity.setWebIssueCode(order.getWebIssueCode());
		entity.setWinsRatio(order.getWinsRatio());

		return entity;
	}

	/** 
	* @Title: gameWinsReport2WinsReport 
	* @Description: 单期盈亏表VO转为实体bean 
	*/
	public static WinsReport gameWinsReport2WinsReport(GameWinsReport g) {
		WinsReport wr = new WinsReport();
		wr.setBetMethodCode(g.getBetMethodCode());
		wr.setGameGroupCode(g.getGameGroupCode());
		wr.setGameSetCode(g.getGameSetCode());
		wr.setIssueCode(g.getIssueCode());
		wr.setLotteryid(g.getLotteryid());
		wr.setLotteryName(g.getLotteryName());

		wr.setTotalCancel(g.getTotalCancel());
		wr.setTotalPoints(g.getTotalPoints());
		wr.setTotalProfit(g.getTotalProfit());
		wr.setTotalSales(g.getTotalSales());
		wr.setTotalWins(g.getTotalWins());
		wr.setWebIssueCode(g.getWebIssueCode());
		wr.setReportDate(g.getReportDate());
		return wr;
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static GameUserAward gameAward2GameUserAward(GameAward ga, Long userid, Long gameUserAwardGroupId,
			Integer userType) {
		GameUserAward gua = new GameUserAward();
		//		gua.setLotterySeriesCode(ga.getLotterySeriesCode());
		//		gua.setGameGroupCode(ga.getGameGroupCode().longValue());
		//		gua.setGameSetCode(ga.getGameSetCode().longValue());
		//		gua.setBetMethodCode(ga.getBetMethodCode().longValue());
		//		gua.setActualBonus(ga.getActualBonus());
		//		gua.setUserAwardGroupId(gameUserAwardGroupId);
		gua.setCreateTime(new Date());
		gua.setUpdateTime(new Date());
		//		gua.setUserid(userid);
		//		gua.setType(userType == -1 ? 0 : 1);

		return gua;
	}

	public static SingleIssueIncomeAndExpenseEntity gameSingleIssueIncomeAndExpense2Entity(
			SingleIssueIncomeAndExpense vo) {
		SingleIssueIncomeAndExpenseEntity e = new SingleIssueIncomeAndExpenseEntity();
		e.setLotteryName(vo.getLotteryName());
		e.setSaleTimePeriod(vo.getSaleTimePeriod());
		e.setWebIssueCode(vo.getWebIssueCode());
		e.setActualSoldAmount(vo.getActualSoldAmount());
		e.setCanceledAmount(vo.getCanceledAmount());
		e.setCancellationsFee(vo.getCancellationsFee());
		e.setDistributedBonuses(vo.getDistributedBonuses());
		e.setDistributedRetPoint(vo.getDistributedRetPoint());
		e.setToDistributeBonuses(vo.getToDistributeBonuses());

		return e;
	}

	public static com.winterframework.firefrog.game.entity.GamePlan convertGamePlanAndPackage2Entity(
			com.winterframework.firefrog.game.dao.vo.GamePlan plan,
			com.winterframework.firefrog.game.dao.vo.GamePackage pk) {

		com.winterframework.firefrog.game.entity.GamePlan gpEntity = new com.winterframework.firefrog.game.entity.GamePlan();

		gpEntity.setCancelIssue(plan.getCancelIssue());
		gpEntity.setCancelMode(EnumTypeConverts.orderCancelModes2Enum(plan.getCancelModes().longValue()));
		gpEntity.setCancelTime(plan.getCancelTime());
		Channel channel = new Channel();
		channel.setVersion(pk.getChannelVersion());
		channel.setChannelType(ChannelType.getChannelType(pk.getChannelId()));
		gpEntity.setChannel(channel);
		gpEntity.setCrateTime(plan.getCreateTime());
		gpEntity.setFileMode(EnumTypeConverts.converFileMode(pk.getFileMode()));
		gpEntity.setFinishIssue(plan.getFinishIssue());
		GameIssueEntity gameIssue = new GameIssueEntity();
		gameIssue.setIssueCode(pk.getIssueCode());
		gameIssue.setWebIssueCode(plan.getStartWebIssue());
		gpEntity.setGameIssue(gameIssue);
		gpEntity.setGamePlanType(EnumTypeConverts.convetGamePlanType2Enum(plan.getPlanType()));
		gpEntity.setId(plan.getId());
		Lottery lottery = new Lottery();
		lottery.setLotteryId(plan.getLotteryid());
		lottery.setLotteryName(plan.getLotteryName());
		gpEntity.setLottery(lottery);
		gpEntity.setOptionParms(plan.getOptionParms());
		gpEntity.setPackageAmount(pk.getPackageAmount());
		gpEntity.setPackageCode(pk.getPackageCode());
		gpEntity.setPackageId(pk.getId());
		gpEntity.setPlanCode(plan.getPlanCode());
		gpEntity.setSaleTime(pk.getSaleTime());
		gpEntity.setServerIp(pk.getServerip());
		gpEntity.setServerip(pk.getServerip());
		gpEntity.setSoldAmount(plan.getSoldAmount());
		gpEntity.setStartIsuueCode(plan.getStartIsuueCode());
		gpEntity.setStartWebIssue(plan.getStartWebIssue());
		gpEntity.setStatus(EnumTypeConverts.convertStatus2GamePlanStatus(plan.getStatus()));
		gpEntity.setStopMode(EnumTypeConverts.convertStopMode2Enum(plan.getStopMode()));
		gpEntity.setStopParms(plan.getStopMode().longValue());
		gpEntity.setTotalIssue(plan.getTotalIssue());
		gpEntity.setTotamount(pk.getPackageAmount());
		gpEntity.setType(EnumTypeConverts.convertType2Emun(pk.getType()));
		User user = new User();
		user.setId(pk.getUserid());
		gpEntity.setUser(user);
		gpEntity.setUserIp(pk.getUserip());
		gpEntity.setWebSaleTime(pk.getWebSaleTime());
		gpEntity.setWinAmount(plan.getWinAmount());

		return gpEntity;
	}

	/** 
	 * 将注单的辅助实体类转换为VO类
	*/
	public static GameSlipAssist convertSlipAssistEntity2Vo(SlipItemAssist gamePackageItem) {
		GameSlipAssist vo = new GameSlipAssist();
		vo.setCreateTime(gamePackageItem.getCreateTime());
		vo.setEvaluateAward(gamePackageItem.getEvaluatAward());
		vo.setEvaluateAwardDown(gamePackageItem.getEvaluatAwardDown());
		vo.setBetTypeCode(gamePackageItem.getBetTypeCode());
		vo.setSlipid(gamePackageItem.getSlipId());
		vo.setWinNumber(gamePackageItem.getWinNumber());
		vo.setId(gamePackageItem.getId());
		return vo;
	}

	public static GamePackageAssist convertPackageAssistEntity2Vo(PackageItemAssist gamePackageItem) {
		GamePackageAssist vo = new GamePackageAssist();
		vo.setCreateTime(gamePackageItem.getCreateTime());
		vo.setEvaluateAward(gamePackageItem.getEvaluatAward());
		vo.setEvaluateAwardDown(gamePackageItem.getEvaluatAwardDown());
		vo.setBetTypeCode(gamePackageItem.getBetTypeCode());
		vo.setItemid(gamePackageItem.getItemId());
		vo.setWinNumber(gamePackageItem.getWinNumber());
		vo.setId(gamePackageItem.getId());
		return vo;
	}

	public static SlipItemAssist convertSlipAssistVo2Entity(GameSlipAssist slipItemAssist) {
		SlipItemAssist vo = new SlipItemAssist();
		vo.setCreateTime(slipItemAssist.getCreateTime());
		vo.setEvaluatAward(slipItemAssist.getEvaluateAward());
		vo.setEvaluatAwardDown(slipItemAssist.getEvaluateAwardDown());
		vo.setBetTypeCode(slipItemAssist.getBetTypeCode());
		vo.setSlipId(slipItemAssist.getSlipid());
		vo.setId(slipItemAssist.getId());
		vo.setWinNumber(slipItemAssist.getWinNumber());
		return vo;
	}

	public static PackageItemAssist convertPackageAssistVo2Entity(GamePackageAssist packageItemAssist) {
		PackageItemAssist vo = new PackageItemAssist();
		vo.setCreateTime(packageItemAssist.getCreateTime());
		vo.setEvaluatAward(packageItemAssist.getEvaluateAward());
		vo.setEvaluatAwardDown(packageItemAssist.getEvaluateAwardDown());
		vo.setBetTypeCode(packageItemAssist.getBetTypeCode());
		vo.setItemId(packageItemAssist.getItemid());
		vo.setId(packageItemAssist.getId());
		vo.setWinNumber(packageItemAssist.getWinNumber());
		return vo;
	}

	/** 
	* @Title: convertGamePointToVo 
	* @Description: 将变价的entity对象转换为vo
	* @param pointList
	* @param id
	* @return
	*/
	public static List<GamePoint> convertGamePointToVo(List<Points> pointList, Long slipId) {
		List<GamePoint> voList = new ArrayList<GamePoint>();
		for (Points points : pointList) {
			voList.add(new GamePoint(points, slipId, null, null));
		}
		return voList;
	}

	/** 
	* @Title: convertGamePointToVo 
	* @Description: 将变价的entity对象转换为vo
	* @param pointList
	* @param id
	* @return
	*/
	public static List<GamePoint> convertGamePointToPlanVo(List<Points> pointList, Long pkgId, Long planDetailId) {
		List<GamePoint> voList = new ArrayList<GamePoint>();
		for (Points points : pointList) {
			voList.add(new GamePoint(points, null, planDetailId, pkgId));
		}
		return voList;
	}
	
	public static GameBetDetailTotAmountEntity gameSlip2BetDetailTotAmount(com.winterframework.firefrog.game.dao.vo.GameBetDetailTotAmount gs) {
		GameBetDetailTotAmountEntity god = new GameBetDetailTotAmountEntity();

		god.setTotAmount(gs.getTotAmount());
		god.setBetDetail(gs.getBetDetail());
		return god;
	}
}
