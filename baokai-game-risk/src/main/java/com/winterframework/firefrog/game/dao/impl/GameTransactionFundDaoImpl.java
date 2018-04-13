package com.winterframework.firefrog.game.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameTransactionFundDao;
import com.winterframework.firefrog.game.dao.vo.GameReportCountVo;
import com.winterframework.firefrog.game.dao.vo.GameTransactionFund;
import com.winterframework.firefrog.game.web.dto.GameReportRequest;
import com.winterframework.firefrog.game.web.dto.GameReportStruc;
import com.winterframework.firefrog.game.web.dto.GameRiskTransactionReportStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameTransactionFundDaoImpl")
public class GameTransactionFundDaoImpl extends BaseIbatis3Dao<GameTransactionFund> implements IGameTransactionFundDao {

	@Override
	public void saveTransactionFund(Long userid, String type, Long amount, Long status) throws Exception {

		log.info("save GameTransactionFund.");
		GameTransactionFund transactionFund = new GameTransactionFund();
		transactionFund.setAmount(amount);
		transactionFund.setCreateTime(new Date());
		transactionFund.setFundType(type);
		transactionFund.setStatus(status); //发送成功
		transactionFund.setOperatorid(userid);
		transactionFund.setUserid(1L); //默认系统
		insert(transactionFund);
	}

	@Override
	public List<GameTransactionFund> queryTransactionByCode(Long userId, List<String> conditionList) {

		List<GameTransactionFund> list = new ArrayList<GameTransactionFund>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userId);

		if (!conditionList.isEmpty()) {
			for (Iterator<String> iterator = conditionList.iterator(); iterator.hasNext();) {
				String value = (String) iterator.next();

				map.put("fundType", value);
				List<GameTransactionFund> result = this.sqlSessionTemplate.selectList("queryTransactionByCode", map);

				list.addAll(result);
			}
		}

		return list;
	}

	@Override
	public Page<GameRiskTransactionReportStruc> queryTransactionReport(PageRequest<GameReportRequest> pageRequest) {
		log.info("queryAllTranscation .");

		Map<String, Object> filters = new HashMap<String, Object>();
		GameReportRequest search = pageRequest.getSearchDo();
		//封装参数
		if (pageRequest.getSearchDo() != null) {
			filters.put("startTime", DateUtils.format(search.getStartTime(), DateUtils.DATETIME_FORMAT_PATTERN));
			filters.put("endTime", DateUtils.format(search.getEndTime(), DateUtils.DATETIME_FORMAT_PATTERN));

			if (search.getGameType() != null && search.getGameType().equals("追号单")) {
				filters.put("isZhuiHao", 1);
			} else if (search.getGameType() != null && search.getGameType().equals("投注单")) {
				filters.put("isTouzhu", 1);
			} else {
				filters.put("isZhuiHao", null);
				filters.put("isTouzhu", null);
			}
			filters.put("userName", search.getUserName());
			//增加包含下级用户选项
			filters.put("containSub", search.getContainSub());
			//游戏提供复选
			filters.put("lotteryName", setStringToList(search.getLotteryName()));

			filters.put("tid", search.getTid());
			filters.put("planCode", search.getPlanCode());
			filters.put("orderCode", search.getOrderCode());
			//摘要提供复选
			filters.put("reason", setStringToList(search.getReson()));
			if (search.getAmonut() != null) {
				filters.put("amount", search.getAmonut() * 10000);
			} else {
				filters.put("amount", null);
			}

			filters.put("status", search.getStatus());
		} else {
			filters.put("startTime", null);
			filters.put("endTime", null);
			filters.put("isZhuiHao", null);
			filters.put("isTouzhu", null);

			filters.put("userName", null);
			filters.put("lotteryName", null);

			filters.put("tid", null);
			filters.put("planCode", null);
			filters.put("orderCode", null);
			filters.put("reason", null);
			filters.put("amonut", null);
			filters.put("status", null);
		}
		Number totalCount = (Number) sqlSessionTemplate.selectOne("getPageTransactionCount", filters);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<GameRiskTransactionReportStruc>(0);
		}

		Page<GameRiskTransactionReportStruc> page = new Page<GameRiskTransactionReportStruc>(
				pageRequest.getPageNumber(), pageRequest.getPageSize(), totalCount.intValue());
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<GameTransactionFund> gos = sqlSessionTemplate.selectList("getPageTransaction", filters, rowBounds);
		List<GameRiskTransactionReportStruc> gosui = parseForUI(gos);
		page.setResult(gosui);

		return page;
	}

	private List<String> setStringToList(String sourceStr) {
		if (sourceStr == null) {
			return null;
		}
		String[] sourceArr = sourceStr.split(",");
		List<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList(sourceArr));
		return list;
	}

	public List<GameRiskTransactionReportStruc> parseForUI(List<GameTransactionFund> gos) {
		List<GameRiskTransactionReportStruc> gosui = new ArrayList<GameRiskTransactionReportStruc>();
		for (GameTransactionFund fund : gos) {
			GameRiskTransactionReportStruc ui = new GameRiskTransactionReportStruc();
			ui.setTid(fund.getTid());
			ui.setUserName(fund.getOperatorName());
			ui.setTradeDate(DateUtils.format(fund.getCreateTime(), DateUtils.DATETIME_FORMAT_PATTERN));
			if (fund.getPlanCode() == null) {
				ui.setPlanCode("");
				ui.setGameType("投注单");
			} else {
				ui.setPlanCode(fund.getPlanCode());
				ui.setGameType("追号单");
			}
			if (fund.getOrderCode() == null) {
				ui.setOrderCode("");
			} else {
				ui.setOrderCode(fund.getOrderCode());
			}
			ui.setReson(fund.getReason());
			ui.setAmonut(fund.getAmount());
			ui.setLotteryName(fund.getLotteryName());
			int status = fund.getStatus().intValue();
			if (status == 0) {
				ui.setStatus("处理中");
			} else if (status == 1) {
				ui.setStatus("已完成");
			} else if (status == 2) {
				ui.setStatus("未完成");
			} else if (status == 3) {
				ui.setStatus("已撤销");
			}
			gosui.add(ui);
		}
		return gosui;
	}

	@Override
	public Page<GameReportStruc> queryAllTransaction(PageRequest<GameReportRequest> pageRequest) {
		log.info("queryAllTranscation .");

		Number totalCount = (Number) this.getCount();
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<GameReportStruc>(0);
		}

		Page<GameReportStruc> page = new Page<GameReportStruc>(pageRequest.getPageNumber(), pageRequest.getPageSize(),
				totalCount.intValue());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<GameReportStruc> gos = sqlSessionTemplate.selectList("getAllTransaction", filters, rowBounds);
		page.setResult(gos);

		return page;
	}

	/** 
	* @Title: getFundCountGroup 
	* @Description: 摘要分类统计
	* @param lotteryId
	* @param issueCode
	* @return
	*/
	public List<GameReportCountVo> getFundCountGroup(Long lotteryId, Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.selectList("getFundCountGroup", map);
	}
}
