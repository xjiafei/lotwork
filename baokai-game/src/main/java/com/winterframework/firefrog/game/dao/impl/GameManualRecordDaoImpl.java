package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameManualRecordDao;
import com.winterframework.firefrog.game.dao.vo.GameManualRecord;
import com.winterframework.firefrog.game.web.dto.GameManualRecordRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName GameManualRecordDaoImpl 
* @Description 手工录号 
* @author  hugh
* @date 2014年9月15日 下午3:09:41 
*  
*/
@Repository("gameManualRecordDaoImpl")
public class GameManualRecordDaoImpl  extends BaseIbatis3Dao<GameManualRecord> implements IGameManualRecordDao{

	@Override
	public Page<GameManualRecord> getGameManualRecordsByLottery(PageRequest<GameManualRecordRequest> pr) {	
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("lotteryId", pr.getSearchDo().getLotteryId());
		Number totalCount = (Number) sqlSessionTemplate.selectOne("getGameManualRecordsCountByLottery", filters);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<GameManualRecord>( pr.getPageSize());
		}

		Page<GameManualRecord> page = new Page<GameManualRecord>(pr.getPageNumber(), pr.getPageSize(),
				totalCount.intValue());
		
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pr.getSortColumns());

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<GameManualRecord> gis = sqlSessionTemplate.selectList("getGameManualRecordsByLottery", filters, rowBounds);
		
		page.setResult(gis);
		return page;		
	}

	@Override
	public GameManualRecord getGameManualRecordByIssue(Long issueCode) {
		return sqlSessionTemplate.selectOne("getGameManualRecordByIssue", issueCode);
	}

}
