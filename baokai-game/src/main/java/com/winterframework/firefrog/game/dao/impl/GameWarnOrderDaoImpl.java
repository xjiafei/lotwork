package com.winterframework.firefrog.game.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameWarnOrderDao;
import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;
import com.winterframework.firefrog.game.dao.vo.RiskOrderDetail;
import com.winterframework.firefrog.game.dao.vo.RiskOrders;
import com.winterframework.firefrog.game.dao.vo.SpiteOrders;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.GameSpiteOperationsEntity;
import com.winterframework.firefrog.game.entity.GameWarnOrderEntity;
import com.winterframework.firefrog.game.web.dto.GameSpiteOrderQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameSpiteOrderQueryRequestDTO;
import com.winterframework.firefrog.game.web.dto.GameWarnDetailQueryDto;
import com.winterframework.firefrog.game.web.dto.GameWarnOrderQueryDTO;
import com.winterframework.firefrog.game.web.dto.GameWarnOrderQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameWarnOrderDaoImpl")
public class GameWarnOrderDaoImpl extends BaseIbatis3Dao<GameWarnOrder> implements IGameWarnOrderDao {

	@Override
	public Page<RiskOrders> queryGameWarnOrderByLotteryIdAndIssueCode(PageRequest<GameWarnDetailQueryDto> pageRequest)
			throws Exception {

		GameWarnDetailQueryDto dto = pageRequest.getSearchDo();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", dto.getLotteryId());
		map.put("issueCode", dto.getIssueCode());

		Number totalCount = (Number) sqlSessionTemplate
				.selectOne("queryGameWarnOrderByLotteryIdAndIssueCodeCount", map);
		if (null == totalCount || totalCount.intValue() <= 0) {
			return new Page<RiskOrders>(0);
		}

		Page<RiskOrders> page = new Page<RiskOrders>(pageRequest.getPageNumber(), pageRequest.getPageSize(),
				totalCount.intValue());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<RiskOrders> riskOrdersList = sqlSessionTemplate.selectList("queryGameWarnOrderByLotteryIdAndIssueCode",
				filters, rowBounds);

		page.setResult(riskOrdersList);

		return page;
	}

	public List<RiskOrderDetail> queryGameWarnOrderByLotteryIdIssueCodeUserId(Long lotteryId, Long issueCode,
			Long userid) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		map.put("userId", userid);

		return sqlSessionTemplate.selectList("queryGameWarnOrderDetailByLotteryIdAndIssueCode", map);
	}

	@Override
	public List<SpiteOrders> querySpiteOrderByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		return sqlSessionTemplate.selectList("querySpiteOrderByLotteryIdAndIssueCode", map);
	}

	@Override
	public List<GameWarnOrderEntity> queryGameWarnOrderDetail(Long lotteryId, Long issueCode) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);

		List<GameWarnOrder> list = sqlSessionTemplate.selectList("queryGameWarnOrderDetail", map);

		List<GameWarnOrderEntity> entities = new ArrayList<GameWarnOrderEntity>();
		if (null != list && list.size() > 0) {

			for (GameWarnOrder order : list) {

				entities.add(VOConverter.convertGameWarnOrder2Entity(order));
			}

		}
		return entities;
	}

	@Override
	public List<GameWarnOrder> getUndealGameWarnOrderByPlanId(Long planId) throws Exception {

		return sqlSessionTemplate.selectList("getUndealGameWarnOrderByPlanId", planId);
	}

	@Override
	public Page<RiskOrders> queryGameWarnOrderByPage(PageRequest<GameWarnOrderQueryDTO> pr) throws Exception {
		GameWarnOrderQueryDTO queryDTO = pr.getSearchDo();
		Map<String, Object> queryParamMap1 = makeQueryMapForQueryGameWarnOrders1(queryDTO);

		Number totalCount = (Number) sqlSessionTemplate
				.selectOne("queryGameWarnOrderByConditionNumber", queryParamMap1);
		if (null == totalCount || totalCount.intValue() <= 0) {
			return new Page<RiskOrders>(0);
		}

		Page<RiskOrders> page = new Page<RiskOrders>(pr.getPageNumber(), pr.getPageSize(), totalCount.intValue());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.putAll(queryParamMap1);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<RiskOrders> riskOrdersList = sqlSessionTemplate.selectList("queryGameWarnOrderByCondition", filters,
				rowBounds);
		Set<String> userSet = new HashSet<String>();
		List<RiskOrders> removeRiskOrdersList = new ArrayList<RiskOrders>();
		if (riskOrdersList != null && riskOrdersList.size() > 0) {

			for (RiskOrders order : riskOrdersList) {

				String user = "" + order.getIssueCode() + order.getUserid();
				if (!userSet.contains(user)) {

					userSet.add(user);
					List<RiskOrderDetail> riskOrderDetails = sqlSessionTemplate.selectList(
							"queryGameWarnOrderByCondition2",
							makeQueryMapForQueryGameWarnOrders2(queryDTO, order.getIssueCode(), order.getUserid()));

					order.setOrderDetails(riskOrderDetails);
				} else {
					removeRiskOrdersList.add(order);
				}

			}

			riskOrdersList.removeAll(removeRiskOrdersList);

		}

		page.setResult(riskOrdersList);
		return page;
	}

	private Map<String, Object> makeQueryMapForQueryGameWarnOrders1(GameWarnOrderQueryDTO queryDTO) {
		Map<String, Object> map = new HashMap<String, Object>();

		Long userid = queryDTO.getUserid();
		GameWarnOrderQueryRequest request = queryDTO.getQueryParam();

		Long lotteryid = request.getLotteryid();

		String paramCode = request.getParamCode();
		Integer status = request.getStatus();
		Integer containSub = request.getContainSub();
		Long totalWins = request.getTotalWins();
		Long winsRatio = request.getWinsRatios();
		//Long continuousWinsTimes = request.getContinuousWinsIssue();

		if (null != userid) {
			map.put("userid", userid);
		}
		if (null != lotteryid) {
			map.put("lotteryid", lotteryid);
		}
		//此处还得根据订单编码规则区分出account或orderCode
		try {
			if (null != paramCode && ("D").equals(paramCode.substring(0, 1))
					&& VOConverter.isNumeric(paramCode.substring(4, 14))) {
				map.put("orderCode", paramCode);
			} else {
				map.put("account", paramCode);
			}
		} catch (Exception e) {
			map.put("account", paramCode);
		}
		if (null != status) {
			map.put("status", status);
		}
		if (null != containSub) {
			map.put("containSub", containSub);
		} else {
			map.put("containSub", 0);
		}
		if (null != totalWins) {
			map.put("totalWins", totalWins);
		}
		if (null != winsRatio) {
			map.put("winsRatio", winsRatio);
		}
		//最大中奖次数
		if (null != request.getWinsTime()) {
			map.put("continuousWinsTimes", request.getWinsTime());
		}
		//单期奖金
		if (null != request.getIssueAward()) {
			map.put("countWins", request.getIssueAward() * 10000);
		}

		//单期中投比
		if (null != request.getIssueWinsRatio()) {
			map.put("issueWinsRatio", request.getIssueWinsRatio());
		}
		Date startCreateTime = null, endCreateTime = null;
		if (null != request.getStartCreateTime()) {
			startCreateTime = new Date(request.getStartCreateTime());
		}
		if (null != request.getEndCreateTime()) {
			endCreateTime = new Date(request.getEndCreateTime());
		}
		if (null != startCreateTime) {
			map.put("startTime", startCreateTime);
		}
		if (null != endCreateTime) {
			map.put("endTime", endCreateTime);
		}
		return map;
	}

	private Map<String, Object> makeQueryMapForQueryGameWarnOrders2(GameWarnOrderQueryDTO queryDTO, Long issueCode,
			Long userid) {
		Map<String, Object> map = new HashMap<String, Object>();

		GameWarnOrderQueryRequest request = queryDTO.getQueryParam();

		Long lotteryid = request.getLotteryid();

		Date startCreateTime = null, endCreateTime = null;
		if (null != request.getStartCreateTime()) {
			startCreateTime = new Date(request.getStartCreateTime());
		}
		if (null != request.getEndCreateTime()) {
			endCreateTime = new Date(request.getEndCreateTime());
		}

		Long countWin = request.getBetAward();
		Long slipWinRatio = request.getBetWinsRatio();
		Integer status = request.getStatus();

		map.put("issueCode", issueCode);
		map.put("userId", userid);

		if (null != lotteryid) {
			map.put("lotteryid", lotteryid);
		}
		if (null != startCreateTime) {
			map.put("startTime", startCreateTime);
		}
		if (null != endCreateTime) {
			map.put("endTime", endCreateTime);
		}
		if (null != countWin) {
			map.put("countWin", countWin * 10000);
		}
		if (null != slipWinRatio) {
			map.put("winsRatio", slipWinRatio);
		}
		if (null != status) {
			map.put("status", status);
		}
		//此处还得根据订单编码规则区分出account或orderCode
		String paramCode = request.getParamCode();
		try {
			if (null != paramCode && ("D").equals(paramCode.substring(0, 1))
					&& VOConverter.isNumeric(paramCode.substring(4, 14))) {
				map.put("orderCode", paramCode);
			} else {
				map.put("account", paramCode);
			}
		} catch (Exception e) {
			map.put("account", paramCode);
		}

		return map;
	}

	@Override
	public Page<GameSpiteOperationsEntity> queryGameSpiteOrders(PageRequest<GameSpiteOrderQueryRequestDTO> pr)
			throws Exception {
		GameSpiteOrderQueryRequestDTO queryDTO = pr.getSearchDo();
		Map<String, Object> queryParamMap = makeQueryMapForQueryGameSpiteOrders(queryDTO);

		Number totalCount = (Number) sqlSessionTemplate.selectOne("queryGameSpiteOrdersNumber", queryParamMap);
		if (null == totalCount || totalCount.intValue() <= 0) {
			return new Page<GameSpiteOperationsEntity>(0);
		}

		Page<GameSpiteOperationsEntity> page = new Page<GameSpiteOperationsEntity>(pr.getPageNumber(),
				pr.getPageSize(), totalCount.intValue());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.putAll(queryParamMap);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<GameSpiteOperationsEntity> spiteEntityList = sqlSessionTemplate.selectList("queryGameSpiteOrders",
				filters, rowBounds);
		page.setResult(spiteEntityList);

		return page;
	}

	private Map<String, Object> makeQueryMapForQueryGameSpiteOrders(GameSpiteOrderQueryRequestDTO queryDTO) {
		Map<String, Object> map = new HashMap<String, Object>();

		Long userid = queryDTO.getUserid();
		GameSpiteOrderQueryRequest request = queryDTO.getRequest();

		Long lotteryid = request.getLotteryid();

		Date startCreateTime = null, endCreateTime = null;
		if (null != request.getStartCreateTime()) {
			startCreateTime = new Date(request.getStartCreateTime());
		}
		if (null != request.getEndCreateTime()) {
			endCreateTime = new Date(request.getEndCreateTime());
		}

		String paramCode = request.getParamCode();
		Integer status = request.getStatus();
		Integer containSub = request.getContainSub();

		if (null != userid) {
			map.put("userid", userid);
		}
		if (null != lotteryid) {
			map.put("lotteryid", lotteryid);
		}
		if (null != startCreateTime) {
			map.put("startTime", startCreateTime);
		}
		if (null != endCreateTime) {
			map.put("endTime", endCreateTime);
		}
		//此处还得根据订单编码规则区分出account或orderCode
		if (null != paramCode && ("D").equals(paramCode.substring(0, 1))
				&& VOConverter.isNumeric(paramCode.substring(4, 14))) {
			map.put("orderCode", paramCode);
		} else {
			map.put("account", paramCode);
		}
		if (null != status) {
			map.put("status", status);
		}
		if (null != containSub) {
			map.put("containSub", containSub);
		} else {
			map.put("containSub", 0);
		}

		return map;
	}

	@Override
	public void updateGameWarnOrderByLotteryIdAndIssueCode(Long lotteryId, Long issueCode, Integer status)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		map.put("status", status);

		sqlSessionTemplate.update("updateGameWarnOrderByLotteryIdAndIssueCode", map);

	}

	@Override
	public void updateGameWarnOrderByOrderCode(String orderCode, Integer status) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderCode", orderCode);
		map.put("status", status);

		if (status == 2) {
			map.put("type", status); //2为废单
		}

		sqlSessionTemplate.update("updateGameWarnOrderByOrderCode", map);
	}

	@Override
	public GameWarnOrder getGameWarnOrderByOrderCode(String orderCode) throws Exception {
		return sqlSessionTemplate.selectOne("getGameWarnOrderByOrderCode", orderCode);
	}

}
