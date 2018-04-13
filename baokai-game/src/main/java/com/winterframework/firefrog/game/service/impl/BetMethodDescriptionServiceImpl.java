package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameBettypeStatusDao;
import com.winterframework.firefrog.game.dao.IGameHelpCheckDao;
import com.winterframework.firefrog.game.dao.IGameHelpDao;
import com.winterframework.firefrog.game.dao.IGameSeriesCheckDao;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.game.dao.vo.GameHelp;
import com.winterframework.firefrog.game.dao.vo.GameHelpAndBonus;
import com.winterframework.firefrog.game.dao.vo.GameHelpCheck;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.dao.vo.GameSeriesCheck;
import com.winterframework.firefrog.game.entity.BetMethodDescription;
import com.winterframework.firefrog.game.entity.VOConvert;
import com.winterframework.firefrog.game.enums.Separator;
import com.winterframework.firefrog.game.service.IBetMethodDescriptionService;
import com.winterframework.firefrog.game.service.IGameBetTypeStatusService;
import com.winterframework.firefrog.game.service.IGameUserAwardGroupService;
import com.winterframework.firefrog.game.web.dto.BetMethodDescriptionJoinBonus;

@Service("betMethodDescriptionServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class BetMethodDescriptionServiceImpl implements IBetMethodDescriptionService {

	@Resource(name = "gameHelpDaoImpl")
	private IGameHelpDao gameHelpDaoImpl;

	@Resource(name = "gameHelpCheckDaoImpl")
	private IGameHelpCheckDao gameHelpCheckDaoImpl;

	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesDaoImpl;

	@Resource(name = "gameSeriesCheckDaoImpl")
	private IGameSeriesCheckDao gameSeriesCheckDaoImpl;

	@Resource(name = "gameBettypeStatusDaoImpl")
	private IGameBettypeStatusDao gameBettypeStatusDao;
	@Resource(name = "gameBetTypeStatusServiceImpl")
	private IGameBetTypeStatusService gameBetTypeStatusService;
	@Resource(name = "gameUserAwardGroupServiceImpl")
	private IGameUserAwardGroupService gameUserAwardGroupService;	

	@Override
	public List<BetMethodDescription> queryBetMethodDescription(long lotteryid) throws Exception {

		List<BetMethodDescription> resultList = new ArrayList<BetMethodDescription>();

		//1、查询主表中玩法描述列表
		List<GameHelp> gameHelpList = gameHelpDaoImpl.getGameHelpByLotteryId(lotteryid);
		//2、查询check表中玩法描述信息
		List<GameHelpCheck> GameHelpCheckList = gameHelpCheckDaoImpl.getGameHelpCheckByLotteryId(lotteryid);

		if (GameHelpCheckList.size() == 0) {
			for (GameHelp gh : gameHelpList) {
				BetMethodDescription bl = VOConvert.gameHelp2BetMethodDescription(gh);
				resultList.add(bl);
			}
		} else {
			for (GameHelp gh : gameHelpList) {
				BetMethodDescription bmd = null;
				for (GameHelpCheck ghc : GameHelpCheckList) {
					//3、如果发现check表中有跟主表匹配的数据，则显示check表中数据，如果没有，则显示主表数据
					if (ghc.getGameGroupCode() == gh.getGameGroupCode() && ghc.getGameSetCode() == gh.getGameSetCode()
							&& ghc.getBetMethodCode() == gh.getBetMethodCode()) {
						bmd = VOConvert.gameHelpCheck2BetMethodDescription(ghc);
						//如发现玩法描述提示不同，则添加对比数据
						if (ghc.getGamePromptDes()!=null && gh.getGamePromptDes()!=null && !ghc.getGamePromptDes().equals(gh.getGamePromptDes())) {
							bmd.setGamePromptDes_bak(gh.getGamePromptDes());
						}
						//如发现投注示例不同，则添加对比数据
						if (ghc.getGameExamplesDes() != null) {
							if (!ghc.getGameExamplesDes().equals(gh.getGameExamplesDes())) {
								bmd.setGameExamplesDes_bak(gh.getGameExamplesDes());
							}
						}
						break;
					}
				}
				if (bmd == null) {
					bmd = VOConvert.gameHelp2BetMethodDescription(gh);
				}
				resultList.add(bmd);
			}
		}

		return resultList;
	}

	@Override
	public void modifyBetMethodDescription(List<BetMethodDescription> betMethodDescriptionList, String lotteryHelpDes,
			long lotteryid) throws Exception {

		// betMethod
		List<GameHelpCheck> gameHelpCheckList = new ArrayList<GameHelpCheck>();
		GameHelpCheck ghc = new GameHelpCheck();
		for (BetMethodDescription bmd : betMethodDescriptionList) {
			ghc = VOConvert.betMethodDescription2GameHelpCheck(bmd);
			ghc.setBetTypeCode(bmd.getGameGroupCode() + "_" + bmd.getGameSetCode() + "_" + bmd.getBetMethodCode());
			ghc.setCheckStatus(3);
			ghc.setCreteatTime(new Date());
			gameHelpCheckList.add(ghc);
		}

		List<GameHelpCheck> ghcs = gameHelpCheckDaoImpl.getGameHelpCheckByLotteryId(lotteryid);
		if (ghcs == null || ghcs.size() == 0) {
			gameHelpCheckDaoImpl.insertAll(gameHelpCheckList);
		} else {
			for (GameHelpCheck g : gameHelpCheckList) {
				gameHelpCheckDaoImpl.updateToCheck(g);
			}
		}

		// lottery
		GameSeriesCheck gsc = gameSeriesCheckDaoImpl.getGameSeriesCheckByLotteryId(lotteryid, 1);
		if (gsc == null) {
			GameSeries gs = gameSeriesDaoImpl.getByLotteyId(lotteryid);
			gsc = VOConvert.gameSeries2GameSeriesCheck(gs);
			gsc.setCheckStatus(3);
			gsc.setCreateTime(new Date());
			gsc.setLotteryHelpDes(lotteryHelpDes);
			//标记为玩法描述
			gsc.setCheckType(1);
			gameSeriesCheckDaoImpl.insertOne(gsc);
		} else {
			gsc.setCheckStatus(3);
			gsc.setLotteryHelpDes(lotteryHelpDes);
			gsc.setCheckType(1);
			gsc.setUpdateTime(new Date());
			gameSeriesCheckDaoImpl.updateToCheck(gsc);
		}
		gameSeriesDaoImpl.updateGameSeriesChangeStatus(lotteryid, 5, 3);
		gameSeriesDaoImpl.updateLastModifyDate(lotteryid);

	}

	@Override
	public void checkBetMethodDescription(Long lotteryid, Long auditType) throws Exception {
		GameSeriesCheck gsc = gameSeriesCheckDaoImpl.getGameSeriesCheckByLotteryId(lotteryid, 1);
		//auditType为审核结果，1为通过，2为不通过
		if (auditType == 1) {
			gsc.setCheckStatus(4);//审核通过
			gameSeriesDaoImpl.updateGameSeriesChangeStatus(lotteryid, 5, 4);
			gameSeriesCheckDaoImpl.updateToCheck(gsc);
		} else {
			gsc.setCheckStatus(5);//审核不通过
			gameSeriesDaoImpl.updateGameSeriesChangeStatus(lotteryid, 5, 5);
			this.cancelModify(lotteryid);
		}
		
	}

	@Override
	public void publishBetMethodDescription(Long lotteryid, Long auditType) throws Exception {
		//auditType为审核结果，1为通过，2为不通过
		if (auditType == 1) {
			//更新开奖周期说明
			GameSeriesCheck gsc = gameSeriesCheckDaoImpl.getGameSeriesCheckByLotteryId(lotteryid, 1);

			GameSeries gs = gameSeriesDaoImpl.getByLotteyId(lotteryid);
			if (gsc != null && !"".equals(gsc)) {
				gs.setLotteryHelpDes(gsc.getLotteryHelpDes());
			}
			gameSeriesDaoImpl.updateGameSeries(gs);

			//更新玩法描述
			List<GameHelpCheck> gameHelpCheckList = gameHelpCheckDaoImpl.getGameHelpCheckByLotteryId(lotteryid);

			List<GameHelp> gameHelpList = new ArrayList<GameHelp>();
			GameHelp gh = null;
			if (gameHelpCheckList != null && gameHelpCheckList.size() > 0) {
				for (GameHelpCheck ghc : gameHelpCheckList) {
					gh = VOConvert.gameHelpCheck2GameHelp(ghc);
					gh.setBetTypeCode(ghc.getGameGroupCode() + "_" + ghc.getGameSetCode() + "_"
							+ ghc.getBetMethodCode());
					gh.setUpdateTime(new Date());
					gameHelpList.add(gh);
				}
			}
			gameHelpDaoImpl.updateGameHelpList(gameHelpList);
			gameHelpCheckDaoImpl.removeGameHelpCheckByLotteryId(lotteryid);
			gameSeriesCheckDaoImpl.removeByLotteryId(lotteryid, 1);
			gameSeriesDaoImpl.updateGameSeriesChangeStatus(lotteryid, 5, 1);
		} else {
			GameSeriesCheck gsc = gameSeriesCheckDaoImpl.getGameSeriesCheckByLotteryId(lotteryid, 1);
			if (gsc != null && !"".equals(gsc)) {
				gsc.setCheckStatus(6);//发布不通过
				gameSeriesCheckDaoImpl.updateToCheck(gsc);
			}
			gameHelpCheckDaoImpl.checkGameHelpCheck(lotteryid, auditType);
			gameSeriesDaoImpl.updateGameSeriesChangeStatus(lotteryid, 5, 6);
			this.cancelModify(lotteryid);
		}
	}

	private void cancelModify(Long lotteryid) throws Exception {
		gameHelpCheckDaoImpl.removeGameHelpCheckByLotteryId(lotteryid);
		gameSeriesCheckDaoImpl.removeByLotteryId(lotteryid, 1);
		gameSeriesDaoImpl.updateGameSeriesChangeStatus(lotteryid, 5, 1);
	}

	@Override
	public String queryLotteryHeloDesBak(Long lotteryid) {
		GameSeriesCheck lotteyHelpDes = gameSeriesCheckDaoImpl.getLotteryHelpDes(lotteryid);
		String lotteyHelpDes_bak = gameSeriesDaoImpl.getLotteryHelpDes(lotteryid);
		if (null != lotteyHelpDes && !lotteyHelpDes.getLotteryHelpDes().equals(lotteyHelpDes_bak)) {
			return lotteyHelpDes_bak;
		}
		return null;
	}

	@Override
	public String queryLotteryHelpDes(Long lotteryid) {
		//先查找check表是否有数据，没有则填充主表中数据
		String lotteyHelpDes = "";
		GameSeriesCheck gameSeriesCheck = gameSeriesCheckDaoImpl.getLotteryHelpDes(lotteryid);

		if (gameSeriesCheck == null) {
			lotteyHelpDes = gameSeriesDaoImpl.getLotteryHelpDes(lotteryid);
		} else {
			lotteyHelpDes = gameSeriesCheck.getLotteryHelpDes();
		}

		return lotteyHelpDes;
	}

	@Override
	public Integer queryLotteryHelpCheckStatus(Long lotteryid) {
		//先查找check表是否有数据，没有则填充主表中数据
		GameSeriesCheck status = gameSeriesCheckDaoImpl.getLotteryHelpDes(lotteryid);
		if (status != null)
			return status.getCheckStatus();
		return null;
	}

	@Override
	public BetMethodDescription queryDescByBetMethod(long lotteryid, int gameGroupCode, int gameSetCode,
			int betMethodCode) {
		GameHelp gh = gameHelpDaoImpl.getByBetMethod(lotteryid, gameGroupCode, gameSetCode, betMethodCode);
		if (gh != null) {
			return VOConvert.gameHelp2BetMethodDescription(gh);
		}
		return null;
	}

	@Override
	public BetMethodDescriptionJoinBonus queryDescByBetMethod(long lotteryid, long userid, int gameGroupCode,
			int gameSetCode, int betMethodCode) throws Exception{
		GameHelpAndBonus gh = gameHelpDaoImpl.getByBetMethod(lotteryid, userid, gameGroupCode, gameSetCode,
				betMethodCode);
		if (gh != null) {
			String betTypeCode=gameGroupCode+Separator.underline+gameSetCode+Separator.underline+betMethodCode;
			GameBettypeStatus betTypeStatus=gameBetTypeStatusService.getByLotteryIdAndBetTypeCode(lotteryid, betTypeCode);
			if(null==betTypeStatus){
				throw new Exception("game bet type status not exist.lotteryId="+lotteryid+" & betTypeCode="+betTypeCode);
			}
			gh.setTheoryBonus(null==betTypeStatus.getTheoryBonus()?0L:betTypeStatus.getTheoryBonus());
			Long retPoint=gameUserAwardGroupService.getRetPointByUserIdAndLotteryIdAndBetTypeCode(userid, lotteryid, betTypeCode);
			gh.setRetPoint(null==retPoint?0L:retPoint);
			return VOConvert.gameHelp2BetMethodAndBonusDescription(gh);
		}
		return null;
	}

	@Override
	public List<GameBettypeStatus> getAllBettypeStatus() {
		return gameBettypeStatusDao.getAll();
	}

}
