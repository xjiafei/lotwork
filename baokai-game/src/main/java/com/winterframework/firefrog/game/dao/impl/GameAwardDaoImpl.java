package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameAwardDao;
import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.dao.vo.GameLhcOdds;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 奖金组明细DAO实现类
 * @author Richard
 * @date 2013-12-27 下午3:48:52 
 */
@Repository("gameAwardDaoImpl")
public class GameAwardDaoImpl extends BaseIbatis3Dao<GameAward> implements IGameAwardDao {

	@Override
	public List<GameAward> queryGameAwardByGroupId(Long groupId, Long lotteryId, Integer status) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("lotteryId", lotteryId);
		map.put("awardGroupId", groupId);
		map.put("status", status);

		return sqlSessionTemplate.selectList("queryGameAwardByGroupId", map);
	}

	public List<GameAward> queryUserGameAwardByGroupId(Long awardGroupId, Long lotteryId, Integer status)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("lotteryId", lotteryId);
		map.put("awardGroupId", awardGroupId);
		map.put("status", 1);
		List<GameAward> awards = sqlSessionTemplate.selectList("queryUserGameAwardByGroupId", map);
		List<GameAward> userAwards = sqlSessionTemplate.selectList("queryUserRetValueGameAwardByGroupId", map);
		for(GameAward award :awards){
			for(GameAward userAward:userAwards){
				if(award.getBetTypeCode().equals(userAward.getBetTypeCode())){
					award.setRetValue(userAward.getRetValue());
				}
			}
		}
		return awards;
	}

	@Override
	public List<GameAward> getGameAwardByGroupId(Long awardGroupId) {
		return sqlSessionTemplate.selectList("getGameAwardByGroupId", awardGroupId);
	}

	/**
	* Title: getActualBonus
	* Description:
	* @param lotteryId
	* @param betTypeCode
	* @param userId
	* @return 
	* @see com.winterframework.firefrog.game.dao.IGameAwardDao#getActualBonus(java.lang.Long, java.lang.String, java.lang.Long) 
	*/
	@Override
	public Long getActualBonus(Long lotteryId, String betTypeCode, Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("betTypeCode", betTypeCode);
		map.put("userId", userId);
		return sqlSessionTemplate.selectOne(getQueryPath("getActualBonus"), map);
	}
	@Override
	public Long getActualBonusDown(Long lotteryId, String betTypeCode,
			Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("betTypeCode", betTypeCode);
		map.put("userId", userId);
		return sqlSessionTemplate.selectOne(getQueryPath("getActualBonusDown"), map);
	}

	@Override
	public GameAward getGameAwardByBetTypeAndLotteryId(Long lotteryId, String betTypeCode, Long awardGroupId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("betTypeCode", betTypeCode);
		map.put("awardGroupId", awardGroupId);
		return sqlSessionTemplate.selectOne(getQueryPath("getGameAwardByBetTypeAndLotteryId"), map);
	}

	@Override
	public List<GameAward> getGameAwardListByBetCodeParent(Long lotteryId, String betTypeCode, Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("betTypeCode", betTypeCode);
		map.put("userId", userId);
		return sqlSessionTemplate.selectList(getQueryPath("getGameAwardListByBetCodeParent"), map);
	}

	@Override
	public Long queryUserAwardGroupIdBySysAwardGroupAndUser(Long sysAwardGroupId, Long userId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sysAwardGroupId", sysAwardGroupId);
		map.put("userId", userId);
		return sqlSessionTemplate.selectOne(getQueryPath("queryIdBySysAwardGroupAndUser"), map);
	}
	@Override
	public List<GameAward> getValidByLotteryIdAndGroupIdAndBetTypeCodeParent(
			Long lotteryId, Long awardGroupId, String betTypeCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("awardGroupId", awardGroupId);
		map.put("betTypeCode", betTypeCode);
		return sqlSessionTemplate.selectList(getQueryPath("getValidByLotteryIdAndGroupIdAndBetTypeCodeParent"), map);
	}
	
	/**
	 * 依據 lotteryId 取回 ID, actualBonus, lhcCode, groupCodeName, setCodeName, methodCodeName, methodTypeName，並依據 orderby, game_award.bet_type_code, lhc_code, actual_bonus 排序 。
	 */
	@Override
	public List<GameLhcOdds> getByLotteryId(Long lotteryId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		return sqlSessionTemplate.selectList(getQueryPath("getByLotteryId"), map);
	}

	@Override
	public String getAwardBetTypeForOne(Long lotteryId) {
		return sqlSessionTemplate.selectOne(getQueryPath("getAwardBetTypeForOne"), lotteryId);
	}

	@Override
	public Long getActualBonusByAwardGroupId(Long lotteryId, String betTypeCode,Integer sysGroupId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("betTypeCode", betTypeCode);
		map.put("sysGroupId", sysGroupId);
		return sqlSessionTemplate.selectOne(getQueryPath("getActualBonusByAwardGroupId"), map);
	}
	
}
