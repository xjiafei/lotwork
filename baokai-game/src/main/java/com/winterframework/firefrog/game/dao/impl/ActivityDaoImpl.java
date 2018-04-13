package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IActivityDao;
import com.winterframework.firefrog.game.dao.vo.Activity;
import com.winterframework.firefrog.game.dao.vo.DailyActivityVo;
import com.winterframework.firefrog.game.web.dto.LotteryRecordStruc;
import com.winterframework.firefrog.game.web.dto.LotteryResultStruc;
import com.winterframework.firefrog.game.web.dto.QueryLotteryRecordRequest;
import com.winterframework.firefrog.game.web.dto.QueryLotteryResultRequest;
import com.winterframework.firefrog.game.web.dto.QueryRedEnvelopeRequest;
import com.winterframework.firefrog.game.web.dto.RedEnvelopeStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("activityDaoImpl")
public class ActivityDaoImpl   extends BaseIbatis3Dao<Activity> implements IActivityDao{

	@Override
	public List<DailyActivityVo> getDailyActivityStrucs(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("getDailyActivityStruc", map);
	}

	@Override
	public Long getRewardNumber(Map<String, Object> map) {
		return this.sqlSessionTemplate.selectOne("getRewardNumber", map);
	}

	@Override
	public Long getBetDays(Map<String, Object> map) {
		return this.sqlSessionTemplate.selectOne("getBetDays", map);
	}

	@Override
	public Page<RedEnvelopeStruc> queryRedEnvelop(
			PageRequest<QueryRedEnvelopeRequest> pr) {
		Map<String, Object> queryParamMap =new HashMap<String, Object>();
		QueryRedEnvelopeRequest queryDto=pr.getSearchDo();
		queryParamMap.put("account", queryDto.getAccount());
		queryParamMap.put("startTime", queryDto.getStartTime());
		queryParamMap.put("endTime", queryDto.getEndTime());
		queryParamMap.put("channel", queryDto.getChannel());
		queryParamMap.put("status", queryDto.getStatus());
		
		Number totalCount = (Number) sqlSessionTemplate.selectOne("getRedEnvelopTotalCount", queryParamMap);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<RedEnvelopeStruc>(0);
		}
		
		Page<RedEnvelopeStruc> page = new Page<RedEnvelopeStruc>(
				pr.getPageNumber(), pr.getPageSize(), totalCount.intValue());
		
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pr.getSortColumns());
		filters.putAll(queryParamMap);
		
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<RedEnvelopeStruc> redEnvelopeStrucList = sqlSessionTemplate.selectList("getRedEnvelopsByCondition", filters, rowBounds);
		Map<String, Object> total =new HashMap<String, Object>();
		Long totalbetAmount=0L;
		Long totalAwardAmount=0L;
		for(RedEnvelopeStruc res:redEnvelopeStrucList){
			totalbetAmount+=res.getBetAmount().longValue();
			if(res.getAward() != null)
			totalAwardAmount+=res.getAward().longValue();
		}
		total.put("betCount", totalbetAmount);
		total.put("rewardcount", totalAwardAmount);
		page.setOtherCount(total);
		page.setResult(redEnvelopeStrucList);
		return page;
	}

	@Override
	public Page<LotteryRecordStruc> queryLotteryRecord(
			PageRequest<QueryLotteryRecordRequest> pr) {
		Map<String, Object> queryParamMap =new HashMap<String, Object>();
		QueryLotteryRecordRequest queryDto=pr.getSearchDo();
		queryParamMap.put("account", queryDto.getAccount());
		queryParamMap.put("startTime", queryDto.getActivityStartTime());
		queryParamMap.put("endTime", queryDto.getActivityEndTime());
		
		Number totalCount = (Number) sqlSessionTemplate.selectOne("getLotteryRecordTotalCount", queryParamMap);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<LotteryRecordStruc>(0);
		}
		
		Page<LotteryRecordStruc> page = new Page<LotteryRecordStruc>(
				pr.getPageNumber(), pr.getPageSize(), totalCount.intValue());
		
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pr.getSortColumns());
		filters.putAll(queryParamMap);
		
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<LotteryRecordStruc> LotteryRecordStrucList = sqlSessionTemplate.selectList("getLotteryRecordByCondition", filters, rowBounds);
		
		if(!LotteryRecordStrucList.isEmpty()){
		for(LotteryRecordStruc ls:LotteryRecordStrucList){
			ls.setAwardCount(ls.getUseCount());
			ls.setUnUseCount(ls.getTotalLotteryCount()-ls.getUseCount().longValue());
			}
		}
		page.setResult(LotteryRecordStrucList);
		return page;
	}

	@Override
	public Page<LotteryResultStruc> queryLotteryResult(
			PageRequest<QueryLotteryResultRequest> pr) {
		Map<String, Object> queryParamMap =new HashMap<String, Object>();
		QueryLotteryResultRequest queryDto=pr.getSearchDo();
		queryParamMap.put("account", queryDto.getAccount());
		queryParamMap.put("startTime", queryDto.getStartTime());
		queryParamMap.put("endTime", queryDto.getEndTime());
		queryParamMap.put("awardId", queryDto.getAwardId());
		queryParamMap.put("channel", queryDto.getChannel());
		
		Number totalCount = (Number) sqlSessionTemplate.selectOne("getLotteryResultTotalCount", queryParamMap);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<LotteryResultStruc>(0);
		}
		
		Page<LotteryResultStruc> page = new Page<LotteryResultStruc>(
				pr.getPageNumber(), pr.getPageSize(), totalCount.intValue());
		
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pr.getSortColumns());
		filters.putAll(queryParamMap);
		
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<LotteryResultStruc> LotteryResultStrucList = sqlSessionTemplate.selectList("getLotteryResultByCondition", filters, rowBounds);
		
		page.setResult(LotteryResultStrucList);
		return page;
	}

}
