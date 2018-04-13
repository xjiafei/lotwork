package com.winterframework.firefrog.game.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameAwardCheckDao;
import com.winterframework.firefrog.game.dao.vo.GameAwardCheck;
import com.winterframework.firefrog.game.entity.GameAwardEntity.GameAwardStatus;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
* @ClassName: GameAwardCheckDaoImpl 
* @Description: 奖金组check表 DAO实现类。
* @author Richard
* @date 2013-12-27 下午3:46:17 
*
 */
@Repository("gameAwardCheckDaoImpl")
public class GameAwardCheckDaoImpl extends BaseIbatis3Dao<GameAwardCheck> implements IGameAwardCheckDao {

	@Override
	public List<GameAwardCheck> queryGameAwardCheckByGroupId(Long awardGroupId) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("awardGroupId", awardGroupId);
		
		return this.sqlSessionTemplate.selectList("queryGameAwardCheckByGroupId", map);
	}

	@Override
	public List<GameAwardCheck> queryGameAwardCheckByGroupId(Long awardGroupId, Long lotteryId, Integer status) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("awardGroupId", awardGroupId);
		map.put("lotteryId", lotteryId);
		map.put("status", status);
		
		return this.sqlSessionTemplate.selectList("queryGameAwardCheckByGroupId", map);
	}

	@Override
	public void updataStatusByGroupId(Long awardGroupId, Long lotteryId, Long status) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("lotteryid", lotteryId);
		map.put("awardGroupId", awardGroupId);
		
		this.sqlSessionTemplate.update("updataStatusByGroupId", map);
	}

	@Override
	public void delByAwardGroupId(Long awardId) throws Exception {
			
		this.sqlSessionTemplate.delete("delByAwardGroupId", awardId);
	}

	@Override
	public void saveOrUpdate(GameAwardCheck check) throws Exception {
		
		GameAwardCheck _check = null;
		if(null != check.getId()){
			_check = getById(check.getId());
			_check.setStatus(GameAwardStatus.WATING_AUDITING.getValue());
			_check.setActualBonus(check.getActualBonus());
			_check.setUpdateTime(new Date());
		}
		if(null == _check){
			insert(check);
			return ;
		}
		update(_check);
	}


}
