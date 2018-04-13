package com.winterframework.firefrog.acl.service;

import com.winterframework.firefrog.acl.entity.AclOperateLog;
import com.winterframework.firefrog.acl.web.dto.AclLogQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/**
 * 
* @ClassName: IAclOperateLogService 
* @Description: 日志管理业务接口
* @author Tod
* @date 2013-10-15 下午5:09:00 
*
 */
public interface IAclOperateLogService {

	/**
	 * 
	* @Title: saveLog 
	* @Description: 保存日志
	* @param log
	* @throws Exception
	 */
	public void saveLog(AclOperateLog log) throws Exception;

	/**
	 * 
	* @Title: queryList 
	* @Description: 查询日志列表
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	public Page<AclOperateLog> queryList(PageRequest<AclLogQueryRequest> pageRequest) throws Exception;

	public AclOperateLog queryLogDetail(Long id) throws Exception;

}
