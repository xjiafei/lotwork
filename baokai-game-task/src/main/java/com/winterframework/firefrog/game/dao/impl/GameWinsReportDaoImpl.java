package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameWinsReportDao;
import com.winterframework.firefrog.game.dao.vo.GameWinsReport;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameWinsReportDaoImpl")
public class GameWinsReportDaoImpl extends BaseIbatis3Dao<GameWinsReport> implements IGameWinsReportDao {

	@Override
	public List<String> getBetTypeCodeListByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryId);
		map.put("issueCode", issueCode);
		
		return this.sqlSessionTemplate.selectList("getBetTypeCodeListByLotteryIdAndIssueCode", map);
	}

	@Override
	public Long getTotalSaleAmountByIssueCodeAndBetTypeCode(Long lotteryId, Long issueCode, String betTypeCode) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryId);
		map.put("issueCode", issueCode);
		map.put("betTypeCode", betTypeCode);
		
		return this.sqlSessionTemplate.selectOne("getTotalSaleAmountByIssueCodeAndBetTypeCode", map);
	}

	@Override
	public Long getTotalWinAmountByIssueCodeAndByTypeCode(Long lotteryId, Long issueCode, String betTypeCode) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("lotteryid", lotteryId);
		map.put("issueCode", issueCode);
		map.put("betTypeCode", betTypeCode);
		
		return this.sqlSessionTemplate.selectOne("getTotalWinAmountByIssueCodeAndByTypeCode", map);
	}

	@Override
	public Long getCancelAmountByIssueCodeAndBetTypeCode(Long lotteryId, Long issueCode, String betTypeCode) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("lotteryid", lotteryId);
		map.put("issueCode", issueCode);
		map.put("betTypeCode", betTypeCode);
		
		return this.sqlSessionTemplate.selectOne("getCancelAmountByIssueCodeAndBetTypeCode", map);
	}
	@Override
	public Long getPlanCancelFeeByLotteryIdAndIssueCode(Long lotteryId,
			Long issueCode) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode); 
		
		return this.sqlSessionTemplate.selectOne("getPlanCancelFeeByLotteryIdAndIssueCode", map);
	}

	@Override
	public List<String> getRetPointByIssueCodeAndBetTypeCode(Long lotteryId, Long issueCode, String betTypeCode) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("lotteryid", lotteryId);
		map.put("issueCode", issueCode);
		map.put("betTypeCode", betTypeCode);
		return this.sqlSessionTemplate.selectList("getRetPointByIssueCodeAndBetTypeCode", map);
	}
	@Override
	public List<Long> getGameIssueCodeListNoWinsReport(Long lotteryId)
			throws Exception {
		return this.sqlSessionTemplate.selectList("getGameIssueCodeListNoWinsReport", lotteryId);
	}

	@Override
	public Long getTotalIssueSaleAmountByIssueCode(Long lotteryId,
			Long issueCode) throws Exception {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("lotteryid", lotteryId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.selectOne("getTotalIssueSaleAmountByIssueCode", map);
	}

	
}
