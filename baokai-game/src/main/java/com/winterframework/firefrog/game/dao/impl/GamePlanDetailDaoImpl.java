package com.winterframework.firefrog.game.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGamePackageDao;
import com.winterframework.firefrog.game.dao.IGamePackageItemDao;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.IGamePlanDetailDao;
import com.winterframework.firefrog.game.dao.IGamePointDao;
import com.winterframework.firefrog.game.dao.IGameReturnPointDao;
import com.winterframework.firefrog.game.dao.IGameUserAwardGroupDao;
import com.winterframework.firefrog.game.dao.vo.GamePoint;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GamePackage;
import com.winterframework.firefrog.game.entity.GamePackageItem;
import com.winterframework.firefrog.game.entity.GamePlan;
import com.winterframework.firefrog.game.entity.GamePlanDetail;
import com.winterframework.firefrog.game.entity.LockPoint;
import com.winterframework.firefrog.game.entity.Points;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gamePlanDetailDaoImpl")
public class GamePlanDetailDaoImpl extends BaseIbatis3Dao<com.winterframework.firefrog.game.dao.vo.GamePlanDetail>
		implements IGamePlanDetailDao {

	@Resource(name = "gamePackageDao")
	private IGamePackageDao gamePackageDao;

	@Resource(name = "gamePackageItemDao")
	private IGamePackageItemDao gamePackageItemDao;

	@Resource(name = "gamePlanDaoImpl")
	private IGamePlanDao gamePlanDao;

	@Resource(name = "gameUserAwardGroupDaoImpl")
	private IGameUserAwardGroupDao gameUserAwardGroupDao;

	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;

	@Resource(name = "gameReturnPointDaoImpl")
	private IGameReturnPointDao gameReturnPointDao;

	@Resource(name = "gamePointDaoImpl")
	private IGamePointDao gamePointDaoImpl;

	@Resource(name = "gameIssueDaoImpl")
	private IGameIssueDao gameIssueDao;

	@Override
	@Deprecated
	public void saveGamePlanDetail(GamePlanDetail detail, Long gamePlanId, Long packageAmount) throws Exception {

		insert(convertGamePlanDetailEntityToVo(detail, gamePlanId, packageAmount, 0));
	}

	@Override
	public Long saveGamePlanDetailList(List<GamePlanDetail> details, GamePackage pkg, GamePlan gamePlan,
			Long packageAmount, Long currentIssueCode) throws Exception {
		List<com.winterframework.firefrog.game.dao.vo.GamePlanDetail> list = new ArrayList<com.winterframework.firefrog.game.dao.vo.GamePlanDetail>();

		Long gamePlanDetailId = null;
		for (GamePlanDetail detail : details) {
			com.winterframework.firefrog.game.dao.vo.GamePlanDetail vo = null;
			if (detail.getGameIssue().getIssueCode().longValue() == currentIssueCode.longValue()) {
				vo = convertGamePlanDetailEntityToVo(detail, gamePlan.getId(), packageAmount, 1); // 已执行
				this.insert(vo);
				gamePlanDetailId = vo.getId();
			} else {
				vo = convertGamePlanDetailEntityToVo(detail, gamePlan.getId(), detail.getTotamount().longValue(), 0); // 默认
				this.insert(vo);
			}

			// 保存变价列表
			if (detail.getLockPoint() != null) {
				Map<String, Long> itemsMap = new HashMap<String, Long>();
				for (GamePackageItem itme : pkg.getItemList()) {
					itemsMap.put(
							org.apache.commons.codec.digest.DigestUtils.md5Hex(itme.getGameBetType().getBetTypeCode()
									+ itme.getBetDetail()+itme.getMoneyMode().getValue()), itme.getId());
				}

				for (LockPoint lp : detail.getLockPoint()) {
					List<Points> pointList = lp.getPoints();
					if (pointList != null && !pointList.isEmpty()) {
						List<GamePoint> voList = VOConverter.convertGamePointToPlanVo(pointList,
								itemsMap.get(lp.getBetDetail()), vo.getId());
						gamePointDaoImpl.saveGamePoints(voList);
					}
				}
			}
		}
		return gamePlanDetailId;
		// insert(list);
	}

	private com.winterframework.firefrog.game.dao.vo.GamePlanDetail convertGamePlanDetailEntityToVo(
			GamePlanDetail detail, Long gamePlanId, Long packageAmount, Integer status) throws Exception {

		com.winterframework.firefrog.game.dao.vo.GamePlanDetail _detail = new com.winterframework.firefrog.game.dao.vo.GamePlanDetail();
		_detail.setPlanid(gamePlanId);
		_detail.setIssueCode(detail.getGameIssue().getIssueCode());
		_detail.setMutiple(detail.getMutiple());
		_detail.setTotamount(detail.getTotamount().longValue());
		_detail.setStatus(status);
		_detail.setCreateTime(new Date());
		_detail.setCancelFee(detail.getCancelFee());
		// 这几个个属性暂时不设值
		// _detail.setCancelTime(null);
		if (status.intValue() == 1) {
			_detail.setRumTime(new Date());
		}
		// _detail.setWebIssueCode(null);

		return _detail;
	}

	@Override
	public com.winterframework.firefrog.game.dao.vo.GamePlanDetail getGamePlanDetailByPlanIdAndIssueCode(Long planId,
			Long issueCode) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planid", planId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.selectOne("getGamePlanDetailByPlanIdAndIssueCode", map);
	}

	@Override
	public int updateGamePlanDetailByPlanIdAndIssueCode(Long planId, Long issueCode, String cancelUser)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cancelTime", new Date());
		map.put("status", GamePlanDetail.GamePlanDetailStatus.CANCEL.getValue());
		map.put("planid", planId);
		map.put("issueCode", issueCode);
		map.put("cancelUser", cancelUser);
		return this.sqlSessionTemplate.update("updateGamePlanDetailByPlanIdAndIssueCode", map);
	}

	@Override
	public int updateReservationCalledGamePlanDetailByPlanIdAndIssueCode(Long planId, Long issueCode, String cancelUser)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cancelTime", new Date());
		map.put("status", GamePlanDetail.GamePlanDetailStatus.CANCEL.getValue());
		map.put("planid", planId);
		map.put("issueCode", issueCode);
		map.put("cancelUser", cancelUser);
		return this.sqlSessionTemplate.update("updateGamePlanDetailByPlanIdCancel", map);
	}
	
	@Override
	public int updateGamePlanDetailByPlanIdAndIssueCodeForOrderCancel(Long planId, Long issueCode, String cancelUser)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cancelTime", new Date());
		map.put("status", GamePlanDetail.GamePlanDetailStatus.CANCEL.getValue());
		map.put("planid", planId);
		map.put("issueCode", issueCode);
		map.put("cancelUser", cancelUser);
		return this.sqlSessionTemplate.update("updateGamePlanDetailByPlanIdCancelForOrderCancel", map);
	}
	
	public int updateReservationCalledGamePlanDetailStatusByPlanIdAndIssueCode(Long planId, List<String> issueCode, String cancelUser)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", GamePlanDetail.GamePlanDetailStatus.WAIT_CANCEL.getValue());
		map.put("planid", planId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.update("updateGamePlanDetailStatusByPlanIdAndIssueCode", map);
	}
	
	public int updateOrderReservationCalledGamePlanDetailStatusByPlanIdAndIssueCode(Long planId, List<String> issueCode, String cancelUser)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", GamePlanDetail.GamePlanDetailStatus.WAIT_CANCEL.getValue());
		map.put("planid", planId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.update("updateGamePlanDetailStatusByPlanIdAndIssueCodeForOrderCancel", map);
	}

	@Override
	public int updateGamePlanDetailByPlanId(Long planId, String cancelUser) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cancelTime", new Date());
		map.put("status", GamePlanDetail.GamePlanDetailStatus.CANCEL.getValue());
		map.put("planid", planId);
		map.put("cancelUser", cancelUser);
		return this.sqlSessionTemplate.update("updateGamePlanDetailByPlanId", map);
	}
	@Override
	public int updateGamePlanDetailByPlanIdDate(Long planId, String cancelUser,Date date) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cancelTime", new Date());
		map.put("status", GamePlanDetail.GamePlanDetailStatus.CANCEL.getValue());
		map.put("planid", planId);
		map.put("cancelUser", cancelUser);
		map.put("date", date);
		return this.sqlSessionTemplate.update("updateGamePlanDetailByPlanIdDate", map);
	}

	@Override
	public List<GamePlanDetail> getPlanDetailsByPlanId(long planId) {
		List<com.winterframework.firefrog.game.dao.vo.GamePlanDetail> gpdvs = this.sqlSessionTemplate.selectList(
				"getPlanDetailsByPlanId", planId);
		com.winterframework.firefrog.game.dao.vo.GamePlan plan = gamePlanDao.getById(planId);

		List<GamePlanDetail> gpdes = new ArrayList<GamePlanDetail>();
		for (com.winterframework.firefrog.game.dao.vo.GamePlanDetail gpdv : gpdvs) {
			GamePlanDetail detail = VOConverter.gpdv2gpde(gpdv);
			try {
				GameIssueEntity gameIssueEntity = gameIssueDao.queryGameIssue(plan.getLotteryid(), detail
						.getGameIssue().getIssueCode());
				detail.getGameIssue().setSaleEndTime(gameIssueEntity.getSaleEndTime());
				detail.getGameIssue().setNumberRecord(gameIssueEntity.getNumberRecord());
			} catch (Exception e) {
				e.printStackTrace();
			}
			gpdes.add(detail);
		}
		return gpdes;
	}

	@Override
	public List<GamePlanDetail> getUnExeuPlanDetailsByPlanId(Long planId) {
		List<com.winterframework.firefrog.game.dao.vo.GamePlanDetail> gpdvs = this.sqlSessionTemplate.selectList(
				"getUnExeuPlanDetailsByPlanId", planId);

		List<GamePlanDetail> gpdes = new ArrayList<GamePlanDetail>();
		for (com.winterframework.firefrog.game.dao.vo.GamePlanDetail gpdv : gpdvs) {
			gpdes.add(VOConverter.gpdv2gpde(gpdv));
		}
		return gpdes;
	}
	@Override
	public List<GamePlanDetail> getUnExeuPlanDetailsByPlanIdDate(Long planId,Date date) {
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("planId", planId);
		filters.put("date", date);
		List<com.winterframework.firefrog.game.dao.vo.GamePlanDetail> gpdvs = this.sqlSessionTemplate.selectList(
				"getUnExeuPlanDetailsByPlanIdDate", filters);

		List<GamePlanDetail> gpdes = new ArrayList<GamePlanDetail>();
		for (com.winterframework.firefrog.game.dao.vo.GamePlanDetail gpdv : gpdvs) {
			gpdes.add(VOConverter.gpdv2gpde(gpdv));
		}
		return gpdes;
	}
	@Override
	@Deprecated
	public Long getUnExeuAndIssueBeforeCurrentPlanDetailsCountByPlanId(Long planId){
		Number totalCount = (Number)this.sqlSessionTemplate.selectOne("getUnExeuAndIssueBeforeCurrentPlanDetailsCountByPlanId", planId);
		return totalCount==null?0l:totalCount.longValue();
	}

	@Override
	public Date getLastPlanCanStopTime(Long planId) {
		return this.sqlSessionTemplate.selectOne("getLastPlanCanStopTime", planId);
	}

	@Override
	public com.winterframework.firefrog.game.dao.vo.GamePlanDetail queryGamePlanDetailByPlanID(Long planId,
			Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planId", planId);
		map.put("issueCode", issueCode);

		return this.sqlSessionTemplate.selectOne("queryGamePlanDetailByPlanID", map);
	}
	
	@Override
	public List<GamePlanDetail> queryGamePlanDetailByNoExecute(Long planId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planId", planId);

		List<com.winterframework.firefrog.game.dao.vo.GamePlanDetail> gpdvs = this.sqlSessionTemplate.selectList(
				"queryGamePlanDetailByNoExecute", map);

		List<GamePlanDetail> gpdes = new ArrayList<GamePlanDetail>();
		for (com.winterframework.firefrog.game.dao.vo.GamePlanDetail gpdv : gpdvs) {
			gpdes.add(VOConverter.gpdv2gpde(gpdv));
		}
		
		return gpdes;
	}

	@Override
	public com.winterframework.firefrog.game.dao.vo.GamePlanDetail queryGamePlanDetailByPlanIDForUndo(Long planId,
			Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planId", planId);
		map.put("issueCode", issueCode);

		return this.sqlSessionTemplate.selectOne("queryGamePlanDetailByPlanIDForUndo", map);
	}

	/**
	 * 根据追号Id和期号更新追号明细状态 Title: updateGamePlanDetailByPlanID Description:
	 * 
	 * @param id
	 * @param issueCode
	 * @param status
	 * @return
	 * @throws Exception
	 * @see com.winterframework.firefrog.game.dao.IGamePlanDetailDao#updateGamePlanDetailByPlanID(java.lang.Long,
	 *      java.lang.Long, java.lang.Integer)
	 */
	@Override
	public void updateGamePlanDetailByPlanID(Long id, Long issueCode, Integer status) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planId", id);
		map.put("issueCode", issueCode);
		map.put("status", status);
		map.put("runTime", new Date());

		this.sqlSessionTemplate.update("updateGamePlanDetailStatusByPlanID", map);
	}

	@Override
	public List<com.winterframework.firefrog.game.dao.vo.GamePlanDetail> queryGamePlanDetailByIssueCode(Long issueCode,
			Long lotteryId) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("issueCode", issueCode);
		map.put("lotteryId", lotteryId);

		return this.sqlSessionTemplate.selectList("queryGamePlanDetailByIssueCode", map);
	}

	@Override
	public List<Long> getPausePlanIdByIssueCode(Long issueCode) {
		return this.sqlSessionTemplate.selectList("getPausePlanIdByIssueCode", issueCode);
	}

	@Override
	public List<com.winterframework.firefrog.game.dao.vo.GamePlanDetail> getGamePlanDetailsByPlanId(Long planId) {
		return this.sqlSessionTemplate.selectList(getQueryPath("getPlanDetailsByPlanId"), planId);
	}
	
	@Override
	public List<com.winterframework.firefrog.game.dao.vo.GamePlanDetail> selectGamePlanDetailsByPlanId(Long planId) {
		return this.sqlSessionTemplate.selectList(getQueryPath("getPlanDetailsByPlanId"), planId);
	}
	
	public int updateReservationCalledErrorStatusByPlanIdAndIssueCode(Long planId, Long issueCode, Long cancelUser)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", GamePlanDetail.GamePlanDetailStatus.CANCEL_ERROR.getValue());
		map.put("planid", planId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.update("updateReservationCalledErrorStatusByPlanIdAndIssueCode", map);
	}

}
