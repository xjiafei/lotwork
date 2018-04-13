package com.winterframework.firefrog.game.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.winterframework.firefrog.game.dao.vo.GameBetAwardGroup;
import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.game.dao.vo.GameBettypeStatusCheck;
import com.winterframework.firefrog.game.dao.vo.GameHelp;
import com.winterframework.firefrog.game.dao.vo.GameHelpAndBonus;
import com.winterframework.firefrog.game.dao.vo.GameHelpCheck;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameMultiple;
import com.winterframework.firefrog.game.dao.vo.GameMultipleCheck;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.dao.vo.GameSeriesCheck;
import com.winterframework.firefrog.game.dao.vo.GameUserAwardGroup;
import com.winterframework.firefrog.game.dao.vo.GameWinsReport;
import com.winterframework.firefrog.game.entity.GamePlan.GamePlanStatus;
import com.winterframework.firefrog.game.entity.GamePlan.GamePlanType;
import com.winterframework.firefrog.game.entity.GamePlanDetail.GamePlanDetailStatus;
import com.winterframework.firefrog.game.web.dto.BetMethodDescriptionJoinBonus;

public class VOConvert {

	public static GamePlan convertGameOrder2GamePlan(GameOrder order) throws Exception {

		GamePlan sourceGamePlan = (GamePlan) order.getGamePackage();
		GamePlan gamePlan = new GamePlan();

		gamePlan.setUser(sourceGamePlan.getUser());
		gamePlan.setLottery(order.getLottery());
		gamePlan.setStartIsuueCode(order.getGameIssue().getIssueCode());
		gamePlan.setFinishIssue(0); //在转换为追号计划时，完成的奖期为0
		gamePlan.setTotalIssue(sourceGamePlan.getTotalIssue());
		gamePlan.setStopMode(sourceGamePlan.getStopMode());
		gamePlan.setStopParms(sourceGamePlan.getStopParms());
		gamePlan.setOptionParms(sourceGamePlan.getOptionParms());
		gamePlan.setCrateTime(new Date());
		gamePlan.setUserIp(sourceGamePlan.getUserIp());
		gamePlan.setServerIp(sourceGamePlan.getServerip());
		gamePlan.setSaleTime(sourceGamePlan.getSaleTime());
		gamePlan.setStatus(GamePlanStatus.EXECUTABLE);
		gamePlan.setGamePlanType(GamePlanType.GENERAL);
		gamePlan.setPackageAmount(order.getGamePackage().getPackageAmount());
		gamePlan.setGamePlanDetails(convertGamePlan2GamePlanDetail(order, gamePlan));
		gamePlan.setPlanCode(order.getOrderCode());
		gamePlan.setSoldAmount(sourceGamePlan.getSoldAmount());
		gamePlan.setWinAmount(0L);

		return gamePlan;
	}

	public static List<GamePlanDetail> convertGamePlan2GamePlanDetail(GameOrder order, GamePlan gamePlan)
			throws Exception {

		//根据拆分来的betDetails数据给GamePlanDetail去重
		List<GamePlanDetail> list = new ArrayList<GamePlanDetail>();
		Set<Long> issueCodeSet = new HashSet<Long>();
		for (GameSlip bet : order.getSlipList()) {
			issueCodeSet.add(bet.getIssueCode().getIssueCode());
		}
		List<Long> issueCodeList = new ArrayList<Long>(issueCodeSet);
		//根据奖期进行排序。
		Collections.sort(issueCodeList,new Comparator<Long>(){  
            public int compare(Long arg0, Long arg1) {  
                return arg0.compareTo(arg1);  
            }  
        });  
		
		for (Long issueCode : issueCodeList) {
			GamePlanDetail detail = new GamePlanDetail();
			detail.setCreateTime(new Date());
			detail.setDetailStatus(GamePlanDetailStatus.UN_EXEC);
			detail.setGameIssue(new GameIssueEntity(issueCode));
			detail.setTotamount(new BigDecimal(0L));
			for (GameSlip bet : order.getSlipList()) {
				if (bet.getIssueCode().getIssueCode().longValue() == issueCode.longValue()) {
					detail.setMutiple(new Long(bet.getMultiple()));
					detail.setTotamount(new BigDecimal(detail.getTotamount().longValue() + bet.getTotalAmount()));
					if(bet.getLockPoints()!=null)
					{
						bet.getLockPoints().setBetDetail(org.apache.commons.codec.digest.DigestUtils.md5Hex(bet.getGameBetType().getBetTypeCode()+bet.getBetDetail()+bet.getMoneyMode().getValue()));
						detail.getLockPoint().add(bet.getLockPoints());
					}
				}
				
				
			}
			detail.setGamePlan(gamePlan);
			list.add(detail);
		}
		
		

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
		//		item.setFileMode(details.getFileMode());

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
		String[] re = gm.getBetTypeCode().split("_");
		bl.setLotteryid(gm.getLotteryid());
		bl.setGameGroupCode(Integer.valueOf(re[0]));
		bl.setGameSetCode(Integer.valueOf(re[1]));
		bl.setBetMethodCode(Integer.valueOf(re[2]));
		bl.setMaxMultiple(gm.getMaxMultiple());
		bl.setSpecialLimit(gm.getSpecialMultiple());
		return bl;
	}

	public static BetLimit gameMultipleCheck2BetLimit(GameMultipleCheck gmc) throws Exception {
		BetLimit bl = new BetLimit();
		bl.setLotteryid(gmc.getLotteryid());
		bl.setGameGroupCode(gmc.getGameGroupCode());
		bl.setGameSetCode(gmc.getGameSetCode());
		bl.setBetMethodCode(gmc.getBetMethodCode());
		bl.setMaxMultiple(gmc.getMaxMultiple());
		bl.setStatus(gmc.getStatus());
		bl.setSpecialLimit(gmc.getSpecialMultiple());
		return bl;
	}

	public static GameMultiple betLimit2GameMultiple(BetLimit bl) throws Exception {
		GameMultiple gm = new GameMultiple();
		gm.setLotteryid(bl.getLotteryid());
		gm.setBetTypeCode(bl.getGameGroupCode() + "_" + bl.getGameSetCode() + "_" + bl.getBetMethodCode());
		//		gm.setGameGroupCode(bl.getGameGroupCode());
		//		gm.setGameSetCode(bl.getGameSetCode());
		//		gm.setBetMethodCode(bl.getBetMethodCode());
		return gm;
	}

	public static GameMultipleCheck betLimit2GameMultipleCheck(BetLimit bl) throws Exception {
		GameMultipleCheck gmc = new GameMultipleCheck();
		gmc.setLotteryid(bl.getLotteryid());
		gmc.setGameGroupCode(bl.getGameGroupCode());
		gmc.setGameSetCode(bl.getGameSetCode());
		gmc.setBetMethodCode(bl.getBetMethodCode());
		gmc.setMaxMultiple(bl.getMaxMultiple());
		gmc.setBetTypeCode(bl.getGameGroupCode() + "_" + bl.getGameSetCode() + "_" + bl.getBetMethodCode());
		gmc.setSpecialMultiple(bl.getSpecialLimit());
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

	public static BetMethodDescriptionJoinBonus gameHelp2BetMethodAndBonusDescription(GameHelpAndBonus gh) {
		BetMethodDescriptionJoinBonus bmd = new BetMethodDescriptionJoinBonus();
		bmd.setLotteryid(gh.getLotteryid());
		bmd.setGameGroupCode(gh.getGameGroupCode());
		bmd.setGameSetCode(gh.getGameSetCode());
		bmd.setBetMethodCode(gh.getBetMethodCode());
		bmd.setGameExamplesDes(gh.getGameExamplesDes());
		bmd.setGamePromptDes(gh.getGamePromptDes());
		bmd.setActualBonus(gh.getActualBonus());
		bmd.setTheoryBonus(gh.getTheoryBonus());
		bmd.setActualBonusDown(gh.getActualBonusDown());
		bmd.setRetPoint(gh.getRetPoint());
		bmd.setMoreBouns(gh.getMoreBouns());
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
		gm.setBetTypeCode(gmc.getGameGroupCode() + "_" + gmc.getGameSetCode() + "_" + gmc.getBetMethodCode());
		//		gm.setGameGroupCode(gmc.getGameGroupCode());
		//		gm.setGameSetCode(gmc.getGameSetCode());
		//		gm.setBetMethodCode(gmc.getBetMethodCode());
		gm.setMaxMultiple(gmc.getMaxMultiple());
		gm.setCreateTime(gmc.getCreateTime());
		gm.setUpdateTime(new Date());
		gm.setSpecialMultiple(gmc.getSpecialMultiple());
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
		uage.setAwardName(guag.getAwardName());
		uage.setDirectRet(guag.getDirectRet());
		uage.setLotterySeriesCode(guag.getLotterySeriesCode());
		uage.setLotterySeriesName(guag.getLotterySeriesName());
		uage.setLotteryId(guag.getLotteryid()); 
		uage.setDirectRet(guag.getDirectRet());
		uage.setDirectLimitRet(guag.getDirectRet() > 1 ? (guag.getDirectRet() - 1) : 0);
		uage.setMaxDirectRet(guag.getMaxDirectRet());
		uage.setThreeoneRet(guag.getThreeoneRet());
		uage.setThreeLimitRet(guag.getThreeoneRet() > 1 ? (guag.getThreeoneRet() - 1) : 0);
		uage.setMaxThreeOneRet(guag.getMaxThreeOneRet());
		uage.setSuperRet(guag.getSuperRet());
		uage.setSuperLimitRet(guag.getSuperRet() > 1 ? (guag.getSuperRet() - 1) : 0);
		uage.setMaxSuperRet(guag.getMaxSuperRet());
		uage.setSetType(guag.getSetType());
		uage.setBetType(guag.getBetType());
		uage.setStatus(guag.getStatus());
		uage.setUserid(guag.getUserid());
		uage.setAwardGroupId(guag.getId());
		GameAwardGroupEntity entity = new GameAwardGroupEntity();
		entity.setId(guag.getSysGroupAwardId());
		uage.setSysGroupAward(entity);
		uage.setMaxDirectRet(guag.getMaxDirectRet());
		uage.setMaxThreeOneRet(guag.getMaxThreeOneRet());
		
		uage.setLhcColor(guag.getLhcColor());
		uage.setLhcYear(guag.getLhcYear());
		
		uage.setLhcColorLimit(guag.getLhcColor());
		uage.setLhcYearLimit(guag.getLhcYear());
		
		uage.setMaxLhcYear(guag.getMaxLhcYear());
		uage.setMaxLhcColor(guag.getMaxLhcColor());
		uage.setSbThreeoneRet(guag.getSbThreeoneRet());
		uage.setMaxSbThreeoneRet(guag.getMaxSbThreeoneRet());
		uage.setSbThreeoneRetLimit(guag.getSbThreeoneRet());
		
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
		
		//可分配返點數上限
		uage.setMaxLhcFlatcode(guag.getMaxLhcFlatcode());
		uage.setMaxLhcHalfwave(guag.getMaxLhcHalfwave());
		uage.setMaxLhcOneyear(guag.getMaxLhcOneyear());
		uage.setMaxLhcNotin(guag.getMaxLhcNotin());
		uage.setMaxLhcContinuein23(guag.getMaxLhcContinuein23());
		uage.setMaxLhcContinuein4(guag.getMaxLhcContinuein4());
		uage.setMaxLhcContinuein5(guag.getMaxLhcContinuein5());
		uage.setMaxLhcContinuenotin23(guag.getMaxLhcContinuenotin23());
		uage.setMaxLhcContinuenotin4(guag.getMaxLhcContinuenotin4());
		uage.setMaxLhcContinuenotin5(guag.getMaxLhcContinuenotin5());
		uage.setMaxLhcContinuecode(guag.getMaxLhcContinuecode());
		
		//可分配返點數
		uage.setLhcFlatcodeLimit(guag.getLhcFlatcode());
		uage.setLhcHalfwaveLimit(guag.getLhcHalfwave());
		uage.setLhcOneyearLimit(guag.getLhcOneyear());
		uage.setLhcNotinLimit(guag.getLhcNotin());
		uage.setLhcContinuein23Limit(guag.getLhcContinuein23());
		uage.setLhcContinuein4Limit(guag.getLhcContinuein4());
		uage.setLhcContinuein5Limit(guag.getLhcContinuein5());
		uage.setLhcContinuenotin23Limit(guag.getLhcContinuenotin23());
		uage.setLhcContinuenotin4Limit(guag.getLhcContinuenotin4());
		uage.setLhcContinuenotin5Limit(guag.getLhcContinuenotin5());
		uage.setLhcContinuecodeLimit(guag.getLhcContinuecode());
		
		
		return uage;
	}

	public static UserBetAwardGroupEntity GameBetAwardGroup2UserBetAwardGroupEntity(GameBetAwardGroup guag) {
		UserBetAwardGroupEntity uage = new UserBetAwardGroupEntity();
		uage.setAwardName(guag.getAwardName());
		uage.setAwardGroupid(guag.getAwardGroupid());
		uage.setLotteryid(guag.getLotteryid());
		uage.setLotterySeriesCode(guag.getLotterySeriesCode());
		uage.setAwardRetStatus(guag.getAwardRetStatus());
		uage.setSysAwardGroupId(guag.getSysAwardGroupId());
		return uage;
	}

	public static GameUserAwardGroup userAwardGroupEntity2GameUserAwardGroup(UserAwardGroupEntity e) {
		GameUserAwardGroup guag = new GameUserAwardGroup();
		guag.setLotterySeriesCode(e.getLotterySeriesCode());
		guag.setLotterySeriesName(e.getLotterySeriesName());
		guag.setLotteryid(e.getLotteryId());
		//		guag.setAwardName(e.getAwardName());
		guag.setDirectRet(e.getDirectRet());
		guag.setMaxDirectRet(e.getMaxDirectRet()); 
		guag.setThreeoneRet(e.getThreeoneRet()); 
		guag.setMaxThreeOneRet(e.getMaxThreeOneRet());
		guag.setSuperRet(e.getSuperRet());
		guag.setMaxSuperRet(e.getMaxSuperRet());
		guag.setCreateTime(e.getCreateTime());
		guag.setUpdateTime(new Date());
		guag.setUserid(e.getUserid());
		guag.setStatus(e.getStatus());
		guag.setSetType(e.getSetType());
		guag.setBetType(e.getBetType());
		guag.setSysGroupAwardId(e.getSysGroupAward().getId()); 
		guag.setSuperRet(e.getSuperRet()==null?0:e.getSuperRet());
        guag.setLhcYear(e.getLhcYear());
		guag.setLhcColor(e.getLhcColor()); 
		guag.setSbThreeoneRet(e.getSbThreeoneRet());
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

	public static GameIssue gameIssueEntity2GameIssue(GameIssueEntity entity) {
		GameIssue gi = new GameIssue();
		gi.setId(entity.getId());
		gi.setLotteryid(entity.getLottery().getLotteryId());
		gi.setIssueCode(entity.getIssueCode());
		gi.setWebIssueCode(entity.getWebIssueCode());
		gi.setSaleStartTime(entity.getSaleStartTime());
		gi.setSaleEndTime(entity.getSaleEndTime());
		gi.setOpenDrawTime(entity.getOpenDrawTime());
		gi.setFactionDrawTime(entity.getFactionDrawTime());
		gi.setStatus(new Long(entity.getStatus().getValue()));
		gi.setCreateTime(entity.getCreateTime());
		gi.setUpdateTime(entity.getUpdateTime());
		gi.setPeriodStatus(new Long(entity.getPeriodStatus().getValue()));
		gi.setPauseStatus(entity.getPauseStatus().getValue());
		gi.setEventStatus(entity.getEventStatus().getValue());
		gi.setSequence(entity.getSequence());
		gi.setPlanFinishStatus(entity.getPlanFinishStatus().getValue());
		gi.setLastIssueStop(entity.getLastIssueStop().getValue());
		gi.setRecivceDrawTime(entity.getRecivceDrawTime());
		gi.setEcVerifiedTime(entity.getEcVerifiedTime());
		gi.setAwardStruct(entity.getAwardStruct());
		gi.setAdminEndCancelTime(entity.getAdminCanCancelEndTime());
		gi.setIssuewarnExceptionTime(entity.getIssuewarnExceptionTime());
		gi.setNumberRecord(entity.getNumberRecord());
		gi.setNumberUpdateCount(entity.getNumberUpdateCount());
		gi.setNumberUpdateTime(entity.getNumberUpdateTime());
		return gi;
	}

	public static TrendType convertTrendTypeViaIntValue(int parseInt) {
		switch (parseInt) {
		case 1:
			return TrendType.WEISHU;
		case 2:
			return TrendType.FENBU;
		case 3:
			return TrendType.KUADU;
		case 4:
			return TrendType.ZUXUAN;
		case 5:
			return TrendType.HEZHI;
		default:
			return TrendType.WEISHU;
		}
	}

}
