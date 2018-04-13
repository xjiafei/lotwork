package com.winterframework.firefrog.game.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameAwardGroupCheckDao;
import com.winterframework.firefrog.game.dao.vo.GameAwardGroupCheck;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.GameAwardGroupEntity;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;
/**
 * 
* @ClassName: GameAwardGroupCheckDaoImpl 
* @Description:奖金组check信息表实现类
* @author Richard
* @date 2013-12-27 下午3:50:13 
*
 */
@Repository("gameAwardGroupCheckDaoImpl")
public class GameAwardGroupCheckDaoImpl extends BaseIbatis3Dao<GameAwardGroupCheck> implements IGameAwardGroupCheckDao {

	@Override
	public Long saveGameAwardGroupCheck(GameAwardGroupCheck awardGroupCheck) throws Exception {
		
		Long id = sqlSessionTemplate.selectOne("getGameAwardGroupId"); 
		
		awardGroupCheck.setId(id);
		insert(awardGroupCheck);
		
		return id;
	}

	@Override
	public GameAwardGroupCheck queryGameAwardGroupCheckById(Long lotteryId, Long groupId) throws Exception {
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("lotteryId", lotteryId);
		map.put("id", groupId);
		
		return this.sqlSessionTemplate.selectOne("queryGameAwardGroupCheckByLIdAndGId", map);
	}

	@Override
	public List<GameAwardGroupEntity> queryGameAwardGroupChecks(Long lotteryId, Integer status, Long awardId)
			throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("checkStatus", status);
		map.put("id", awardId);
		
		List<GameAwardGroupCheck> list = sqlSessionTemplate.selectList("queryGameAwardGroupChecks", map);
		
		List<GameAwardGroupEntity> entityList = new ArrayList<GameAwardGroupEntity>();
		
		for(GameAwardGroupCheck check : list){
			
			entityList.add(VOConverter.convertGameAwardGroupCheck2Entity(check));
		}
		return entityList;
	}

	@Override
	public Boolean checkIsExistAwardName(Long lotteryId, String awardName) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("awardName", awardName);
		map.put("lotteryId", lotteryId);
		
		Long count = this.sqlSessionTemplate.selectOne("checkIsExistAwardName", map);
		
		return count==null ? false : count>0 ? true : false;
	}

	@Override
	public void saveOrUpdate(GameAwardGroupCheck awardGroupCheck) throws Exception {
		
		GameAwardGroupCheck _awardGroupCheck = this.getById(awardGroupCheck.getId());
		if(null == _awardGroupCheck){
			insert(awardGroupCheck);
			return ;
		}
		
		update(awardGroupCheck);
	}

	@Override
	public GameAwardGroupCheck queryGameGroupByLotteryIdAndName(Long lotteryId, String gameAwardName) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryId);
		map.put("awardName", gameAwardName);
		return this.sqlSessionTemplate.selectOne("queryGameGroupCheckByLotteryIdAndName", map);
	}

}
