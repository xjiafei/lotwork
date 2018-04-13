package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameBettypeStatusCheckDao;
import com.winterframework.firefrog.game.dao.vo.GameBettypeStatusCheck;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: GameBettypeStatusCheckDaoImpl 
* @Description: 投注方式状态实现类 
* @author Denny 
* @date 2013-8-27 上午11:05:32 
*  
*/
@Repository("gameBettypeStatusCheckDaoImpl")
public class GameBettypeStatusCheckDaoImpl extends BaseIbatis3Dao<GameBettypeStatusCheck> implements IGameBettypeStatusCheckDao {

	@Override
	public void updateToPublish(Long lotteryid, Long auditType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryid);
		map.put("status", auditType==1 ? 4 : 5);
		this.sqlSessionTemplate.update("com.winterframework.firefrog.game.dao.vo.GameBettypeStatusCheck.updateToPublish", map);
	}

	@Override
	public List<GameBettypeStatusCheck> getAllByLotteryId(Long lotteryid) {
		return this.sqlSessionTemplate.selectList("com.winterframework.firefrog.game.dao.vo.GameBettypeStatusCheck.getAllByLotteryId", lotteryid);
	}

	@Override
	public GameBettypeStatusCheck queryGameBettypeStatusCheckByMap(Map<String, Object> map) {
		return this.sqlSessionTemplate.selectOne("com.winterframework.firefrog.game.dao.vo.GameBettypeStatusCheck.getGBSCByMap", map);
	}

	@Override
	public void insertToCheck(GameBettypeStatusCheck g) {
		this.insert(g);
	}

	@Override
	public void updateToCheck(GameBettypeStatusCheck g) {
		this.sqlSessionTemplate.update("com.winterframework.firefrog.game.dao.vo.GameBettypeStatusCheck.updateToCheck", g);
	}

	@Override
	public void deleteAllByLotteryId(Long lotteryid) {
		List<GameBettypeStatusCheck> list = getAllByLotteryId(lotteryid);
		for (GameBettypeStatusCheck g : list) {
			this.delete(g.getId());
		}
	}

	@Override
	public void updateBetttypeStatusCheckToNotPublished(Long lotteryid) {
		this.sqlSessionTemplate.update("com.winterframework.firefrog.game.dao.vo.GameBettypeStatusCheck.updateBetttypeStatusCheckToNotPublished", lotteryid);
	}

}
