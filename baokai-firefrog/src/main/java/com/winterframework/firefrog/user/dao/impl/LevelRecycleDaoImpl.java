package com.winterframework.firefrog.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.user.dao.ILevelRecycleDao;
import com.winterframework.firefrog.user.dao.vo.LevelRecycle;
import com.winterframework.firefrog.user.entity.LevelRecycleDTO;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("levelRecycleDaoImpl")
public class LevelRecycleDaoImpl extends BaseIbatis3Dao<LevelRecycle> implements
		ILevelRecycleDao {

	@Override
	public Page<LevelRecycle> queryLevelRecycleHistory(PageRequest<LevelRecycleDTO> pageReqeust)
			throws Exception {
		Number totalCount = (Number) sqlSessionTemplate.selectOne("getRecycleTotalCount", pageReqeust.getSearchDo());
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<LevelRecycle>(0);
		}
		
		Page<LevelRecycle> page = new Page<LevelRecycle>(
				pageReqeust.getPageNumber(), 
				pageReqeust.getPageSize(), 
				totalCount.intValue());
		
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageReqeust.getSortColumns());
		filters.put("operator", pageReqeust.getSearchDo().getOperator());
		filters.put("account", pageReqeust.getSearchDo().getAccount());
		filters.put("userId", pageReqeust.getSearchDo().getUserId());
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<LevelRecycle> list = sqlSessionTemplate.selectList("queryLevelRecycleHistory", filters, rowBounds);
		
		page.setResult(list);

		return page;
	}

	@Override
	public void applyLevelRecycle(LevelRecycleDTO levelRecycleDTO)
			throws Exception {
		sqlSessionTemplate.insert("applyLevelRecycle", levelRecycleDTO);

	}

	@Override
	public void updateRecycleStatus(LevelRecycleDTO levelRecycleDTO)
			throws Exception {
		sqlSessionTemplate.update("updateRecycleStatus", levelRecycleDTO);
		
	}


}
