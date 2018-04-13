package com.winterframework.firefrog.acl.service;

import java.util.List;

import com.winterframework.firefrog.acl.entity.AclUser;
import com.winterframework.firefrog.acl.web.dto.AclUserStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/**
 * 
* @ClassName: AclUserService 
* @Description: 用户管理业务接口
* @author Tod
* @date 2013-10-15 下午5:06:06 
*
 */
public interface AclUserService {

	public AclUser adminLogin(String userName, String password) throws Exception;

	/**
	 * 
	* @Title: saveUser 
	* @Description: 创建用户
	* @param user
	* @throws Exception
	 */
	public void saveUser(AclUser user) throws Exception;

	/**
	 * 
	* @Title: deleteUser 
	* @Description: 删除用户
	* @param id
	* @throws Exception
	 */
	public void deleteUser(Long id) throws Exception;

	/**
	 * 
	* @Title: modifyUser 
	* @Description: 编辑用户
	* @param user
	* @throws Exception
	 */
	public void modifyUser(AclUser user) throws Exception;

	public void modifyUserPwd(Long id, String oldPwd, String newPwd) throws Exception;

	/**
	 * 
	* @Title: queryList 
	* @Description: 查询用户列表
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	public Page<AclUser> queryList(PageRequest<AclUserStruc> pageRequest) throws Exception;

	/**
	 * 
	* @Title: getByUserId 
	* @Description: 查询用户详情
	* @param id
	* @return
	* @throws Exception
	 */
	public AclUser getByUserId(Long id) throws Exception;

	public AclUser getUserByBindPwd(String bindPwd) throws Exception;
	
	public List<AclUser> queryUserByBindCard(String bindCard) throws Exception;

	/** 
	 * 根据账号获取用户列表
	*/
	public List<AclUser> getUserByAccount(String param);

}
