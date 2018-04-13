package com.winterframework.firefrog.game.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGamePackageItemDao;
import com.winterframework.firefrog.game.dao.vo.GamePackageItem;
import com.winterframework.firefrog.game.service.IGamePackageItemService;

@Service("gamePackageItemServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GamePackageItemServiceImpl implements IGamePackageItemService {

	@Resource(name = "gamePackageItemDao")
	private IGamePackageItemDao gamePackageItemDao;
	
	@Override
	public List<GamePackageItem> queryPackageItemListByPackageId(Long packageId) {
		
		return gamePackageItemDao.getPackageItemListByPackageId(packageId);
	}

}
