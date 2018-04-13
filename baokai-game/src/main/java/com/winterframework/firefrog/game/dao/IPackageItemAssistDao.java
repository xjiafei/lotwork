package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.entity.PackageItemAssist;

public interface IPackageItemAssistDao {
	/** 
	* @Title: savePackageAssistItem 
	* @Description: 保存单个方案辅助信息
	* @param gamePackageItem
	*/
	public void savePackageAssistItem(PackageItemAssist gamePackageItem);

	/** 
	* @Title: savePackageAssistItem 
	* @Description: 保存方案辅助信息列表
	* @param gamePackageItemList
	*/
	public void savePackageAssistItem(List<PackageItemAssist> gamePackageItemList);

	/** 
	* @Title: getPackageAssistItemList 
	* @Description: 根据方案细节id获取对应的辅助信息列表
	* @param itemId
	* @return
	*/
	List<PackageItemAssist> getPackageAssistItemList(Long itemId);
}
