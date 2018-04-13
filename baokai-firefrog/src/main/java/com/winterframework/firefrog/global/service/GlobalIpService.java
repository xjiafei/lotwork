package com.winterframework.firefrog.global.service;

import java.util.List;

import com.winterframework.firefrog.global.entity.IPAddress;
import com.winterframework.firefrog.global.web.dto.IpSearchDto;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/**
 * 
* @ClassName: GlobalIpService 
* @Description: IP黑白名单列表业务接口
* @author Tod
* @date 2013-10-15 下午4:53:24 
*
 */
public interface GlobalIpService {

	/**
	 * 
	* @Title: saveIp 
	* @Description: 新增IP
	* @param ip
	* @param period
	* @throws Exception
	 */
	public void saveIp(IPAddress ip, int period) throws Exception;

	/**
	 * 
	* @Title: deleteIp 
	* @Description: 删除IP
	* @param ids
	* @throws Exception
	 */
	public void deleteIp(List<Long> ids) throws Exception;

	/**
	 * 
	* @Title: queryList 
	* @Description: 查询IP列表
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	public Page<IPAddress> queryList(PageRequest<IpSearchDto> pageRequest) throws Exception;
	
	/**
	 * 
	* @Title: isIpLimit
	* @Description: 查询当前ip是否被限制 
	* @param ids
	* @throws Exception
	 */
	public Boolean isIpLimit(String ip,String limitType) throws Exception;
	
	public IPAddress getSameIpLimit(String ip, String limitType) throws Exception;

}
