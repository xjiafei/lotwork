package com.winterframework.firefrog.game.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGameIssueRuleCheckDao;
import com.winterframework.firefrog.game.dao.IGameIssueRuleDao;
import com.winterframework.firefrog.game.dao.IGameIssueRuleLogDao;
import com.winterframework.firefrog.game.dao.IGameIssueTemplateCheckDao;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameIssueRuleLog;
import com.winterframework.firefrog.game.entity.GameIssueRuleEntity;
import com.winterframework.firefrog.game.entity.GameIssueRuleEntity.RuleType;
import com.winterframework.firefrog.game.entity.GameIssueTemplateEntity;
import com.winterframework.firefrog.game.exception.GameIssueCommonRuleValidForPublishHasBeenExistException;
import com.winterframework.firefrog.game.exception.GameIssueRuleEffectiveTimeAfterEndTimeException;
import com.winterframework.firefrog.game.exception.GameIssueRuleEffectiveTimeBeforeCurrentTimeException;
import com.winterframework.firefrog.game.exception.GameIssueRuleOverlapErrorException;
import com.winterframework.firefrog.game.exception.GameIssueRuleStartTimeShouldSuitableException;
import com.winterframework.firefrog.game.exception.GameIssueTemplateOverlapErrorException;
import com.winterframework.firefrog.game.exception.GameIssueTemplateSalePeriodTimeShouledBiggerThenScheduleStopTimeException;
import com.winterframework.firefrog.game.service.IGameIssueRuleService;

@Service("gameIssueRuleServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameIssueRuleServiceImpl implements IGameIssueRuleService {

	@Resource(name = "gameIssueRuleCheckDaoImpl")
	private IGameIssueRuleCheckDao gameIssueRuleCheckDao;

	@Resource(name = "gameIssueRuleDaoImpl")
	private IGameIssueRuleDao gameIssueRuleDao;

	@Resource(name = "gameIssueTemplateCheckDaoImpl")
	private IGameIssueTemplateCheckDao gameIssueTemplateCheckDao;

	@Resource(name = "gameIssueRuleLogDaoImpl")
	private IGameIssueRuleLogDao gameIssueRuleLogDao;

	@Resource(name = "gameIssueDaoImpl")
	private IGameIssueDao gameIssueDao;

	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesDaoImpl;

	@Override
	public void addOrUpdateCommenOrSpecialGameIssueRule(GameIssueRuleEntity gameIssueRuleEntity, Integer operationType) throws Exception {
		if (!checkRuleOverLap(gameIssueRuleEntity) && operationType == 1) {
			throw new GameIssueRuleOverlapErrorException();
		}
		if (checkTemplateOverLap(gameIssueRuleEntity) && operationType == 1) {
			throw new GameIssueTemplateOverlapErrorException();
		}
		if (gameIssueRuleEntity.getRuleStartTime().before(DateUtils.currentDate())) {
			throw new GameIssueRuleEffectiveTimeBeforeCurrentTimeException();
		}
		if (null != gameIssueRuleEntity.getRuleEndTime()) {
			if (gameIssueRuleEntity.getRuleStartTime().after(gameIssueRuleEntity.getRuleEndTime())) {
				throw new GameIssueRuleEffectiveTimeAfterEndTimeException();
			}
		}
		/*Date date = DateUtils.getStartTimeOfDate(DateUtils.addDays(new Date(),
				(int) GameIssueRuleEntity.AUDIT_LIMIT_DAY));
		if (gameIssueRuleEntity.getRuleStartTime().before(date)) {//验证规则生效时间在n天之后
			throw new GameIssueRuleStartTimeShouldSuitableException();
		}*/
		/*GameIssue gameIssue = gameIssueDao.getMaxGameIssuesByLotteryId(gameIssueRuleEntity.getLottery().getLotteryId());
		if (gameIssueRuleEntity.getRuleStartTime().before(gameIssue.getSaleEndTime())) {
			throw new GameIssueRuleStartTimeShouldSuitableException();
		}*/
		for (GameIssueTemplateEntity gite : gameIssueRuleEntity.getGameIssueTemplateEntitys()) {

			if (gite.getScheduleStopTime() >= gite.getSalePeriodTime().longValue()) {
				throw new GameIssueTemplateSalePeriodTimeShouledBiggerThenScheduleStopTimeException();
			}
		}
		if (operationType == 1) {//新增
			gameIssueRuleCheckDao.saveCommonOrSpecialGameIssueRule(gameIssueRuleEntity);
		} else {//这里的修改是对已有的数据进行修改，

			//2013-11-11 如果是修改，先判断check是否存在记录的， 如果存在先获取获取chekc表里的信息，然后再修改；如果check表不存在，则从主表里获取。
			//情况一：前台新增，然后修改；（主表没有数据)
			//情况二：原主表里存在，然后修改；(主表和check都有数据) 

			GameIssueRuleEntity gameIssueRuleCheck = gameIssueRuleCheckDao.getGameIssueRuleCheckByLotteryIdAndRuleId(
					gameIssueRuleEntity.getLottery().getLotteryId(), gameIssueRuleEntity.getId());

			if (null != gameIssueRuleCheck) { //如果check表存在
				//先获取主表里的信息。
				boolean isUpdate = false;
				GameIssueRuleEntity gire = gameIssueRuleDao.getGameIssueRuleById(gameIssueRuleEntity.getId()); //从主表获取信息。
				if (null == gire) {
					//存在这种情况，新增后，再继续修改。现在只存在于check表中
					gire = gameIssueRuleCheckDao.getGameIssueRuleCheckById(gameIssueRuleEntity.getId()); //从check表中去
					isUpdate = true;
				}
				if (gire.getRuleType() == RuleType.COMMEN) {
					List<GameIssueRuleEntity> gires = gameIssueRuleCheckDao
							.getCommenGameIssuesByMainRuleId(gameIssueRuleEntity.getRuleid());
					if (!gires.isEmpty() && gires.size() != 0) {
						throw new GameIssueCommonRuleValidForPublishHasBeenExistException(); //"不能存在两条有效待发布常规规则"
					}
				}

				gameIssueRuleEntity.setRuleid(gameIssueRuleEntity.getId()); //与主表相关联
				gameIssueRuleEntity.setCreateTime(new Date());
				if (isUpdate) {
					gameIssueRuleCheckDao.updateCommonOrSpecialGameIssuseRule(gameIssueRuleEntity);
					return;
				}
				//然后保存到check表中。
				//				gameIssueRuleCheckDao.updateCommonOrSpecialGameIssuseRule(gameIssueRuleEntity);
				//如果是从check表中修改的，则吧能更新关联字段。
				gameIssueRuleCheckDao.saveCommonOrSpecialGameIssueRule(gameIssueRuleEntity);
			} else {

				if (null == gameIssueRuleCheck) {
					GameIssueRuleEntity gire = gameIssueRuleDao.getGameIssueRuleById(gameIssueRuleEntity.getId()); //从主表获取信息。

					if (gire.getRuleType() == RuleType.COMMEN) {
						List<GameIssueRuleEntity> gires = gameIssueRuleCheckDao
								.getCommenGameIssuesByMainRuleId(gameIssueRuleEntity.getId());
						if (!gires.isEmpty() && gires.size() != 0) {
							throw new GameIssueCommonRuleValidForPublishHasBeenExistException(); //"不能存在两条有效待发布常规规则"
						}
					}

					gameIssueRuleEntity.setRuleid(gameIssueRuleEntity.getId()); //与主表相关联
					gameIssueRuleEntity.setCreateTime(new Date());
					gameIssueRuleEntity.setIssueRulesName(gire.getIssueRulesName());
					Long id = gameIssueRuleCheckDao.saveCommonOrSpecialGameIssueRule(gameIssueRuleEntity);
					gameIssueRuleEntity.setRuleid(id);
				}

				gameIssueRuleCheckDao.updateCommonOrSpecialGameIssuseRule(gameIssueRuleEntity);

			}
			gameSeriesDaoImpl.updateLastModifyDate(gameIssueRuleEntity.getLottery().getLotteryId());

		}

	}
	
	public void updateCommonRuleScheduleStopTime(Long lotteryId,Integer scheduleStopTime) throws Exception{
		
	}
	

	/** 
	* @Title: checkTemplateOverLap 
	* @Description: 验证分段奖期是否有重叠时间段 
	* @param gameIssueRuleEntity
	* @return
	*/
	private boolean checkTemplateOverLap(GameIssueRuleEntity gameIssueRuleEntity) {
		boolean flag = false;
		List<GameIssueTemplateEntity> gameIssueTemplateEntitys = gameIssueRuleEntity.getGameIssueTemplateEntitys();
		for (int i = 0; i < gameIssueTemplateEntitys.size(); i++) {
			for (int j = 0; j < gameIssueTemplateEntitys.size(); j++) {
				if (j != i) {
					flag = checkOverLap(gameIssueTemplateEntitys.get(i).getFirstAwardTime(), gameIssueTemplateEntitys
							.get(i).getLastAwardTime(), gameIssueTemplateEntitys.get(j).getFirstAwardTime(),
							gameIssueTemplateEntitys.get(j).getLastAwardTime());
					if (flag == true) {
						return true;
					}
				}
			}
		}

		return flag;
	}

	private boolean checkOverLap(Date leftStartDate, Date leftEndDate, Date rightStartDate, Date rightEndDate) {
		return ((leftStartDate.getTime() >= rightStartDate.getTime()) && leftStartDate.getTime() < rightEndDate
				.getTime())
				|| ((leftStartDate.getTime() > rightStartDate.getTime()) && leftStartDate.getTime() <= rightEndDate
						.getTime())
				|| ((rightStartDate.getTime() >= leftStartDate.getTime()) && rightStartDate.getTime() < leftEndDate
						.getTime())
				|| ((rightStartDate.getTime() > leftStartDate.getTime()) && rightStartDate.getTime() <= leftEndDate
						.getTime());
	}

	/** 
	* @Title: checkOverLap 
	* @Description:验证规则重叠时间段，按照规则 修改的时候，也是形成新的记录
	* @param gameIssueRuleEntity
	*/
	private boolean checkRuleOverLap(GameIssueRuleEntity gameIssueRuleEntity) {
		List<GameIssueRuleEntity> GameIssueRuleEntitys = null;
		if (gameIssueRuleEntity.getRuleEndTime() == null) {
			GameIssueRuleEntitys = gameIssueRuleCheckDao.querySpecialGameIssueRuleCheckByLotteryIdAndPeriodTime(
					gameIssueRuleEntity.getLottery().getLotteryId(), gameIssueRuleEntity.getRuleStartTime(),
					gameIssueRuleEntity.getRuleEndTime(), gameIssueRuleEntity.getRuleid());
		} else {
			GameIssueRuleEntitys = gameIssueRuleCheckDao.querySpecialGameIssueRuleCheckByLotteryAndTime(
					gameIssueRuleEntity.getLottery().getLotteryId(), gameIssueRuleEntity.getRuleStartTime(),
					gameIssueRuleEntity.getRuleEndTime(), gameIssueRuleEntity.getRuleid());
		}
		int num = 0;
		if (GameIssueRuleEntitys != null) {
			for (GameIssueRuleEntity gameIssueRuleEntity2 : GameIssueRuleEntitys) {
				if (gameIssueRuleEntity2.getStatus().longValue() == 2L) {
					num++;
				}
			}
		}

		if (GameIssueRuleEntitys == null || GameIssueRuleEntitys.size() == num) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void addOrUpdateStopGameIssueRule(GameIssueRuleEntity gameIssueRuleEntity, Integer operationType)
			throws Exception {
		/*if (!checkRuleOverLap(gameIssueRuleEntity)) {
			throw new GameIssueRuleOverlapErrorException();
		}*/
		Date date = DateUtils.getStartTimeOfDate(DateUtils.addDays(new Date(),
				(int) GameIssueRuleEntity.AUDIT_LIMIT_DAY));

		GameIssue gameIssue = gameIssueDao.getMaxGameIssuesByLotteryId(gameIssueRuleEntity.getLottery().getLotteryId());
		if (gameIssueRuleEntity.getRuleStartTime().before(gameIssue.getSaleEndTime())) {
			throw new GameIssueRuleStartTimeShouldSuitableException();
		}

		/*if(gameIssueRuleEntity.getRuleStartTime().before(date)){//验证规则生效时间在3天之后
			throw new GameIssueRuleStartTimeShouldSuitableException();
		}*/
		if (operationType == 1) {//新增
			gameIssueRuleCheckDao.saveStopGameIssueRule(gameIssueRuleEntity);
		} else {//修改

			//2013-11-11 在修改时，可能存在对原新增的特例奖期进行修改

			GameIssueRuleEntity gameIssueRuleCheckEntity = gameIssueRuleCheckDao
					.getGameIssueRuleCheckByLotteryIdAndRuleId(gameIssueRuleEntity.getLottery().getLotteryId(),
							gameIssueRuleEntity.getId());

			if (null == gameIssueRuleCheckEntity) { //check不存在，要保存到check表中，从主表从拷贝
				gameIssueRuleEntity.setRuleid(gameIssueRuleEntity.getId());
				gameIssueRuleCheckDao.saveStopGameIssueRule(gameIssueRuleEntity);
			}

			gameIssueRuleCheckDao.updateStopGameIssuseRule(gameIssueRuleEntity);
		}
		gameSeriesDaoImpl.updateLastModifyDate(gameIssueRuleEntity.getLottery().getLotteryId());

	}

	@Override
	public void cancelGameIssueRule(Long lotteryId, Long ruleId) {
		/** 2013 12 27修改，在停止时，不需要去验证日期
		GameIssueRuleEntity gameIssueRuleEntity=gameIssueRuleDao.getGameIssueRuleById(ruleId);
		Date date=DateUtils.getStartTimeOfDate(DateUtils.addDays(new Date(),(int) GameIssueRuleEntity.AUDIT_LIMIT_DAY));
		if(gameIssueRuleEntity.getRuleStartTime().before(date)){//验证规则生效时间在3天之后
			throw new GameIssueRuleStartTimeShouldSuitableException();
		}*/
		gameIssueRuleDao.cancelGameIssueRule(lotteryId, ruleId);
	}

	@Override
	public void deleteGameIssueRule(Long lotteryId, Long ruleId) {
		//2013-11-11修改，情况一，存在主表的，针对于那些未开始的奖期规则， 情况二，操作主表和附表的，
		GameIssueRuleEntity gameIssueRuleCheckEntity = gameIssueRuleCheckDao.getGameIssueRuleCheckByLotteryIdAndRuleId(
				lotteryId, ruleId);

		//不存在check表，从主表中获取
		if (null == gameIssueRuleCheckEntity) {
			gameIssueRuleCheckEntity = gameIssueRuleDao.getGameIssueRuleById(ruleId);
			gameIssueRuleCheckEntity.setRuleid(ruleId); //用于对主表进行删除更新
		}

		Date date = DateUtils.getStartTimeOfDate(DateUtils.addDays(new Date(),
				(int) GameIssueRuleEntity.AUDIT_LIMIT_DAY));
		//		if(gameIssueRuleCheckEntity.getRuleStartTime().before(date)){//验证规则生效时间在3天之后
		//			throw new GameIssueRuleStartTimeShouldSuitableException();
		//		}

		//如果存在主表的信息，则更新删除状态
		if (null != gameIssueRuleCheckEntity.getRuleid()) {
			GameIssueRuleEntity gameIssueRuleEntity = gameIssueRuleDao.getGameIssueRuleById(gameIssueRuleCheckEntity
					.getRuleid());
			//2013-11-11往上移动
			//			Date date=DateUtils.getStartTimeOfDate(DateUtils.addDays(new Date(),(int) GameIssueRuleEntity.AUDIT_LIMIT_DAY));
			//			if(gameIssueRuleEntity.getRuleStartTime().before(date)){//验证规则生效时间在3天之后
			//				throw new GameIssueRuleStartTimeShouldSuitableException();
			//			}
			//更新状态为删除状态
			gameIssueRuleDao.deleteGameIssueRule(lotteryId, ruleId);
		}

		//2013-11-14 add
		GameIssueRuleLog gameIssueRuleLog = new GameIssueRuleLog();
		gameIssueRuleLog.setCreateTime(new Date());
		gameIssueRuleLog.setIssueRulesName(gameIssueRuleCheckEntity.getIssueRulesName());
		gameIssueRuleLog.setLotteryid(lotteryId);
		gameIssueRuleLog.setOpenAwardPeriod(gameIssueRuleCheckEntity.getOpenAwardPeriod());
		gameIssueRuleLog.setRuleEndTime(gameIssueRuleCheckEntity.getRuleEndTime());
		gameIssueRuleLog.setRuleStartTime(gameIssueRuleCheckEntity.getRuleStartTime());
		gameIssueRuleLog.setRuleType(new Long(gameIssueRuleCheckEntity.getRuleType().getValue()));
		gameIssueRuleLog.setStatus(gameIssueRuleCheckEntity.getStatus());
		gameIssueRuleLog.setUpdateTime(new Date());
		gameIssueRuleLogDao.insert(gameIssueRuleLog);

		//删除check表的信息
		gameIssueRuleCheckDao.deleteCheckRecordByRuleId(ruleId);
		gameSeriesDaoImpl.updateLastModifyDate(lotteryId);

	}

	@Override
	public void auditGameIssueRule(Long lotteryId, Long ruleCheckId, Integer checkType, Integer checkResult)
			throws Exception {
		GameIssueRuleEntity gire = gameIssueRuleCheckDao.getGameIssueRuleCheckByLotteryIdAndRuleId(lotteryId,
				ruleCheckId);

		//审核 1通过 2不通过
		if (checkResult == 1) {

			Date date = DateUtils.getStartTimeOfDate(DateUtils.addDays(new Date(),
					(int) GameIssueRuleEntity.AUDIT_LIMIT_DAY));
			/*if(gire.getRuleStartTime().before(date)){//验证规则生效时间在4天之后
				throw new GameIssueRuleStartTimeShouldSuitableException();
			}*/
			GameIssue gameIssue = gameIssueDao.getMaxGameIssuesByLotteryId(lotteryId);
			if (gire.getRuleStartTime().before(gameIssue.getSaleEndTime())) {
				throw new GameIssueRuleStartTimeShouldSuitableException();
			}
			gire.setStatus(GameIssueRuleEntity.STATUS_INUSEING);
			if (checkType == RuleType.SPECIAL.getValue()) {//审核特殊奖期规则
				if (null == gire.getRuleid()) {//新增记录审核 逻辑：将issueCheck和templateCheck中的记录写在主表中，并删除两个check表中的数据
					List<GameIssueTemplateEntity> gameIssueTemplateCheckEntitys = gameIssueTemplateCheckDao
							.getGameIssueTemplateCheckByRuleId(ruleCheckId);
					gire.setGameIssueTemplateEntitys(gameIssueTemplateCheckEntitys);
					gameIssueRuleDao.saveGameIssueRule(gire);//正式表中保存规则记录
					gameIssueRuleCheckDao.deleteCheckRecordByRuleId(ruleCheckId);//check表中删除规则记录
				} else {//修改记录审核，需要注意GameIssueRuleEntity的id值不变，创建时间不变，但是更新时间和分段期间会变化，分段期间先删除再保存
					auditModifyRecord(lotteryId, ruleCheckId, gire);
				}
			} else if (checkType == RuleType.STOP.getValue()) {//审核休市规则
				if (null == gire.getRuleid()) {//新增休市记录审核
					gameIssueRuleDao.saveGameIssueRule(gire);//正式表中保存规则记录
					if (ruleCheckId > 0) {
						gameIssueRuleCheckDao.deleteCheckRecordByRuleId(ruleCheckId);
					}
				} else {
					auditModifyRecord(lotteryId, ruleCheckId, gire);
				}
			} else if (checkType == RuleType.COMMEN.getValue()) {
				List<GameIssueTemplateEntity> gameIssueTemplateCheckEntitys = gameIssueTemplateCheckDao
						.getGameIssueTemplateCheckByRuleId(ruleCheckId);
				gire.setGameIssueTemplateEntitys(gameIssueTemplateCheckEntitys);
				gameIssueRuleDao.saveGameIssueRule(gire);//常规规则审核 只能有一条正在使用的有效的常规规则
				gameIssueRuleCheckDao.deleteCheckRecordByRuleId(ruleCheckId);
				//auditModifyRecord(lotteryId, ruleCheckId, gire);
			}
		} else {
			gire.setStatus(5L);
			gameIssueRuleCheckDao.updateGameIssueRuleCheck(gire);
		}
	}

	private void auditModifyRecord(Long lotteryId, Long ruleCheckId, GameIssueRuleEntity gire) {
		GameIssueRuleEntity gameIssueRule = gameIssueRuleDao.getGameIssueRuleById(gire.getRuleid());
		List<GameIssueTemplateEntity> gameIssueTemplateEntitys = gameIssueTemplateCheckDao
				.getGameIssueTemplateCheckByRuleId(ruleCheckId);
		gire.setId(gameIssueRule.getId());
		gire.setGameIssueTemplateEntitys(gameIssueTemplateEntitys);
		gire.setCreateTime(gameIssueRule.getCreateTime());
		gameIssueRuleDao.updateGameIssueRule(gire);
		gameIssueRuleCheckDao.deleteCheckRecordByRuleId(ruleCheckId);
	}

}
