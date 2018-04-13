package com.winterframework.firefrog.acl.dao;

import com.winterframework.firefrog.acl.entity.AclOperateLog;
import com.winterframework.firefrog.acl.web.dto.AclLogQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/**
 * 
* @ClassName: IAclOperateLogDao 
* @Description: 日志管理数据库接口
* @author Tod
* @date 2013-10-15 下午5:13:06 
*
 */
public interface IAclOperateLogDao {
	
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
	* @Title: selectList 
	* @Description: 查询日志列表
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	public Page<AclOperateLog> selectList(PageRequest<AclLogQueryRequest> pageRequest) throws Exception;
	
	public AclOperateLog selectById(Long id) throws Exception;

}
