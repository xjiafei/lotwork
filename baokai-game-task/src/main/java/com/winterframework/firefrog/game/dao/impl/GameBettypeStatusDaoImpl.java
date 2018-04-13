package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameBettypeStatusDao;
import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameBettypeStatusDaoImpl")
public class GameBettypeStatusDaoImpl extends BaseIbatis3Dao<GameBettypeStatus> implements IGameBettypeStatusDao {

	@Override
	public Long getTheoryBonusByLottery(Long lotteryId, Integer groupCode, Integer setCode, Integer methodCode)
			throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("gameGroupCode", groupCode);
		map.put("gameSetCode", setCode);
		map.put("betMethodCode", methodCode);
		
		return sqlSessionTemplate.selectOne("getTheoryBonus", map);
	}

	@Override
	public List<GameBettypeStatus> getAllByLotteryId(long lotteryid) {
		return this.sqlSessionTemplate.selectList("com.winterframework.firefrog.game.dao.vo.GameBettypeStatus.getAllByLotteryId", lotteryid);
	}

	@Override
	public void updateForPublish(GameBettypeStatus gbs) {
		this.sqlSessionTemplate.update("com.winterframework.firefrog.game.dao.vo.GameBettypeStatus.updateForPublish", gbs);
		
	}


	@Override
	public List<GameBettypeStatus> getTheoryByLotteryId(Long lotteryid) {
		return this.sqlSessionTemplate.selectList("getTheoryByLotteryId", lotteryid);
	}

	@Override
	public Long getStatus(Long lotteryId, Integer groupCode, Integer setCode, Integer methodCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("gameGroupCode", groupCode);
		map.put("gameSetCode", setCode);
		map.put("betMethodCode", methodCode);
		
		return sqlSessionTemplate.selectOne("getStatus", map);
	}

	@Override
	public List<GameBettypeStatus> getValidBetMethodByLotteryId(long lotteryid) {
		return this.sqlSessionTemplate.selectList("getValidBetMethodByLotteryId", lotteryid);
	}
	@Override
	public Long getStatusOfBjkl8Interest(Long lotteryid, Integer gameGroupCode, Integer gameSetCode, String betDetail) throws Exception {
		
		Long result = 0L;
		if(betDetail.contains("上") || betDetail.contains("中") || betDetail.contains("下")){
			result=getStatus(lotteryid, gameGroupCode, gameSetCode, 30);
			if(result.longValue() == 0L){
				return result;
			}
		}
		if(betDetail.contains("奇") || betDetail.contains("偶") || betDetail.contains("和")){
			result=getStatus(lotteryid, gameGroupCode, gameSetCode, 31);
			if(result.longValue() == 0L){
				return result;
			}
		}
		if(betDetail.contains("大单") || betDetail.contains("大双") || betDetail.contains("小单") || betDetail.contains("小双")){
			result=getStatus(lotteryid, gameGroupCode, gameSetCode, 32);
			if(result.longValue() == 0L){
				return result;
			}
		}
		return result;
	}
	@Override
	public GameBettypeStatus getGameBetTypeStatusByLotteryIdAndBetTypeCode(
			Long lotteryId, String betTypeCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId); 
		map.put("betTypeCode", betTypeCode);
		
		return sqlSessionTemplate.selectOne("getGameBetTypeStatusByLotteryIdAndBetTypeCode", map);
	}
}
