package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.entity.GamePackage;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGamePackageDao extends BaseDao<com.winterframework.firefrog.game.dao.vo.GamePackage> {

	public Long savePackage(GamePackage gamePackage);

	public com.winterframework.firefrog.game.dao.vo.GamePackage queryGamePackageByPlanId(Long id, Long lotteryId,
			Long startIsuueCode);

	com.winterframework.firefrog.game.dao.vo.GamePackage queryGamePackageById(Long id);

	/** 
	* @Title: getGamePackageByPackageCode 
	* @Description: 根据方案订单号判断是否已经存在
	* @param packageCode
	* @return
	*/
	public List<com.winterframework.firefrog.game.dao.vo.GamePackage> getGamePackageByPackageCode(String packageCode);

	public Long saveMMCPackage(GamePackage gamePackage);

}
