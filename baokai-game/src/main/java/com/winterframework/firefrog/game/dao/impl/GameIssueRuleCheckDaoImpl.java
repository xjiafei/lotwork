package com.winterframework.firefrog.game.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameIssueRuleCheckDao;
import com.winterframework.firefrog.game.dao.IGameIssueTemplateCheckDao;
import com.winterframework.firefrog.game.dao.vo.GameIssueRuleCheck;
import com.winterframework.firefrog.game.dao.vo.GameIssueTemplateCheck;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.dao.vo.VOConverter4Task;
import com.winterframework.firefrog.game.entity.GameIssueRuleEntity;
import com.winterframework.firefrog.game.entity.GameIssueRuleEntity.RuleType;
import com.winterframework.firefrog.game.entity.GameIssueTemplateEntity;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameIssueRuleCheckDaoImpl")
public class GameIssueRuleCheckDaoImpl extends BaseIbatis3Dao<GameIssueRuleCheck> implements IGameIssueRuleCheckDao {

	@Resource(name = "gameIssueTemplateCheckDaoImpl")
	private IGameIssueTemplateCheckDao gameIssueTemplateCheckDao;

	@Override
	public List<GameIssueRuleEntity> queryGameIssueRuleCheckByLotteryIdAndRuleId(long lotteryId, Long ruleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("ruleId", ruleId);
		List<GameIssueRuleCheck> gircs = this.sqlSessionTemplate.selectList(
				"queryGameIssueRuleCheckByLotteryIdAndRuleId", map);
		List<GameIssueRuleEntity> gires = new ArrayList<GameIssueRuleEntity>();
		if (!gircs.isEmpty() && gircs.size() != 0) {
			for (GameIssueRuleCheck girc : gircs) {
				GameIssueRuleEntity gire = VOConverter.gameIssueRuleCheck2GameIssueRuleEntity(girc);
				gires.add(gire);
			}
		}
		return gires;
	}

	@Override
	public Long saveCommonOrSpecialGameIssueRule(GameIssueRuleEntity gameIssueRuleEntity) {
		Long id = saveGameIssueCheckAndTemplate(gameIssueRuleEntity);
		return id;
	}

	@Override
	public void updateGameIssueRuleCheck(GameIssueRuleEntity gameIssueRuleEntity) {
		GameIssueRuleCheck entity = VOConverter.gameIssueRuleEntity2GameIssueRuleCheck(gameIssueRuleEntity);
		this.update(entity);
	}

	private Long saveGameIssueCheckAndTemplate(GameIssueRuleEntity gameIssueRuleEntity) {
		GameIssueRuleCheck gameIssueRuleCheck = VOConverter.gameIssueRuleEntity2GameIssueRuleCheck(gameIssueRuleEntity);
		Long id = getGameIssueRuleCheckInsertId();
		//2013-11-11 这里的状态必须是待审核
		gameIssueRuleCheck.setStatus(4l);
		gameIssueRuleCheck.setId(id);
		this.insert(gameIssueRuleCheck);
		if (null != gameIssueRuleEntity.getGameIssueTemplateEntitys()
				&& gameIssueRuleEntity.getGameIssueTemplateEntitys().size() != 0) {
			for (GameIssueTemplateEntity gite : gameIssueRuleEntity.getGameIssueTemplateEntitys()) {
				GameIssueTemplateCheck gameIssueTemplateCheck = VOConverter
						.gameIssueTemplateEntity2GameIssueTemplateCheck(gite);
				gameIssueTemplateCheck.setRuleid(id);
				gameIssueTemplateCheckDao.insertTemplate(gameIssueTemplateCheck);
			}
		}
		gameIssueRuleEntity.setRuleid(id);
		return gameIssueRuleCheck.getId();
	}

	@Override
	public void updateCommonOrSpecialGameIssuseRule(GameIssueRuleEntity gameIssueRuleEntity) {
		//		saveGameIssueCheckAndTemplate(gameIssueRuleEntity);
		//2013-11-11 修改为更新check表方法。
		GameIssueRuleCheck check = this.getById(gameIssueRuleEntity.getRuleid());

		check.setUpdateTime(new Date());
		if (StringUtils.isNotBlank(gameIssueRuleEntity.getIssueRulesName())) {
			check.setIssueRulesName(gameIssueRuleEntity.getIssueRulesName());
		}

		check.setRuleStartTime(gameIssueRuleEntity.getRuleStartTime());
		if (null != gameIssueRuleEntity.getRuleEndTime()) {
			check.setRuleEndTime(gameIssueRuleEntity.getRuleEndTime());
		}

		if (StringUtils.isNotBlank(gameIssueRuleEntity.getOpenAwardPeriod())) {
			check.setOpenAwardPeriod(gameIssueRuleEntity.getOpenAwardPeriod());
		}
		if(null != gameIssueRuleEntity.getStopStartTime()){
			check.setStopStartTime(gameIssueRuleEntity.getStopStartTime());
		}
		if(null != gameIssueRuleEntity.getStopEndTime()){
			check.setStopEndTime(gameIssueRuleEntity.getStopEndTime());
		}
		if(null != gameIssueRuleEntity.getSeriesIssueCode()){
			check.setSeriesIssueCode(gameIssueRuleEntity.getSeriesIssueCode());
		}

		if (null != gameIssueRuleEntity.getGameIssueTemplateEntitys()
				&& gameIssueRuleEntity.getGameIssueTemplateEntitys().size() != 0) {

			//在这里获取GameIssueTempCheck表的ID
			for (GameIssueTemplateEntity gite : gameIssueRuleEntity.getGameIssueTemplateEntitys()) {

				GameIssueTemplateCheck gameIssueTemplateCheck = VOConverter
						.gameIssueTemplateEntity2GameIssueTemplateCheck(gite);
				//存在可能新增的情况。
				if (null == gameIssueTemplateCheck.getId()) {
					gameIssueTemplateCheckDao.insert(gameIssueTemplateCheck);
				} else {
					gameIssueTemplateCheckDao.update(gameIssueTemplateCheck);
				}
			}
		}

		this.update(check); //更新操作
	}

	public Long getGameIssueRuleCheckInsertId() {
		return this.sqlSessionTemplate.selectOne("getGameIssueRuleCheckInsertId");
	}

	@Override
	public List<GameIssueRuleEntity> querySpecialGameIssueRuleCheckByLotteryIdAndPeriodTime(Long lotteryId,
			Date fromDate, Date toDate, Long ruleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("fromDate", fromDate);
		map.put("toDate", toDate);
		map.put("ruleId", ruleId);
		List<GameIssueRuleCheck> gircs = this.sqlSessionTemplate.selectList(
				"querySpecialGameIssueRuleCheckByLotteryIdAndPeriodTime", map);
		List<GameIssueRuleEntity> gires = new ArrayList<GameIssueRuleEntity>();
		if (!gircs.isEmpty() && gircs.size() != 0) {
			for (GameIssueRuleCheck girc : gircs) {
				GameIssueRuleEntity gire = VOConverter.gameIssueRuleCheck2GameIssueRuleEntity(girc);
				gires.add(gire);
			}
		}
		return gires;
	}

	@Override
	public List<GameIssueRuleEntity> querySpecialGameIssueRuleCheckByLotteryAndTime(Long lotteryId, Date fromDate,
			Date toDate, Long ruleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("fromDate", fromDate);
		map.put("toDate", toDate);
		map.put("ruleId", ruleId);
		List<GameIssueRuleCheck> gircs = this.sqlSessionTemplate.selectList(
				"querySpecialGameIssueRuleCheckByLotteryAndTime", map);
		List<GameIssueRuleEntity> gires = new ArrayList<GameIssueRuleEntity>();
		if (!gircs.isEmpty() && gircs.size() != 0) {
			for (GameIssueRuleCheck girc : gircs) {
				GameIssueRuleEntity gire = VOConverter.gameIssueRuleCheck2GameIssueRuleEntity(girc);
				gires.add(gire);
			}
		}
		return gires;
	}

	@Override
	public void saveStopGameIssueRule(GameIssueRuleEntity gameIssueRuleEntity) {
		saveGameIssueCheckAndTemplate(gameIssueRuleEntity);

	}

	@Override
	public void updateStopGameIssuseRule(GameIssueRuleEntity gameIssueRuleEntity) {
		//		saveGameIssueCheckAndTemplate(gameIssueRuleEntity);
		//2013-11-11 修改
		updateCommonOrSpecialGameIssuseRule(gameIssueRuleEntity);

	}

	@Override
	public GameIssueRuleEntity getGameIssueRuleCheckByLotteryIdAndRuleId(Long lotteryId, Long ruleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("ruleId", ruleId);
		GameIssueRuleCheck girc = this.sqlSessionTemplate.selectOne("getGameIssueRuleCheckByLotteryIdAndRuleId", map);

		if (null == girc) {
			return null;
		}
		GameIssueRuleEntity gire = VOConverter.gameIssueRuleCheck2GameIssueRuleEntity(girc);
		return gire;
	}

	@Override
	public void deleteCheckRecordByRuleId(Long ruleCheckId) {
		gameIssueTemplateCheckDao.deleteCheckRecordByRuleId(ruleCheckId);
		this.delete(ruleCheckId);
	}

	@Override
	public GameIssueRuleEntity getCommenGameIssueRuleByRuleTypeAndDayWithUnAuditRule(Long lotteryId, Date tempFromDate,
			Date tempToDate, int dayValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("fromDate", tempFromDate);
		map.put("toDate", tempToDate);
		map.put("ruleType", RuleType.COMMEN.getValue());
		map.put("day", dayValue + "");
		Date date = DateUtils.getStartTimeOfDate(DateUtils.addDays(new Date(),
				(int) GameIssueRuleEntity.AUDIT_LIMIT_DAY));
		map.put("auditLimitDate", date);
		GameIssueRuleCheck girs = this.sqlSessionTemplate.selectOne(
				"getCommenGameIssueRuleByRuleTypeAndDayWithUnAuditRule", map);
		if (girs != null) {
			GameIssueRuleEntity gire = VOConverter.gameIssueRuleCheck2GameIssueRuleEntity(girs);
			return gire;
		} else {
			return null;
		}
	}

	@Override
	public GameIssueRuleEntity getSpecialOrStopGameIssueRuleByRuleTypeAndDayWithUnAuditRule(Long lotteryId,
			Date tempFromDate, Date tempToDate, int dayValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("fromDate", tempFromDate);
		map.put("toDate", tempToDate);
		map.put("day", dayValue + "");
		Date date = DateUtils.getStartTimeOfDate(DateUtils.addDays(new Date(),
				(int) GameIssueRuleEntity.AUDIT_LIMIT_DAY));
		map.put("auditLimitDate", date);
		GameIssueRuleCheck girs = this.sqlSessionTemplate.selectOne(
				"getSpecialOrStopGameIssueRuleByRuleTypeAndDayWithUnAuditRule", map);
		GameIssueRuleEntity gire = new GameIssueRuleEntity();
		if (girs != null) {
			gire = VOConverter.gameIssueRuleCheck2GameIssueRuleEntity(girs);
		}
		return gire;
	}

	@Override
	public List<GameIssueRuleEntity> getCommenGameIssuesByMainRuleId(Long mainRuleid) {
		List<GameIssueRuleCheck> gircs = this.sqlSessionTemplate.selectList("getCommenGameIssuesByMainRuleId",
				mainRuleid);
		List<GameIssueRuleEntity> gires = new ArrayList<GameIssueRuleEntity>();
		if (!gircs.isEmpty() && gircs.size() != 0) {
			for (GameIssueRuleCheck girc : gircs) {
				GameIssueRuleEntity gire = VOConverter.gameIssueRuleCheck2GameIssueRuleEntity(girc);
				gires.add(gire);
			}
		}
		return gires;
	}

	@Override
	public GameIssueRuleEntity getGameIssueRuleCheckById(Long id) {
		GameIssueRuleCheck gircs = this.getById(id);

		return VOConverter.gameIssueRuleCheck2GameIssueRuleEntity(gircs);
	}

	@Override
	public GameIssueRuleEntity getSpecialGameIssueRuleByRuleTypeAndDayWithUnAuditRule(Long lotteryId, Date beginOfDate,
			Date endOfDate, int dayValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("fromDate", beginOfDate);
		map.put("toDate", endOfDate);
		map.put("day", dayValue + "");
		Date date = DateUtils.getStartTimeOfDate(DateUtils.addDays(new Date(),
				(int) GameIssueRuleEntity.AUDIT_LIMIT_DAY));
		map.put("auditLimitDate", date);
		GameIssueRuleCheck girs = this.sqlSessionTemplate.selectOne(
				"getSpecialGameIssueRuleByRuleTypeAndDayWithUnAuditRule", map);
		GameIssueRuleEntity gire = new GameIssueRuleEntity();
		if (girs != null) {
			gire = VOConverter.gameIssueRuleCheck2GameIssueRuleEntity(girs);
		}
		return gire;
	}

	@Override
	public List<GameIssueRuleEntity> getStopGameIssueRuleByRuleTypeAndDayWithUnAuditRule(Long lotteryId,
			Date beginOfDate, Date endOfDate, int dayValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("fromDate", beginOfDate);
		map.put("toDate", endOfDate);
		map.put("day", dayValue + "");
		Date date = DateUtils.getStartTimeOfDate(DateUtils.addDays(new Date(),
				(int) GameIssueRuleEntity.AUDIT_LIMIT_DAY));
		map.put("auditLimitDate", date);
		List<GameIssueRuleCheck> girss = this.sqlSessionTemplate.selectList(
				"getStopGameIssueRuleByRuleTypeAndDayWithUnAuditRule", map);
		List<GameIssueRuleEntity> gires = new ArrayList<GameIssueRuleEntity>();
		if (!girss.isEmpty()) {
			for (GameIssueRuleCheck girs : girss) {
				gires.add(VOConverter4Task.gameIssueRuleCheck2GameIssueRuleEntity(girs));
			}
		}
		return gires;
	}
	
	@Override
	public GameIssueRuleEntity getCommenGameIssueRuleByLottery(Long lotteryId,
			Integer day) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("ruleType", RuleType.COMMEN.getValue());
		map.put("day", day + "");
		map.put("status", GameIssueRuleEntity.STATUS_UNAUDIT);
		GameIssueRuleCheck girs = this.sqlSessionTemplate.selectOne("getCommenGameIssueRuleCheckByLottery", map);
		if (girs != null) {
			return VOConverter4Task.gameIssueRuleCheck2GameIssueRuleEntity(girs);
		}
		return null;
	}
	
	@Override
	public GameIssueRuleEntity getSpecialGameIssueRuleByRuleTypeAndDay(Long lotteryId, Date beginOfDate,
			Date endOfDate, Integer day) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("fromDate", beginOfDate);
		map.put("toDate", endOfDate);
		map.put("day", day + "");
		map.put("status", GameIssueRuleEntity.STATUS_UNAUDIT);
		GameIssueRuleCheck girs = this.sqlSessionTemplate.selectOne("getSpecialGameIssueRuleCheckByRuleTypeAndDay", map);
		GameIssueRuleEntity gire = new GameIssueRuleEntity();
		if (girs != null) {
			gire = VOConverter4Task.gameIssueRuleCheck2GameIssueRuleEntity(girs);
		}
		return gire;
	}
	
	@Override
	public List<GameIssueRuleEntity> getStopGameIssueRuleByRuleTypeAndDay(Long lotteryId, Date beginOfDate,
			Date endOfDate, Integer day) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("fromDate", beginOfDate);
		map.put("toDate", endOfDate);
		map.put("day", day + "");
		map.put("status", GameIssueRuleEntity.STATUS_UNAUDIT);
		List<GameIssueRuleCheck> girss = this.sqlSessionTemplate.selectList("getStopGameIssueRuleByRuleCheckTypeAndDay", map);
		List<GameIssueRuleEntity> gires = new ArrayList<GameIssueRuleEntity>();
		if (!girss.isEmpty()) {
			for (GameIssueRuleCheck girs : girss) {
				gires.add(VOConverter4Task.gameIssueRuleCheck2GameIssueRuleEntity(girs));
			}
		}
		return gires;
	}
}
