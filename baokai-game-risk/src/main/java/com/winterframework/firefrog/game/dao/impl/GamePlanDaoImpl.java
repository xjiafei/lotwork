package com.winterframework.firefrog.game.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
* @ClassName: GamePlanDaoImpl 
* @Description: GamePlan DAO 
* @author Richard
* @date 2013-11-18 下午8:16:02 
*
 */
@Repository("gamePlanDaoImpl")
public class GamePlanDaoImpl extends BaseIbatis3Dao<GamePlan> implements IGamePlanDao {

	@Override
	public Long getChannelIdByOrderId(Long id) {

		Long chennalId = this.sqlSessionTemplate.selectOne("getChannelIdByOrderId", id);
		if (null == chennalId) {
			return 1L; //web
		}
		return chennalId;
	}

	@Override
	public List<GamePlan> getPauseGamePlanList(Long lotteryId) {

		return this.sqlSessionTemplate.selectList("getPauseGamePlanList", lotteryId);
	}
	
	@Override
	public List<GamePlan> getGamePlanListByIssue(Long lotteryId) {
		return this.sqlSessionTemplate.selectList("getGamePlanListByIssue", lotteryId);
	}
	
	/** 
	* @Title: getGamePlanByOrderId 
	* @Description: 根据OrderId获取追号对象
	* @param orderId
	* @return
	*/
	public GamePlan getGamePlanByOrderId(Long orderId){
		return this.sqlSessionTemplate.selectOne("getGamePlanByOrderId", orderId);
	}

}
