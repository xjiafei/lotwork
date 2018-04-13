package com.winterframework.firefrog.acl.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.acl.dao.IAclOperateLogDao;
import com.winterframework.firefrog.acl.entity.AclOperateLog;
import com.winterframework.firefrog.acl.service.IAclOperateLogService;
import com.winterframework.firefrog.acl.web.dto.AclLogQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

@Service("aclOperateLogServiceImpl")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class AclOperateLogServiceImpl implements IAclOperateLogService {

	@Resource(name = "aclOperateLogDaoImpl")
	private IAclOperateLogDao logDao;

	@Override
	public void saveLog(AclOperateLog log) throws Exception {
		logDao.saveLog(log);
	}

	@Override
	public Page<AclOperateLog> queryList(PageRequest<AclLogQueryRequest> pageRequest) throws Exception {
		return logDao.selectList(pageRequest);
	}

	@Override
	public AclOperateLog queryLogDetail(Long id) throws Exception {
		return logDao.selectById(id);
	}

}
