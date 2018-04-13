package com.winterframework.firefrog.game.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGamePackageItemDao;
import com.winterframework.firefrog.game.dao.vo.GamePackageItem;
import com.winterframework.firefrog.game.dao.vo.VOConverter4Task;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gamePackageItemDao")
public class GamePackageItemDaoImpl extends BaseIbatis3Dao<GamePackageItem> implements IGamePackageItemDao {

	@Override
	public void savePackageitemList(List<com.winterframework.firefrog.game.entity.GamePackageItem> list)
			throws Exception {

		List<GamePackageItem> gamePackageItemList = new ArrayList<GamePackageItem>();
		for (com.winterframework.firefrog.game.entity.GamePackageItem gamePackageItemEntity : list) {
			GamePackageItem gamePackageItem = VOConverter4Task.gpiEntity2gpiVo(gamePackageItemEntity);
			gamePackageItemList.add(gamePackageItem);
		}
		insert(gamePackageItemList);
	}

	@Override
	public List<GamePackageItem> queryGamePackeItemListByPackId(Long id) {

		GamePackageItem entity = new GamePackageItem();
		entity.setPackageid(id);

		return this.getAllByEntity(entity);
	}

	@Override
	public List<GamePackageItem> getPackageItemListByPackageId(Long id) {
		return this.sqlSessionTemplate.selectList("getPackageItemListByPackageId", id);
	}

	@Override
	public List<GamePackageItem> getPackageItemListByIssue(Long lotteryid,Long issuecode) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("lotteryid", lotteryid);
		map.put("issuecode", issuecode);
		return this.sqlSessionTemplate.selectList("getplanItem", map);
	}
	
}
