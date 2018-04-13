package com.winterframework.firefrog.acl.dao;

import java.util.List;

import com.winterframework.firefrog.acl.entity.AclUser;
import com.winterframework.firefrog.acl.web.dto.AclUserStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/**
 * 
* @ClassName: AclUserDao 
* @Description: 用户管理数据库操作接口
* @author Tod
* @date 2013-10-15 下午5:15:05 
*
 */
public interface AclUserDao {

	/**
	 * 
	* @Title: saveUser 
	* @Description: 保存用户
	* @param user
	* @throws Exception
	 */
	public void saveUser(AclUser user) throws Exception;

	/**
	 * 
	* @Title: updateUser 
	* @Description: 修改用户
	* @param user
	* @throws Exception
	 */
	public void updateUser(AclUser user) throws Exception;

	/**
	 * 
	* @Title: deleteUser 
	* @Description: 删除用户
	* @param id
	 */
	public void deleteUser(Long id);

	/**
	 * 
	* @Title: selectList 
	* @Description: 查询用户列表
	* @param pageRequest
	* @return
	 */
	public Page<AclUser> selectList(PageRequest<AclUserStruc> pageRequest);

	/**
	 * 
	* @Title: getByUserId 
	* @Description: 获取用户详情
	* @param id
	* @return
	* @throws Exception
	 */
	public AclUser getByUserId(Long id) throws Exception;

	public AclUser getUserByBindPwd(String bindPwd) throws Exception;
	public String getUserByBindCard(String bindCard) throws Exception;

	/**
	 * 
	* @Title: checkUserByGroupIds 
	* @Description: 
	* @param ids
	* @return
	* @throws Exception
	 */
	public Boolean checkUserByGroupIds(List<Long> ids) throws Exception;

	public AclUser getUserByName(String name) throws Exception;

	public AclUser getUserByNamePwd(String name, String pwd) throws Exception;

	/** 
	 * 根据账号获取用户列表
	*/
	public List<AclUser> getUserByAccount(String account);
	
	public List<AclUser> queryUserByBindCard(String bindCard) throws Exception;

}
