package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GamePackageItem;

public interface IGamePackageItemService {

	public List<GamePackageItem> queryPackageItemListByPackageId(Long packageId);
}
