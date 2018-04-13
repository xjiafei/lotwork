package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGamePackageDao;
import com.winterframework.firefrog.game.dao.IGameUserAwardGroupDao;
import com.winterframework.firefrog.game.dao.vo.GamePackage;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.UserAwardGroupEntity;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gamePackageDao")
public class GamePackageDaoImpl extends BaseIbatis3Dao<GamePackage> implements IGamePackageDao {
	@Resource(name = "gameUserAwardGroupDaoImpl")
	private IGameUserAwardGroupDao gameUserAwardGroupDao;

	@Override
	public Long savePackage(com.winterframework.firefrog.game.entity.GamePackage gamePackageEntity) {
		GamePackage gamePackage = VOConverter.gpEntity2gpVo(gamePackageEntity);
		UserAwardGroupEntity awardGroup = gameUserAwardGroupDao.selectUserAwardGroup(gamePackage.getUserid(),
				gamePackage.getLotteryid());
		Long sysAwardGroupId = awardGroup.getSysGroupAward().getId();
		gamePackage.setAwardId(sysAwardGroupId);
		insert(gamePackage);
		return gamePackage.getId();
	}
	
	@Override
	public Long saveMMCPackage(com.winterframework.firefrog.game.entity.GamePackage gamePackageEntity) {
		GamePackage gamePackage = VOConverter.gpEntity2gpVo(gamePackageEntity);
		UserAwardGroupEntity awardGroup = gameUserAwardGroupDao.selectUserAwardGroup(gamePackage.getUserid(),
				gamePackage.getLotteryid());
		Long sysAwardGroupId = awardGroup.getSysGroupAward().getId();
		gamePackage.setAwardId(sysAwardGroupId);
		insert(gamePackage);
		return gamePackage.getId();
	}

	@Override
	public GamePackage queryGamePackageByPlanId(Long id, Long lotteryId, Long preIssueCode) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planId", id);
		map.put("lotteryId", lotteryId);
		map.put("issueCode", preIssueCode);

		return this.sqlSessionTemplate.selectOne("queryGamePackageByPlanId", map);
	}

	@Override
	public GamePackage queryGamePackageById(Long id) {
		return this.getById(id);
	}

	@Override
	public List<GamePackage> getGamePackageByPackageCode(String packageCode) {
		return this.sqlSessionTemplate.selectList(getQueryPath("getGamePackageByPackageCode"), packageCode);
	}
}
