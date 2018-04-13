package com.winterframework.firefrog.game.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrder.FundStatus;
import com.winterframework.firefrog.game.entity.GameOrderOperationsEntity;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
* @ClassName: GameOrderDaoImpl 
* @Description: 游戏订单 DAO
* @author Richard
* @date 2013-11-18 下午3:39:32 
*
 */
@Repository("gameOrderDaoImpl")
public class GameOrderDaoImpl extends BaseIbatis3Dao<GameOrder> implements IGameOrderDao {

	@Override
	public List<GameOrder> queryOrderByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);

		//这里只获取等待开奖的订单信息
		return this.sqlSessionTemplate.selectList("queryOrderByLotteryIdAndIssueCode", map);
	}

	@Override
	public List<GameOrder> queryOrderByParent(Long parentId, Long parentType) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", parentId);
		map.put("parentType", parentType);

		return this.sqlSessionTemplate.selectList("queryOrderByParent", map);
	}

	@Override
	public List<GameOrderOperationsEntity> getOrdersByIssueCode(Long issueCode) {
		return this.sqlSessionTemplate.selectList("getOrdersByIssueCode", issueCode);
	}

	@Override
	public Long saveGameOrder(GameOrder gameOrderVo) {
		insert(gameOrderVo);
		return gameOrderVo.getId();
	}

	@Override
	public void updateOrders(Map<String, Object> map) throws Exception {
		this.sqlSessionTemplate.update("updateOrders", map);
	}

	@Override
	public void updateOrders2(Map<String, Object> map) throws Exception {
		this.sqlSessionTemplate.update("updateOrders2", map);
	}

	@Override
	public List<GameOrder> getGameOrderByPreGameIssueAndLotterId(Long issueCode, Long lotteryId) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);

		//这里只获取等待开奖、中奖、未中奖的订单信息。
		return this.sqlSessionTemplate.selectList("getGameOrderByPreGameIssueAndLotterId", map);
	}

	@Override
	public GameOrder getGameOrderBySlipId(Long gameSlipId) {
		return this.sqlSessionTemplate.selectOne("getGameOrderBySlipId", gameSlipId);
	}

	@Override
	public List<GameOrder> getGameOrderListByLotteryAndIssueCode(Long lotteryId, Long issueCode) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.selectList("getGameOrderListByLotteryAndIssueCode", map);
	}

	@Override
	public GameOrder selectGameOrderByPlanIdAndIssueCode(Long planId, Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planId", planId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.selectOne("getGameOrderByPlanIdAndIssueCode", map);
	}

	@Override
	public List<GameOrder> selectGameOrderByPlanid(Long planid) {
		return this.sqlSessionTemplate.selectList("queryOrderListByPlanid", planid);
	}

	@Override
	public Long selectOrderWinByOrderCode(String orderCode) {
		return this.sqlSessionTemplate.selectOne("getOrderWinbyOrderCode", orderCode);
	}

	@Override
	public List<GameOrder> selectFollowGameOrderByPlanid(Map<String, Object> orderMap) {
		return this.sqlSessionTemplate.selectList("getFollowGameOrderByPlanid", orderMap);
	}

	@Override
	public List<GameOrder> getGameOrderByLotteryIdAndIssueCodeStatus(Map<String, Object> orderMap) {
		return this.sqlSessionTemplate.selectList("getGameOrderByLotteryIdAndIssueCodeStatus", orderMap);
	}

	@Override
	public void undoGameOrders(Map<String, Object> orderMap) {
		this.sqlSessionTemplate.update(getQueryPath("undoGameOrders"), orderMap);
	}

	@Override
	public Date getCalculateTimeByLotteryIdAndIssueCode(Map<String, Object> orderMap) {
		GameOrder gameOrder = this.sqlSessionTemplate.selectOne(
				getQueryPath("getCalculateTimeByLotteryIdAndIssueCode"), orderMap);
		return gameOrder.getCalculateWinTime();
	}

	@Override
	public List<GameOrder> getGameOrderByLotteryInfo(Map<String, Object> orderMap) {
		return this.sqlSessionTemplate.selectList("getGameOrderByLotteryInfo", orderMap);
	}

	@Override
	public List<GameOrder> getNextIssueGameOrder(Long orderId) {
		return this.sqlSessionTemplate.selectList("getNextIssueGameOrder", orderId);
	}

	@Override
	public List<GameOrder> getGamePlanOrderListByLotteryAndIssueCode(Long lotteryId, Long issueCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lotteryId", lotteryId);
		paramMap.put("issueCode", issueCode);
		return this.sqlSessionTemplate.selectList("getGamePlanOrderListByLotteryAndIssueCode", paramMap);
	}

	/** 
	* @Title: getBetAfterDrawTimeOrder 
	* @Description: 找出在开奖时间后投注的单
	* @param lotteryId
	* @param issueCode
	* @param drawTime
	* @return
	*/
	@Override
	public List<GameOrder> getBetAfterDrawTimeOrders(Long lotteryId, Long issueCode, Date drawTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		map.put("drawTime", drawTime);
		return this.sqlSessionTemplate.selectList("getBetAfterDrawTimeOrders", map);
	}

	@Override
	public List<GameOrder> getOrderByCancalGamePlanDetail(Long planId, Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planId", planId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.selectList("getOrderByCancalGamePlanDetail", map);
	}

	@Override
	public List<GameOrder> queryOrderByLotteryIdAndIssueCodeForKaiJiang(Long lotteryId, Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		//这里只获取等待开奖的订单信息
		return this.sqlSessionTemplate.selectList("queryOrderByLotteryIdAndIssueCodeForKaiJiang", map);
	} 
	@Override
	public List<GameOrder> getGameOrderByIssueAndLottery(
			Long lotteryId, Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode); 
		return this.sqlSessionTemplate.selectList("getGameOrderByIssueAndLottery", map);
	}
	@Override
	public List<GameOrder> getFromPlanByIssueAndLottery(Long lotteryId,
			Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode); 
		return this.sqlSessionTemplate.selectList(getQueryPath("getFromPlanByIssueAndLottery"), map);
	}
	@Override
	public List<GameOrder> getOrderFollowedByPlanIdAndIssueCode(Long planId,
			Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planId", planId);
		map.put("issueCode", issueCode); 
		return this.sqlSessionTemplate.selectList("getOrderFollowedByPlanIdAndIssueCode", map);
	}
	@Override
	public List<GameOrder> getNotPlanByLotteryAndIssue(Long lotteryId,
			Long issueCode) { 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode); 
		return this.sqlSessionTemplate.selectList("getNotPlanByLotteryAndIssue", map);
	}
	@Override
	public List<GameOrder> getByPackageId(Long packageId) throws Exception { 
		return this.sqlSessionTemplate.selectList("getByPackageId", packageId);
	}
	@Override
	public GameOrder getOrderByPlanDetailId(Long planDetailId) {
		return this.sqlSessionTemplate.selectOne("getOrderByPlanDetailId", planDetailId);
	}
	
	@Override
	public List<GameOrder> getGameOrderByLotteryIdAndTime(Long lotteryId,
			Date startTime, Date endTime) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("startTime", startTime);
		map.put("endTime", endTime); 
		return this.sqlSessionTemplate.selectList("getGameOrderByLotteryIdAndTime", map);
	}
	
	@Override
	public List<GameOrder> getGameOrderWaiting(Long planId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planId",planId); 
		map.put("status", GameOrder.Status.WAIT.getValue());
		return this.sqlSessionTemplate.selectList("getGameOrderWaiting", map);
	}
	@Override
	public List<GameOrder> getByLotteryUserIdTime(Long lotteryId, Long userId,
			Date startTime, Date endTime) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId",lotteryId); 
		map.put("userId",userId); 
		map.put("startTime",startTime); 
		map.put("endTime", endTime);
		return this.sqlSessionTemplate.selectList("getByLotteryUserIdTime", map);
	}

	@Override
	public List<GameOrder> getOrderListByGameIssue(Long issueCode) {
		return this.sqlSessionTemplate.selectList("getOrderListByGameIssue", issueCode);
	}
	@Override
	public Integer getCountByLotteryIdAndIssueCodeAndFundStatus(Long lotteryId,
			Long issueCode, FundStatus fundStatus) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId",lotteryId); 
		map.put("issueCode",issueCode); 
		map.put("fundStatus",fundStatus.getValue());  
		return this.sqlSessionTemplate.selectOne(getQueryPath("getCountByLotteryIdAndIssueCodeAndFundStatus"), map);
	}
	@Override
	public List<GameOrder> getByLotteryIdAndIssueCodeAndFundStatusAndBatchSize(
			Long lotteryId, Long issueCode, FundStatus fundStatus, int batchSize)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId",lotteryId); 
		map.put("issueCode",issueCode); 
		map.put("fundStatus",fundStatus.getValue());
		map.put("batchSize",batchSize);  
		return this.sqlSessionTemplate.selectList(getQueryPath("getByLotteryIdAndIssueCodeAndFundStatusAndBatchSize"), map);
	}
	@Override
	public List<GameOrder> getByLotteryIdAndIssueCodeAndFundStatusAndIndexes(
			Long lotteryId, Long issueCode, FundStatus fundStatus,
			int beginIndex, int endIndex) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId",lotteryId); 
		map.put("issueCode",issueCode); 
		map.put("fundStatus",fundStatus.getValue());
		map.put("beginIndex",beginIndex);
		map.put("endIndex",endIndex);  
		return this.sqlSessionTemplate.selectList(getQueryPath("getByLotteryIdAndIssueCodeAndFundStatusAndIndexes"), map);
	}
	@Override
	public Integer updateFundStatus(FundStatus fundStatus,
			List<String> orderCodeList) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("fundStatus",fundStatus.getValue());
		map.put("orderCodeList",orderCodeList);  
		return this.sqlSessionTemplate.update(getQueryPath("updateFundStatus"), map);
	}

	@Override
	public int updateMMC(GameOrder order) {
		return this.sqlSessionTemplate.update(getQueryPath("updateMMC"), order);
	}
	
	@Override
	public Long getSumAmtByUserIdAndStatus(Long userId, List<Integer> statuses,Date dateLimit,Date chargeTime) {
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("userId",userId);
		map.put("statuses",statuses);  
		map.put("dateLimit",dateLimit);
		map.put("chargeTime", chargeTime);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getSumAmtByUserIdAndStatus"), map);
	}

	@Override
	public Long getSumAmtByUserThisDay(Long userId, List<Integer> statuses,Date validTime) {
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("userId",userId);
		map.put("statuses",statuses);  
		map.put("missionValidTime", validTime);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getSumAmtByUserThisDay"), map);
	}
	
	@Override
	public List<String> queryBeginMissionOrder(Map<String,Object> params) {
		return this.sqlSessionTemplate.selectList(getQueryPath("queryBeginMissionOrder"), params);
	}
	
	@Override
	public GameOrder getOrderByPlanIdAndIssueCode(Long planId, Long nextIssueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planId", planId);
		map.put("issueCode", nextIssueCode);
		return this.sqlSessionTemplate.selectOne("getOrderByPlanIdAndIssueCode", map);
	}
	
}
