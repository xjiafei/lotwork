package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameUserAwardDao;
import com.winterframework.firefrog.game.dao.vo.GameUserAward;
import com.winterframework.firefrog.game.exception.UserGameAwardConfigErrorException;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameUserAwardDaoImpl")
public class GameUserAwardDaoImpl extends BaseIbatis3Dao<GameUserAward> implements IGameUserAwardDao {

	@Override
	public List<GameUserAward> getByLotteryIdAndUserId(Long lotteryId,
			long userId) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("userId", userId);
		return this.sqlSessionTemplate.selectList("getByLotteryIdAndUserId", map);
	} 
	@Override
	public List<GameUserAward> getByLotteryIdAndUserIdAndAwardGroupId(Long lotteryId,Long userId,Long userAwardGroupId) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("userId", userId);
		map.put("userAwardGroupId", userAwardGroupId);
		return this.sqlSessionTemplate.selectList("getByLotteryIdAndUserIdAndAwardGroupId", map);
	} 
	@Override
	public GameUserAward getByLotteryIdAndUserIdAndBetTypeCode(Long lotteryId,
			long userId, String betTypeCode) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("userId", userId);
		map.put("betTypeCode", betTypeCode);
		return this.sqlSessionTemplate.selectOne("getByLotteryIdAndUserIdAndBetTypeCode", map);
	}
	@Override
	public Long selectActualBoundsByCondition(Map<String, Object> map) throws Exception {
		Long actualBounds = this.sqlSessionTemplate.
				selectOne("com.winterframework.firefrog.game.dao.vo.GameUserAward.selectActualBoundsByCondition", map);
		return actualBounds;
	}
	
	@Override
	public Long getUserAward(Long lotteryid, Integer gameGroupCode, Integer gameSetCode, Long betMethodCode, Long userid) {

//		GameUserAward award = new GameUserAward();
//		award.setLotterySeriesCode(lotteryid.intValue());
//		award.setGameGroupCode(new Long(gameGroupCode));
//		award.setGameSetCode(new Long(gameSetCode));
//		award.setBetMethodCode(new Long(betMethodCode));
//		award.setUserid(userid);
//		award.setType(0); //投注奖金组
//
//		List<GameUserAward> resultList = this.getAllByEntity(award);
//		if (null == resultList || resultList.isEmpty()) {
//			return 0L;
//		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("lotteryid", lotteryid);
		map.put("gameGroupCode", gameGroupCode);
		map.put("gameSetCode", gameSetCode);
		map.put("betMethodCode", betMethodCode);
		map.put("userId", userid);
		
		Long bonus = this.sqlSessionTemplate.selectOne("selectActualBoundsByCondition", map);
		if(null == bonus||bonus==0){//当获取奖金为null 或者为0时 抛出奖金组错误异常
			throw new UserGameAwardConfigErrorException();
		}
//		return resultList.get(0).getActualBonus();
		return bonus;
	}


	@Override
	public List<GameUserAward> getByGroupIdAndUserId(Long userAwardGroupId, Long userid) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupId", userAwardGroupId);
		map.put("userId", userid);
		return this.sqlSessionTemplate.selectList("getByGroupIdAndUserId", map);
	}

	@Override
	public boolean isUserAwardExist(Long lotterySeriesCode, Integer gameGroupCode, Integer gameSetCode, Long betMethodCode,
			Long userAwardGroupId, Long userid) {
		
		GameUserAward award = new GameUserAward();
		//		award.setLotterySeriesCode(lotterySeriesCode.intValue());
		//		award.setGameGroupCode(new Long(gameGroupCode));
		//		award.setGameSetCode(new Long(gameSetCode));
		//		award.setBetMethodCode(new Long(betMethodCode));
		//		award.setUserAwardGroupId(userAwardGroupId);
		//		award.setUserid(userid);

		List<GameUserAward> resultList = this.getAllByEntity(award);
		if (null == resultList || resultList.isEmpty()) {
			return false;
		}
		
		return true;
	}

	@Override
	public void updateUserAward(GameUserAward gua) {
		this.sqlSessionTemplate.update("updateUserAward", gua);
	}
	
	@Override
	public void updateUserAwardByAwardGroupID(Long userAwardGroupId, Long userId,int status) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupId", userAwardGroupId);
		map.put("userId", userId);
		map.put("status", status);
		this.sqlSessionTemplate.update("updateUserAwardByAwardGroupID", map);
	}
	@Override
	public int updateByUserIdLotteryIdBetTypeCode(GameUserAward userAward)
			throws Exception { 
		return this.sqlSessionTemplate.update("updateByUserIdLotteryIdBetTypeCode", userAward);
	}
}
