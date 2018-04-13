package com.winterframework.firefrog.game.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameWinsReportDao;
import com.winterframework.firefrog.game.dao.vo.GameWinsReport;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.dao.vo.WinsSumReport;
import com.winterframework.firefrog.game.entity.WinsReport;
import com.winterframework.firefrog.game.web.dto.WinsReportQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: GameWinsReportDaoImpl 
* @Description: 运营盈亏报表查询DAO实现类 
* @author Denny 
* @date 2013-10-16 下午4:04:04 
*  
*/
@Repository("gameWinsReportDaoImpl")
public class GameWinsReportDaoImpl extends BaseIbatis3Dao<GameWinsReport> implements IGameWinsReportDao {

	@Override
	public Page<WinsReport> getWinsReport(PageRequest<WinsReportQueryRequest> pr) {
		WinsReportQueryRequest requestParam = pr.getSearchDo();
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(null != requestParam.getStartCreateTime()){
			map.put("startTime", DateUtils.format(DataConverterUtil.convertLong2Date(requestParam.getStartCreateTime()), DateUtils.DATETIME_FORMAT_PATTERN));
		}
		if(null != requestParam.getEndCreateTime()){
			map.put("endTime", DateUtils.format(DataConverterUtil.convertLong2Date(requestParam.getEndCreateTime()), DateUtils.DATETIME_FORMAT_PATTERN));
		}
		
		if(null != requestParam.getLotteryid()){
			map.put("lotteryid", requestParam.getLotteryid());
		}
		/*if(requestParam.getStartCreateTime() != null){
			map.put("startTime", new Date(requestParam.getStartCreateTime()));
		}
		if(requestParam.getEndCreateTime() != null){
			map.put("endTime", new Date(requestParam.getEndCreateTime()));
		}
		*/
		Number totalCount = (Number) sqlSessionTemplate.selectOne("getCountGameWinsReport", map);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<WinsReport>(0);
		}
		
		Page<WinsReport> page = new Page<WinsReport>(pr.getPageNumber(), pr.getPageSize(), totalCount.intValue());
		
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", 	page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pr.getSortColumns());
		filters.putAll(map);
		
		List<GameWinsReport> l = new ArrayList<GameWinsReport>();
		List<WinsReport> list = new ArrayList<WinsReport>();
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
			
		l = sqlSessionTemplate.selectList("getAllGameWinsReport", filters, rowBounds);
		
		for (GameWinsReport g : l) {
			WinsReport winsReport = VOConverter.gameWinsReport2WinsReport(g);
			list.add(winsReport);
		}
		page.setResult(list);
		
		return page;
	}

	@Override
	public WinsSumReport getWinsSumReport(Long lotteryid, Long startTime, Long endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("startTime", DateUtils.format(DataConverterUtil.convertLong2Date(startTime), DateUtils.DATETIME_FORMAT_PATTERN));
//		map.put("endTime", DateUtils.format(DataConverterUtil.convertLong2Date(endTime), DateUtils.DATETIME_FORMAT_PATTERN));
		if(startTime != null){
			map.put("startTime", DateUtils.format(DataConverterUtil.convertLong2Date(startTime), DateUtils.DATETIME_FORMAT_PATTERN));
		}
		if(endTime != null){
			map.put("endTime", DateUtils.format(DataConverterUtil.convertLong2Date(endTime), DateUtils.DATETIME_FORMAT_PATTERN));
		}
		
		if(lotteryid != null && lotteryid > 0){
			map.put("lotteryid", lotteryid);
			return this.sqlSessionTemplate.selectOne("getWinsSumReport", map);
		}
		
		return this.sqlSessionTemplate.selectOne("getAllWinsSumReport", map);
	}

	@Override
	public List<GameWinsReport> getWinsDetailReport(Long lotteryid, Long issueCode, String sortColumns) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryid);
		map.put("issueCode", issueCode);
		map.put("sortColumns", sortColumns);
		return this.sqlSessionTemplate.selectList("getWinsDetailReport", map);
	}

	@Override
	public List<GameWinsReport> getWinsReportForExport(Long lotteryid, Long startTime, Long endTime, String sortColumns) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryid);
		//map.put("startTime", DateUtils.format(DataConverterUtil.convertLong2Date(startTime), DateUtils.DATETIME_FORMAT_PATTERN));
		//map.put("endTime", DateUtils.format(DataConverterUtil.convertLong2Date(endTime), DateUtils.DATETIME_FORMAT_PATTERN));
		if(startTime != null){
			map.put("startTime", DateUtils.format(DataConverterUtil.convertLong2Date(startTime), DateUtils.DATETIME_FORMAT_PATTERN));
		}
		if(endTime != null){
			map.put("endTime", DateUtils.format(DataConverterUtil.convertLong2Date(endTime), DateUtils.DATETIME_FORMAT_PATTERN));
		}
		map.put("sortColumns", sortColumns);
		return this.sqlSessionTemplate.selectList("getAllGameWinsReport", map);
	}

	@Override
	public WinsSumReport getWinsDetailSumReport(Long lotteryid, Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryid);
		map.put("issueCode", issueCode);
		
		return this.sqlSessionTemplate.selectOne("getWinsDetailSumReport", map);
	}

}
