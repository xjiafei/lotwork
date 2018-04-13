package com.winterframework.firefrog.beginmession.service;

import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginNewLog;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.LogType;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

public interface BeginNewLogService {

	public Page<BeginNewLog> getAllByPage(PageRequest<BeginNewLog> request)throws Exception;
	
	public void insert(List<BeginNewLog> logs);

	public <T> void compareBforeAfter(T befor, T after, LogType type,String logUser) throws Exception;
	
}
