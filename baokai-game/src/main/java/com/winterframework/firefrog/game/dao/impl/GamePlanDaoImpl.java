package com.winterframework.firefrog.game.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.SNUtil;
import com.winterframework.firefrog.game.dao.IGamePackageDao;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.vo.GamePackage;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.exception.GameOrderOrPlanCodeExistErrorException;
import com.winterframework.firefrog.game.web.dto.GamePlanQueryDTO;
import com.winterframework.firefrog.game.web.dto.GamePlanQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.util.RequestContext;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: GamePlanDaoImpl 
* @Description: 游戏追号DAO
* @author Denny 
* @date 2013-7-22 下午2:41:26 
*  
*/
@Repository("gamePlanDaoImpl")
public class GamePlanDaoImpl extends BaseIbatis3Dao<GamePlan> implements IGamePlanDao {

	@Resource(name = "SNUtil")
	private SNUtil snUtil;

	@Resource(name = "gamePackageDao")
	private IGamePackageDao gamePackageDao;

	@Override
	public GamePlan saveGamePlan(com.winterframework.firefrog.game.entity.GamePlan gamePlan) throws Exception {
		GamePlan vo = convertGamePlanEntityToVo(gamePlan);
		insert(vo);
		return vo;
	}

	/**
	 * 
	* @Title: convertGamePlanEntityToVo 
	* @Description: 转换GamePlanEntity 为Vo实体类
	* @param gamePlan
	* @return
	* @throws Exception
	 */
	private GamePlan convertGamePlanEntityToVo(com.winterframework.firefrog.game.entity.GamePlan gamePlan)
			throws Exception {

		GamePlan _gamePlan = new GamePlan();
		_gamePlan.setPlanUserId(gamePlan.getUser().getId());
		_gamePlan.setLotteryid(gamePlan.getLottery().getLotteryId());
		_gamePlan.setStartIsuueCode(gamePlan.getStartIsuueCode());
		_gamePlan.setFinishIssue(gamePlan.getFinishIssue());
		_gamePlan.setTotalIssue(gamePlan.getTotalIssue());
		_gamePlan.setStopMode(gamePlan.getStopMode().getValue());
		_gamePlan.setStopParms(gamePlan.getStopParms());
		_gamePlan.setOptionParms(gamePlan.getOptionParms());
		_gamePlan.setCreateTime(new Date());
		_gamePlan.setStartWebIssue(gamePlan.getStartWebIssue());
		_gamePlan.setPlanType(gamePlan.getGamePlanType().getValue());
		_gamePlan.setStatus(GamePlan.Status.WAITING.getValue());//为开始。
		_gamePlan.setUpdateTime(DateUtils.currentDate());
		//		_gamePlan.setCancelTime(null);
		String planCode = snUtil.createSN(SNUtil.TYPE_PLAN, gamePlan.getLottery().getLotteryId(),
				gamePlan.getStartWebIssue());

		//检查追号订单号是否已经存在
		List<GamePlan> gamePlanList = getGamePlanByPlanCode(planCode);
		if (gamePlanList != null && !gamePlanList.isEmpty()) {
			log.error("方案编号，订单编号或追号编号已经存在:" + planCode);
			throw new GameOrderOrPlanCodeExistErrorException();
		}

		_gamePlan.setPlanCode(planCode);
		_gamePlan.setPackageId(gamePlan.getPackageId());
		//		_gamePlan.setCancelIssue(null);
		_gamePlan.setSoldAmount(gamePlan.getSoldAmount());
		//		_gamePlan.setCanceledAmount(null);
		_gamePlan.setCancelModes(0);
		//		_gamePlan.setWinAmount(null);

		return _gamePlan;
	}

	public List<GamePlan> getGamePlanByPlanCode(String planCode) {
		return this.sqlSessionTemplate.selectList(getQueryPath("getGamePlanByPlanCode"), planCode);
	}

	@Override
	public Integer stopGamePlan(Long planId, Integer userType, Date cancelTime) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", planId);
		map.put("cancelTime", cancelTime);
		map.put("status", 3);//已终止
		map.put("cancelModes", userType);
		return this.sqlSessionTemplate.update("updateGamePlanStatus", map);
	}

	@Override
	public Page<com.winterframework.firefrog.game.entity.GamePlan> getPlans(PageRequest<GamePlanQueryDTO> pr) {

		GamePlanQueryDTO queryParam = pr.getSearchDo();
		Map<String, Object> queryParamMap = makeQueryParamMap(queryParam);

		Number totalCount = (Number) sqlSessionTemplate.selectOne("getPlansCount", queryParamMap);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<com.winterframework.firefrog.game.entity.GamePlan>(0);
		}

		Page<com.winterframework.firefrog.game.entity.GamePlan> page = new Page<com.winterframework.firefrog.game.entity.GamePlan>(
				pr.getPageNumber(), pr.getPageSize(), totalCount.intValue());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pr.getSortColumns());
		filters.putAll(queryParamMap);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<GamePlan> gps = sqlSessionTemplate.selectList("getPlans", filters, rowBounds);
		List<com.winterframework.firefrog.game.entity.GamePlan> planEntityList = new ArrayList<com.winterframework.firefrog.game.entity.GamePlan>();

		for (GamePlan gpv : gps) {
			com.winterframework.firefrog.game.entity.GamePlan planEntity = VOConverter.gamePlanV2GamePlanE(gpv);
			planEntityList.add(planEntity);
		}
		page.setResult(planEntityList);
		return page;
	}

	/** 
	* @Title: makeQueryParamMap 
	* @Description: 根据GamePlanQueryDTO 返回相应的Map信息
	* @param @param queryParam
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	private Map<String, Object> makeQueryParamMap(GamePlanQueryDTO queryParam) {
		Long userid = queryParam.getUserId();
		GamePlanQueryRequest gamePlanQueryRequest = queryParam.getQueryParam();

		Long lotteryid = gamePlanQueryRequest.getLotteryId();
		Integer status = gamePlanQueryRequest.getStatus();
		String issueCode = gamePlanQueryRequest.getWebIssueCode();
		Date recycleDate = gamePlanQueryRequest.getRecycleDate();

		Date startTime = null;
		if (gamePlanQueryRequest.getStartTime() != null) {
			startTime = new Date(gamePlanQueryRequest.getStartTime());
		}

		Date endTime = null;
		if (gamePlanQueryRequest.getEndTime() != null) {
			endTime = new Date(gamePlanQueryRequest.getEndTime());
		}
		String planCode = gamePlanQueryRequest.getPlanCode();

		Map<String, Object> map = new HashMap<String, Object>();
		if (userid != null) {
			map.put("userid", userid);
		}
		if (lotteryid != null) {
			map.put("lotteryid", lotteryid);
		}
		if (status != null) {
			map.put("status", status);
		}
		if (issueCode != null) {
			map.put("issueCode", issueCode);
		}
		if (startTime != null) {
			map.put("startTime", startTime);
		}
		if (endTime != null) {
			map.put("endTime", endTime);
		}
		if (planCode != null) {
			map.put("planCode", planCode);
		}
		if (recycleDate != null) {
			map.put("recycleDate", recycleDate);
		}

		return map;
	}

	@Override
	public com.winterframework.firefrog.game.entity.GamePlan getPlanById(Long planId) {
		//		GamePlan gamePlan = this.getById(planId);
		GamePlan gamePlan = this.sqlSessionTemplate.selectOne("getPlanById", planId);

		return VOConverter.gamePlanV2GamePlanE(gamePlan);
	}

	@Override
	public Long getPackageIdByPlanId(Long planId) {
		return this.sqlSessionTemplate.selectOne("getPackageIdByPlanId", planId);
	}

	@Override
	public void updateGamePlanSoldAmount(Long soldAmount, Long id) {

		GamePlan plan = new GamePlan();
		plan.setId(id);
		plan.setSoldAmount(soldAmount);

		this.update(plan);
	}

	@Override
	public com.winterframework.firefrog.game.entity.GamePlan getPlanByUserIdAndPlanId(Long planId, Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planId", planId);
		map.put("userId", userId);
		GamePlan gamePlan = this.sqlSessionTemplate.selectOne("getPlanByUserIdAndPlanId", map);
		if (gamePlan != null) {
			GamePackage pk = gamePackageDao.getById(gamePlan.getPackageId());
			return VOConverter.convertGamePlanAndPackage2Entity(gamePlan, pk);
		} else {
			return null;
		}
	}

	@Override
	public List<GamePlan> getPlanByLotteryIdAndIssueCode(Long lotteryId, Long issueCode, Long nextIssueCode) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		map.put("nextIssueCode", nextIssueCode);
		return this.sqlSessionTemplate.selectList("getPlanByLotteryIdAndIssueCode", map);
	}

	@Override
	public List<GamePlan> queryPlanByLotteryIdAndIssue(Long lotteryId, Long issueCode, Date date) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		if(null != date){
			map.put("date", date);
		}
		return this.sqlSessionTemplate.selectList("queryPlanByLotteryIdAndIssue", map);
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
	public GamePlan getPlanVoById(Long planId) {
		return this.sqlSessionTemplate.selectOne("getPlanById", planId);
	}

	@Override
	public Long getUndoPlansCountByUserId(Long userId) {
		return sqlSessionTemplate.selectOne("getUnDoPlansCountByUserId", userId);
	}
}
