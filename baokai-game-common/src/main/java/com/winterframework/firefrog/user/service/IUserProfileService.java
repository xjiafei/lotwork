package com.winterframework.firefrog.user.service;

import java.util.List;

import com.winterframework.firefrog.user.entity.CustomerQueryDTO;
import com.winterframework.firefrog.user.entity.LoginLog;
import com.winterframework.firefrog.user.entity.QAInfo;
import com.winterframework.firefrog.user.entity.QueryGeneralAgentDTO;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserProfile;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

public interface IUserProfileService {

	public UserProfile queryUserProfile(long id) throws Exception;

	public List<LoginLog> queryUserLoginLog(long id) throws Exception;

	public void saveUserProfile(long id, UserProfile profile) throws Exception;

	public void saveWithdrawPassword(long id, String withdrawPwd) throws Exception;

	public void saveEmail(long id, String email) throws Exception;

	public void saveCipher(long id, String cipher) throws Exception;

	public void savePassword(long id, String password, int passwordLevel) throws Exception;

	public void saveSecurityQA(long id, List<QAInfo> qaList) throws Exception;

	public List<QAInfo> querySecurityQA(long id) throws Exception;

	public User queryUserByName(String name) throws Exception;

	public void saveWithdrawPasswordAndSecurityQA(long id, List<QAInfo> qaList, String withdrawPwd) throws Exception;

	public void savePasswdAndWithdrawPasswordAndSecurityQA(long id, List<QAInfo> qaList, String withdrawPwd,
			String pwd, int pwdLevel) throws Exception;

	/**
	 * 根据条件查询客户信息
	 * 
	 * @param pageRequest
	 * @return
	 * @throws Exception
	 */
	public Page<User> searchCustomer(PageRequest<CustomerQueryDTO> pageRequest) throws Exception;

	/**
	 * 
	* @Title: queryGeneralAgentByCriteria 
	* @Description: 总代用户查询代理接口
	* @param @param pageRequest
	* @param @return
	* @param @throws Exception    设定文件 
	* @return Page<User>    返回类型 
	* @throws
	 */
	public Page<User> queryGeneralAgentByCriteria(PageRequest<QueryGeneralAgentDTO> pageRequest) throws Exception;

	/**
	 * 
	* @Title: createGeneralAgentUser 
	* @Description: 创建总代用户 
	* @param account
	* @param password
	* @param agentLimit
	* @throws Exception
	 */
	public void createGeneralAgentUser(String account, String password, Integer agentLimit) throws Exception;

	/**
	 * 
	* @Title: updateGeneralAgentUser 
	* @Description: 更新总代用户配额 
	* @param userId
	* @param availableQuota
	* @throws Exception
	 */
	public void updateGeneralAgentUser(Long userId, Integer availableQuota) throws Exception;

	/**
	 * 
	* @Title: queryUserById 
	* @Description: 根据用户ID获取用户信息 
	* @param @param id
	* @param @return
	* @param @throws Exception    设定文件 
	* @return User    返回类型 
	* @throws
	 */
	public User queryUserById(long id) throws Exception;
	
	/**
	 * 
	 * 方法描述：根据用户id获取用户及下级用户信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<User> getUserAndSubUserList(long userId) throws Exception;

	public void saveUserHeadImg(Long userId,String nickName,String headImg) throws Exception;
	
	public boolean checkUserNickNameOnly(String nickName) throws Exception;
	
}