package com.winterframework.firefrog.game.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameWarnIssueDao;
import com.winterframework.firefrog.game.dao.vo.GameWarnEvent;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssue;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueList;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.GameWarnIssueEntity;
import com.winterframework.firefrog.game.entity.LotteryIssueMonitorLogs;
import com.winterframework.firefrog.game.web.dto.LotteryMonitorListRequestDTO;
import com.winterframework.firefrog.game.web.dto.QueryLotteryIssueWarnDTO;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;
/**
 * 
* @ClassName: GameWarnIssueDaoImpl 
* @Description: 警告奖期DAO实现类
* @author Richard
* @date 2013-12-27 下午4:13:09 
*
 */
@Repository("gameWarnIssueDaoImpl")
public class GameWarnIssueDaoImpl extends BaseIbatis3Dao<GameWarnIssue> implements IGameWarnIssueDao {
	
	@Override
	public int saveOrUpdate(GameWarnIssue warn) throws Exception {
		int i = 0;
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("issueCode", warn.getIssueCode());
		map.put("lotteryId", warn.getLotteryid());
		GameWarnIssue db = this.sqlSessionTemplate.selectOne("queryGameWarnIssueByLotteryIdAndIssueCode",map);
		if(db !=null){
			warn.setId(db.getId());
			if(db.getStatusRout() != null){
				warn.setStatusRout(db.getStatusRout() +","+ warn.getStatusRout());
			}			
			i = update(warn);
		}else{
			i = insert(warn);
		}	
		return i;
	}

	@Override
	public int updateIfHave(GameWarnIssue warn) throws Exception {
		int i = 0;
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("issueCode", warn.getIssueCode());
		map.put("lotteryId", warn.getLotteryid());
		GameWarnIssue db = this.sqlSessionTemplate.selectOne("queryGameWarnIssueByLotteryIdAndIssueCode",map);
		if(db !=null){
			warn.setId(db.getId());
			if(db.getStatusRout() != null){
				warn.setStatusRout(db.getStatusRout() +","+ warn.getStatusRout());
			}			
			i = update(warn);
		}	
		return i;
	}
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	@Override
	public List<GameWarnIssueEntity> queryGameWarnIssueNotices() throws Exception {
		
		List<GameWarnIssue> list = this.sqlSessionTemplate.selectList("queryLotteryIssueNotices");
		List<GameWarnIssueEntity> entities = new ArrayList<GameWarnIssueEntity>();
		for(GameWarnIssue issue : list){
			entities.add(VOConverter.convertGameWarnIssue2Entity(issue));
		}
		return entities;
	}
	
	
	/** 
	* @Title: updateNoticeStatus 
	* @Description: 变已阅 
	* @param issueCode
	* @throws Exception
	*/
	public void updateNoticeStatus(Long lotteryId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();		
		//map.put("issueCode",issueCode);
		map.put("lotteryId", lotteryId);
		this.sqlSessionTemplate.update("updateNoticeStatus",map);
		
	}

	@Override
	public Page<GameWarnIssueList> queryGameWarnIssueList(PageRequest<LotteryMonitorListRequestDTO> pageRequest) throws Exception {
		
		LotteryMonitorListRequestDTO dto = pageRequest.getSearchDo();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("startIssueTime", dto.getStartIssueTime() == null ? null : dateFormat.format(dto.getStartIssueTime()));
		String date = dateFormat.format(new Date());
		if( (dto.getStartIssueTime() != null && date.equals(dateFormat.format(dto.getStartIssueTime())))
				||(dto.getEndIssueTime()!=null && date.equals(dateFormat.format(dto.getEndIssueTime())))
				){
			map.put("endIssueTime",null);
		}else{
			map.put("endIssueTime", dto.getEndIssueTime() == null ? null : dateFormat.format(dto.getEndIssueTime()));			
		}
		map.put("lotteryid", dto.getLotteryId()>0 ? dto.getLotteryId() : null);
		
		Number totalCount = (Number) sqlSessionTemplate.selectOne("queryCountGameWarnIssueList", map);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<GameWarnIssueList>(0);
		}
		
		Page<GameWarnIssueList> page = new Page<GameWarnIssueList>(pageRequest.getPageNumber(), pageRequest.getPageSize(), totalCount.intValue());
		
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", 	page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);
		
		List<GameWarnIssueList> list = new ArrayList<GameWarnIssueList>();
		
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
			
		list = sqlSessionTemplate.selectList("queryAllGameWarnIssueList", filters, rowBounds);
		page.setResult(list);
		
		return page;
	}

	@Override
	public Page<GameWarnIssueList> queryGameWarnIssueOnSale(PageRequest<LotteryMonitorListRequestDTO> pageRequest)
			throws Exception {
		LotteryMonitorListRequestDTO dto = pageRequest.getSearchDo();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("startIssueTime", dto.getStartIssueTime() == null ? null : dateFormat.format(dto.getStartIssueTime()));
		String date = dateFormat.format(new Date());
		if( (dto.getStartIssueTime() != null && date.equals(dateFormat.format(dto.getStartIssueTime())))
				||(dto.getEndIssueTime()!=null && date.equals(dateFormat.format(dto.getEndIssueTime())))
				){
			map.put("endIssueTime",null);
		}else{
			map.put("endIssueTime", dto.getEndIssueTime() == null ? null : dateFormat.format(dto.getEndIssueTime()));			
		}
		map.put("lotteryid", dto.getLotteryId()>0 ? dto.getLotteryId() : null);
		
		Number totalCount = (Number) sqlSessionTemplate.selectOne("queryCountGameWarnIssueOnSale", map);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<GameWarnIssueList>(0);
		}
		
		Page<GameWarnIssueList> page = new Page<GameWarnIssueList>(pageRequest.getPageNumber(), pageRequest.getPageSize(), totalCount.intValue());
		
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", 	page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);
		
		List<GameWarnIssueList> list = new ArrayList<GameWarnIssueList>();
		
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
			
		list = sqlSessionTemplate.selectList("queryGameWarnIssueOnSale", filters, rowBounds);
		page.setResult(list);
		
		return page;
	}
	
	@Override
	public Page<GameWarnIssueList> queryGameWarnOnlyCurrntIssue(PageRequest<LotteryMonitorListRequestDTO> pageRequest) throws Exception{
			
		LotteryMonitorListRequestDTO dto = pageRequest.getSearchDo();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("startIssueTime", dto.getStartIssueTime() == null ? null : dateFormat.format(dto.getStartIssueTime()));
		String date = dateFormat.format(new Date());
		if( (dto.getStartIssueTime() != null && date.equals(dateFormat.format(dto.getStartIssueTime())))
				||(dto.getEndIssueTime()!=null && date.equals(dateFormat.format(dto.getEndIssueTime())))
				){
			map.put("endIssueTime",null);
		}else{
			map.put("endIssueTime", dto.getEndIssueTime() == null ? null : dateFormat.format(dto.getEndIssueTime()));			
		}
		map.put("lotteryid", dto.getLotteryId()>0 ? dto.getLotteryId() : null);
		
		Number totalCount = (Number) sqlSessionTemplate.selectOne("queryCountGameWarnOnlyCurrntIssue", map);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<GameWarnIssueList>(0);
		}
		
		Page<GameWarnIssueList> page = new Page<GameWarnIssueList>(pageRequest.getPageNumber(), pageRequest.getPageSize(), totalCount.intValue());
		
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", 	page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);
		
		List<GameWarnIssueList> list = new ArrayList<GameWarnIssueList>();
		
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
			
		list = sqlSessionTemplate.selectList("queryGameWarnOnlyCurrntIssue", filters, rowBounds);
		page.setResult(list);
		
		return page;
	}

	@Override
	public Page<GameWarnIssueList> queryGameWarnIssue(PageRequest<LotteryMonitorListRequestDTO> pageRequest) throws Exception {
		
		LotteryMonitorListRequestDTO dto = pageRequest.getSearchDo();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("startIssueTime", dto.getStartIssueTime() == null ? null : dateFormat.format(dto.getStartIssueTime()));
		String date = dateFormat.format(new Date());
		if( (dto.getStartIssueTime() != null && date.equals(dateFormat.format(dto.getStartIssueTime())))
				||(dto.getEndIssueTime()!=null && date.equals(dateFormat.format(dto.getEndIssueTime())))
				){
			map.put("endIssueTime",null);
		}else{
			map.put("endIssueTime", dto.getEndIssueTime() == null ? null : dateFormat.format(dto.getEndIssueTime()));			
		}
		
		map.put("lotteryid", dto.getLotteryId()>0 ? dto.getLotteryId() : null);
		
		Number totalCount = (Number) sqlSessionTemplate.selectOne("queryCountGameWarnIssue", map);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<GameWarnIssueList>(0);
		}
		
		Page<GameWarnIssueList> page = new Page<GameWarnIssueList>(pageRequest.getPageNumber(), pageRequest.getPageSize(), totalCount.intValue());
		
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", 	page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);
		
		List<GameWarnIssueList> list = new ArrayList<GameWarnIssueList>();
		
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
			
		list = sqlSessionTemplate.selectList("queryGameWarnIssue", filters, rowBounds);
		page.setResult(list);
		
		return page;
	}

	@Override
	public List<GameWarnIssueEntity> queryGameWarnIssueByLotteryIdAndIssueCode(Long lotteryId, Long issueCode)
			throws Exception {
		
		List<GameWarnIssueEntity> issueEns=new ArrayList<GameWarnIssueEntity>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		
		List<GameWarnIssue> issues = this.sqlSessionTemplate.selectList("queryGameWarnIssueByLotteryIdAndIssueCode", map);
		if(!issues.isEmpty()){
		for(GameWarnIssue issue:issues){
			issueEns.add(VOConverter.convertGameWarnIssue2Entity(issue));
		}
		}
		return issueEns;
	}

	@Override
	public Page<LotteryIssueMonitorLogs> queryLotteryIssueWarnLog(PageRequest<QueryLotteryIssueWarnDTO> pageRequest)
			throws Exception {
		
		QueryLotteryIssueWarnDTO issueWarnDTO = pageRequest.getSearchDo();
		Map<String, Object> map = new HashMap<String, Object>();
		
		String startCreateTime = issueWarnDTO.getStartCreateTime()== null ? null : dateFormat.format(issueWarnDTO.getStartCreateTime());
		String endCreateTime = issueWarnDTO.getEndCreateTime()== null ? null : dateFormat.format(issueWarnDTO.getEndCreateTime());
		
		map.put("lotteryId", issueWarnDTO.getLotteryid() > 0 ? issueWarnDTO.getLotteryid() : null );
		map.put("event", issueWarnDTO.getWarnType() > 0 ? GameWarnEvent.getCodeByPageCode(issueWarnDTO.getWarnType())   : null);
		
		
		if(null != startCreateTime){
			if(startCreateTime.equals(endCreateTime)){
				map.put("todayTime", startCreateTime);
			}else{
				map.put("startCreateTime", startCreateTime);
				map.put("endCreateTime", endCreateTime);
			}
		}
		
		Number totalCount = (Number) sqlSessionTemplate.selectOne("queryLotteryIssueWarnLogCount", map);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<LotteryIssueMonitorLogs>(0);
		}
		
		Page<LotteryIssueMonitorLogs> page = new Page<LotteryIssueMonitorLogs>(pageRequest.getPageNumber(), pageRequest.getPageSize(), totalCount.intValue());
		
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", 	page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);
		
		List<LotteryIssueMonitorLogs> list = new ArrayList<LotteryIssueMonitorLogs>();
		
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
			
		list = sqlSessionTemplate.selectList("queryLotteryIssueWarnLog", filters, rowBounds);
		page.setResult(list);
		
		return page;
	}


}
