package com.winterframework.firefrog.acl.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.acl.dao.IAclOperateLogDao;
import com.winterframework.firefrog.acl.dao.vo.AclOperateLogVO;
import com.winterframework.firefrog.acl.dao.vo.VOConverter;
import com.winterframework.firefrog.acl.entity.AclOperateLog;
import com.winterframework.firefrog.acl.web.dto.AclLogQueryRequest;
import com.winterframework.firefrog.common.util.IPConverter;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("aclOperateLogDaoImpl")
public class AclOperateLogDaoImpl extends BaseIbatis3Dao<AclOperateLogVO> implements IAclOperateLogDao {

	@Override
	public void saveLog(AclOperateLog log) throws Exception {
		AclOperateLogVO vo = VOConverter.transLog2VO(log);
		this.insert(vo);
	}

	@Override
	public Page<AclOperateLog> selectList(PageRequest<AclLogQueryRequest> pageRequest) throws Exception {
		AclLogQueryRequest search = pageRequest.getSearchDo();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", search.getAccount());
		map.put("id", search.getSeqNo());
		map.put("ip", search.getIp() == null ? null : IPConverter.longToIp(search.getIp()));
		map.put("startTime", search.getStartTime());
		map.put("endTime", search.getEndTime());
        map.put("groupId", search.getGroupId());
        map.put("userId", search.getUserId());
		int totalCount = ((Long) sqlSessionTemplate.selectOne("getLogCountByPage", map)).intValue();

		Page<AclOperateLog> page = new Page<AclOperateLog>(pageRequest.getPageNumber(), pageRequest.getPageSize(),
				totalCount);

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<AclOperateLogVO> list = sqlSessionTemplate.selectList("getLogByPage", filters, rowBounds);

		List<AclOperateLog> logList = new ArrayList<AclOperateLog>();
		for (AclOperateLogVO vo : list) {
			logList.add(VOConverter.transVO2Log(vo));
		}
		page.setResult(logList);
		return page;
	}

	@Override
	public AclOperateLog selectById(Long id) throws Exception {
		return VOConverter.transVO2Log(this.getById(id));
	}
}
