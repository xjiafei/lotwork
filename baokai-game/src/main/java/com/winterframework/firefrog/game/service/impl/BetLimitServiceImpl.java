package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.winterframework.firefrog.game.dao.*;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.dao.vo.GameSeriesCheck;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.vo.GameMultiple;
import com.winterframework.firefrog.game.dao.vo.GameMultipleCheck;
import com.winterframework.firefrog.game.entity.BetLimit;
import com.winterframework.firefrog.game.entity.BetLimitAssist;
import com.winterframework.firefrog.game.entity.GameAwardEntity;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.VOConvert;
import com.winterframework.firefrog.game.service.IBetLimitService;

/** 
* @ClassName: BetLimitServiceImpl 
* @Description: 投注限制Service实现类 
* @author Denny 
* @date 2013-8-20 下午4:15:01 
*  
*/
@Service("betLimitServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class BetLimitServiceImpl implements IBetLimitService {

	@Resource(name = "gameMultipleDaoImpl")
	private IGameMultipleDao gameMultipleDaoImpl;

	@Resource(name = "gameMultipleCheckDaoImpl")
	private IGameMultipleCheckDao gameMultipleCheckDaoImpl;

	@Resource(name = "gameAwardGroupDaoImpl")
	private IGameAwardGroupDao gameAwardGroupDaoImpl;

	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesDaoImpl;

	@Resource(name = "gameSeriesCheckDaoImpl")
	private IGameSeriesCheckDao gameSeriesCheckDaoImpl;

	//目前改成只要查询投注限制信息，就整合主表和check表的数据进行显示。
	@Override
	public List<BetLimit> queryBetLimit(long lotteryid) throws Exception {
		// 投注接口需要的最大奖金金额接口需要单独提供，不需要做比较，因为用户的投注奖金组只有一个
		// 由奖金组接口取得当前最大奖金额
		List<GameAwardEntity> maxGameAwardList = gameAwardGroupDaoImpl.queryGameAwardEntityByLotteryId(lotteryid);
		List<BetLimit> resultList = new ArrayList<BetLimit>();

		//1、查询check表数据
		List<GameMultipleCheck> gameMultipleCheckList = gameMultipleCheckDaoImpl
				.getGameMultipleCheckByLotteryId(lotteryid);
		//2、查询主表数据
		List<GameMultiple> gameMultipleList = gameMultipleDaoImpl.getGameMultipleByLotteryId(lotteryid);

		//3、对比两表数据，如果check表中没有主表中对应的数据，则读取主表中的数据
		if (gameMultipleCheckList.size() == 0) {
			for (GameMultiple gm : gameMultipleList) {
				BetLimit bl = new BetLimit();
				bl = VOConvert.gameMultiple2BetLimit(gm);
				Long maxBonus = calculateMaxBonusOneBet(bl, maxGameAwardList);
				if(bl.getSpecialLimit() != null){
					//如果是特殊限制倍數，則回傳所有玩法獎金
					String maxBonusDetail = "";
					for(GameAwardEntity ga:maxGameAwardList){
						maxBonusDetail += ga.getActualBonus()+",";
					}
					bl.setSpecialMaxBonus(maxBonusDetail.substring(0, maxBonusDetail.length()-1));
					
					
				}else{
					bl.setMaxBonus(maxBonus);
				}
				bl.setId(gm.getId());
				resultList.add(bl);
			}
		} else {
			for (GameMultiple gm : gameMultipleList) {
				BetLimit bl = null;
				for (GameMultipleCheck gmc : gameMultipleCheckList) {
					//4、如果发现check表中有跟主表匹配的数据，则显示check表中数据，如果没有，则显示主表数据
					if (gmc.getGameGroupCode() == gm.getGameGroupCode() && gmc.getGameSetCode() == gm.getGameSetCode()
							&& gmc.getBetMethodCode() == gm.getBetMethodCode()) {
						bl = VOConvert.gameMultipleCheck2BetLimit(gmc);
						//如果发现check表中倍数限制跟主表不同，则增加对比数据
						if (gmc.getMaxMultiple() != gm.getMaxMultiple()) {
							bl.setMaxMultiple_bak(gm.getMaxMultiple());
						}
						if(gmc.getSpecialMultiple() != gm.getSpecialMultiple()){
							bl.setSpecialLimit_bak(gm.getSpecialMultiple());
						}
						bl.setId(gm.getId());
						break;
					}
				}
				//没有找到匹配数据，则读取主表中数据
				if (bl == null) {
					bl = VOConvert.gameMultiple2BetLimit(gm);
					bl.setId(gm.getId());
				}
				Long maxBonus  = 0l;
				if(bl.getSpecialLimit() != null){
					//如果是特殊限制倍數，則回傳所有玩法獎金
					String maxBonusDetail = "";
					for(GameAwardEntity ga:maxGameAwardList){
						maxBonusDetail += ga.getActualBonus()+",";
					}
					bl.setSpecialMaxBonus(maxBonusDetail.substring(0, maxBonusDetail.length()-1));
				}else{
					maxBonus =calculateMaxBonusOneBet(bl, maxGameAwardList);
				}
				bl.setMaxBonus(maxBonus);
				resultList.add(bl);
			}
		}

		return resultList;
	}

	/** 
	* @Title: calculateMaxBonusOneBet 
	* @Description: 计算投注方式的当前最大奖金额度的奖金 
	*/
	private Long calculateMaxBonusOneBet(BetLimit bl, List<GameAwardEntity> maxGameAwardList) {
		Long maxValue = 0l;
		for (GameAwardEntity ga : maxGameAwardList) {
			GameBetType gameBetType = ga.getGameBetType();

			if ((gameBetType.getGameGroupCode() == bl.getGameGroupCode())
					&& (gameBetType.getGameSetCode() == bl.getGameSetCode())) {
				if (bl.getGameGroupCode() == 18 && bl.getGameSetCode() == 16) {
					if (gameBetType.getMethodType() != null) {
						if (bl.getBetMethodCode() == 30) {
							if (gameBetType.getMethodType() == 39 || gameBetType.getMethodType() == 40
									|| gameBetType.getMethodType() == 41) {
								maxValue = ga.getActualBonus().longValue() > maxValue ? ga.getActualBonus().longValue()
										: maxValue;
							}
						} else if (bl.getBetMethodCode() == 31) {
							if (gameBetType.getMethodType() == 42 || gameBetType.getMethodType() == 45
									|| gameBetType.getMethodType() == 46) {
								maxValue = ga.getActualBonus().longValue() > maxValue ? ga.getActualBonus().longValue()
										: maxValue;
							}
						} else {
							if (gameBetType.getMethodType() == 47 || gameBetType.getMethodType() == 48
									|| gameBetType.getMethodType() == 49 || gameBetType.getMethodType() == 50) {
								maxValue = ga.getActualBonus().longValue() > maxValue ? ga.getActualBonus().longValue()
										: maxValue;
							}
						}
					}
				} else {
					if (gameBetType.getBetMethodCode() == bl.getBetMethodCode()) {

						maxValue = ga.getActualBonus().longValue() > maxValue ? ga.getActualBonus().longValue()
								: maxValue;
					}
				}
				/*if (gameBetType.getMethodType() == null) {
					return ga.getActualBonus().longValue();
				} else {*/

				/*}*/
			}
		}
		return maxValue;
	}

	@Override
	public void modifyBetLimit(List<BetLimit> betLimitList, Long lotteryid) throws Exception {
		GameMultipleCheck gmc;

		for (BetLimit bl : betLimitList) {
			gmc = VOConvert.betLimit2GameMultipleCheck(bl);

			//判断check表中是否有匹配数据
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("lotteryid", bl.getLotteryid());
			map.put("betTypeCode", gmc.getBetTypeCode());
			GameMultipleCheck gameMultipleCheck = gameMultipleCheckDaoImpl.getGameMultipleCheckByCondition(map);
			//发现check表中有数据，倍数限制如有改动则更新，如没有数据，则从主表中复制数据到check表中来。
			if (null != gameMultipleCheck) {
				if (bl.getMaxMultiple() != gameMultipleCheck.getMaxMultiple() || bl.getSpecialLimit() != gameMultipleCheck.getSpecialMultiple()) {
					gameMultipleCheck.setMaxMultiple(bl.getMaxMultiple());
					gameMultipleCheck.setStatus(3);
					gameMultipleCheckDaoImpl.updateGameMultipleCheck(gameMultipleCheck);
				}
			} else {
				gmc.setCreateTime(new Date());
				gmc.setStatus(3);
				gameMultipleCheckDaoImpl.addGameMultipleCheck(gmc);
			}
		}
		// lottery
		GameSeriesCheck gsc = gameSeriesCheckDaoImpl.getGameSeriesCheckByLotteryId(lotteryid, 3);
		if (gsc == null) {
			GameSeries gs = gameSeriesDaoImpl.getByLotteyId(lotteryid);
			gsc = VOConvert.gameSeries2GameSeriesCheck(gs);
			gsc.setCheckStatus(3);
			gsc.setCreateTime(new Date());
			//标记为玩法描述
			gsc.setCheckType(3);
			gameSeriesCheckDaoImpl.insertOne(gsc);
		} else {
			gsc.setCheckStatus(3);
			gsc.setCheckType(3);
			gameSeriesCheckDaoImpl.updateStatus(lotteryid, 3, 3);
		}
		gameSeriesDaoImpl.updateGameSeriesChangeStatus(lotteryid, 4, 3);
		gameSeriesDaoImpl.updateLastModifyDate(lotteryid);
	}

	private void cancelModifyForCheck(Long lotteryid) throws Exception {
		gameMultipleCheckDaoImpl.removeGameMultipleCheckByLotteryId(lotteryid);
		gameSeriesCheckDaoImpl.removeByLotteryId(lotteryid, 3);
		gameSeriesDaoImpl.updateGameSeriesChangeStatus(lotteryid, 4, 1);
	}

	@Override
	public void checkBetLimit(Long lotteryid, Long auditType) throws Exception {
		gameMultipleCheckDaoImpl.checkGameMultipleCheck(lotteryid, auditType);
		Integer status = auditType == 1 ? 4 : 5;

		gameSeriesCheckDaoImpl.updateStatus(lotteryid, status, 3);
		gameSeriesDaoImpl.updateGameSeriesChangeStatus(lotteryid, 4, status);
		if(status==5){
			cancelModifyForCheck(lotteryid);
		}
	}

	@Override
	public void publishBetLimit(Long lotteryid, Long publishType) throws Exception {
		//1为发布通过，2为发布不通过
		if (publishType == 1) {
			List<GameMultipleCheck> gameMultipleCheckList = gameMultipleCheckDaoImpl
					.getGameMultipleCheckByLotteryId(lotteryid);

			List<GameMultiple> gameMultipleList = new ArrayList<GameMultiple>();
			GameMultiple gm = new GameMultiple();
			if (gameMultipleCheckList != null && gameMultipleCheckList.size() > 0) {
				for (GameMultipleCheck gmc : gameMultipleCheckList) {
					gm = VOConvert.gameMultipleCheck2GameMultiple(gmc);
					gm.setUpdateTime(new Date());
					gameMultipleList.add(gm);
				}
			}
			

			gameMultipleDaoImpl.updateGameMultipleList(gameMultipleList);
			gameMultipleCheckDaoImpl.removeGameMultipleCheckByLotteryId(lotteryid);
			gameSeriesCheckDaoImpl.removeByLotteryId(lotteryid, 3);
			gameSeriesDaoImpl.updateGameSeriesChangeStatus(lotteryid, 4, 1);
			
			List<GameMultiple> gameMultipleDbList = gameMultipleDaoImpl.getGameMultipleByLotteryId(lotteryid);
			for(GameMultiple gameMultipleDb:gameMultipleDbList){
				if(gameMultipleDb.getMaxMultiple()==-1){
					List<BetLimitAssist> assists = gameMultipleDaoImpl.getGameMultipleAssistParentId(gameMultipleDb.getId());
					if(assists != null && !assists.isEmpty()){
						for(BetLimitAssist assist:assists){
							assist.setMaxMultiple(assist.getMaxMultiple_bak());
							gameMultipleDaoImpl.modifyBetLimitAssist(assist);
						}
					}
				}
			}
		} else {
			gameMultipleCheckDaoImpl.publishGameMultipleCheck(lotteryid, publishType);
			gameSeriesCheckDaoImpl.updateStatus(lotteryid, 6, 3);
			gameSeriesDaoImpl.updateGameSeriesChangeStatus(lotteryid, 4, 6);
			cancelModifyForCheck(lotteryid);
		}

	}

	/**
	 * 投注界面首次加载该彩种所有投注倍数
	 */
	@Override
	public List<BetLimit> queryBetLimitByBet(long lotteryid) throws Exception {
		List<BetLimit> resultList = new ArrayList<BetLimit>();

		List<GameMultiple> gameMultipleList = gameMultipleDaoImpl.getGameMultipleByLotteryId(lotteryid);

		for (GameMultiple gm : gameMultipleList) {
			List<BetLimitAssist> assistList = gameMultipleDaoImpl.getGameMultipleAssistParentId(gm.getId());
			
			BetLimit bl = new BetLimit();
			bl = VOConvert.gameMultiple2BetLimit(gm);
			bl.setMaxBonus(0L);
			bl.setBetLimitAssist(assistList);
			resultList.add(bl);
		}

		return resultList;
	}

	/**
	* Title: getMaxGameIssue
	* Description:
	* @param lotteryId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IBetLimitService#getMaxGameIssue(java.lang.Long) 
	*/
	@Override
	public Long getMaxGameIssue(Long lotteryId) throws Exception {
		return gameSeriesDaoImpl.getMaxGameIssue(lotteryId);
	}

	@Override
	public Integer betLimitStatus(Long lotteryId) throws Exception {
		Integer status = gameSeriesCheckDaoImpl.getStatus(lotteryId, 3);
		return status == null ? 1 : status;
	}

	@Override
	public void modifyBetLimitAssist(List<BetLimitAssist> assists) throws Exception {
		for(BetLimitAssist betLimitAssist:assists){
			gameMultipleDaoImpl.modifyBetLimitAssist(betLimitAssist);
		}
	}
}
