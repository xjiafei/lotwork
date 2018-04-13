package com.winterframework.firefrog.game.dao.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.winterframework.firefrog.game.entity.GameAwardEntity;
import com.winterframework.firefrog.game.entity.GameAwardEntity.GameAwardStatus;
import com.winterframework.firefrog.game.entity.GameAwardGroupEntity;
import com.winterframework.firefrog.game.entity.GameAwardGroupEntity.Status;
import com.winterframework.firefrog.game.entity.GameAwardGroupEntity.SysAwardGroup;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameIssueRuleEntity;
import com.winterframework.firefrog.game.entity.GameIssueRuleEntity.RuleType;
import com.winterframework.firefrog.game.entity.GameIssueTemplateEntity;
import com.winterframework.firefrog.game.entity.GameMultipleEntity;
import com.winterframework.firefrog.game.entity.GameOrder;
import com.winterframework.firefrog.game.entity.GamePlanDetail;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity.StatusType;
import com.winterframework.firefrog.game.entity.GameWarnIssueEntity;
import com.winterframework.firefrog.game.entity.GameWarnIssueEntity.GameWarnStatus;
import com.winterframework.firefrog.game.entity.GameWarnIssueEntity.ReadFlag;
import com.winterframework.firefrog.game.entity.GameWarnOrderEntity;
import com.winterframework.firefrog.game.entity.GameWarnOrderEntity.GameWarnOrderStatus;
import com.winterframework.firefrog.game.entity.GameWarnOrderEntity.GameWarnType;
import com.winterframework.firefrog.game.entity.GameWarnOrderEntity.ParentType;
import com.winterframework.firefrog.game.entity.Lottery;
import com.winterframework.firefrog.game.entity.WinsReport;
import com.winterframework.firefrog.user.entity.User;

/** 
* @ClassName: VOConverter4Task 
* @Description: 
* @author david,richard,denny
* @date 2013-7-22 下午3:19:14 
*  
*/
public class VOConverter4Task {

	public static GamePackage gpEntity2gpVo(com.winterframework.firefrog.game.entity.GamePackage gamePackageEntity) {
		GamePackage gamePackage = new GamePackage();
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
		//		gamePackage.setPlanId(gamePackageEntity.getId());
		return gamePackage;
	}

	public static GamePackageItem gpiEntity2gpiVo(
			com.winterframework.firefrog.game.entity.GamePackageItem gamePackageItemEmtity) {
		GamePackageItem gamePackageItem = new GamePackageItem();
		gamePackageItem.setPackageid(gamePackageItemEmtity.getGamePackage().getPackageId());
		//		gamePackageItem.setBetMethodCode(gamePackageItemEmtity.getBetMethodCode());
		//		gamePackageItem.setMoneyMode(gamePackageItemEmtity.getMoneyMode());
		gamePackageItem.setTotbets(gamePackageItemEmtity.getTotbets());
		gamePackageItem.setTotamount(gamePackageItemEmtity.getTotamount());
		gamePackageItem.setMultiple(gamePackageItemEmtity.getMultiple());
		gamePackageItem.setBetDetail(gamePackageItemEmtity.getBetDetail());
		gamePackageItem.setCreateTime(gamePackageItemEmtity.getCreateTime());
		//		gamePackageItem.setItemAmount(gamePackageItemEmtity.getItemAmount());
		//		gamePackageItem.setGameGroupCode(gamePackageItemEmtity.getGameGroupCode());
		//		gamePackageItem.setGameSetCode(gamePackageItemEmtity.getGameSetCode());
		GameBetType betTypeCode = gamePackageItemEmtity.getGameBetType();
		gamePackageItem.setBetTypeCode(betTypeCode.getBetTypeCode());
		return gamePackageItem;
	}

	public static com.winterframework.firefrog.game.dao.vo.GameOrder goe2gov(GameOrder gameOrder) {
		com.winterframework.firefrog.game.dao.vo.GameOrder gameOrderVo = new com.winterframework.firefrog.game.dao.vo.GameOrder();
		//		gameOrderVo.setUserid(gameOrder.getUser().getId());
		//		gameOrderVo.setIssueCode(gameOrder.getIssueCode());
		gameOrderVo.setLotteryid(gameOrder.getLottery().getLotteryId());
		//		gameOrderVo.setTotamount(gameOrder.getPackageAmount());
		gameOrderVo.setStatus(gameOrder.getStatus().getValue());
		gameOrderVo.setOrderTime(new Date());
		gameOrderVo.setSaleTime(gameOrder.getSaleTime());
		gameOrderVo.setCancelModes(new Long(gameOrder.getCancelModes().getValue()).intValue());
		//		gameOrderVo.setParentType(new Long(gameOrder.getParentType().getValue()).intValue());
		return gameOrderVo;
	}

	public static GameOrder gov2goe(com.winterframework.firefrog.game.dao.vo.GameOrder gov) {
		GameOrder goe = new GameOrder();
		goe.setOrderCode(gov.getOrderCode());
		Lottery lottery = new Lottery();
		lottery.setLotteryId(gov.getLotteryid());
		goe.setLottery(lottery);
		//		goe.setIssueCode(gov.getIssueCode());
		//		goe.setWebIssueCode(gov.getWebIssueCode());
		goe.setTotalAmount(gov.getTotamount());
		goe.setCancelModes(EnumTypeConverts.orderCancelModes2Enum(Long.valueOf(gov.getCancelModes())));
		goe.setId(gov.getId());
		//goe.setNumberRecord(g)
		//goe.setOptionParms(gov.get)
		goe.setOrderCode(gov.getOrderCode());
		//		goe.setPackageAmount(gov.getTotamount());
		//		goe.setParentId(gov.getParentid());
		//		goe.setParentType(orderParentType2Enum(Long.valueOf(gov.getParentType())));
		//goe.setPlanAmount(gov.get)
		goe.setSaleTime(gov.getSaleTime());
		//goe.setServerip(gov.get)
		goe.setStatus(EnumTypeConverts.orderStatus2Enum(Long.valueOf(gov.getStatus())));
		//goe.setStopParms(gov.get)
		goe.setTotalAmount(gov.getTotamount());
		goe.setEndCanCancelTime(gov.getEndCanCancelTime());
		//goe.setTotalIssue(gov.get)
		//		goe.setTotalWin(gov.get)
		User user = new User();
		user.setId(gov.getUserid());
		//		goe.setUser(user);
		//goe.setUserIp(gov)
		return goe;
	}

	/** 
	* @Title: gamePlanV2GamePlanE 
	* @Description: Gameplan值对象转换为实体Bean
	* @param @param gp
	* @param @return    设定文件 
	* @return com.winterframework.firefrog.game.entity.GamePlan    返回类型 
	* @throws 
	*/
	public static com.winterframework.firefrog.game.entity.GamePlan gamePlanV2GamePlanE(GamePlan gpv) {
		com.winterframework.firefrog.game.entity.GamePlan gpe = new com.winterframework.firefrog.game.entity.GamePlan();

		Lottery lottery = new Lottery();
		long lotteryid = gpv.getLotteryid();
		lottery.setLotteryId(lotteryid);
		lottery.setLotteryName(gpv.getLotteryName());
		gpe.setLottery(lottery);
		//		gpe.setStatus(gpv.getStatus());
		gpe.setStartIsuueCode(gpv.getStartIsuueCode());
		gpe.setStartWebIssue(gpv.getStartWebIssue());

		gpe.setPlanCode(gpv.getPlanCode());
		gpe.setFinishIssue(gpv.getFinishIssue());
		gpe.setTotalIssue(gpv.getTotalIssue());
		//		gpe.setTotamount(gpv.getTotamount());
		gpe.setSoldAmount(gpv.getSoldAmount());
		//int stopMode = gpv.getStopMode();
		//		gpe.setStopMode();
		gpe.setStopParms(gpv.getStopParms());
		//		gpe.setSaleTime(gpv.getSaleTime());
		gpe.setPackageId(gpv.getId());

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
		gpde.setGamePlan(gamePlan);
		//		gpde.setIssueCode(gpdv.getIssueCode());
		gpde.setMutiple(gpdv.getMutiple());
		//		gpde.setDetailStatus();
		gpde.setCreateTime(gpdv.getCreateTime());
		gpde.setCancelTime(gpdv.getCancelTime());

		return gpde;
	}

	public static GameIssueEntity gameIssue2GameIssueEntity(GameIssue gameIssue) {
		GameIssueEntity gie = new GameIssueEntity();
		gie.setCreateTime(gameIssue.getCreateTime());
		gie.setFactionDrawTime(gameIssue.getFactionDrawTime());
		gie.setId(gameIssue.getId());
		gie.setIssueCode(gameIssue.getIssueCode());
		Lottery lottery = new Lottery();
		lottery.setLotteryId(gameIssue.getLotteryid());
		gie.setLottery(lottery);
		gie.setOpenDrawTime(gameIssue.getOpenDrawTime());
		gie.setPeriodStatus(EnumTypeConverts.convertStatus2GameIssuePeriodStatus(gameIssue.getPeriodStatus()));
		gie.setSaleEndTime(gameIssue.getSaleEndTime());
		gie.setSaleStartTime(gameIssue.getSaleStartTime());
		gie.setStatus(EnumTypeConverts.convertStatus2GameIssueStatus(gameIssue.getStatus()));
		gie.setUpdateTime(gameIssue.getUpdateTime());
		gie.setWebIssueCode(gameIssue.getWebIssueCode());

		gie.setLastIssueStop(EnumTypeConverts.convertStatus2LastIssueStop(gameIssue.getLastIssueStop() == null ? 0
				: gameIssue.getLastIssueStop()));
		gie.setPlanFinishStatus(EnumTypeConverts.convertStatus2PlanFinishStatus(gameIssue.getPlanFinishStatus() == null ? 1
				: gameIssue.getPlanFinishStatus()));
		gie.setPauseStatus(EnumTypeConverts.convertStatus2PauseStatus(gameIssue.getPauseStatus() == null ? 1
				: gameIssue.getPauseStatus()));
		gie.setEventStatus(EnumTypeConverts.convertStatus2EventStatus(gameIssue.getEventStatus() == null ? 1
				: gameIssue.getEventStatus()));
		
		gie.setAwardStruct(gameIssue.getAwardStruct());
		gie.setPreNumberRecord(gameIssue.getPreNumberRecord());
		gie.setRecivceDrawTime(gameIssue.getRecivceDrawTime());
		gie.setEcVerifiedTime(gameIssue.getEcVerifiedTime());
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

		entity.setLottery(lottery);
		entity.setStatus(convertValue2Enum(gameAwardGroup.getStatus()));
		entity.setSysAwardGroup(convertValueToEnum(gameAwardGroup.getSysAwardGroup()));
		entity.setThreeoneRet(gameAwardGroup.getThreeoneRet());

		if (null != gameAwardGroup.getUpdateTime()) {
			entity.setUpdateTime(gameAwardGroup.getUpdateTime());
		}

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
		entity.setStatus(convertValue2Enum(group.getStatus()));
		entity.setSysAwardGroup(convertValueToEnum(group.getSysAwardGroup()));
		entity.setThreeoneRet(group.getThreeoneRet());

		if (null != group.getUpdateTime()) {
			entity.setUpdateTime(group.getUpdateTime());
		}

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
		entity.setStatus(convertValue2Enum(gameAwardGroup.getStatus()));
		entity.setSysAwardGroup(convertValueToEnum(gameAwardGroup.getSysAwardGroup()));
		entity.setThreeoneRet(gameAwardGroup.getThreeoneRet());

		if (null != gameAwardGroup.getUpdateTime()) {
			entity.setUpdateTime(gameAwardGroup.getUpdateTime());
		}

		if (null != list && list.size() > 0) {
			entity.setGameAwards(list);
		}

		return entity;
	}

	public static Status convertValue2Enum(Long status) {

		if (null == status) {
			return null;
		}

		switch (status.intValue()) {
		case 1:
			return Status.CURRENT;
		case 2:
			return Status.DELETE;
		case 3:
			return Status.WATING_AUDIT;
		case 4:
			return Status.WATING_PUBLISH;

		default:
			return null;
		}
	}

	public static SysAwardGroup convertValueToEnum(Integer awardGroup) {

		if (null == awardGroup) {
			return null;
		}

		switch (awardGroup) {
		case 1:
			return SysAwardGroup.SYSTEM;
		case 2:
			return SysAwardGroup.USER;
		default:
			return null;
		}
	}

	public static GameAwardStatus convertValue2GameAwardStatus(Integer status) {

		if (null == status) {
			return null;
		}

		switch (status.intValue()) {
		case 1:
			return GameAwardStatus.CURRENT;
		case 2:
			return GameAwardStatus.DELETE;
		case 3:
			return GameAwardStatus.WATING_AUDITING;
		case 4:
			return GameAwardStatus.WATING_PUBLISH;

		default:
			return null;
		}
	}

	/**
	 * 
	* @Title: convertGameAward2Entity 
	* @Description:转换Vo2Entity
	* @param gameAward
	* @return
	 */
	public static GameAwardEntity convertGameAward2Entity(GameAward gameAward, GameAwardGroup awardGroup,
			Float miniLotteryProfit, Long theoryBonus) {

		GameAwardEntity entity = new GameAwardEntity();

		entity.setActualBonus(new BigDecimal(gameAward.getActualBonus()));
		entity.setAwardGroupId(gameAward.getAwardGroupId());

		GameBetType betType = new GameBetType();
		betType.setBetMethodCode(gameAward.findBetMethodCode());
		entity.setCreateTime(gameAward.getCreateTime());
		betType.setGameGroupCode(gameAward.findGameGroupCode());
		betType.setGameSetCode(gameAward.findGameSetCode());
		entity.setAwardStatus(convertValue2GameAwardStatus(gameAward.getStatus()));
		//		entity.setGameAwardName(awardGroup.getAwardName());

		entity.setGameBetType(betType);

		Lottery lottery = new Lottery();
		lottery.setLotteryId(awardGroup.getLotteryid());
		lottery.setLotteryName(awardGroup.getAwardName());

		entity.setLottery(lottery);

		if (null != gameAward.getUpdateTime()) {
			entity.setUpdateTime(gameAward.getUpdateTime());
		}

		if (null != awardGroup.getThreeoneRet()) {
			entity.setThreeoneRet(new BigDecimal(awardGroup.getThreeoneRet()));
		}

		if (null != awardGroup.getDirectRet()) {
			entity.setDirectRet(new BigDecimal(awardGroup.getDirectRet()));
		}

		entity.setMiniLotteryProfit(new BigDecimal(miniLotteryProfit * 100));
		if (theoryBonus != null) {
			entity.setTheoryBonus(new BigDecimal(theoryBonus));
		} else {
			entity.setTheoryBonus(new BigDecimal(0));
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
		//		multiple.setGameGroupCode(entity.getGameGroupCode());
		//		multiple.setGameSetCode(entity.getGameSetCode());
		//		multiple.setBetMethodCode(entity.getBetMethodCode());

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

			entity.setGameGroupCode(vo.getGameGroupCode().intValue());
			entity.setGameSetCode(vo.getGameSetCode().intValue());
			entity.setBetMethodCode(vo.getBetMethodCode().intValue());
			entity.setMaxMultiple(vo.getMaxMultiple().intValue());
			entity.setCreteatTime(vo.getGmtCreated());
			entity.setUpdateTime(vo.getGmtModified());
		}

		return entity;
	}

	public static GameAwardEntity convertGameAwardCheck2Entity(GameAwardCheck check, GameAwardGroupCheck groupCheck,
			Long miniLotteryProfit, Long theoryBonus) {

		GameAwardEntity entity = new GameAwardEntity();

		entity.setActualBonus(new BigDecimal(check.getActualBonus()));
		entity.setAwardGroupId(check.getAwardGroupId());
		GameBetType gameBetType = new GameBetType();

		gameBetType.setBetMethodCode(check.getBetMethodCode());
		entity.setCreateTime(check.getCreateTime());
		gameBetType.setGameGroupCode(check.getGameGroupCode());
		gameBetType.setGameSetCode(check.getGameSetCode());
		entity.setAwardStatus(convertValue2GameAwardStatus(check.getStatus()));
		//		entity.setGameAwardName(groupCheck.getAwardName());
		entity.setGameBetType(gameBetType);

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
		entity.setStatus(convertValue2GameSeriesConfigStatusType(check.getStatus()));
		entity.setId(check.getId());

		return entity;
	}

	public static StatusType convertValue2GameSeriesConfigStatusType(Long status) {

		if (null == status) {
			return null;
		}

		switch (status.intValue()) {
		case 3:
			return StatusType.Pending;
		case 4:
			return StatusType.Released;

		default:
			return null;
		}
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

		if (null != entity.getStatus() && entity.getStatus().getValue() > 0) {
			check.setStatus(new Long(entity.getStatus().getValue()));
		}

		if (null != entity.getId() && entity.getId() > 0) {
			check.setId(entity.getId());
		}

		return check;
	}

	public static GameSeriesConfigEntity gameSeriesConfig2GameSeriesConfigEntity(GameSeriesConfig check) {

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
		entity.setIssuewarnUserBackoutTime(check.getIssuewarnUserBackoutTime());
		entity.setEmail(check.getEmail());

		return entity;
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
		entity.setStatus(getGameWarnOrderStatus(order.getStatus()));
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

	public static GameWarnOrderStatus getGameWarnOrderStatus(Long action) {

		switch (action.intValue()) {
		case 0:
			return GameWarnOrderStatus.PendingAudit;
		case 1:
			return GameWarnOrderStatus.Audit;
		case 2:
			return GameWarnOrderStatus.unAudit;

		default:
			break;
		}

		return null;
	}

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
}
