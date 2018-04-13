package com.winterframework.firefrog.beginmession.dao;

import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginNewLog;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface BeginNewLogDao extends BaseDao<BeginNewLog> {

	public List<BeginNewLog> findByCondition(BeginNewLog log);
	
	public Page<BeginNewLog> selectByPage(PageRequest<BeginNewLog> pageRequest) throws Exception;
	
}
