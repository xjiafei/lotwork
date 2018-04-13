package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GamePackage;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGamePackageDao extends BaseDao<GamePackage> {

	Long savePackage(com.winterframework.firefrog.game.entity.GamePackage gamePackage) throws Exception;

	/**
	 * 
	* @Title: queryGamePackageByPlanId 
	* @Description: 根据PlanId， lotteryId， 上一期期号 获取方案表信息。
	* @param id
	* @param lotteryId
	* @param preIssueCode
	* @return
	 */
	GamePackage queryGamePackageByPlanId(Long id, Long lotteryId, Long preIssueCode);
	
	List<GamePackage> getByLotteryIdAndIssueCode(Long lotteryId,Long issueCode) throws Exception;

}
