package com.winterframework.firefrog.game.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameAwardDao;
import com.winterframework.firefrog.game.dao.IGameAwardGroupDao;
import com.winterframework.firefrog.game.dao.IGameBettypeStatusDao;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.dao.vo.GameAwardGroup;
import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.GameAwardEntity;
import com.winterframework.firefrog.game.entity.GameAwardGroupEntity;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameAwardGroupDaoImpl")
public class GameAwardGroupDaoImpl extends BaseIbatis3Dao<GameAwardGroup> implements IGameAwardGroupDao {

	@Resource(name = "gameBettypeStatusDaoImpl")
	private IGameBettypeStatusDao bettypeStatusDao;
	
	@Resource(name = "gameAwardDaoImpl")
	private IGameAwardDao awardDao;
	
	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesDao;
	
	@Override
	public Long getMaxAwardMoney(Long lotteryId) throws Exception {
		
		Long awardGroupId = sqlSessionTemplate.selectOne("getMaxAwardMoney", lotteryId); 
		return awardGroupId;
	}

	@Override
	public List<GameAwardGroupEntity> queryGameAwardGroup(Long lotteryId, Integer status, Long awardId) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("lotteryid", lotteryId);
		map.put("status", status);
		map.put("id", awardId);
		
		List<GameAwardGroup> list = sqlSessionTemplate.selectList("queryGameAwardGroup", map);
		
		List<GameAwardGroupEntity> entityList = new ArrayList<GameAwardGroupEntity>();
		for(GameAwardGroup group : list ){
			entityList.add(VOConverter.convertGameAwardGroup2Entity(group));
		}
		
		return entityList;
	}

	@Override
	public Long saveOrUpdate(GameAwardGroup group) throws Exception {
		
		GameAwardGroup awardGroup = null;
		if(group.getId() != null){
			awardGroup = this.getById(group.getId());
		}
		
		if(null == awardGroup){
			insert(group);
		}else{
			update(group);
		}
		return group.getId();
		
	}
	

	@Override
	public List<GameAwardGroupEntity> queryGameAwardGroupByLotteryId(Long lotteryId) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("lotteryid", lotteryId);
		
		List<GameAwardGroup> list = sqlSessionTemplate.selectList("queryGameAwardGroupByLotteryId", map); 
		
		List<GameAwardGroupEntity> entityList = new ArrayList<GameAwardGroupEntity>();
		for(GameAwardGroup game : list){
			
			entityList.add(VOConverter.convertGameAwardGroup2Entity(game));
		}
		
		return entityList;
	}

	@Override
	public List<GameAwardEntity> queryGameAwardEntityByLotteryId(Long lotteryId) throws Exception {
		List<GameAwardEntity> entityList = new ArrayList<GameAwardEntity>();
		
		Long groupId = sqlSessionTemplate.selectOne("getMaxAwardMoney", lotteryId);
		
		List<GameAward> list = awardDao.queryGameAwardByGroupId(groupId, lotteryId, null); 
		
		GameAwardGroup gameAwardGroup = this.getById(groupId);
		
		Float miniLotteryProfit = gameSeriesDao.getMiniLotteryProfitByLotteryId(lotteryId);
		
		for(GameAward award : list){
			
			GameBettypeStatus gameBettypeStatus = bettypeStatusDao.getGameBettypeStatusByLottery(lotteryId, award.findGameGroupCode(), award.findGameSetCode(), award.findBetMethodCode());
			entityList.add(VOConverter.convertGameAward2Entity(award, gameAwardGroup, miniLotteryProfit, gameBettypeStatus));
		}
		
		return entityList;
	}

	@Override
	public Long queryAwardGroupIdByAwardGroupName(String awardName) throws Exception {
		return sqlSessionTemplate.selectOne("getAwardGroupIdByAwardGroupName", awardName);
	}

	@Override
	public Integer queryUsedAwardGroupUserSum(Long lotteryId, Long userid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("userid", userid);
		return sqlSessionTemplate.selectOne("queryUsedAwardGroupUserSum", map);
	}

	@Override
	public GameAwardGroup queryGameGroupByLotteryIdAndName(Long lotteryId, String gameAwardName) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryId);
		map.put("awardName", gameAwardName);
		return this.sqlSessionTemplate.selectOne("queryGameGroupByLotteryIdAndName", map);
	}

	@Override
	public List<GameAwardGroupEntity> queryGameAwardGroupByUserId(long userid) {
		
		List<GameAwardGroup> list = sqlSessionTemplate.selectList("getGameAwardGroupByUserId", userid);
		
		List<GameAwardGroupEntity> entityList = new ArrayList<GameAwardGroupEntity>();
		for(GameAwardGroup group : list ){
			entityList.add(VOConverter.convertGameAwardGroup2Entity(group));
		}
		
		return entityList;
	}

	@Override
	public List<GameAwardGroupEntity> queryGameAwardGroupByGroupId(long groupid, long lotterySeriesCodes) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotterySeriesCodes", lotterySeriesCodes);
		map.put("groupid", groupid);
		List<GameAwardGroup> list = sqlSessionTemplate.selectList("queryGameAwardGroupbyGameAwardGroupID", map);
		
		List<GameAwardGroupEntity> entityList = new ArrayList<GameAwardGroupEntity>();
		for(GameAwardGroup group : list ){
			entityList.add(VOConverter.convertGameAwardGroup2Entity(group));
		}
		
		return entityList;
	}

	@Override
	public Long queryAwardCountByLotteryId(Long lotteryid) throws Exception {
		return this.sqlSessionTemplate.selectOne("queryAwardCountByLotteryId", lotteryid);
	}

}
