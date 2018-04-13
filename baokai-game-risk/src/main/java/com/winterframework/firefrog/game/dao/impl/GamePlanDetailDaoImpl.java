package com.winterframework.firefrog.game.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGamePlanDetailDao;
import com.winterframework.firefrog.game.dao.vo.GamePlanDetail;
import com.winterframework.firefrog.game.entity.GamePlanDetail.GamePlanDetailStatus;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
* @ClassName: GamePlanDetailDaoImpl 
* @Description: 追号计划明细表DAO
* @author Richard，Alan
* @date 2013-11-27 下午2:27:40 
*
 */
@Repository("gamePlanDetailDaoImpl")
public class GamePlanDetailDaoImpl extends BaseIbatis3Dao<GamePlanDetail> implements IGamePlanDetailDao {

	/**
	 * 根据追号Id和期号获取追号明细信息。
	* Title: queryGamePlanDetailByPlanID
	* Description:
	* @param id
	* @param issueCode
	* @return 
	* @see com.winterframework.firefrog.game.dao.IGamePlanDetailDao#queryGamePlanDetailByPlanID(java.lang.Long, java.lang.Long)
	 */
	@Override
	public GamePlanDetail queryGamePlanDetailByPlanID(Long id, Long issueCode) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planId", id);
		map.put("issueCode", issueCode);

		return this.sqlSessionTemplate.selectOne("queryGamePlanDetailByPlanID", map);
	}

	@Override
	public GamePlanDetail queryGamePlanDetailByPlanIDWithOutStatus(Long id, Long issueCode) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planId", id);
		map.put("issueCode", issueCode);

		return this.sqlSessionTemplate.selectOne("queryGamePlanDetailByPlanIDWithOutStatus", map);
	}

	/**
	 * 根据追号Id和期号更新追号明细状态
	* Title: updateGamePlanDetailByPlanID
	* Description:
	* @param id
	* @param issueCode
	* @param status
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.dao.IGamePlanDetailDao#updateGamePlanDetailByPlanID(java.lang.Long, java.lang.Long, java.lang.Integer)
	 */
	@Override
	public int updateGamePlanDetailByPlanID(Long id, Long issueCode, Integer status) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planId", id);
		map.put("issueCode", issueCode);
		map.put("status", status);
		map.put("runTime", new Date());

		return this.sqlSessionTemplate.update("updateGamePlanDetailStatusByPlanID", map);
	}

	/**
	 * 根据彩种Id和开始、结束奖期获取追号明细列表
	* Title: queryGamePlanDetails
	* Description:
	* @param lotteryid
	* @param startIssueCode
	* @param endIssueCode
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.dao.IGamePlanDetailDao#queryGamePlanDetails(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<GamePlanDetail> queryGamePlanDetails(Long lotteryid, Long startIssueCode, Long endIssueCode)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("lotteryid", lotteryid);
		map.put("startIssueCode", startIssueCode);
		map.put("endIssueCode", endIssueCode);

		List<GamePlanDetail> list = this.sqlSessionTemplate.selectList("queryGamePlanDetailsByCondition", map);
		return list;
	}

	/**
	 * 撤销追号明细。更新追号明细状态。
	* Title: revocationGamePlanDetails
	* Description:
	* @param lotteryid
	* @param startIssueCode
	* @param endIssueCode
	* @throws Exception 
	* @see com.winterframework.firefrog.game.dao.IGamePlanDetailDao#revocationGamePlanDetails(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public void revocationGamePlanDetails(Long lotteryid, Long startIssueCode, Long endIssueCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("lotteryid", lotteryid);
		map.put("startIssueCode", startIssueCode);
		map.put("endIssueCode", endIssueCode);
		map.put("status", 2);
		map.put("cancelTime", new Date());

		this.sqlSessionTemplate.update("updateGamePlanDetailsByCondition", map);
	}

	/**
	 * 获取所有暂停计划追号的追号明细要进行追号的奖期信息,期号从小到大
	* Title: getPauseGamePlanDetailUniqueIssueCode
	* Description:
	* @param lotteryId
	* @return 
	* @see com.winterframework.firefrog.game.dao.IGamePlanDetailDao#getPauseGamePlanDetailUniqueIssueCode(java.lang.Long)
	 */
	@Override
	public List<GamePlanDetail> getPauseGamePlanDetailUniqueIssueCode(Long lotteryId) {
		return this.sqlSessionTemplate.selectList("getPauseGamePlanDetailUniqueIssueCode", lotteryId);
	}

	@Override
	public void updateGamePlanDetailStatusByPlanId(Integer status, Long planid) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("status", status);
		map.put("planid", planid);

		this.sqlSessionTemplate.update("updateGamePlanDetailsStatusByPlanid", map);
	}

	@Override
	public List<GamePlanDetail> selectGamePlanDetailsByPlanId(Long planId) {
		return this.sqlSessionTemplate.selectList(getQueryPath("getPlanDetailsByPlanId"), planId);
	}

	/**
	* Title: getPlanIdByIssueCode
	* Description: 根据追号明细获取某一奖期的所有Planid（用于暂停奖期）
	* @param issueCode
	* @return 
	 */
	@Override
	public List<Long> getPlanIdByIssueCode(Long issueCode) {
		return this.sqlSessionTemplate.selectList("getPlanIdByIssueCode", issueCode);
	}

	/**
	* Title: getPausePlanIdByIssueCode
	* Description: 根据issueCode在追号明细中获取暂停奖期的planid列表（用于继续派奖）
	* @param issueCode
	* @return 
	*/
	@Override
	public List<Long> getPausePlanIdByIssueCode(Long issueCode) {
		return this.sqlSessionTemplate.selectList("getPausePlanIdByIssueCode", issueCode);
	}

	@Override
	public GamePlanDetail selectGamePlanDetailsByPlanIdAndIssueCode(Long parentid, Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("planId", parentid);
		map.put("issueCode", issueCode);

		return this.sqlSessionTemplate.selectOne("selectGamePlanDetailsByPlanIdAndIssueCode", map);
	}

	@Override
	public int updateGamePlanDetailByPlanId(Long planId) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cancelTime", new Date());
		map.put("status", GamePlanDetailStatus.CANCEL.getValue());
		map.put("planid", planId);
		return this.sqlSessionTemplate.update("updateGamePlanDetailByPlanId", map);
	}

	@Override
	public List<GamePlanDetail> queryGamePlanDetailByIssueCode(Long issueCode, Long lotteryId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("issueCode", issueCode);
		map.put("lotteryId", lotteryId);

		return this.sqlSessionTemplate.selectList("queryGamePlanDetailByIssueCode", map);
	}

	@Override
	public List<GamePlanDetail> getGamePlanDetailByLotteryId(Long lotteryId) {
		return this.sqlSessionTemplate.selectList("getGamePlanDetailByLotteryId", lotteryId);
	}

	/**
	* @Title: selectOneIssueUndoGamePlanDetailsByLotteryIssue
	* @Description:
	* @param issueCode
	* @param lotteryId
	* @return 
	* @see com.winterframework.firefrog.game.dao.IGamePlanDetailDao#selectOneIssueUndoGamePlanDetailsByLotteryIssue(java.lang.Long, java.lang.Long) 
	*/
	@Override
	public List<GamePlanDetail> selectOneIssueUndoGamePlanDetailsByLotteryIssue(Long issueCode, Long lotteryId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("issueCode", issueCode);
		map.put("lotteryid", lotteryId);

		return this.sqlSessionTemplate.selectList("selectOneIssueUndoGamePlanDetailsByLotteryIssue", map);
	}

	@Override
	public void updateGamePlanDetailContinue(Long planId) {
		this.sqlSessionTemplate.update("updateGamePlanDetailContinue", planId);
	}

	@Override
	public void updateGamePlanDetailPause(Long planId) {
		this.sqlSessionTemplate.update("updateGamePlanDetailPause", planId);
	}

	@Override
	public void updateGamePlanDetailByPlanIdAfterIssue(Long planId, Long issueCode, int aimStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planId", planId);
		map.put("issueCode", issueCode);
		map.put("status", aimStatus);
		this.sqlSessionTemplate.update("updateGamePlanDetailByPlanIdAfterIssue", map);
	}

	@Override
	public List<GamePlanDetail> getGamePlanDetailByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.selectList(getQueryPath("getGamePlanDetailByLotteryIdAndIssueCode"), map);
	}
}
