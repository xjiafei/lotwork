package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGamePackageDao;
import com.winterframework.firefrog.game.dao.vo.GamePackage;
import com.winterframework.firefrog.game.dao.vo.VOConverter4Task;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gamePackDaoImpl")
public class GamePackDaoImpl extends BaseIbatis3Dao<GamePackage> implements IGamePackageDao {

	@Override
	public Long savePackage(com.winterframework.firefrog.game.entity.GamePackage gamePackageEntity) throws Exception {
		GamePackage gamePackage = VOConverter4Task.gpEntity2gpVo(gamePackageEntity);
		insert(gamePackage);
		return gamePackage.getId();
	}

	@Override
	public GamePackage queryGamePackageByPlanId(Long id, Long lotteryId, Long preIssueCode) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planId", id);
		map.put("lotteryId", lotteryId);
		map.put("issueCode", preIssueCode);

		return this.sqlSessionTemplate.selectOne(getQueryPath("queryGamePackageByPlanId"), map);
	}
	@Override
	public List<GamePackage> getByLotteryIdAndIssueCode(Long lotteryId,
			Long issueCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId); 
		map.put("issueCode", issueCode);

		return this.sqlSessionTemplate.selectOne(getQueryPath("getByLotteryIdAndIssueCode"), map);
	}

}
