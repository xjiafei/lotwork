package com.winterframework.firefrog.game.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Override
	public GamePlan getGamePlanByPackageId(Long packageId) {
		return this.sqlSessionTemplate.selectOne("getGamePlanByPackageId", packageId);
	}

	@Override
	public void redoGamePlan(Long lotteryId, Long issueCode, Date saleTime) {
		Map<String, Object> orderMap = new HashMap<String, Object>();
		orderMap.put("lotteryId", lotteryId);
		orderMap.put("issueCode", issueCode);
		if (saleTime != null) {
			orderMap.put("saleTime", saleTime);
		}
		this.sqlSessionTemplate.update("redoGamePlan", orderMap);
	}

	/** 
	* @Title: getGamePlanByOrderId 
	* @Description: 根据OrderId获取追号对象
	* @param orderId
	* @return
	*/
	public GamePlan getGamePlanByOrderId(Long orderId) {
		return this.sqlSessionTemplate.selectOne("getGamePlanByOrderId", orderId);
	}

	@Override
	public Long getOrderIdbyPlanId(Long planId, Long issueCode) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planId", planId);
		map.put("issueCode", issueCode);

		return this.sqlSessionTemplate.selectOne("getOrderIdbyPlanId", map);
	}
	@Override
	public GamePlan getByPackageId(Long packageId) throws Exception {
		return this.sqlSessionTemplate.selectOne("getByPackageId", packageId);
	}

	@Override
	public GamePlan getPlanById(Long planId) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planId", planId);
		return this.sqlSessionTemplate.selectOne("getPlanById", map);
	}
	
	@Override
	public List<GamePlan> getGamePlanByLotteryIdAndIssueCode(Long lotteryId,
			Long issueCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.selectList("getGamePlanByLotteryIdAndIssueCode", map);
	}
	@Override
	public List<GamePlan> getByLotteryIdAndStartIssueCode(Long lotteryId,
			Long startIssueCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("startIssueCode", startIssueCode);
		return this.sqlSessionTemplate.selectList("getByLotteryIdAndStartIssueCode", map);
	}

	@Override
	public List<GamePlan> getGamePlanByLotteryIdAndIssueCodeAndNoOrder(
			Long lotteryId, Long issueCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.selectList("getGamePlanByLotteryIdAndIssueCodeAndNoOrder", map);
	}
	@Override
	public boolean hasNoOrder(Long planId) throws Exception {
		return this.sqlSessionTemplate.selectOne("hasNoOrder", planId)!=null;
	}
}
