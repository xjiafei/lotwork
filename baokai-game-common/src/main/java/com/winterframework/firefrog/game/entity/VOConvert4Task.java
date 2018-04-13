package com.winterframework.firefrog.game.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.game.dao.vo.GameBettypeStatusCheck;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameHelp;
import com.winterframework.firefrog.game.dao.vo.GameHelpCheck;
import com.winterframework.firefrog.game.dao.vo.GameMultiple;
import com.winterframework.firefrog.game.dao.vo.GameMultipleCheck;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.dao.vo.GameSeriesCheck;
import com.winterframework.firefrog.game.dao.vo.GameUserAwardGroup;
import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;
import com.winterframework.firefrog.game.dao.vo.GameWarnUser;
import com.winterframework.firefrog.game.dao.vo.GameWinsReport;
import com.winterframework.firefrog.game.entity.GamePlan.GamePlanType;
import com.winterframework.firefrog.user.entity.User;

public class VOConvert4Task {

	public static GamePlan convertGameOrder2GamePlan(GameOrder order) throws Exception {

		GamePlan gamePlan = new GamePlan();

		//		gamePlan.setUser(order.getUser());
		gamePlan.setLottery(order.getLottery());
		gamePlan.setStartIsuueCode(order.getGameIssue().getIssueCode());
		gamePlan.setFinishIssue(0); //在转换为追号计划时，完成的奖期为0
		//		gamePlan.setTotalIssue(order.getTotalIssue());
		//		gamePlan.setStopMode(getStopModeByValue(order.getStopMode()));
		//		gamePlan.setStopParms(order.getStopParms());
		//		gamePlan.setOptionParms(order.getOptionParms());
		//		gamePlan.setBetDetails(order.getBetdetails());
		gamePlan.setCrateTime(new Date());
		//		gamePlan.setUserIp(order.getUserIp());
		//		gamePlan.setServerIp(order.getServerip());
		gamePlan.setSaleTime(order.getSaleTime());
		//		gamePlan.setGamePlanStatus(GamePlanStatus.executable);
		gamePlan.setGamePlanType(GamePlanType.GENERAL);
		//		gamePlan.setTotamount(order.getPlanAmount());
		gamePlan.setGamePlanDetails(convertGamePlan2GamePlanDetail(order));
		gamePlan.setPlanCode(order.getOrderCode());

		return gamePlan;
	}

	public static List<GamePlanDetail> convertGamePlan2GamePlanDetail(GameOrder order) throws Exception {

		List<GamePlanDetail> list = new ArrayList<GamePlanDetail>();
		/*for (BetDetails bet : order.getBetdetails()) {
			GamePlanDetail detail = new GamePlanDetail();
			detail.setCreateTime(new Date());
			detail.setDetailStatus(GamePlanDetailStatus.unexec);
			detail.setIssueCode(bet.getIssueCode());
			detail.setMutiple(new Long(bet.getMultiple()));
			detail.setTotamount(new BigDecimal(bet.getTotamount()));

			list.add(detail);
		}*/

		return list;
	}

	public static GamePackageItem convertBetDetail2GamePackageItem(BetDetails details, Long gamePackageId)
			throws Exception {

		GamePackageItem item = new GamePackageItem();

		GamePackage gamePackage = new GamePackage();
		gamePackage.setPackageId(gamePackageId);
		item.setGamePackage(gamePackage);
		//		item.setBetMethodCode(details.getBetMethodCode());
		//		item.setMoneyMode(details.getMoneyMode());
		item.setTotbets(new Long(details.getTotbets()));
		item.setTotamount(new Long(details.getTotamount()));
		item.setMultiple(details.getMultiple());
		item.setBetDetail(details.getBetdetail());
		item.setCreateTime(new Date());
		item.setItemAmount(new Long(details.getItemAmount()));
		//		item.setGameGroupCode(details.getGameGroupCode());
		//		item.setGameSetCode(details.getGameSetCode());

		return item;
	}

	public static GameHelpEntity convertGameHelp2Entity(GameHelp help) throws Exception {

		GameHelpEntity entity = new GameHelpEntity();
		entity.setBetMethodCode(help.getBetMethodCode());
		entity.setCreteatTime(help.getCreteatTime());
		entity.setGameExamplesDes(help.getGameExamplesDes());
		entity.setGameGroupCode(help.getGameGroupCode());
		entity.setGamePromptDes(help.getGamePromptDes());
		entity.setGameSetCode(help.getGameSetCode());
		Lottery lottery = new Lottery();
		lottery.setId(help.getLotteryid());
		entity.setLottery(lottery);
		if (null != help.getUpdateTime()) {
			entity.setUpdateTime(help.getUpdateTime());
		}

		return entity;
	}

	public static BetLimit gameMultiple2BetLimit(GameMultiple gm) throws Exception {
		BetLimit bl = new BetLimit();
		bl.setLotteryid(gm.getLotteryid());
		bl.setGameGroupCode(gm.getGameGroupCode());
		bl.setGameSetCode(gm.getGameSetCode());
		bl.setBetMethodCode(gm.getBetMethodCode());
		bl.setMaxMultiple(gm.getMaxMultiple());
		return bl;
	}

	public static BetLimit gameMultipleCheck2BetLimit(GameMultipleCheck gmc) throws Exception {
		BetLimit bl = new BetLimit();
		bl.setLotteryid(gmc.getLotteryid());
		bl.setGameGroupCode(gmc.getGameGroupCode());
		bl.setGameSetCode(gmc.getGameSetCode());
		bl.setBetMethodCode(gmc.getBetMethodCode());
		bl.setMaxMultiple(gmc.getMaxMultiple());
		return bl;
	}

	public static GameMultiple betLimit2GameMultiple(BetLimit bl) throws Exception {
		GameMultiple gm = new GameMultiple();
		gm.setLotteryid(bl.getLotteryid());
		gm.setBetTypeCode(bl.getGameGroupCode() + "_" + bl.getGameSetCode() + "_" + bl.getBetMethodCode());
		/*gm.setGameGroupCode(bl.getGameGroupCode());
		gm.setGameSetCode(bl.getGameSetCode());
		gm.setBetMethodCode(bl.getBetMethodCode());*/
		return gm;
	}

	public static GameMultipleCheck betLimit2GameMultipleCheck(BetLimit bl) throws Exception {
		GameMultipleCheck gmc = new GameMultipleCheck();
		gmc.setLotteryid(bl.getLotteryid());
		gmc.setGameGroupCode(bl.getGameGroupCode());
		gmc.setGameSetCode(bl.getGameSetCode());
		gmc.setBetMethodCode(bl.getBetMethodCode());
		gmc.setMaxMultiple(bl.getMaxMultiple());
		return gmc;
	}

	public static BetMethodDescription gameHelp2BetMethodDescription(GameHelp gh) {
		BetMethodDescription bmd = new BetMethodDescription();
		bmd.setLotteryid(gh.getLotteryid());
		bmd.setGameGroupCode(gh.getGameGroupCode());
		bmd.setGameSetCode(gh.getGameSetCode());
		bmd.setBetMethodCode(gh.getBetMethodCode());
		bmd.setGameExamplesDes(gh.getGameExamplesDes());
		bmd.setGamePromptDes(gh.getGamePromptDes());
		return bmd;
	}

	public static BetMethodDescription gameHelpCheck2BetMethodDescription(GameHelpCheck ghc) {
		BetMethodDescription bmd = new BetMethodDescription();
		bmd.setLotteryid(ghc.getLotteryid());
		bmd.setGameGroupCode(ghc.getGameGroupCode());
		bmd.setGameSetCode(ghc.getGameSetCode());
		bmd.setBetMethodCode(ghc.getBetMethodCode());
		bmd.setGameExamplesDes(ghc.getGameExamplesDes());
		bmd.setGamePromptDes(ghc.getGamePromptDes());
		return bmd;
	}

	public static GameHelpCheck betMethodDescription2GameHelpCheck(BetMethodDescription bmd) {
		GameHelpCheck ghc = new GameHelpCheck();
		ghc.setLotteryid(bmd.getLotteryid());
		ghc.setGameGroupCode(bmd.getGameGroupCode());
		ghc.setGameSetCode(bmd.getGameSetCode());
		ghc.setBetMethodCode(bmd.getBetMethodCode());
		ghc.setGameExamplesDes(bmd.getGameExamplesDes());
		ghc.setGamePromptDes(bmd.getGamePromptDes());

		return ghc;
	}

	public static SellingStatus gameBettypeStatus2SellingStatus(GameBettypeStatus gbs) {
		SellingStatus ss = new SellingStatus();
		ss.setLotteryid(gbs.getLottoryid());
		ss.setGameGroupCode(gbs.getGameGroupCode());
		ss.setGameSetCode(gbs.getGameSetCode());
		ss.setBetMethodCode(gbs.getBetMethodCode());
		ss.setStatus(gbs.getStatus());
		ss.setCreateTime(gbs.getCreateTime());
		ss.setUpdateTime(gbs.getUpdateTime());
		ss.setTheoryBonus(gbs.getTheoryBonus());
		return ss;
	}

	public static GameBettypeStatusCheck sellingStatus2GameBettypeStatusCheck(SellingStatus ss) {
		GameBettypeStatusCheck gbsc = new GameBettypeStatusCheck();
		gbsc.setLotteryid(ss.getLotteryid());
		gbsc.setGameGroupCode(ss.getGameGroupCode());
		gbsc.setGameSetCode(ss.getGameSetCode());
		gbsc.setBetMethodCode(ss.getBetMethodCode());
		gbsc.setStatus(ss.getStatus());
		gbsc.setCreateTime(ss.getCreateTime());
		gbsc.setUpdateTime(ss.getUpdateTime());
		gbsc.setTheoryBonus(ss.getTheoryBonus());
		return gbsc;
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
		gsc.setMiniLotteryProfit(gs.getMiniLotteryProfit());
		gsc.setLotteryHelpDes(gs.getLotteryHelpDes());
		return gsc;
	}

	public static GameBettypeStatus gameBettypeStatusCheck2GameBettypeStatus(GameBettypeStatusCheck gbsc) {
		GameBettypeStatus gbs = new GameBettypeStatus();
		gbs.setGameGroupCode(gbsc.getGameGroupCode());
		gbs.setGameSetCode(gbsc.getGameSetCode());
		gbs.setBetMethodCode(gbsc.getBetMethodCode());
		gbs.setLotteryid(gbsc.getLotteryid());
		gbs.setStatus(gbsc.getStatus());
		gbs.setTheoryBonus(gbsc.getTheoryBonus());
		gbs.setCreateTime(gbsc.getCreateTime());
		gbs.setUpdateTime(gbsc.getUpdateTime());

		return gbs;
	}

	public static GameBettypeStatusCheck GameBettypeStatus2GameBettypeStatusCheck(GameBettypeStatus gbs) {
		GameBettypeStatusCheck sc = new GameBettypeStatusCheck();
		sc.setLotteryid(gbs.getLottoryid());
		sc.setGameGroupCode(gbs.getGameGroupCode());
		sc.setGameSetCode(gbs.getGameSetCode());
		sc.setBetMethodCode(gbs.getBetMethodCode());
		sc.setStatus(gbs.getStatus());
		sc.setCreateTime(gbs.getCreateTime());
		sc.setUpdateTime(gbs.getUpdateTime());
		sc.setTheoryBonus(gbs.getTheoryBonus());
		return sc;
	}

	public static GameMultiple gameMultipleCheck2GameMultiple(GameMultipleCheck gmc) {
		GameMultiple gm = new GameMultiple();
		gm.setLotteryid(gmc.getLotteryid());
		/*gm.setGameGroupCode(gmc.getGameGroupCode());
		gm.setGameSetCode(gmc.getGameSetCode());
		gm.setBetMethodCode(gmc.getBetMethodCode());*/
		gm.setBetTypeCode(gmc.getGameGroupCode() + "_" + gmc.getGameSetCode() + "_" + gmc.getBetMethodCode());
		gm.setMaxMultiple(gmc.getMaxMultiple());
		gm.setCreateTime(gmc.getCreateTime());
		gm.setUpdateTime(new Date());
		return gm;
	}

	public static GameHelp gameHelpCheck2GameHelp(GameHelpCheck ghc) {
		GameHelp gh = new GameHelp();
		gh.setLotteryid(ghc.getLotteryid());
		gh.setGameGroupCode(ghc.getGameGroupCode());
		gh.setGameSetCode(ghc.getGameSetCode());
		gh.setBetMethodCode(ghc.getBetMethodCode());
		gh.setGameExamplesDes(ghc.getGameExamplesDes());
		gh.setGamePromptDes(ghc.getGamePromptDes());
		gh.setUpdateTime(new Date());

		return gh;
	}

	public static SellingStatus gameBettypeStatusCheck2SellingStatus(GameBettypeStatusCheck gbsc) {
		SellingStatus ss = new SellingStatus();
		ss.setLotteryid(gbsc.getLotteryid());
		ss.setGameGroupCode(gbsc.getGameGroupCode());
		ss.setGameSetCode(gbsc.getGameSetCode());
		ss.setBetMethodCode(gbsc.getBetMethodCode());
		ss.setStatus(gbsc.getStatus());
		ss.setCreateTime(gbsc.getCreateTime());
		ss.setUpdateTime(gbsc.getUpdateTime());
		ss.setTheoryBonus(gbsc.getTheoryBonus());
		return ss;
	}

	public static UserAwardGroupEntity gameUserAwardGroup2UserAwardGroupEntity(GameUserAwardGroup guag) {
		UserAwardGroupEntity uage = new UserAwardGroupEntity();
		//		uage.setAwardName(guag.getAwardName());
		uage.setDirectRet(guag.getDirectRet());
		uage.setThreeoneRet(guag.getThreeoneRet());
		uage.setDirectLimitRet(guag.getDirectRet() > 1 ? (guag.getDirectRet() - 1) : 0);
		uage.setThreeLimitRet(guag.getThreeoneRet() > 1 ? (guag.getThreeoneRet() - 1) : 0);
		uage.setSetType(guag.getSetType());
		uage.setStatus(guag.getStatus());
		uage.setUserid(guag.getUserid());
		
		//**六合彩平码返点*/
		uage.setLhcFlatcode(guag.getLhcFlatcode());
		//**六合彩半波返点*/
		uage.setLhcHalfwave(guag.getLhcHalfwave());
		//**六合彩一肖返点*/
		uage.setLhcOneyear(guag.getLhcOneyear());
		//**六合彩不中返点*/
		uage.setLhcNotin(guag.getLhcNotin());
		//**六合彩连肖(中)二、三连肖返点*/
		uage.setLhcContinuein23(guag.getLhcContinuein23());
		//**六合彩连肖(中)四连肖返点*/
		uage.setLhcContinuein4(guag.getLhcContinuein4());
		//**六合彩连肖(中)五连肖返点*/
		uage.setLhcContinuein5(guag.getLhcContinuein5());
		//**六合彩连肖(不中)二、三连肖返点*/
		uage.setLhcContinuenotin23(guag.getLhcContinuenotin23());
		//**六合彩连肖(不中)四连肖返点*/
		uage.setLhcContinuenotin4(guag.getLhcContinuenotin4());
		//**六合彩连肖(不中)五连肖返点*/
		uage.setLhcContinuenotin5(guag.getLhcContinuenotin5());
		//**六合彩连码返点*/
		uage.setLhcContinuecode(guag.getLhcContinuecode());
		return uage;
	}

	public static GameUserAwardGroup userAwardGroupEntity2GameUserAwardGroup(UserAwardGroupEntity e) {
		GameUserAwardGroup guag = new GameUserAwardGroup();
		guag.setLotteryid(e.getLotteryId());
		//		guag.setAwardName(e.getAwardName());
		guag.setDirectRet(e.getDirectRet());
		guag.setThreeoneRet(e.getThreeoneRet());
		guag.setCreateTime(e.getCreateTime());
		guag.setUpdateTime(new Date());
		guag.setUserid(e.getUserid());
		guag.setStatus(e.getStatus());
		guag.setSetType(e.getSetType());
		//**六合彩平码返点*/
		guag.setLhcFlatcode(e.getLhcFlatcode());
		//**六合彩半波返点*/
		guag.setLhcHalfwave(e.getLhcHalfwave());
		//**六合彩一肖返点*/
		guag.setLhcOneyear(e.getLhcOneyear());
		//**六合彩不中返点*/
		guag.setLhcNotin(e.getLhcNotin());
		//**六合彩连肖(中)二、三连肖返点*/
		guag.setLhcContinuein23(e.getLhcContinuein23());
		//**六合彩连肖(中)四连肖返点*/
		guag.setLhcContinuein4(e.getLhcContinuein4());
		//**六合彩连肖(中)五连肖返点*/
		guag.setLhcContinuein5(e.getLhcContinuein5());
		//**六合彩连肖(不中)二、三连肖返点*/
		guag.setLhcContinuenotin23(e.getLhcContinuenotin23());
		//**六合彩连肖(不中)四连肖返点*/
		guag.setLhcContinuenotin4(e.getLhcContinuenotin4());
		//**六合彩连肖(不中)五连肖返点*/
		guag.setLhcContinuenotin5(e.getLhcContinuenotin5());
		//**六合彩连码返点*/
		guag.setLhcContinuecode(e.getLhcContinuecode());
		return guag;
	}

	public static WinsReport gameWinsReport2WinsReport(GameWinsReport gwr) {
		WinsReport wr = new WinsReport();
		wr.setGameGroupName(gwr.getGameGroupName());
		wr.setGameGroupCode(gwr.getGameGroupCode());
		wr.setGameSetName(gwr.getGameSetName());
		wr.setGameSetCode(gwr.getGameSetCode());
		wr.setBetMethodName(gwr.getBetMethodName());
		wr.setBetMethodCode(gwr.getBetMethodCode());

		wr.setIssueCode(gwr.getIssueCode());
		wr.setLotteryid(gwr.getLotteryid());
		wr.setLotteryName(gwr.getLotteryName());
		//		wr.setLotterySeriesCode(lotterySeriesCode);
		//		wr.setLotteryTypeCode(lotteryTypeCode);
		wr.setReportDate(gwr.getReportDate());
		wr.setWebIssueCode(gwr.getWebIssueCode());

		wr.setTotalCancel(gwr.getTotalCancel());
		wr.setTotalPoints(gwr.getTotalPoints());
		wr.setTotalProfit(gwr.getTotalProfit());
		wr.setTotalSales(gwr.getTotalSales());
		wr.setTotalWins(gwr.getTotalWins());

		return wr;
	}

	public static GameOrderWinEntity convertGameOrderWin2Entity(GameOrderWin win) {

		GameOrderWinEntity entity = new GameOrderWinEntity();
		entity.setCalculateWinTime(win.getCalculateWinTime());
		entity.setCountWin(win.getCountWin());
		entity.setIssueCode(win.getIssueCode());
		entity.setLotteryid(win.getLotteryid());
		entity.setMaxslipWins(win.getMaxslipWins());
		entity.setOrderid(win.getOrderid());
		entity.setOrderTime(win.getOrderTime());
		entity.setSaleTime(win.getSaleTime());
		entity.setSlipWinsratio(win.getSlipWinsratio());
		entity.setStatus(win.getStatus());
		entity.setUserid(win.getUserid());
		entity.setWinsRatio(win.getWinsRatio());

		return entity;
	}

	public static GameOrderWin getGameOrderWinVo(com.winterframework.firefrog.game.dao.vo.GameOrder order, Long winAmout) {
		GameOrderWin entity = new GameOrderWin();
		entity.setCalculateWinTime(DateUtils.currentDate());
		entity.setCountWin(winAmout);
		entity.setIssueCode(order.getIssueCode());
		entity.setLotteryid(order.getLotteryid().intValue());
		entity.setOrderid(order.getId());
		entity.setOrderTime(order.getOrderTime());
		entity.setSaleTime(order.getSaleTime());
		entity.setStatus(Long.valueOf(GameOrderWin.Status.WAITING.getValue())); //待派奖
		entity.setUserid(order.getUserid());
		//		entity.setWinsRatio(winsRatio);
		//		entity.setMaxslipWins(maxSlipWins);
		//		entity.setSlipWinsratio(slipWinsRatio);
		entity.setIsNotice(0L);
		return entity;
	}

	public static GameWarnOrder getGameWarnOrderVo(com.winterframework.firefrog.game.dao.vo.GameOrder order,
			Long totalOrderBouns, Long winsRatio, Long maxSlipWins, Long slipWinsRatio, Long channelId) {
		GameWarnOrder warnOrder = new GameWarnOrder();
		warnOrder.setChannelId(channelId);
		warnOrder.setCountWin(totalOrderBouns);
		warnOrder.setCreateTime(new Date());
		warnOrder.setIssueCode(order.getIssueCode());
		warnOrder.setLotteryid(order.getLotteryid());
		warnOrder.setMaxslipWins(maxSlipWins);
		warnOrder.setOrderCode(order.getOrderCode());
		warnOrder.setOrderId(order.getId());
		warnOrder.setParentType(new Long(order.getParentType()));
		warnOrder.setSaleTime(order.getSaleTime());
		warnOrder.setSlipWinsratio(slipWinsRatio);
		warnOrder.setStatus(0L);//待审核
		warnOrder.setType(1L); //风险订单
		warnOrder.setUpdateTime(new Date());
		warnOrder.setUserid(order.getUserid());
		warnOrder.setWebIssueCode(order.getWebIssueCode());
		warnOrder.setWinsRatio(winsRatio);
		warnOrder.setTotamount(order.getTotamount());
		return warnOrder;
	}

	public static GameWarnUser getGameWarnUserVo(com.winterframework.firefrog.game.dao.vo.GameOrder order,
			Long totalOrderBouns, Long winsRatio, User user) {
		GameWarnUser warnUser = new GameWarnUser();
		warnUser.setLotteryid(order.getLotteryid());
		warnUser.setIssueCode(order.getIssueCode());
		warnUser.setUserid(order.getUserid());
		warnUser.setUserAccount(user.getUserProfile().getAccount());
		warnUser.setTotalWins(totalOrderBouns);
		warnUser.setWinsRatio(winsRatio);
		warnUser.setContinuousWinsIssue(1L);
		warnUser.setContinuousWinsTimes(1L);
		warnUser.setMaxslipWins(totalOrderBouns);
		return warnUser;
	}

	public static GameControlEvent getGameControlEventVo(Long lotteryId, GameIssueEntity entity, Long eventType) {
		GameControlEvent event = new GameControlEvent();
		event.setLotteryid(lotteryId);
		event.setStartIssueCode(entity.getIssueCode());
		event.setEndIssueCode(entity.getIssueCode());
		event.setSaleStartTime(entity.getSaleStartTime());
		event.setSaleEndTime(entity.getSaleEndTime());
		event.setStatus(0L);
		event.setCreateTime(new Date());

		event.setEnentType(eventType);
		return event;
	}
}
