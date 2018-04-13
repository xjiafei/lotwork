package com.winterframework.firefrog.global.dao;

import java.util.List;

import com.winterframework.firefrog.global.entity.IPAddress;
import com.winterframework.firefrog.global.web.dto.IpSearchDto;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/**
 * 
* @ClassName: GlobalIpDao 
* @Description: IP黑白名单数据库操作接口
* @author 你的名字 
* @date 2013-10-15 下午4:57:37 
*
 */
public interface GlobalIpDao {

	/**
	 * 
	* @Title: saveIp 
	* @Description: 保存IP
	* @param ip
	* @throws Exception
	 */
	public void saveIp(IPAddress ip) throws Exception;

	/**
	 * 
	* @Title: deleteIp 
	* @Description: 删除IP
	* @param id
	* @throws Exception
	 */
	public void deleteIp(Long id) throws Exception;

	/**
	 * 
	* @Title: selectList 
	* @Description: 查询IP列表
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	public Page<IPAddress> selectList(PageRequest<IpSearchDto> pageRequest) throws Exception;

	/**
	 * 
	* @Title: queryEffectIpList 
	* @Description: 查询有效的iplist
	* @param List
	* @return
	* @throws Exception
	 */
	public List<IPAddress> queryEffectIpList(String type) throws Exception;

}
