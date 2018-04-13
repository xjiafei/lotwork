package com.winterframework.firefrog.game.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IPackageItemAssistDao;
import com.winterframework.firefrog.game.dao.vo.GamePackageAssist;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.PackageItemAssist;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("packageItemAssistDaoImpl")
public class PackageItemAssistDaoImpl extends BaseIbatis3Dao<GamePackageAssist> implements IPackageItemAssistDao {

	@Override
	public void savePackageAssistItem(PackageItemAssist gamePackageItem) {
		GamePackageAssist vo = VOConverter.convertPackageAssistEntity2Vo(gamePackageItem);
		this.insert(vo);
	}

	@Override
	public void savePackageAssistItem(List<PackageItemAssist> gamePackageItemList) {
		List<GamePackageAssist> list = new ArrayList<GamePackageAssist>();
		for (PackageItemAssist slipItemAssist : gamePackageItemList) {
			list.add(VOConverter.convertPackageAssistEntity2Vo(slipItemAssist));
		}
		this.insert(list);
	}

	@Override
	public List<PackageItemAssist> getPackageAssistItemList(Long packageId) {
		List<GamePackageAssist> list = this.sqlSessionTemplate.selectList(getQueryPath("getItemByOrderId"), packageId);
		List<PackageItemAssist> entityList = new ArrayList<PackageItemAssist>();
		for (GamePackageAssist slipItemAssist : list) {
			entityList.add(VOConverter.convertPackageAssistVo2Entity(slipItemAssist));
		}
		return entityList;
	}
}
