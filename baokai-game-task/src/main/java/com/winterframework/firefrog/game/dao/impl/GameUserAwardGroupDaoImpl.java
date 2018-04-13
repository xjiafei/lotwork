package com.winterframework.firefrog.game.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameUserAwardGroupDao;
import com.winterframework.firefrog.game.dao.vo.GameUserAwardGroup;
import com.winterframework.firefrog.game.entity.UserAwardGroupEntity;
import com.winterframework.firefrog.game.entity.VOConvert;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameUserAwardGroupDaoImpl")
public class GameUserAwardGroupDaoImpl extends BaseIbatis3Dao<GameUserAwardGroup> implements IGameUserAwardGroupDao {

	@Override
	public UserAwardGroupEntity selectUserAwardGroup(Long userid, Long lotteryid) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryid);
		map.put("userId", userid);
		GameUserAwardGroup guag = this.sqlSessionTemplate.selectOne("getGameUserAwardGroupByUserIdAndLotteryId", map);

		return VOConvert.gameUserAwardGroup2UserAwardGroupEntity(guag);
	}

	@Override
	public List<GameUserAwardGroup> getAwardByUserIdAndLryIdAndSysAwardId(List<Long> userIds, Long lotteryId,
			Long sysAwardGroupId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryId);
		map.put("sysAwardGroupId", sysAwardGroupId);
		map.put("userIds", userIds);
		List<GameUserAwardGroup> guag = this.sqlSessionTemplate
				.selectList("getAwardByUserIdAndLryIdAndSysAwardId", map);
		return guag;
	}

	@Override
	public List<Map<String, BigDecimal>> getUserAwardGroupDirPoint(Long sysAwardGroupId, List<Long> userAwardGroupIds,
			Long lotteryId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryId);
		map.put("sysAwardGroupId", sysAwardGroupId);
		return this.sqlSessionTemplate.selectList("getUserAwardGroupDirPoint", map);
	}

	@Override
	public List<Map<String, BigDecimal>> getUserAwardGroupThreeOnePoint(Long sysAwardGroupId,
			List<Long> userAwardGroupIds, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryid);
		map.put("sysAwardGroupId", sysAwardGroupId);
		return this.sqlSessionTemplate.selectList("getUserAwardGroupThreeOnePoint", map);
	}
	
	@Override
	public List<GameUserAwardGroup> getUserAwardGroupByLotteryIdAndUserId(
			Long lotteryId, Long userId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("userId", userId); 
		return this.sqlSessionTemplate.selectList("getUserAwardGroupByLotteryIdAndUserId", map);
	}
	
	@Override
	public Map<String, BigDecimal> getUserAwardPoint(String gameBetType,
			Long userId, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gameBetType", gameBetType);
		map.put("lotteryId", lotteryid);
		map.put("userId", userId);
		return this.sqlSessionTemplate.selectOne("getUserAwardPoint", map);
	}

	@Override
	public Map<String, BigDecimal> getUserAwardGroupDirPoint(Long sysAwardGroupId, Long userAwardGroupIds,
			Long lotteryId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryId);
		map.put("sysAwardGroupId", sysAwardGroupId);
		return this.sqlSessionTemplate.selectOne("getUserAwardGroupDirPoint", map);
	}

	@Override
	public Map<String, BigDecimal> getUserAwardGroupThreeOnePoint(Long sysAwardGroupId,
			Long userAwardGroupIds, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryid);
		map.put("sysAwardGroupId", sysAwardGroupId);
		return this.sqlSessionTemplate.selectOne("getUserAwardGroupThreeOnePoint", map);
	}
	
	@Override
	public List<Map<String, BigDecimal>> getUserAwardGroupSbThreeOnePoint(Long sysAwardGroupId,
			List<Long> userAwardGroupIds, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryid);
		map.put("sysAwardGroupId", sysAwardGroupId);
		return this.sqlSessionTemplate.selectList("getUserAwardGroupSbThreeOnePoint", map);
	}
	
	@Override
	public Map<String, BigDecimal> getUserAwardGroupSbThreeOnePoint(Long sysAwardGroupId,
			Long userAwardGroupIds, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryid);
		map.put("sysAwardGroupId", sysAwardGroupId);
		return this.sqlSessionTemplate.selectOne("getUserAwardGroupSbThreeOnePoint", map);
	}
	
	@Override
	public Map<String, BigDecimal> getUserAwardGroupSuperPoint(
			Long sysAwardGroupIds, Long userAwardGroupIds, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryid);
		map.put("sysAwardGroupId", sysAwardGroupIds);
		return this.sqlSessionTemplate.selectOne("getUserAwardGroupSuperPoint", map);
	}
}
