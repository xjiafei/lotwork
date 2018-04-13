package com.winterframework.firefrog.game.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.game.dao.IGameDrawResultDao;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.CancelMode;
import com.winterframework.firefrog.game.entity.GameOrderOperationsEntity;
import com.winterframework.firefrog.game.entity.GameOrderUserBetInfoEntity;
import com.winterframework.firefrog.game.web.dto.GameLockDataQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderOperationsQueryDTO;
import com.winterframework.firefrog.game.web.dto.GameOrderOperationsQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryDTO;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryRequest;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 游戏投注订单DAO 
 * @author Denny 
 * @date 2013-7-22 下午2:03:14 
 */
@Repository("gameOrderDaoImpl")
public class GameOrderDaoImpl extends BaseIbatis3Dao<GameOrder> implements IGameOrderDao {
	private Logger log = LoggerFactory.getLogger(GameOrderDaoImpl.class);
	@Resource(name = "gameDrawResultDaoImpl")
	private IGameDrawResultDao gameDrawResultDaoImpl;

	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao customerDao;

	@Resource(name = "gamePlanDaoImpl")
	private IGamePlanDao gamePlanDaoImpl;
	
	@Override
	public Page<com.winterframework.firefrog.game.entity.GameOrder> getOrders(PageRequest<GameOrderQueryDTO> pr)
			throws Exception {
		GameOrderQueryDTO queryParam = pr.getSearchDo();
		Map<String, Object> queryParamMap = makeQueryParamMap(queryParam);

		Number totalCount = (Number) sqlSessionTemplate.selectOne("getOrdersNumber", queryParamMap);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<com.winterframework.firefrog.game.entity.GameOrder>(0);
		}

		Page<com.winterframework.firefrog.game.entity.GameOrder> page = new Page<com.winterframework.firefrog.game.entity.GameOrder>(
				pr.getPageNumber(), pr.getPageSize(), totalCount.intValue());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pr.getSortColumns());
		filters.putAll(queryParamMap);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<GameOrder> gos = sqlSessionTemplate.selectList("getOrders", filters, rowBounds);
		List<com.winterframework.firefrog.game.entity.GameOrder> orderEntityList = new ArrayList<com.winterframework.firefrog.game.entity.GameOrder>();

		for (GameOrder go : gos) {
			com.winterframework.firefrog.game.entity.GameOrder orderEntity = VOConverter.gov2goe(go);
			User user = customerDao.queryUserById(go.getUserid());
			if (user != null && user.getUserProfile() != null) {
				orderEntity.getGamePackage().getUser().setUserProfile(user.getUserProfile());
			}
			orderEntityList.add(orderEntity);
		}
		page.setResult(orderEntityList);
		return page;
	}

	/**
	 * 生成查询参数map 
	 * @param queryParam
	 * @return
	 */
	private Map<String, Object> makeQueryParamMap(GameOrderQueryDTO queryParam) {
		Long userid = queryParam.getUserId();
		GameOrderQueryRequest gameOrderQueryRequest = queryParam.getQueryParam();
		Long lotteryid = gameOrderQueryRequest.getLotteryId();
		String orderCode = gameOrderQueryRequest.getOrderCode();
		Integer status = gameOrderQueryRequest.getStatus();
		String issueCode = gameOrderQueryRequest.getWebIssueCode();
		Date startTime = null, endTime = null;
		if (gameOrderQueryRequest.getStartTime() != null) {
			startTime = new Date(gameOrderQueryRequest.getStartTime());
		}
		if (gameOrderQueryRequest.getEndTime() != null) {
			endTime = new Date(gameOrderQueryRequest.getEndTime());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (userid != null) {
			map.put("userid", userid);
		}
		if (lotteryid != null) {
			map.put("lotteryid", lotteryid);
		}
		if (orderCode != null) {
			map.put("orderCode", orderCode);
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
		if (queryParam.getQueryParam().getParentType() != null) {
			map.put("parentType", queryParam.getQueryParam().getParentType());
		}
		if (queryParam.getQueryParam().getContainSub() != null) {
			map.put("containSub", queryParam.getQueryParam().getContainSub());
		}
		if (queryParam.getQueryParam().getAccount() != null) {
			map.put("account", queryParam.getQueryParam().getAccount());
		}
		//检查回收用户
		if (queryParam.getQueryParam().getRecycleDate() != null) {
			map.put("recycleDate", queryParam.getQueryParam().getRecycleDate());
		}
		return map;
	}

	@Override
	public Long saveGameOrder(GameOrder gameOrder) {
		log.info("开始生成订单信息。");
		insert(gameOrder);
		return gameOrder.getId();
	}

	@Override
	public Long saveMMCGameOrder(GameOrder gameOrder) {
		log.info("开始生成订单信息。");
		insert(gameOrder);
		gameOrder.setIssueCode(gameOrder.getId());
		update(gameOrder);
		return gameOrder.getId();
	}

	@Override
	public List<GameOrder> getAllByEntity(GameOrder entity) {
		return super.getAllByEntity(entity);
	}

	/**
	 * 依據 orderId 取得訂單Entity物件，一併產生其內的 GameIssue、GamePackage Entity 物件；及取得開獎號碼。
	 * @throws Exception 查無用戶資料時拋出錯誤
	 */
	@Override
	public com.winterframework.firefrog.game.entity.GameOrder getOrderById(long orderId) throws Exception {
		GameOrder gov = this.getById(orderId);
		com.winterframework.firefrog.game.entity.GameOrder goe = VOConverter.gov2goe(gov);
		try {
			User user = customerDao.queryUserById(gov.getUserid());
			goe.getGamePackage().setUser(user);
		} catch (Exception e) {
			log.error("订单查询时，获取用户信息出错", e);
			throw e;
		}
		long lotteryId = goe.getLottery().getLotteryId();
		long issueCode = goe.getGameIssue().getIssueCode().longValue();
		String numberRecord = gameDrawResultDaoImpl.getNumberRecordByLotteryIdAndIssueCode(lotteryId, issueCode);
		goe.setNumberRecord(numberRecord);
		return goe;
	}

	@Override
	public com.winterframework.firefrog.game.entity.GameOrder getOrderByOrderIdAndUserId(long orderId, Long userId)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("userId", userId);
		GameOrder gov = this.sqlSessionTemplate.selectOne("getOrderByOrderIdAndUserId", map);
		com.winterframework.firefrog.game.entity.GameOrder goe = new com.winterframework.firefrog.game.entity.GameOrder();
		if (gov != null) {
			goe = VOConverter.gov2goe(gov);
			try {
				User user = customerDao.queryUserById(gov.getUserid());
				goe.getGamePackage().setUser(user);
			} catch (Exception e) {
				log.error("订单查询时，获取用户信息出错", e);
				throw e;
			}
		}
		return goe;
	}

	@Override
	public List<com.winterframework.firefrog.game.entity.GameOrder> getOrdersByPlanId(long planid) {

		List<com.winterframework.firefrog.game.entity.GameOrder> goes = new ArrayList<com.winterframework.firefrog.game.entity.GameOrder>();
		List<GameOrder> govs = sqlSessionTemplate.selectList("getOrdersByPlanId", planid);
		for (GameOrder gov : govs) {
			goes.add(VOConverter.gov2goe(gov));
		}
		return goes;
	}

	@Override
	public void updateOrder(Long orderId, Long issueCode, Long cancelTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cancelTime", DataConverterUtil.convertLong2Date(cancelTime));
		map.put("status", com.winterframework.firefrog.game.entity.GameOrder.STATUS_CANCEL);
		map.put("orderId", orderId);
		map.put("issueCode", issueCode);
		this.sqlSessionTemplate.update("updateGameOrder", map);

	}

	@Override
	public int updateOrderCancel(Long orderId, Long issueCode, Long cancelTime, boolean isFrontUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", com.winterframework.firefrog.game.entity.GameOrder.STATUS_CANCEL);
		//解决0005071: 【中三玩法】后台/游戏中心/方案记录 -- “管理员撤销”保存为“用户撤销”类型 问题。
		map.put("cancelModes", isFrontUser == true ? CancelMode.USER.getValue() : CancelMode.SYSTEM.getValue());
		map.put("cancelTime", DataConverterUtil.convertLong2Date(cancelTime));
		map.put("orderId", orderId);
		map.put("issueCode", issueCode);
		map.put("cancelStatus", GameOrder.Status.CANCEL.getValue());
		return this.sqlSessionTemplate.update("updateGameOrderNoStatus", map);
	}

	@Override
	public Page<GameOrderOperationsEntity> getOrderOperations(PageRequest<GameOrderOperationsQueryDTO> pr)
			throws Exception {
		GameOrderOperationsQueryDTO queryParam = pr.getSearchDo();
		Map<String, Object> queryParamMap = makeOrderOperationsQueryMap(queryParam);

		Number totalCount = (Number) sqlSessionTemplate.selectOne("getOrderOperationsNumber", queryParamMap);
		log.info("------------------totalCount : " + totalCount.intValue());
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<GameOrderOperationsEntity>(0);
		}

		Page<GameOrderOperationsEntity> page = new Page<GameOrderOperationsEntity>(pr.getPageNumber(),
				pr.getPageSize(), totalCount.intValue());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pr.getSortColumns());
		filters.putAll(queryParamMap);
		log.info("------------------filter : " + filters.toString());
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<GameOrderOperationsEntity> orderEntityList = sqlSessionTemplate.selectList("getOrderOperations", filters,
				rowBounds);

		for (GameOrderOperationsEntity gameOrderOperationsEntity : orderEntityList) {
			if (gamePlanDaoImpl.getGamePlanByOrderId(gameOrderOperationsEntity.getOrderId()) != null) {
				gameOrderOperationsEntity.setParentType(2);
			} else {
				gameOrderOperationsEntity.setParentType(1);
			}
		}

		page.setResult(orderEntityList);
		return page;
	}

	@Override
	public List<GameOrderOperationsEntity> getOrderOperationsList(GameOrderOperationsQueryDTO queryDTO)
			throws Exception {
		Map<String, Object> queryParamMap = makeOrderOperationsQueryMap(queryDTO);
		queryParamMap.put("sortColumns", "saleTime desc");
		List<GameOrderOperationsEntity> orderEntityList = sqlSessionTemplate.selectList("getOrderOperations",
				queryParamMap);
		return orderEntityList;
	}

	private Map<String, Object> makeOrderOperationsQueryMap(GameOrderOperationsQueryDTO queryParam) {
		Map<String, Object> map = new HashMap<String, Object>();
		Long userid = queryParam.getUserid();
		log.info("makeOrderOperationsQueryMap :" + queryParam.getQueryRequest().getDevice());
		GameOrderOperationsQueryRequest request = queryParam.getQueryRequest();
		Long lotteryid = request.getLotteryid();

		Date startTime = null, endTime = null;
		if (null != request.getStartCreateTime()) {
			startTime = new Date(request.getStartCreateTime());
		}
		if (null != request.getEndCreateTime()) {
			endTime = new Date(request.getEndCreateTime());
		}

		String paramCode = request.getParamCode();
		Integer status = request.getStatus();
		Long startWinsCount = request.getStartWinsCount();
		Long endWinsCount = request.getEndWinsCount();
		Integer containSub = request.getContainSub();
		map.put("device", queryParam.getQueryRequest().getDevice());
		if (null != request.getIssueCode() && request.getIssueCode() > 0L) {
			map.put("issueCode", request.getIssueCode());
		}

		if (null != userid) {
			map.put("userid", userid);
		}
		if (null != lotteryid && lotteryid != 0) {
			map.put("lotteryid", lotteryid);
		}
		//若有傳獎期搜尋資料，就不用過濾 startTime 及 endTime
		if (null != startTime &&(null == request.getIssueCode())) {
			map.put("startTime", startTime);
		}
		if (null != endTime &&(null == request.getIssueCode())) {
			map.put("endTime", endTime);
		}
		if (null != paramCode) {
			paramCode = paramCode.trim();
		}
		//此处还得根据订单编码规则区分出account或orderCode
		if (null != paramCode) {
			if(String.valueOf(paramCode.charAt(0)).equals("D")){
				map.put("orderCode", paramCode);
			}else{
				map.put("account", paramCode);
			}
		}
		if (null != status) {
			if (status == 41) {
				map.put("status", 4);//撤销
				map.put("cancelModes", new Long(1));//用户
			} else if (status == 42) {
				map.put("status", 4);//撤销
				map.put("cancelModes", new Long(2));//系统
			} else if (status.intValue() == 3) {
				map.put("auditNoPass", 3);//不中奖
				map.put("status", 3);//其他类型
			} else if (status.intValue() == 10) {
				map.put("auditNoPass", 10);//审核未通过
				map.put("status", 3);//其他类型
			} else {
				map.put("status", status);//其他类型
			}
		}
		if (null != startWinsCount) {
			map.put("startWinsCount", startWinsCount);
		}
		if (null != endWinsCount) {
			map.put("endWinsCount", endWinsCount);
		}
		if (null != containSub) {
			map.put("containSub", containSub);
		} else {
			map.put("containSub", 0);
		}

		return map;
	}

	@Override
	public GameOrderOperationsEntity getOrderOperationsDetail(Long orderid) throws Exception {
		GameOrderOperationsEntity entity = this.sqlSessionTemplate.selectOne("queryOrderOperationsDetailbyOrderID",
				orderid);
		return entity;
	}

	@Override
	public List<GameOrderOperationsEntity> queryOrderOperationsListByPlanID(Long planid) throws Exception {
		List<GameOrderOperationsEntity> orderLists = this.sqlSessionTemplate.selectList(
				"queryOrderOperationsListByPlanID", planid);
		return orderLists;
	}

	@Override
	public List<com.winterframework.firefrog.game.entity.GameOrder> queryOrderByLotteryIdAndIssueCode(Long lotteryId,
			Long issueCode) throws Exception {

		List<com.winterframework.firefrog.game.entity.GameOrder> goes = new ArrayList<com.winterframework.firefrog.game.entity.GameOrder>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);

		List<GameOrder> govs = sqlSessionTemplate.selectList("queryOrderByLotteryIdAndIssueCode", map);
		for (GameOrder gov : govs) {
			goes.add(VOConverter.gov2goe(gov));
		}
		return goes;
	}

	@Override
	public GameOrder getOrderByPlanIdAndIssueCode(Long planId, Long nextIssueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planId", planId);
		map.put("issueCode", nextIssueCode);
		return this.sqlSessionTemplate.selectOne("getOrderByPlanIdAndIssueCode", map);
	}

	@Override
	public List<GameOrder> getGameOrderByOrderCode(String orderCode) {
		return this.sqlSessionTemplate.selectList(getQueryPath("getGameOrderByOrderCode"), orderCode);
	}

	@Override
	public Long getPlanIdByOrder(Long orderId) throws Exception {
		return this.sqlSessionTemplate.selectOne("getPlanIdByOrder", orderId);
	}

	@Override
	public Integer getCurrentIssueMutiple(String betDetail, String betTypeCode, long userId, long lotteryId,
			Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		map.put("betTypeCode", betTypeCode);
		map.put("userId", userId);
		map.put("betDetail", betDetail);
		return this.sqlSessionTemplate.selectOne(getQueryPath("getCurrentIssueMutiple"), map);
	}

	@Override
	public List<GameOrderUserBetInfoEntity> queryUserBetInfoByDate(Long userId, Date startDate) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("startDate", startDate);
		return this.sqlSessionTemplate.selectList(getQueryPath("queryUserBetInfoByDate"), map);
	}

	@Override
	public List<String> queryBeginMissionOrder(Map<String,Object> params) {
		return this.sqlSessionTemplate.selectList(getQueryPath("queryBeginMissionOrder"), params);
	}
	
	@Override
	public Long queryUserDailyBets(Long userId,List<String> betTypeCodes) {
		Map<String, Object> map = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		map.put("userId", userId);
		map.put("betTypeCodes", betTypeCodes);
		map.put("beginTime", new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(now));
		map.put("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
		log.info("-----map : " + map.toString());
		return this.sqlSessionTemplate.selectOne(getQueryPath("queryUserDailyBets"), map);
	}
	
	public Long queryUserDailyPeriodBets(Long userId,Date beginTime,Date endTime, List<String> betTypeCodes){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("betTypeCodes", betTypeCodes);
		map.put("beginTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(beginTime));
		map.put("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime));
		log.info("-----map : " + map.toString());
		return this.sqlSessionTemplate.selectOne(getQueryPath("queryUserDailyPeriodBets"), map);
	}
	
	@Override
	public void updateEndCanCancelTime(Long lotteryId,Long issueCode, Date newSaleEndTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lotteryId", lotteryId);
		params.put("issueCode", issueCode);
		params.put("newSaleEndTime", newSaleEndTime);
		this.sqlSessionTemplate.selectList(getQueryPath("updateEndCanCancelTime"), params);
	}
	@Override
	public int updateMMC(GameOrder order) {
		return this.sqlSessionTemplate.update(getQueryPath("updateMMC"), order);
	}	

	@Override
	public GameOrder getOrderByPlanDetailId(Long planDetailId) {
		return this.sqlSessionTemplate.selectOne("getOrderByPlanDetailId", planDetailId);
	}
	
	@Override
	public GameOrder getWinningList(Long lotteryId,Long issueCode,Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		map.put("userId", userId);
		return this.sqlSessionTemplate.selectOne("getWinningList", map);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<GameOrder> getOrderList(Long lotteryId, Long issueCode,
			Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		map.put("count", 7);
		map.put("userId", userId);
		List<GameOrder> queryList =  this.sqlSessionTemplate.selectList("getMaxOrderList", map);
		if(queryList == null || queryList.size()<7){
			if(queryList == null || queryList.size()==0){
				map.put("count", 7);
				queryList = new ArrayList<GameOrder>();
			}else{
				map.put("count", 7-queryList.size());
				List<String> notContain = new ArrayList<String>();
				for(GameOrder result:queryList){
					notContain.add(result.getNickName());
				}
				map.put("list", notContain);
				
			}
			
			List<GameOrder> queryLastList = this.sqlSessionTemplate.selectList("getLastMaxOrderList", map);
			if(queryLastList != null && queryLastList.size() >0){
				queryList.addAll(queryLastList);
				Collections.sort(queryList, new Comparator(){

					@Override
					public int compare(Object o1, Object o2) {
						GameOrder g1=(GameOrder)o1;
						GameOrder g2=(GameOrder)o2;
						return g2.getTotamount().compareTo(g1.getTotamount());
					}
					
				});
			}
		}
		return queryList;
	}

	@Override
	public List<com.winterframework.firefrog.game.entity.GameOrder> queryPlayerBet(
			long lotteryId, Long userId, Long issueCode) throws Exception {
		List<com.winterframework.firefrog.game.entity.GameOrder> goes = new ArrayList<com.winterframework.firefrog.game.entity.GameOrder>();
		List<GameOrder> orderList = this.getOrderList(lotteryId, issueCode, userId);
		for(GameOrder gameOrder:orderList){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("lotteryId", lotteryId);
			map.put("issueCode", gameOrder.getIssueCode());
			map.put("userId", userId);
			map.put("TOTAMOUNT", gameOrder.getTotamount());
			GameOrder order = this.sqlSessionTemplate.selectOne(this.getQueryPath("queryPlayerBet"), map);
			com.winterframework.firefrog.game.entity.GameOrder gameOrderE = VOConverter.gov2goe(order);
			gameOrderE.setHeadImg(gameOrder.getHeadImg());
			gameOrderE.setNickName(gameOrder.getNickName());
			goes.add(gameOrderE);
		}
		return goes;
	}
	
	@Override
	public Long getTotamount(GameLockDataQueryRequest lockdata) throws Exception {
		return this.sqlSessionTemplate.selectOne("getTotamount", lockdata);
	}
	@Override
	public Double getWinAmt(Long lotteryId, Long issueCode, Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		map.put("userId", userId);
		return  this.sqlSessionTemplate.selectOne(this.getQueryPath("getWinAmt"), map);
	}
}
