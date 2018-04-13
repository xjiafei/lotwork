package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameMultipleCheckDao;
import com.winterframework.firefrog.game.dao.vo.GameMultipleCheck;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: GameMultipleCheckDaoImpl 
* @Description: 投注限制审核DAO实现类 
* @author Denny 
* @date 2013-8-25 下午10:22:11 
*  
*/
@Repository("gameMultipleCheckDaoImpl")
public class GameMultipleCheckDaoImpl extends BaseIbatis3Dao<GameMultipleCheck> implements IGameMultipleCheckDao {

	@Override
	public List<GameMultipleCheck> getGameMultipleCheckByLotteryId(long lotteryid) {
		List<GameMultipleCheck> gameMultipleCheckList = sqlSessionTemplate.selectList(
				"com.winterframework.firefrog.game.dao.vo.GameMultipleCheck.getAllByLotteryId", lotteryid);
		return gameMultipleCheckList;
	}

	@Override
	public GameMultipleCheck getGameMultipleCheckByCondition(Map<String, Object> map) {
		GameMultipleCheck entity = sqlSessionTemplate.selectOne("getEntityByCondition", map);
		return entity;
	}

	@Override
	public void addGameMultipleCheck(GameMultipleCheck gameMultipleCheck) {
		this.insert(gameMultipleCheck);
	}

	@Override
	public void removeGameMultipleCheckByLotteryId(long lotteryid) {
		List<GameMultipleCheck> gmcs = this.getGameMultipleCheckByLotteryId(lotteryid);
		for (GameMultipleCheck gmc : gmcs) {
			long id = gmc.getId();
			this.delete(id);
		}
	}

	@Override
	public void updateGameMultipleCheck(GameMultipleCheck GameMultipleCheck) {
		this.update(GameMultipleCheck);
	}

	@Override
	public void insert(List<GameMultipleCheck> entitys) {
		super.insert(entitys);
	}

	@Override
	public void checkGameMultipleCheck(Long lotteryid, Long auditType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryid);
		map.put("status", auditType==1 ? 4 : 5);//审核通过则改为待发布，不通过则改为审核不通过
		this.sqlSessionTemplate.update("updateGameMultipleCheck", map);
	}

	@Override
	public void publishGameMultipleCheck(Long lotteryid, Long publishType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryid);
		map.put("status", 6);//发布不通过
		this.sqlSessionTemplate.update("updateGameMultipleCheck", map);
	}

}
