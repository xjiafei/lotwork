package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GamePackageItem;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGamePackageItemDao extends BaseDao<GamePackageItem> {

	void savePackageitemList(List<com.winterframework.firefrog.game.entity.GamePackageItem> list) throws Exception;

	List<GamePackageItem> queryGamePackeItemListByPackId(Long id);

	List<GamePackageItem> getPackageItemListByPackageId(Long id);
	
	List<GamePackageItem> getPackageItemListByIssue(Long ilotteryidd,Long issuecode);
}
