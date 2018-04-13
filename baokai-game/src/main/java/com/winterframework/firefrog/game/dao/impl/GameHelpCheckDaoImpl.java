package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameHelpCheckDao;
import com.winterframework.firefrog.game.dao.vo.GameHelpCheck;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
* @ClassName: GameHelpCheckDaoImpl 
* @Description: 玩法描述审核Dao实现类
* @author Denny
* @date 2013-8-23 上午9:59:37 
*
 */
@Repository("gameHelpCheckDaoImpl")
public class GameHelpCheckDaoImpl extends BaseIbatis3Dao<GameHelpCheck> implements IGameHelpCheckDao {

	@Override
	public void addGameHelpCheck(long lotteryid) {

	}

	@Override
	public void removeGameHelpCheck(long id) {

	}

	@Override
	public void updateGameHelpCheck(GameHelpCheck GameHelpCheck) {

	}

	@Override
	public void insertAll(List<GameHelpCheck> entitys) {
		for (GameHelpCheck ghc : entitys) {
			this.insert(ghc);
		}
		
	}

	@Override
	public List<GameHelpCheck> getGameHelpCheckByLotteryId(long lotteryid) {
		return this.sqlSessionTemplate.selectList("getGameHelpCheckByLotteryId", lotteryid);
	}

	@Override
	public void removeGameHelpCheckByLotteryId(Long lotteryid) {
		List<GameHelpCheck> ghcs = this.getGameHelpCheckByLotteryId(lotteryid);
		for (GameHelpCheck ghc : ghcs) {
			long id = ghc.getId();
			this.delete(id);
		}
	}

	@Override
	public void updateToCheck(GameHelpCheck g) {
		this.sqlSessionTemplate.update("com.winterframework.firefrog.game.dao.vo.GameHelpCheck.updateToCheck", g);
	}

	@Override
	public void checkGameHelpCheck(Long lotteryid, Long auditType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryid);
		map.put("status", 5);
		this.sqlSessionTemplate.update("com.winterframework.firefrog.game.dao.vo.GameHelpCheck.updateGameHelpStatus", map);
	}

}
