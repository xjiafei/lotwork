package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.entity.GamePackage;
import com.winterframework.firefrog.game.entity.GamePackageItem;

public interface IGamePackageItemDao {
	public void savePackageitem(GamePackageItem gamePackageItem);

	public void savePackageitemList(List<GamePackageItem> gamePackageItemList,GamePackage gamePackage) throws Exception;
	
	public List<com.winterframework.firefrog.game.dao.vo.GamePackageItem> getPackageItemListByPackageId(Long packageId);
	
	public void deleteByPackageId(Long packageId);

}
