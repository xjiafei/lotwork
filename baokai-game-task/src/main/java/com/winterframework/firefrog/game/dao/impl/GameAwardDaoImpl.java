package com.winterframework.firefrog.game.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameAwardDao;
import com.winterframework.firefrog.game.dao.IGameBonusPoolDao;
import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.dao.vo.GameAwardCheck;
import com.winterframework.firefrog.game.dao.vo.GameAwardGroup;
import com.winterframework.firefrog.game.dao.vo.GameBonusAwardJsonBean;
import com.winterframework.firefrog.game.dao.vo.GameBonusJsonBean;
import com.winterframework.firefrog.game.dao.vo.GameBonusPool;
import com.winterframework.firefrog.game.dao.vo.GameBonusPoolJson;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
* @ClassName: GameAwardDaoImpl 
* @Description: 奖金组明细DAO实现类
* @author Richard
* @date 2013-12-27 下午3:48:52 
*
 */
@Repository("gameAwardDaoImpl")
public class GameAwardDaoImpl extends BaseIbatis3Dao<GameAward> implements IGameAwardDao {

	@Resource(name = "gameBonusPoolDaoImpl")
	private IGameBonusPoolDao gameBonusPoolDao;

	@Override
	public List<GameAward> queryGameAwardByGroupId(Long groupId, Long lotteryId, Integer status) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("lotteryId", lotteryId);
		map.put("awardGroupId", groupId);
		map.put("status", status);

		return sqlSessionTemplate.selectList("queryGameAwardByGroupId", map);
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
	public void saveOrUpdate(List<GameAwardCheck> awardCheck, GameAwardGroup awardGroup) throws Exception {

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

	/**
	* Title: getGameBonusJsonBean
	* Description:
	* @param lotteryId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.dao.IGameAwardDao#getGameBonusJsonBean(java.lang.Long) 
	*/
	@Override
	public GameBonusJsonBean getGameBonusJsonBean(Long lotteryId) throws Exception {
		GameBonusJsonBean bean = new GameBonusJsonBean();
		List<GameBonusAwardJsonBean> gameBonusAwardJsonBeans = new ArrayList<GameBonusAwardJsonBean>();
		List<GameAward> gameAwards = this.sqlSessionTemplate.selectList(getQueryPath("getGameAwardByLotteryId"),
				lotteryId);

		for (GameAward gameAward : gameAwards) {
			GameBonusAwardJsonBean gameBonusAwardJsonBean = new GameBonusAwardJsonBean();
			gameBonusAwardJsonBean.setGameBetType(gameAward.getBetTypeCode());
			gameBonusAwardJsonBean.setMaxAward(gameAward.getActualBonus());
			gameBonusAwardJsonBean.setMinAward(gameAward.getActualBonusDown());
			gameBonusAwardJsonBeans.add(gameBonusAwardJsonBean);
		}
		bean.setAwards(gameBonusAwardJsonBeans);

		GameBonusPool gameBonusPool = gameBonusPoolDao.queryGameBonus(lotteryId);
		if (gameBonusPool != null) {
			GameBonusPoolJson jsonObject = new GameBonusPoolJson();
			jsonObject.setLotteryid(gameBonusPool.getLotteryid());
			jsonObject.setActualBonus(gameBonusPool.getActualBonus() == null ? null
					: gameBonusPool.getActualBonus() );
			jsonObject.setMinimumBonus(gameBonusPool.getMinimumBonus() == null ? null
					: gameBonusPool.getMinimumBonus() );
			jsonObject.setDistribute1(gameBonusPool.getDistribute1() == null ? null
					: gameBonusPool.getDistribute1() );
			jsonObject.setDistribute2(gameBonusPool.getDistribute2() == null ? null
					: gameBonusPool.getDistribute2() );
			bean.setGameBonusPoolJson(jsonObject);
		}
		return bean;
	}
	
	@Override
	public List<GameAward> getGameAwardByGroupIdAndBetTypeCodeParent(
			Long awardGroupId, String betTypeCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("awardGroupId", awardGroupId);
		map.put("betTypeCode", betTypeCode); 
		return sqlSessionTemplate.selectList(getQueryPath("getGameAwardByGroupIdAndBetTypeCodeParent"), map);
	}

	@Override
	public List<GameAward> getGameAwardByLotteryIdFilterFourCode(Long lotteryId) throws Exception {
		return this.sqlSessionTemplate.selectList(getQueryPath("getGameAwardByLotteryIdFilterFourCode"),
				lotteryId);
	}
}
