package com.winterframework.firefrog.game.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameIssueRuleDao;
import com.winterframework.firefrog.game.dao.IGameIssueTemplateDao;
import com.winterframework.firefrog.game.dao.vo.GameIssueRule;
import com.winterframework.firefrog.game.dao.vo.GameIssueTemplate;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.dao.vo.VOConverter4Task;
import com.winterframework.firefrog.game.entity.GameIssueRuleEntity;
import com.winterframework.firefrog.game.entity.GameIssueRuleEntity.RuleType;
import com.winterframework.firefrog.game.entity.GameIssueTemplateEntity;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameIssueRuleDaoImpl")
public class GameIssueRuleDaoImpl extends BaseIbatis3Dao<GameIssueRule> implements IGameIssueRuleDao {

	@Resource(name = "gameIssueTemplateDaoImpl")
	private IGameIssueTemplateDao gameIssueTemplateDao;

	@Override
	public GameIssueRuleEntity getCommenGameIssueRuleByRuleTypeAndDay(Long lotteryId, Date fromDate, Date toDate,
			Integer day) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("fromDate", fromDate);
		map.put("toDate", toDate);
		map.put("ruleType", RuleType.COMMEN.getValue());
		map.put("day", day + "");
		map.put("status", GameIssueRuleEntity.STATUS_INUSEING);
		GameIssueRule girs = this.sqlSessionTemplate.selectOne("getCommenGameIssueRuleByRuleTypeAndDay", map);
		if (girs != null) {
			GameIssueRuleEntity gire = VOConverter.gameIssueRule2GameIssueRuleEntity(girs);
			return gire;
		} else {
			return null;
		}
	}

	@Override
	public List<GameIssueRuleEntity> queryGameIssueRuleByLotteryIdAndRuleId(long lotteryId, Long ruleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("ruleId", ruleId);
		List<GameIssueRule> girs = this.sqlSessionTemplate.selectList("queryGameIssueRuleByLotteryIdAndRuleId", map);
		List<GameIssueRuleEntity> gires = new ArrayList<GameIssueRuleEntity>();
		if (!girs.isEmpty() && girs.size() != 0) {
			for (GameIssueRule gir : girs) {
				GameIssueRuleEntity gire = VOConverter.gameIssueRule2GameIssueRuleEntity(gir);
				gires.add(gire);
			}
		}
		return gires;
	}

	public Long getGameIssueRuleInsertId() {
		return this.sqlSessionTemplate.selectOne("getGameIssueRuleInsertId");
	}

	@Override
	public void cancelGameIssueRule(Long lotteryId, Long ruleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("ruleId", ruleId);
		map.put("status", GameIssueRuleEntity.STATUS_STOP);
		this.sqlSessionTemplate.update("cancelGameIssueRule", map);

	}

	@Override
	public void deleteGameIssueRule(Long lotteryId, Long ruleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("ruleId", ruleId);
		map.put("status", GameIssueRuleEntity.STATUS_DELETED);
		this.sqlSessionTemplate.update("deleteGameIssueRule", map);

	}

	@Override
	public void saveGameIssueRule(GameIssueRuleEntity gameIssueRuleEntity) {
		GameIssueRule gameIssueRule = VOConverter.gameIssueRuleEntity2GameIssueRule(gameIssueRuleEntity);
		Long id = getGameIssueRuleInsertId();
		gameIssueRule.setId(id);
		this.insert(gameIssueRule);
		if (null != gameIssueRuleEntity.getGameIssueTemplateEntitys()
				&& gameIssueRuleEntity.getGameIssueTemplateEntitys().size() != 0) {
			for (GameIssueTemplateEntity gite : gameIssueRuleEntity.getGameIssueTemplateEntitys()) {
				GameIssueTemplate gameIssueTemplate = VOConverter.gameIssueTemplateEntity2GameIssueTemplate(gite);
				gameIssueTemplate.setRuleid(id);
				gameIssueTemplateDao.insertTemplate(gameIssueTemplate);
			}
		}
	}

	@Override
	public GameIssueRuleEntity getGameIssueRuleById(Long ruleid) {
		GameIssueRule gameIssueRule = this.getById(ruleid);
		if (null == gameIssueRule) {
			return null;
		}
		GameIssueRuleEntity gameIssueRuleEntity = VOConverter.gameIssueRule2GameIssueRuleEntity(gameIssueRule);
		return gameIssueRuleEntity;
	}

	@Override
	public void updateGameIssueRule(GameIssueRuleEntity gameIssueRuleEntity) {
		GameIssueRule gameIssueRule = VOConverter.gameIssueRuleEntity2GameIssueRule(gameIssueRuleEntity);
		this.update(gameIssueRule);
		gameIssueTemplateDao.deleteGameIssueTemplateByRuleId(gameIssueRule.getId());
		if (null != gameIssueRuleEntity.getGameIssueTemplateEntitys()
				&& gameIssueRuleEntity.getGameIssueTemplateEntitys().size() != 0) {
			for (GameIssueTemplateEntity gite : gameIssueRuleEntity.getGameIssueTemplateEntitys()) {
				GameIssueTemplate gameIssueTemplate = VOConverter.gameIssueTemplateEntity2GameIssueTemplate(gite);
				gameIssueTemplate.setRuleid(gameIssueRule.getId());
				gameIssueTemplateDao.insertTemplate(gameIssueTemplate);
			}
		}
	}

	@Override
	public GameIssueRuleEntity getSpecialOrStopGameIssueRuleByRuleTypeAndDay(Long lotteryId, Date beginOfDate,
			Date endOfDate, Integer day) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("fromDate", beginOfDate);
		map.put("toDate", endOfDate);
		map.put("day", day + "");
		map.put("status", GameIssueRuleEntity.STATUS_INUSEING);
		GameIssueRule girs = this.sqlSessionTemplate.selectOne("getSpecialOrStopGameIssueRuleByRuleTypeAndDay", map);
		GameIssueRuleEntity gire = new GameIssueRuleEntity();
		if (girs != null) {
			gire = VOConverter.gameIssueRule2GameIssueRuleEntity(girs);
		}
		return gire;
	}

	@Override
	public GameIssueRuleEntity getSpecialGameIssueRuleByRuleTypeAndDay(Long lotteryId, Date beginOfDate,
			Date endOfDate, Integer day) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("fromDate", beginOfDate);
		map.put("toDate", endOfDate);
		map.put("day", day + "");
		map.put("status", GameIssueRuleEntity.STATUS_INUSEING);
		GameIssueRule girs = this.sqlSessionTemplate.selectOne("getSpecialGameIssueRuleByRuleTypeAndDay", map);
		GameIssueRuleEntity gire = new GameIssueRuleEntity();
		if (girs != null) {
			gire = VOConverter4Task.gameIssueRule2GameIssueRuleEntity(girs);
		}
		return gire;
	}

	@Override
	public List<GameIssueRuleEntity> getStopGameIssueRuleByRuleTypeAndDay(Long lotteryId, Date beginOfDate,
			Date endOfDate, Integer day) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		if (beginOfDate != null) {
			map.put("fromDate", beginOfDate);
		}
		if (endOfDate != null) {
			map.put("toDate", endOfDate);
		}
		if (day != null) {
			map.put("day", day + "");
		}
		map.put("status", GameIssueRuleEntity.STATUS_INUSEING);
		List<GameIssueRule> girss = this.sqlSessionTemplate.selectList("getStopGameIssueRuleByRuleTypeAndDay", map);
		List<GameIssueRuleEntity> gires = new ArrayList<GameIssueRuleEntity>();
		if (!girss.isEmpty()) {
			for (GameIssueRule girs : girss) {
				gires.add(VOConverter4Task.gameIssueRule2GameIssueRuleEntity(girs));
			}
		}
		return gires;
	}

	@Override
	public GameIssueRuleEntity getCommenGameIssueRuleByLottery(Long lotteryId, Integer day,Date fromDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("ruleType", RuleType.COMMEN.getValue());
		map.put("day", day + "");
		map.put("status", GameIssueRuleEntity.STATUS_INUSEING);
		map.put("fromDate", fromDate);
		GameIssueRule girs = this.sqlSessionTemplate.selectOne("getCommenGameIssueRuleByLottery", map);
		if (girs != null) {
			return VOConverter4Task.gameIssueRule2GameIssueRuleEntity(girs);
		}
		return null;
	}

	@Override
	public List<GameIssueRuleEntity> getSpecialGameIssueRuleByLotteryAndDate(
			Long lotteryId, Date beginOfDate, Date endOfDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		if (beginOfDate != null) {
			map.put("fromDate", beginOfDate);
		}
		if (endOfDate != null) {
			map.put("toDate", endOfDate);
		}
		map.put("status", GameIssueRuleEntity.STATUS_INUSEING);
		List<GameIssueRule> girss = this.sqlSessionTemplate.selectList("getSpecialGameIssueRuleByLotteryAndDate", map);
		List<GameIssueRuleEntity> gires = new ArrayList<GameIssueRuleEntity>();
		if (!girss.isEmpty()) {
			for (GameIssueRule girs : girss) {
				gires.add(VOConverter4Task.gameIssueRule2GameIssueRuleEntity(girs));
			}
		}
		return gires;
	}
	
	@Override
	public List<GameIssueRuleEntity> queryGameIssueRuleByMap(Map<String, Object> map) {
	
		List<GameIssueRule> girss = this.sqlSessionTemplate.selectList("queryGameIssueRuleByMap", map);
		List<GameIssueRuleEntity> gires = new ArrayList<GameIssueRuleEntity>();
		if (!girss.isEmpty()) {
			for (GameIssueRule girs : girss) {
				gires.add(VOConverter4Task.gameIssueRule2GameIssueRuleEntity(girs));
			}
		}
		return gires;
	}

}
